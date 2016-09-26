
/*
 * Title: CMinusLexer
 * Author: Matthew Boyette
 * Date: 9/08/2016
 * 
 * This class functions as a generic lexical analyzer for the C-Minus language.
 */

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import api.util.Lexer;
import api.util.Support;
import api.util.datastructures.Token;
import edu.princeton.cs.algs4.StdOut;

public class CMinusLexer<T> extends Lexer<T>
{
    /*
     * This helper enumerator class represents each of the various special types of tokens of interest.
     */
    public static enum TokenType
    {
        //@formatter:off
        COMMENT(Lexer.C_COMMENTS),
        GROUPING(CMinusLexer.C_GROUPINGS),
        KEYWORD(CMinusLexer.C_KEYWORDS),
        IDENTIFIER(CMinusLexer.C_IDENTIFIERS),
        NUMBER(Lexer.C_NUMBERS),
        OPERATOR(CMinusLexer.C_OPERATORS),
        WHITESPACE(Lexer.C_WHITESPACES),
        ERROR(CMinusLexer.C_ERRORS);
        //@formatter:on

        private String pattern;

        private TokenType(final String pattern)
        {
            this.pattern = pattern;
        }

        public final String getPattern()
        {
            return this.pattern;
        }

        @Override
        public final String toString()
        {
            return this.name();
        }
    }

    // RegExr patterns describing the various components of the C-Minus language grammar.
    public static final String C_ERRORS      = "([^\\(\\)\\{\\}\\[\\]\\+\\-\\*\\/\\<\\>\\=,;\\s]+)";
    public static final String C_GROUPINGS   = "([\\(\\)\\{\\}\\[\\],;])";
    public static final String C_IDENTIFIERS = "([A-Za-z])([A-Za-z0-9]*)";
    public static final String C_KEYWORDS    = "(else)|(float)|(if)|(int)|(return)|(void)|(while)";
    public static final String C_OPERATORS   = "(\\<\\=)|(\\>\\=)|(\\=\\=)|(\\!\\=)|([\\+\\-\\*\\/\\<\\>\\=])";

