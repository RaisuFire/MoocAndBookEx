package constant;

public enum Kind {
    STATIC("static"),
    FIELD("field"),
    ARGUMENT("argument"),
    VAR("var");

    private String text;
    private Kind(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
