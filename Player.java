import java.util.List;

public class Player extends Entity {
    //players inventory - when make an object class change the type
    protected Item[] inventory;

    public Player(Integer x, Integer y) {
        super(x, y, "blackPawn");
        inventory = new Item[10];
        //this.setZIndex(1);
    }

    //need to change type when make object class
    public Boolean addItem(Item item){
        for (int i=0;i<inventory.length;i++){
            if (inventory[i] == null){
                inventory[i] = item;
                item.pickUp();
                return true;
            }
        }
        return false;
    }
    //need to change type when make object class
    public Boolean removeItem(Item item) {
        for (int i=0;i<inventory.length;i++){
            if (inventory[i] == item){
                inventory[i] = null;
                if (item.putDown(getCoords()))
                    return true;
            }
        }
        return false;
    }
    
    @Override
    void query(Level level) {
        //TODO check for walls and out of bounds
        //TODO add methods to level for adding and removing a piece from updates
      if(level.moved == false) {
        level.updates.add(new Update(this.getX(), this.getY(), this.getZIndex(), this.type, this.getImage(), true));
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
                        item.pickUp();
                        this.addItem(item);
                        level.updates.add(new Update(item.getX(), item.getY(), item.getZIndex(), item.type, item.getImage(), true));
                    }
                }
                break;
            }
        }
        level.moved = true;
        level.updates.add(new Update(this.getX(), this.getY(), this.getZIndex(), this.type, this.getImage(), false));
      }
    }
  }
