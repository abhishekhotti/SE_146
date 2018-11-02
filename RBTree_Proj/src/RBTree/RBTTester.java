package RBTree;


import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.junit.Test;


public class RBTTester {

	@Test
    //Test the Red Black Tree
	public void test() {
		RedBlackTree rbt = new RedBlackTree();
        rbt.insert("D");
        rbt.insert("B");
        rbt.insert("A");
        rbt.insert("C");
        rbt.insert("F");
        rbt.insert("E");
        rbt.insert("H");
        rbt.insert("G");
        rbt.insert("I");
        rbt.insert("J");
        assertEquals("DBACFEHGIJ", makeString(rbt));
        String str=     "Color: 1, Key:D Parent: \n"+
                        "Color: 1, Key:B Parent: D\n"+
                        "Color: 1, Key:A Parent: B\n"+
                        "Color: 1, Key:C Parent: B\n"+
                        "Color: 1, Key:F Parent: D\n"+
                        "Color: 1, Key:E Parent: F\n"+
                        "Color: 0, Key:H Parent: F\n"+
                        "Color: 1, Key:G Parent: H\n"+
                        "Color: 1, Key:I Parent: H\n"+
                        "Color: 0, Key:J Parent: I\n";
		assertEquals(str, makeStringDetails(rbt));
		rbt.removeTree();
        runThePoem();
    }
	
    
    private void runThePoem() 
    {
    	RedBlackTree rbt2 = new RedBlackTree();
		try {
			BufferedReader In=new BufferedReader (new FileReader ("Proj_RB/Dictionary.txt"));
			String expectedLine = In.readLine();
			int timeStart = (int) System.currentTimeMillis();
			while(expectedLine != null) // if the read in line is not null, then continue reading
			{
				rbt2.insert(expectedLine); // checks if each line is the same or not
				expectedLine = In.readLine(); // go to the next line
			}
			int timeEnd = (int) System.currentTimeMillis();
			int totalTime = timeEnd - timeStart;
			System.out.println("It took "+ totalTime +" milli secs to read in the dictionary.\n");
	    	In = new BufferedReader (new FileReader ("Proj_RB/HottiAbhishekPoem.txt"));
			String chkMe = In.readLine();
			String notFound=""; // holds all the words that were not found
			int timeStartToFind = (int) System.currentTimeMillis();
			while(chkMe != null) // if the read in line is not null, then continue reading
			{
				ArrayList<String> words = extractWords(chkMe);
				for(String x : words)
				{
					x = x.toLowerCase(); // since the dictionary has all lower case words
					// if the last character is not an alphabet, remove the last character
					if(x.length() != 0 && !((x.charAt(x.length()-1)) >= 'a' && (x.charAt(x.length()-1)) <= 'z'))
						x = x.substring(0, x.length()-1);
					RedBlackTree.Node<String> hold = rbt2.lookup(x); 
					if(hold != null) // if the word exists in the RBT, compare it to the value to see if its accurate
						assertEquals(hold.key,x);
					else if(x.length() != 0) // the word was not found, so need to indicate to the user that there is a typo
						notFound = notFound + x +"\n";
				}
				chkMe = In.readLine(); // reads next line
			}
			int timeEndToFind = (int) System.currentTimeMillis();
			int totalTimeToFind = timeEndToFind - timeStartToFind;
			System.out.println("It took "+ totalTimeToFind +" milli secs to search the words of the entire poem in the dictionary.\n\n"
					+ "The words that were not found in the dictionary:");
			System.out.println(notFound);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	//add tester for spell checker
    
    private ArrayList<String> extractWords(String chkMe)
    {
    	ArrayList<String> hold = new ArrayList<>();
    	int start = 0;
    	boolean makeWord = false;
    	chkMe = chkMe.toLowerCase();
    	for(int i = 0; i < chkMe.length(); i++)
    	{
    		if(!(chkMe.charAt(i) >= 'a' && chkMe.charAt(i) <= 'z' || (chkMe.charAt(i)+"").equals("’") || (chkMe.charAt(i)+"").equals("'") ))
    			makeWord = true;
    		if(makeWord == true)
    		{
    			hold.add(chkMe.substring(start, i));
    			start = i+1;
    			makeWord = false;
    		}
    	}
		return hold;
	}


	public static String makeString(RedBlackTree t)
    {
    	
       class MyVisitor implements RedBlackTree.Visitor {
          String result = "";
          public void visit(RedBlackTree.Node n)
          {
             result = result + n.key;
          }
       };
       MyVisitor v = new MyVisitor();
       t.preOrderVisit(v);
       return v.result;
    }

    public static String makeStringDetails(RedBlackTree t) {
    	{
    	       class MyVisitor implements RedBlackTree.Visitor {
    	          String result = "";
    	          int counter = 0;
    	          public void visit(RedBlackTree.Node n)
    	          {
    	        	  if(counter != 0)
    	        		  result = result +"Color: "+n.color+", Key:"+(String)n.key+" Parent: "+(String)n.parent.key+"\n";
    	              else
    	            	  result = result +"Color: "+n.color+", Key:"+(String)n.key+" Parent: \n";	  
    	              counter++;
    	          }
    	       };
    	       MyVisitor v = new MyVisitor();
    	       t.preOrderVisit(v);
    	       return v.result;
    	 }
    }
  // add this in your class  
  //  public static interface Visitor
  //  {
  //  	/**
  //     This method is called at each node.
  //     @param n the visited node
  //  	 */
  //  	void visit(Node n);
  //  }
 
  
  // public void preOrderVisit(Visitor v)
  //  {
  //  	preOrderVisit(root, v);
  //  }
 
 
  // private static void preOrderVisit(Node n, Visitor v)
  //  {
  //  	if (n == null) return;
  //  	v.visit(n);
  //  	preOrderVisit(n.left, v);
  //  	preOrderVisit(n.right, v);
  //  }
    
    
 }
  
