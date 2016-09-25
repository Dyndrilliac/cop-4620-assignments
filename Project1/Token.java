/*
 * Title: Token
 * Author: Matthew Boyette
 * Date: 3/27/2015
 * 
 * This helper class represents a token in a string.
 */

public class Token<T>
{
    private int    braceDepth;
    private int    bracketDepth;
    private String data;
    private int    parenthDepth;
    private T      type;
    
    public Token(final T type, final String data)
    {
        this.setType(type);
        this.setData(data);
        this.setBraceDepth(-1);
        this.setBracketDepth(-1);
        this.setParenthDepth(-1);
    }

    public Token(final T type, final String data, final int braceDepth, final int bracketDepth, final int parenthDepth)
    {
        this.setType(type);
        this.setData(data);
        this.setBraceDepth(braceDepth);
        this.setBracketDepth(bracketDepth);
        this.setParenthDepth(parenthDepth);
    }

    public final int getBraceDepth()
    {
        return braceDepth;
    }

    public final int getBracketDepth()
    {
        return bracketDepth;
    }

    public final String getData()
    {
        return this.data;
    }

    public final int getParenthDepth()
    {
        return parenthDepth;
    }

    public final T getType()
    {
        return this.type;
    }

    protected final void setBraceDepth(final int braceDepth)
    {
        this.braceDepth = braceDepth;
    }

    protected final void setBracketDepth(final int bracketDepth)
    {
        this.bracketDepth = bracketDepth;
    }

    protected final void setData(final String data)
    {
        this.data = data;
    }

    protected final void setParenthDepth(final int parenthDepth)
    {
        this.parenthDepth = parenthDepth;
    }

    protected final void setType(final T type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return String.format("%s: %s", this.getType(), this.getData());
    }
}
