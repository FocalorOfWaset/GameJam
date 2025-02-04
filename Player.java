import java.util.List;

public class Player extends Entity {
    //players inventory - when make an object class change the type
    protected Item[] inventory;

    public Player(Integer x, Integer y, Integer z) {
        super(x, y, z, "player");
        inventory = new Item[10];
        //this.setZIndex(1);
    }

    //need to change type when make object class
    public Boolean addItem(Item item, Level level){
        for (int i=0;i<inventory.length;i++){
            if (inventory[i] == null) {
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

    private void movePlayer(Level level, Direction d, int x, int y) {
        Scenery square = level.scenery[level.floor][this.getY()][this.getX()];
        boolean isWall = square.isWall(d);
        if (isWall) {
            return;
        }
        if (this.getY() + y < level.scenery[level.floor].length && this.getX() + x < level.scenery[level.floor][0].length) {
            square = level.scenery[level.floor][this.getY()+y][this.getX()+x];
            isWall = square.isWall(oppositeDirection(d));
            if (isWall) {
             return;
            }
        }
        this.move(x,y);
    }

    private void moveOnStairs(Level level, int z) {
        if (this.getZ() + z >= 0 && this.getZ() + z < level.scenery.length) {
            //floor exists
            List<Entity> ents = level.entities.getEntity(this.getX(), this.getY(), this.getZ());
            if (ents != null) {
                for(Entity ent:ents) {
                    if(ent instanceof Stairs) {
                        //go on stairs
                        this.move(z);
                        level.floor += z;
                        level.updates.add(new Update(this.getX(), this.getY(), z, this.type, this.getImage(), UpdateType.UP_STAIRS));
                        level.updates.add(new Update(this, UpdateType.REMOVE));
                    }
                }
            }
        }
    }

    private Direction oppositeDirection(Direction d) {
        switch(d) {
            case N: return Direction.S;
            case E: return Direction.W;
            case W: return Direction.E;
            case S: return Direction.N;
            case PICKUP: return Direction.PUTDOWN;
            case PUTDOWN: return Direction.PICKUP;
            case UP: return Direction.DOWN;
            case DOWN: return Direction.UP;
        }
        return d;
    }
    
    @Override
    void query(Level level, int time) {
        //TODO check for out of bounds
      if(level.moved == false) {
        level.updates.add(new Update(this, UpdateType.REMOVE));
        switch(level.d) {
            case N: this.movePlayer(level, Direction.N, 0, -1); break;
            case S: this.movePlayer(level, Direction.S, 0, 1); break;
            case E: this.movePlayer(level, Direction.E, 1, 0); break;
            case W: this.movePlayer(level, Direction.W, -1, 0); break;
            case PICKUP: {
                List<Entity> list = level.entities.getEntity(this.getX(), this.getY(), this.getZ());
                if (list != null){
                    for(Entity e : list) {
                        if (e instanceof Item) {
                            Item item = (Item)e;
                            if (this.addItem(item, level)) {
                                level.updates.add(new Update(item, UpdateType.REMOVE));
                            }
                        }
                    }
                }break;}
            case PUTDOWN: {
                if(removeItem(level)) {
                    
                }
                break;
            }
            case UP: moveOnStairs(level, 1); break;
            case DOWN: moveOnStairs(level, -1); break;
        }
        level.moved = true;
        level.updates.add(new Update(this, UpdateType.ADD));
      }
    }
  }
