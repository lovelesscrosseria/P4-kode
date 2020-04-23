import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import AST.BuildAstVisitor;
import AST.PrintAst;
import GrammarOut.roboLexer;
import GrammarOut.roboParser;
import expr.Expression;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class program 
{
    public static void main(String[] args) throws Exception{

        System.out.println("Hello World!");
        /*Used parser to turn a string of characters into a parse tree*/
        var input = convertFileToInputStream("src/Test");

        ReadableByteChannel channel = Channels.newChannel(input);
        var in = CharStreams.fromChannel(channel);
        roboLexer lexer = new roboLexer(in);

        // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        // create a parser that feeds off the tokens buffer

        roboParser parser = new roboParser(tokens);
        var cst = parser.program();
        var ast = new BuildAstVisitor().visitProgram(cst);
        ast.visit(new PrintAst());


    }

    /*Fil to inputstream*/
    public static InputStream convertFileToInputStream (String path) throws IOException {
        File initialFile = new File (path);
        return new FileInputStream(initialFile);
    }
}
