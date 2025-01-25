
/**Provides methods and attributes shared by scenery and entities */
public class Gamepiece {
  //position of piece on grid in form [x,y]
  protected int[] coords;
  //string of URL for piece's image
  protected String image;

  /**Constructor with coordinates provided as array */
  public Gamepiece(int[] coords, String resource) {
    this.coords = coords;
    this.image = resource;
  }
  /**Constructor with coordinates provided seperately */
  public Gamepiece(int x, int y, String resource) {
    int[] coordinates = {x,y};
    this.coords = coordinates;
    this.image = resource;
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
}
