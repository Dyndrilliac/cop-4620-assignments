void foo(float p) /* b too few parms */
{
   int w;
   return;
}
void main(void)
{
   int x;
   float y;
   foo(x, y);
   return;
}