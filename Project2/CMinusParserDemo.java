/*
 * Title: CMinusParserDemo
 * Author: Matthew Boyette
 * Date: 10/04/2016
 * 
 * This is a test program demonstrating a syntactical analyzer class for the C-Minus language.
 */

import java.util.List;
import api.util.Support;
import api.util.cminus.CMinusLexer;
import api.util.cminus.CMinusParser;
import api.util.cminus.CMinusSemantics.SymTab;
import api.util.cminus.CMinusSemantics.SymTabRec;
import api.util.datastructures.Token;
import edu.princeton.cs.algs4.StdOut;

public class CMinusParserDemo
{
    public static void main(String[] args)
    {
        final boolean SILENT = true;

        if ( args.length > 0 ) // Allow user to select multiple files sequentially using command-line arguments. Each argument is a separate file name.
        {
            for ( int i = 0; i < args.length; i++ )
            {
                CMinusParserDemo.run(args[i], SILENT);
            }
        }
        else // Allow user to select a file using a GUI.
        {
            String fileName = Support.getFilePath(null, true, !SILENT);

            CMinusParserDemo.run(fileName, SILENT);
        }
    }

    public static void run(final String fileName, final boolean silent)
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
        SymTab<SymTabRec> symbolTables = new SymTab<SymTabRec>();

        // Create an instance of the parser; pass the tokens and the symbol tables to it.
        CMinusParser parser = new CMinusParser(tokens, symbolTables, silent);

        // Print the parser's results.
        StdOut.println(parser.getResult());
    }
}
