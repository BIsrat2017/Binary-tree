import java.util.*;
import java.io.*;
/**
 * class that uses tree class implimentation to guess what the user 
 * might thinking. the game gets learned by the user and saves the previos user input 
 * to a file and make the use of the saved anser and question to
 * guess what the next user is thinking.
 * 
 * 
 * @author Bisrat Asefaw
 * @version 11/30/2018
 */

public class QueryTree{
    private QueryTreeNode root; 
    private QueryTreeNode currentNode; 

    /**
     * Constructor that constructs the root of the tree and assignes it to root
     * Constructs new Query myTree, initializes root 
     * answer sets the instance variable currentNode to root
     */
    public QueryTree( ){ 
        root = new QueryTreeNode("Computer");  
        currentNode = root;
    }

    /**
     * method that gets the root of the tree
     * @return root the root of this QueryTree
     */
    public QueryTreeNode getroot(){
        return root;
    }

    /**
     * method that gets the currentNode of the tree
     * 
     * @return currentNode the current node of this QueryTree
     */
    public QueryTreeNode getCurrentNode(){
        return currentNode;
    }

    /**
     * Method that determines if there is a next question
     * by checking if yes/no reference QuerymyTreeNodes
     * are null
     * 
     * @return true if myTree has next question, false otherwise
     */
    public boolean hasNextQuestion(){
        if(currentNode.y == null && currentNode.n == null){
            return false;
        }
        else{
            return true;
        }

    }

    /**Method that returns the next question by using the pointer currentNode
     * Returns next question
     * 
     * 
     * @return currentNode.data which contains the question
     */
    public String nextQuestion( ){
        return currentNode.data;
    }

    /**
     * Method that moves the currentNode or the pointer to left or right depending
     * on the user input y right n left
     * 
     * @param  input user y or n input to geuss
     */
    public void userResponse(char input){
        validate(input + "");

        if(input == 'y' || input == 'Y'){
            currentNode = currentNode.y;  
        }

        if(input == 'n' || input == 'N'){
            currentNode = currentNode.n;  
        }
    }

    /**
     * Method that returns the final guess of the program if the pointer is 
     * in the leaf node returns the data on the leaf node
     * 
     * @return currentNode.data of leaf QuerymyTreeNode with answer
     * 
     */
    public String finalGuess( ){
        if(currentNode.y == null && currentNode.n == null){ 
            return currentNode.data;
        }
        else{
            return "Not final guess";
        }
    }

    /**
     * method that restarts the tree to the beging
     */
    public void restartTree( ){
        currentNode=root;
    }

    /**
     * Update this QueryTree using the given data:q is the new question, 
     * item is the new item that is being added to the QueryTree
     * input is the yes or no answer that leads from this question to the new item.
     * 
     * @param q the new question
     * @param item is the answer to the new question
     * @param input is y/n to deteremine what route to go
     * @throws IllegalArgumentException if the input is valied i.e. y or n
     */
    public void updatemyTree(String q, String item, char input){
        if(!hasNextQuestion()){  
            validate(input +" ");            
            String temp = currentNode.data;
            if(input == 'y' || input == 'Y'){
                currentNode.data = q;
                currentNode.y = new QueryTreeNode(item);  
                currentNode.n  = new QueryTreeNode(temp);
            }

            if(input == 'n' || input == 'N'){
                currentNode.data = q;
                currentNode.y = new QueryTreeNode(temp);  
                currentNode.n  = new QueryTreeNode(item);
            }
            currentNode = root;

        }
        else{
            throw new IllegalStateException();
        }
    }

    /**
     * Private method that validates the user input for the character being vaid for the
     * tree means y or n with out case sensitive
     * 
     * @param s char user input that is changed to string  
     * 
     * @throws IllegalArgumentException if the input char is not y/n 
     */
    private void  validate(String s){
        String[] result = s.split(" ");
        if(result.length > 1){
            throw new IllegalArgumentException();
        }
        if(result[0].length() > 1||result[0].length()==0){
            throw new IllegalArgumentException();
        }
        char ch = result[0].charAt(0);
        if(ch != 'y' && ch != 'Y' && ch != 'n' && ch != 'N'){
            throw new IllegalArgumentException(); 
        }

    }

    /**
     * Method that Reads in a new set of questions/items into this QueryTree from the given
     * file, replacing whatever was currently in the QueryTree
     * 
     * @param f file that the program is reading to construct the QueryTree
     * @throws IOException if there is any problem incountered with the file
     */
    public void readIn(File f){
        try{
            Scanner inputfile = new Scanner(f);
            root = preOrder(inputfile); 
            currentNode = root;
        }
        catch(IOException d){
            System.out.println("file read problem");
        }
    }

    /**
     * Private ricursive method that used to read from file and creat the 
     * Querytree 
     * 
     * @param input Scanned file
     * @return QuerymyTreeNode the root of the constructed QueryTree 
     */
    private QueryTreeNode preOrder(Scanner input){
        String QueryTreeNodeType = input.nextLine();
        if(QueryTreeNodeType.equals("A:")){ 
            String QueryTreeNodeText = input.nextLine();
            QueryTreeNode newTree = new QueryTreeNode(QueryTreeNodeText); 
            return newTree;
        }
        else{
            String QueryTreeNodeText = input.nextLine(); 
            QueryTreeNode newTree = new QueryTreeNode(QueryTreeNodeText); 
            newTree.n = preOrder(input);  
            newTree.y = preOrder(input);
            return newTree;
        }
    }

    /**
     * Method that Writes the current set of questions/items in this QueryTree 
     * out to the given file. Does not change the current state.
     *
     * @param f file that the current QueryTree is to be written in
     * @throws IOException if there is any problem in file  
     */
    public void writeOut(File f){
        try{
            PrintStream output = new PrintStream(f); 
            writeOut(output, root); 

        }
        catch(IOException d){ 
            System.out.println("Error in write out file ");
        }
    }

    /**
     * Private helper method that uses recursive to write out into a file 
     * 
     * @param out file the file to save the QuerryTrees data 
     * 
     * @param head QueryTreeNode the head of the current QuerryTree
     */
    private void writeOut(PrintStream out, QueryTreeNode head){
        if(head.n == null && head.y == null){  
            out.println("A:");
            out.println(head.data);
        }
        else{
            out.println("Q:"); 
            out.println(head.data);
            writeOut(out, head.n);
            writeOut(out, head.y);
        }
    }

}

/**
 * QuerymyTreeNode class 
 */
class QueryTreeNode {
    
    public  String data; 
    public QueryTreeNode y;
    public QueryTreeNode n;
    /**QuerymyTreeNode constructor that takes a String data , QueryTreeNode left N and 
     * QueryTreeNode right Y and creates a node 
     * 
     */
    public QueryTreeNode(String data, QueryTreeNode y, QueryTreeNode n) {
        this.data = data; 
        this.y = y;
        this.n = n;
    }

    /**
     * QuerymyTreeNode constructor that takes a String data , QueryTreeNode left Null and 
     * QueryTreeNode right null and creates a leaf 
     */

    public QueryTreeNode(String data){
        this(data, null,null);
    }

}