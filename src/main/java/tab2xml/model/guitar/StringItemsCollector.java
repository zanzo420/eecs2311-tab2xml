package tab2xml.model.guitar;

import java.util.List;

public class StringItemsCollector extends StringItem {
	private static final long serialVersionUID = -5896057496942602721L;
	private List<StringItem> stringItems;

	public StringItemsCollector(List<StringItem> stringItems) {
		this.stringItems = stringItems;
	}

	public List<StringItem> getStringItems() {
		return stringItems;
	}

	public void add(StringItem item) {
		if (item != null)
			stringItems.add(item);
	}

	@Override
	public double getPosition() {
		return -1;
	}

	@Override
	public int getStringNum() {
		return 0;
	}
	
	@Override
	public int getNoteCount() {
		return 0;
	}
}