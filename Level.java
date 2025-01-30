
import java.util.ArrayList;
import java.util.List;
/**Stores information on a level including scenery and entities */
//TODO add support for multiple layers
//TODO add getters and setters etc
public class Level {
  public Scenery[][] scenery;
  public EntityMap entities;
  public Direction d;
  public boolean moved;
  public List<Gamepiece> updates;
  private GameController ui;

  public Level(Scenery[][] scenery, EntityMap entities) {
    this.scenery = scenery;
    this.entities = entities;
    this.d = Direction.N;
    this.moved = true;
    this.updates = new ArrayList<>();
  }

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
    for (List<Entity> ents:this.entities.getAll()) {
      for (Entity ent : ents) {
        ent.query(this);
      }
    }
    this.ui.updateBoard(this.updates);
  }

  public int getWidth() {
    return this.scenery[0].length;
  }

  public int getHeight() {
    return this.scenery.length;
  }

  public List<Entity> getAllEntities() {
    List<Entity> ret = new ArrayList<>();
    this.entities.getAll().forEach(ret::addAll);
    return ret;
  }
}
