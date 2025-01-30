public class Player extends Entity {
    //players inventory - when make an object class change the type
    protected Item[] inventory;

    public Player(Integer x, Integer y, Item[] inventory) {
        super(x, y, "blackPawn");
        inventory = new Item[10];
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
    public Boolean removeItem(Item item){
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
      
    }
    
  }
