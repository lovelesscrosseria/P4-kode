import GrammarOut.*;

import java.io.*;

import GrammarOut.roboParser;
import org.antlr.v4.runtime.*;

public class program 
{
    public static void main(String[] args)
    {
        while (true)
        {
            System.out.println("> ");
            var exprText = System.console().readLine();

            var inputStream = new ANTLRInputStream(new StringReader(exprText));
            var lexer = new roboLexer(inputStream);
            var tokenStream = new CommonTokenStream(lexer);
            var parser = new roboParser(tokenStream);

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
    }
}
