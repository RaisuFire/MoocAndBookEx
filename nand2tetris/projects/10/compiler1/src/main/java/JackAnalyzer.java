import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JackAnalyzer {
    private JackTokenizer tokenizer;
    private CompilationEngine engine;
    private PrintWriter writer;

    private List<JackTokenizer> tokenizers = new ArrayList<JackTokenizer>();
    private List<File> files = new ArrayList<File>();


    public JackAnalyzer(String path) throws IOException {
        File file = new File(path);
        if (file.isDirectory()) {
            for (File f: file.listFiles()) {
                if (f.isFile() && f.getName().endsWith(".jack")) {
                    files.add(f);
                }
            }
        }
        if (file.isFile() && file.getName().endsWith(".jack")) {
            files.add(file);
        }
    }

    public void writeXMLFiles() throws IOException {
        for (File file: files) {
            System.out.println(file.toString());
            JackTokenizer tokenizer = this.initToken(file);
            engine = new CompilationEngine(tokenizer);
            engine.firstToken();
            engine.compileClass();
            writer = new PrintWriter(file.toString().replace(".jack", "R.xml"));
            System.out.println(engine.getXml());
            writer.print(engine.getXml());
            writer.flush();
            writer.close();
        }
    }


    private JackTokenizer initToken(File inFile) throws IOException {
        byte[] bytesArray = new byte[(int) inFile.length()];
        FileInputStream fis = new FileInputStream(inFile);
        fis.read(bytesArray); //read file into bytes[]
        fis.close();
        return new JackTokenizer(new String(bytesArray));
    }

    private void tokenTOTXMl() {
        String Txml = "<tokens>\n";
        while (tokenizer.hasMoreTokens()) {
            Token token = tokenizer.advance();
            Txml += "<" + token.getType() + ">" + token.getVal() + "</" + token.getType() + ">\n";
        }
        Txml += "</tokens>\n";
        this.writer.print(Txml);
        this.writer.flush();
    }

    private void tokenTOXML() {
        this.engine.firstToken();
        this.engine.compileClass();
        this.writer.println(engine.getXml());
        this.writer.flush();
    }

    private String tokenXml(Token token) {
        if (token != null) {
            return "<" + token.getType() + "> " + token.getVal() + " </" + token.getType() + ">\n";
        }
        return "";
    }




    public static void main(String[] args) throws IOException {
//        String pre = System.getProperty("user.dir");
//        File in = new File("/Users/zihuaye/Documents/GitHub/MoocAndBookEx/nand2tetris/projects/10/ArrayTest/Main.jack");
//        File out = new File("/Users/zihuaye/Documents/GitHub/MoocAndBookEx/nand2tetris/projects/10/ArrayTest/Rmain.xml");
        String path = "/Users/zihuaye/Documents/GitHub/MoocAndBookEx/nand2tetris/projects/10/Square/";
        JackAnalyzer analyzer = new JackAnalyzer(path);
        analyzer.writeXMLFiles();
    }

}
