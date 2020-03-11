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
            this.porpertiesFromLine(CommandType.C_ARITHMETIC, s, null, null);
        } else if (s.equals("push")) {
            this.porpertiesFromLine(CommandType.C_PUSH, s, strs.get(1), Integer.valueOf(strs.get(2)));
        } else if (s.equals("pop")) {
            this.porpertiesFromLine(CommandType.C_POP, s, strs.get(1), Integer.valueOf(strs.get(2)));
        } else if (s.equals("label")){
            this.porpertiesFromLine(CommandType.C_LABEL, s, strs.get(1), null);
        } else if (s.equals("goto")) {
            this.porpertiesFromLine(CommandType.C_GOTO, s, strs.get(1), null);
        } else if (s.equals("if-goto")) {
            this.porpertiesFromLine(CommandType.C_IF, s, strs.get(1), null);
        } else if (s.equals("function")) {
            this.porpertiesFromLine(CommandType.C_FUNCTION, s,  strs.get(1), Integer.valueOf(strs.get(2)));
        } else if (s.equals("call")) {
            this.porpertiesFromLine(CommandType.C_CALL, s, strs.get(1), Integer.valueOf(strs.get(2)));
        } else if (s.equals("return")) {
            this.porpertiesFromLine(CommandType.C_RETURN, s, null, null);
        } else {
            throw new ParserException("unexpected parser command: " + line);
        }
    }

    private void porpertiesFromLine(CommandType type, String command, String arg1, Integer arg2) {
        this.type = type;
        this.command = command;
        if (arg1 != null) {
            this.arg1 = arg1;
        }
        if (arg2 != null) {
            this.arg2 = arg2;
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
