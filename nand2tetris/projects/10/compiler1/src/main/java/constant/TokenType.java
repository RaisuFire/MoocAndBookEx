package constant;

public enum  TokenType {
    KEYWORD("keyword"),
    SYMBOL("symbol"),
    IDENTIFIER("identifier"),
    INT_CONST("integerConstant"),
    STRING_CONST("stringConstant");

    private String text;
    private TokenType(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
