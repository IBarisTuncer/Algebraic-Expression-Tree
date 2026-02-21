# Algebraic-Expression-Tree
A Java implementation of a binary expression tree that constructs a tree structure from a prefix expression and performs various operations on it, including expression form conversion and arithmetic evaluation.

# Overview
This project demonstrates the use of binary trees and stack-based parsing to represent and evaluate algebraic expressions. Given a valid prefix expression, the program constructs an expression tree and is capable of outputting the expression in all three standard notations as well as computing the final numeric result.

# Features

 - Constructs a binary expression tree from a prefix expression string  
 - Converts and displays the expression in prefix, infix, and postfix notation  
 - Evaluates the expression and prints the numeric result  
 - Accepts user-provided expressions at runtime or falls back to a built-in default
 - Validates input and provides descriptive error messages for malformed expressions  

# Limitations

 - Only single-digit numbers (0–9) are supported  
 - Only the four basic arithmetic operators are supported: +, -, *, /  
    
The user is asked whether they want to enter their own prefix expression or use the default sample.  
Default Sample: "+-+7*/935/82*/625"
