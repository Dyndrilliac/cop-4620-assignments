*******************************************************************

* Title:     COP 4620 Assignments
* Author:    [Matthew Boyette](mailto:N00868808@ospreys.unf.edu)
* Class:     Construction of Language Translators (COP 4620)
* Professor: Dr. Roger Eggen
* Term:      Fall 2016

*******************************************************************

This code makes use of my [Custom Java API](https://github.com/Dyndrilliac/java-custom-api). In order to build this source, you should clone the repository for the API using your Git client, then import the project into your IDE of choice (I prefer Eclipse), and finally modify the build path to include the API project. For more detailed instructions, see the README for the API project.

#Project 1 - Lexical Analyzer

    * Due:       9/22/2016
    * Submitted: 9/24/2016

    The goal of this project is to produce a list of valid tokens to pass to the parser (Syntax Analyzer) in Project 2. In order to execute the program, the names of the files containing the text to be scanned must be fed to the program via command-line arguments. If you are GUI capable and not running in a terminal window, you can also just run the program without any arguments. You will then be prompted for a file path.

    Usage: **java CMinusLexerDemo _FileName1_ _FileName2_ ... _FileNameK_**
    Usage: **java CMinusLexerDemo**

    #File Listing

    CMinusLexerDemo.java: Class where the main method and program entry-point is located.
    CMinusLexer.java:     Class that represents the vast majority of the lexer logic.

    #Design

    The lexical analyzer opens the file, reads the text in line by line, strips comments/whitespace, and feeds each piece of input to Java's Regular Expression engine to match tokens with their lexemes using RegExr patterns.

#Project 2 - Syntax Analyzer

    * Due:       10/20/2016
    * Submitted: 10/22/2016

    The goal of this project is to verify that the list of tokens passed in from Project 1 are in a syntactically correct order. In order to execute the program, the names of the files containing the text to be scanned must be fed to the program via command-line arguments. If you are GUI capable and not running in a terminal window, you can also just run the program without any arguments. You will then be prompted for a file path.

    Usage: **java CMinusParserDemo _FileName1_ _FileName2_ ... _FileNameK_**
    Usage: **java CMinusParserDemo**

    #File Listing

    CMinusParserDemo.java: Class where the main method and program entry-point is located.
    CMinusParser.java:     Class that represents the vast majority of the parser logic.

    #Design

    This class functions as a generic syntactical analyzer for the C-Minus language. The parser is built in the model of a linear-bounded automaton processing a finite strand of tape which represents the list of tokens. I keep track of which index of the tape I am currently looking at. I can read, write, go forwards, go backwards, or can look back and ahead at any previous or future token, respectively. This effectively parses any language that is LL(k) parsable for any finite constant non-negative integer k. This parser could be made GLL(k) capable by executing all of the calls to the declaration method in parallel.

#Project 3 - Semantic Analyzer

    * Due:       11/10/2016
    * Submitted: 11/12/2016

    The goal of this project is to verify that the list of tokens passed in from Project 2 are in a symantically correct order. In order to execute the program, the names of the files containing the text to be scanned must be fed to the program via command-line arguments. If you are GUI capable and not running in a terminal window, you can also just run the program without any arguments. You will then be prompted for a file path.

    Usage: **java CMinusSemanticsDemo _FileName1_ _FileName2_ ... _FileNameK_**
    Usage: **java CMinusSemanticsDemo**

    #File Listing

    CMinusSemanticsDemo.java: Class where the main method and program entry-point is located.
    CMinusSemantics.java:     Class that represents the vast majority of the semantic logic.

    #Design

    This class functions as a generic semantical analyzer for the C-Minus language.

#Project 4 - Code Generation

    TODO

#Project 5 - YACC/Lex

    TODO
