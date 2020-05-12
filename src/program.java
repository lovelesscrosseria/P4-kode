import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import AST.AST;
import AST.BuildAstVisitor;
import CodeGeneration.JavaCodeGen;
import ContexualAnalysis.BasicErrorListener;
import ContexualAnalysis.ContextualAnalysis;
import ContexualAnalysis.MethodDeclaration;
import GrammarOut.roboLexer;
import GrammarOut.roboParser;
import TypeChecking.TypeChecking;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.*;

public class program 
{
    public static void main(String[] args) throws Exception{

        System.out.println("RoboCompiler v.1337");
        /*Used parser to turn a string of characters into a parse tree*/
        var input = convertFileToInputStream("src/MyAwesomeRobot");

        ReadableByteChannel channel = Channels.newChannel(input);
        var in = CharStreams.fromChannel(channel);
        roboLexer lexer = new roboLexer(in);

        // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        // create a parser that feeds off the tokens buffer

        roboParser parser = new roboParser(tokens);
        var errorListener = new BasicErrorListener();
        parser.addErrorListener(errorListener);
        var cst = parser.program();

        if (errorListener.hasErrors()) {
            throw new Error();
        }

        AST.errors.clear();
        AST.symbolTable.clear();
        var ast = new BuildAstVisitor().visitProgram(cst);
        //ast.visit(new PrintAst());
        System.out.println("Declaring methods...");
        ast.visit(new MethodDeclaration());
        System.out.println("Doing contextual analysis...");
        ast.visit(new ContextualAnalysis());
        checkErrors();
        System.out.println("Checking types and valid returns...");
        ast.visit(new TypeChecking());
        checkErrors();
        System.out.println("Generating java code...");
        ast.visit(new JavaCodeGen("C:\\robocode\\robots\\Rooster"));
        System.out.println("Generated code is located at: C:\\robocode\\robots\\Rooster");
        checkErrors();
        System.out.println("All looks good.");
        input.close();
    }


    public static void checkErrors() {
        if (AST.errors.size() > 0) {
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
