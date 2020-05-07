import entity.CharUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Util {
    public static String skipWhileSpace(String str) {
        String newStr = "";
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!CharUtil.isWhiteSpace(c)){
                newStr += c;
            }
        }
        return newStr;
    }

    public static boolean compareStr(String str1, String str2) {
        for (int i = 0; i < str1.length(); i++) {
            char c1 = str1.charAt(i);
            char c2 = str2.charAt(i);
            if (c1 != c2) {
                System.out.println(c1 + "  " + c2);
                System.out.println(i);
                return false;
            }
        }
        return true;
    }

    public static JackTokenizer initToken(File inFile) throws IOException {
        byte[] bytesArray = new byte[(int) inFile.length()];
        FileInputStream fis = new FileInputStream(inFile);
        fis.read(bytesArray); //read file into bytes[]
        fis.close();
        return new JackTokenizer(new String(bytesArray));
    }
}
