package excelian.maze;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

public class ExplorerTest {

	private static final String TEST_MAZE4x4SINGLESOL = "4x4MazeSingleSolution.txt";	
	private static final String TEST_MAZE4x4NOSOL = "4x4MazeNoSolution.txt";
	private static final String TEST_MAZE5x5MULTISOL = "5x5MazeMultipleSolutions.txt";
	
	private Explorer explorer;
	private Maze maze;
	
	@Before
	public void setUp() throws Exception {
		
		maze = new Maze(TEST_MAZE4x4SINGLESOL);
		assertNotNull(maze);
		
		Resolver resolver = new ResolverImpl();
		explorer = new Explorer(maze, resolver);
		assertNotNull(explorer);
	}
	
	@Test
	public void testMoveUp() {
		
		Cell cell = explorer.current();
		System.out.println("Current: " + cell);
		
		Cell newCell = explorer.moveUp();
		System.out.println("New cell: " + newCell);

		assertEquals(2, newCell.X());
		
		assertEquals(2, explorer.moveUp().X());
	}
	
	@Test
	public void testMoveDown() {
		Cell cell = explorer.setCurrent(new Cell(0,2));
		System.out.println("Current: " + cell);
					
		assertEquals(1, explorer.moveDown().X());
		assertEquals(1, explorer.moveDown().X());		
	}
	
	@Test
	public void testMoveLeft() {
		Cell cell = explorer.setCurrent(new Cell(1,2));
		System.out.println("Current: " + cell);
					
		assertEquals(1, explorer.moveLeft().Y());
		assertEquals(1, explorer.moveLeft().Y());
	}
	
	@Test
	public void testMoveRight() {
		Cell cell = explorer.setCurrent(new Cell(2,0));
		System.out.println("Current: " + cell);
					
		assertEquals(1, explorer.moveRight().Y());
		assertEquals(1, explorer.moveRight().Y());
	}
	
	@Test
	public void testPossibleMoves() {
		
		Cell cell = explorer.setCurrent(new Cell(2,1));
		System.out.println("Current: " + cell);
					
		assertEquals(2, explorer.possibleMoves().size());
	}
	
	@Test
	public void testExploreMaze() {

		Optional<Solution> solution = explorer.resolve();
		assertTrue(solution.isPresent());

		System.out.println(" ================ SOLUTION ==============");

		maze.printSolution(solution.get());
	}
	
	@Test
	public void testExploreMazeMulti() {

		Maze maze = new Maze(TEST_MAZE5x5MULTISOL);
		assertNotNull(maze);
		
		Resolver resolver = new ResolverImpl();
		explorer = new Explorer(maze, resolver);
		assertNotNull(explorer);
		
		Optional<Solution> solution = explorer.resolve();
		assertTrue(solution.isPresent());

		System.out.println(" ================ SOLUTION ==============");

		maze.printSolution(solution.get());
	}

	@Test
	public void testExploreMazeNone() {

		Maze maze = new Maze(TEST_MAZE4x4NOSOL);
		assertNotNull(maze);
		
		Resolver resolver = new ResolverImpl();
		explorer = new Explorer(maze, resolver);
		assertNotNull(explorer);

		Optional<Solution> solution = explorer.resolve();
		assertFalse(solution.isPresent());

	}
	
}
