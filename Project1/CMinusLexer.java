/*
 * Title: CMinusLexer
 * Author: Matthew Boyette
 * Date: 9/08/2016
 * 
 * This class operates as a lexical analyzer for the C-Minus language.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CMinusLexer<T>
{
    /*
     * This helper enumerator class represents each of the various special types of tokens we are interested in.
     */
    public static enum TokenType
    {
        COMMENT(CMinusLexer.COMMENTS), GROUPING(CMinusLexer.GROUPINGS), KEYWORD(CMinusLexer.KEYWORDS), IDENTIFIER(CMinusLexer.IDENTIFIERS), NUMBER(CMinusLexer.NUMBERS), OPERATOR(CMinusLexer.OPERATORS), CATCHALL(CMinusLexer.CATCHALLS);

        private final String pattern;

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

    // Regular Expressions describing the various components of the C-Minus grammar.
    public static final String COMMENTS    = "((\\/\\/)|(\\/\\*)|(\\*\\/))";
    public static final String GROUPINGS   = "(([\\(\\)\\{\\}\\[\\]])|(,)|(;))";
    public static final String KEYWORDS    = "((else)|(float)|(if)|(int)|(return)|(void)|(while))";
    public static final String IDENTIFIERS = "(\\b([a-zA-Z])+\\d*)";
    public static final String NUMBERS     = "(((\\-)?\\d+(\\.\\d+)?((E|e)(\\+|\\-)?\\d+)?)|((\\-)?\\.\\d+((E|e)(\\+|\\-)?\\d+)?))";
    public static final String OPERATORS   = "((\\<\\=)|(\\>\\=)|(\\=\\=)|(\\!\\=)|([\\+\\-\\*\\/\\<\\>\\=]))";
    public static final String CATCHALLS   = "([^,;\\+\\-\\*\\/\\>\\<\\=\\(\\)\\{\\}\\[\\]\\s])+";

    private int commentDepth = -1;
    private int parenthDepth = -1;
    private int bracketDepth = -1;
    private int braceDepth   = -1;

    public ArrayList<Token<T>> lexFile(final String fileName)
    {
        // A buffer for the tokens we want to return.
        ArrayList<Token<T>> tokens = new ArrayList<Token<T>>();

        // A buffer
        String textBuffer = "";

        try
        {
            // Try to open the given file for a read operation.
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            // Pass each line of text from the file to lex(), and add all the returned tokens to our output token buffer.
            while((textBuffer = br.readLine()) != null)
            {
                tokens.addAll(this.lex(textBuffer));
            }

            br.close();
        }
        catch (final FileNotFoundException fnfe)
        {
            fnfe.printStackTrace();
        }
        catch (final IOException ioe)
        {
            ioe.printStackTrace();
        }

        return tokens;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Token<T>> lex(final String s)
    {
        // Strip out unnecessary whitespace.
        String input = s.replaceAll("\\s+", " ").trim();

        // Skip empty lines.
        if (input.isEmpty())
        {
            return new ArrayList<Token<T>>();
        }

        // Echo input.
        System.out.flush();
        System.err.flush();
        System.out.println("INPUT: " + s);

        // A buffer for the tokens we want to return.
        ArrayList<Token<T>> tokens = new ArrayList<Token<T>>();

        // Lexer logic begins here.
        StringBuffer tokenPatternsBuffer = new StringBuffer();

        for (TokenType tokenType : TokenType.values())
        {
            tokenPatternsBuffer.append(String.format("|(?<%s>%s)", tokenType, tokenType.getPattern()));
        }

        Pattern tokenPatterns = Pattern.compile(new String(tokenPatternsBuffer.substring(1)));

        // Begin matching tokens using the RegExr patterns.
        Matcher matcher = tokenPatterns.matcher(input);

        while (matcher.find())
        {
            Token<T> token = null;

            if (this.commentDepth >= 0)
            {
                if (matcher.group(TokenType.COMMENT.name()) != null)
                {
                    token = new Token<T>((T) TokenType.COMMENT, matcher.group(TokenType.COMMENT.name()), this.braceDepth, this.bracketDepth, this.parenthDepth);

                    if (token.getData().contentEquals("/*"))
                    {
                        this.commentDepth++;
                    }
                    else if (token.getData().contentEquals("*/"))
                    {
                        this.commentDepth--;
                    }
                }

                continue;
            }
            else
            {
                if (matcher.group(TokenType.COMMENT.name()) != null)
                {
                    token = new Token<T>((T) TokenType.COMMENT, matcher.group(TokenType.COMMENT.name()), this.braceDepth, this.bracketDepth, this.parenthDepth);

                    if (token.getData().contentEquals("/*"))
                    {
                        this.commentDepth++;
                    }
                    else if (token.getData().contentEquals("*/"))
                    {
                        Token<T> new_tok1 = new Token<T>((T) TokenType.OPERATOR, "*", this.braceDepth, this.bracketDepth, this.parenthDepth);
                        tokens.add(new_tok1);
                        System.out.println(new_tok1);

                        Token<T> new_tok2 = new Token<T>((T) TokenType.OPERATOR, "/", this.braceDepth, this.bracketDepth, this.parenthDepth);
                        tokens.add(new_tok2);
                        System.out.println(new_tok2);
                    }

                    continue;
                }
                else if (matcher.group(TokenType.GROUPING.name()) != null)
                {
                    token = new Token<T>((T) TokenType.GROUPING, matcher.group(TokenType.GROUPING.name()), this.braceDepth, this.bracketDepth, this.parenthDepth);
                    tokens.add(token);
                    System.out.println(token);

                    switch (token.getData())
                    {
                        case "(":
                            this.parenthDepth++;
                            break;

                        case ")":
                            this.parenthDepth--;
                            break;

                        case "[":
                            this.bracketDepth++;
                            break;

                        case "]":
                            this.bracketDepth--;
                            break;

                        case "{":
                            this.braceDepth++;
                            break;

                        case "}":
                            this.braceDepth--;
                            break;

                        default:
                            break;
                    }

                    continue;
                }
                else if (matcher.group(TokenType.KEYWORD.name()) != null)
                {
                    token = new Token<T>((T) TokenType.KEYWORD, matcher.group(TokenType.KEYWORD.name()), this.braceDepth, this.bracketDepth, this.parenthDepth);
                    tokens.add(token);
                    System.out.println(token);
                    continue;
                }
                else if (matcher.group(TokenType.IDENTIFIER.name()) != null)
                {
                    token = new Token<T>((T) TokenType.IDENTIFIER, matcher.group(TokenType.IDENTIFIER.name()), this.braceDepth, this.bracketDepth, this.parenthDepth);
                    tokens.add(token);
                    System.out.println(token);

                    // TODO: Add identifiers to the symbol table.

                    continue;
                }
                else if (matcher.group(TokenType.NUMBER.name()) != null)
                {
                    token = new Token<T>((T) TokenType.NUMBER, matcher.group(TokenType.NUMBER.name()), this.braceDepth, this.bracketDepth, this.parenthDepth);
                    tokens.add(token);
                    System.out.println(token);
                    continue;
                }
                else if (matcher.group(TokenType.OPERATOR.name()) != null)
                {
                    token = new Token<T>((T) TokenType.OPERATOR, matcher.group(TokenType.OPERATOR.name()), this.braceDepth, this.bracketDepth, this.parenthDepth);
                    tokens.add(token);
                    System.out.println(token);
                    continue;
                }
                else if (matcher.group(TokenType.CATCHALL.name()) != null)
                {
                    token = new Token<T>((T) TokenType.CATCHALL, matcher.group(TokenType.CATCHALL.name()), this.braceDepth, this.bracketDepth, this.parenthDepth);
                    System.err.println("ERROR: " + token.getData() + " (LEXICAL_INVALID_TOKEN)");
                    continue;
                }
            }
        }

        return tokens;
    }
}