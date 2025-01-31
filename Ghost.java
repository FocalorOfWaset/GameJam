
public class Ghost extends Entity {

    public Ghost(Integer x, Integer y) {
        super(x, y, "ghost");
        //this.setZIndex(1);
    }

    //really only want this to happen if player has moved
    public void move(Player player){
        int[] pcoords = player.getCoords();
        int num = (int)(Math.random() * 10);
        if (num==1){
            if (getX() < pcoords[0])
                coords[0] ++;
            else    
                coords[0] --;
        } else if (num==2){
            if (getY() < pcoords[1])
                coords[1] ++;
            else
                coords[1] --;
        }
    }

    
    @Override
    void query(Level level) {
    
    }
  

    
}
