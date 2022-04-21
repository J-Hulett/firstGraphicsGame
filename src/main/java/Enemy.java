import Entities.EntityA;
import Entities.EntityB;

import java.awt.*;
import java.util.Random;

public class Enemy extends GameObject implements EntityB {

        private Textures textures;
        Random r = new Random();
        private Game game;
        private Controller c;

        private int speed = r.nextInt(3) + 1;

        Animation animation;


        public Enemy(double x, double y, Textures textures, Controller c, Game game){
            super(x, y);
            this.textures = textures;
            this.game = game;
            this.c = c;

            animation = new Animation(5, textures.enemy[0], textures.enemy[1], textures.enemy[2]);
        }

        public void tick(){
            y += speed;

            if(y > (Game.HEIGHT * Game.SCALE)){
                speed = r.nextInt(3) + 1;
                y = -10;
                x = r.nextInt(Game.WIDTH * Game.SCALE);
            }

            for (int i = 0; i < game.entityAList.size(); i++) {
                EntityA tempEntity = game.entityAList.get(i);
                if(Physics.Collision(this, tempEntity)){
                    c.removeEntity(tempEntity);
                    c.removeEntity(this);
                    game.setEnemyKilled(game.getEnemyKilled() + 1);
                }
            }



            animation.runAnimation();
        }

        public void render(Graphics g){
            animation.drawAnimation(g,x,y,0);
        }

    public Rectangle getBounds(){
        return new Rectangle((int)x,(int)y, Game.SPRITE_SIZE - 12, Game.SPRITE_SIZE - 14);
    }

    @Override
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
