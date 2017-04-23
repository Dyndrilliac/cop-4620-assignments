%{
#include <stdio.h>
#include <stdlib.h>
extern int yylex();
extern int yytext[];
extern void yyerror(const char *s);
%}
%start Start
%token LtC   GtC    LeC   GeC   EqC   NeC    Lp    Rp    Lsb    Rsb
%token Cma   Ren    As    Whe   Uni   Int    Min   Tim   Joi    Div
%token S     P      SP    PRDCT CUST  ORDERS CNO   CITY  CNAME  SNO
%token PNO   TQTY   SNAME QUOTA PNAME COST   AVQTY SHASH STATUS PHASH
%token COLOR WEIGHT QTY   Number
%%
Start                   : Expression                            {
                                                                    printf("ACCEPT\n");
                                                                };
Expression              : OneRelationExpression                 {
                                                                };
                        | TwoRelationExpression                 {
                                                                };
OneRelationExpression   : Renaming                              {
                                                                };
                        | Restriction                           {
                                                                };
                        | Projection                            {
                                                                };
Renaming                : Term Ren Attribute As Attribute       {
                                                                };
Term                    : Relation                              {
                                                                };
                        | Lp Expression Rp                      {
                                                                };
Restriction             : Term Whe Comparison                   {
                                                                };
Projection              : Term                                  {
                                                                };
                        | Term Lsb AttributeCommalist Rsb       {
                                                                };
AttributeCommalist      : Attribute                             {
                                                                };
                        | Attribute Cma AttributeCommalist      {
                                                                };
TwoRelationExpression   : Projection BinaryOperation Expression {
                                                                };
BinaryOperation         : Uni                                   {
                                                                };
                        | Int                                   {
                                                                };
                        | Min                                   {
                                                                };
                        | Tim                                   {
                                                                };
                        | Joi                                   {
                                                                };
                        | Div                                   {
                                                                };
Comparison              : Attribute Compare Number              {
                                                                };
Compare                 : LtC                                   {
                                                                };
                        | GtC                                   {
                                                                };
                        | LeC                                   {
                                                                };
                        | GeC                                   {
                                                                };
                        | EqC                                   {
                                                                };
                        | NeC                                   {
                                                                };
Attribute               : CNO                                   {
																};
                        | CITY                                  {
                                                                };
                        | CNAME                                 {
                                                                };
                        | SNO                                   {
                                                                };
                        | PNO                                   {
                                                                };
                        | TQTY                                  {
                                                                };
                        | SNAME                                 {
                                                                };
                        | QUOTA                                 {
                                                                };
                        | PNAME                                 {
                                                                };
                        | COST                                  {
                                                                };
                        | AVQTY                                 {
                                                                };
                        | SHASH                                 {
                                                                };
                        | STATUS                                {
                                                                };
                        | PHASH                                 {
                                                                };
                        | COLOR                                 {
                                                                };
                        | WEIGHT                                {
                                                                };
                        | QTY                                   {
                                                                };
Relation                : S                                     {
                                                                };
                        | P                                     {
                                                                };
                        | SP                                    {
                                                                };
                        | PRDCT                                 {
                                                                };
                        | CUST                                  {
                                                                };
                        | ORDERS                                {
                                                                };
%%
int main()
{
   yyparse();
}
void yyerror(const char *s)
{
   printf("REJECT\n");
   exit(0);
}
int yywrap()
{

}
