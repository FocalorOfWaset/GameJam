public class Update {
  public int x;
  public int y;
  public int zIndex;
  public String image;
  public PieceType type;
  public UpdateType uptype;
  
  public Update(int x, int y, int zIndex, PieceType type, String image, UpdateType uptype) {
    this.x = x;
    this.y = y;
    this.zIndex = zIndex;
    this.type = type;
    this.image = image;
    this.uptype = uptype;
  }

  public Update(Gamepiece piece, UpdateType type) {
    this.x = piece.getX();
    this.y = piece.getY();
    this.zIndex = piece.getZIndex();
    this.type = piece.type;
    this.image = piece.getImage();
    this.uptype = type;
  }
}
