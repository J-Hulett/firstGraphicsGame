import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        /**
         *     public Rectangle playButton = new Rectangle(Game.WIDTH / 2 + 120, 150,100,50);
         *     public Rectangle helpButton = new Rectangle(Game.WIDTH / 2 + 120, 250,100,50);
         *     public Rectangle quitButton = new Rectangle(Game.WIDTH / 2 + 120, 350,100,50);
         */

        int mouseX = e.getX();
        int mouseY = e.getY();

        //Play Button
        if(mouseX >= Game.WIDTH / 2 + 120  && mouseX <= Game.WIDTH / 2 + 220){
            if(mouseY >= 150 && mouseY <= 200){
                //Pressed Play Button
                Game.state = Game.STATE.GAME;
            }
        }

        //Quit Button
        if(mouseX >= Game.WIDTH / 2 + 120  && mouseX <= Game.WIDTH / 2 + 220){
            if(mouseY >= 350 && mouseY <= 400){
                //Pressed Quit Button
                System.exit(1);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
