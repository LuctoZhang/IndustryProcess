/**
 * @author Lucto
 * @date 2019/08/18
 */
public class Box {
    private String name;
    private float capacity;
    private double liquid;
    private String state;
    private String operation;
    private double fillRate;
    private int next;
    private int last;
    private int x;
    private int y;
    private int[] relatedSlots;
    private int width;

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
        if(liquid<=0.1)
            this.liquid = 0;
        else {
            liquid = (double) Math.round(liquid*10)/10;
            this.liquid = liquid;
        }
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public int getLast() {
        return last;
    }

    public void setLast(int last) {
        this.last = last;
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

    public double getFillRate() {
        return fillRate;
    }

    public void setFillRate(double fillRate) {
        this.fillRate = fillRate;
    }

    public int[] getRelatedSlots() {
        return relatedSlots;
    }

    public void setRelatedSlots(int[] relatedSlots) {
        this.relatedSlots = relatedSlots;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
