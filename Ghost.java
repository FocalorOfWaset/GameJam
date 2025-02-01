import java.util.List;
public class Ghost extends Entity {

    public Ghost(Integer x, Integer y) {
        super(x, y, "ghost");
        //this.setZIndex(1);
    }

    //really only want this to happen if player has moved
    public boolean kill(Level level, int[] coords){
        if (getX() == coords[0] && getY() == coords[1])
            level.end();
            return true;
        else    
            return false;
    }

    
    @Override
    void query(Level level, int time) {
        int playerIndex = 0;
        List<Entity> entityList = level.getAllEntities();
        for (int i=0;i<entityList.size();i++){
            if (entityList.get(i) instanceof Player)
                playerIndex = i;
        }
        int[] pcoords = entityList.get(playerIndex).getCoords();
        if (time==1){
            if (getX() < pcoords[0])
                coords[0] ++;
            else    
                coords[0] --;
        } else if (time==180){
            if (getY() < pcoords[1])
                coords[1] ++;
            else
                coords[1] --;
        }
    }
  

    
}
