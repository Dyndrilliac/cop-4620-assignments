/*
 * Title: CMinusLexerDemo
 * Author: Matthew Boyette
 * Date: 9/14/2016
 * 
 * This is a test program demonstrating a lexical analyzer class for the C-Minus language.
 */

import java.util.ArrayList;

public class CMinusLexerDemo
{
	public static void main(String[] args)
	{
		// Read in file names given as command-line arguments.
		if (args.length > 0)
        {
            for (int i = 0; i < args.length; i++)
            {
            	CMinusLexer<CMinusLexer.TokenType> lexer = new CMinusLexer<CMinusLexer.TokenType>();
            	ArrayList<Token<CMinusLexer.TokenType>> tokens = new ArrayList<Token<CMinusLexer.TokenType>>();
            	
            	tokens = lexer.lexFile(args[i]);
            	
            	// TODO: Pass the tokens to the parser.
            }
        }
	}
}
