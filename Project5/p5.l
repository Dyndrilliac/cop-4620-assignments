%{
#include "p5.tab.h"
%}
Delimiter       [ \t\n]
WhiteSpace      {Delimiter}+
Digit           [0-9]
Number          {Digit}+
BinaryOperation UNION|INTERSECT|MINUS|TIMES|JOIN|DIVIDEBY
Compare         <|>|<=|>=|=|<>
Attribute       CNO|CITY|CNAME|SNO|PNO|TQTY|SNAME|QUOTA|PNAME|COST|AVQTY|S#|STATUS|P#|COLOR|WEIGHT|QTY
Relation        S|P|SP|PRDCT|CUST|ORDERS
%%
{WhiteSpace}      ;
{Number}          return Number;
{BinaryOperation} return BinaryOperation;
{Compare}         return Compare;
{Attribute}       return Attribute;
{Relation}        return Relation;
"RENAME"          return Rename;
"AS"              return As;
"WHERE"           return Where;
%%
