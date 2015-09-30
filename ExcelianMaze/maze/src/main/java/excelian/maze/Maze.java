package excelian.maze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class Maze {
	
	private char maze[][];
	private int wallCount, spaceCount;

	public Maze(String filename) {
		maze = new MazeReader(filename).read();
	}

	public Maze(char maze[][]) {
		this.maze = maze;		
	}
	
	
	public boolean validate() {
		
		boolean start = false, finish = false;
		
		for (int x = 0; x < maze.length; x++) {
			for (int y = 0; y < maze[0].length; y++) { 
				if (maze[x][y] == ' ' ||
					maze[x][y] == 'X' ||
					(maze[x][y] == 'S' && !start) ||
					(maze[x][y] == 'F' && !finish)) {
					if (maze[x][y] == 'S') start = true;
					if (maze[x][y] == 'F') finish = true;
					continue;
				}
				return false;
			}	
		}
		
		return true;
	}
	
	public int countWalls() {
		
		if (wallCount == 0) {
			wallCount = charCount('X');
		}

		return wallCount;
	}
	
	public int countSpaces() {
		if (spaceCount == 0) {
			spaceCount = charCount(' ');
		}

		return spaceCount;
	}
	
	public char checkCoordinate(Cell cell) {
		
		if (cell.X() < 0 || cell.X() > width()-1)
			throw new RuntimeException("Invalid X coordinate " + cell.X());

		if (cell.Y() < 0 || cell.Y() > height()-1)
			throw new RuntimeException("Invalid Y coordinate " + cell.X());

		return maze[cell.X()][cell.Y()];
	}
	
	private int charCount(char c) {
		int count = 0;
		for (int x = 0; x < maze.length; x++) {
			for (int y = 0; y < maze[0].length; y++) { 
				if	(maze[x][y] == c)
					count++;
			}	
		}			
		return count;
	}
	
	public Cell startingPoint() {
		return cellFor('S');		
	}
	
	public Cell finishPoint() {
		return cellFor('F');		
	}

	private Cell cellFor(char c) {

		for (int x = 0; x < maze.length; x++) {
			for (int y = 0; y < maze[0].length; y++) {
				if (maze[x][y] == c)
					return new Cell(x, y,c);
			}	
		}
		return new Cell(0,0,c);
	}

	public Cell up(Cell current) {
				
		if (current.X() > 0) {
			Cell next = new Cell(current.X()-1, current.Y(), 
								 maze[current.X()-1][current.Y()]);
			System.out.println("next: " + next);
			if (next.value() == ' ')
				return next;
		}
		
		return current;
	}
	
	
	public Cell down(Cell current) {
		if (current.X() < height()-1) {
			Cell next = new Cell(current.X()+1, current.Y(), 
								 maze[current.X()+1][current.Y()]);
			System.out.println("next: " + next);
			if (next.value() == ' ')
				return next;
		}
		
		return current;
	}
	
	public Cell left(Cell current) {
		if (current.Y() > 0) {
			Cell next = new Cell(current.X(), current.Y()-1, 
								 maze[current.X()][current.Y()-1]);
			System.out.println("next: " + next);
			if (next.value() == ' ')
				return next;
		}
		
		return current;
	}

	public Cell right(Cell current) {
		
		if (current.Y() < width()-1) {
			Cell next = new Cell(current.X(), current.Y()+1, 
								 maze[current.X()][current.Y()+1]);
			System.out.println("next: " + next);
			if (next.value() == ' ')
				return next;
		}
		
		return current;
	}
	
	public List<Cell> moves(Cell current) {
		
		Set<Cell> moves = new HashSet<>();
		
		moves.add(up(current));
		moves.add(down(current));
		moves.add(left(current));
		moves.add(right(current));
		moves.remove(current);
		
		return new ArrayList<Cell>(moves);
	}
	
	public int width() {
		return maze.length;
	}

	public int height() {
		return maze[0].length;
	}

	public char value(Cell cell) {
		return maze[cell.X()][cell.Y()];
	}

	@Override
	public String toString() {
		return "Maze [maze=" + Arrays.toString(maze) + ", wallCount=" + wallCount + ", spaceCount=" + spaceCount + "]";
	}

	public void printCoordinates() {
		for (int x = 0; x < maze.length; x++) {
			for (int y = 0; y < maze[0].length; y++) {
				Logger.getGlobal().info("X="+x+",Y="+y+",Value="+maze[x][y]);
			}	
		}
	}

	public void printSolution(Solution s) {

		for (int x = 0; x < maze.length; x++) {
			for (int y = 0; y < maze[0].length; y++) {
				if (s.path()[x][y] == true)
					System.out.print('*');
				else 
					System.out.print(maze[x][y]);
			}	
			System.out.println();
		}
	}
}
