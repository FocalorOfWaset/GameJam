import java.util.Arrays;

public class Item extends Entity {

    protected Boolean pickedUp;
    protected int[] destCoordinates;

    public Item(Integer x, Integer y, Integer z, int[] coordinates){
        super(x, y,z, "pot");
        pickedUp = false;
        //Obviously need to change these to correct square
        destCoordinates = coordinates;
        //this.setZIndex(2);
    }

    public void pickUp() {
        pickedUp = true;
    }

    public Boolean putDown(int[] coordinates){
        if (Arrays.equals(destCoordinates,coordinates)){
            moveTo(coordinates[0], coordinates[1], coordinates[2]);
            return true;
        }
        return false;
    }

    @Override
    void query(Level level, int time) {
      
    }
}
