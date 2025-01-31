public class Item extends Entity {

    protected Boolean pickedUp;
    protected int[] destCoordinates;

    public Item(Integer x, Integer y, int[] coordinates){
        super(x, y, "blackPawn");
        pickedUp = false;
        //Obviously need to change these to correct square
        destCoordinates = coordinates;
        //this.setZIndex(2);
    }

    public void pickUp() {
        pickedUp = true;
    }

    public Boolean putDown(int[] coordinates){
        if (destCoordinates == coordinates){
            move(coordinates[0], coordinates[1]);
            return true;
        }
        return false;
    }

    @Override
    void query(Level level) {
      
    }
}
