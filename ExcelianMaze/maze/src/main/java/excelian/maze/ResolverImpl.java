package excelian.maze;

import java.util.Optional;
import java.util.logging.Logger;

public class ResolverImpl implements Resolver {
	
	private boolean[][] wasHere;	 // History of cells visited
	private boolean[][] correctPath; // The solution to the maze

	private Maze maze;
		
	@Override
	public Optional<Solution> resolve(final Maze aMaze) {

		maze = aMaze;

		Optional<Solution> s = Optional.empty();
		if (!aMaze.validate())
			return s;
		
		this.wasHere = new boolean[maze.width()][maze.height()];
		this.correctPath = new boolean[maze.width()][maze.height()];
		
	    for (int x = 0; x < maze.width(); x++)  
	        // Sets boolean Arrays to default values
	        for (int y = 0; y < maze.height(); y++){
	            wasHere[x][y] = false;
	            correctPath[x][y] = false;
	        }
	    	    
	    boolean resolved = recursiveSolve(maze.startingPoint());
	    // Will leave you with a boolean array (correctPath) 
	    // with the path indicated by true values.
	    // If b is false, there is no solution to the maze
	    
	    if (resolved) {
	    	Solution solution = new Solution(correctPath, wasHere);
	    	s = Optional.of(solution);
	    }
	    
	    return s;
	}
	
	private boolean recursiveSolve(Cell cell) {

		Logger.getGlobal().fine(() -> "RecursiveSolve for " + cell); 
		
		if (cell.X() == maze.finishPoint().X() && 
			cell.Y() == maze.finishPoint().Y()) 
			return true; 	// Reached the end!
		
	    if (maze.value(cell) == 'X' || wasHere[cell.X()][cell.Y()]) 
	    	return false;   // If you are on a wall or already were here
	    
	    if (maze.value(cell) != 'S')
	    	wasHere[cell.X()][cell.Y()] = true;
	    
	    if (cell.X() != 0) {			  		// Check if not on left edge
	        if (recursiveSolve(cell.left())) { 	// Recalls method one to the left
	    	    if (maze.value(cell) != 'S')
	    	    	correctPath[cell.X()][cell.Y()] = true; // Sets that path value to true;
	            return true;
	        }
	    }

	    if (cell.X() != maze.width() - 1) {		// Checks if not on right edge
	        if (recursiveSolve(cell.right())) { // Recalls method one to the right
	    	    if (maze.value(cell) != 'S')
	    	    	correctPath[cell.X()][cell.Y()] = true;
	            return true;
	        }
	    }
	    
	    if (cell.Y() != 0) { 					// Checks if not on top edge
	        if (recursiveSolve(cell.down())) { 	// Recalls method one down
	    	    if (maze.value(cell) != 'S')
	    	    	correctPath[cell.X()][cell.Y()] = true;
	            return true;
	        }
	    }
	    
	    if (cell.Y() != maze.height() - 1) { 	// Checks if not on bottom edge
	        if (recursiveSolve(cell.up())) { 	// Recalls method one up
	    	    if (maze.value(cell) != 'S')
	    	    	correctPath[cell.X()][cell.Y()] = true;
	            return true;
	        }
	    }
	    
	    return false;
	}

}
