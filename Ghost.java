import java.util.List;
public class Ghost extends Entity {

    public Ghost(Integer x, Integer y, Integer z) {
        super(x, y, z, "ghost");
        //this.setZIndex(1);
    }

    //really only want this to happen if player has moved
    public void kill(Level level, int[] coords){
        if (getX() == coords[0] && getY() == coords[1]) 
            level.end();
    }


    @Override
    void query(Level level, int time) {
        int playerIndex = 0;
        List<Entity> entityList = level .getAllEntities();
        level.updates.add(new Update(this, UpdateType.REMOVE));
        for (int i=0;i<entityList.size();i++){
            if (entityList.get(i) instanceof Player)
                playerIndex = i;
        }
        int x = 0;
        int y = 0;
        int[] pcoords = entityList.get(playerIndex).getCoords();
        kill( level, pcoords );
        if (time==1||time==180){
            if (getX() < pcoords[0])
                x = 1;
            else  {  
                x = -1;
                
            }
        } else if (time==90||time==270){
            if (getY() < pcoords[1]){
                y = 1;
                System.out.println("move up");
            }else
                y = -1;
        }
        if (this.getY() + y < level.scenery[1].length && this.getX() + x < level.scenery[0].length)
            
            this.moveTo(this.getX() + x,this.getY() + y, this.getZ());
            level.updates.add(new Update(this, UpdateType.ADD));
    }
  

    
}
