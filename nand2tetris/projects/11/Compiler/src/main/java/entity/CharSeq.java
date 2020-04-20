package entity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharSeq implements CharSequence{
    private String str;
    private int pos;

    public CharSeq(String str) {
        this.str = str;
        this.pos = 0;
    }

    // TODO rename
    char nextChar() {
        return str.charAt(pos++);
    }

    public void next(int n) {
        pos += n;
    }

    public boolean startsWith(String prefix) {
        return str.startsWith(prefix, pos);
    }

    public int indexOf(String s) {
        return str.indexOf(s, pos) - pos;
    }

    public String substring(int beginIndex, int endIndex) {
        return str.substring(beginIndex + pos, endIndex + pos);
    }

    public String find(Pattern pattern) {
        Matcher matcher = pattern.matcher(this);
        return matcher.find()
                ? matcher.group(0) : null;
    }

    public int length() {
        return str.length() - pos;
    }

    public char charAt(int index) {
        return str.charAt(index + pos);
    }

    public CharSequence subSequence(int start, int end) {
        return str.subSequence(start + pos, end + pos);
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
