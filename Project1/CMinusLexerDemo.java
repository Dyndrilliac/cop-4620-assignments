/*
 * Title: CMinusLexerDemo
 * Author: Matthew Boyette
 * Date: 9/14/2016
 * 
 * This is a test program demonstrating a lexical analyzer class for the C-Minus language.
 */

import api.util.Support;
import api.util.cminus.CMinusLexer;
import api.util.cminus.CMinusLexer.TokenType;

public class CMinusLexerDemo
{
    public static void main(String[] args)
    {
        // Allow user to select multiple files sequentially using command-line arguments. Each argument is a separate file name.
        if ( args.length > 0 )
        {
            for ( int i = 0; i < args.length; i++ )
            {
                CMinusLexerDemo.run(args[i]);
            }
        }
        // Allow user to select a file using a GUI.
        else
        {
            String fileName = Support.getFilePath(null, true, false);

            CMinusLexerDemo.run(fileName);
        }
    }

    public static void run(final String fileName)
    {
        // Perform lexical analysis on a file.
        ( new CMinusLexer<TokenType>() ).lexFile(fileName, false, true, true);
    }
}
