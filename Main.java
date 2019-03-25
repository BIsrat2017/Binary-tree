import java.io.*;
import java.util.*;
/**
 * the the user interface part of the program QueryTree
 *
 * @author Bisrat Asefaw
 * @version 12/03/2018
 */

public class Main{
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);        
        System.out.println("Welcome to the let me guess what you think." );
        System.out.println();
        System.out.println("Do you want to read in the previous tree? (y/n)?");
        String input = scan.nextLine();
        boolean isValid =validate(input) ; 
        while(!isValid){ 
            System.out.println(" Invalid input \"Please enter (y/n):\"");
            input = scan.nextLine();
            isValid = validate(input);
        }
        QueryTree myTree = new QueryTree();  
        if(input.equals("y") || input.equals("y")){              
            myTree.readIn(new File("QueryTree.txt")); 
        }

        do{  
            System.out.println(" Please think of an object for me to guess.");

            while(myTree.hasNextQuestion()){                                               
                System.out.println(" "+myTree.nextQuestion()+" (y/n)?");                    
                String answer = scan.nextLine();
                isValid = validate(answer);
                while(!isValid){ 
                    System.out.println(" Invalid input \"Please enter (y/n):\"");
                    answer = scan.nextLine();
                    isValid = validate(answer);
                }
                char c = answer.charAt(0);
                myTree.userResponse(c); 
            } 
            System.out.println(" Would your object happen to be " + myTree.finalGuess()+"? (y/n)?");
            String ans = scan.nextLine(); 
            if(ans.equals("N") || ans.equals("n")){ 
                System.out.println(" What is the name of your object? ");
                ans = scan.nextLine();
                while(ans.length()==0){
                    System.out.println(" Opps no name of object detected..");
                    System.out.println(" What is the name of your object? ");
                    ans= scan.nextLine();
                }
                System.out.println(" Please give me a yes/no question that distinguishes between your object and mine-->");               
                String q = scan.nextLine();                
                System.out.println(" And what is the answer for your object? (y/n)?");
                String ch = scan.nextLine();
                isValid = validate(ch);
                while(!isValid){ 
                    System.out.println(" Invalid input \"Please enter (y/n)\"");
                    ch = scan.nextLine();
                    isValid = validate(ch);
                }
                char chAt = ch.charAt(0);
                myTree.updatemyTree(q,ans,chAt); 
            }
            else{
                System.out.println(" Great, I got it right!"); 
            }
            System.out.println();
            System.out.println(" Do you want to go again? (y/n)?" );
            input = scan.nextLine();
            isValid = validate(input); 
            while(!isValid){ 
                System.out.println(" Invalid input \"Please enter (y/n)\"");
                input = scan.nextLine();
                isValid = validate(input);
            }
            myTree.restartTree();           
        }while(input.equals("y") || input.equals("Y"));
        myTree.writeOut(new File("QueryTree.txt" ));
        System.out.println(" See you next,Thanks for playing!"); 
    }

    /**
     * Private validate method that returns true if the input is Y/N not case sensitive
     * @param s the string representation of the user input char or char y/n
     * 
     * @return true if the user input is valid (y/n), false otherwise
     */
    private static boolean  validate(String s){
        String[] charAns = s.split(" ");
        if(charAns.length > 1){
            return false;
        }        
        if(charAns.length==0){
            return false;
        }
        if(charAns[0].length()==0){
            return false;
        }
        if(charAns[0].length() > 1){
            return false;
        }
        char ch = charAns[0].charAt(0);
        if(ch != 'y' && ch != 'Y' && ch != 'n' && ch != 'N'){
            return false; 
        }
        return true;
    }
}

