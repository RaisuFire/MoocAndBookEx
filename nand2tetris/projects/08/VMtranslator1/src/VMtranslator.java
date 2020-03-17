import constant.CommandType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class VMtranslator {

    private List<Parser> parsers = new ArrayList<>();
    private CodeWriter writer;

    public VMtranslator(String path, String asmFile) throws FileNotFoundException {
        File file = new File(path);
        if (file.isDirectory()) {
            for (File f: file.listFiles()) {
                if (f.isFile() && f.getName().endsWith(".vm")) {
                    Parser p = new Parser(f);
                    this.parsers.add(p);
                }
            }
        }
        if (file.isFile() && file.getName().endsWith(".vm")) {
                Parser p = new Parser(file);
                this.parsers.add(p);
        }

        this.writer = new CodeWriter(new File(asmFile));
    }

    public void translate() throws Exception {
        writer.write(writer.writeInit());

        for (Parser p: parsers) {
            this.transOneFile(p);
        }

        writer.colse();
    }


    public void transOneFile(Parser parser) throws Exception {
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
                writer.write(code);
            }
        }
    }


    public static void main(String[] args) throws Exception {

        String inFile = "/Users/zihuaye/Documents/GitHub/MoocAndBookEx/nand2tetris/projects/08/FunctionCalls/FibonacciElement";
        String outFile = "/Users/zihuaye/Documents/GitHub/MoocAndBookEx/nand2tetris/projects/08/FunctionCalls/FibonacciElement/FibonacciElement.asm";

        VMtranslator vMtranslator = new VMtranslator(inFile, outFile);
        vMtranslator.translate();
    }

}



