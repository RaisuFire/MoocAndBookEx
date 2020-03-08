import constant.CommandType;

import java.io.File;

public class VMtranslator {

    private Parser parser;
    private CodeWriter codeWriter;

    public VMtranslator(Parser parser, CodeWriter codeWriter) {
        this.parser = parser;
        this.codeWriter = codeWriter;
    }

    public void translate() throws Exception {
        while (this.parser.hasMoreCommands()) {
            if (this.parser.asdvance()) {
                if (CommandType.C_ARITHMETIC.equals(parser.getType())) {
                    this.codeWriter.writeArithmetic(parser.getCommand());
                } else if (CommandType.C_PUSH.equals(parser.getType()) || CommandType.C_POP.equals(parser.getType())) {
                    this.codeWriter.writePushPop(parser.getType(), parser.getCommand(), parser.getArg1(), parser.getArg2());
                } else if (CommandType.C_LABEL.equals(parser.getType())) {
                    this.codeWriter.writeLabel(parser.getArg1());
                } else if (CommandType.C_GOTO.equals(parser.getType())) {
                    this.codeWriter.writeGoto(parser.getArg1());
                } else if (CommandType.C_IF.equals(parser.getType())) {
                    this.codeWriter.writeIf(parser.getArg1());
                }

                else {
                    throw new Exception("unexpect command");
                }

                this.codeWriter.out.println();
                this.codeWriter.out.flush();
            }
        }
        this.codeWriter.colse();
    }


    public static void main(String[] args) throws Exception {
        String inFile = "G:\\MOOC\\Nand2\\nand2tetris\\projects\\08\\ProgramFlow\\FibonacciSeries\\FibonacciSeries.vm";
        String outFile = "G:\\MOOC\\Nand2\\nand2tetris\\projects\\08\\ProgramFlow\\FibonacciSeries\\FibonacciSeries.asm";

        Parser parser = new Parser(new File(inFile));
        CodeWriter writer = new CodeWriter(new File(outFile));
        VMtranslator vMtranslator = new VMtranslator(parser, writer);
        vMtranslator.translate();

    }
}
