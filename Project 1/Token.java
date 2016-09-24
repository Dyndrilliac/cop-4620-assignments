/*
 * Title: Token
 * Author: Matthew Boyette
 * Date: 3/27/2015
 * 
 * This helper class represents a token in a string.
 */

public class Token<T>
{
    public String data;
    public T      type;
    
    public Token(final T type, final String data)
    {
        this.setType(type);
        this.setData(data);
    }
    
    public final String getData()
    {
        return this.data;
    }
    
    public final T getType()
    {
        return this.type;
    }
    
    public final void setData(final String data)
    {
        this.data = data;
    }
    
    public final void setType(final T type)
    {
        this.type = type;
    }
    
    @Override
    public String toString()
    {
        return String.format("%s: %s", this.getType(), this.getData());
    }
}
