package maze;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Stack;

public class DFS_solver 
{
	public Vertex[][] brokenMaze;
	public PrintWriter pw;
	private int counter = 0;
	
	public DFS_solver(Maze x)
	{
		brokenMaze = x.mazeMe;
	}
	
	public DFS_solver(int s)
	{
		Maze y = new Maze(s);
		y.createMatrix();
		y.breakWalls();
		this.brokenMaze = y.mazeMe;
	}

	public void findPathThroughMaze() 
	{	
		Vertex currentCell = brokenMaze[0][0];
		Vertex targetCell = brokenMaze[brokenMaze.length-1][brokenMaze[0].length-1];
		
		Stack<Vertex> cellsInProgress = new Stack<>();
		int counter = 1;
		currentCell.used = counter+"";
		while(currentCell != targetCell) // loop to keep going until the perfect path is found
		{
			if(currentCell.paths.size() == 0) // if the currentCell is blocked off, then backtrack
			{
				Vertex temp = currentCell;
				currentCell = cellsInProgress.pop();
				currentCell.paths.remove(temp); // remove the association of the blocked off cell and its parent
			}
			else
			{
				counter++;
				Vertex temp = currentCell.paths.get(0); // look at all the neighbors of the cell
				temp.used = counter%10+""; 
				cellsInProgress.push(currentCell); // if there is a neighbor, put currentcell on the stack, and do DFS
				currentCell = temp;
			}
		}
		
		System.out.println();
		printMazeEastSouthFixed();
		for(Vertex x : cellsInProgress)
		{
			x.used = "#"; // indicates the path
		}
		targetCell.used = "#";
		pw.println();
		for(int row = 0; row < brokenMaze.length; row++)
			for(int col = 0; col < brokenMaze[0].length; col++)
				if(brokenMaze[row][col].used!="#")
					brokenMaze[row][col].used = " "; // sets the vertex's that are not in the path to be empty to not be included in the final output
		printMazeEastSouthFixed();
		
	}

	public void printToTextFile(String string) 
	{
		File outputFile = new File("Proj_2/" + string); // print out to this file
		FileWriter fw; 
		try {
			fw = new FileWriter(outputFile);
			pw = new PrintWriter(fw);
			pw.println("Graph size: "+brokenMaze.length+"\n");
			pw.println("Maze");
			printMazeEastSouthFixed();
			pw.println();
			pw.println("DFS:");
			findPathThroughMaze();
			BFS_Solver mazeBFS = new BFS_Solver(brokenMaze.length);
			pw.println("\nBFS:");
			mazeBFS.printToTextFile(pw);
			pw.close(); fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void printMazeEastSouthFixed() // please read this to understand how I am printing to textFile
	{
		for(int row = 0; row < brokenMaze.length; row++)
		{
			if(row == 0)
			{
				for(int col = 0; col < brokenMaze[0].length; col++) // prints the north border of the maze
				{
					if(row == 0 && col == 0)
					{
						pw.printf("%-4s","+");
					}
					else if(col == brokenMaze[0].length-1) 
						pw.printf("%3s","+ - +");
					else
						pw.printf("%-4s","+ -");
				}
			}
			pw.println("");
			pw.print("|"); // always needs to have a west wall at the border of the maze
			for(int col = 0; col < brokenMaze[0].length; col++)
			{
				Vertex current = brokenMaze[row][col];
				if(current.East == true || col == brokenMaze[0].length-1) // prints the east wall of the maze if there is a wall there 
				{
					pw.printf("%2s",current.used); // prints the used value of the vertex, ie the path
					pw.printf("%2s","|"); // west wall
				}
				else
				{
					if(current.East == false && brokenMaze[row][col+1].used == current.used) // if the wall is not there, and if this path has a hash, and the west neighbor is also hashed, means this is the path
					{
						pw.printf("%2s",current.used); // prints the hashes on the current location
						pw.printf("%2s",current.used); // prints the hash at the location of the west wall
					}
					else
					{
						pw.printf("%2s",current.used); // prints the path number
						pw.printf("%2s"," "); // indicates an no wall between this and its west neighbor
					}
				}				
			}
			pw.println();
			for(int col = 0; col < brokenMaze[0].length; col++)
			{
				Vertex current = brokenMaze[row][col];
				if(current.South == true || row == brokenMaze[0].length - 1)
				{
					if(row == brokenMaze.length-1 && col == brokenMaze[0].length-1) // prints the south wall
					{
						if(counter != 2) // this is if the program is in the first and second iteration, so no path has been found yet
							pw.printf("%4s","+   +");
						else
							pw.printf("%4s","+ # +"); //prints a hash on the third iteration, when the path is displayed
					}
					else
						pw.printf("%-4s","+ -"); // prints the south wall
					if(col == brokenMaze[0].length - 1 && row != brokenMaze.length - 1)
						pw.print("+"); 
				}
				else
				{
					if(current.South == false && brokenMaze[row+1][col].used == current.used)// if the wall is not there, and if this path has a hash, and the south neighbor is also hashed, means this is the path
					{
						if(col == brokenMaze[0].length-1)
							pw.printf("%4s","+ "+current.used+" +");
						else
							pw.printf("%-4s","+ "+current.used);
					}
					else // this is if there a path between this cell and the south neighbor but we are not printing hashes yet
					{
						if(col == brokenMaze[0].length-1)
							pw.printf("%4s","+   +");
						else
							pw.printf("%-4s","+");
					}
				}
			}
		}
		pw.println();
		counter++;
	}
}
