import constant.CommandType;
import constant.Jump;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class CodeWriter {
    public Map<String, String> segment1 = new HashMap<>();

    public PrintWriter out;
    private Integer jumpNum = 0;

    public CodeWriter(File file) throws FileNotFoundException {
        this.out = new PrintWriter(file);
        this.initSegment1();
    }

    private void initSegment1() {
        this.segment1.put("local", "LCL");
        this.segment1.put("argument", "ARG");
        this.segment1.put("this", "THIS");
        this.segment1.put("that", "THAT");
        this.segment1.put("temp", "5");
    }

    // Arithmetic
    public void writeArithmetic(String command) throws Exception {
        String aCode = "";
        if ("add".equals(command)) {
            aCode = this.popTwoElm() + "M=D+M\n";
        } else if ("sub".equals(command)) {
            aCode = this.popTwoElm() + "M=M-D\n";
        } else if ("neg".equals(command)) {
            aCode = this.negAsm();
        } else if ("eq".equals(command)) {
            aCode = this.popTwoElm() + this.compareformat(jumpNum.toString(), Jump.JEQ.getJump());
            this.jumpNum++;
        } else if ("gt".equals(command)) {
            aCode = this.popTwoElm() + this.compareformat(jumpNum.toString(), Jump.JLT.getJump());
            this.jumpNum++;
        } else if ("lt".equals(command)) {
            aCode = this.popTwoElm() + this.compareformat(jumpNum.toString(), Jump.JGT.getJump());
            this.jumpNum++;
        } else if ("and".equals(command)) {
            aCode = this.popTwoElm() + "M=M&D\n";
        } else if ("or".equals(command)) {
            aCode = this.popTwoElm() + "M=D|M\n";
        } else if ("not".equals(command)) {
            aCode = this.notAsm();
        } else {
            throw new Exception("unexpected arithmetic command");
        }
        this.out.print(aCode);
        this.out.flush();
    }

    // PushPop
    public void writePushPop(CommandType commandType, String command, String arg1, Integer arg2) throws Exception {
        String code = "";
        if (CommandType.C_PUSH.equals(commandType)) {
            code = this.writePush( arg1, arg2);
        } else if (CommandType.C_POP.equals(commandType)) {
            code = this.writePop( arg1, arg2);
        }
        this.out.print(code);
        this.out.flush();
    }

    // init
    public void writeInit() {


    }

    // label
    public void writeLabel(String label) {
        this.out.print(MessageFormat.format("({0})", label));
        this.out.flush();
    }

    // goto
    public void writeGoto(String label) {
        String code = "@" + label + "\n" + "0;JMP\n";
        this.out.print(code);
        this.out.flush();
    }

    // if
    public void writeIf(String label) {
        String code = "@SP\n" + "AM=M-1\n" + "D=M\n" + "@" + label + "\n" +  "D;JGT\n";
        this.out.print(code);
        this.out.flush();
    }

    // call
    public void writeCall(String functionName, Integer numArgs) {

    }

    // return
    public void writeReturn() {

    }

    // function
    public void writeFunction(String functionName, Integer numArgs) {

    }



    private String writePop(String arg1, Integer arg2) {
        String code = "";
        if ("constant".equals(arg1)) {
//            code = this.popConstant(arg2);
        } else if (this.segment1.containsKey(arg1)) {
            code = this.popSegment1(this.segment1.get(arg1), arg2);
        } else if ("static".equals(arg1)) {
            code = this.popSegment2(String.valueOf((arg2+16)));
        } else if ("pointer".equals(arg1)) {
            if (arg2 == 0) {
                code = this.popSegment2("THIS");
            } else if (arg2 == 1) {
                code = this.popSegment2("THAT");
            }
        }
        return code;
    }

    private String writePush(String arg1, Integer arg2) throws Exception {
        String code = "";
        if ("constant".equals(arg1)) {
            code = this.pushConstant(arg2);
        } else if (this.segment1.containsKey(arg1)) {
            code = this.pushSement1(this.segment1.get(arg1), arg2);
        } else if ("static".equals(arg1)) {
            code = this.pushSement2(String.valueOf((arg2+16)));
        } else if ("pointer".equals(arg1)) {
            if (arg2 == 0) {
                code = this.pushSement2("THIS");
            } else if (arg2 == 1) {
                code = this.pushSement2("THAT");
            }
        }

        else {
            throw new Exception("unexpected push command");
        }
        return code;
    }

    private String popSegment2(String index) {
        return "@" + index + "\nD=A\n" + "@R13\n" + "M=D\n"  + "@SP\n"+ "AM=M-1\n"+ "D=M\n"+ "@R13\n"+ "A=M\n"+ "M=D\n";
    }

    private String pushSement2(String index) {
        return "@" + index + "\nD=M\n" + "@SP\n" + "A=M\n" + "M=D\n" + "@SP\n" + "M=M+1\n";
    }

    private String popSegment1(String segment, Integer index) {
        return "@" + index + "\n" + "D=A\n" + "@" + segment + "\n" + "D=M+D\n" + "@R13\n" + "M=D\n" + "@SP\n" + "AM=M-1\n" + "D=M\n"
                + "@R13\n" + "A=M\n" + "M=D\n";
    }

    private String pushSement1(String segment, Integer index) {
        return "@" + index + "\n" + "D=A\n" + "@" + segment + "\n" + "D=M+D\n" + "A=D\n" + "D=M\n" +
                "@SP\n" + "A=M\n" +"M=D\n" + "@SP\n" + "M=M+1\n";
    }

    private String pushConstant(Integer arg2) {
        return "@" + arg2 + "\n" + "D=A\n" + "@SP\n" + "A=M\n" + "M=D\n" + "@SP\n" + "M=M+1\n";
    }

    private String compareformat(String jumpNum, String jump) {
        return
                "D=D-M\n" +
                "@TRUE" + jumpNum + "\n" +
                "D;" + jump + "\n" +
                "@SP\n" +
                "A=M-1\n" +
                "M=0\n" +
                "@CONTINUE" + jumpNum + "\n" +
                "0;JMP\n" +
                "(TRUE" + jumpNum + ")\n" +
                "@SP\n" +
                "A=M-1\n" +
                "M=-1\n" +
                "(CONTINUE" + jumpNum + ")\n";
    }

    private String popTwoElm() {
        return "@SP\n" + "AM=M-1\n" + "D=M\n" + "A=A-1\n";
    }

    private String notAsm() {
        return "@SP\n" + "A=M-1\n" + "M=!M\n";
    }

    private String negAsm() {
        return "@SP\n" + "A=M\n" + "M=-M\n";
    }

    public void colse() {
        this.out.close();
    }

}
