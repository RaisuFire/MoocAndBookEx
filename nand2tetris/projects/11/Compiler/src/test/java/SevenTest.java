import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SevenTest {
    private File inFile;
    private File outFile;

    @Before
    public void before() {
        inFile = new File("/Users/zihuaye/Documents/GitHub/MoocAndBookEx/nand2tetris/projects/11/Seven/Main.jack");
        outFile = new File("/Users/zihuaye/Documents/GitHub/MoocAndBookEx/nand2tetris/projects/11/Seven/Main1.VM");
    }

    @Test
    public void test() throws IOException {
        JackTokenizer tokenizer = this.initToken(inFile);
        VMWrite vmWrite = new VMWrite(outFile);
        CompilationEngine1 engine = new CompilationEngine1(tokenizer, vmWrite);
        engine.firstToken();
        engine.compileClass();
    }


    private JackTokenizer initToken(File inFile) throws IOException {
        byte[] bytesArray = new byte[(int) inFile.length()];
        FileInputStream fis = new FileInputStream(inFile);
        fis.read(bytesArray); //read file into bytes[]
        fis.close();
        return new JackTokenizer(new String(bytesArray));
    }
}
