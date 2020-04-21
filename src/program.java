import java.io.*;

import GrammarOut.*;
import GrammarOut.roboLexer;
import GrammarOut.roboParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class program 
{
    public static void main(String[] args) throws Exception{

        System.out.println("Hello World!");
        /*CharStream input = CharStream.fromPath(Paths.get(args[0]));
        */
        /*Used parser to turn a string of characters into a parse tree*/
        ANTLRInputStream input = new ANTLRInputStream(convertingFileToInputStream("src/Test"));

        roboLexer lexer = new roboLexer (input);

        TokenStream tokens = new CommonTokenStream(lexer);

        roboParser parser = new roboParser(tokens);

        ParseTree tree = parser.program();
        System.err.println(tree.toStringTree(parser));
        //System.out.println(tree.toStringTree(parser));

        ParseTreeWalker walker = new ParseTreeWalker();
        roboListener listener = new roboListen();
        walker.walk(listener, tree);
    }

    /*Fil to inputstream*/
    public static InputStream convertingFileToInputStream (String path)
        throws IOException {
            File initialFile = new File (path);
        return new FileInputStream(initialFile);
        }

    /*
    public static void main(String[] args)
    {
        while (true)
        {
            System.out.println("> ");
            var exprText = System.console().readLine();

            ANTLRInputStream inputStream = null;
            try {
                inputStream = new ANTLRInputStream(new StringReader(exprText));
            } catch (IOException e) {
                e.printStackTrace();
            }
            var lexer = new roboLexer(inputStream);
            var tokenStream = new CommonTokenStream(lexer);
            var parser = new roboParser(tokenStream);
            parser.setBuildParseTree(true);

            try
            {
                var cst = parser.compileUnit();
                var ast = new BuildAstVisitor().VisitCompileUnit(cst);
                var value = new EvaluateExpressionVisitor().Visit(ast);

                System.out.println("= {0}", value);
            }
            catch(Exception ex)
            {
                System.out.println(ex.Message);
            }

            System.out.println();
        }
    }*/
}
