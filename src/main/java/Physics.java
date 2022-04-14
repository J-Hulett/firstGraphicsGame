import Entities.EntityA;
import Entities.EntityB;

import java.util.LinkedList;

public class Physics {

    public static boolean Collision(EntityA entityA, LinkedList<EntityB> entityB){

        for(int i = 0; i < entityB.size(); i++){

            if(entityA.getBounds().intersects(entityB.get(i).getBounds())){
                return true;
            }
        }
            return false;
    }
}
