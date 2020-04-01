import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    String find(Pattern pattern, String str) {
        Matcher matcher = pattern.matcher(str);
        return matcher.find()
                ? matcher.group(0) : null;
    }


    public static void main(String[] args) throws IOException {
	    // write your code here

//        Main main = new Main();
//        String text = "/** dsfsdf 8*/";
//        Pattern pattern = Pattern.compile("[/**]?|[*/]");
//        System.out.println(main.find(pattern, text));


        String a = "wfsdfsdfds";
        a.substring(2, 4);
        System.out.print(a);

    }
}
