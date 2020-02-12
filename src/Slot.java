/**
 * @author Lucto
 * @date 2019/08/18
 */
public class Slot {
    private String name;
    private float capacity;
    private double liquid;
    private int operation;
    private int x;
    private int y;
    private int width;
    private int height;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCapacity() {
        return capacity;
    }

    public void setCapacity(float capacity) {
        this.capacity = capacity;
    }

    public double getLiquid() {
        return liquid;
    }

    public void setLiquid(double liquid) {
        if(liquid<=0.1) {
            this.liquid = 0;
        }else {
            liquid = (double) Math.round(liquid*10)/10;
            this.liquid = liquid;
        }
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
