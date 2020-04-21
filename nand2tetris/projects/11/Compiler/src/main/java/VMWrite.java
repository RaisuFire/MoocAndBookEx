import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class VMWrite {
    public static final String[] segments = new String[] {
            "CONST", "ARG", "LOCAL", "STATIC", "THIS", "THAT", "POINTER", "TEMPfff"};

    private PrintWriter writer;

    public VMWrite(File file) throws FileNotFoundException {
        this.writer = new PrintWriter(file);
    }

    public void writePush(String segment, Integer index) {
        writer.println("push " + segment + " " + index);
    }

    public void writePop(String segment, Integer index) {
        writer.println("pop " + segment + " " + index);
    }

    public void writeArithmetic(String command) {
        writer.println(command);
    }

    public void writeLabel(String label) {
        writer.println("label " + label);
    }

    public void writeGoto() {

    }

    public void writeIf() {

    }

    public void writeCall(String ClassName, String methodName, Integer args) {
        writer.println("call");
    }

    public void writeReturn() {

    }

    public void close() {

    }

}
