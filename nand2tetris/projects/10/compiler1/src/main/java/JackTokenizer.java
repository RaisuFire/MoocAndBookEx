
import constant.Keyword;
import constant.TokenType;

import java.io.*;
import java.util.List;
import java.util.regex.Pattern;

public class JackTokenizer {

    public static final Pattern longComment = Pattern.compile("[/**].*[*/]");
    public static final Pattern reNumber = Pattern.compile("\\d+");
    public static final Pattern reNewLine = Pattern.compile("\\r\\n|\\n\\r|\\n|\\r");
    public static final Pattern reShorStr = Pattern.compile("\".*\"");
    public static final Pattern reIdentifer = Pattern.compile("^[_\\d\\w]+");

    private CharSeq text;
    private Token cachedNextToken;


    public JackTokenizer(String text) {
        this.text = new CharSeq(text);
    }

    public boolean hasMoreTokens() {
        return this.text.getPos() < this.text.getStr().length();
    }

    public Token advance() {

        if (cachedNextToken != null) {
            Token token = cachedNextToken;
            cachedNextToken = null;
            return token;
        }

        this.skipWhiteSpaces();

        if (!hasMoreTokens()) {
            return null;
        }

        //
        switch (text.charAt(0)) {
            case '{' :
                text.next(1);
                return new Token(TokenType.SYMBOL, "{");
            case '}' :
                text.next(1);
                return new Token(TokenType.SYMBOL, "}");
            case '(' :
                text.next(1);
                return new Token(TokenType.SYMBOL, "(");
            case ')' :
                text.next(1);
                return new Token(TokenType.SYMBOL, ")");
            case '[' :
                text.next(1);
                return new Token(TokenType.SYMBOL, "[");
            case ']' :
                text.next(1);
                return new Token(TokenType.SYMBOL, "]");
            case '.' :
                text.next(1);
                return new Token(TokenType.SYMBOL, ".");
            case ',' :
                text.next(1);
                return new Token(TokenType.SYMBOL, ",");
            case ';' :
                text.next(1);
                return new Token(TokenType.SYMBOL, ";");
            case '+' :
                text.next(1);
                return new Token(TokenType.SYMBOL, "+");
            case '-' :
                text.next(1);
                return new Token(TokenType.SYMBOL, "-");
            case '*' :
                text.next(1);
                return new Token(TokenType.SYMBOL, "*");
            case '/' :
                if (text.startsWith("//") && text.startsWith("/**")) {
                    this.skipWhiteSpaces();
                } else {
                    text.next(1);
                    return new Token(TokenType.SYMBOL, "/");
                }
            case '&' :
                text.next(1);
                return new Token(TokenType.SYMBOL, "&amp;");
            case '|' :
                text.next(1);
                return new Token(TokenType.SYMBOL, "|");
            case '<' :
                text.next(1);
                return new Token(TokenType.SYMBOL, "&lt;");
            case '>' :
                text.next(1);
                return new Token(TokenType.SYMBOL, "&gt;");
            case '=' :
                text.next(1);
                return new Token(TokenType.SYMBOL, "=");
            case '~' :
                text.next(1);
                return new Token(TokenType.SYMBOL, "~");

            case '"' :
                return new Token(TokenType.STRING_CONST, scanShortStr());
        }

        char c = text.charAt(0);
        if (CharUtil.isDigit(c)) {
            return new Token(TokenType.INT_CONST, scanNumber());
        }

        if (CharUtil.isLetter(c)) {
            String id = this.scanIdentifier();
            if (this.contain(Keyword.values(), id)) {
                return new Token(TokenType.KEYWORD, id);
            } else {
                return new Token(TokenType.IDENTIFIER, id);
            }
        }

        return error("syntax error near");
    }

    public Token LookAhead() {
        if (cachedNextToken == null) {
            cachedNextToken = advance();
        }
        return cachedNextToken;
    }


    private boolean contain(Keyword[] values, String id) {
        for (Keyword k: values) {
            if (k.getText().equals(id)) {
                return true;
            }
        }
        return false;
    }

    private String scanIdentifier() {
        return scan(reIdentifer);
    }

    private String scanNumber() {
        return scan(reNumber);
    }

    private String scanShortStr() {
        String s = scan(reShorStr);
        return s.substring(1, s.length() - 1);
    }

    public String scan(Pattern pattern) {
        String token = text.find(pattern);
        if (token == null) {
            throw new RuntimeException("unreachable!");
        }
        text.next(token.length());
        return token;
    }

    private void skipWhiteSpaces() {
        while (text.length() > 0) {
            if (text.startsWith("\r\n") || text.startsWith("\n\r")) {
                text.next(2);
            } else if (CharUtil.isNewLine(text.charAt(0))) {
                text.next(1);
            } else if(CharUtil.isWhiteSpace(text.charAt(0))) {
                text.next(1);
            } else if (text.startsWith("//")) {
                skipshortComment();
            } else if (text.startsWith("/**")) {
                skipLongComment();
            } else {
                break;
            }
        }
    }

    private void skipshortComment() {
        if (text.startsWith("//")) {
            text.next(2);
            while (text.length() > 0 && !CharUtil.isNewLine(text.charAt(0))) {
                text.next(1);
            }
        }
    }

    private String skipLongComment() {
        String close = text.find(Pattern.compile("\\*/"));
        if (close == null) {
            return error("no close comment symbole */");
        }

        int lenght = text.indexOf("*/") + 2;
        String comment = text.substring(0, lenght);
        text.next(lenght);
        return comment;
    }

    <T> T error(String fmt, Object... args) {
        String msg = String.format(fmt, args);
        msg = String.format("%s", msg);
        throw new RuntimeException(msg);
    }

    public Token getCachedNextToken() {
        return cachedNextToken;
    }

    public void setCachedNextToken(Token cachedNextToken) {
        this.cachedNextToken = cachedNextToken;
    }

    //
    public CharSeq getText() {
        return text;
    }

    public void setText(CharSeq text) {
        this.text = text;
    }


//    public TokenType tokenType() {
//
//    }
//
//    public Keyword keyword() {
//
//    }
//
//    public String symbol() {
//
//    }
//
//    public String identifier() {
//
//    }
//
//    public Integer intVal() {
//
//    }
//
//    public String stringVal() {
//
//    }


}
