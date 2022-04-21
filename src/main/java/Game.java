import Entities.EntityA;
import Entities.EntityB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

public class Game extends Canvas implements Runnable{

    public static final int WIDTH = 320;
    public static final int HEIGHT = WIDTH / 12 * 9;
    public static final int SCALE = 2;
    public static final int SPRITE_SIZE = 32;
    public final String TITLE = "2D Space Game";

    private boolean running = false;
    private Thread thread;

    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private BufferedImage spriteSheet = null;
    private BufferedImage background = null;

    private boolean isShooting = false;
    private int enemyCount = 1;
    private int enemyKilled = 0;

    private Player p;
    private Controller c;
    private Textures textures;
    private Menu menu;

    public LinkedList<EntityA> entityAList;
    public LinkedList<EntityB> entityBList;

    public static int health = 200;

    //Make a class to do this -- Don't use public static variables.
    public static enum STATE{
        MENU,
        GAME
    };
    public static STATE state = STATE.MENU;

    public void init(){
        requestFocus();
        BufferedImageLoader loader = new BufferedImageLoader();
        try{
            spriteSheet = loader.loadImage("Sprite_Sheet.png");
            background = loader.loadImage("background.png");
        }catch(IOException e){
            e.printStackTrace();
        }

        addKeyListener(new KeyInput(this));
        this.addMouseListener(new MouseInput());

        textures = new Textures(this);
        c = new Controller(textures, this);
        p = new Player(200,200, textures, this, c);
        menu = new Menu();

        entityAList = c.getEntityAList();
        entityBList = c.getEntityBList();


        c.createEnemy(enemyCount);
    }

    //Call this to start the game thread
    private synchronized void start(){
        if(running)
            return;

        running = true;
        thread = new Thread(this);
        thread.start();
    }

    //Call this to stop the game Thread
    private synchronized void stop(){
        if(!running)
            return;

            running = false;

            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.exit(1);
    }

    @Override
    public void run(){
        init();
        //System time in nanoseconds
        long lastTime = System.nanoTime();
        final double amountOfTicks = 60.0; //Sets the tick rate for FPS
        double ns = 1000000000 / amountOfTicks;
        double delta = 0; //Sets a value so fps can be adjusted if it falls behind or gets ahead.
        int updates = 0;
        int frames = 0;
        long timer = System.currentTimeMillis();

        while(running){
            //this is the game loop
            long now = System.nanoTime(); //Gets the time now to compare it to "lastTime";
            delta += (now - lastTime) / ns;
            lastTime = now;
            if(delta >= 1){
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println(updates + " Ticks, FPS " + frames);
                updates = 0;
                frames = 0;
            }

        }
        stop();
    }

    private void tick(){
        if(state == STATE.GAME) {
            p.tick();
            c.tick();
        }

        if(enemyKilled >= enemyCount){
            enemyCount += 2;
            enemyKilled = 0;
            c.createEnemy(enemyCount);
        }
    }

    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        ///////////////////////////////////
        //////Draw Stuff In Here///////////

        g.drawImage(image, 0 ,0, getWidth(), getHeight(), this);
        g.drawImage(background, 0,0,null);

        if(state == STATE.GAME) {
            p.render(g);
            c.render(g);

            g.setColor(Color.gray);
            g.fillRect(5,5,200,35);

            g.setColor(Color.green);
            g.fillRect(5,5,health,35);

            g.setColor(Color.white);
            g.drawRect(5,5,200,35);
        } else if (state == STATE.MENU){
            menu.render(g);
        }

        ///////////////////////////////////
        g.dispose();
        bs.show();

    }


    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();

        if(state == STATE.GAME) {
            if (key == KeyEvent.VK_RIGHT) {
                p.setVelX(5);
            } else if (key == KeyEvent.VK_LEFT) {
                p.setVelX(-5);
            } else if (key == KeyEvent.VK_DOWN) {
                p.setVelY(5);
            } else if (key == KeyEvent.VK_UP) {
                p.setVelY(-5);
            } else if (key == KeyEvent.VK_SPACE && !isShooting) {
                c.addEntity(new Bullet(p.getX(), p.getY(), textures, this));
                isShooting = true;
            }
        }
    }


    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_RIGHT){
            p.setVelX(0);
        }else if(key == KeyEvent.VK_LEFT){
            p.setVelX(0);
        }else if(key == KeyEvent.VK_DOWN){
            p.setVelY(0);
        }else if(key == KeyEvent.VK_UP){
            p.setVelY(0);
        } else if(key == KeyEvent.VK_SPACE){
            isShooting = false;
        }

    }

    public static void main(String[] args) {
        Game game = new Game();

        game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

        JFrame frame = new JFrame(game.TITLE);
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        game.start();
    }

    public BufferedImage getSpriteSheet(){
        return spriteSheet;
    }

    public int getEnemyCount() {
        return enemyCount;
    }

    public void setEnemyCount(int enemyCount) {
        this.enemyCount = enemyCount;
    }

    public int getEnemyKilled() {
        return enemyKilled;
    }

    public void setEnemyKilled(int enemyKilled) {
        this.enemyKilled = enemyKilled;
    }
}
