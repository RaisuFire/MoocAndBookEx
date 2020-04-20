package entity;

public class CharUtil {

    public static boolean isWhiteSpace(char c) {
        switch (c) {
            case '\t':
            case '\n':
            case 0x0B: // \v
            case '\f':
            case '\r':
            case ' ':
                return true;
        }
        return false;
    }

    public static boolean isNewLine(char c) {
        return c == '\r' || c == '\n';
    }

    public static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    public static boolean isLetter(char c) {
        return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z';
    }

}