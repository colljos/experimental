package excelian.maze;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class Explorer {
	
	private Cell current;
	private Resolver resolver;
	private Maze maze;
	
	public Explorer(Maze maze, Resolver resolver) {
		this.maze = maze;
		this.resolver = resolver;
		this.current = maze.startingPoint();
	}
	
	public Optional<Solution> resolve() {
		Optional<Solution> solution = resolver.resolve(maze);
		if (solution.isPresent())
			Logger.getGlobal().info(() -> "Resolved Maze successfully");
		
		return solution;
	}
	
	Cell moveUp() {
		return maze.up(current);
	}
	
	Cell moveDown() {
		return  maze.down(current);
	}
	
	Cell moveLeft() {
		return  maze.left(current);
	}
	
	Cell moveRight() {
		return  maze.right(current);
	}
	
	List<Cell> possibleMoves() {
		return  maze.moves(current);
	}

	public Cell current() {
		return current;
	}

	public Cell setCurrent(Cell cell) {
		current = new Cell(cell.X(), cell.Y(), maze.value(cell));
		return current;
	}
}
