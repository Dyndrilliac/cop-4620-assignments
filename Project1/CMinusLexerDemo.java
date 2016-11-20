/*
 * Title: CMinusLexerDemo
 * Author: Matthew Boyette
 * Date: 9/14/2016
 * 
 * This is a test program demonstrating a lexical analyzer class for the C-Minus language.
 */

import java.util.List;
import api.util.Support;
import api.util.cminus.CMinusLexer;
import api.util.datastructures.Token;
import edu.princeton.cs.algs4.StdOut;

public class CMinusLexerDemo
{
    public static void main(String[] args)
    {
        final boolean SILENT = false;

        if ( args.length > 0 ) // Allow user to select multiple files sequentially using command-line arguments. Each argument is a separate file name.
        {
            for ( int i = 0; i < args.length; i++ )
            {
                if (i > 0)
                {
                    StdOut.println();
                }
                
                CMinusLexerDemo.run(args[i], SILENT);
            }
        }
        else // Allow user to select a file using a GUI.
        {
            String fileName = Support.getFilePath(null, true, !SILENT);

            CMinusLexerDemo.run(fileName, SILENT);
        }
    }

    public static void run(final String fileName, final boolean silent)
    {
        // Create an instance of the lexical analyzer.
        CMinusLexer<CMinusLexer.TokenType> lexer = new CMinusLexer<CMinusLexer.TokenType>();

        // Get the Tokens recognized by the lexer.
        @SuppressWarnings("unused")
        List<Token<CMinusLexer.TokenType>> tokens = lexer.lexFile(fileName, silent, true, true);
    }
}
