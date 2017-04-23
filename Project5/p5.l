%{
#include "p5.tab.h"
extern int yylval;
%}
Delimiter   [ \t\n]
WhiteSpace  {Delimiter}+
Digit       [0-9]
%%
{WhiteSpace}                ;
{Digit}+                    return(Number);
"S"                         return(S);
"P"                         return(P);
"SP"                        return(SP);
"PRDCT"                     return(PRDCT);
"CUST"                      return(CUST);
"ORDERS"                    return(ORDERS);
"CNO"                       return(CNO);
"CITY"                      return(CITY);
"CNAME"                     return(CNAME);
"SNO"                       return(SNO);
"PNO"                       return(PNO);
"TQTY"                      return(TQTY);
"SNAME"                     return(SNAME);
"QUOTA"                     return(QUOTA);
"PNAME"                     return(PNAME);
"COST"                      return(COST);
"AVQTY"                     return(AVQTY);
"S#"                        return(SHASH);
"STATUS"                    return(STATUS);
"P#"                        return(PHASH);
"COLOR"                     return(COLOR);
"WEIGHT"                    return(WEIGHT);
"QTY"                       return(QTY);
"RENAME"                    return(Ren);
"AS"                        return(As);
"WHERE"                     return(Whe);
"UNION"                     return(Uni);
"INTERSECT"                 return(Int);
"MINUS"                     return(Min);
"TIMES"                     return(Tim);
"JOIN"                      return(Joi);
"DIVIDEBY"                  return(Div);
","                         return(Cma);
"]"                         return(Rsb);
"["                         return(Lsb);
")"                         return(Rp);
"("                         return(Lp);
"<>"                        return(NeC);
"="                         return(EqC);
">="                        return(GeC);
"<="                        return(LeC);
">"                         return(GtC);
"<"                         return(LtC);
%%
