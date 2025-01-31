
/**Provides methods and attributes shared by scenery and entities */
public class Gamepiece {
  //position of piece on grid in form [x,y]
  protected int[] coords;
  //whether a piece is 'on top of' or 'below' another piece in the image stack
  protected int zIndex = 0;
  //string of URL for piece's image
  protected String image;
  protected PieceType type;

  /**Constructor with coordinates provided as array */
  public Gamepiece(int[] coords, String resource, PieceType type) {
    this.coords = coords;
    this.image = resource;
    this.type = type;
  }
  /**Constructor with coordinates provided seperately */
  public Gamepiece(int x, int y, String resource, PieceType type) {
    int[] coordinates = {x,y};
    this.coords = coordinates;
    this.image = resource;
    this.type = type;
  }
  /**Returns URl of image associated with object */
  public String getImage() {
    return this.image;
  }
  /**Returns the coordinates of the object as an array */
  public int[] getCoords() {
    return this.coords;
  }
  /**Returns the x coordinate of the object */
  public int getX() {
    return this.coords[0];
  }
  /**Returns the y coordinate of the object*/
  public int getY() {
    return this.coords[1];
  }
  /**Adds the provided offsets to the coordinates of the object */
  public void move(int x, int y) {
    coords[0] += x;
    coords[1] += y;
  }

  /**Changes the coordinates to the provided values */
  public void moveTo(int x , int y) {
    coords[0] = x;
    coords[1] = y;
  }

  /**Returns the type of the piece (SCENERY or ENTITY) */
  public PieceType type() {
    return this.type;
  }

  /**Returns how high the piece is located in the image stack */
  public int getZIndex() {
    return this.zIndex;
  }
}
