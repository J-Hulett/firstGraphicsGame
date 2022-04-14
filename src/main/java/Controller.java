import Entities.EntityA;
import Entities.EntityB;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class Controller {

    private LinkedList<EntityA> ea = new LinkedList<EntityA>();
    private LinkedList<EntityB> eb = new LinkedList<EntityB>();

    EntityA entityA;
    EntityB entityB;

    private Textures textures;
    Random r = new Random();

    public Controller(Textures textures){
        this.textures = textures;

    }

    public void createEnemy(int enemyCount){
        for(int i = 0; i < enemyCount; i++){
           addEntity(new Enemy(r.nextInt((Game.WIDTH * Game.SCALE)), -10, textures));
        }
    }

    public void tick(){
        //A Entities
        for(int i = 0; i < ea.size(); i++){
            entityA = ea.get(i);
            entityA.tick();
        }
        //B Entities
        for(int i = 0; i < eb.size(); i++){
            entityB = eb.get(i);
            entityB.tick();
        }
    }

    public void render(Graphics g){
        //A Entities
        for(int i = 0; i < ea.size(); i++){
            entityA = ea.get(i);
            entityA.render(g);
        }
        //B Entities
        for(int i = 0; i < eb.size(); i++){
            entityB = eb.get(i);
            entityB.render(g);
        }
    }

    public void addEntity(EntityA block){
        ea.add(block);
    }

    public void removeEntity(EntityA block){
        ea.remove(block);
    }

    public void addEntity(EntityB block){
        eb.add(block);
    }
    //REFACTOR ALL THESE ENTITIES LATER - ENTITY A IS PLAYER - ENTITY B IS ENEMY
    public void removeEntity(EntityB block){
        eb.remove(block);
    }

    public LinkedList<EntityA> getEntityAList() {
        return ea;
    }

    public LinkedList<EntityB> getEntityBList() {
        return eb;
    }
}
