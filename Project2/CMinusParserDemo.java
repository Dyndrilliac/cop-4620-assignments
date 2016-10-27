/*
 * Title: CMinusParserDemo
 * Author: Matthew Boyette
 * Date: 10/04/2016
 * 
 * This is a test program demonstrating a syntactical analyzer class for the C-Minus language.
 */

import java.util.LinkedList;
import java.util.List;
import api.util.Support;
import api.util.cminus.CMinusIdentifierParameters;
import api.util.cminus.CMinusLexer;
import api.util.cminus.CMinusParser;
import api.util.datastructures.SeparateChainingSymbolTable;
import api.util.datastructures.Token;

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

        // Create a doubly-linked list of symbol tables.
        List<SeparateChainingSymbolTable<String, CMinusIdentifierParameters>> symbolTables = new LinkedList<SeparateChainingSymbolTable<String, CMinusIdentifierParameters>>();

        // Create an instance of the parser; pass the tokens and the symbol tables to it.
        @SuppressWarnings("unused")
        CMinusParser parser = new CMinusParser(tokens, symbolTables, silent);
    }
}
