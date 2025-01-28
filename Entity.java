
/**Represents a game piece that is queried each pass of the gameloop */
public abstract class Entity extends Gamepiece {

  /**Constructor with coorinates provided seperately */
  public Entity(int x, int y, String resource) {
    super(x, y, resource, PieceType.ENTITY);
  }

  /**Constructor with coordinates provided as array */
  public Entity(int[] coords, String resource) {
    super(coords, resource, PieceType.ENTITY);
  }

  /**Is queried for every entity each pass of the gameloop */
  abstract void query(Level level);
}
