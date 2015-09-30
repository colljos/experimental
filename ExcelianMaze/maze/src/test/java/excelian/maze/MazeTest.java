package excelian.maze;

import static org.junit.Assert.*;

import org.junit.Test;

public class MazeTest {

	private static final String TEST_MAZE3x3 = "3x3Maze.txt";
	private static final String TEST_MAZE4x4NOSOL = "4x4MazeNoSolution.txt";
	private static final String TEST_MAZE4x4SINGLESOL = "4x4MazeSingleSolution.txt";
	
	private static final char[][] TEST_MAZE = {
			{'X','X',' ','F'},
			{'X',' ',' ','X'},
			{' ',' ','X','X'},
			{'S','X','X','X'}			
	};
	
	private static final char[][] TEST_BAD_MAZE = {
			{'X','X',' ','F'},
			{'X',' ',' ','X','X'},
			{' ','S','X','X'},
			{'S','X','X','X'}			
	};
	
	@Test(expected=RuntimeException.class)
	public void loadMazeMissingFilename() {
		new Maze("garbage");
	}
	
	@Test
	public void loadMazeInvalid() {
		Maze maze = new Maze(TEST_MAZE3x3);
		System.out.println(maze);
		assertFalse(maze.validate());
		
		maze = new Maze(TEST_BAD_MAZE);
		System.out.println(maze);
		assertFalse(maze.validate());
	}

	@Test
	public void loadMazeValid() {
		Maze maze = new Maze(TEST_MAZE4x4NOSOL);
		assertTrue(maze.validate());
	}
	
	@Test
	public void countWalls() {
		Maze maze = new Maze(TEST_MAZE4x4NOSOL);
		assertEquals(10, maze.countWalls());
	}
	
	@Test
	public void countSpaces() {
		Maze maze = new Maze(TEST_MAZE4x4NOSOL);
		assertEquals(4, maze.countSpaces());
	}
		
	@Test
	public void checkCoordinate() {
		
		Maze maze = new Maze(TEST_MAZE4x4NOSOL);
		assertEquals('F', maze.checkCoordinate(new Cell(0,3)));
		assertEquals('S', maze.checkCoordinate(new Cell(3,0)));
		assertEquals(' ', maze.checkCoordinate(new Cell(1,2)));
		assertEquals('X', maze.checkCoordinate(new Cell(2,3)));
	}
	
	@Test(expected=RuntimeException.class)
	public void checkInvalidCoordinates1() {

		Maze maze = new Maze(TEST_MAZE4x4NOSOL);
		assertEquals(' ', maze.checkCoordinate(new Cell(-1,3)));
	}
	
	@Test(expected=RuntimeException.class)
	public void checkInvalidCoordinates2() {

		Maze maze = new Maze(TEST_MAZE4x4NOSOL);
		assertEquals(' ', maze.checkCoordinate(new Cell(3,-1)));
	}
	
	@Test(expected=RuntimeException.class)
	public void checkInvalidCoordinates3() {

		Maze maze = new Maze(TEST_MAZE4x4NOSOL);
		assertEquals(' ', maze.checkCoordinate(new Cell(4,3)));
	}
	
	@Test(expected=RuntimeException.class)
	public void checkInvalidCoordinates4() {

		Maze maze = new Maze(TEST_MAZE4x4NOSOL);
		assertEquals(' ', maze.checkCoordinate(new Cell(3,4)));
	}
	
	@Test
	public void testMazeCoordinates() {

		System.out.println("============== TEST_MAZE3x3 ===========");

		Maze maze = new Maze(TEST_MAZE3x3);
		assertNotNull(maze);
		
		System.out.println(maze);
		maze.printCoordinates();
		
		System.out.println("============== TEST_MAZE ===========");
		
		Maze anotherMaze = new Maze(TEST_MAZE);
		assertNotNull(anotherMaze);
		
		System.out.println(anotherMaze);
		anotherMaze.printCoordinates();
		
	}

	@Test
	public void testMaze4x4SingleSol() {

		Maze maze = new Maze(TEST_MAZE4x4SINGLESOL);
		assertNotNull(maze);
		
		System.out.println(maze);
		maze.printCoordinates();
	}
	
	@Test
	public void testStartingPoint4x4SingleSol() throws Exception {

		Maze maze = new Maze(TEST_MAZE4x4SINGLESOL);
		Cell sp = maze.startingPoint();
		System.out.println(sp);
		assertTrue(sp.X() == 3 && sp.Y() == 0);
	}

	@Test
	public void testFinishingPoint4x4SingleSol() throws Exception {

		Maze maze = new Maze(TEST_MAZE4x4SINGLESOL);
		Cell sp = maze.finishPoint();
		System.out.println(sp);
		assertTrue(sp.X() == 0 && sp.Y() == 3);
	}

}
