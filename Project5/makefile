P5.out:	P5.tab.o lex.yy.o 
	cc -DECHO -o P5.out lex.yy.o P5.tab.o 

P5.tab.o: P5.tab.c
	cc -DECHO -c P5.tab.c

P5.tab.c: P5.y
	bison -d P5.y

lex.yy.o: lex.yy.c P5.tab.h
	cc -DECHO -c lex.yy.c

lex.yy.c: P5.l P5.tab.c
	flex P5.l

clean:
	rm -rf *.c *.o *.h *.exe *.out
