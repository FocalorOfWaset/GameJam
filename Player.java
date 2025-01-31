import java.util.List;

public class Player extends Entity {
    //players inventory - when make an object class change the type
    protected Item[] inventory;

    public Player(Integer x, Integer y) {
        super(x, y, "player");
        inventory = new Item[10];
        //this.setZIndex(1);
    }

    //need to change type when make object class
    public Boolean addItem(Item item, Level level){
        for (int i=0;i<inventory.length;i++){
            if (inventory[i] == null){
                inventory[i] = item;
                item.pickUp();
                level.updates.add(new Update(0, 0, i, this.type, item.getImage(), UpdateType.ADD_INV));
                return true;
            }
        }
        return false;
    }
    //need to change type when make object class
    public Boolean removeItem(Level level) {
        for (int i=0;i<inventory.length;i++){
            Item item = inventory[i];
            if (item != null) {
            if (item.putDown(getCoords())) {
                inventory[i] = null;
                level.updates.add(new Update(0, 0, i, this.type, item.getImage(), UpdateType.REMOVE_INV));
                level.updates.add(new Update(this.getX(), this.getY(), this.getZIndex() + 1, item.type, item.getImage(), UpdateType.ADD));
                level.numItems--;
                return true;
            } 
        }
        }
        return false;
    }
    
    @Override
    void query(Level level, int time) {
        //TODO check for walls and out of bounds
        //TODO add methods to level for adding and removing a piece from updates
      if(level.moved == false) {
        level.updates.add(new Update(this.getX(), this.getY(), this.getZIndex(), this.type, this.getImage(), UpdateType.REMOVE));
        switch(level.d) {
            case N: this.move(0, -1); break;
            case S: this.move(0, 1); break;
            case E: this.move(-1, 0); break;
            case W: this.move(1, 0); break;
            case PICKUP: {
                List<Entity> list = level.entities.getEntity(this.getX(), this.getY());
                for(Entity e : list) {
                    if (e instanceof Item) {
                        Item item = (Item)e;
                        if (this.addItem(item, level)) {
                            level.updates.add(new Update(item.getX(), item.getY(), item.getZIndex(), item.type, item.getImage(), UpdateType.REMOVE));
                        }
                    }
                }
                break;}
            case PUTDOWN: {
                if(removeItem(level)) {
                    
                }
                break;
            }
        }
        level.moved = true;
        level.updates.add(new Update(this.getX(), this.getY(), this.getZIndex(), this.type, this.getImage(), UpdateType.ADD));
      }
    }
  }
