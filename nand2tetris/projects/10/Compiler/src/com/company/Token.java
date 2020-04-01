package com.company;

import com.company.constant.TokenType;

public class Token {
    private TokenType type;
    private String val;

    public Token(TokenType type, String val) {
        this.type = type;
        this.val = val;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
