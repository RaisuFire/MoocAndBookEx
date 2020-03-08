import constant.CommandType;
import exception.ParserException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Parser {
    public static final List<String> ArithmeticOps = new ArrayList<String>(Arrays.asList("add", "sub", "neg", "eq", "gt", "lt", "and", "or", "not"));

    private Scanner scanner;

    private CommandType type;
    private String command;
    private String arg1;
    private Integer arg2;

    public Parser(File file) throws FileNotFoundException {
        this.scanner = new Scanner(file);
    }

    public boolean hasMoreCommands() {
        return this.scanner.hasNext();
    }

    public boolean asdvance() {
        if (this.hasMoreCommands()) {
            String line = this.scanner.nextLine();
            if (isCommandLine(line)) {
                this.argsFromLine(line);
                return true;
            }
        }
        return false;
    }

    private void argsFromLine(String line) {
        List<String> strs = this.preProcess(line);
        String s = strs.get(0);
        if (ArithmeticOps.contains(s)) {
            this.type = CommandType.C_ARITHMETIC;
            this.command = s;
        } else if (s.equals("push")) {
            this.type = CommandType.C_PUSH;
            this.command = s;
            this.arg1 = strs.get(1);
            this.arg2 = Integer.valueOf(strs.get(2));
        } else if (s.equals("pop")) {
            this.type = CommandType.C_POP;
            this.command = s;
            this.arg1 = strs.get(1);
            this.arg2 = Integer.valueOf(strs.get(2));
        } else if (s.equals("label")){
            this.type = CommandType.C_LABEL;
            this.command = s;
            this.arg1 = strs.get(1);
        } else if (s.equals("goto")) {
            this.type = CommandType.C_GOTO;
            this.command = s;
            this.arg1 = strs.get(1);
        } else if (s.equals("if-goto")) {
            this.type = CommandType.C_IF;
            this.command = s;
            this.arg1 = strs.get(1);
        } else if (s.equals("function")) {
            this.type = CommandType.C_FUNCTION;
            this.command = s;
            this.arg1 = strs.get(1);
            this.arg2 = Integer.valueOf(strs.get(2));
        } else if (s.equals("call")) {
            this.type = CommandType.C_CALL;
            this.command = s;
            this.arg1 = strs.get(1);
            this.arg2 = Integer.valueOf(strs.get(2));
        } else if (s.equals("return")) {
            this.type = CommandType.C_RETURN;
            this.command = s;
        } else {
            throw new ParserException("unexpected parser command: " + line);
        }
    }


    // 分解输入命令，得到指令和参数
    private List<String> preProcess(String line) {
        String[] strs = line.split(" +");
        return Arrays.asList(strs).stream().map(String::trim)
                .collect(Collectors.toList());
    }

    // 处理注释和空白行
    private boolean isCommandLine(String line) {
        return line.length() >= 2 && !"//".equals(line.substring(0, 2));
    }

    public CommandType getType() {
        return type;
    }

    public void setType(CommandType type) {
        this.type = type;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    public Integer getArg2() {
        return arg2;
    }

    public void setArg2(Integer arg2) {
        this.arg2 = arg2;
    }
}
