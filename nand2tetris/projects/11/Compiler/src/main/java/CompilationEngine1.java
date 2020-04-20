import constant.Kind;
import constant.TokenType;
import entity.Symbol;
import entity.Token;

public class CompilationEngine1 {
    private JackTokenizer tokenizer;
    private SymbolTabel tabel;
    private VMWrite vmWrite;
    private Token token;

    private String className = "";
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
        String type = this.argFromToken();
        String name = this.argFromToken();
        this.tabel.define(name, type, kind);
        while (token.getVal().equals(",")) {
            this.eat(",");
            String name1 = this.argFromToken();
            this.tabel.define(name1, type, kind);
        }
        this.eat(";");
    }

    private void compileSubroutine() {
        assert (token.getVal().equals("function") || token.getVal().equals("method") || token.getVal().equals("constructor"));
        tabel.startSubroutine();
        this.eat(token.getVal());
        this.eat(token.getVal());
        subroutineName = token.getVal();
        this.eat(token.getVal());
        this.eat("(");
        this.compileParameterList();
        this.eat(")");
        this.compileSubroutineBody();
    }

    private void compileSubroutineBody() {
        this.eat("{");
        while (token.getVal().equals("var")) {
            this.compileVarDec();
        }

        this.compileStatements();

        this.eat("}");

    }


    public void compileSubroutineCall() {

    }

    public void compileParameterList() {
        if (token.getVal().equals(")")) {
            return;
        }
        String type = this.argFromToken();
        String name = this.argFromToken();
        this.tabel.define(name, type, Kind.ARGUMENT);
        while (token!= null && token.getVal().equals(",")) {
            this.eat(",");
            String type1 = this.argFromToken();
            String name1 = this.argFromToken();
            this.tabel.define(name1, type1, Kind.ARGUMENT);
        }
    }

    private void compileVarDec() {
        this.eat("var");
        String type = this.argFromToken();
        String name = this.argFromToken();
        this.tabel.define(name, type, Kind.VAR);
        while (token.getVal().equals(",")) {
            this.eat(",");
            String name1 = this.argFromToken();
            this.tabel.define(type, name1, Kind.VAR);
        }
        this.eat(";");

    }

    private void compileStatements() {

    }

    public void compileDo() {

    }

    public void compileLet() {

    }

    public void compileWhile() {
    }

    public void compileReturn() {
    }

    public void compileIf() {

    }

    private void compileExpression() {

    }

    public void compileTerm() {

    }

    public void compileExpressionList() {

    }

    private String argFromToken() {
        String s = token.getVal();
        this.eat(s);
        return s;
    }

    private void eat(String string) {
        if (!token.getVal().equals(string)) {
            throw new RuntimeException("unexpect token" + token);
        } else {
            token = tokenizer.advance();
        }
    }

    public void firstToken() {

    }



}
