import constant.CommandType;

import java.io.File;

public class VMtranslator {

    private Parser parser;
    private CodeWriter writer;

    public VMtranslator(Parser parser, CodeWriter codeWriter) {
        this.parser = parser;
        this.writer = codeWriter;
    }

    public void translate() throws Exception {

        while (parser.hasMoreCommands()) {
            if (parser.asdvance()) {
                if (CommandType.C_ARITHMETIC.equals(parser.getType())) {
                    writer.writeArithmetic(parser.getCommand());
                } else if (CommandType.C_PUSH.equals(parser.getType()) || CommandType.C_POP.equals(parser.getType())) {
                    writer.writePushPop(parser.getType(), parser.getCommand(), parser.getArg1(), parser.getArg2());
                } else if (CommandType.C_LABEL.equals(parser.getType())) {
                    writer.writeLabel(parser.getArg1());
                } else if (CommandType.C_GOTO.equals(parser.getType())) {
                    writer.writeGoto(parser.getArg1());
                } else if (CommandType.C_IF.equals(parser.getType())) {
                    writer.writeIf(parser.getArg1());
                } else if (CommandType.C_FUNCTION.equals(parser.getType())) {
                    writer.writeFunction(parser.getArg1(), parser.getArg2());
                }

                else {
                    throw new Exception("unexpect command");
                }

                writer.out.println();
                writer.out.flush();
            }
        }
        writer.colse();
    }


    public static void main(String[] args) throws Exception {
        String dir = System.getProperty("user.dir");

        String inFile = dir +  "/../ProgramFlow/FibonacciSeries/FibonacciSeries.vm";
        String outFile = dir + "/../ProgramFlow/FibonacciSeries/FibonacciSeries.asm";
        Parser parser = new Parser(new File(inFile));
        CodeWriter writer = new CodeWriter(new File(outFile));
        VMtranslator vMtranslator = new VMtranslator(parser, writer);
        vMtranslator.translate();

    }

}



