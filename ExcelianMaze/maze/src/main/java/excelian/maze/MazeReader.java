package excelian.maze;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class MazeReader {

	private String filename;

	public MazeReader(String filename) {
		this.filename = filename;
	}

	public char[][] read() {
	
		List<char[]> maze = new ArrayList<char[]>();
		
		URL resource = this.getClass().getClassLoader().getResource(filename);
		if (resource == null) {
			Logger.getGlobal().severe(() -> "Unable to locate file: " + filename);
			throw new RuntimeException("Invalid maze file");
		}
		else {
			try {
				Path path = Paths.get(resource.toURI());
				Logger.getGlobal().info(() -> filename + " resolves to " + path);
				
				try (Scanner in = new Scanner(path)) {
					while (in.hasNextLine()) {
						String line = in.nextLine();
						char[] cline = line.toCharArray();
						maze.add(cline);
					}
				} catch (IOException e) {
					Logger.getGlobal().severe(() -> e.getMessage());
				}
			} catch (URISyntaxException e) {
				Logger.getGlobal().severe(() -> e.getMessage());
			}
		}
		
		char[][] cmaze = new char[maze.size()][];
		cmaze = maze.toArray(cmaze);
		
		return cmaze;
	}	
}
