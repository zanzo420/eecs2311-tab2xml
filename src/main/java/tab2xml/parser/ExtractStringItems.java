package tab2xml.parser;

import java.util.List;
import java.util.regex.Pattern;
import java.util.ArrayList;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import tab2xml.antlr.GuitarTabBaseVisitor;
import tab2xml.antlr.GuitarTabParser.FretContext;
import tab2xml.antlr.GuitarTabParser.HammerPullContext;
import tab2xml.antlr.GuitarTabParser.HammeronContext;
import tab2xml.antlr.GuitarTabParser.HarmonicContext;
import tab2xml.antlr.GuitarTabParser.PulloffContext;
import tab2xml.antlr.GuitarTabParser.SlideContext;
import tab2xml.antlr.GuitarTabParser.StringContext;
import tab2xml.antlr.GuitarTabParser.StringItemsContext;
import tab2xml.antlr.GuitarTabParser.TuneContext;
import tab2xml.model.StringItem;
import tab2xml.model.StringItemsCollector;
import tab2xml.model.guitar.Bar;
import tab2xml.model.guitar.GuitarString;
import tab2xml.model.guitar.HammerOn;
import tab2xml.model.guitar.HammerPull;
import tab2xml.model.guitar.Harmonic;
import tab2xml.model.guitar.Note;
import tab2xml.model.guitar.PullOff;
import tab2xml.model.guitar.Slide;
import tab2xml.model.guitar.Tune;

/**
 * Extract string contents from parse tree.
 * 
 * @author amir
 *
 */
public class ExtractStringItems extends GuitarTabBaseVisitor<StringItem> {
	private GuitarString s;
	private final static double EPSILON = 0.001;

	/**
	 * Construct a sample parse tree visitor from a specified {@code GuitarString}
	 * and {@code StringContext}.
	 * 
	 * @param string the guitar string model
	 * @param sc     the corresponding string context
	 */
	public ExtractStringItems(GuitarString string, StringContext sc) {
		this.s = string;
		List<StringItem> visited = new ArrayList<>();

		Tune tune = (Tune) visit(sc.getChild(0));
		s.add(tune);
		visited.add(visit(sc.getChild(1)));

		for (StringItem item : visited) {
			if (item.getClass() == Tune.class)
				string.add(item);
			else if (item.getClass() == StringItemsCollector.class) {
				StringItemsCollector coll = (StringItemsCollector) item;
				string.addAll(coll.getStringItems());
			}
		}
	}

	@Override
	public StringItem visitTune(TuneContext ctx) {
		String value = ctx.getChild(0).getText();
		Tune tune;
		if (!value.equals("|"))
			tune = new Tune(value);
		else
			tune = new Tune();
		tune.setStringNum(s.getStringNum());
		return tune;
	}

	@Override
	public StringItem visitStringItems(StringItemsContext ctx) {
		StringItemsCollector coll = new StringItemsCollector(new ArrayList<StringItem>());
		int numMeasures = (int) ctx.children.stream().filter(c -> c.getClass() == TerminalNodeImpl.class
				&& Pattern.matches("\\*?(\\||\\d+)\\||\\|\\*?", c.getText())).count();
		s.setNumMeasures(numMeasures);
		ctx.children.stream().forEach(c -> coll.add(visit(c)));
		return coll;
	}

	@Override
	public StringItem visitSlide(SlideContext ctx) {
		int index = 0;
		boolean isGrace = ctx.getChild(0).getClass() == TerminalNodeImpl.class;

		if (isGrace)
			index++;

		Note start = (Note) visit(ctx.getChild(index));
		start.setStartSlide(true);
		Note stop = (Note) visit(ctx.getChild(index + 2));
		stop.setStopSlide(true);
		Slide slide = new Slide(start, stop);

		if (isGrace) {
			start.setGrace(true);
			stop.setGrace(true);
			stop.setPosition(start.getPosition() + EPSILON);
		}

		return slide;
	}

