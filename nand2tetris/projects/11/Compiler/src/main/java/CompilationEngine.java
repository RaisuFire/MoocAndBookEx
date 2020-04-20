import constant.Keyword;
import constant.TokenType;
import entity.Token;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class CompilationEngine {
    public static String[] opArray = new String[]{"+", "-", "*", "/", "&", "|", "<", ">", "=", "&lt;", "&gt;", "&amp;"};
    public static String[] unaryArray = new String[] {"~", "-"};

    private JackTokenizer tokenizer;
    private String xml = "";
    private Token token;

    public CompilationEngine(JackTokenizer tokenizer) throws FileNotFoundException {
        this.tokenizer = tokenizer;
    }

    public void compileClass() {
        xml += "<class>\n";
        this.eat("class");
        this.eat(token.getVal());
        this.eat("{");
        while (token.getVal().equals("static") || token.getVal().equals("field")) {
            this.compileClassVarDec();
        }
        while (token.getVal().equals("function") || token.getVal().equals("method") || token.getVal().equals("constructor")) {
            this.compileSubroutine();
        }
        this.eat("}");
        xml += "</class>\n";
    }

    private void compileSubroutine() {
        assert (token.getVal().equals("function") || token.getVal().equals("method") || token.getVal().equals("constructor"));
        xml += "<subroutineDec>\n";
        this.eat(token.getVal());
        this.eat(token.getVal());
        this.eat(token.getVal());
        this.eat("(");
        this.compileParameterList();
        this.eat(")");
        this.compileSubroutineBody();
        xml += "</subroutineDec>\n";
    }

    private void compileSubroutineBody() {
        xml += "<subroutineBody>\n";
        this.eat("{");
        while (token.getVal().equals("var")) {
            this.compileVarDec();
        }

        xml += "<statements>\n";
        this.compileStatements();
        xml += "</statements>\n";

        this.eat("}");
        xml += "</subroutineBody>\n";
    }

    public void compileClassVarDec() {
        xml += "<classVarDec>\n";
        String s = token.getVal().equals("static")? "static": "field";
        this.eat(s);
        this.eat(token.getVal());
        this.eat(token.getVal());
        while (token.getVal().equals(",")) {
            this.eat(",");
            this.eat(token.getVal());
        }
        this.eat(";");
        xml += "</classVarDec>\n";
    }

    public void compileSubroutineCall() {
        assert (token.getType().equals(TokenType.IDENTIFIER));
        this.eat(token.getVal());
        if (token.getVal().equals(".")) {
            this.eat(".");
            this.eat(token.getVal());
        }
        this.eat("(");
        this.compileExpressionList();
        this.eat(")");
        this.eat(";");
    }

    public void compileParameterList() {
        xml += "<parameterList>\n";
        if (token.getVal().equals(")") && token.getType().equals(TokenType.SYMBOL)) {
            xml += "</parameterList>\n";
            return;
        }
        this.eat(token.getVal());
        this.eat(token.getVal());
        while (token!= null && token.getVal().equals(",")) {
            this.eat(",");
            this.eat(token.getVal());
            this.eat(token.getVal());
        }
        xml += "</parameterList>\n";
    }

    public void compileVarDec() {
        xml += "<varDec>\n";
        this.eat("var");
        this.eat(token.getVal());
        this.eat(token.getVal());
        while (token.getVal().equals(",")) {
            this.eat(",");
            this.eat(token.getVal());
        }
        this.eat(";");
        xml += "</varDec>\n";
    }

    public void compileStatements() {
//        System.out.println(xml);
        if (null == token) {
            return;
        } else if (token.getVal().equals("}") && token.getType().equals(TokenType.SYMBOL)) {
            return;
        }  else if (token.getType() == TokenType.KEYWORD) {
            if (Keyword.LET.getText().equals(token.getVal())) {
                this.compileLet();
            } else if (Keyword.DO.getText().equals(token.getVal())) {
                this.compileDo();
            } else if (Keyword.IF.getText().equals(token.getVal())) {
                this.compileIf();
            } else if (Keyword.WHILE.getText().equals(token.getVal())) {
                this.compileWhile();
            } else if (Keyword.RETURN.getText().equals(token.getVal())) {
                this.compileReturn();
            }
        }
        compileStatements();
    }

    public void compileDo() {
        xml += "<doStatement>\n";
        this.eat("do");
        this.compileSubroutineCall();
        xml += "</doStatement>\n";
    }

    public void compileLet() {
        xml += "<letStatement>\n";
        this.eat("let");
        this.eat(token.getVal());
        if (token.getVal().equals("[")){
            this.eat("[");
            this.compileExpression();
            this.eat("]");
        }

        this.eat("=");
        this.compileExpression();
        this.eat(";");
        xml += "</letStatement>\n";
    }

    public void compileWhile() {
        xml += "<whileStatement>\n";
        this.eat("while");
        this.eat("(");
        this.compileExpression();
        this.eat(")");
        this.eat("{");
        xml += "<statements>\n";
        this.compileStatements();
        xml += "</statements>\n";
        this.eat("}");
        xml += "</whileStatement>\n";
    }

    public void compileReturn() {
        xml += "<returnStatement>\n";
        this.eat("return");
        if (token.getVal().equals(";") && token.getType().equals(TokenType.SYMBOL)) {
            this.eat(";");
        }
        else {
            this.compileExpression();
            this.eat(";");
        }
        xml += "</returnStatement>\n";
    }

    public void compileIf() {
        xml += "<ifStatement>\n";
        this.eat("if");
        this.eat("(");
        this.compileExpression();
        this.eat(")");
        this.eat("{");
        xml += "<statements>\n";
        this.compileStatements();
        xml += "</statements>\n";
        this.eat("}");
        if (token.getType().equals(TokenType.KEYWORD) && token.getVal().equals("else")) {
            this.eat("else");
            this.eat("{");
            xml += "<statements>\n";
            this.compileStatements();
            xml += "</statements>\n";
            this.eat("}");
        }
        xml += "</ifStatement>\n";
    }

    private void compileExpression() {
        xml += "<expression>\n";
        this.compileTerm();
        if (Arrays.asList(opArray).contains(token.getVal())) {
            this.eat(token.getVal());
            this.compileTerm();
        }
        xml += "</expression>\n";
    }

    public void compileTerm() {
        xml += "<term>\n";
        if (token.getVal().equals("(") && token.getType().equals(TokenType.SYMBOL)) {
            this.eat("(");
            this.compileExpression();
            this.eat(")");
            xml += "</term>\n";
            return;
        }
        if (Arrays.asList(unaryArray).contains(token.getVal())) {
            this.eat(token.getVal());
            this.compileTerm();
            xml += "</term>\n";
            return;
        }
        this.eat(token.getVal());
        if (token.getVal().equals(".") && token.getType().equals(TokenType.SYMBOL)) {
            this.eat(".");
            this.eat(token.getVal());
            this.eat("(");
            this.compileExpressionList();
            this.eat(")");
        }
        if (token.getVal().equals("[") && token.getType().equals(TokenType.SYMBOL)) {
            this.eat("[");
            this.compileExpression();
            this.eat("]");
        }

        xml += "</term>\n";
    }

    public void compileExpressionList() {
        xml += "<expressionList>\n";
        if (token.getVal().equals(")") && token.getType().equals(TokenType.SYMBOL)) {
            xml += "</expressionList>\n";
            return;
        }
        this.compileExpression();
        while (token.getVal().equals(",")) {
            xml += "<symbol>"+ token.getVal() + "</symbol>\n";
            token = tokenizer.advance();
            this.compileExpression();
        }
        xml += "</expressionList>\n";
    }

    private void eat(String string) {
        if (!token.getVal().equals(string)) {
            throw new RuntimeException("unexpect token" + token);
        } else {
            xml += "<" + token.getType().getText() + ">" + token.getVal() + "</" + token.getType().getText() + ">\n";
            token = tokenizer.advance();
        }
    }

    public void firstToken() {
        token = tokenizer.advance();
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }
}
