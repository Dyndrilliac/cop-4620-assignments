%{
#include <stdio.h>
#include <stdlib.h>
extern int  yylex();
extern int  yytext[];
extern void yyerror(const char *s);
%}
%start Start
%token Number Ren As Whe Cma Rsb Lsb Rp Lp BinaryOperation
%token Compare Attribute Relation
%%
Start                 : Expression                            ;
Expression            : OneRelationExpression
                      | TwoRelationExpression                 ;
OneRelationExpression : Renaming
                      | Restriction
                      | Projection                            ;
Renaming              : Term Ren Attribute As Attribute       ;
Term                  : Relation
                      | Lp Expression Rp                      ;
Restriction           : Term Whe Comparison                   ;
Projection            : Term
                      | Term Lsb AttributeCommalist Rsb       ;
AttributeCommalist    : Attribute
                      | Attribute Cma AttributeCommalist      ;
TwoRelationExpression : Projection BinaryOperation Expression ;
Comparison            : Attribute Compare Number              ;
%%
int main()
{
   yyparse();
   fprintf(stdout, "ACCEPT\n");
}
void yyerror(const char *s)
{
   fprintf(stdout, "REJECT\n");
   exit(0);
}
int yywrap() {}
