import java.io.InputStream;
import java.util.Scanner;

/**For writing and reading of levels to files */
public class FileHandler {
  
  /**Deserialises a level from the file with name provided */
  public Level readLevel(String name) {
    Level level = null;
    String path = "levels/" + name + ".lvl";
    Scanner reader = null;
    try {
      InputStream in = this.getClass().getResourceAsStream(path);
      reader = new Scanner(in);
      int width = reader.nextInt();
      int height = reader.nextInt();
      reader.nextLine();
      String sceneryStr = reader.nextLine();
      String entitiesStr = reader.nextLine();
      Scenery[][] scenery = new Scenery[height][width];
      String[] sceneryStrs = sceneryStr.split(";");
      for (int row = 0; row < height; row++) {
        for(int col = 0; col < width; col++) {
          scenery[row][col] = stringToScenery(sceneryStrs[(width*row)+col], col, row);
        }
      }
      EntityMap entities = new EntityMap();
      for (String entstr:entitiesStr.split(";")) {
        entities.addEntity(stringToEntity(entstr));
      }
      level = new Level(scenery, entities);
    } catch(Exception e) {
      //TODO add actual error handling
      e.printStackTrace();
    } finally {
      reader.close();
    }
    return level;
  }

  /**Serialises the level provided into a file with the name provided. */
  public void writeLevel(Level level, String name) {
    //TODO implement for saving (low priority)
  }

  private Scenery stringToScenery(String serial, int x, int y) {
    boolean north,east,south,west;
    north = (serial.charAt(0) == '1');
    east = (serial.charAt(1) == '1');
    south = (serial.charAt(2) == '1');
    west = (serial.charAt(3) == '1');
    return new Scenery(x, y, north, east, south, west);
  }

  private Entity stringToEntity(String serial) {
    String[] serialparts = serial.split(",");
    int x = Integer.valueOf(serialparts[0]);
    int y = Integer.valueOf(serialparts[1]);
    String className = serialparts[2];
    Entity entity = null;
    //will need to look up class name
    //TODO use enum not classname
    switch (className) {
      case "Player": entity = new Player(x, y); break;
      case "Item":{
        int[] dest = {Integer.valueOf(serialparts[3]), Integer.valueOf(serialparts[4])};
        entity = new Item(x, y, dest);
        break;
      }
      default: entity = new Pawn(x, y);
    }
    return entity;
  }
}
