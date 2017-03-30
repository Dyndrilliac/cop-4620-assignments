void foo(int x, float p) /* b, too few args */
{
   int w;
   return;
}
void main(void)
{
   int x;
   float y;
   foo(x);
   return;
}