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
import api.util.cminus.CMinusLexer;
import api.util.cminus.CMinusLexer.TokenType;
import api.util.cminus.CMinusParser;
import api.util.cminus.CMinusParser.CMinusIdentifierParameters;
import api.util.datastructures.SeparateChainingSymbolTable;
import api.util.datastructures.Token;

public class CMinusParserDemo
{
    public static void main(String[] args)
    {
        // Allow user to select multiple files sequentially using command-line arguments. Each argument is a separate file name.
        if ( args.length > 0 )
        {
            for ( int i = 0; i < args.length; i++ )
            {
                CMinusParserDemo.run(args[i]);
            }
        }
        // Allow user to select a file using a GUI.
        else
        {
            String fileName = Support.getFilePath(null, true, false);

            CMinusParserDemo.run(fileName);
        }
    }

    public static void run(final String fileName)
    {
        // Create an instance of the lexical analyzer.
        CMinusLexer<TokenType> lexer = new CMinusLexer<TokenType>();

        // Get the Tokens recognized by the lexer.
        List<Token<CMinusLexer.TokenType>> tokens = lexer.lexFile(fileName, true, true, true);

        // Create a doubly-linked list of symbol tables.
        List<SeparateChainingSymbolTable<String, CMinusIdentifierParameters>> symbolTables = new LinkedList<SeparateChainingSymbolTable<String, CMinusIdentifierParameters>>();

        // Create an instance of the parser, and pass the tokens and the symbol tables to it.
        @SuppressWarnings("unused")
        CMinusParser parser = new CMinusParser(tokens, symbolTables);
    }
}
