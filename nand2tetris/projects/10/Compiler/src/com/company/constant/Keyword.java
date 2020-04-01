package com.company.constant;

public enum  Keyword {
    CLASS("class"),
    MEHTOD("method"),
    INT("int"),
    FUNCTION("function"),
    BOOLEAN("boolean"),
    CONSTRUCTOR("constructor"),
    CHAR("char"),
    VOID("void"),
    VAR("var"),
    STATIC("sattic"),
    FIELD("field"),
    LET("let"),
    DO("do"),
    IF("if"),
    ELSE("else"),
    WHILE("while"),
    RETURN("return"),
    TRUE("true"),
    FALSE("false"),
    NULL("null"),
    THIS("this");

    private String text;
    private Keyword(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }


}
