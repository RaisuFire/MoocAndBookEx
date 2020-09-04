import constant.TokenType;
import org.junit.Before;
import org.junit.Test;

public class TokenTest {

    @Before
    public void before() {

    }

    @Test
    public void Test() {
//        symbolTest();
//        whiteSpaceTest();
        letTest();
    }

    public void symbolTest() {
        String str = "{}()[].,;+-*/&|<>=~";
        JackTokenizer tokenizer = new JackTokenizer(str);
        for (int i = 0; i < str.length(); i++) {
            Token t1 = new Token(TokenType.SYMBOL, Character.toString(str.charAt(i)));
            Token t2 = tokenizer.advance();
            assert (t1.equals(t2));
        }
    }

    public void whiteSpaceTest() {
        String str = "    \r\n \n\r /** fsdfsd \n sdfsdf */ // dsfsdfaskdfs";
        JackTokenizer tokenizer = new JackTokenizer(str);
        Token token = tokenizer.advance();
        assert (token == null);
    }

    public void letTest() {
        String str = "let count = count + 1;";
        JackTokenizer tokenizer = new JackTokenizer(str);
        while (tokenizer.hasMoreTokens()) {
            Token token = tokenizer.advance();
            System.out.print(token);
        }


    }





}
