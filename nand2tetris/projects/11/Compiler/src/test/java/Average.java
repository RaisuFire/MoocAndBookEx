import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class Average {
    private File inFile;
    private File outFile;

    @Before
    public void before() {
        inFile = new File("/Users/zihuaye/Documents/GitHub/MoocAndBookEx/nand2tetris/projects/11/Average/Main.jack");
        outFile = new File("/Users/zihuaye/Documents/GitHub/MoocAndBookEx/nand2tetris/projects/11/Average/Main1.VM");
    }

    @Test
    public void test() throws IOException {
        JackTokenizer tokenizer = Util.initToken(inFile);
        VMWrite vmWrite = new VMWrite(outFile);
        CompilationEngine1 engine = new CompilationEngine1(tokenizer, vmWrite);
        engine.firstToken();
        engine.compileClass();
    }
}
