
import java.util.Map;
import java.util.EnumMap;
/**Represents a game piece that is not queried each pass of the gameloop, but restricts movement. */
public class Scenery extends Gamepiece {
  //each direction is mapped to a TRUE/FALSE whether that side is exitable
  private Map<Direction, Boolean> walls;

  /**Constructor with coordinates provided as array */
  public Scenery(int[] coords, String resource, boolean northWall, boolean eastWall, boolean southWall, boolean westWall) {
    super(coords, resource);
    //create enum map of walls
    this.walls = new EnumMap<>(Direction.class);
    this.walls.put(Direction.N, northWall);
    this.walls.put(Direction.E, eastWall);
    this.walls.put(Direction.S, southWall);
    this.walls.put(Direction.W, westWall);
  }
  /**Constructor with coorinates provided seperately */
  public Scenery(int x, int y, String resource, boolean northWall, boolean eastWall, boolean southWall, boolean westWall) {
    super(x, y, resource);
    //create enum map of walls
    this.walls = new EnumMap<>(Direction.class);
    this.walls.put(Direction.N, northWall);
    this.walls.put(Direction.E, eastWall);
    this.walls.put(Direction.S, southWall);
    this.walls.put(Direction.W, westWall);
  }

  /**Sets whether a wall is in a specific direction */
  public void alterWall(Direction direc, boolean value) {
    this.walls.replace(direc, value);
  }
  
  /**Returns whether there is a wall in a particular direction */
  public boolean isWall(Direction d) {
    return this.walls.get(d);
  }
}
