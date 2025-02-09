
import java.util.ArrayList;
import java.util.List;
import javafx.scene.text.Text;
/**Stores information on a level including scenery and entities */
//TODO add support for multiple layers
//TODO add getters and setters etc
public class Level {
  //grid of entities
  public Scenery[][][] scenery;
  public int floor;
  //map of entites and coordinates
  public EntityMap entities;
  //direction player piece will move in next
  public Direction d;
  //records whether the player peice has been moved in the above direction
  public boolean moved;
  //a list of all updated pieces
  public List<Update> updates;
  //reference to UI
  private GameController ui;
  //number of items until the game is won
  public int numItems;

  /**Constructs Level object with scenery and entities provided */
  public Level(Scenery[][][] scenery, EntityMap entities, int num) {
    this.scenery = scenery;
    this.entities = entities;
    this.d = Direction.N;
    this.moved = true;
    this.updates = new ArrayList<>();
    this.numItems = num;
    this.floor = 0;
  }

  /**Sets reference to the user interface */
  public void setUI(GameController ui) {
    this.ui = ui;
  }

  /**Records when a movement key has been pressed */
  public void logKey(Direction direc) {
    if (moved) {
      this.d = direc;
      moved = false;
    }
  }

  /**Queries all entities in map and prompts the ui to display any changed pieces */
  public void queryEntities(int time) {
    //clears all previous updates
    this.updates.clear();
    for (List<Entity> ents:this.entities.getAll()) {
      for (Entity ent : ents) {
        //queries every entity (may update this.updates)
        if (ent.getZ() == floor) {
          ent.query(this, time);
        }
      }
    }
    //displays updates
    this.ui.updateBoard(this.updates);
    if(this.numItems == 0 ) {
      //win game
      this.ui.endGame(new Text("You have won!"));
    } 
  }

  public int getFloors() {
    return this.scenery.length;
  }

  /**Returns width of grid of scenery */
  public int getWidth() {
    return this.scenery[0][0].length;
  }

  /**Returns height of grid of scenery */
  public int getHeight() {
    return this.scenery[0].length;
  }

  /**Returns all entities in level as a list */
  public List<Entity> getAllEntities() {
    List<Entity> ret = new ArrayList<>();
    this.entities.getAll().forEach(ret::addAll);
    return ret;
  }

  public void end() {
    this.ui.endGame(new Text("You lost :("));
  }
}
