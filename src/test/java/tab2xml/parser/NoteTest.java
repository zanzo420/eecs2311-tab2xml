package tab2xml.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;

import tab2xml.parser.Lexer.InvalidTokenException;

class NoteTest {
	
	/**
	 * @author Edward and Sayed this tests the getNoteType method in the Note class
	 */
	@Test
	void getNoteTypeTester() {
		Note note = new Note(NoteType.A);
		NoteType expected = NoteType.A;
		assertEquals(expected, note.getNoteType());
	}

	/**
	 * @author Edward and Sayed this tests the getName method for A  in the
	 *         Note class
	 * @throws InvalidTokenException 
	 */
	@Test
	void getNameTester1() throws InvalidTokenException {
		Note note = new Note(NoteType.A, "A");
		String expected = "A";
		assertEquals(expected, note.getData());
		assertEquals(expected, note.getNoteType().getValue());
	}
	
	/**
	 * @author Edward and Sayed this tests the getName method for A sharp in the
	 *         Note class
	 * @throws InvalidTokenException 
	 */
	@Test
	void getNameTester2() throws InvalidTokenException {
		Note note = new Note(NoteType.AS, "A#");
		String expected = "A#";
		assertEquals(expected, note.getData());
		assertEquals(expected, note.getNoteType().getValue());
	}
	
	/**
	 * @author Edward and Sayed this tests the getName method for B in the
	 *         Note class
	 * @throws InvalidTokenException 
	 */
	@Test
	void getNameTester3() throws InvalidTokenException {
		Note note = new Note(NoteType.B, "B");
		String expected = "B";
		assertEquals(expected, note.getData());
		assertEquals(expected, note.getNoteType().getValue());
	}
	
	/**
	 * @author Edward and Sayed this tests the getName method for C in the
	 *         Note class
	 * @throws InvalidTokenException 
	 */
	@Test
	void getNameTester4() throws InvalidTokenException {
		Note note = new Note(NoteType.C, "C");
		String expected = "C";
		assertEquals(expected, note.getData());
		assertEquals(expected, note.getNoteType().getValue());
	}
	
	/**
	 * @author Edward and Sayed this tests the getName method for C Sharp in the
	 *         Note class
	 * @throws InvalidTokenException 
	 */
	@Test
	void getNameTester5() throws InvalidTokenException {
		Note note = new Note(NoteType.CS, "C#");
		String expected = "C#";
		assertEquals(expected, note.getData());
		assertEquals(expected, note.getNoteType().getValue());
	}
	
	/**
	 * @author Edward and Sayed this tests the getName method for D in the
	 *         Note class
	 * @throws InvalidTokenException 
	 */
	@Test
	void getNameTester6() throws InvalidTokenException {
		Note note = new Note(NoteType.D, "D");
		String expected = "D";
		assertEquals(expected, note.getData());
		assertEquals(expected, note.getNoteType().getValue());
	}
	
	/**
	 * @author Edward and Sayed this tests the getName method for D Sharp in the
	 *         Note class
	 * @throws InvalidTokenException 
	 */
	@Test
	void getNameTester7() throws InvalidTokenException {
		Note note = new Note(NoteType.DS, "D#");
		String expected = "D#";
		assertEquals(expected, note.getData());
		assertEquals(expected, note.getNoteType().getValue());
	}
	
	/**
	 * @author Edward and Sayed this tests the getName method for E in the
	 *         Note class
	 * @throws InvalidTokenException 
	 */
	@Test
	void getNameTester8() throws InvalidTokenException {
		Note note = new Note(NoteType.E, "E");
		String expected = "E";
		assertEquals(expected, note.getData());
		assertEquals(expected, note.getNoteType().getValue());
	}
	
	/**
	 * @author Edward and Sayed this tests the getName method for F in the
	 *         Note class
	 * @throws InvalidTokenException 
	 */
	@Test
	void getNameTester9() throws InvalidTokenException {
		Note note = new Note(NoteType.F, "F");
		String expected = "F";
		assertEquals(expected, note.getData());
		assertEquals(expected, note.getNoteType().getValue());
	}
	
	/**
	 * @author Edward and Sayed this tests the getName method for F Sharp in the
	 *         Note class
	 * @throws InvalidTokenException 
	 */
	@Test
	void getNameTester10() throws InvalidTokenException {
		Note note = new Note(NoteType.FS, "F#");
		String expected = "F#";
		assertEquals(expected, note.getData());
		assertEquals(expected, note.getNoteType().getValue());
	}
	
	/**
	 * @author Edward and Sayed this tests the getName method for G in the
	 *         Note class
	 * @throws InvalidTokenException 
	 */
	@Test
	void getNameTester11() throws InvalidTokenException {
		Note note = new Note(NoteType.G, "G");
		String expected = "G";
		assertEquals(expected, note.getData());
		assertEquals(expected, note.getNoteType().getValue());
	}
	
	/**
	 * @author Edward and Sayed this tests the getName method for G Sharp in the
	 *         Note class
	 * @throws InvalidTokenException 
	 */
	@Test
	void getNameTester12() throws InvalidTokenException {
		Note note = new Note(NoteType.GS, "G#");
		String expected = "G#";
		assertEquals(expected, note.getData());
		assertEquals(expected, note.getNoteType().getValue());
	}
	
	
	/**
	 * @author Edward and Sayed this tests the getIndex method for note B in the
	 *         Note class
	 */
	@Test
	void getIndexTester() {
		Note note = new Note(NoteType.B);
		int expected = 2;
		assertEquals(expected, note.getIndex());
	}

	/**
	 * @author Edward and Sayed this tests the getIndex method for note A in the
	 *         Note class
	 */
	@Test
	void getIndexTester2() {
		Note note = new Note(NoteType.A);
		int expected = 0;
		assertEquals(expected, note.getIndex());
	}

	/**
	 * @author Edward and Sayed this tests the Tonote method in the Note class
	 * @throws InvalidTokenException
	 */
	@Test
	void testToNote() throws InvalidTokenException {
		NoteType expected = NoteType.A;
		assertEquals(expected, Note.toNote("A0").getNoteType());
		try {
			assertEquals(expected, Note.toNote("A0").getNoteType());
		} catch (InvalidTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("InvalidTokenException occured.");
		}
	}

}
