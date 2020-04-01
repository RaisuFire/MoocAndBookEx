package com.company;

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

    void next(int n) {
        pos += n;
    }

    boolean startsWith(String prefix) {
        return str.startsWith(prefix, pos);
    }

    int indexOf(String s) {
        return str.indexOf(s, pos) - pos;
    }

    String substring(int beginIndex, int endIndex) {
        return str.substring(beginIndex + pos, endIndex + pos);
    }

    String find(Pattern pattern) {
        Matcher matcher = pattern.matcher(this);
        return matcher.find()
                ? matcher.group(0) : null;
    }

    @Override
    public int length() {
        return str.length() - pos;
    }

    @Override
    public char charAt(int index) {
        return str.charAt(index + pos);
    }

    @Override
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
