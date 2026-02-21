//-----------------------------------------------------
// Title: AlgExpressionTree class
// Author: Barış Tuncer
// Description: This class contains the whole program. It creates an algebraic expression tree from a given 
// prefix expression string and performs operations on it, including calculating the result and 
// displaying the prefix, infix and postfix forms of the given expression.
//-----------------------------------------------------

package homework3;

import java.util.Scanner;

//the class that encompasses the implementation
class AlgExpressionTree
{
    //the Node class creates the individual nodes in the binary expression tree
	//Each node holds a character (digit or operator) and references to left and right children
    class Node
    {    
        char data;
        Node left, right;
 
        // Constructor for node
        public Node(char data)
        {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    } 
 
    // The StackNode class is used to implement a linkedlist based stack
    class StackNode
    {
        Node treeNode;
        StackNode next;
 
        // Constructor for stack
        public StackNode(Node treeNode)
        {
            this.treeNode = treeNode;
            next = null;
        }
    }
    
    // Top of the stack, used in tree construction
    private StackNode root;
 
    
    // Constructor for AlgExpressionTree class. creates a root that will further be extended upon separately
    public AlgExpressionTree()
    {
        root = null;
    }
 

    // pushes a tree node to the top of the stack
    private void push(Node ptr)
    {
        if (root == null)
            root = new StackNode(ptr);
        else
        {
            StackNode nptr = new StackNode(ptr);
            nptr.next = root;
            root = nptr;
        }
    }
 
    // Pops and returns the node at the top of the stack
    // throws exception if the stack is empty
    private Node pop()
    {
        if (root == null)
            throw new RuntimeException("Underflow");
        else
        {
            Node ptr = root.treeNode;
            root = root.next;
            return ptr;
        }
    }
 
    // returns the node at the top of the stack
    private Node getRoot()
    {
        return root.treeNode;
    }
 
    // inserts a character into the expression tree
    // If the character is a digit, the character is pushed into the stack
    // If it is an operator, its left and right children are popped and the operator is 
    // pushed into the stack
    private void insert(char val)
    {

        if (isDigit(val))
        {
            Node nptr = new Node(val);
            push(nptr);
        }
        else if (isOperator(val))
        {
            Node nptr = new Node(val);
            nptr.left = pop();
            nptr.right = pop();
            push(nptr);
        }
        else
        {
        	throw new IllegalArgumentException("Invalid character in expression '" + val + "'");
        }
        

    }
 
    // returns true if the given char is a digit
    private boolean isDigit(char ch)
    {
        return (ch >= '0' && ch <= '9');
    }
 
    // returns true if the given char is an operator
    private boolean isOperator(char ch)
    {
        return (ch == '+' || ch == '-' || ch == '*' || ch == '/');
    }
 
    // converts the given char into its integer value
    private int toDigit(char ch)
    {
        return (ch - '0');
    }
 
    
    // builds the binary expression tree from the given prefix expression string
    // iterates through the string in reverse order and calls insert() for each character
    public void buildTree(String s)
    {
    	
    	if(s == null || s.isEmpty())
    	{
    	throw new IllegalArgumentException("Expression string cant be null or empty");	
    	}
    	
        for (int i = s.length() - 1; i >= 0; i--)
            insert(s.charAt(i));
    }
    
    
 
    //sends to the private recursive helper method starting from the root node
    public double evaluate()
    {
        return evaluate(getRoot());
    }
 
    // recursively evaluates the expression tree rooted at the given node
    // If the node is a leaf, the digit value is returned
    // Otherwise, evaluates both subtrees recursively and applies the operator at the current node
    private double evaluate(Node nd)
    {
        if (nd.left == null && nd.right == null)
            return toDigit(nd.data);
        else
        {
            double result = 0.0;
            double left = evaluate(nd.left);		//recursively evaluates left subtree
            double right = evaluate(nd.right);		//recursively evaluates right subtree
            char operator = nd.data;
 
            switch (operator)						//determines which operation to apply
            {										//for every different operator, intended operation is used and result is returned, continuing the recursion until the final result
            case '+' : result = left + right; break;
            case '-' : result = left - right; break;
            case '*' : result = left * right; break;
            case '/' : result = left / right; break;
            default  : throw new IllegalStateException("Unknown operator: '" + operator + "'");
            }
            return result;
        }
    }
    
    
    
    // public entry for displaying the prefix form of the expression tree
    public void displayPrefix()
    {
        preOrder(getRoot());
    }
 
    // recursively traverses the tree in pre-order. this gives the prefix form of the expression
    private void preOrder(Node nd)
    {
        if (nd != null)
        {
            System.out.print(nd.data);
            preOrder(nd.left);
            preOrder(nd.right);            
        }    
    }
    

    // public entry for displaying the infix form of the expression tree
    public void displayInfix()
    {
        inOrder(getRoot());
    }
 
    // recursively traverses the tree in in-order. this gives the infix form of the expression
    private void inOrder(Node nd)
    {
        if (nd != null)
        {
            inOrder(nd.left);
            System.out.print(nd.data);
            inOrder(nd.right);            
        }    
    }
 
    
    // public entry for displaying the postfix form of the expression tree
    public void displayPostfix()
    {
        postOrder(getRoot());
    }
 
    // recursively traverses the tree in post-order. this gives the postfix form of the expression
    private void postOrder(Node nd)
    {
        if (nd != null)
        {
            postOrder(nd.left);            
            postOrder(nd.right);
            System.out.print(nd.data);            
        }    
    }
 

    
    // helper method that builds the tree from the given expression, then displays
    // its prefix, infix, and postfix forms and prints the evaluated result
    private static void runTree(String expression)
    {
        try
        {
            AlgExpressionTree T = new AlgExpressionTree();	// creates a new AlgExpressionTree instance
            T.buildTree(expression);						// builds the binary expression tree from the prefix string

            System.out.print("\n\nPrefix form: ");
            T.displayPrefix();								// displays the expression in prefix form with pre-order traversal

            System.out.print("\n\nInfix form: ");
            T.displayInfix();								// displays the expression in infix form with in-order traversal

            System.out.print("\n\nPostfix form: ");
            T.displayPostfix();								// displays the expression in postfix form with post-order traversal

            System.out.println("\n\nResult for the expression " + expression + " is: " + T.evaluate());	// evaluates and prints the final result
        }
        catch (RuntimeException e)
        {
            // catches malformed or invalid expressions and prints a friendly error message
            System.out.println("\nError: " + e.getMessage());
            System.out.println("Please make sure your expression is a valid prefix expression using single digits (0-9) and operators (+, -, *, /).");
        }
    }
    
    
    
    // main method. asks the user whether they want to enter their own prefix expression
    // or use the built-in sample. Note: only single-digit numbers (0-9) are supported.
    public static void main(String[] args)
    {
    	String prefixinput1 = "+-+7*/935/82*/625";		// sample prefix expression to build the tree from

    	Scanner n = new Scanner(System.in);
    	
        System.out.println("Would you like to enter your own prefix expression? (yes/no)");
        System.out.println("Note: only single-digit numbers (0-9) and operators (+, -, *, /) are supported.");
        String answer = n.nextLine().trim().toLowerCase();
        
        if (answer.equals("yes") || answer.equals("y"))
        {
            														// user chose to provide their own expression
            System.out.println("Enter your prefix expression:");
            String userInput = n.nextLine().trim();
            runTree(userInput);
        }
        else
        {
            // user chose to use the default sample expression
            System.out.println("Using the default expression: " + prefixinput1);
            runTree(prefixinput1);
        }

        
    }
    
    
    
}
 

