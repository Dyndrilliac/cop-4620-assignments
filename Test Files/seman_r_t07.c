void foo(void) /* b even with good function call */
{
   int x;
}
void main(void)
{
   int x;
   int y;
   x = y + foo();  /* bad mixed type */
}