    public static void main(final String[] args)
    {
        // It comes with a limited test bed program so you can lex arbitrary input quickly.
        StdOut.println("RegEx Grammars:");
        StdOut.println("COMMENTS:\t" + Lexer.C_COMMENTS);
        StdOut.println("GROUPINGS:\t" + CMinusLexer.C_GROUPINGS);
        StdOut.println("KEYWORDS:\t" + CMinusLexer.C_KEYWORDS);
        StdOut.println("IDENTIFIERS:\t" + CMinusLexer.C_IDENTIFIERS);
        StdOut.println("NUMBERS:\t" + Lexer.C_NUMBERS);
        StdOut.println("OPERATORS:\t" + CMinusLexer.C_OPERATORS);
        StdOut.println("WHITESPACES:\t" + Lexer.C_WHITESPACES);
        StdOut.println("ERRORS:\t" + CMinusLexer.C_ERRORS);

        String input = Support.getInputString(null, "Please provide an expression.", "Expression Lexer Input");

        ArrayList<Token<TokenType>> tokens = ( new CMinusLexer<TokenType>() ).lex(input, false, true);

        StdOut.println("\nInput:\t" + input + "\n");

        for ( Token<TokenType> token : tokens )
        {
            StdOut.println(token);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<Token<T>> lex(final String s, final boolean silent, final boolean ignoreWhiteSpace)
    {
        // Skip empty lines.
        if ( s.isEmpty() )
        {
            StdOut.println();
            return new ArrayList<Token<T>>();
        }

        // Echo input.
        StdOut.println("INPUT: " + s);

        // A buffer for the tokens we want to return.
        ArrayList<Token<T>> tokens = new ArrayList<Token<T>>();

        // Lexer logic begins here.
        StringBuffer tokenPatternsBuffer = new StringBuffer();

        for ( TokenType tokenType : TokenType.values() )
        {
            tokenPatternsBuffer.append(String.format("|(?<%s>%s)", tokenType, tokenType.getPattern()));
        }

        Pattern tokenPatterns = Pattern.compile(new String(tokenPatternsBuffer.substring(1)));

        // Begin matching tokens using the designated RegExr patterns.
        Matcher matcher = tokenPatterns.matcher(s);

        while ( matcher.find() )
        {
            Token<T> token = null;

            if ( this.Depth[DepthType.COMMENT.ordinal()] > 0 )
            {
                if ( matcher.group(TokenType.COMMENT.name()) != null )
                {
                    token = new Token<T>((T) TokenType.COMMENT, matcher.group(TokenType.COMMENT.name()), this.Depth[DepthType.BRACE.ordinal()], this.Depth[DepthType.BRACKET.ordinal()], this.Depth[DepthType.PARENTH.ordinal()]);

                    if ( token.getData().contentEquals("/*") )
                    {
                        this.Depth[DepthType.COMMENT.ordinal()]++;
                    }
                    else if ( token.getData().contentEquals("*/") )
                    {
                        this.Depth[DepthType.COMMENT.ordinal()]--;
                    }
                }
            }
            else
            {
                if ( matcher.group(TokenType.COMMENT.name()) != null )
                {
                    token = new Token<T>((T) TokenType.COMMENT, matcher.group(TokenType.COMMENT.name()), this.Depth[DepthType.BRACE.ordinal()], this.Depth[DepthType.BRACKET.ordinal()], this.Depth[DepthType.PARENTH.ordinal()]);

                    if ( token.getData().contentEquals("/*") )
                    {
                        this.Depth[DepthType.COMMENT.ordinal()]++;
                    }
                    else if ( token.getData().contentEquals("*/") )
                    {
                        Token<T> new_tok1 = new Token<T>((T) TokenType.OPERATOR, "*", this.Depth[DepthType.BRACE.ordinal()], this.Depth[DepthType.BRACKET.ordinal()], this.Depth[DepthType.PARENTH.ordinal()]);
                        tokens.add(new_tok1);

                        Token<T> new_tok2 = new Token<T>((T) TokenType.OPERATOR, "/", this.Depth[DepthType.BRACE.ordinal()], this.Depth[DepthType.BRACKET.ordinal()], this.Depth[DepthType.PARENTH.ordinal()]);
                        tokens.add(new_tok2);

                        if ( !silent )
                        {
                            StdOut.println(new_tok1);
                            StdOut.println(new_tok2);
                        }
                    }
                    else if ( token.getData().contentEquals("//") ) { return tokens; }

                    continue;
                }
                else if ( matcher.group(TokenType.GROUPING.name()) != null )
                {
                    token = new Token<T>((T) TokenType.GROUPING, matcher.group(TokenType.GROUPING.name()), this.Depth[DepthType.BRACE.ordinal()], this.Depth[DepthType.BRACKET.ordinal()], this.Depth[DepthType.PARENTH.ordinal()]);

                    switch ( token.getData() )
                    {
                        case "(":
                            this.Depth[DepthType.PARENTH.ordinal()]++;
                            break;

                        case ")":
                            this.Depth[DepthType.PARENTH.ordinal()]--;
                            break;

                        case "[":
                            this.Depth[DepthType.BRACKET.ordinal()]++;
                            break;

                        case "]":
                            this.Depth[DepthType.BRACKET.ordinal()]--;
                            break;

                        case "{":
                            this.Depth[DepthType.BRACE.ordinal()]++;
                            break;

                        case "}":
                            this.Depth[DepthType.BRACE.ordinal()]--;
                            break;

                        default:
                            break;
                    }
                }
                else if ( matcher.group(TokenType.KEYWORD.name()) != null )
                {
                    token = new Token<T>((T) TokenType.KEYWORD, matcher.group(TokenType.KEYWORD.name()), this.Depth[DepthType.BRACE.ordinal()], this.Depth[DepthType.BRACKET.ordinal()], this.Depth[DepthType.PARENTH.ordinal()]);
                }
                else if ( matcher.group(TokenType.IDENTIFIER.name()) != null )
                {
                    token = new Token<T>((T) TokenType.IDENTIFIER, matcher.group(TokenType.IDENTIFIER.name()), this.Depth[DepthType.BRACE.ordinal()], this.Depth[DepthType.BRACKET.ordinal()], this.Depth[DepthType.PARENTH.ordinal()]);

                    // TODO: Add the identifiers to the symbol table.
                }
                else if ( matcher.group(TokenType.NUMBER.name()) != null )
                {
                    token = new Token<T>((T) TokenType.NUMBER, matcher.group(TokenType.NUMBER.name()), this.Depth[DepthType.BRACE.ordinal()], this.Depth[DepthType.BRACKET.ordinal()], this.Depth[DepthType.PARENTH.ordinal()]);
                }
                else if ( matcher.group(TokenType.OPERATOR.name()) != null )
                {
                    token = new Token<T>((T) TokenType.OPERATOR, matcher.group(TokenType.OPERATOR.name()), this.Depth[DepthType.BRACE.ordinal()], this.Depth[DepthType.BRACKET.ordinal()], this.Depth[DepthType.PARENTH.ordinal()]);
                }
                else if ( matcher.group(TokenType.WHITESPACE.name()) != null )
                {
                    token = new Token<T>((T) TokenType.WHITESPACE, matcher.group(TokenType.WHITESPACE.name()), this.Depth[DepthType.BRACE.ordinal()], this.Depth[DepthType.BRACKET.ordinal()], this.Depth[DepthType.PARENTH.ordinal()]);

                    if ( ignoreWhiteSpace )
                    {
                        continue;
                    }
                }
                else if ( matcher.group(TokenType.ERROR.name()) != null )
                {
                    token = new Token<T>((T) TokenType.ERROR, matcher.group(TokenType.ERROR.name()), this.Depth[DepthType.BRACE.ordinal()], this.Depth[DepthType.BRACKET.ordinal()], this.Depth[DepthType.PARENTH.ordinal()]);
                }

                tokens.add(token);

                if ( !silent )
                {
                    StdOut.println(token);
                }
            }
        }

        return tokens;
    }
}
