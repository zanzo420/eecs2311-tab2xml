// Generated from tab2xml\antlr\DrumTab.g4 by ANTLR 4.9.2

	package tab2xml.antlr; 

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DrumTabLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		CYMBALS=1, DRUMS=2, TYPE=3, BAR=4, HYPHEN=5, SPACE=6, NEWLINE=7, MULTI_COMMENT=8, 
		LINE_COMMENT=9;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"CYMBALS", "DRUMS", "TYPE", "BAR", "HYPHEN", "SPACE", "NEWLINE", "MULTI_COMMENT", 
			"LINE_COMMENT"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, "'|'", "'-'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "CYMBALS", "DRUMS", "TYPE", "BAR", "HYPHEN", "SPACE", "NEWLINE", 
			"MULTI_COMMENT", "LINE_COMMENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public DrumTabLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "DrumTab.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\13w\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\3\2\3\2"+
		"\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5"+
		"\4L\n\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\7\bU\n\b\f\b\16\bX\13\b\3\b\5\b[\n"+
		"\b\3\b\3\b\3\t\3\t\3\t\3\t\7\tc\n\t\f\t\16\tf\13\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\n\3\n\3\n\3\n\7\nq\n\n\f\n\16\nt\13\n\3\n\3\n\3d\2\13\3\3\5\4\7\5\t"+
		"\6\13\7\r\b\17\t\21\n\23\13\3\2\6\b\2%%ZZdeqruuzz\t\2BBDDQQddffhiqq\4"+
		"\2\13\13\"\"\4\2\f\f\17\17\2\u0092\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2"+
		"\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3"+
		"\2\2\2\3\25\3\2\2\2\5\27\3\2\2\2\7K\3\2\2\2\tM\3\2\2\2\13O\3\2\2\2\rQ"+
		"\3\2\2\2\17V\3\2\2\2\21^\3\2\2\2\23l\3\2\2\2\25\26\t\2\2\2\26\4\3\2\2"+
		"\2\27\30\t\3\2\2\30\6\3\2\2\2\31\32\7D\2\2\32L\7F\2\2\33\34\7D\2\2\34"+
		"L\7f\2\2\35\36\7U\2\2\36L\7U\2\2\37 \7U\2\2 L\7F\2\2!\"\7G\2\2\"L\7U\2"+
		"\2#$\7V\2\2$L\7\63\2\2%&\7E\2\2&L\7J\2\2\'(\7V\2\2(L\7\64\2\2)*\7R\2\2"+
		"*L\7J\2\2+,\7N\2\2,L\7V\2\2-.\7J\2\2.L\7J\2\2/\60\7N\2\2\60L\7O\2\2\61"+
		"\62\7O\2\2\62L\7V\2\2\63\64\7E\2\2\64L\7E\2\2\65\66\7J\2\2\66L\7V\2\2"+
		"\678\7T\2\28L\7F\2\29:\7E\2\2:L\7j\2\2;<\7T\2\2<L\7D\2\2=>\7V\2\2>L\7"+
		"C\2\2?@\7U\2\2@L\7E\2\2AB\7E\2\2BL\7D\2\2CD\7E\2\2DL\7e\2\2EF\7T\2\2F"+
		"L\7f\2\2GH\7J\2\2HL\7E\2\2IJ\7N\2\2JL\7E\2\2K\31\3\2\2\2K\33\3\2\2\2K"+
		"\35\3\2\2\2K\37\3\2\2\2K!\3\2\2\2K#\3\2\2\2K%\3\2\2\2K\'\3\2\2\2K)\3\2"+
		"\2\2K+\3\2\2\2K-\3\2\2\2K/\3\2\2\2K\61\3\2\2\2K\63\3\2\2\2K\65\3\2\2\2"+
		"K\67\3\2\2\2K9\3\2\2\2K;\3\2\2\2K=\3\2\2\2K?\3\2\2\2KA\3\2\2\2KC\3\2\2"+
		"\2KE\3\2\2\2KG\3\2\2\2KI\3\2\2\2L\b\3\2\2\2MN\7~\2\2N\n\3\2\2\2OP\7/\2"+
		"\2P\f\3\2\2\2QR\t\4\2\2R\16\3\2\2\2SU\5\r\7\2TS\3\2\2\2UX\3\2\2\2VT\3"+
		"\2\2\2VW\3\2\2\2WZ\3\2\2\2XV\3\2\2\2Y[\7\17\2\2ZY\3\2\2\2Z[\3\2\2\2[\\"+
		"\3\2\2\2\\]\7\f\2\2]\20\3\2\2\2^_\7\61\2\2_`\7,\2\2`d\3\2\2\2ac\13\2\2"+
		"\2ba\3\2\2\2cf\3\2\2\2de\3\2\2\2db\3\2\2\2eg\3\2\2\2fd\3\2\2\2gh\7,\2"+
		"\2hi\7\61\2\2ij\3\2\2\2jk\b\t\2\2k\22\3\2\2\2lm\7\61\2\2mn\7\61\2\2nr"+
		"\3\2\2\2oq\n\5\2\2po\3\2\2\2qt\3\2\2\2rp\3\2\2\2rs\3\2\2\2su\3\2\2\2t"+
		"r\3\2\2\2uv\b\n\2\2v\24\3\2\2\2\b\2KVZdr\3\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}