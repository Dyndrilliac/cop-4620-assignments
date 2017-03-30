int foo(void)  /* b no ret val */
{
   int x;
   return;
}
void main(void)
{
   int x;
   int y;
   foo();
   return;
}