	@Override
	public StringItem visitPulloff(PulloffContext ctx) {
		int index = 0;
		boolean isGrace = ctx.getChild(0).getClass() == TerminalNodeImpl.class;

		if (isGrace)
			index++;

		Note start = (Note) visit(ctx.getChild(index));
		start.setStartPull(true);
		Note stop = (Note) visit(ctx.getChild(index + 2));
		stop.setStopPull(true);
		PullOff pullOff = new PullOff(start, stop);

		if (isGrace) {
			start.setGrace(true);
			stop.setGrace(true);
			stop.setPosition(start.getPosition() + EPSILON);
		}

		return pullOff;
	}

	@Override
	public StringItem visitHammeron(HammeronContext ctx) {
		int index = 0;
		boolean isGrace = ctx.getChild(0).getClass() == TerminalNodeImpl.class;

		if (isGrace)
			index++;

		Note start = (Note) visit(ctx.getChild(index));
		start.setStartHammer(true);
		Note stop = (Note) visit(ctx.getChild(index + 2));
		stop.setStopHammer(true);
		HammerOn hammerOn = new HammerOn(start, stop);

		if (isGrace) {
			start.setGrace(true);
			stop.setGrace(true);
			stop.setPosition(start.getPosition() + EPSILON);
		}

		return hammerOn;
	}

	@Override
	public StringItem visitHammerPull(HammerPullContext ctx) {
		List<Note> notes = new ArrayList<>();
		boolean isGrace = ctx.getChild(0).getClass() == TerminalNodeImpl.class;

		for (int i = (isGrace ? 1 : 0); i < ctx.getChildCount(); i++) {
			ParseTree child = ctx.getChild(i);
			if (child.getClass() != TerminalNodeImpl.class) {
				Note note = (Note) visit(child);
				notes.add(note);
			}
		}

		Note start = notes.get(0);
		start.setStartChain(true);
		Note stop = notes.get(notes.size() - 1);
		stop.setStopChain(true);
		List<Note> subList = notes.subList(1, notes.size() - 1);
		List<Note> middle = new ArrayList<>(subList);
		start.setMiddle(middle);

		if (isGrace) {
			middle.forEach(n -> n.setPosition(start.getPosition() + EPSILON));
			stop.setPosition(start.getPosition() + EPSILON);
			notes.forEach(n -> n.setGrace(true));
		}
		HammerPull hammerPull = new HammerPull(start, middle, stop);
		return hammerPull;
	}

	@Override
	public StringItem visitHarmonic(HarmonicContext ctx) {
		Note note = (Note) visit(ctx.getChild(1));
		note.setHarmonic(true);
		Harmonic harmonic = new Harmonic(note);
		return harmonic;
	}

	@Override
	public StringItem visitFret(FretContext ctx) {
		Token token = ctx.FRET_NUM().getSymbol();
		int length = token.getText().length();
		int column = token.getCharPositionInLine() + length;
		String value = ctx.getChild(0).getText();
		Note note = new Note(s.getTune(), value);
		note.setPosition(column);
		note.setString(Integer.toString(s.getStringNum()));
		note.setOctave(s.getOctave());
		return note;
	}

	@Override
	public StringItem visitTerminal(TerminalNode node) {
		Token token = node.getSymbol();

		if (token.getText().equals("-"))
			return null;

		String value = token.getText();
		String start = value.substring(0, value.indexOf("|"));
		int column = token.getCharPositionInLine() + value.length();

		Bar bar = new Bar();
		bar.setStringNum(s.getStringNum());
		bar.setPosition(column);
		bar.setTune(s.getTune());

		if (start.equals("*")) {
			bar.setDoubleBar(true);
			bar.setRepeat(true);
			bar.setStop(true);
		}

		if (value.charAt(value.length() - 1) == '*') {
			bar.setDoubleBar(true);
			bar.setRepeat(true);
			bar.setStart(true);
		}

		if (isNumeric(start)) {
			bar.setRepeatCount(Integer.parseInt(start));
			bar.setDoubleBar(true);
			bar.setRepeat(true);
			bar.setStop(true);
		}

		if (value.equals("||"))
			bar.setDoubleBar(true);

		return bar;
	}

	private static boolean isNumeric(String s) {
		return Pattern.matches("\\d+", s);
	}
}
