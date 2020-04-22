import java.io.*;

import GrammarOut.*;
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
        ANTLRInputStream input = new ANTLRInputStream(convertingFileToInputStream("src/Test"));

        Expression expr = parse(input);
        System.out.println("Final note: " + expr.toString());

    }

    public static Expression parse(ANTLRInputStream input) {
        // Make a parser
        roboParser parser = makeParser(input);

        // Generate the parse tree using the starter rule.
        // root is the starter rule for this grammar.
        // Other grammars may have different names for the starter rule.
        ParseTree tree = parser.program();

        // *** Debugging option #1: print the tree to the console
        System.err.println(tree.toStringTree(parser));

        // *** Debugging option #2: show the tree in a window
        //Trees.inspect(tree, parser);

        // *** Debugging option #3: walk the tree with a listener
        new ParseTreeWalker().walk(new roboListenPrint(), tree);

        MakeExpressions exprMaker = new MakeExpressions();
        new ParseTreeWalker().walk(exprMaker, tree);
        return exprMaker.getExpression();
    }

    public static roboParser makeParser(ANTLRInputStream input)
    {
        roboLexer lexer = new roboLexer (input);
        //lexer.reportErrorsAsExceptions();
        TokenStream tokens = new CommonTokenStream(lexer);

        //roboParser parser = new roboParser(tokens);
        //parser.reportErrorsAsExceptions();

        return new roboParser(tokens);
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
