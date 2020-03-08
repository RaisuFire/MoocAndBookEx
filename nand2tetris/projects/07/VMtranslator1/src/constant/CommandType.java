package constant;

public enum  CommandType {
    C_ARITHMETIC(1),
    C_PUSH(2),
    C_POP(3),
    C_LABEL(4),
    C_GOTO(5),
    C_IF(6),
    C_FUNCTION(7),
    C_RETURN(8),
    C_CALL(9);

    private int code;   //

    CommandType(int code) {
        this.code = code;
    }

    public int getTypeCode() {
        return code;
    }
}
