import Entities.EntityA;

import java.awt.*;

public class Bullet extends GameObject implements EntityA {

    private Textures textures;
    private Game game;

    Animation animation;

    public Bullet(double x, double y, Textures textures, Game game){
        super(x, y);
        this.textures = textures;
        this.game = game;

        animation = new Animation(5, textures.missile[0], textures.missile[1], textures.missile[2]);
    }

    public void tick(){
        y -= 10;
        animation.runAnimation();
    }

    public void render(Graphics g){
        animation.drawAnimation(g,x,y,0);
    }

    public Rectangle getBounds(){
        return new Rectangle((int)x,(int)y, Game.SPRITE_SIZE, Game.SPRITE_SIZE);
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }
}
