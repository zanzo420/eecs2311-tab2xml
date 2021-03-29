package tab2xml.xmlconversion;

import org.w3c.dom.Document;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;

import tab2xml.model.Instrument;
import tab2xml.model.Line;
import tab2xml.model.Score;
import tab2xml.model.Staff;
import tab2xml.model.drum.DrumStaff;
import tab2xml.model.LineItem;
import tab2xml.model.Measure;
import tab2xml.model.Note;
import tab2xml.model.guitar.GuitarNote;
import tab2xml.model.guitar.GuitarStaff;
import tab2xml.model.guitar.GuitarString;

/**
 * The transformer which generates the XML output as a string.
 * 
 * @author amir
 */
public class Transform<T extends Staff<? extends Line<? extends Note>, ? extends Note>> {
	private final Score<T> sheet;
	MusicSheet musicSheet;
	private Document doc;
	private DocumentBuilder dBuilder;
	private DocumentBuilderFactory dbFactory;
	private XMLMetadata metadata;
	private Instrument instrument;

	/**
	 * Construct a transformer that accepts a specified score and an instrument.
	 * 
	 * @param sheet      the score retrieved by the parser
	 * @param instrument the type of instrument corresponding to this score
	 * @param metadata   the metadata that should be applied to the XML
	 */
	public Transform(Score<T> sheet, Instrument instrument, XMLMetadata metadata) {
		this.sheet = sheet;
		this.dbFactory = DocumentBuilderFactory.newInstance();
		try {
			this.dBuilder = dbFactory.newDocumentBuilder();
			this.doc = dBuilder.newDocument();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		this.musicSheet = new MusicSheet(doc, dBuilder, dbFactory);
		this.instrument = instrument;
		this.metadata = metadata;
		switch (instrument) {
		case GUITAR:
		case BASS:
			generateGuitarBass();
			break;
		case DRUM:
			generateDrum();
			break;
		default:
			throw new IllegalArgumentException("Unsupported instrument: " + instrument);
		}
	}

	/**
	 * A string of the music sheet in musicXML format.
	 * 
	 * @return a string format of the converted XML
	 */
	public String toXML() {
		return musicSheet.toXML();
	}

	/**
	 * Generate XML from data for selected instrument, Guitar or Bass.
	 * 
	 */
	private void generateGuitarBass() {
		XMLElement root = new XMLElement("score-partwise", musicSheet);
		setDefaults(root);
		XMLElement part1 = new XMLElement("part", musicSheet);
		part1.setAttribute("id", "P1");
		root.append(part1);

		int measureCount = sheet.numberOfMeasures();
		ArrayList<XMLElement> measures = new ArrayList<>();
		measures.ensureCapacity(measureCount);

		for (int i = 0; i < measureCount; i++) {
			XMLElement measure = new XMLElement("measure", musicSheet);
			measure.setAttribute("number", Integer.toString(i + 1));
			measures.add(measure);
		}

		GuitarStaff staff = (GuitarStaff) sheet.getStaffs().get(0);

		// if not selected by user set to default
		staff.setBeats("4");
		staff.setBeatType("4");
		setGuitarBassStaffAttributes(staff, measures.get(0));

		for (Measure<? extends Note> m : sheet.getMeasures()) {
			for (LineItem item : m) {
				GuitarNote note = (GuitarNote) item;
				if (note == null)
					continue;
				addNoteToMeasure(note, note.getMeasure(), measures);
			}
		}

		for (XMLElement measure : measures)
			part1.append(measure);
	}

	/**
	 * Append a note element to its correct measure.
	 * 
	 * @param currNote    the note to be added to specified measure
	 * @param currMeasure the index of the measure to append to
	 * @param measures    the list of all the measures
	 */
	private void addNoteToMeasure(GuitarNote currNote, int currMeasure, ArrayList<XMLElement> measures) {
		if (measures.get(currMeasure) != null) {
			XMLElement note = new XMLElement("note", musicSheet);

			if (currNote.isChord()) {
				XMLElement chord = new XMLElement("chord", musicSheet);
				note.append(chord);
			}

			if (currNote.isGrace()) {
				XMLElement grace = new XMLElement("grace", musicSheet);
				note.append(grace);
			}

			XMLElement pitch = new XMLElement("pitch", musicSheet);
			XMLElement step = new XMLElement("step", musicSheet);
			XMLElement alter = null;

			if (currNote.getStep().contains("#")) {
				step.setText(currNote.getStep().substring(0, 1));
				alter = new XMLElement("alter", musicSheet);
				alter.setText("1");
			} else
				step.setText(currNote.getStep());

			XMLElement octave = new XMLElement("octave", musicSheet);
			octave.setText(currNote.getOctave());
			pitch.append(step, alter, octave);

			XMLElement duration = new XMLElement("duration", musicSheet);
			duration.setText(currNote.getDuration());
			XMLElement voice = new XMLElement("voice", musicSheet);
			voice.setText(currNote.getVoice());
			XMLElement type = new XMLElement("type", musicSheet);
			type.setText(currNote.getType());

			XMLElement notations = new XMLElement("notations", musicSheet);
			XMLElement technical = new XMLElement("technical", musicSheet);

			if (currNote.isStartHammer()) {
				XMLElement hammeron = new XMLElement("hammer-on", musicSheet);
				setNotationAttr(hammeron, currNote, "start", "H");
				technical.append(hammeron);
			} else if (currNote.isStopHammer()) {
				XMLElement hammeron = new XMLElement("hammer-on", musicSheet);
				setNotationAttr(hammeron, currNote, "stop", "");
				technical.append(hammeron);
			} else if (currNote.isStartPull()) {
				XMLElement pulloff = new XMLElement("pull-off", musicSheet);
				setNotationAttr(pulloff, currNote, "start", "P");
				technical.append(pulloff);
			} else if (currNote.isStopPull()) {
				XMLElement pulloff = new XMLElement("pull-off", musicSheet);
				setNotationAttr(pulloff, currNote, "stop", "");
				technical.append(pulloff);
			} else if (currNote.isStartChain()) {
				notations.append(slur(currNote, "start", "above"));
			} else if (currNote.isStopChain()) {
				notations.append(slur(currNote, "stop", "above"));
			} else if (currNote.isStartSlide()) {
				XMLElement slide = new XMLElement("slide", musicSheet);
				slide.setAttribute("line-type", "solid");
				setNotationAttr(slide, currNote, "start", "S");
				notations.append(slide);
			} else if (currNote.isStopSlide()) {
				XMLElement slide = new XMLElement("slide", musicSheet);
				slide.setAttribute("line-type", "solid");
				setNotationAttr(slide, currNote, "stop", "");
				notations.append(slide);
			} else if (currNote.isHarmonic()) {
				XMLElement harmonic = new XMLElement("harmonic", musicSheet);
				XMLElement artificial = new XMLElement("artificial", musicSheet);
				XMLElement basePitch = new XMLElement("basePitch", musicSheet);
				harmonic.append(artificial, basePitch);
				technical.append(harmonic);
			}

			XMLElement string = new XMLElement("string", musicSheet);
			string.setText(currNote.getString());
			XMLElement fret = new XMLElement("fret", musicSheet);
			fret.setText(currNote.getFret());
			technical.append(string, fret);
			notations.append(technical);
			note.append(pitch, duration, voice, type, notations);

			if (currNote.isRepeatedStart()) {
				XMLElement barline = new XMLElement("barline", musicSheet);
				barline.setAttribute("location", "left");
				XMLElement barStyle = new XMLElement("bar-style", musicSheet);
				barStyle.setText("heavy-light");
				XMLElement repeat = new XMLElement("repeat", musicSheet);
				repeat.setAttribute("direction", "forward");
				barline.append(barStyle, repeat);

				XMLElement direction = new XMLElement("direction", musicSheet);
				direction.setAttribute("placement", "above");
				XMLElement directionType = new XMLElement("direction-type", musicSheet);
				XMLElement words = new XMLElement("words", musicSheet);
				words.setAttribute("relative-x", "256.17");
				words.setAttribute("relative-y", "16.01");
				words.setText(String.format("Repeat %d times", currNote.getRepeatCount()));
				directionType.append(words);
				direction.append(directionType);

				measures.get(currMeasure).append(barline, direction);

			}

			measures.get(currMeasure).append(note);

			if (currNote.isRepeatedStop()) {
				XMLElement barline = new XMLElement("barline", musicSheet);
				barline.setAttribute("location", "right");
				XMLElement barStyle = new XMLElement("bar-style", musicSheet);
				barStyle.setText("light-heavy");
				XMLElement repeat = new XMLElement("repeat", musicSheet);
				repeat.setAttribute("direction", "backward");
				barline.append(barStyle, repeat);
				measures.get(currMeasure).append(barline);
			}

			if (currNote.isDoubleBar()) {
				XMLElement barline = new XMLElement("barline", musicSheet);
				barline.setAttribute("location", "right");
				XMLElement barStyle = new XMLElement("bar-style", musicSheet);
				barStyle.setText("light-heavy");
				barline.append(barStyle);
				measures.get(currMeasure).append(barline);
			}
		}
	}

	/**
	 * @param note      the note to add a slur to
	 * @param type      the type of slur
	 * @param placement the placement of the slur
	 * @return the slur XML element
	 */
	private XMLElement slur(GuitarNote note, String type, String placement) {
		XMLElement slur = new XMLElement("slur", musicSheet);
		slur.setAttribute("number", Integer.toString(note.getLineNum()));
		slur.setAttribute("type", type);
		slur.setAttribute("placement", placement);
		return slur;
	}

	/**
	 * @param item the item to set attributes for
	 * @param note the note to add the item to
	 * @param type the type of item
	 * @param text the text content
	 */
	private void setNotationAttr(XMLElement item, GuitarNote note, String type, String text) {
		item.setAttribute("number", Integer.toString(note.getLineNum()));
		item.setAttribute("type", type);
		item.setAttribute("default-y", "-11");
		item.setText(text);
	}

	/**
	 * Set the default values that every music sheet has.
	 * 
	 * @param root the root element of the music sheet
	 */
	private void setDefaults(XMLElement root) {
		root.setAttribute("version", "3.1");
		musicSheet.append(root);
		XMLElement work = new XMLElement("work", musicSheet);
		XMLElement workTitle = new XMLElement("work-title", musicSheet);
		work.append(workTitle);
		workTitle.setText(metadata.getTitle());
		XMLElement partList = new XMLElement("part-list", musicSheet);
		XMLElement scorePart = new XMLElement("score-part", musicSheet);
		scorePart.setAttribute("id", "P1");
		XMLElement partName = new XMLElement("part-name", musicSheet);
		partName.setText(instrument.getName());

		switch (instrument) {
		case GUITAR:
			scorePart.append(partName);
			break;
		case BASS:
			scorePart.append(partName);
			XMLElement scoreInstrumentB = new XMLElement("score-instrument", musicSheet);
			scoreInstrumentB.setAttribute("id", String.format("P1-I%d", 1));
			XMLElement instrumentNameB = new XMLElement("instrument-name", musicSheet);
			instrumentNameB.setText("Bass Guitar (Tablature)");
			scoreInstrumentB.append(instrumentNameB);
			scorePart.append(scoreInstrumentB);
			
			XMLElement midiDevice = new XMLElement("midi-device", musicSheet);
			midiDevice.setAttribute("id", String.format("P1-I%d", 1));
			scorePart.append(midiDevice);
			XMLElement midiInstrument = midiInstrument(String.format("P1-I%d", 1), "35", "35", "", "78.7402", "0");
			scorePart.append(midiInstrument);
			
			break;
		case DRUM:
			int p = 0;
			for (int i = 36; i <= 65; i++) {
				if (i == 40)
					continue;
				XMLElement scoreInstrumentD = new XMLElement("score-instrument-name", musicSheet);
				scoreInstrumentD.setAttribute("id", String.format("P1-I%d", i));
				XMLElement instrumentNameD = new XMLElement("instrument-name", musicSheet);
				instrumentNameD.setText(Instrument.drumSet[p++][1]);
				scoreInstrumentD.append(instrumentNameD);
				scorePart.append(scoreInstrumentD);
			}
			break;
		default:
			break;
		}
		partList.append(scorePart);
		root.append(work, partList);
	}

	/**
	 * Set the default staff attributes if the Guitar is in standard tuning.
	 * 
	 * @param staff   the first staff of the score
	 * @param measure first measure to append the staff attributes to
	 */
	private void setGuitarBassStaffAttributes(GuitarStaff staff, XMLElement measure) {
		XMLElement attributes = new XMLElement("attributes", musicSheet);
		XMLElement divisions = new XMLElement("divisions", musicSheet);
		divisions.setText("2");

		XMLElement key = new XMLElement("key", musicSheet);
		XMLElement fifths = new XMLElement("fifths", musicSheet);
		fifths.setText("0");
		key.append(fifths);

		XMLElement time = new XMLElement("time", musicSheet);
		XMLElement beats = new XMLElement("beats", musicSheet);
		beats.setText("4");
		XMLElement beatType = new XMLElement("beat-type", musicSheet);
		beatType.setText(staff.getBeatType());
		time.append(beats, beatType);

		XMLElement clef = new XMLElement("clef", musicSheet);
		XMLElement sign = new XMLElement("sign", musicSheet);
		sign.setText("TAB");
		XMLElement line = new XMLElement("line", musicSheet);
		line.setText("5");
		clef.append(sign, line);

		XMLElement staffDetails = new XMLElement("staff-details", musicSheet);
		XMLElement staffLines = new XMLElement("staff-lines", musicSheet);
		staffLines.setText(staff.lineCount());
		staffDetails.append(staffLines);

		for (GuitarString s : staff.getLines()) {
			System.out.println("String: " + s.toString());
			XMLElement staffTuning = new XMLElement("staff-tuning", musicSheet);
			staffTuning.setAttribute("line", String.valueOf(s.getStringNum()));
			XMLElement tuningStep = new XMLElement("tuning-step", musicSheet);
			tuningStep.setText(s.getTune());
			XMLElement tuningOctave = new XMLElement("tuning-octave", musicSheet);
			tuningOctave.setText(s.getOctave());
			staffTuning.append(tuningStep, tuningOctave);
			staffDetails.append(staffTuning);
		}
		attributes.append(divisions, key, time, clef, staffDetails);
		measure.append(attributes);
	}

	/**
	 * Set the default staff attributes if the Guitar is in standard tuning.
	 * 
	 * @param staff   the first staff of the score
	 * @param measure first measure to append the staff attributes to
	 */
	private void setDrumStaffAttributes(DrumStaff staff, XMLElement measure) {
		XMLElement attributes = new XMLElement("attributes", musicSheet);
		XMLElement divisions = new XMLElement("divisions", musicSheet);
		divisions.setText("4");

		XMLElement key = new XMLElement("key", musicSheet);
		XMLElement fifths = new XMLElement("fifths", musicSheet);
		fifths.setText("0");
		key.append(fifths);

		XMLElement time = new XMLElement("time", musicSheet);
		XMLElement beats = new XMLElement("beats", musicSheet);
		beats.setText(staff.getBeats());
		XMLElement beatType = new XMLElement("beat-type", musicSheet);
		beatType.setText(staff.getBeatType());
		time.append(beats, beatType);

		XMLElement clef = new XMLElement("clef", musicSheet);
		XMLElement sign = new XMLElement("sign", musicSheet);
		sign.setText("percussion");
		XMLElement line = new XMLElement("line", musicSheet);
		line.setText("2");
		clef.append(sign, line);
		attributes.append(divisions, time, clef);
		measure.append(attributes);
	}

	private XMLElement midiInstrument(String id, String channel, String program, String unPitched, String volume,
			String pan) {
		XMLElement midiInstrument = new XMLElement("midi-instrument", musicSheet);
		midiInstrument.setAttribute("id", id);
		XMLElement midiChannel = new XMLElement("midid-channel", musicSheet);
		midiChannel.setText(channel);
		XMLElement midiProgram = new XMLElement("midi-program", musicSheet);
		midiProgram.setText(program);
		XMLElement unPitchedA = null;
		
		if (unPitched == null || unPitched.length() == 0) {
			unPitchedA = new XMLElement("midi-unpitched", musicSheet);
			unPitchedA.setText(unPitched);
		}
		XMLElement volumeA = new XMLElement("volume", musicSheet);
		volumeA.setText(volume);
		XMLElement panA = new XMLElement("pan", musicSheet);
		midiInstrument.append(midiChannel, midiProgram, unPitchedA, volumeA, panA);
		return midiInstrument;
	}
	/**
	 * Generate XML from score for selected instrument, Drum.
	 * 
	 */
	private void generateDrum() {
		XMLElement root = new XMLElement("score-partwise", musicSheet);
		setDefaults(root);
		XMLElement part1 = new XMLElement("part", musicSheet);
		part1.setAttribute("id", "P1");
		root.append(part1);

		int measureCount = sheet.numberOfMeasures();
		ArrayList<XMLElement> measures = new ArrayList<>();
		measures.ensureCapacity(measureCount);

		for (int i = 0; i < measureCount; i++) {
			XMLElement measure = new XMLElement("measure", musicSheet);
			measure.setAttribute("number", Integer.toString(i + 1));
			measures.add(measure);
		}

		DrumStaff staff = (DrumStaff) sheet.getStaffs().get(0);
		// if not selected by user set to default
		staff.setBeats("4");
		staff.setBeatType("4");
		setDrumStaffAttributes(staff, measures.get(0));

	}
}
