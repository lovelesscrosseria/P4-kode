import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import AST.AST;
import AST.BuildAstVisitor;
import AST.PrintAst;
import ContexualAnalysis.ContextualAnalysis;
import ContexualAnalysis.MethodDeclaration;
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

        System.out.println("RoboCompiler v.1337");
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
        AST.errors.clear();
        AST.symbolTable.clear();
        var ast = new BuildAstVisitor().visitProgram(cst);
        //ast.visit(new PrintAst());
        ast.visit(new MethodDeclaration());
        ast.visit(new ContextualAnalysis());

        if (ast.errors.size() > 0) {
            StringBuilder error = new StringBuilder("\n");
            for (var err : AST.errors) {
                error.append(err);
            }

            throw new Error(error.toString());
        }
    }

    /*Fil to inputstream*/
    public static InputStream convertFileToInputStream (String path) throws IOException {
        File initialFile = new File (path);
        return new FileInputStream(initialFile);
    }
}
