import Entities.EntityA;
import Entities.EntityB;

import java.util.LinkedList;

public class Physics {

    public static boolean Collision(EntityA entityA, EntityB entityB){
        if(entityA.getBounds().intersects(entityB.getBounds())){
            return true;
        }
        return false;
    }
    public static boolean Collision(EntityB entityB, EntityA entityA){
        if(entityB.getBounds().intersects(entityA.getBounds())){
            return true;
        }
        return false;
    }

}
