package maze;

import java.util.ArrayList;

public class Vertex 
{
	boolean North, South, East, West;
	int row, col, value;
	CheckColor color;
	ArrayList<Vertex> paths;
	String used;
	Vertex parent;
	
	public Vertex(int val, int r, int c)
	{
		value = val;
		North = South = East = West = true;
		row = r;
		col = c;
		color = CheckColor.WHITE;
		paths = new ArrayList<>();
		used=" ";
		parent = null;
	}
	
	public boolean hasAllWalls()	// easy way to check if a cell is currently inaccessible and needs to accessed
	{
		if(North == false)
			return false;
		if(South == false)
			return false;
		if(East == false)
			return false;
		if(West == false)
			return false;
		return true;
	}
	
	public CheckColor myColor()
	{
		return color;
	}
	
	public void changingColor()	// set the flag
	{
		if(color == CheckColor.WHITE)
			color = CheckColor.GRAY;
		else if(color == CheckColor.GRAY)
			color = CheckColor.BLACK;
	}
	
	public enum CheckColor	// white == not accessed ever, gray == still in progress, black == all neighbours found
	{
		BLACK, WHITE, GRAY;
	}
	
	public String toString() 
	{
		if(North == true)
			System.out.printf("%7s","+ --- +");
		System.out.println();
		if(value < 100)
		{
			if(West == true)
				System.out.printf("%s","|");
			else
				System.out.print(" ");
			System.out.printf("%3d",value);
			if(East == true)
				System.out.printf("%3s","|");
			System.out.println();
			if(South == true)
				System.out.printf("%7s","+ --- +");
			System.out.println();
		}
		else if(value >= 100)
		{
			if(West == true)
				System.out.printf("%s","|");
			else
				System.out.print(" ");
			System.out.printf("%4d",value);
			if(East == true)
				System.out.printf("%2s","|");
			System.out.println();
			if(South == true)
				System.out.printf("%7s","+ --- +");
			System.out.println();
		}
		return "";
	}
	
}
