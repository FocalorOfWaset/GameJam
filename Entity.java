
/**Represents a game piece that is queried each pass of the gameloop */
public abstract class Entity extends Gamepiece {

  /**Constructor with coorinates provided seperately */
  public Entity(int x, int y, int z, String resource) {
    super(x, y, z, resource, PieceType.ENTITY);
  }

  /**Constructor with coordinates provided as array */
  public Entity(int[] coords, String resource) {
    super(coords, resource, PieceType.ENTITY);
  }

  /**Sets how high the entity is in th eimage stack */
  public void setZIndex(int index) {
    this.zIndex = index;
  }

  /**Returns how high the entity is in the image stack */
  @Override
  public int getZIndex() {
    //entities are never on the bottom layer
    return zIndex==0?1:zIndex;
  }

  /**Is queried for every entity each pass of the gameloop */
  abstract void query(Level level, int time);
}

//TODO create stairs