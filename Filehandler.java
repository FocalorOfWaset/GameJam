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
      int floors = reader.nextInt();
      reader.nextLine();
      Scenery[][][] scenery = new Scenery[floors][height][width];
      for (int i = 0;i  < floors;i++) {
        String sceneryStr = reader.nextLine();
        String[] sceneryStrs = sceneryStr.split(";");
        for (int row = 0; row < height; row++) {
          for(int col = 0; col < width; col++) {
            scenery[i][row][col] = stringToScenery(sceneryStrs[(width*row)+col], col, row, i);
          }
        }
      }
      String entitiesStr = reader.nextLine();
      int numItems = 0;
      EntityMap entities = new EntityMap();
      for (String entstr:entitiesStr.split(";")) {
        Entity ent = stringToEntity(entstr);
        if (ent instanceof Item) {
          numItems++;
        }
        entities.addEntity(ent);
      }
      level = new Level(scenery, entities, numItems);
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

  private Scenery stringToScenery(String serial, int x, int y, int z) {
    boolean north,east,south,west;
    north = (serial.charAt(0) == '1');
    east = (serial.charAt(1) == '1');
    south = (serial.charAt(2) == '1');
    west = (serial.charAt(3) == '1');
    return new Scenery(x, y, z, north, east, south, west);
  }

  private Entity stringToEntity(String serial) {
    String[] serialparts = serial.split(",");
    int x = Integer.valueOf(serialparts[0]);
    int y = Integer.valueOf(serialparts[1]);
    int z = Integer.valueOf(serialparts[2]);
    String className = serialparts[3];
    Entity entity = null;
    //will need to look up class name
    //TODO use enum not classname
    switch (className) {
      case "Player": entity = new Player(x, y, z); break;
      case "Item":{
        int[] dest = {Integer.valueOf(serialparts[4]), Integer.valueOf(serialparts[5]), Integer.valueOf(serialparts[6])};
        entity = new Item(x, y, z, dest);
        break;
      }
      case "Ghost": entity  = new Ghost(x,y,z); break;
      case "Stairs": entity  = new Stairs(x,y,z); break;
      default: entity = new Pawn(x, y, z);
    }
    return entity;
  }
}
