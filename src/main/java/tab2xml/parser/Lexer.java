
package tab2xml.parser;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tab2xml.exceptions.InvalidTokenException;
import tab2xml.model.GToken;
import tab2xml.model.TokenType;

/**
 * A lexer for tokenizing ASCII tablature for a specified instrument.
 * 
 * @author amir
 */
public class Lexer {
	private String input;
	private Instrument instrument;

	/**
	 * Construct a lexer object for an input string and instrument.
	 * 
	 * @param input      string representing ASCII tablature
	 * @param instrument the type of instrument for the tablature
	 */
	public Lexer(String input, Instrument instrument) {
		this.input = input;
		this.instrument = instrument;
	}

	/**
	 * Delegate tokenization based on instrument.
	 *
	 * @return a list of list of tokens representing the ASCII tablature
	 * @throws InvalidTokenException 
	 */
	public ArrayList<ArrayList<GToken>> tokenize() throws InvalidTokenException {
		switch (this.instrument) {
		case GUITAR:
			return tokenizeGuitar(input);
		case DRUM:
			return tokenizeDrum(input);
		case BASS:
			return tokenizeBass(input);
		default:
			throw new UnsupportedOperationException("This instrument is not supported.");
		}
	}

	/**
	 * This method will extract tokens from the guitar tablature.
	 * 
	 * @param input guitar ASCII tablature
	 * @return a list of list of tokens representing the guitar tablature
	 */
	public static ArrayList<ArrayList<GToken>> tokenizeGuitar(String input) throws InvalidTokenException {
		ArrayList<ArrayList<GToken>> tokens = new ArrayList<>();

		if (input == null || input.length() == 0 || input.equals("Enter text tab or load it from a file..."))
			return tokens;

		try (Scanner sc = new Scanner(input)) {
			StringBuffer sb = new StringBuffer();

			// g-0() | g-1() | g-2() | ... g-n()
			// named capturing group: (name + pattern of a token)
			for (TokenType type : TokenType.values())
				sb.append(String.format("|(?<%s>%s)", type.name(), type.pattern));

			Pattern p = Pattern.compile(new String(sb.substring(1)));

			while (sc.hasNextLine()) {
				String currentLine = sc.nextLine();
				Matcher m = p.matcher(currentLine);

				ArrayList<GToken> newTokens = new ArrayList<>();

				while (m.find()) {
					for (TokenType type : TokenType.values()) {
						String name = type.name();
						if (m.group(name) != null) {
							newTokens.add(new GToken(type, m.group(name)));
						}
					}
				}
				if (!newTokens.isEmpty())
					tokens.add(newTokens);
			}
		}
		return tokens;
	}

	/**
	 * This method will extract tokens from the drum tablature.
	 * 
	 * @param input drum ASCII tablature
	 * @return a list of list of tokens representing the drum tablature
	 */
	public static ArrayList<ArrayList<GToken>> tokenizeDrum(String input) {
		ArrayList<ArrayList<GToken>> tokens = new ArrayList<>();
		//TO-DO
		return tokens;
	}

	/**
	 * This method will extract tokens from the bass tablature.
	 * 
	 * @param input bass ASCII tablature
	 * @return a list of list of tokens representing the bass tablature
	 */
	public static ArrayList<ArrayList<GToken>> tokenizeBass(String input) {
		ArrayList<ArrayList<GToken>> tokens = new ArrayList<>();
		//TO-DO
		return tokens;
	}
}