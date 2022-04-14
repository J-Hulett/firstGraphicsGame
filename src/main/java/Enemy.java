import Entities.EntityB;

import java.awt.*;
import java.util.Random;

public class Enemy extends GameObject implements EntityB {

        private Textures textures;
        Random r = new Random();

        private int speed = r.nextInt(3) + 1;


        public Enemy(double x, double y, Textures textures){
            super(x, y);
            this.textures = textures;
        }

        public void tick(){
            y += speed;

            if(y > (Game.HEIGHT * Game.SCALE)){
                speed = r.nextInt(3) + 1;
                y = -10;
                x = r.nextInt(Game.WIDTH * Game.SCALE);
            }
        }

        public void render(Graphics g){
            g.drawImage(textures.enemy, (int)x, (int)y, null);
        }

    public Rectangle getBounds(){
        return new Rectangle((int)x,(int)y, Game.SPRITE_SIZE, Game.SPRITE_SIZE);
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
