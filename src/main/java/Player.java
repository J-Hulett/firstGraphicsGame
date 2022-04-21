import Entities.EntityA;
import Entities.EntityB;

import java.awt.*;

public class Player extends GameObject implements EntityA {

        private double x;
        private double y;

        private double velX = 0;
        private double velY = 0;
        private Textures textures;

        Game game;
        Animation animation;
        Controller controller;


        public Player (double x, double y, Textures textures, Game game, Controller controller){
            super(x, y);
            this.textures = textures;
            this.game = game;
            this.controller = controller;

            animation = new Animation(5, textures.player[0], textures.player[1], textures.player[2]);
        }

        public void tick(){
            x+=velX;
            y+=velY;

            if(x <= 0){
                x = 0;
            }
            if(x >= (Game.WIDTH * Game.SCALE) - (Game.SPRITE_SIZE)){
                x = (Game.WIDTH * Game.SCALE) - (Game.SPRITE_SIZE);
            }
            if(y <= 0){
                y = 0;
            }
            if(y >= (Game.HEIGHT * Game.SCALE) - Game.SPRITE_SIZE){
                y = (Game.HEIGHT * Game.SCALE) - Game.SPRITE_SIZE;
            }

            for (int i = 0; i < game.entityBList.size(); i++) {
                EntityB tempEntity = game.entityBList.get(i);

                if(Physics.Collision(this, tempEntity)){
                    controller.removeEntity(tempEntity);
                    Game.health -= 10;
                    game.setEnemyKilled(game.getEnemyKilled() + 1);
                }
            }

            animation.runAnimation();
        }

        public void render(Graphics g){
            animation.drawAnimation(g,x,y,0);
        }

    public Rectangle getBounds(){
        return new Rectangle((int)x,(int)y, Game.SPRITE_SIZE, Game.SPRITE_SIZE);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }
}
