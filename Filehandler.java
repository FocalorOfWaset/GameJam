import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**For writing and reading of levels to files */
public class FileHandler {
  
  /**Deserialises a level from the file with name provided */
  public Level readLevel(String name) {
    Level level = null;
    String path = "levels/" + name;
    Scanner reader = null;
    try {
      InputStream in = this.getClass().getResourceAsStream(path);
      reader = new Scanner(in);
      int width = reader.nextInt();
      int height = reader.nextInt();
      String sceneryStr = reader.nextLine();
      String entitiesStr = reader.nextLine();
      Scenery[][] scenery = new Scenery[height][width];
      String[] sceneryStrs = sceneryStr.split(";");
      for (int row = 0; row < width; row++) {
        for(int col = 0; col < height; col++) {
          scenery[row][col] = stringToScenery(sceneryStrs[(width*row)+col], col, row);
        }
      }
      List<Entity> entities = new ArrayList<Entity>();
      for (String entstr:entitiesStr.split(";")) {
        entities.add(stringToEntity(entstr));
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
    //FIXME probably ought to switch on the className on classses that definitely exist 
    //but reflection seemed fun (is definitely unneeded)
    int x = Integer.valueOf(serial.substring(0, 1));
    int y = Integer.valueOf(serial.substring(1, 2));
    String className = serial.substring(2);
    Entity entity = null;
    //will need to look up class name
    //TODO really needs proper error handling
    try {
      Class<?> entClass = Class.forName("className");
      Object obj = entClass.getDeclaredConstructor().newInstance(x, y, className);
      entity = (Entity)obj;
    } catch(Exception e) {
      e.printStackTrace();
    }
    return entity;
  }
}
