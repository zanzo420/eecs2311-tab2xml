package tab2xml.model.guitar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HammerOn extends StringItem {
	private static final long serialVersionUID = 1578840013025953896L;
	private Note start;
	private Note stop;

	public HammerOn(Note start, Note stop) {
		this.start = start;
		this.stop = stop;
	}

	public Note getStart() {
		return start;
	}

	public Note getStop() {
		return stop;
	}

	public Collection<? extends StringItem> getNotes() {
		List<StringItem> notes = new ArrayList<>();
		notes.add((StringItem) StringItem.deepClone(start));
		notes.add((StringItem) StringItem.deepClone(stop));
		return notes;
	}

	@Override
	public double getPosition() {
		return start.getPosition();
	}

	@Override
	public int getStringNum() {
		return start.getStringNum();
	}

	@Override
	public int getNoteCount() {
		return getNotes().size();
	}

	@Override
	public String toString() {
		if (start == null)
			return "";
		StringBuilder sb = new StringBuilder();
		sb.append(start.getStep());
		sb.append(" ");
		sb.append(stop.getStep());
		return sb.toString();
	}
}