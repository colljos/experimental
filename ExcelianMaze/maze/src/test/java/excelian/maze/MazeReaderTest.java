package excelian.maze;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class MazeReaderTest {

	private MazeReader mr = new MazeReader("ExampleMaze.txt");
	
	@Test
	public void testRead() {
		
		char[][] maze = mr.read();
		assertNotNull(maze);
		
		for (char[] mazeLine : maze) {
			System.out.println(Arrays.toString(mazeLine));			
		}
	}
}
