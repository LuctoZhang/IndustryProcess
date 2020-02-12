import java.awt.*;

/**
 * @author Lucto
 * @date 2019/10/23
 */
public class Truck {
    private int x;
    private int y;
    private double liquid;
    private int capacity;
    private Color color;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getLiquid() {
        return liquid;
    }

    public void setLiquid(double liquid) {
        if(liquid<=0.1)
            this.liquid = 0;
        else {
            liquid = (double)Math.round(liquid*10)/10;
            this.liquid = liquid;
        }
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
