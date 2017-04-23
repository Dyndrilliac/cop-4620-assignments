p5:	p5.tab.o lex.yy.o 
	cc -DECHO -o p5 lex.yy.o p5.tab.o 

p5.tab.o: p5.tab.c
	cc -DECHO -c p5.tab.c

p5.tab.c: p5.y
	bison -d p5.y

lex.yy.o: lex.yy.c p5.tab.h
	cc -DECHO -c lex.yy.c

lex.yy.c: p5.l p5.tab.c
	flex p5.l

clean:
	rm -rf *.c *.o *.h *.exe
