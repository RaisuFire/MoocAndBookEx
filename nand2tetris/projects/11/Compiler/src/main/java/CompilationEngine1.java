import constant.Keyword;
import constant.Kind;
import constant.TokenType;
import entity.Symbol;
import entity.Token;

import java.util.Arrays;

public class CompilationEngine1 {
    public static String[] opArray = new String[]{"+", "-", "*", "/", "&", "|", "<", ">", "=", "&lt;", "&gt;", "&amp;"};
    public static String[] unaryArray = new String[] {"~", "-"};


    private JackTokenizer tokenizer;
    private SymbolTabel tabel;
    private VMWrite vmWrite;
    private Token token;

    private String className = "";
    private String returnType = "";
    private String subroutineName = "";


    public CompilationEngine1(JackTokenizer tokenizer, VMWrite vmWrite) {
        this.tokenizer = tokenizer;
        this.vmWrite = vmWrite;
        this.tabel = new SymbolTabel();
    }

    public void compileClass() {
        this.eat("class");
        this.className = token.getVal();
        this.eat(token.getVal());
        this.eat("{");
        while (token.getVal().equals("static") || token.getVal().equals("field")) {
            this.compileClassVarDec();
        }
        while (token.getVal().equals("function") || token.getVal().equals("method") || token.getVal().equals("constructor")) {
            this.compileSubroutine();
        }
        this.eat("}");

    }

    public void compileClassVarDec() {
        Kind kind = token.getVal().equals("static")? Kind.STATIC: Kind.FIELD;
        this.eat(kind.getText());
        String type = this.eat(token.getVal());
        String name = this.eat(token.getVal());
        this.tabel.define(name, type, kind);
        while (token.getVal().equals(",")) {
            this.eat(",");
            String name1 = this.eat(token.getVal());
            this.tabel.define(name1, type, kind);
        }
        this.eat(";");
    }

    private void compileSubroutine() {
        assert (token.getVal().equals("function") || token.getVal().equals("method") || token.getVal().equals("constructor"));
        tabel.startSubroutine();
        this.eat(token.getVal());
        String type = this.eat(token.getVal());
        subroutineName = this.eat(token.getVal());

        this.eat("(");
        this.compileParameterList();
        this.eat(")");

        this.eat("{");
        while (token.getVal().equals("var")) {
            this.compileVarDec();
        }

        Integer numArgs = tabel.varCount(Kind.VAR) + tabel.varCount(Kind.ARGUMENT);
        vmWrite.writeFunction(className, subroutineName, numArgs);

        this.compileStatements();
        this.eat("}");
    }



    public void compileSubroutineCall() {
        assert (token.getType().equals(TokenType.IDENTIFIER));
        String objectName = "";
        String methodName = "";
        objectName = this.eat(token.getVal());
        if (token.getVal().equals(".")) {
            this.eat(token.getVal());
            methodName = this.eat(token.getVal());
        }
        this.eat("(");
        Integer n = this.compileExpressionList();
        this.eat(")");
        this.eat(";");
        this.vmWrite.writeCall(objectName, methodName, n);
    }

    private void compileParameterList() {
        if (token.getVal().equals(")")) {
            return ;
        }
        String type = this.eat(token.getVal());
        String name = this.eat(token.getVal());
        this.tabel.define(name, type, Kind.ARGUMENT);
        while (token!= null && token.getVal().equals(",")) {
            this.eat(",");
            String type1 = this.eat(token.getVal());
            String name1 = this.eat(token.getVal());
            this.tabel.define(name1, type1, Kind.ARGUMENT);
        }

        return ;
    }

    private void compileVarDec() {
        this.eat("var");
        String type = this.eat(token.getVal());
        String name = this.eat(token.getVal());
        this.tabel.define(name, type, Kind.VAR);
        while (token.getVal().equals(",")) {
            this.eat(",");
            String name1 = this.eat(token.getVal());
            this.tabel.define(type, name1, Kind.VAR);
        }
        this.eat(";");
    }

    private void compileStatements() {
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
        this.eat("do");
        this.compileSubroutineCall();
    }

    public void compileLet() {

    }

    public void compileWhile() {
    }

    public void compileReturn() {
        this.eat("return");
        String type = this.eat(token.getVal());
        if (type.equals(";")) {
            this.vmWrite.writeReturn("void");
        }
    }

    public void compileIf() {

    }

    private void compileExpression() {
        this.compileTerm();
        while (Arrays.asList(opArray).contains(token.getVal())) {
            String op = this.eat(token.getVal());
            this.compileTerm();
            this.vmWrite.writeArithmetic(op);
        }
    }

    public void compileTerm() {
        if (token.getVal().equals("(") && token.getType().equals(TokenType.SYMBOL)) {
            this.eat("(");
            this.compileExpression();
            this.eat(")");
        }
        if (token.getType().equals(TokenType.INT_CONST)) {
            this.vmWrite.writePush("constant", Integer.valueOf(token.getVal()));
            this.eat(token.getVal());
        }
//        if (Arrays.asList(unaryArray).contains(token.getVal())) {
//            this.eat(token.getVal());
//            this.compileTerm();
//        }
//        this.eat(token.getVal());
//        if (token.getVal().equals(".") && token.getType().equals(TokenType.SYMBOL)) {
//            this.eat(".");
//            this.eat(token.getVal());
//            this.eat("(");
//            this.compileExpressionList();
//            this.eat(")");
//        }
//        if (token.getVal().equals("[") && token.getType().equals(TokenType.SYMBOL)) {
//            this.eat("[");
//            this.compileExpression();
//            this.eat("]");
//        }
    }

    private Integer compileExpressionList() {
        int n = 0;
        if (token.getVal().equals(")") && token.getType().equals(TokenType.SYMBOL)) {
            return n;
        }
        this.compileExpression();
        n += 1;
        while (token.getVal().equals(",")) {
            token = tokenizer.advance();
            this.compileExpression();
            n += 1;
        }
        return n;
    }

    private String eat(String string) {
        if (!token.getVal().equals(string)) {
            throw new RuntimeException("unexpect token" + token);
        } else {
            token = tokenizer.advance();
        }
        return string;
    }

    public void firstToken() {
        token = tokenizer.advance();
    }



}
