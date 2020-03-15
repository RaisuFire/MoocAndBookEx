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
                String code = "";
                if (CommandType.C_ARITHMETIC.equals(parser.getType())) {
                    code = writer.writeArithmetic(parser.getCommand());
                } else if (CommandType.C_PUSH.equals(parser.getType()) || CommandType.C_POP.equals(parser.getType())) {
                    code = writer.writePushPop(parser.getType(), parser.getCommand(), parser.getArg1(), parser.getArg2());
                } else if (CommandType.C_LABEL.equals(parser.getType())) {
                    code = writer.writeLabel(parser.getArg1());
                } else if (CommandType.C_GOTO.equals(parser.getType())) {
                    code = writer.writeGoto(parser.getArg1());
                } else if (CommandType.C_IF.equals(parser.getType())) {
                    code = writer.writeIf(parser.getArg1());
                } else if (CommandType.C_FUNCTION.equals(parser.getType())) {
                    code = writer.writeFunction(parser.getArg1(), parser.getArg2());
                } else if (CommandType.C_CALL.equals(parser.getType())) {
                    code = writer.writeCall(parser.getArg1(), parser.getArg2());
                } else if (CommandType.C_RETURN.equals(parser.getType())) {
                    code = writer.writeReturn();
                } else {
                    throw new Exception("unexpect command");
                }
                writer.out.print(code);
                writer.out.println();
                writer.out.flush();
            }
        }
        writer.colse();
    }


    public static void main(String[] args) throws Exception {
        String dir = System.getProperty("user.dir");

        String inFile = dir +  "/../FunctionCalls/SimpleFunction/SimpleFunction.vm";
        String outFile = dir + "/../FunctionCalls/SimpleFunction/SimpleFunction.asm";
        Parser parser = new Parser(new File(inFile));
        CodeWriter writer = new CodeWriter(new File(outFile));
        VMtranslator vMtranslator = new VMtranslator(parser, writer);
        vMtranslator.translate();
    }

}



