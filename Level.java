
import java.util.List;
import java.util.ArrayList;
/**Stores information on a level including scenery and entities */
public class Level {
  private Scenery[][] scenery;
  private boolean sceneryAltered = false;
  private List<Entity> entities;
  private Direction d;
  private boolean moved;
  private GameController ui;

  public Level(GameController ui) {
    this.ui = ui;
  }

  public void logKey(Direction direc) {
    if (moved) {
      this.d = direc;
      moved = false;
    }
  }

  public void queryEntities(int time) {
    this.ui.updateBoard();
  }
}
