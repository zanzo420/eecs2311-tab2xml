package tab2xml.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Font;
import java.awt.event.FocusListener;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Testing the {@link PromptingTextArea} class.
 *
 * @since 2021-02-05
 */
class PromptingTextAreaTest {
	
	private static void simulateGainingFocus(PromptingTextArea area) {
		final FocusListener[] array = area.getFocusListeners();
		array[array.length - 1].focusGained(null);
	}
	
	private static void simulateLosingFocus(PromptingTextArea area) {
		final FocusListener[] array = area.getFocusListeners();
		array[array.length - 1].focusLost(null);
	}
	
	/**
	 * Ensure that the PromptingTextArea sets its colour and font properly (using
	 * custom fonts).
	 * 
	 * @since 2021-02-05
	 */
	@Test
	final void testPromptColourFont() {
		final String PROMPT_TEXT = "PROMPT";
		final Font regularFont = new Font(Font.MONOSPACED, Font.BOLD, 15);
		final Font promptFont = new Font(Font.MONOSPACED, Font.BOLD, 18);
		
		final PromptingTextArea area = new PromptingTextArea(PROMPT_TEXT);
		area.setRegularFont(regularFont);
		area.setPromptFont(promptFont);
		
		area.setPrompting(false);
		assertEquals(PromptingTextArea.REGULAR_TEXT_COLOR, area.getForeground());
		assertEquals(regularFont, area.getFont());
		
		area.setPrompting(true);
		assertEquals(PromptingTextArea.PROMPT_TEXT_COLOR, area.getForeground());
		assertEquals(promptFont, area.getFont());
	}
	
	/**
	 * Ensures the prompt text displays properly & reacts to focus
	 * 
	 * @since 2021-02-05
	 */
	@Test
	final void testPromptFocusChanges() {
		final String PROMPT_TEXT = "PROMPT";
		final PromptingTextArea area = new PromptingTextArea(PROMPT_TEXT);
		
		simulateLosingFocus(area);
		
		assertEquals(PROMPT_TEXT, area.getText());
		
		simulateGainingFocus(area);
		
		assertEquals("", area.getText());
	}
	
	/**
	 * Ensures the prompt text reacts correctly to typing, setText() and
	 * setPromptText().
	 * 
	 * @since 2021-02-05
	 */
	@Test
	final void testPromptTyping() {
		final String PROMPT_TEXT = "PROMPT";
		final String PROMPT_TEXT_2 = "PROMPT 2";
		final PromptingTextArea area = new PromptingTextArea(PROMPT_TEXT);
		
		simulateLosingFocus(area);
		
		area.setText("hi");
		
		assertEquals("hi", area.getText());
		assertFalse(area.isPrompting());
		
		simulateGainingFocus(area);
		area.setText(""); // simulate deleting everything
		
		assertEquals("", area.getText());
		assertFalse(area.isPrompting());
		
		simulateLosingFocus(area);
		
		assertEquals(PROMPT_TEXT, area.getText());
		assertTrue(area.isPrompting());
		
		area.setPromptText(PROMPT_TEXT_2);
		assertEquals(PROMPT_TEXT_2, area.getPromptText());
		assertEquals(PROMPT_TEXT_2, area.getText());
		assertTrue(area.isPrompting());
	}
	
	/**
	 * Tests that the prompt fonts are set correctly. The argument specifies
	 * whether the prompt is enabled or disabled when the font is set - as the
	 * setFont() method behaves differently depending on this state.
	 *
	 * @since 2021-02-22
	 */
	@ParameterizedTest
	@ValueSource(booleans = { true, false })
	final void testSetFont(boolean promptEnabledAtStart) {
		final String PROMPT_TEXT = "PROMPT";
		final Font regularFont = new Font(Font.MONOSPACED, Font.PLAIN, 12);
		final Font promptFont = new Font(Font.MONOSPACED, Font.ITALIC, 12);
		
		final PromptingTextArea area = new PromptingTextArea(PROMPT_TEXT);
		
		// test that the font is set correctly
		area.setPrompting(promptEnabledAtStart);
		area.setFont(promptEnabledAtStart ? promptFont : regularFont);
		
		assertEquals(regularFont, area.getRegularFont());
		assertEquals(promptFont, area.getPromptFont());
		
		// test that the correct font is visible
		assertEquals(promptEnabledAtStart ? promptFont : regularFont,
				area.getFont());
		
		// ensure fonts change correctly when prompt state is changed
		area.setPrompting(!promptEnabledAtStart);
		assertEquals(regularFont, area.getRegularFont());
		assertEquals(promptFont, area.getPromptFont());
		assertEquals(!promptEnabledAtStart ? promptFont : regularFont,
				area.getFont());
	}
}