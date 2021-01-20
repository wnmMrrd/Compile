// Generated from C:/Users/ASUS/Desktop/My_Mx/src/Parser\Mx.g4 by ANTLR 4.9
package Parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MxLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		BoolValue=1, Bool=2, Int=3, Void=4, String=5, Null=6, If=7, Else=8, Continue=9, 
		Break=10, For=11, Switch=12, While=13, Return=14, Struct=15, This=16, 
		True=17, False=18, New=19, Class=20, LeftParen=21, RightParen=22, LeftBracket=23, 
		RightBracket=24, LeftBrace=25, RightBrace=26, Less=27, LessEqual=28, Greater=29, 
		GreaterEqual=30, LeftShift=31, RightShift=32, Plus=33, PlusPlus=34, Minus=35, 
		MinusMinus=36, Mul=37, Div=38, Mod=39, And=40, Or=41, AndAnd=42, OrOr=43, 
		Caret=44, Not=45, Tilde=46, Dot=47, Question=48, Colon=49, Semi=50, Comma=51, 
		Assign=52, Equal=53, NotEqual=54, StringLiteral=55, Identifier=56, DecimalInteger=57, 
		Whitespace=58, Newline=59, BlockComment=60, LineComment=61;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"BoolValue", "Bool", "Int", "Void", "String", "Null", "If", "Else", "Continue", 
			"Break", "For", "Switch", "While", "Return", "Struct", "This", "True", 
			"False", "New", "Class", "LeftParen", "RightParen", "LeftBracket", "RightBracket", 
			"LeftBrace", "RightBrace", "Less", "LessEqual", "Greater", "GreaterEqual", 
			"LeftShift", "RightShift", "Plus", "PlusPlus", "Minus", "MinusMinus", 
			"Mul", "Div", "Mod", "And", "Or", "AndAnd", "OrOr", "Caret", "Not", "Tilde", 
			"Dot", "Question", "Colon", "Semi", "Comma", "Assign", "Equal", "NotEqual", 
			"StringLiteral", "Identifier", "DecimalInteger", "Whitespace", "Newline", 
			"BlockComment", "LineComment"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, "'bool'", "'int'", "'void'", "'string'", "'null'", "'if'", 
			"'else'", "'continue'", "'break'", "'for'", "'switch'", "'while'", "'return'", 
			"'struct'", "'this'", "'true'", "'false'", "'new'", "'class'", "'('", 
			"')'", "'['", "']'", "'{'", "'}'", "'<'", "'<='", "'>'", "'>='", "'<<'", 
			"'>>'", "'+'", "'++'", "'-'", "'--'", "'*'", "'/'", "'%'", "'&'", "'|'", 
			"'&&'", "'||'", "'^'", "'!'", "'~'", "'.'", "'?'", "':'", "';'", "','", 
			"'='", "'=='", "'!='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "BoolValue", "Bool", "Int", "Void", "String", "Null", "If", "Else", 
			"Continue", "Break", "For", "Switch", "While", "Return", "Struct", "This", 
			"True", "False", "New", "Class", "LeftParen", "RightParen", "LeftBracket", 
			"RightBracket", "LeftBrace", "RightBrace", "Less", "LessEqual", "Greater", 
			"GreaterEqual", "LeftShift", "RightShift", "Plus", "PlusPlus", "Minus", 
			"MinusMinus", "Mul", "Div", "Mod", "And", "Or", "AndAnd", "OrOr", "Caret", 
			"Not", "Tilde", "Dot", "Question", "Colon", "Semi", "Comma", "Assign", 
			"Equal", "NotEqual", "StringLiteral", "Identifier", "DecimalInteger", 
			"Whitespace", "Newline", "BlockComment", "LineComment"
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


	public MxLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Mx.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2?\u017e\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\3\2\3\2\5\2\u0080\n\2\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\5\3"+
		"\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\b\3\b"+
		"\3\b\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13"+
		"\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22"+
		"\3\22\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32\3\33"+
		"\3\33\3\34\3\34\3\35\3\35\3\35\3\36\3\36\3\37\3\37\3\37\3 \3 \3 \3!\3"+
		"!\3!\3\"\3\"\3#\3#\3#\3$\3$\3%\3%\3%\3&\3&\3\'\3\'\3(\3(\3)\3)\3*\3*\3"+
		"+\3+\3+\3,\3,\3,\3-\3-\3.\3.\3/\3/\3\60\3\60\3\61\3\61\3\62\3\62\3\63"+
		"\3\63\3\64\3\64\3\65\3\65\3\66\3\66\3\66\3\67\3\67\3\67\38\38\38\38\7"+
		"8\u013e\n8\f8\168\u0141\138\38\38\39\39\79\u0147\n9\f9\169\u014a\139\3"+
		":\3:\7:\u014e\n:\f:\16:\u0151\13:\3:\5:\u0154\n:\3;\6;\u0157\n;\r;\16"+
		";\u0158\3;\3;\3<\3<\5<\u015f\n<\3<\5<\u0162\n<\3<\3<\3=\3=\3=\3=\7=\u016a"+
		"\n=\f=\16=\u016d\13=\3=\3=\3=\3=\3=\3>\3>\3>\3>\7>\u0178\n>\f>\16>\u017b"+
		"\13>\3>\3>\3\u016b\2?\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27"+
		"\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33"+
		"\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a\62c\63"+
		"e\64g\65i\66k\67m8o9q:s;u<w=y>{?\3\2\n\6\2\f\f\17\17$$^^\6\2$$^^pptt\4"+
		"\2C\\c|\6\2\62;C\\aac|\3\2\63;\3\2\62;\4\2\13\13\"\"\4\2\f\f\17\17\2\u0188"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2"+
		"\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2"+
		"\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2"+
		"\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3"+
		"\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2"+
		"\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2"+
		"U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3"+
		"\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2m\3\2\2"+
		"\2\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2u\3\2\2\2\2w\3\2\2\2\2y\3\2\2\2\2"+
		"{\3\2\2\2\3\177\3\2\2\2\5\u0081\3\2\2\2\7\u0086\3\2\2\2\t\u008a\3\2\2"+
		"\2\13\u008f\3\2\2\2\r\u0096\3\2\2\2\17\u009b\3\2\2\2\21\u009e\3\2\2\2"+
		"\23\u00a3\3\2\2\2\25\u00ac\3\2\2\2\27\u00b2\3\2\2\2\31\u00b6\3\2\2\2\33"+
		"\u00bd\3\2\2\2\35\u00c3\3\2\2\2\37\u00ca\3\2\2\2!\u00d1\3\2\2\2#\u00d6"+
		"\3\2\2\2%\u00db\3\2\2\2\'\u00e1\3\2\2\2)\u00e5\3\2\2\2+\u00eb\3\2\2\2"+
		"-\u00ed\3\2\2\2/\u00ef\3\2\2\2\61\u00f1\3\2\2\2\63\u00f3\3\2\2\2\65\u00f5"+
		"\3\2\2\2\67\u00f7\3\2\2\29\u00f9\3\2\2\2;\u00fc\3\2\2\2=\u00fe\3\2\2\2"+
		"?\u0101\3\2\2\2A\u0104\3\2\2\2C\u0107\3\2\2\2E\u0109\3\2\2\2G\u010c\3"+
		"\2\2\2I\u010e\3\2\2\2K\u0111\3\2\2\2M\u0113\3\2\2\2O\u0115\3\2\2\2Q\u0117"+
		"\3\2\2\2S\u0119\3\2\2\2U\u011b\3\2\2\2W\u011e\3\2\2\2Y\u0121\3\2\2\2["+
		"\u0123\3\2\2\2]\u0125\3\2\2\2_\u0127\3\2\2\2a\u0129\3\2\2\2c\u012b\3\2"+
		"\2\2e\u012d\3\2\2\2g\u012f\3\2\2\2i\u0131\3\2\2\2k\u0133\3\2\2\2m\u0136"+
		"\3\2\2\2o\u0139\3\2\2\2q\u0144\3\2\2\2s\u0153\3\2\2\2u\u0156\3\2\2\2w"+
		"\u0161\3\2\2\2y\u0165\3\2\2\2{\u0173\3\2\2\2}\u0080\5#\22\2~\u0080\5%"+
		"\23\2\177}\3\2\2\2\177~\3\2\2\2\u0080\4\3\2\2\2\u0081\u0082\7d\2\2\u0082"+
		"\u0083\7q\2\2\u0083\u0084\7q\2\2\u0084\u0085\7n\2\2\u0085\6\3\2\2\2\u0086"+
		"\u0087\7k\2\2\u0087\u0088\7p\2\2\u0088\u0089\7v\2\2\u0089\b\3\2\2\2\u008a"+
		"\u008b\7x\2\2\u008b\u008c\7q\2\2\u008c\u008d\7k\2\2\u008d\u008e\7f\2\2"+
		"\u008e\n\3\2\2\2\u008f\u0090\7u\2\2\u0090\u0091\7v\2\2\u0091\u0092\7t"+
		"\2\2\u0092\u0093\7k\2\2\u0093\u0094\7p\2\2\u0094\u0095\7i\2\2\u0095\f"+
		"\3\2\2\2\u0096\u0097\7p\2\2\u0097\u0098\7w\2\2\u0098\u0099\7n\2\2\u0099"+
		"\u009a\7n\2\2\u009a\16\3\2\2\2\u009b\u009c\7k\2\2\u009c\u009d\7h\2\2\u009d"+
		"\20\3\2\2\2\u009e\u009f\7g\2\2\u009f\u00a0\7n\2\2\u00a0\u00a1\7u\2\2\u00a1"+
		"\u00a2\7g\2\2\u00a2\22\3\2\2\2\u00a3\u00a4\7e\2\2\u00a4\u00a5\7q\2\2\u00a5"+
		"\u00a6\7p\2\2\u00a6\u00a7\7v\2\2\u00a7\u00a8\7k\2\2\u00a8\u00a9\7p\2\2"+
		"\u00a9\u00aa\7w\2\2\u00aa\u00ab\7g\2\2\u00ab\24\3\2\2\2\u00ac\u00ad\7"+
		"d\2\2\u00ad\u00ae\7t\2\2\u00ae\u00af\7g\2\2\u00af\u00b0\7c\2\2\u00b0\u00b1"+
		"\7m\2\2\u00b1\26\3\2\2\2\u00b2\u00b3\7h\2\2\u00b3\u00b4\7q\2\2\u00b4\u00b5"+
		"\7t\2\2\u00b5\30\3\2\2\2\u00b6\u00b7\7u\2\2\u00b7\u00b8\7y\2\2\u00b8\u00b9"+
		"\7k\2\2\u00b9\u00ba\7v\2\2\u00ba\u00bb\7e\2\2\u00bb\u00bc\7j\2\2\u00bc"+
		"\32\3\2\2\2\u00bd\u00be\7y\2\2\u00be\u00bf\7j\2\2\u00bf\u00c0\7k\2\2\u00c0"+
		"\u00c1\7n\2\2\u00c1\u00c2\7g\2\2\u00c2\34\3\2\2\2\u00c3\u00c4\7t\2\2\u00c4"+
		"\u00c5\7g\2\2\u00c5\u00c6\7v\2\2\u00c6\u00c7\7w\2\2\u00c7\u00c8\7t\2\2"+
		"\u00c8\u00c9\7p\2\2\u00c9\36\3\2\2\2\u00ca\u00cb\7u\2\2\u00cb\u00cc\7"+
		"v\2\2\u00cc\u00cd\7t\2\2\u00cd\u00ce\7w\2\2\u00ce\u00cf\7e\2\2\u00cf\u00d0"+
		"\7v\2\2\u00d0 \3\2\2\2\u00d1\u00d2\7v\2\2\u00d2\u00d3\7j\2\2\u00d3\u00d4"+
		"\7k\2\2\u00d4\u00d5\7u\2\2\u00d5\"\3\2\2\2\u00d6\u00d7\7v\2\2\u00d7\u00d8"+
		"\7t\2\2\u00d8\u00d9\7w\2\2\u00d9\u00da\7g\2\2\u00da$\3\2\2\2\u00db\u00dc"+
		"\7h\2\2\u00dc\u00dd\7c\2\2\u00dd\u00de\7n\2\2\u00de\u00df\7u\2\2\u00df"+
		"\u00e0\7g\2\2\u00e0&\3\2\2\2\u00e1\u00e2\7p\2\2\u00e2\u00e3\7g\2\2\u00e3"+
		"\u00e4\7y\2\2\u00e4(\3\2\2\2\u00e5\u00e6\7e\2\2\u00e6\u00e7\7n\2\2\u00e7"+
		"\u00e8\7c\2\2\u00e8\u00e9\7u\2\2\u00e9\u00ea\7u\2\2\u00ea*\3\2\2\2\u00eb"+
		"\u00ec\7*\2\2\u00ec,\3\2\2\2\u00ed\u00ee\7+\2\2\u00ee.\3\2\2\2\u00ef\u00f0"+
		"\7]\2\2\u00f0\60\3\2\2\2\u00f1\u00f2\7_\2\2\u00f2\62\3\2\2\2\u00f3\u00f4"+
		"\7}\2\2\u00f4\64\3\2\2\2\u00f5\u00f6\7\177\2\2\u00f6\66\3\2\2\2\u00f7"+
		"\u00f8\7>\2\2\u00f88\3\2\2\2\u00f9\u00fa\7>\2\2\u00fa\u00fb\7?\2\2\u00fb"+
		":\3\2\2\2\u00fc\u00fd\7@\2\2\u00fd<\3\2\2\2\u00fe\u00ff\7@\2\2\u00ff\u0100"+
		"\7?\2\2\u0100>\3\2\2\2\u0101\u0102\7>\2\2\u0102\u0103\7>\2\2\u0103@\3"+
		"\2\2\2\u0104\u0105\7@\2\2\u0105\u0106\7@\2\2\u0106B\3\2\2\2\u0107\u0108"+
		"\7-\2\2\u0108D\3\2\2\2\u0109\u010a\7-\2\2\u010a\u010b\7-\2\2\u010bF\3"+
		"\2\2\2\u010c\u010d\7/\2\2\u010dH\3\2\2\2\u010e\u010f\7/\2\2\u010f\u0110"+
		"\7/\2\2\u0110J\3\2\2\2\u0111\u0112\7,\2\2\u0112L\3\2\2\2\u0113\u0114\7"+
		"\61\2\2\u0114N\3\2\2\2\u0115\u0116\7\'\2\2\u0116P\3\2\2\2\u0117\u0118"+
		"\7(\2\2\u0118R\3\2\2\2\u0119\u011a\7~\2\2\u011aT\3\2\2\2\u011b\u011c\7"+
		"(\2\2\u011c\u011d\7(\2\2\u011dV\3\2\2\2\u011e\u011f\7~\2\2\u011f\u0120"+
		"\7~\2\2\u0120X\3\2\2\2\u0121\u0122\7`\2\2\u0122Z\3\2\2\2\u0123\u0124\7"+
		"#\2\2\u0124\\\3\2\2\2\u0125\u0126\7\u0080\2\2\u0126^\3\2\2\2\u0127\u0128"+
		"\7\60\2\2\u0128`\3\2\2\2\u0129\u012a\7A\2\2\u012ab\3\2\2\2\u012b\u012c"+
		"\7<\2\2\u012cd\3\2\2\2\u012d\u012e\7=\2\2\u012ef\3\2\2\2\u012f\u0130\7"+
		".\2\2\u0130h\3\2\2\2\u0131\u0132\7?\2\2\u0132j\3\2\2\2\u0133\u0134\7?"+
		"\2\2\u0134\u0135\7?\2\2\u0135l\3\2\2\2\u0136\u0137\7#\2\2\u0137\u0138"+
		"\7?\2\2\u0138n\3\2\2\2\u0139\u013f\7$\2\2\u013a\u013e\n\2\2\2\u013b\u013c"+
		"\7^\2\2\u013c\u013e\t\3\2\2\u013d\u013a\3\2\2\2\u013d\u013b\3\2\2\2\u013e"+
		"\u0141\3\2\2\2\u013f\u013d\3\2\2\2\u013f\u0140\3\2\2\2\u0140\u0142\3\2"+
		"\2\2\u0141\u013f\3\2\2\2\u0142\u0143\7$\2\2\u0143p\3\2\2\2\u0144\u0148"+
		"\t\4\2\2\u0145\u0147\t\5\2\2\u0146\u0145\3\2\2\2\u0147\u014a\3\2\2\2\u0148"+
		"\u0146\3\2\2\2\u0148\u0149\3\2\2\2\u0149r\3\2\2\2\u014a\u0148\3\2\2\2"+
		"\u014b\u014f\t\6\2\2\u014c\u014e\t\7\2\2\u014d\u014c\3\2\2\2\u014e\u0151"+
		"\3\2\2\2\u014f\u014d\3\2\2\2\u014f\u0150\3\2\2\2\u0150\u0154\3\2\2\2\u0151"+
		"\u014f\3\2\2\2\u0152\u0154\7\62\2\2\u0153\u014b\3\2\2\2\u0153\u0152\3"+
		"\2\2\2\u0154t\3\2\2\2\u0155\u0157\t\b\2\2\u0156\u0155\3\2\2\2\u0157\u0158"+
		"\3\2\2\2\u0158\u0156\3\2\2\2\u0158\u0159\3\2\2\2\u0159\u015a\3\2\2\2\u015a"+
		"\u015b\b;\2\2\u015bv\3\2\2\2\u015c\u015e\7\17\2\2\u015d\u015f\7\f\2\2"+
		"\u015e\u015d\3\2\2\2\u015e\u015f\3\2\2\2\u015f\u0162\3\2\2\2\u0160\u0162"+
		"\7\f\2\2\u0161\u015c\3\2\2\2\u0161\u0160\3\2\2\2\u0162\u0163\3\2\2\2\u0163"+
		"\u0164\b<\2\2\u0164x\3\2\2\2\u0165\u0166\7\61\2\2\u0166\u0167\7,\2\2\u0167"+
		"\u016b\3\2\2\2\u0168\u016a\13\2\2\2\u0169\u0168\3\2\2\2\u016a\u016d\3"+
		"\2\2\2\u016b\u016c\3\2\2\2\u016b\u0169\3\2\2\2\u016c\u016e\3\2\2\2\u016d"+
		"\u016b\3\2\2\2\u016e\u016f\7,\2\2\u016f\u0170\7\61\2\2\u0170\u0171\3\2"+
		"\2\2\u0171\u0172\b=\2\2\u0172z\3\2\2\2\u0173\u0174\7\61\2\2\u0174\u0175"+
		"\7\61\2\2\u0175\u0179\3\2\2\2\u0176\u0178\n\t\2\2\u0177\u0176\3\2\2\2"+
		"\u0178\u017b\3\2\2\2\u0179\u0177\3\2\2\2\u0179\u017a\3\2\2\2\u017a\u017c"+
		"\3\2\2\2\u017b\u0179\3\2\2\2\u017c\u017d\b>\2\2\u017d|\3\2\2\2\16\2\177"+
		"\u013d\u013f\u0148\u014f\u0153\u0158\u015e\u0161\u016b\u0179\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}