import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class Items {

    private double str;
    private double intel;

    private double xI;
    private double yI;

    public Vector position;
    public Vector velocity;
    public Image image;
    public Rectangle boundary;

    public Items(double str, double intel) {
        this.str = str;
        this.intel = intel;

        position = new Vector(0,0);
        velocity = new Vector(0,0);
        boundary = new Rectangle(0,0,0,0);
    }

    public double getStr() {
        return str;
    }

    public double getIntel() {
        return intel;
    }

    public void setPosition(double x, double y){
        position.set(x,y);
    }

    public void setImage(String filename) {

        image = new Image(filename);
        boundary.width = image.getWidth();
        boundary.height = image.getHeight();
    }

    public Rectangle getBoundary() {
        boundary.x = position.x;
        boundary.y = position.y;
        return boundary;
    }

    public void render(GraphicsContext context) {
        context.drawImage(image, position.x, position.y);
    }

}
