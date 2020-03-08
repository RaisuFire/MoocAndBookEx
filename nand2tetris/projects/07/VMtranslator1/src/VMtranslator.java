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
                } else {
                    throw new Exception("unexpect command");
                }

                this.codeWriter.out.println();
                this.codeWriter.out.flush();
            }
        }
        this.codeWriter.colse();
    }


    public static void main(String[] args) throws Exception {
//        String inFileName = args[0];
//        String outFileName = inFileName.substring(0, inFileName.length()-3) + ".asm";
//
//        File vmFile = new File(inFileName);
//        Parser parser = new Parser(vmFile);
//
//        File asmFile = new File(outFileName);
//        CodeWriter writer = new CodeWriter(asmFile);

//        String inFile = System.getProperty("user.dir") + "\\resource\\StackTests.vm";
//        String outFile = System.getProperty("user.dir") + "\\resource\\StackTest.asm";

        String inFile = "G:\\MOOC\\Nand2\\nand2tetris\\projects\\07\\MemoryAccess" + "\\PointerTest\\PointerTest.vm";
        String outFile = "G:\\MOOC\\Nand2\\nand2tetris\\projects\\07\\MemoryAccess" + "\\PointerTest\\PointerTest.asm";

        Parser parser = new Parser(new File(inFile));
        CodeWriter writer = new CodeWriter(new File(outFile));
        VMtranslator vMtranslator = new VMtranslator(parser, writer);
        vMtranslator.translate();

    }
}
