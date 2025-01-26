
import java.util.List;
import java.util.ArrayList;
/**Stores information on a level including scenery and entities */
public class Level {
  public Scenery[][] scenery;
  public boolean sceneryAltered = false;
  public List<Entity> entities;
  public Direction d;
  public boolean moved;
  private GameController ui;

  public void setUI(GameController ui) {
    this.ui = ui;
  }

  public void logKey(Direction direc) {
    if (moved) {
      this.d = direc;
      moved = false;
    }
  }

  public void queryEntities(int time) {
    for (Entity ent:this.entities) {
      ent.query(this);
    }
    this.ui.updateBoard();
  }
}
