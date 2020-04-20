import entity.CharSeq;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class statementTest {
    private JackTokenizer tokenizer;
    private CompilationEngine engine;

    @Before
    public void before() throws FileNotFoundException {
        tokenizer = new JackTokenizer("");
        engine = new CompilationEngine(tokenizer);
    }

    @Test
    public void Test() {
        List<String> statements = statement();
        for (String s: statements) {
            this.compileStatement(s);
        }
    }

    public List<String> statement() {
        List<String> statments = new ArrayList<String>();

        String returnStr = "return;";
        String ifStr = "if (i) {\n" +
                "            let s = i;\n" +
                "            let s = j;\n" +
                "        }\n" +
                "        else {              // There is no else keyword in the Square files.\n" +
                "            let i = i;\n" +
                "            let j = j;\n" +
                "        }";
        String doStr = "do Output.printString(a, b);";
        String whileStr = "while ( count < 100) { let count = count + 1; }";
        String letStr = "let count = count + 1;";
        statments.add(returnStr);
        statments.add(ifStr);
        statments.add(doStr);
        statments.add(whileStr);
        statments.add(letStr);
        return statments;
    }


    public List<String> outputStatement() {
        List<String> outXml = new ArrayList<String>();
        String returnXml= "";
        String ifXml = "";
        String doXml= "";
        String whileXml= "<whileStatement>\n" +
                "<keyword>while</keyword>\n" +
                "<symbol>(</symbol>\n" +
                "<expression>\n" +
                "<term>\n" +
                "<identifier>count</identifier>\n" +
                "</term>\n" +
                "<symbol><</symbol>\n" +
                "<term>\n" +
                "<integerConstant>100</integerConstant>\n" +
                "</term>\n" +
                "</expression>\n" +
                "<symbol>)</symbol>\n" +
                "<symbol>{</symbol>\n" +
                "<letStatement>\n" +
                "<keyword>let</keyword>\n" +
                "<term>\n" +
                "<identifier>count</identifier>\n" +
                "</term>\n" +
                "<symbol>=</symbol>\n" +
                "<expression>\n" +
                "<term>\n" +
                "<identifier>count</identifier>\n" +
                "</term>\n" +
                "<symbol>+</symbol>\n" +
                "<term>\n" +
                "<integerConstant>1</integerConstant>\n" +
                "</term>\n" +
                "</expression>\n" +
                "<symbol>;</symbol>\n" +
                "</letStatement>\n" +
                "<symbol>}</symbol>\n" +
                "</whileStatement>";;
        String letXml= "<letStatement>\n" +
                "<keyword> let </keyword>\n" +
                "<term>\n" +
                "<identifier> count </identifier>\n" +
                "</term>\n" +
                "<symbol> = </symbol>\n" +
                "<expression>\n" +
                "<term>\n" +
                "<identifier> count </identifier>\n" +
                "</term>\n" +
                "<symbol> + </symbol>\n" +
                "<term>\n" +
                "<integerConstant>1</integerConstant>\n" +
                "</term>\n" +
                "</expression>\n" +
                "<symbol> ; </symbol>\n" +
                "</letStatement>";

        outXml.add(returnXml);
        outXml.add(ifXml);
        outXml.add(doXml);
        outXml.add(whileXml);
        outXml.add(letXml);
        return outXml;
    }




    public String compileStatement(String str) {
        tokenizer.setText(new CharSeq(str));
        engine.setXml("");
        engine.firstToken();
        engine.compileStatements();
        System.out.print(engine.getXml());
        System.out.println();
        return engine.getXml();
    }

}
