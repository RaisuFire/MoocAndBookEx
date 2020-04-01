import constant.TokenType;
import org.junit.Before;
import org.junit.Test;

public class TokenTest {

    @Before
    public void before() {

    }

//    @Test
//    public void symbolTest() {
//        String str = "{}()[].,;+-*/&|<>=~";
//        JackTokenizer tokenizer = new JackTokenizer(str);
//        for (int i = 0; i < str.length(); i++) {
//            Token t1 = new Token(TokenType.SYMBOL, Character.toString(str.charAt(i)));
//            Token t2 = tokenizer.advance();
//            assert (t1.equals(t2));
//        }
//    }

    @Test
    public void whiteSpaceTest() {
        String str = "    \r\n \n\r /** fsdfsd \n sdfsdf */";
        JackTokenizer tokenizer = new JackTokenizer(str);
        Token token = tokenizer.advance();
        System.out.println();
    }






}
