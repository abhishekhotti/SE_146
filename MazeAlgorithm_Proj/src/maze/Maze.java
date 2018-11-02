package maze;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Maze 
{
	public int size;
	Vertex[][] mazeMe;
	private Random myRandGen;

	public Maze(int s) 
	{
		size = s;
		myRandGen = new java.util.Random(0);
	}

	public Maze() 
	{
		size = 3;
		myRandGen = new java.util.Random(0);
	}
	
	public void createMatrix() 
	{
		mazeMe = new Vertex[size][size]; // creates a matrix of size x size
		
		for(int row = 0; row < size; row++)
			for(int col = 0; col < size; col++)
			{
				mazeMe[row][col] = new Vertex(row*size+1+col,row,col); 	// populate each cell with a vertex which 
																		// has fields to store neighbor information
			}
	}	
		
	public void printMazeEastSouth()	// use method for debugging by printing output to terminal
	{
		for(int row = 0; row < size; row++)
		{
			if(row == 0)
			{
				for(int col = 0; col < size; col++) // prints the north border of the maze
				{
					if(row == 0 && col == 0)
					{
						System.out.printf("%-4s","+");
					}
					else if(col == size-1)
						System.out.printf("%3s","+ - +");
					else
						System.out.printf("%-4s","+ -");
				}
			}
			System.out.println("");
			System.out.print("|"); // always needs to have a west wall at the border of the maze
			for(int col = 0; col < size; col++)
			{
				Vertex current = mazeMe[row][col];
				if(current.value < 100)
				{
					if(current.East == true || col == size-1) // prints the east wall of the maze always, 
					{
						System.out.printf("%3s","");
						System.out.printf("%1s","|");
					}
					else
					{
						System.out.printf("%2s","");
						System.out.printf("%2s"," ");
					}
				}
				else
				{
					if(current.East == true || col == size-1)
					{
						System.out.printf("%3s","");
						System.out.printf("%1s","|");
					}
					else
					{
						System.out.printf("%2s","");
						System.out.printf("%2s"," ");
					}
				}
				
			}
			System.out.println();
			for(int col = 0; col < size; col++)
			{
				Vertex current = mazeMe[row][col];
				if(current.South == true || row == size - 1)
				{
					if(row == size-1 && col == size-1)
						System.out.printf("%4s","+   +");
					else
						System.out.printf("%-4s","+ -");
					if(col == size - 1 && row != size - 1)
						System.out.print("+");
				}
				else
				{
					if(col == size-1)
						System.out.printf("%4s","+   +");
					else
						System.out.printf("%-4s","+");
				}
			}
		}
		System.out.println("");
	}

	public void breakWalls()			// create the maze method by breaking walls between vertexes and update vertex neighbors
	{
		Vertex currentCell = mazeMe[0][0];				
		Vertex targetCell = mazeMe[size-1][size-1];		
		Stack<Vertex> cellsInProgress = new Stack<>();
		int totalCells = size*size;
		int visitedCells = 1;
		while(totalCells > visitedCells) // keep going until total < visitedCels, means all cells can be accessed
		{
			Vertex north = getNorthNeighbor(currentCell);
			Vertex west = getWestNeighbor(currentCell);
			Vertex east = getEastNeighbor(currentCell);
			Vertex south = getSouthNeighbor(currentCell);
			ArrayList<Vertex> surroundings = new ArrayList<>();
			
			// check for corner case and if that neighboring cell does not have a path, then add it to the possible wall to be broken
			if(north != null && north.hasAllWalls())			 
				surroundings.add(north);
			if(south != null && south.hasAllWalls())
				surroundings.add(south);
			if(west != null && west.hasAllWalls())
				surroundings.add(west);
			if(east != null && east.hasAllWalls())
				surroundings.add(east);
			
			// at a corner case, or reached targetCell, so pop off prev cell to see if maze can be continued
			if(surroundings.size() == 0 || currentCell == targetCell)	
			{
				currentCell = cellsInProgress.pop();
			}
			else
			{
				Vertex temp = surroundings.get((int)(myrandom()*surroundings.size()));
				currentCell.paths.add(temp);	// the path from currentCell to the nextCell
				if(temp == north)
				{
					currentCell.North = false;
					temp.South = false;
				}
				else if(temp == south)
				{
					currentCell.South = false;
					temp.North = false;
				}
				else if(temp == west)
				{
					currentCell.West = false;
					temp.East = false;
				}
				else if(temp == east)
				{
					currentCell.East = false;
					temp.West = false;
				}
				cellsInProgress.push(currentCell); // store currCell in stack because there might be other cells with all walls still intact you need to access
				currentCell = temp;
				visitedCells++;
			}
			
		}
		mazeMe[0][0].North = false;
		mazeMe[size-1][size-1].South = false;
		//printMazeEastSouth();
	}
	
	public double myrandom()
	{
		return myRandGen.nextDouble();
	}
	public Vertex getNorthNeighbor(Vertex mine)
	{
		if(mine.row != 0)
			return mazeMe[mine.row-1][mine.col];
		return null;
	}
	public Vertex getEastNeighbor(Vertex mine)
	{
		if(mine.col != size-1)
			return mazeMe[mine.row][mine.col+1];
		return null;
	}
	public Vertex getSouthNeighbor(Vertex mine)
	{
		if(mine.row != size-1)
			return mazeMe[mine.row+1][mine.col];
		return null;
	}
	public Vertex getWestNeighbor(Vertex mine)
	{
		if(mine.col != 0)
			return mazeMe[mine.row][mine.col-1];
		return null;
	}
	
	public static void main (String []args)
	{
		Maze x = new Maze(5);
		x.createMatrix();
		//x.breakWalls();
		x.printMazeEastSouth();
	}
}
