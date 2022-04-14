import Entities.EntityA;

import java.awt.*;

public class Bullet extends GameObject implements EntityA {

    private Textures textures;
    private Game game;

    public Bullet(double x, double y, Textures textures, Game game){
        super(x, y);
        this.textures = textures;
        this.game = game;
    }

    public void tick(){
        y -= 10;

        if(Physics.Collision(this, game.entityBList)){
            System.out.println("COLLISION DETECTED");
        }
    }

    public void render(Graphics g){
        g.drawImage(textures.missile, (int) x, (int) y, null);
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
