%{
#include <stdio.h>
#include <stdlib.h>
extern int  yylex();
extern void yyerror(const char *s);
%}
%start Start
%token Number BinaryOperation Compare Attribute Relation Rename As Where
%%
Start                 : Expression
                      ;
Expression            : OneRelationExpression
                      | TwoRelationExpression
                      ;
OneRelationExpression : Renaming
                      | Restriction
                      | Projection
                      ;
Renaming              : Term Rename Attribute As Attribute
                      ;
Term                  : Relation
                      | '(' Expression ')'
                      ;
Restriction           : Term Where Comparison
                      ;
Projection            : Term
                      | Term '[' AttributeCommalist ']'
                      ;
AttributeCommalist    : Attribute
                      | Attribute ',' AttributeCommalist
                      ;
TwoRelationExpression : Projection BinaryOperation Expression
                      ;
Comparison            : Attribute Compare Number
                      ;
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
