package excelian.maze;

import java.util.Optional;

public interface Resolver {

	Optional<Solution> resolve(Maze aMaze);

}
