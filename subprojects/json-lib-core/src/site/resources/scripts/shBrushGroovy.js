dp.sh.Brushes.Groovy = function()
{
	var keywords =	'as assert break case catch class continue def default do else extends finally ' +
                        'if in implements import instanceof interface new package property return switch ' +
                        'throw throws try while';
        var types    =  'void boolean byte char short int long float double';
        var modifiers = 'public protected private static';
        var constants = 'null';
        var methods   = 'allProperties count get size '+
                        'collect each eachProperty eachPropertyName eachWithIndex find findAll ' +
                        'findIndexOf grep inject max min reverseEach sort ' +
                        'asImmutable asSynchronized flatten intersect join pop reverse subMap toList ' +
                        'padRight padLeft contains eachMatch toCharacter toLong toUrl tokenize ' +
                        'eachFile eachFileRecurse eachB yte eachLine readBytes readLine getText ' +
                        'splitEachLine withReader append encodeBase64 decodeBase64 filterLine ' +
                        'transformChar transformLine withOutputStream withPrintWriter withStream ' +
                        'withStreams withWriter withWriterAppend write writeLine '+
                        'dump inspect invokeMethod print println step times upto use waitForOrKill '+
                        'getText';

	this.regexList = [
		{ regex: dp.sh.RegexLib.SingleLineCComments,				css: 'comment' },	// one line comments
		{ regex: dp.sh.RegexLib.MultiLineCComments,				css: 'comment' },	// multiline comments
		{ regex: dp.sh.RegexLib.DoubleQuotedString,				css: 'string' },	// strings
		{ regex: dp.sh.RegexLib.SingleQuotedString,				css: 'string' },	// strings
		{ regex: new RegExp('""".*"""','g'),		css: 'string' },	// GStrings
		{ regex: new RegExp('\\b([\\d]+(\\.[\\d]+)?|0x[a-f0-9]+)\\b', 'gi'),	css: 'number' },	// numbers
		{ regex: new RegExp(this.GetKeywords(keywords), 'gm'),			css: 'keyword' },	// goovy keyword
		{ regex: new RegExp(this.GetKeywords(types), 'gm'),			css: 'type' },		// goovy/java type
		{ regex: new RegExp(this.GetKeywords(modifiers), 'gm'),			css: 'modifier' },	// java modifier
		{ regex: new RegExp(this.GetKeywords(constants), 'gm'),			css: 'constant' },	// constants
		{ regex: new RegExp(this.GetKeywords(methods), 'gm'),			css: 'method' }		// methods
		];

	this.CssClass = 'dp-g';
}

dp.sh.Brushes.Groovy.prototype	= new dp.sh.Highlighter();
dp.sh.Brushes.Groovy.Aliases	= ['groovy'];
