package tab2xml.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

/**
 * A view with a single text box, handling both input and output.
 *
 * @since 2021-01-18
 */
public final class SingleEntryView implements View {
	/**
	 * Creates a {@code GridBagConstraints} object.
	 *
	 * @since 2021-01-18
	 */
	private static GridBagConstraints gridBag(int x, int y) {
		return gridBag(x, y, 1, 1);
	}
	
	/**
	 * Creates a {@code GridBagConstraints} object.
	 *
	 * @since 2021-01-18
	 */
	private static GridBagConstraints gridBag(int x, int y, int width,
			int height) {
		final GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = width;
		gbc.gridheight = height;
		return gbc;
	}
	
	/**
	 * Creates and opens a View.
	 *
	 * @since 2021-01-18
	 */
	public static void main(String[] args) {
		final View view = new SingleEntryView();
		view.init();
	}
	
	/** The frame that the GUI is displayed on. */
	private final JFrame frame;
	
	/** The text box that handles both input and output. */
	private final JTextArea textBox;
	
	/**
	 * Creates the {@code SingleEntryView}.
	 * 
	 * @since 2021-01-18
	 */
	public SingleEntryView() {
		// create frame
		this.frame = new JFrame("Tab2XML");
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// create components
		final JPanel masterPanel = new JPanel();
		masterPanel.setLayout(new GridBagLayout());
		this.frame.add(masterPanel);
		
		// buttons
		final JButton loadFileButton = new JButton("Load From File");
		masterPanel.add(loadFileButton, gridBag(0, 1));
		
		final JButton convertButton = new JButton("Convert");
		masterPanel.add(convertButton, gridBag(1, 1));
		
		final JButton saveFileButton = new JButton("Save to File");
		masterPanel.add(saveFileButton, gridBag(2, 1));
		
		// text box
		this.textBox = new JTextArea(12, 80);
		this.textBox.setBorder(new LineBorder(Color.BLACK));
		masterPanel.add(new JScrollPane(this.textBox), gridBag(0, 0, 3, 1));
		
		// give everything the correct size
		this.frame.pack();
	}
	
	@Override
	public String getInputText() {
		return this.textBox.getText();
	}
	
	@Override
	public void init() {
		this.frame.setVisible(true);
	}
	
	@Override
	public void setOutputText(String text) {
		this.textBox.setText(text);
	}
	
}