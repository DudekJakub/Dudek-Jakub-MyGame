import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

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

    public Vector getVelocity() {
        return velocity;
    }

    public void setVelocity(double x, double y, Items item) {
        item.velocity.set(x, y);
        item.velocity.multiply(1/60.0);
        item.position.add(item.velocity);
    }

    public void addSpeed(Items item, Sprite sprite, double yI) {
        item.setPosition(sprite.getPosition().x, sprite.getPosition().y);
        item.getVelocity().y = item.getVelocity().y + yI;
    }

    public Vector getPosition() {
        return position;
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