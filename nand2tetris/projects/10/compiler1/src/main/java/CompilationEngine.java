import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class CompilationEngine {
    private JackTokenizer tokenizer;
    private PrintWriter writer;

    public CompilationEngine(JackTokenizer tokenizer, File outFile) throws FileNotFoundException {
        this.tokenizer = tokenizer;
        this.writer = new PrintWriter(outFile);
    }



}
