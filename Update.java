public class Update {
  public int x;
  public int y;
  public int zIndex;
  public String image;
  public PieceType type;
  public boolean remove;
  
  public Update(int x, int y, int zIndex, PieceType type, String image, boolean remove) {
    this.x = x;
    this.y = y;
    this.zIndex = zIndex;
    this.type = type;
    this.image = image;
    this.remove = remove;
  }
}
