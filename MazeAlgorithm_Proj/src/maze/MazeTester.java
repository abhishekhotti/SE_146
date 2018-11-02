package maze;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MazeTester {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	public void test() // make all test cases according to canvas
	{
		int size = 1;
		do {
			System.out.println("Please enter the size of the maze between 4-20 only (for example: if you want a 4x4 maze, enter 4)");
			System.out.println("if you would like to exit the maze program, please enter any negative value");
			size = new Scanner(System.in).nextInt();
			if(size > 3 && size < 21)
			{
				DFS_solver maze = new DFS_solver(size);
				maze.printToTextFile("solvedMaze.txt");
				String chkWithMe = "Proj_2/Target"+size+".txt";
				try {
					
					BufferedReader Out=new BufferedReader (new FileReader ("Proj_2/solvedMaze.txt"));
					BufferedReader In=new BufferedReader (new FileReader (chkWithMe));
					String expectedLine = In.readLine();
					String actualLine = Out.readLine();
					while(expectedLine != null) // if the read in line is not null, then continue reading
					{
						assertEquals(expectedLine,actualLine); // checks if each line is the same or not
						expectedLine = In.readLine(); // go to the next line
						actualLine = Out.readLine();
					}
					In.close(); Out.close();
				} catch (IOException e) {
					e.getMessage();
				}
			}
			else if(size<0)
				System.out.println("Goodbye!");
			else
				System.out.println("--> You entered a value outside of the range, please enter an integer value between 4-20 (inclusive) <--\n");
			
		}while(size >= 0);
		
	    
	}


}
