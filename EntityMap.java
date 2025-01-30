import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

public class EntityMap {
  private Map<Integer, List<Entity>> map;

  public EntityMap() {
    this.map = new HashMap<Integer, List<Entity>>();
  }

  public List<Entity> getEntity(int x, int y) {
    int key = Integer.valueOf(Integer.toString(x) + Integer.toString(y));
    return this.map.get(key);
  }

  public void addEntity(Entity ent) {
    Integer key = Integer.valueOf(Integer.toString(ent.getX()) + Integer.toString(ent.getY()));
    List<Entity> ents = this.map.get(key);
    if (ents == null) {
      ents = new ArrayList<Entity>();
    }
    ents.add(ent);
    this.map.put(key, ents);
  }

  public Collection<List<Entity>> getAll() {
    return this.map.values();
  }
}
