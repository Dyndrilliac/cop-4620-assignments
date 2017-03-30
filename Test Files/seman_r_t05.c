int func(int x[], int y) /* b */
{

	return func(x[5]);
	
}

void main(void)
{
   int x;
   x = func();
}