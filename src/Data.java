import java.io.*;

public class Data {
    public int speed;

    public int getSpeed() {
        return speed;
    }

    public Data() {
        readSpeed();
    }

    /**
     * 读入TXT文件
     */
    public  void readSpeed() {
        String pathname = "./speed.txt";
        try (FileReader reader = new FileReader(pathname);
             BufferedReader br = new BufferedReader(reader)
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                speed = Integer.parseInt(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
