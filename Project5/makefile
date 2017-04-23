p5:	p5.tab.o lex.yy.o 
	cc -o p5 lex.yy.o p5.tab.o 

p5.tab.o: p5.tab.c
	cc -c p5.tab.c

p5.tab.c: p5.y
	bison -d p5.y

lex.yy.o: lex.yy.c p5.tab.h
	cc -c lex.yy.c

lex.yy.c: p5.l p5.tab.c
	flex p5.l
