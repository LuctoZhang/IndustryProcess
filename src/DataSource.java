import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataSource {
    public String[] process1;
    public String[] process2;
    public String[] process3;
    public int[][] time1;
    public int[][] time2;
    public int[][] time3;
    public double[][] fillRate1;
    public double[] fillRate2;
    public double[] fillRate3;
    public double[] tankCurLiquid;
    public float[] tankCapacity;
    public int[] curRunTime = new int[3];
    public double[] boxLiquid = new double[3];
    public float[] boxCapacity = new float[3];
    public int[] C2Time = new int[9];
    public int[] C1Time = new int[8];
    public int[] P1B1Time = new int[7];
    public int[] P2B2Time = new int[7];
    public int[] P3Time = new int[5];
    public int[] P4Time = new int[5];
    public int[] P5Time = new int[5];
    public int[] P1B3Time = new int[7];
    public int[] P2B3Time = new int[7];
    public double[] C1Emission = new double[6];
    public double[] C2Emission = new double[7];
    public double[] P1B1Emission = new double[5];
    public double[] P2B2Emission = new double[5];
    public double[] P3Emission = new double[3];
    public double[] P4Emission = new double[3];
    public double[] P5Emission = new double[3];
    public double[] P1B3Emission = new double[5];
    public double[] P2B3Emission = new double[5];

    public double[] getC1Emission() {
        return C1Emission;
    }
    public double[] getC2Emission() {
        return C2Emission;
    }
    public double[] getP1B1Emission() {
        return P1B1Emission;
    }
    public double[] getP2B2Emission() {
        return P2B2Emission;
    }
    public double[] getP3Emission() {
        return P3Emission;
    }
    public double[] getP4Emission() {
        return P4Emission;
    }
    public double[] getP5Emission() {
        return P5Emission;
    }
    public double[] getP1B3Emission() {
        return P1B3Emission;
    }
    public double[] getP2B3Emission() {
        return P2B3Emission;
    }
    public int[] getC2Time() {
        return C2Time;
    }
    public int[] getC1Time() {
        return C1Time;
    }
    public int[] getP1B1Time() {
        return P1B1Time;
    }
    public int[] getP2B2Time() {
        return P2B2Time;
    }
    public int[] getP3Time() {
        return P3Time;
    }
    public int[] getP4Time() {
        return P4Time;
    }
    public int[] getP5Time() {
        return P5Time;
    }
    public int[] getP1B3Time() {
        return P1B3Time;
    }
    public int[] getP2B3Time() {
        return P2B3Time;
    }
    public String[] getProcess1() {
        return process1;
    }
    public String[] getProcess2() {
        return process2;
    }
    public String[] getProcess3() {
        return process3;
    }
    public int[][] getTime1() {
        return time1;
    }
    public int[][] getTime2() {
        return time2;
    }
    public int[][] getTime3() {
        return time3;
    }
    public double[][] getFillRate1() {
        return fillRate1;
    }
    public double[] getFillRate2() {
        return fillRate2;
    }
    public double[] getFillRate3() {
        return fillRate3;
    }
    public double[] getTankCurLiquid() {
        return tankCurLiquid;
    }
    public float[] getTankCapacity() {
        return tankCapacity;
    }
    public int[] getCurRunTime() {
        return curRunTime;
    }
    public double[] getBoxLiquid() {
        return boxLiquid;
    }
    public float[] getBoxCapacity() {
        return boxCapacity;
    }

    public String[] getProcess(String processSeq){
        String[] process = processSeq.split(", ");
        return process;
    }

    public int[][] getTime(String line){
        int time[][];
        String ti[]=line.split("}, ");
        time = new int[ti.length][];
        for(int m=0;m<ti.length;++m){
            String tmp = ti[m];
            if(m==ti.length-1) {
                tmp = tmp.substring(tmp.indexOf('{') + 1, tmp.length() - 1);
            }else {
                tmp = tmp.substring(tmp.indexOf('{') + 1, tmp.length());
            }
            String piece[] = tmp.split(", ");
            time[m] = new int[piece.length];
            for(int n=0;n<piece.length;++n) {
                int goal = Integer.parseInt(piece[n]);
                time[m][n]=goal;
            }
        }
        return time;
    }

    /**读取文件中的箱子1的装填率序列**/
    public double[][] getFillRate_(String line){
        double fillRate[][];
        String fill[] = line.split("}, ");
        fillRate = new double[fill.length][];
        for (int m=0;m<fill.length;++m) {
            if(m==fill.length-1) {
                fill[m] = fill[m].substring(fill[m].indexOf('{') + 1, fill[m].length()-1);
            }else{
                fill[m] = fill[m].substring(fill[m].indexOf('{') + 1, fill[m].length());
            }
            String fi[]=fill[m].split(",");
            fillRate[m] = new double[fi.length];
            for(int n=0;n<fi.length;++n){
                fillRate[m][n] = Double.parseDouble(fi[n]);
            }
        }
        return fillRate;
    }

    /**读取文件中的箱子2/3的装填率序列**/
    public double[] getFillRate(String line){
        double fillRate[]={};
        String fi[] = line.split(", ");
        fillRate = new double[fi.length];
        for (int m=0;m<fi.length;++m){
            fillRate[m]=Double.parseDouble(fi[m]);
        }
        return fillRate;
    }

    public DataSource(){
        Connection con;
        String driver = "com.mysql.cj.jdbc.Driver";
//        String url = "jdbc:mysql://10.141.222.203:3306/DataCollection?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=false";
//        String user = "admin";
//        String password = "123456";
        String url = "jdbc:mysql://localhost:3306/new?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=false";
        String user = "root";
        String password = "";
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);
            Statement statement = con.createStatement();
            String sql = "SELECT * FROM calResults ORDER BY lastChange DESC LIMIT 1";
            String sql2 = "SELECT tankLevelHighWarning,tankCurLevel FROM tankParameter ORDER BY lastChange DESC,tankName ASC LIMIT 9";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                process1 = getProcess(rs.getString(1));
                process2 = getProcess(rs.getString(2));
                process3 = getProcess(rs.getString(3));
                time1 = getTime(rs.getString(4));
                time2 = getTime(rs.getString(5));
                time3 = getTime(rs.getString(6));
                fillRate1 = getFillRate_(rs.getString(7));
                fillRate2 = getFillRate(rs.getString(8));
                fillRate3 = getFillRate(rs.getString(9));
            }
            rs.close();
            ResultSet rs2 = statement.executeQuery(sql2);
            int count=0;
            tankCapacity = new float[9];
            tankCurLiquid = new double[9];
            while(rs2.next()){
                tankCapacity[count] = Float.parseFloat(rs2.getString(1));
                tankCurLiquid[count] = Double.valueOf(rs2.getString(2));
                count++;
            }
            rs2.close();
            /*****/
            Statement statement1 = con.createStatement();
            String sql3 = "SELECT curRunProcess,cryCurLevel,runTime FROM crystallizerCurState ORDER BY lastChange DESC,forOrder ASC LIMIT 3";
            String sql4 = "SELECT * FROM adjustedCurState ORDER BY lastChange DESC LIMIT 1";
            String sql5 = "SELECT * FROM crystallizerCapacity ORDER BY lastChange DESC LIMIT 1";
            ResultSet rs3 = statement1.executeQuery(sql3);
            count=0;
            while(rs3.next()){
                curRunTime[count] = rs3.getInt(3);
                count++;
            }
            rs3.close();
            ResultSet rs4 = statement1.executeQuery(sql4);
            while(rs4.next()){
                boxLiquid[0] = rs4.getDouble(10);
                boxLiquid[1] = rs4.getDouble(12);
                boxLiquid[2] = rs4.getDouble(14);            }
            rs4.close();
            ResultSet rs5 = statement1.executeQuery(sql5);
            while(rs5.next()){
                boxCapacity[0]=rs5.getFloat(1);
                boxCapacity[1]=rs5.getFloat(2);
                boxCapacity[2]=rs5.getFloat(3);
            }
            rs5.close();
            /***/
            Statement statement2 = con.createStatement();
            String sqlC1 = "SELECT * FROM cry1C1Op ORDER BY lastChange DESC,forOrder ASC LIMIT 6 ";
            String sqlC2 = "SELECT * FROM cry1C2Op ORDER BY lastChange DESC,forOrder ASC LIMIT 7";
            String sqlP1B1 = "SELECT * FROM cry1P1Op ORDER BY lastChange DESC,forOrder ASC LIMIT 5";
            String sqlP2B2 = "SELECT * FROM cry2P2Op ORDER BY lastChange DESC,forOrder ASC LIMIT 5";
            String sqlP3 = "SELECT * FROM cry2P3Op ORDER BY lastChange DESC,forOrder ASC LIMIT 3";
            String sqlP4 = "SELECT * FROM cry2P4Op ORDER BY lastChange DESC,forOrder ASC LIMIT 3";
            String sqlP5 = "SELECT * FROM cry2P5Op ORDER BY lastChange DESC,forOrder ASC LIMIT 3";
            String sqlP1B3 = "SELECT * FROM cry3P1Op ORDER BY lastChange DESC,forOrder ASC LIMIT 5";
            String sqlP2B3 = "SELECT * FROM cry3P2Op ORDER BY lastChange DESC,forOrder ASC LIMIT 5";

            ResultSet rsC1 = statement2.executeQuery(sqlC1);
            C1Time[0] = 0;
            count =1;
            while (rsC1.next()){
                C1Emission[count-1] = rsC1.getDouble(3);
                C1Time[count++] = rsC1.getInt(2);
            }
            C1Time[7] =C1Time[6] + 35;
            rsC1.close();

            ResultSet rsC2 = statement2.executeQuery(sqlC2);
            C2Time[0] = 0;
            count =1;
            while (rsC2.next()){
                C2Emission[count-1] = rsC2.getDouble(3);
                C2Time[count++] = rsC2.getInt(2);
            }
            C2Time[8] =C2Time[7] + 35;
            rsC2.close();

            ResultSet rsP1B1 = statement2.executeQuery(sqlP1B1);
            P1B1Time[0] = 0;
            count=1;
            while(rsP1B1.next()){
                P1B1Emission[count-1] = rsP1B1.getDouble(3);
                P1B1Time[count++] = rsP1B1.getInt(2);
            }
            P1B1Time[6]=P1B1Time[5] + 35;
            rsP1B1.close();

            ResultSet rsP2B2 = statement2.executeQuery(sqlP2B2);
            P2B2Time[0] = 0;
            count=1;
            while(rsP2B2.next()){
                P2B2Emission[count-1]=rsP2B2.getDouble(3);
                P2B2Time[count++] = rsP2B2.getInt(2);
            }
            P2B2Time[6]=P2B2Time[5] + 27;
            rsP2B2.close();

            ResultSet rsP3 = statement2.executeQuery(sqlP3);
            P3Time[0] = 0;
            count=1;
            while(rsP3.next()){
                P3Emission[count-1] = rsP3.getDouble(3);
                P3Time[count++] = rsP3.getInt(2);
            }
            P3Time[4] = P3Time[3] + 22;
            rsP3.close();

            ResultSet rsP4 = statement2.executeQuery(sqlP4);
            P4Time[0] = 0;
            count=1;
            while(rsP4.next()){
                P4Emission[count-1] = rsP4.getDouble(3);
                P4Time[count++] = rsP4.getInt(2);
            }
            P4Time[4] = P4Time[3] + 35;
            rsP4.close();

            ResultSet rsP5 = statement2.executeQuery(sqlP5);
            P5Time[0] = 0;
            count=1;
            while(rsP5.next()){
                P5Emission[count-1] = rsP5.getDouble(3);
                P5Time[count++] = rsP5.getInt(2);
            }
            P5Time[4] = P5Time[3] + 35;
            rsP5.close();

            ResultSet rsP1B3 = statement2.executeQuery(sqlP1B3);
            P1B3Time[0] = 0;
            count=1;
            while(rsP1B3.next()){
                P1B3Emission[count-1] = rsP1B3.getDouble(3);
                P1B3Time[count++] = rsP1B3.getInt(2);
            }
            P1B3Time[6]=P1B3Time[5] + 35;
            rsP1B3.close();

            ResultSet rsP2B3 = statement2.executeQuery(sqlP2B3);
            P2B3Time[0] = 0;
            count=1;
            while(rsP2B3.next()){
                P2B3Emission[count-1] = rsP2B3.getDouble(3);
                P2B3Time[count++] = rsP2B3.getInt(2);
            }
            P2B3Time[6]=P2B3Time[5] + 20;
            rsP2B3.close();
            con.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Sorry,can't find the Driver!");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            System.out.println("Finish successfully!");
        }
    }

}

