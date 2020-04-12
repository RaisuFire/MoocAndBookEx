import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class classTest {
    private JackTokenizer tokenizer;
    private CompilationEngine engine;

    @Before
    public void before() throws FileNotFoundException {
        tokenizer = new JackTokenizer("");
        engine = new CompilationEngine(tokenizer);
    }

    @Test
    public void Test() throws IOException {
        classTest();

    }

    public void classTest() throws IOException {
        String str  = "class Main {\n" +
                "    function void main() {\n" +
                "        var Array a;\n" +
                "        var int length;\n" +
                "        var int i, sum;\n" +
                "\t\n" +
                "\tlet length = Keyboard.readInt(\"HOW MANY NUMBERS? \");\n" +
                "\tlet a = Array.new(length);\n" +
                "\tlet i = 0;\n" +
                "\t\n" +
                "\twhile (i < length) {\n" +
                "\t    let a[i] = Keyboard.readInt(\"ENTER THE NEXT NUMBER: \");\n" +
                "\t    let i = i + 1;\n" +
                "\t}\n" +
                "\t\n" +
                "\tlet i = 0;\n" +
                "\tlet sum = 0;\n" +
                "\t\n" +
                "\twhile (i < length) {\n" +
                "\t    let sum = sum + a[i];\n" +
                "\t    let i = i + 1;\n" +
                "\t}\n" +
                "\t\n" +
                "\tdo Output.printString(\"THE AVERAGE IS: \");\n" +
                "\tdo Output.printInt(sum / length);\n" +
                "\tdo Output.println();\n" +
                "\t\n" +
                "\treturn;\n" +
                "    }\n" +
                "}\n";
        tokenizer.setText(new CharSeq(str));
        engine.firstToken();
        engine.compileClass();
        System.out.println(engine.getXml());

        File file = new File("/Users/zihuaye/Documents/GitHub/MoocAndBookEx/nand2tetris/projects/10/ArrayTest/Main.xml");
        byte[] bytesArray = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(bytesArray); //read file into bytes[]
        fis.close();
        String targetXml = new String(bytesArray);
        assert (Util.compareStr(Util.skipWhileSpace(targetXml), Util.skipWhileSpace(engine.getXml())));
    }

    public void subTest() {
        List<String> statements = statement();
        for (String s: statements) {
            this.compile(s);
        }
    }


    public List<String> statement() {
        List<String> statments = new ArrayList<String>();
        String varStr = "var Array a;";
        String clsVarStr = "field int x, y;";
        String paramStr = "int a, boolean c, char d, Hello e";
//        statments.add(varStr);
//        statments.add(clsVarStr);
        statments.add(paramStr);
        return statments;
    }

    public String compile(String str) {
        tokenizer.setText(new CharSeq(str));
        engine.setXml("");
        engine.firstToken();
        engine.compileParameterList();
        System.out.print(engine.getXml());
        System.out.println();
        return engine.getXml();
    }

}
