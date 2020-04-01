package com.company;

import java.io.*;

public class JackAnalyzer {
    private JackTokenizer tokenizer;
    private CompilationEngine engine;
    private PrintWriter writer;

    public JackAnalyzer(File inFile, File outFile) throws IOException {
        this. tokenizer = this.initToken(inFile);
        this.writer = new PrintWriter(outFile);
        this.engine = new CompilationEngine(tokenizer, outFile);
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
            Txml += this.tokenXml(token);
        }
        Txml += "</tokens>\n";
        this.writer.print(Txml);
        this.writer.flush();
    }

    private String tokenXml(Token token) {
        return "<" + token.getType() + ">" + token.getVal() + "</" + token.getType() + ">\n";
    }


    private PrintWriter setTokenFile() {
        return null;
    }

    public static void main(String[] args) throws IOException {
        String pre = System.getProperty("user.dir");
        File in = new File(pre + "/Main.jack");
        File out = new File(pre + "/MainT.xml");
        JackAnalyzer analyzer = new JackAnalyzer(in, out);
        analyzer.tokenTOTXMl();
    }

}
