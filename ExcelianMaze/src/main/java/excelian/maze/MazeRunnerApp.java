package excelian.maze;

import java.util.Optional;

public class MazeRunnerApp {


	 public static void main (String[] args) {

		 	if (args.length != 1) {
		 		System.out.println("Usage: MazeRunnerApp <maze-spec-filename>");
		 		System.out.println("eg. MazeRunnerApp ExampleMaze.txt");
		 		System.exit(0);
		 	}
		 
			Maze maze = new Maze(args[0]);
			
			Resolver resolver = new ResolverImpl();
			Explorer explorer = new Explorer(maze, resolver);
			
			Optional<Solution> solution = explorer.resolve();
			if (solution.isPresent()) {
				System.out.println(" ================ SOLUTION ==============");
				maze.printSolution(solution.get());
			}
			else {
				System.out.println(" ================ UNRESOLVABLE ==============");
			}
		

	 }  // method main
}
