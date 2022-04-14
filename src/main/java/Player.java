import Entities.EntityA;

import java.awt.*;

public class Player extends GameObject implements EntityA {

        private double x;
        private double y;

        private double velX = 0;
        private double velY = 0;

        private Textures textures;

        public Player (double x, double y, Textures textures){
            super(x, y);
            this.textures = textures;
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
        }

        public void render(Graphics g){
            g.drawImage(textures.player, (int)x, (int)y,null);
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
