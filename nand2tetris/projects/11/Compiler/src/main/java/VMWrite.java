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
        this.println("push " + segment + " " + index);
    }

    public void writePop(String segment, Integer index) {
        this.println("pop " + segment + " " + index);
    }

    public void writeArithmetic(String command) {
        if (command.equals("<")) {
            this.println("lt");
        } else if (command.equals(">")) {
            this.println("gt");
        } else if (command.equals("&")) {
            this.println("and");
        } else if (command.equals("+")) {
            this.println("add");
        } else if (command.equals("-")) {
            this.println("sub");
        } else if (command.equals("*")) {
            this.println("call Math.multiply 2");
        } else if (command.equals("/")) {
            this.println("call Math.divide 2");
        } else if (command.equals("=")) {
            this.println("eq");
        } else if (command.equals("|")) {
            this.println("or");
        }
    }

    public void writeLabel(String label) {
        this.println("label " + label);
    }

    public void writeGoto() {
        this.println("return");
    }

    public void writeIf() {

    }

    public void writeFunction(String className, String methodName, Integer numArgs) {
        this.println("function " + className + "." + methodName + " " + numArgs);
    }

    public void writeCall(String className, String methodName, Integer numArgs) {
        this.println("call " + className + "." + methodName + " " + numArgs);
    }

    public void writeReturn(String type) {
        if ("void".equals(type)) {
            this.println(
                    "pop temp 0\n" +
                    "push constant 0\n" +
                    "return\n"
            );
        }
    }

    public void close() {
        this.writer.close();
    }

    private void println(String str) {
        this.writer.println(str);
        this.writer.flush();
    }

}
