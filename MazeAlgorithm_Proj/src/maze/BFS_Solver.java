package maze;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BFS_Solver 
{
	public Vertex[][] brokenMaze;
	public PrintWriter pw;
	private int counter = 0;
	
	public BFS_Solver(Maze x)
	{
		brokenMaze = x.mazeMe;
	}
	
	public BFS_Solver(int s)
	{
		Maze y = new Maze(s);
		y.createMatrix();
		y.breakWalls();
		this.brokenMaze = y.mazeMe;	
	}
	public void findPathThroughMaze() 
	{	
		Vertex currentCell = brokenMaze[0][0];			// start cell
		Vertex targetCell = brokenMaze[brokenMaze.length-1][brokenMaze[0].length-1];	// end cell
		Queue<Vertex> cellsInProgress = new LinkedList<>();	// create stack to keep track of path
		int counter = 1;	// the number of cells being visited
		currentCell.used = counter+"";
		while(currentCell != targetCell)	// keep going until reached target cell
		{
			for(Vertex x : currentCell.paths)	// add all the neighbors of curr cell to the stack
			{
				counter++;
				x.used = counter%10+"";
				x.parent = currentCell;
				cellsInProgress.add(x);
			}
			currentCell = cellsInProgress.poll();	// change current cell to 
		}
		
		printMazeEastSouthFixed();
		pw.println("\n");
		while(currentCell.parent != null)
		{
			currentCell.used = "#";
			currentCell = currentCell.parent;
		}
		
		brokenMaze[0][0].used = "#";
		for(int row = 0; row < brokenMaze.length; row++)
			for(int col = 0; col < brokenMaze[0].length; col++)
				if(brokenMaze[row][col].used!="#")
					brokenMaze[row][col].used = " ";
		System.out.println();
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
			pw.close(); fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void printMazeEastSouthFixed()
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
				if(current.East == true || col == brokenMaze[0].length-1) // prints the east wall of the maze always, 
				{
					pw.printf("%2s",current.used);
					pw.printf("%2s","|");
				}
				else
				{
					if(current.East == false && brokenMaze[row][col+1].used == current.used)
					{
						pw.printf("%2s",current.used);
						pw.printf("%2s",current.used);
					}
					else
					{
						pw.printf("%2s",current.used);
						pw.printf("%2s"," ");
					}
				}				
			}
			pw.println();
			for(int col = 0; col < brokenMaze[0].length; col++)
			{
				Vertex current = brokenMaze[row][col];
				if(current.South == true || row == brokenMaze[0].length - 1)
				{
					if(row == brokenMaze.length-1 && col == brokenMaze[0].length-1 && counter == 1)
						pw.printf("%4s","+ # +");
					else if(row == brokenMaze.length-1 && col == brokenMaze[0].length-1)
						pw.printf("%-4s","+   +");
					else
						pw.printf("%-4s","+ -");
					if(col == brokenMaze[0].length - 1 && row != brokenMaze.length - 1)
						pw.print("+");
				}
				else
				{
					if(current.South == false && brokenMaze[row+1][col].used == current.used)
					{
						if(col == brokenMaze[0].length-1)
							pw.printf("%4s","+ "+current.used+" +");
						else
							pw.printf("%-4s","+ "+current.used);
					}
					else
					{
						if(col == brokenMaze[0].length-1)
							pw.printf("%4s","+   +");
						else
							pw.printf("%-4s","+");
					}
				}
			}
		}
		counter++;
	}

	public void printToTextFile(PrintWriter pw2) 
	{
		pw = pw2;
		findPathThroughMaze();
	}
}
