/*
 * Title: CMinusCodeGenDemo
 * Author: Matthew Boyette
 * Date: 04/10/2017
 * 
 * This is a test program demonstrating an intermediate code generator class for the C-Minus language.
 */

import java.util.List;
import api.util.Support;
import api.util.cminus.CMinusCodeGeneration;
import api.util.cminus.CMinusLexer;
import api.util.cminus.CMinusParser;
import api.util.cminus.CMinusSemantics;
import api.util.datastructures.Token;

public class CMinusCodeGenDemo
{
    public static final void main(String[] args)
    {
        final boolean SILENT = true;

        if ( args.length > 0 ) // Allow user to select multiple files sequentially using command-line arguments. Each argument is a separate file name.
        {
            for ( int i = 0; i < args.length; i++ )
            {
                CMinusCodeGenDemo.run(args[i], SILENT);
            }
        }
        else // Allow user to select a file using a GUI.
        {
            String fileName = Support.getFilePath(null, true, !SILENT);

            CMinusCodeGenDemo.run(fileName, SILENT);
        }
    }

    public static final void run(final String fileName, final boolean silent)
    {
        // Create an instance of the lexical analyzer.
        CMinusLexer<CMinusLexer.TokenType> lexer = new CMinusLexer<CMinusLexer.TokenType>();

        // Get the Tokens recognized by the lexer.
        List<Token<CMinusLexer.TokenType>> tokens = lexer.lexFile(fileName, silent, true, true);

        if ( !silent )
        {
            StdOut.println();
        }

        // Create symbol tables.
        CMinusSemantics.SymTab<CMinusSemantics.SymTabRec> symbolTables = new CMinusSemantics.SymTab<CMinusSemantics.SymTabRec>();

        // Create an instance of the parser; pass the tokens and the symbol tables to it.
        CMinusParser parser = new CMinusParser(tokens, symbolTables, silent);

        if ( parser.getResult().contentEquals("ACCEPT") )
        {
            // Create an instance of the semantic analyzer; pass the tokens and the symbol tables to it.
            CMinusSemantics semantics = new CMinusSemantics(tokens, symbolTables, silent);

            if ( semantics.getResult().contentEquals("ACCEPT") )
            {
                CMinusCodeGeneration codeGen = new CMinusCodeGeneration(tokens, symbolTables, silent);

                StdOut.println(codeGen.getResult());
            }
            else
            {
                StdOut.println(semantics.getResult());
            }
        }
        else
        {
            StdOut.println(parser.getResult());
        }
    }
}
