package constant;

public enum  Jump {
    NULL("null", 0),
    JGT("JGT", 1),
    JEQ("JEQ", 2),
    JGE("JGE", 3),
    JLT("JLT", 4),
    JNE("JNE", 5),
    JLE("JLE", 6),
    JMP("JMP", 7);

    Jump(String jump, Integer value) {
        this.jump = jump;
        this.value = value;
    }
    private int value;
    private String jump;

    public int getValue() {
        return value;
    }

    public String getJump() {
        return jump;
    }



}

