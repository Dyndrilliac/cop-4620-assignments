/*
 * Title: CMinusLexer
 * Author: Matthew Boyette
 * Date: 9/08/2016
 * 
 * This class operates as a lexical analyzer for the C-Minus language.
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

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
        // The tokens to return.
        ArrayList<Token<T>> tokens = new ArrayList<Token<T>>();

        // Try to read from the given file.
        try (Stream<String> stream = Files.lines(Paths.get(fileName)))
        {
            // Pass each line of text from the file to lex(), and add all the returned tokens to our output token buffer.
            stream.forEach(s -> tokens.addAll(this.lex(s)));
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }

        return tokens;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Token<T>> lex(final String s)
    {
        // Strip out whitespace.
        String input = s.replaceAll("\\s+", " ").trim();

        // Skip empty lines.
        if (input.isEmpty())
        {
            return new ArrayList<Token<T>>();
        }

        // Echo input.
        System.out.println("INPUT: " + s);

        // The tokens to return.
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
                    token = new Token<T>((T) TokenType.COMMENT, matcher.group(TokenType.COMMENT.name()));

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
                    token = new Token<T>((T) TokenType.COMMENT, matcher.group(TokenType.COMMENT.name()));

                    if (token.getData().contentEquals("/*"))
                    {
                        this.commentDepth++;
                    }
                    else if (token.getData().contentEquals("*/"))
                    {
                        Token<T> new_tok1 = new Token<T>((T) TokenType.OPERATOR, "*");
                        tokens.add(new_tok1);
                        System.out.println(new_tok1);

                        Token<T> new_tok2 = new Token<T>((T) TokenType.OPERATOR, "/");
                        tokens.add(new_tok2);
                        System.out.println(new_tok2);
                    }

                    continue;
                }
                else if (matcher.group(TokenType.GROUPING.name()) != null)
                {
                    token = new Token<T>((T) TokenType.GROUPING, matcher.group(TokenType.GROUPING.name()));
                    tokens.add(token);
                    System.out.println(token);
                    
                    // TODO: Implement depth counters for parentheses, square brackets, and curly braces.
                    
                    continue;
                }
                else if (matcher.group(TokenType.KEYWORD.name()) != null)
                {
                    token = new Token<T>((T) TokenType.KEYWORD, matcher.group(TokenType.KEYWORD.name()));
                    tokens.add(token);
                    System.out.println(token);
                    continue;
                }
                else if (matcher.group(TokenType.IDENTIFIER.name()) != null)
                {
                    token = new Token<T>((T) TokenType.IDENTIFIER, matcher.group(TokenType.IDENTIFIER.name()));
                    tokens.add(token);
                    System.out.println(token);
                    
                    // TODO: Add identifiers to the symbol table.
                    
                    continue;
                }
                else if (matcher.group(TokenType.NUMBER.name()) != null)
                {
                    token = new Token<T>((T) TokenType.NUMBER, matcher.group(TokenType.NUMBER.name()));
                    tokens.add(token);
                    System.out.println(token);
                    continue;
                }
                else if (matcher.group(TokenType.OPERATOR.name()) != null)
                {
                    token = new Token<T>((T) TokenType.OPERATOR, matcher.group(TokenType.OPERATOR.name()));
                    tokens.add(token);
                    System.out.println(token);
                    continue;
                }
                else if (matcher.group(TokenType.CATCHALL.name()) != null)
                {
                    token = new Token<T>((T) TokenType.CATCHALL, matcher.group(TokenType.CATCHALL.name()));
                    System.out.flush();
                    System.err.println("ERROR: " + token.getData() + " (LEXICAL_INVALID_TOKEN)");
                    continue;
                }
            }
        }

        return tokens;
    }
}
