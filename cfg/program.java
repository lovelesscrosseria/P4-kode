import java.io.*;
import java.util.function.Function;
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
                var cst = parser.CompileUnit();
                var ast = new buildAstVisitor().VisitCompileUnit(cst);
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
