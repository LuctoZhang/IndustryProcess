import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.TimerTask;


public class Window extends JFrame {
    /**演示速度，推荐值为50,在文件speed.txt中可修改**/
    private int speed;
    private String process1[];
    private String process2[];
    private String process3[];
    private int time1[][];
    private int time2[][];
    private int time3[][];
    private int totTime;
    private double fillRate1[][];
    private double fillRate2[];
    private double fillRate3[];
    private double liquid[];
    private float tankCapacity[];
    private double boxLiquid[];
    private float boxCapacity[];
    private int curRunTime[];
    private static Box[] boxes;
    private static Slot[] slots;
    private static Truck truck;
    private PaintBoxesJPanel jPanel1;
    private int count1;
    private int count2;
    private int count3;
    private int C2Time[];
    private int C1Time[];
    private int P1B1Time[];
    private int P2B2Time[];
    private int P3Time[];
    private int P4Time[];
    private int P5Time[];
    private int P1B3Time[];
    private int P2B3Time[];
    public double[] C1Emission;
    public double[] C2Emission;
    public double[] P1B1Emission;
    public double[] P2B2Emission;
    public double[] P3Emission;
    public double[] P4Emission;
    public double[] P5Emission;
    public double[] P1B3Emission;
    public double[] P2B3Emission;
    private int truckCount=1;
    private int flowInCount[]={1,1,1};
    private int flowOutCount[]={1,1,1};
    private long startTime = System.currentTimeMillis();
    private Timer timer = new Timer(50,new ReboundListener());
    private  Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    DecimalFormat df = new DecimalFormat("0.0");
    private double wh = Double.parseDouble(df.format((double)screenSize.height*0.9/1000));
    private double ww = Double.parseDouble(df.format((double)screenSize.width*0.9/1880));
    private JButton jButton;
    public Window(){
        getDatas();
        count1 = process1.length;
        count2 = process2.length;
        count3 = process3.length;
        Dimension dim=getToolkit().getScreenSize();
        this.setBounds(dim.width/4,dim.height/4,dim.width/2,dim.height/2);
        setTitle("流程可视化");
        setResizable(true);
        setDefault();
        initComponents();
        timer.start();
        getTotTime();
        start();
        end();
    }

    private void getDatas(){
        DataSource data = new DataSource();
        Data data1 =new Data();
        process1=data.getProcess1();
        process2=data.getProcess2();
        process3=data.getProcess3();
        time1=data.getTime1();
        time2=data.getTime2();
        time3=data.getTime3();
        fillRate1=data.getFillRate1();
        fillRate2=data.getFillRate2();
        fillRate3=data.getFillRate3();
        liquid=data.getTankCurLiquid();
        speed=data1.getSpeed();
        tankCapacity=data.getTankCapacity();
        boxLiquid=data.getBoxLiquid();
        boxCapacity=data.getBoxCapacity();
        curRunTime=data.getCurRunTime();
        C2Time=data.getC2Time();
        C1Time=data.getC1Time();
        P1B1Time=data.getP1B1Time();
        P2B2Time=data.getP2B2Time();
        P3Time=data.getP3Time();
        P4Time=data.getP4Time();
        P5Time=data.getP5Time();
        P1B3Time=data.getP1B3Time();
        P2B3Time=data.getP2B3Time();
        C1Emission=data.getC1Emission();
        C2Emission=data.getC2Emission();
        P1B1Emission=data.getP1B1Emission();
        P2B2Emission=data.getP2B2Emission();
        P3Emission=data.getP3Emission();
        P4Emission=data.getP4Emission();
        P5Emission=data.getP5Emission();
        P1B3Emission=data.getP1B3Emission();
        P2B3Emission=data.getP2B3Emission();
    }

    private void getTotTime(){
        int tmp=-1;
        if(tmp < time1[time1.length-1][time1[time1.length-1].length-1]){
            tmp = time1[time1.length-1][time1[time1.length-1].length-1];
        }
        if(tmp < time2[time2.length-1][time2[time2.length-1].length-1]){
            tmp = time2[time2.length-1][time2[time2.length-1].length-1];
        }
        if(tmp < time3[time3.length-1][time3[time3.length-1].length-1]){
            tmp = time3[time3.length-1][time3[time3.length-1].length-1];
        }
        totTime = tmp;
    }

    private void start(){
        if("C2".equals(process1[0])){
            C2forFirst(time1[0]);
        }
        else if("C1".equals(process1[0])) {
            C1forFirst(time1[0]);
        }
        else if("P1".equals(process1[0])) {
            P1B1forFirst(time1[0]);
        }
        if("P2".equals(process2[0])) {
            P2B2forFirst(time2[0]);
        }
        else if("P3".equals(process2[0])) {
            P3forFirst(time2[0]);
        }
        else if("P4".equals(process2[0])) {
            P4forFirst(time2[0]);
        }
        else if("P5".equals(process2[0])){
            P5forFirst(time2[0]);
        }
        if("P1".equals(process3[0])) {
            P1B3forFirst(time3[0]);
        }
        else if("P2".equals(process3[0])){
            P2B3forFirst(time3[0]);
        }
        for(int i=1;i<count1-1;++i){
            if("C2".equals(process1[i])){
                C2(time1[i],i);
            }
            else if("C1".equals(process1[i])) {
                C1(time1[i],i);
            }
            else if("P1".equals(process1[i])) {
                P1B1(time1[i],i);
            }
        }
        for(int i=1;i<count2-1;++i){
            if("P2".equals(process2[i])) {
                P2B2(time2[i],i);
            }
            else if("P3".equals(process2[i])) {
                P3(time2[i],i);
            }
            else if("P4".equals(process2[i])) {
                P4(time2[i],i);
            }
            else {
                P5(time2[i],i);
            }
        }
        for(int i=1;i<count3-1;++i){
            if("P1".equals(process3[i])) {
                P1B3(time3[i],i);
            }
            else {
                P2B3(time3[i],i);
            }
        }
    }

    private void end(){
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                truck.setLiquid(truck.getLiquid()+slots[8].getLiquid());
                slots[8].setLiquid(0);
                this.cancel();
            }}, totTime*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                timer.stop();
                this.cancel();
            }}, (totTime+1000)*speed);
    }

    private void initComponents() {
        jButton = new JButton();
        jButton.setBackground(new Color(220,220,220));
        jButton.setBounds(1500,800,100,50);
        jButton.setText("STOP");
        jPanel1 = new PaintBoxesJPanel();
        this.getContentPane().add(jPanel1);
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setBackground(new java.awt.Color(25,25,25));
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 1000, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 500, Short.MAX_VALUE)
        );
        //jPanel1.add(jButton);
        pack();

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if("Start".equals(jButton.getText())){
                    timer.start();
                    jButton.setText("Stop");
                }else{
                    jButton.setText("Start");
                    timer.stop();
                }
            }
        });

    }

    public void setDefault(){
        boxes = new Box[3];
        for(int i=0;i<boxes.length;i++) {
            boxes[i] = new Box();
        }
        int x[]={200,200,200};
        int y[]={350,550,750};
        String name[]={"箱1","箱2","箱3"};
        String operation[]={"-","-","-"};
        int relatedSlots[][]={{0,1,2,3,4},{5,6,7,8},{}};
        for(int i=0; i<boxes.length; ++i){
            boxes[i].setX((int)(x[i]*ww));
            boxes[i].setY((int)(y[i]*wh));
            boxes[i].setName(name[i]);
            boxes[i].setCapacity(boxCapacity[i]);
            boxes[i].setLiquid(boxLiquid[i]);
            boxes[i].setOperation(operation[i]);
            boxes[i].setRelatedSlots(relatedSlots[i]);
            boxes[i].setWidth((int)(100*ww));
        }
        boxes[0].setState(process1[0]);
        boxes[1].setState(process2[0]);
        boxes[2].setState(process3[0]);
        slots = new Slot[9];
        for(int i=0;i<slots.length;++i) {
            slots[i] = new Slot();
        }
        //int capacity[]={120,120,120,120,120,180,90,90,120};
        int xs[]={400,520,640,760,880,1000,1120,1240,1360};
        int ys[]={100,100,100,100,100,100,100,100,100};
        String names[]={"A10","A11","A12","A13","A14","A15&A20","A16","A17","A18&A19"};
        for (int i=0;i<slots.length;++i){
            slots[i].setName(names[i]);
            slots[i].setCapacity(tankCapacity[i]);
            slots[i].setX((int)(xs[i]*ww));
            slots[i].setY((int)(ys[i]*wh));
            slots[i].setLiquid(liquid[i]);
            slots[i].setWidth((int)(50*ww));
            slots[i].setHeight((int)(140*wh));
        }
        slots[0].setCapacity(120);
        slots[8].setCapacity(120);
        truck = new Truck();
        truck.setLiquid(0);
        truck.setCapacity(500);
    }

    public void C2forFirst(int[] time){
        int runTime = curRunTime[0];
        if(time.length == 1){
            if(runTime < C2Time[7]){
                if (1 < count1 && "C1".equals(process1[1])) {
                    new java.util.Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            boxes[0].setState("-");
                            boxes[0].setOperation("-");
                            this.cancel();
                        }
                    }, (C2Time[7]-runTime-1) * speed);
                } else {
                    new java.util.Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            boxes[0].setOperation("排料");
                            boxes[0].setNext(13);
                            this.cancel();
                        }
                    }, (C2Time[7]-runTime) * speed);
                    new java.util.Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            boxes[0].setNext(0);
                            slots[3].setLiquid(slots[3].getLiquid() + boxes[0].getLiquid());
                            boxes[0].setLiquid(0);
                            boxes[0].setState("空转");
                            boxes[0].setOperation("-");
                            this.cancel();
                        }
                    }, (C2Time[8]-runTime-1) * speed);
                }
            }
            else{
                new java.util.Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        boxes[0].setNext(0);
                        slots[3].setLiquid(slots[3].getLiquid() + boxes[0].getLiquid());
                        boxes[0].setLiquid(0);
                        boxes[0].setState("空转");
                        boxes[0].setOperation("-");
                        this.cancel();
                    }
                }, (C2Time[8]-runTime-1) * speed);
            }
            return;
        }
        int exe = 1;
        if(runTime < C2Time[0]) {
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[0].setState("C2");
                    boxes[0].setOperation("进料+预冷");
                    boxes[0].setLast(11);
                    this.cancel();
                }
            }, time[exe++] * speed);
        }
        if(runTime < C2Time[1]) {
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[0].setFillRate(fillRate1[0][0]);
                    add(0, 1, boxes[0].getFillRate());
                    boxes[0].setLast(0);
                    boxes[0].setState("C2");
                    boxes[0].setOperation("结晶");
                    this.cancel();
                }
            }, time[exe] * speed);
            new java.util.Timer().schedule(new TimerTask(){
                @Override
                public void run() {
                    boxes[0].setOperation("热量返送");
                    this.cancel();
                }}, (time[exe]+200)*speed);
            new java.util.Timer().schedule(new TimerTask(){
                @Override
                public void run() {
                    boxes[0].setOperation("排放");
                    boxes[0].setNext(10);
                    this.cancel();
                }}, (time[exe++]+215)*speed);
        }
        if(runTime < C2Time[2]) {
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    emission(0, 0, boxes[0].getFillRate(), C2Emission[1]);
                    boxes[0].setOperation("发汗");
                    this.cancel();
                }
            }, time[exe++] * speed);
        }
        if(runTime < C2Time[3]) {
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    emission(0, 0, boxes[0].getFillRate(), C2Emission[2]);
                    boxes[0].setNext(0);
                    boxes[0].setOperation("加料融化");
                    boxes[0].setLast(12);
                    this.cancel();
                }
            }, time[exe++] * speed);
        }
        if(runTime < C2Time[4]) {
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[0].setFillRate(fillRate1[0][1]);
                    add(0, 2, boxes[0].getFillRate());
                    boxes[0].setLast(0);
                    boxes[0].setOperation("预冷");
                    this.cancel();
                }
            }, time[exe] * speed);
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[0].setOperation("结晶");
                    this.cancel();
                }
            }, (time[exe] + 40) * speed);
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[0].setOperation("热量返送");
                    this.cancel();
                }
            }, (time[exe] + 210) * speed);
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[0].setOperation("排放");
                    boxes[0].setNext(11);
                    this.cancel();
                }
            }, (time[exe++] + 225) * speed);
        }
        if(runTime < C2Time[5]) {
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    emission(0, 1, boxes[0].getFillRate(), C2Emission[4]);
                    boxes[0].setOperation("发汗");
                    this.cancel();
                }
            }, time[exe] * speed);
        }
        if(runTime < C2Time[6]) {
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    emission(0, 1, boxes[0].getFillRate(), C2Emission[5]);
                    boxes[0].setNext(0);
                    boxes[0].setOperation("融化");
                    this.cancel();
                }
            }, time[exe] * speed);
            if (1 < count1 && "C1".equals(process1[1])) {
                new java.util.Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        boxes[0].setState("-");
                        boxes[0].setOperation("-");
                        this.cancel();
                    }
                }, (time[exe] + 34) * speed);
            } else {
                new java.util.Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        boxes[0].setOperation("排料");
                        boxes[0].setNext(13);
                        this.cancel();
                    }
                }, (time[exe] + 36) * speed);
                new java.util.Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        boxes[0].setNext(0);
                        slots[3].setLiquid(slots[3].getLiquid() + boxes[0].getLiquid());
                        boxes[0].setLiquid(0);
                        boxes[0].setState("空转");
                        boxes[0].setOperation("-");
                        this.cancel();
                    }
                }, (time[exe] + 69) * speed);
            }
        }
    }

    public void C1forFirst(int[] time){
        int runTime = curRunTime[0];
        if(time.length == 1){
            if(runTime < C1Time[6]){
                if(1<count1 && "P1".equals(process1[1])){
                    new java.util.Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            boxes[0].setState("-");
                            boxes[0].setOperation("-");
                            this.cancel();
                        }
                    }, (C1Time[6]-runTime-1) * speed);
                }else{
                    new java.util.Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            boxes[0].setNext(13);
                            boxes[0].setOperation("排料");
                            this.cancel();
                        }
                    }, (C1Time[6]-runTime) * speed);
                    new java.util.Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            boxes[0].setNext(0);
                            slots[3].setLiquid(slots[3].getLiquid() + boxes[0].getLiquid());
                            boxes[0].setLiquid(0);
                            boxes[0].setState("空转");
                            boxes[0].setOperation("-");
                            this.cancel();
                        }
                    }, (C1Time[7]-runTime-1) * speed);
                }
            }
            else{
                new java.util.Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        boxes[0].setNext(0);
                        slots[3].setLiquid(slots[3].getLiquid() + boxes[0].getLiquid());
                        boxes[0].setLiquid(0);
                        boxes[0].setState("空转");
                        boxes[0].setOperation("-");
                        this.cancel();
                    }
                }, (C1Time[7]-runTime-1) * speed);
            }
            return;
        }
        int exe=1;
        if(runTime < C1Time[0]) {
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[0].setFillRate(fillRate1[0][0]);
                    boxes[0].setState("C1");
                    boxes[0].setOperation("进料+预冷");
                    boxes[0].setLast(12);
                    this.cancel();
                }
            }, time[exe++] * speed);
        }
        if(runTime < C1Time[1]) {
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[0].setFillRate(fillRate1[0][0]);
                    add(0, 2, boxes[0].getFillRate());
                    boxes[0].setLast(0);
                    boxes[0].setState("C1");
                    boxes[0].setOperation("结晶");
                    this.cancel();
                }
            }, time[exe] * speed);
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[0].setOperation("热量返送");
                    this.cancel();
                }
            }, (time[exe] + 180) * speed);
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[0].setOperation("排放");
                    boxes[0].setNext(11);
                    this.cancel();
                }
            }, (time[exe++] + 195) * speed);
        }
        if(runTime < C1Time[2]) {
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    emission(0, 1, boxes[0].getFillRate(), C1Emission[1]);
                    boxes[0].setOperation("发汗");
                    this.cancel();
                }
            }, time[exe++] * speed);
        }
        if(runTime < C1Time[3]) {
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    emission(0, 1, boxes[0].getFillRate(), C1Emission[2]);
                    boxes[0].setNext(0);
                    boxes[0].setOperation("融化");
                    this.cancel();
                }
            }, time[exe] * speed);
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[0].setOperation("预冷");
                    this.cancel();
                }
            }, (time[exe] + 30) * speed);
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[0].setOperation("结晶");
                    this.cancel();
                }
            }, (time[exe] + 60) * speed);
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[0].setOperation("热量返送");
                    this.cancel();
                }
            }, (time[exe] + 200) * speed);
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[0].setOperation("排放");
                    boxes[0].setNext(12);
                    this.cancel();
                }
            }, (time[exe++] + 255) * speed);
        }
        if(runTime < C1Time[4]) {
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    emission(0, 2, boxes[0].getFillRate(), C1Emission[3]);
                    boxes[0].setOperation("发汗");
                    this.cancel();
                }
            }, time[exe++] * speed);
        }
        if(runTime < C1Time[5]) {
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    emission(0, 2, boxes[0].getFillRate(), C1Emission[4]);
                    boxes[0].setNext(0);
                    boxes[0].setOperation("融化");
                    this.cancel();
                }
            }, time[exe] * speed);
            if (1 < count1 && "P1".equals(process1[1])) {
                new java.util.Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        boxes[0].setState("-");
                        boxes[0].setOperation("-");
                        this.cancel();
                    }
                }, (time[exe] + 33) * speed);
            } else {
                new java.util.Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        boxes[0].setNext(13);
                        boxes[0].setOperation("排料");
                        this.cancel();
                    }
                }, (time[exe] + 35) * speed);
                new java.util.Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        boxes[0].setNext(0);
                        slots[3].setLiquid(slots[3].getLiquid() + boxes[0].getLiquid());
                        boxes[0].setLiquid(0);
                        boxes[0].setState("空转");
                        boxes[0].setOperation("-");
                        this.cancel();
                    }
                }, (time[exe] + 68) * speed);
            }
        }
    }

    public void P1B1forFirst(int[] time){
        int runTime = curRunTime[0];
        if(time.length == 1){
            if(runTime < P1B1Time[5]){
                new java.util.Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        boxes[0].setOperation("排料");
                        boxes[0].setNext(14);
                        this.cancel();
                    }
                }, (P1B1Time[5] - runTime) * speed);
            }
            if(runTime < P1B1Time[6]){
                new java.util.Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        boxes[0].setNext(0);
                        boxes[0].setLiquid(0);
                        slots[4].setLiquid(slots[4].getLiquid() + 36);
                        boxes[0].setState("空转");
                        boxes[0].setOperation("-");
                        this.cancel();
                    }
                }, (P1B1Time[6] - runTime-1) * speed);
            }
            return;
        }
        int exe =1;
        if(runTime < P1B1Time[0]) {
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[0].setFillRate(fillRate1[0][0]);
                    boxes[0].setState("P1");
                    boxes[0].setOperation("进料预冷");
                    boxes[0].setLast(13);
                    if (slots[3].getLiquid() < 56)
                        slots[3].setLiquid(56);
                    this.cancel();
                }
            }, time[exe++] * speed);
        }
        if(runTime < P1B1Time[1]) {
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[0].setState("P1");
                    add(0, 3, boxes[0].getFillRate());
                    boxes[0].setLast(0);
                    boxes[0].setOperation("结晶");
                    this.cancel();
                }
            }, time[exe] * speed);
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[0].setState("P1");
                    boxes[0].setOperation("热量返送");
                    this.cancel();
                }
            }, (time[exe] + 240) * speed);
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[0].setOperation("排放");
                    boxes[0].setNext(12);
                    this.cancel();
                }
            }, (time[exe++] + 255) * speed);
        }
        if(runTime < P1B1Time[2]) {
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    emission(0, 2, boxes[0].getFillRate(), P1B1Emission[1]);
                    boxes[0].setOperation("发汗1");
                    this.cancel();
                }
            }, time[exe++] * speed);
        }
        if(runTime < P1B1Time[3]) {
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    emission(0, 2, boxes[0].getFillRate(), P1B1Emission[2]);
                    boxes[0].setNext(13);
                    boxes[0].setOperation("发汗2");
                    this.cancel();
                }
            }, time[exe++] * speed);
        }
        if(runTime < P1B1Time[4]) {
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    emission(0, 3, boxes[0].getFillRate(), P1B1Emission[3]);
                    boxes[0].setNext(0);
                    boxes[0].setOperation("加料融化");
                    this.cancel();
                }
            }, time[exe] * speed);
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[0].setOperation("排料");
                    boxes[0].setNext(14);
                    this.cancel();
                }
            }, (time[exe] + 25) * speed);
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[0].setNext(0);
                    boxes[0].setLiquid(0);
                    slots[4].setLiquid(slots[4].getLiquid() + 36);
                    boxes[0].setState("空转");
                    boxes[0].setOperation("-");
                    this.cancel();
                }
            }, (time[exe] + 59) * speed);
        }
    }

    public void P2B2forFirst(int[] time){
        int runTime = curRunTime[1];
        if(time.length == 1){
            if(runTime < P2B2Time[5]){
                if(1<count2 && "P3".equals(process2[1])){
                    new java.util.Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            boxes[1].setState("-");
                            boxes[1].setOperation("-");
                            this.cancel();
                        }
                    }, (P2B2Time[5] - runTime -1) * speed);
                }else {
                    new java.util.Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            boxes[1].setOperation("排料");
                            boxes[1].setNext(15);
                            this.cancel();
                        }
                    }, (P2B2Time[5] -runTime) * speed);
                    new java.util.Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            boxes[1].setNext(0);
                            slots[5].setLiquid(slots[5].getLiquid() + boxes[1].getLiquid());
                            boxes[1].setLiquid(0);
                            boxes[1].setState("空转");
                            boxes[1].setOperation("-");
                            this.cancel();
                        }
                    }, (P2B2Time[6] -runTime-1) * speed);
                }
            }
            else{
                new java.util.Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        boxes[1].setNext(0);
                        slots[5].setLiquid(slots[5].getLiquid() + boxes[1].getLiquid());
                        boxes[1].setLiquid(0);
                        boxes[1].setState("空转");
                        boxes[1].setOperation("-");
                        this.cancel();
                    }
                }, (P2B2Time[6] -runTime-1) * speed);
            }
            return;
        }
        int exe=1;
        if(runTime < P2B2Time[0]) {
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[1].setState("P2");
                    boxes[1].setOperation("进料");
                    boxes[1].setLast(14);
                    this.cancel();
                }
            }, time[exe]++ * speed);
        }
        if(runTime < P2B2Time[1]) {
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[1].setState("P2");
                    boxes[1].setFillRate(fillRate2[0]);
                    add(1, 4, boxes[1].getFillRate());
                    boxes[1].setLast(0);
                    boxes[1].setOperation("结晶");
                    this.cancel();
                }
            }, time[exe] * speed);
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[1].setState("P2");
                    boxes[1].setOperation("热量返送");
                    this.cancel();
                }
            }, (time[exe] + 180) * speed);
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[1].setOperation("排放");
                    boxes[1].setNext(13);
                    this.cancel();
                }
            }, (time[exe++] + 195) * speed);
        }
        if(runTime < P2B2Time[2]) {
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    emission(1, 3, boxes[1].getFillRate(), P2B2Emission[1]);
                    boxes[1].setOperation("发汗1");
                    this.cancel();
                }
            }, time[exe++] * speed);
        }
        if(runTime < P2B2Time[3]) {
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    emission(1, 3, boxes[1].getFillRate(), P2B2Emission[2]);
                    boxes[1].setNext(14);
                    boxes[1].setOperation("发汗2");
                    this.cancel();
                }
            }, time[exe++] * speed);
        }
        if(runTime < P2B2Time[4]){
            new java.util.Timer().schedule(new TimerTask(){
                @Override
                public void run() {
                    emission(1,4,boxes[1].getFillRate(),P2B2Emission[3]);
                    boxes[1].setNext(0);
                    boxes[1].setOperation("加料融化");
                    this.cancel();
                }}, time[exe]*speed);
            if(1<count2 && "P3".equals(process2[1])){
                new java.util.Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        boxes[1].setState("-");
                        boxes[1].setOperation("-");
                        this.cancel();
                    }
                }, (time[exe]+29) * speed);
            }else {
                new java.util.Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        boxes[1].setOperation("排料");
                        boxes[1].setNext(15);
                        this.cancel();
                    }
                }, (time[exe] + 30) * speed);
                new java.util.Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        boxes[1].setNext(0);
                        slots[5].setLiquid(slots[5].getLiquid() + boxes[1].getLiquid());
                        boxes[1].setLiquid(0);
                        boxes[1].setState("空转");
                        boxes[1].setOperation("-");
                        this.cancel();
                    }
                }, (time[exe] + 64) * speed);
            }
        }
    }

    public void P3forFirst(int[] time){
        int runTime = curRunTime[1];
        if(time.length == 1){
            if(runTime < P3Time[3]){
                if(1<count2 && "P4".equals(process2[1])){
                    new java.util.Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            boxes[1].setOperation("-");
                            boxes[1].setState("-");
                            this.cancel();
                        }
                    }, (P3Time[3] - runTime -1) * speed);
                }else {
                    new java.util.Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            boxes[1].setOperation("排料");
                            boxes[1].setNext(16);
                            this.cancel();
                        }
                    }, (P3Time[3] - runTime) * speed);
                    new java.util.Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            boxes[1].setNext(0);
                            slots[6].setLiquid(slots[6].getLiquid() + boxes[1].getLiquid());
                            boxes[1].setLiquid(0);
                            boxes[1].setOperation("-");
                            boxes[1].setState("空转");
                            this.cancel();
                        }
                    }, (P3Time[4] - runTime - 1) * speed);
                }
            }
            else{
                new java.util.Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        boxes[1].setNext(0);
                        slots[6].setLiquid(slots[6].getLiquid() + boxes[1].getLiquid());
                        boxes[1].setLiquid(0);
                        boxes[1].setOperation("-");
                        boxes[1].setState("空转");
                        this.cancel();
                    }
                }, (P3Time[4] - runTime - 1) * speed);
            }
            return;
        }
        int exe = 1;
        if(runTime < P3Time[0]) {
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[1].setFillRate(fillRate2[0]);
                    boxes[1].setState("P3");
                    boxes[1].setOperation("进料");
                    boxes[1].setLast(15);
                    this.cancel();
                }
            }, time[exe++] * speed);
        }
        if(runTime < P3Time[1]) {
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    add(1, 5, boxes[1].getFillRate());
                    boxes[1].setLast(0);
                    boxes[1].setState("P3");
                    boxes[1].setOperation("结晶");
                    this.cancel();
                }
            }, time[exe] * speed);

            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[1].setState("P3");
                    boxes[1].setOperation("热量返送");
                    this.cancel();
                }
            }, (time[exe] + 180) * speed);
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[1].setOperation("排放/发汗");
                    boxes[1].setNext(14);
                    this.cancel();
                }
            }, (time[exe++] + 195) * speed);
        }
        if(runTime < P3Time[2]){
            new java.util.Timer().schedule(new TimerTask(){
                @Override
                public void run() {
                    emission(1,4,boxes[1].getFillRate(),P3Emission[1]);
                    boxes[1].setOperation("加料融化");
                    this.cancel();
                }}, time[exe]*speed);
            if(1<count2 && "P4".equals(process2[1])){
                new java.util.Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        boxes[1].setOperation("-");
                        boxes[1].setState("-");
                        this.cancel();
                    }
                }, (time[exe]+29) * speed);
            }else {
                new java.util.Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        boxes[1].setOperation("排料");
                        boxes[1].setNext(16);
                        this.cancel();
                    }
                }, (time[exe] + 30) * speed);
                new java.util.Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        boxes[1].setNext(0);
                        slots[6].setLiquid(slots[6].getLiquid() + boxes[1].getLiquid());
                        boxes[1].setLiquid(0);
                        boxes[1].setOperation("-");
                        boxes[1].setState("空转");
                        this.cancel();
                    }
                }, (time[exe] + 64) * speed);
            }
        }
    }

    public void P4forFirst(int[] time){
        int runTime = curRunTime[1];
        if(time.length == 1){
            if(runTime < P4Time[3]){
                if (1 < count2 && "P5".equals(process2[1])) {
                    new java.util.Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            boxes[1].setState("-");
                            boxes[1].setOperation("-");
                            this.cancel();
                        }
                    }, (P4Time[3] - runTime -1) * speed);
                } else {
                    new java.util.Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            boxes[1].setOperation("排料");
                            boxes[1].setNext(17);
                            this.cancel();
                        }
                    }, (P4Time[3] - runTime) * speed);
                    new java.util.Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            boxes[1].setNext(0);
                            slots[7].setLiquid(slots[7].getLiquid() + boxes[1].getLiquid());
                            boxes[1].setLiquid(0);
                            boxes[1].setState("空转");
                            boxes[1].setOperation("-");
                            this.cancel();
                        }
                    }, (P4Time[4] - runTime -1) * speed);
                }
            }
            else{
                    new java.util.Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                        boxes[1].setNext(0);
                        slots[7].setLiquid(slots[7].getLiquid() + boxes[1].getLiquid());
                        boxes[1].setLiquid(0);
                        boxes[1].setState("空转");
                        boxes[1].setOperation("-");
                        this.cancel();
                        }
                    }, (P4Time[4]-runTime-1) * speed);
                }
            return;
        }
        int exe=1;
        if(runTime < P4Time[0]) {
            new java.util.Timer().schedule(new TimerTask(){
                @Override
                public void run() {
                    boxes[1].setFillRate(fillRate2[0]);
                    boxes[1].setState("P4");
                    boxes[1].setOperation("进料");
                    boxes[1].setLast(16);
                    this.cancel();
                }}, time[exe++]*speed);
        }
        if(runTime < P4Time[1]) {
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[1].setState("P4");
                    add(1, 6, boxes[1].getFillRate());
                    boxes[1].setLast(0);
                    boxes[1].setOperation("结晶");
                    this.cancel();
                }
            }, time[exe] * speed);
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[1].setState("P4");
                    boxes[1].setOperation("热量返送");
                    this.cancel();
                }
            }, (time[exe] + 180) * speed);
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[1].setOperation("排放/发汗");
                    boxes[1].setNext(15);
                    this.cancel();
                }
            }, (time[exe++] + 195) * speed);
        }
        if(runTime < P4Time[2]) {
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    emission(1, 5, boxes[1].getFillRate(), P4Emission[1]);
                    boxes[1].setOperation("加料融化");
                    boxes[1].setNext(0);
                    this.cancel();
                }
            }, time[exe] * speed);
            if (1 < count2 && "P5".equals(process2[1])) {
                new java.util.Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        boxes[1].setState("-");
                        boxes[1].setOperation("-");
                        this.cancel();
                    }
                }, (time[exe] + 29) * speed);
            } else {
                new java.util.Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        boxes[1].setOperation("排料");
                        boxes[1].setNext(17);
                        this.cancel();
                    }
                }, (time[exe] + 30) * speed);
                new java.util.Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        boxes[1].setNext(0);
                        slots[7].setLiquid(slots[7].getLiquid() + boxes[1].getLiquid());
                        boxes[1].setLiquid(0);
                        boxes[1].setState("空转");
                        boxes[1].setOperation("-");
                        this.cancel();
                    }
                }, (time[exe] + 64) * speed);
            }
        }
    }

    public void P5forFirst(int[] time){
        int runTime = curRunTime[1];
        if(time.length == 1){
            if(runTime < P5Time[3]) {
                new java.util.Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        boxes[1].setOperation("排料");
                        boxes[1].setNext(18);
                        this.cancel();
                    }
                }, (P5Time[3]-runTime) * speed);
            }
            if(runTime < P5Time[4]){
                new java.util.Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        boxes[1].setNext(0);
                        slots[8].setLiquid(slots[8].getLiquid() + boxes[1].getLiquid());
                        boxes[1].setLiquid(0);
                        boxes[1].setState("空转");
                        boxes[1].setOperation("-");
                        this.cancel();
                    }
                }, (P5Time[4]-runTime-1) * speed);
            }
            return;
        }
        int exe=1;
        if(runTime < P5Time[0]) {
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[1].setFillRate(fillRate2[0]);
                    boxes[1].setState("P5");
                    boxes[1].setOperation("进料");
                    boxes[1].setLast(17);
                    this.cancel();
                }
            }, time[exe++] * speed);
        }
        if(runTime < P5Time[1]) {
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    add(1, 7, boxes[1].getFillRate());
                    boxes[1].setState("P5");
                    boxes[1].setLast(0);
                    boxes[1].setOperation("结晶");
                    this.cancel();
                }
            }, time[exe] * speed);
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[1].setState("P5");
                    boxes[1].setOperation("热量返送");
                    this.cancel();
                }
            }, (time[exe] + 180) * speed);
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[1].setOperation("排放/发汗");
                    boxes[1].setNext(16);
                    this.cancel();
                }
            }, (time[exe++] + 195) * speed);
        }
        if(runTime < P5Time[2]) {
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    emission(1, 6, boxes[1].getFillRate(), P5Emission[1]);
                    boxes[1].setOperation("加料融化");
                    boxes[1].setNext(0);
                    this.cancel();
                }
            }, time[exe] * speed);
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[1].setOperation("排料");
                    boxes[1].setNext(18);
                    this.cancel();
                }
            }, (time[exe] + 30) * speed);
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[1].setNext(0);
                    slots[8].setLiquid(slots[8].getLiquid() + boxes[1].getLiquid());
                    boxes[1].setLiquid(0);
                    boxes[1].setState("空转");
                    boxes[1].setOperation("-");
                    this.cancel();
                }
            }, (time[exe] + 64) * speed);
        }
    }

    public void P1B3forFirst(int[] time){
        int runTime = curRunTime[2];
        if(time.length == 1){
            if(runTime < P1B3Time[5]){
                if(1<process3.length && "P2".equals(process3[1])){
                    new java.util.Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            boxes[2].setState("-");
                            boxes[2].setOperation("-");
                            this.cancel();
                        }
                    }, (P1B3Time[5] - runTime -1) * speed);
                }else {
                    new java.util.Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            boxes[2].setOperation("排料");
                            boxes[2].setNext(14);
                            this.cancel();
                        }
                    }, (P1B3Time[5] - runTime) * speed);
                    new java.util.Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            boxes[2].setNext(0);
                            boxes[2].setLiquid(0);
                            slots[4].setLiquid(slots[4].getLiquid() + 34);
                            boxes[2].setState("空转");
                            boxes[2].setOperation("-");
                            this.cancel();
                        }
                    }, (P1B3Time[6] - runTime -1) * speed);
                }
            }
            else{
                new java.util.Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        boxes[2].setNext(0);
                        boxes[2].setLiquid(0);
                        slots[4].setLiquid(slots[4].getLiquid() + 34);
                        boxes[2].setState("空转");
                        boxes[2].setOperation("-");
                        this.cancel();
                    }
                }, (P1B3Time[6] - runTime -1) * speed);
            }
            return;
        }
        int exe=1;
        if(runTime < P1B3Time[0]) {
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[2].setFillRate(fillRate3[0]);
                    boxes[2].setState("P1");
                    boxes[2].setOperation("进料");
                    boxes[2].setLast(13);
                    if (slots[3].getLiquid() < 56)
                        slots[3].setLiquid(56);
                    this.cancel();
                }
            }, time[exe++] * speed);
        }
        if(runTime < P1B3Time[1]) {
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[2].setState("P1");
                    add(2, 3, boxes[2].getFillRate());
                    boxes[2].setLast(0);
                    boxes[2].setOperation("结晶");
                    this.cancel();
                }
            }, time[exe] * speed);
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[2].setOperation("热量返送");
                    this.cancel();
                }
            }, (time[exe] + 240) * speed);
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[2].setOperation("排放");
                    boxes[2].setNext(12);
                    this.cancel();
                }
            }, (time[exe++] + 255) * speed);
        }
        if(runTime < P1B3Time[2]) {
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    emission(2, 2, boxes[2].getFillRate(), P1B3Emission[1]);
                    boxes[2].setOperation("发汗1");
                    this.cancel();
                }
            }, time[exe++] * speed);
        }
        if(runTime < P1B3Time[3]) {
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    emission(2, 2, boxes[2].getFillRate(), P1B3Emission[2]);
                    boxes[2].setNext(13);
                    boxes[2].setOperation("发汗2");
                    this.cancel();
                }
            }, time[exe++] * speed);
        }
        if(runTime < P1B3Time[4]){
            new java.util.Timer().schedule(new TimerTask(){
                @Override
                public void run() {
                    emission(2,3,boxes[2].getFillRate(),P1B3Emission[3]);
                    boxes[2].setNext(0);
                    boxes[2].setOperation("融化");
                    this.cancel();
                }}, time[exe]*speed);
            if(1<process3.length && "P2".equals(process3[1])){
                new java.util.Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        boxes[2].setState("-");
                        boxes[2].setOperation("-");
                        this.cancel();
                    }
                }, (time[exe]+24) * speed);
            }else {
                new java.util.Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        boxes[2].setOperation("排料");
                        boxes[2].setNext(14);
                        this.cancel();
                    }
                }, (time[exe] + 25) * speed);
                new java.util.Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        boxes[2].setNext(0);
                        boxes[2].setLiquid(0);
                        slots[4].setLiquid(slots[4].getLiquid() + 34);
                        boxes[2].setState("空转");
                        boxes[2].setOperation("-");
                        this.cancel();
                    }
                }, (time[exe] + 59) * speed);
            }
        }
    }

    public void P2B3forFirst(int[] time){
        int runTime = curRunTime[2];
        if(time.length == 1){
            if(runTime < P2B3Time[5]){
                new java.util.Timer().schedule(new TimerTask(){
                    @Override
                    public void run() {
                        boxes[2].setOperation("排料");
                        boxes[2].setNext(15);
                        this.cancel();
                    }}, (P2B3Time[5] - runTime)*speed);
            }
            if(runTime < P2B3Time[6]){
                new java.util.Timer().schedule(new TimerTask(){
                    @Override
                    public void run() {
                        boxes[2].setNext(0);
                        slots[5].setLiquid(slots[5].getLiquid()+boxes[2].getLiquid());
                        boxes[2].setLiquid(0);
                        boxes[2].setState("空转");
                        boxes[2].setOperation("-");
                        this.cancel();
                    }}, (P2B3Time[6] - runTime - 1)*speed);
            }
            return;
        }
        int exe = 1;
        if(runTime < P2B3Time[0]){
            new java.util.Timer().schedule(new TimerTask(){
                @Override
                public void run() {
                    boxes[2].setFillRate(fillRate3[0]);
                    boxes[2].setState("P2");
                    boxes[2].setOperation("进料");
                    boxes[2].setLast(14);
                    this.cancel();
                }}, time[exe++]*speed);
        }
        if(runTime < P2B3Time[1]) {
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[2].setState("P2");
                    add(2, 4, boxes[2].getFillRate());
                    boxes[2].setLast(0);
                    boxes[2].setOperation("结晶");
                    this.cancel();
                }
            }, time[exe] * speed);

            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[2].setState("P2");
                    boxes[2].setOperation("热量返送");
                    this.cancel();
                }
            }, (time[exe] + 220) * speed);
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[2].setOperation("排放");
                    boxes[2].setNext(13);
                    this.cancel();
                }
            }, (time[exe++] + 235) * speed);
        }
        if(runTime < P2B3Time[2]) {
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    emission(2, 3, boxes[2].getFillRate(), P2B3Emission[1]);
                    boxes[2].setOperation("发汗1");
                    this.cancel();
                }
            }, time[exe++] * speed);
        }
        if(runTime < P2B3Time[3]) {
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    emission(2, 3, boxes[2].getFillRate(), P2B3Emission[2]);
                    boxes[2].setNext(14);
                    boxes[2].setOperation("发汗2");
                    this.cancel();
                }
            }, time[exe++] * speed);
        }
        if(runTime < P2B3Time[4]) {
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    emission(2, 4, boxes[2].getFillRate(), P2B3Emission[3]);
                    boxes[2].setNext(0);
                    boxes[2].setOperation("融化");
                    this.cancel();
                }
            }, time[exe] * speed);
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[2].setOperation("排料");
                    boxes[2].setNext(15);
                    this.cancel();
                }
            }, (time[exe] + 50) * speed);
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[2].setNext(0);
                    slots[5].setLiquid(slots[5].getLiquid() + boxes[2].getLiquid());
                    boxes[2].setLiquid(0);
                    boxes[2].setState("空转");
                    boxes[2].setOperation("-");
                    this.cancel();
                }
            }, (time[exe] + 84) * speed);
        }
    }

    public void C2(int[] time,final int pos){
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                boxes[0].setState("C2");
                boxes[0].setOperation("进料+预冷");
                boxes[0].setLast(11);
                this.cancel();
            }}, time[0]*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                boxes[0].setFillRate(fillRate1[pos][0]);
                add(0,1,boxes[0].getFillRate());
                boxes[0].setLast(0);
                boxes[0].setState("C2");
                boxes[0].setOperation("结晶");
                this.cancel();
            }}, time[1]*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                boxes[0].setOperation("热量返送");
                this.cancel();
            }}, (time[1]+200)*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                boxes[0].setOperation("排放");
                boxes[0].setNext(10);
                this.cancel();
            }}, (time[1]+215)*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                emission(0,0,boxes[0].getFillRate(),C2Emission[1]);
                boxes[0].setOperation("发汗");
                this.cancel();
            }}, time[2]*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                emission(0,0,boxes[0].getFillRate(),C2Emission[2]);
                boxes[0].setNext(0);
                boxes[0].setOperation("加料融化");
                boxes[0].setLast(12);
                this.cancel();
            }}, time[3]*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                boxes[0].setFillRate(fillRate1[pos][1]);
                add(0,2,boxes[0].getFillRate());
                boxes[0].setLast(0);
                boxes[0].setOperation("预冷");
                this.cancel();
            }}, time[4]*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                boxes[0].setOperation("结晶");
                this.cancel();
            }}, (time[4]+40)*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                boxes[0].setOperation("热量返送");
                this.cancel();
            }}, (time[4]+210)*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                boxes[0].setOperation("排放");
                boxes[0].setNext(11);
                this.cancel();
            }}, (time[4]+225)*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                emission(0,1,boxes[0].getFillRate(),C2Emission[4]);
                boxes[0].setOperation("发汗");
                this.cancel();
            }}, time[5]*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                emission(0,1,boxes[0].getFillRate(),C2Emission[5]);
                boxes[0].setNext(0);
                boxes[0].setOperation("融化");
                this.cancel();
            }}, time[6]*speed);
        if((pos+1)<count1 && "C1".equals(process1[pos + 1])){
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[0].setState("-");
                    boxes[0].setOperation("-");
                    this.cancel();
                }
            }, (time[6]+34) * speed);
        }else{
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[0].setOperation("排料");
                    boxes[0].setNext(13);
                    this.cancel();
                }
            }, (time[6]+36) * speed);
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[0].setNext(0);
                    slots[3].setLiquid(slots[3].getLiquid() + boxes[0].getLiquid());
                    boxes[0].setLiquid(0);
                    boxes[0].setState("空转");
                    boxes[0].setOperation("-");
                    this.cancel();
                }
            }, (time[6]+69) * speed);
        }
    }

    public void C1(int[] time,final int pos){
        int count=0;
        if(pos != 0 && "C2".equals(process1[pos - 1])){
            new java.util.Timer().schedule(new TimerTask(){
                @Override
                public void run() {
                    boxes[0].setFillRate(fillRate1[pos][0]);
                    add(0,2,boxes[0].getFillRate());
                    boxes[0].setState("C1");
                    boxes[0].setOperation("结晶");
                    this.cancel();
                }}, time[count]*speed);
        }else{
            new java.util.Timer().schedule(new TimerTask(){
                @Override
                public void run() {
                    boxes[0].setFillRate(fillRate1[pos][0]);
                    boxes[0].setState("C1");
                    boxes[0].setOperation("进料+预冷");
                    boxes[0].setLast(12);
                    this.cancel();
                }}, time[count++]*speed);
            new java.util.Timer().schedule(new TimerTask(){
                @Override
                public void run() {
                    boxes[0].setFillRate(fillRate1[pos][0]);
                    add(0,2,boxes[0].getFillRate());
                    boxes[0].setLast(0);
                    boxes[0].setState("C1");
                    boxes[0].setOperation("结晶");
                    this.cancel();
                }}, time[count]*speed);
        }
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                boxes[0].setOperation("热量返送");
                this.cancel();
            }}, (time[count]+180)*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                boxes[0].setOperation("排放");
                boxes[0].setNext(11);
                this.cancel();
            }}, (time[count++]+195)*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                emission(0,1,boxes[0].getFillRate(),C1Emission[1]);
                boxes[0].setOperation("发汗");
                this.cancel();
            }}, time[count++]*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                emission(0,1,boxes[0].getFillRate(),C1Emission[2]);
                boxes[0].setNext(0);
                boxes[0].setOperation("融化");
                this.cancel();
            }}, time[count]*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                boxes[0].setOperation("预冷");
                this.cancel();
            }}, (time[count]+30)*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                boxes[0].setOperation("结晶");
                this.cancel();
            }}, (time[count]+60)*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                boxes[0].setOperation("热量返送");
                this.cancel();
            }}, (time[count]+200)*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                boxes[0].setOperation("排放");
                boxes[0].setNext(12);
                this.cancel();
            }}, (time[count++]+255)*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                emission(0,2,boxes[0].getFillRate(),C1Emission[3]);
                boxes[0].setOperation("发汗");
                this.cancel();
            }}, time[count++]*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                emission(0,2,boxes[0].getFillRate(),C1Emission[4]);
                boxes[0].setNext(0);
                boxes[0].setOperation("融化");
                this.cancel();
            }}, time[count]*speed);
        if((pos+1)<count1 && "P1".equals(process1[pos+1])){
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[0].setState("-");
                    boxes[0].setOperation("-");
                    this.cancel();
                }
            }, (time[count]+33) * speed);
        }else{
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[0].setNext(13);
                    boxes[0].setOperation("排料");
                    this.cancel();
                }
            }, (time[count]+35) * speed);
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[0].setNext(0);
                    slots[3].setLiquid(slots[3].getLiquid() + boxes[0].getLiquid());
                    boxes[0].setLiquid(0);
                    boxes[0].setState("空转");
                    boxes[0].setOperation("-");
                    this.cancel();
                }
            }, (time[count]+68) * speed);
        }
    }

    public void P1B1(int[] time,final int pos){
        int count =0;
        if(pos != 0 && "C1".equals(process1[pos - 1])){
            new java.util.Timer().schedule(new TimerTask(){
                @Override
                public void run() {
                    boxes[0].setFillRate(fillRate1[pos][0]);
                    boxes[0].setState("P1");
                    boxes[0].setOperation("结晶");
                    this.cancel();
                }}, time[count]*speed);
        }else{
            new java.util.Timer().schedule(new TimerTask(){
                @Override
                public void run() {
                    boxes[0].setFillRate(fillRate1[pos][0]);
                    boxes[0].setState("P1");
                    boxes[0].setOperation("进料预冷");
                    boxes[0].setLast(13);
                    if(slots[3].getLiquid() < 56)
                        slots[3].setLiquid(56);
                    this.cancel();
                }}, time[count++]*speed);
            new java.util.Timer().schedule(new TimerTask(){
                @Override
                public void run() {
                    boxes[0].setState("P1");
                    add(0,3,boxes[0].getFillRate());
                    boxes[0].setLast(0);
                    boxes[0].setOperation("结晶");
                    this.cancel();
                }}, time[count]*speed);
        }
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                boxes[0].setState("P1");
                boxes[0].setOperation("热量返送");
                this.cancel();
            }}, (time[count]+240)*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                boxes[0].setOperation("排放");
                boxes[0].setNext(12);
                this.cancel();
            }}, (time[count++]+255)*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                emission(0,2,boxes[0].getFillRate(),P1B1Emission[1]);
                boxes[0].setOperation("发汗1");
                this.cancel();
            }}, time[count++]*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                emission(0,2,boxes[0].getFillRate(),P1B1Emission[2]);
                boxes[0].setNext(13);
                boxes[0].setOperation("发汗2");
                this.cancel();
            }}, time[count++]*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                emission(0,3,boxes[0].getFillRate(),P1B1Emission[3]);
                boxes[0].setNext(0);
                boxes[0].setOperation("加料融化");
                this.cancel();
            }}, time[count]*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                boxes[0].setOperation("排料");
                boxes[0].setNext(14);
                this.cancel();
            }}, (time[count]+25)*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                boxes[0].setNext(0);
                boxes[0].setLiquid(0);
                slots[4].setLiquid(slots[4].getLiquid()+36);
                boxes[0].setState("空转");
                boxes[0].setOperation("-");
                this.cancel();
            }}, (time[count]+59)*speed);
    }

    public void P2B2(int[] time,final int pos){
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                boxes[1].setState("P2");
                boxes[1].setOperation("进料");
                boxes[1].setLast(14);
                this.cancel();
            }}, time[0]*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                boxes[1].setState("P2");
                boxes[1].setFillRate(fillRate2[pos]);
                add(1,4,boxes[1].getFillRate());
                boxes[1].setLast(0);
                boxes[1].setOperation("结晶");
                this.cancel();
            }}, time[1]*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                boxes[1].setState("P2");
                boxes[1].setOperation("热量返送");
                this.cancel();
            }}, (time[1]+180)*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                boxes[1].setOperation("排放");
                boxes[1].setNext(13);
                this.cancel();
            }}, (time[1]+195)*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                emission(1,3,boxes[1].getFillRate(),P2B2Emission[1]);
                boxes[1].setOperation("发汗1");
                this.cancel();
            }}, time[2]*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                emission(1,3,boxes[1].getFillRate(),P2B2Emission[2]);
                boxes[1].setNext(14);
                boxes[1].setOperation("发汗2");
                this.cancel();
            }}, time[3]*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                emission(1,4,boxes[1].getFillRate(),P2B2Emission[3]);
                boxes[1].setNext(0);
                boxes[1].setOperation("加料融化");
                this.cancel();
            }}, time[4]*speed);
        if((pos+1)<count2 && "P3".equals(process2[pos + 1])){
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[1].setState("-");
                    boxes[1].setOperation("-");
                    this.cancel();
                }
            }, (time[4]+29) * speed);
        }else{
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[1].setOperation("排料");
                    boxes[1].setNext(15);
                    this.cancel();
                }
            }, (time[4]+30) * speed);
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[1].setNext(0);
                    slots[5].setLiquid(slots[5].getLiquid() + boxes[1].getLiquid());
                    boxes[1].setLiquid(0);
                    boxes[1].setState("空转");
                    boxes[1].setOperation("-");
                    this.cancel();
                }
            }, (time[4]+64) * speed);
        }
    }

    public void P3(int[] time,final int pos) {
        int count =0;
        if(pos != 0 && "P2".equals(process2[pos - 1])){
            new java.util.Timer().schedule(new TimerTask(){
                @Override
                public void run() {
                    boxes[1].setFillRate(fillRate2[pos]);
                    add(1,5,boxes[1].getFillRate());
                    boxes[1].setState("P3");
                    boxes[1].setOperation("结晶");
                    this.cancel();
                }}, time[count]*speed);
        }else{
            new java.util.Timer().schedule(new TimerTask(){
                @Override
                public void run() {
                    boxes[1].setFillRate(fillRate2[pos]);
                    boxes[1].setState("P3");
                    boxes[1].setOperation("进料");
                    boxes[1].setLast(15);
                    this.cancel();
                }}, time[count++]*speed);
            new java.util.Timer().schedule(new TimerTask(){
                @Override
                public void run() {
                    add(1,5,boxes[1].getFillRate());
                    boxes[1].setLast(0);
                    boxes[1].setState("P3");
                    boxes[1].setOperation("结晶");
                    this.cancel();
                }}, time[count]*speed);
        }
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                boxes[1].setState("P3");
                boxes[1].setOperation("热量返送");
                this.cancel();
            }}, (time[count]+180)*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                boxes[1].setOperation("排放/发汗");
                boxes[1].setNext(14);
                this.cancel();
            }}, (time[count++]+195)*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                emission(1,4,boxes[1].getFillRate(),P3Emission[1]);
                boxes[1].setOperation("加料融化");
                this.cancel();
            }}, time[count]*speed);
        if((pos+1)<count2 && "P4".equals(process2[pos + 1])){
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[1].setOperation("-");
                    boxes[1].setState("-");
                    this.cancel();
                }
            }, (time[count]+29) * speed);
        }else{
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[1].setOperation("排料");
                    boxes[1].setNext(16);
                    this.cancel();
                }
            }, (time[count]+30) * speed);
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[1].setNext(0);
                    slots[6].setLiquid(slots[6].getLiquid() + boxes[1].getLiquid());
                    boxes[1].setLiquid(0);
                    boxes[1].setOperation("-");
                    boxes[1].setState("空转");
                    this.cancel();
                }
            }, (time[count]+64) * speed);
        }
    }

    public void P4(int[] time,final int pos){
        int count=0;
        if(pos != 0 && "P3".equals(process2[pos - 1])){
            new java.util.Timer().schedule(new TimerTask(){
                @Override
                public void run() {
                    boxes[1].setFillRate(fillRate2[pos]);
                    add(1,6,boxes[1].getFillRate());
                    boxes[1].setState("P4");
                    boxes[1].setOperation("结晶");
                    this.cancel();
                }}, time[count]*speed);
        }else{
            new java.util.Timer().schedule(new TimerTask(){
                @Override
                public void run() {
                    boxes[1].setFillRate(fillRate2[pos]);
                    boxes[1].setState("P4");
                    boxes[1].setOperation("进料");
                    boxes[1].setLast(16);
                    this.cancel();
                }}, time[count++]*speed);
            new java.util.Timer().schedule(new TimerTask(){
                @Override
                public void run() {
                    boxes[1].setState("P4");
                    add(1,6,boxes[1].getFillRate());
                    boxes[1].setLast(0);
                    boxes[1].setOperation("结晶");
                    this.cancel();
                }}, time[count]*speed);
        }
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                boxes[1].setState("P4");
                boxes[1].setOperation("热量返送");
                this.cancel();
            }}, (time[count]+180)*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                boxes[1].setOperation("排放/发汗");
                boxes[1].setNext(15);
                this.cancel();
            }}, (time[count++]+195)*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                emission(1,5,boxes[1].getFillRate(),P4Emission[1]);
                boxes[1].setOperation("加料融化");
                boxes[1].setNext(0);
                this.cancel();
            }}, time[count]*speed);
        if((pos+1)<count2 && "P5".equals(process2[pos + 1])){
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[1].setState("-");
                    boxes[1].setOperation("-");
                    this.cancel();
                }
            }, (time[count]+29) * speed);
        }else{
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[1].setOperation("排料");
                    boxes[1].setNext(17);
                    this.cancel();
                }
            }, (time[count]+30) * speed);
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[1].setNext(0);
                    slots[7].setLiquid(slots[7].getLiquid() + boxes[1].getLiquid());
                    boxes[1].setLiquid(0);
                    boxes[1].setState("空转");
                    boxes[1].setOperation("-");
                    this.cancel();
                }
            }, (time[count]+64) * speed);
        }
    }

    public void P5(int[] time,final int pos){
        int count=0;
        if(pos != 0 && "P4".equals(process2[pos - 1])){
            new java.util.Timer().schedule(new TimerTask(){
                @Override
                public void run() {
                    boxes[1].setFillRate(fillRate2[pos]);
                    add(1,7,boxes[1].getFillRate());
                    boxes[1].setState("P5");
                    boxes[1].setOperation("结晶");
                    this.cancel();
                }}, time[count]*speed);
        }else{
            new java.util.Timer().schedule(new TimerTask(){
                @Override
                public void run() {
                    boxes[1].setFillRate(fillRate2[pos]);
                    boxes[1].setState("P5");
                    boxes[1].setOperation("进料");
                    boxes[1].setLast(17);
                    this.cancel();
                }}, time[count++]*speed);
            new java.util.Timer().schedule(new TimerTask(){
                @Override
                public void run() {
                    add(1,7,boxes[1].getFillRate());
                    boxes[1].setState("P5");
                    boxes[1].setLast(0);
                    boxes[1].setOperation("结晶");
                    this.cancel();
                }}, time[count]*speed);
        }
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                boxes[1].setState("P5");
                boxes[1].setOperation("热量返送");
                this.cancel();
            }}, (time[count]+180)*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                boxes[1].setOperation("排放/发汗");
                boxes[1].setNext(16);
                this.cancel();
            }}, (time[count++]+195)*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                emission(1,6,boxes[1].getFillRate(),P5Emission[1]);
                boxes[1].setOperation("加料融化");
                boxes[1].setNext(0);
                this.cancel();
            }}, time[count]*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                boxes[1].setOperation("排料");
                boxes[1].setNext(18);
                this.cancel();
            }}, (time[count]+30)*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                boxes[1].setNext(0);
                slots[8].setLiquid(slots[8].getLiquid()+boxes[1].getLiquid());
                boxes[1].setLiquid(0);
                boxes[1].setState("空转");
                boxes[1].setOperation("-");
                this.cancel();
            }}, (time[count]+64)*speed);
    }

    public void P1B3(int[] time,final int pos){
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                boxes[2].setFillRate(fillRate3[pos]);
                boxes[2].setState("P1");
                boxes[2].setOperation("进料");
                boxes[2].setLast(13);
                if(slots[3].getLiquid() < 56)
                    slots[3].setLiquid(56);
                this.cancel();
            }}, time[0]*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                boxes[2].setState("P1");
                add(2,3,boxes[2].getFillRate());
                boxes[2].setLast(0);
                boxes[2].setOperation("结晶");
                this.cancel();
            }}, time[1]*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                boxes[2].setOperation("热量返送");
                this.cancel();
            }}, (time[1]+240)*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                boxes[2].setOperation("排放");
                boxes[2].setNext(12);
                this.cancel();
            }}, (time[1]+255)*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                emission(2,2,boxes[2].getFillRate(),P1B3Emission[1]);
                boxes[2].setOperation("发汗1");
                this.cancel();
            }}, time[2]*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                emission(2,2,boxes[2].getFillRate(),P1B3Emission[2]);
                boxes[2].setNext(13);
                boxes[2].setOperation("发汗2");
                this.cancel();
            }}, time[3]*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                emission(2,3,boxes[2].getFillRate(),P1B3Emission[3]);
                boxes[2].setNext(0);
                boxes[2].setOperation("融化");
                this.cancel();
            }}, time[4]*speed);
        if((pos+1)<process3.length && "P2".equals(process3[pos+1])){
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[2].setState("-");
                    boxes[2].setOperation("-");
                    this.cancel();
                }
            }, (time[4]+24) * speed);
        }else{
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[2].setOperation("排料");
                    boxes[2].setNext(14);
                    this.cancel();
                }
            }, (time[4]+25) * speed);
            new java.util.Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    boxes[2].setNext(0);
                    boxes[2].setLiquid(0);
                    slots[4].setLiquid(slots[4].getLiquid() + 34);
                    boxes[2].setState("空转");
                    boxes[2].setOperation("-");
                    this.cancel();
                }
            }, (time[4]+59) * speed);
        }
    }

    public void P2B3(int[] time,final int pos){
        int count=0;
        if(pos != 0 && "P1".equals(process3[pos - 1])){
            new java.util.Timer().schedule(new TimerTask(){
                @Override
                public void run() {
                    boxes[2].setFillRate(fillRate3[pos]);
                    add(2,4,boxes[2].getFillRate());
                    boxes[2].setState("P2");
                    boxes[2].setOperation("结晶");
                    this.cancel();
                }}, time[count]*speed);
        }else{
            new java.util.Timer().schedule(new TimerTask(){
                @Override
                public void run() {
                    boxes[2].setFillRate(fillRate3[pos]);
                    boxes[2].setState("P2");
                    boxes[2].setOperation("进料");
                    boxes[2].setLast(14);
                    this.cancel();
                }}, time[count++]*speed);
            new java.util.Timer().schedule(new TimerTask(){
                @Override
                public void run() {
                    boxes[2].setState("P2");
                    add(2,4,boxes[2].getFillRate());
                    boxes[2].setLast(0);
                    boxes[2].setOperation("结晶");
                    this.cancel();
                }}, time[count]*speed);
        }
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                boxes[2].setState("P2");
                boxes[2].setOperation("热量返送");
                this.cancel();
            }}, (time[count]+220)*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                boxes[2].setOperation("排放");
                boxes[2].setNext(13);
                this.cancel();
            }}, (time[count++]+235)*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                emission(2,3,boxes[2].getFillRate(),P2B3Emission[1]);
                boxes[2].setOperation("发汗1");
                this.cancel();
            }}, time[count++]*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                emission(2,3,boxes[2].getFillRate(),P2B3Emission[2]);
                boxes[2].setNext(14);
                boxes[2].setOperation("发汗2");
                this.cancel();
            }}, time[count++]*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                emission(2,4,boxes[2].getFillRate(),P2B3Emission[3]);
                boxes[2].setNext(0);
                boxes[2].setOperation("融化");
                this.cancel();
            }}, time[count]*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                boxes[2].setOperation("排料");
                boxes[2].setNext(15);
                this.cancel();
            }}, (time[count]+50)*speed);
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                boxes[2].setNext(0);
                slots[5].setLiquid(slots[5].getLiquid()+boxes[2].getLiquid());
                boxes[2].setLiquid(0);
                boxes[2].setState("空转");
                boxes[2].setOperation("-");
                this.cancel();
            }}, (time[count]+84)*speed);
    }

    public void add(int box,int slot,double rate){
        double tmp1 = 56*rate/100;
        tmp1 = (double)Math.round(tmp1*10)/10;
        double tmp2 = tmp1 - boxes[box].getLiquid();
        tmp2 = (double)Math.round(tmp2*10)/10;
        boxes[box].setLiquid(tmp1);
        tmp2 = (double)Math.round((slots[slot].getLiquid()-tmp2)*10)/10;
        slots[slot].setLiquid(tmp2);
    }

    public void emission(int box,int slot,double rate,double level){
        rate = rate / 100;
        double actual = level*rate;
        actual = (double) Math.round(actual * 10) / 10;
        double tmp = (double)Math.round((slots[slot].getLiquid()+actual)*10) / 10;
        slots[slot].setLiquid(tmp);
        actual = (double)Math.round((boxes[box].getLiquid()-actual) * 10) / 10;
        boxes[box].setLiquid(actual);
    }

    private class ReboundListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            jPanel1.repaint();
        }
    }

    public boolean isFlowIn(int l){
        if (l!=10&&l!=11&&l!=12&&l!=13&&l!=14&&l!=15&&l!=16&&l!=17&&l!=18)
            return false;
        else
            return true;
    }

    public boolean isFlowOut(int l){
        if (l!=10&&l!=11&&l!=12&&l!=13&&l!=14&&l!=15&&l!=16&&l!=17&&l!=18)
            return false;
        else
            return true;
    }


    public class PaintBoxesJPanel extends JPanel implements ActionListener {
        @Override
        public void paint(Graphics g){
            super.paint(g);
            BasicStroke stroke = new BasicStroke(4.0f);
            Graphics2D g2d = (Graphics2D)g;
            g2d.setStroke(stroke);
            g.setColor(Color.white);
            g.setFont(new Font("宋体",Font.PLAIN, 22));
            long endTime = System.currentTimeMillis();
            long day = (endTime - startTime) /speed/60/24;
            long hour = (endTime - startTime) /speed/60 - 24*day;
            long minute = (endTime - startTime) /speed % 60;
            g.drawString("目前执行时间："+day + "天"+ hour+"时"+minute+"分",(int)(10*ww),(int)(30*wh));
            g.setFont(new Font("宋体",Font.PLAIN, 20));
            /**绘制箱子**/
            drawBoxes(g,g2d);
            /**绘制小货车**/
            drawTruck(g,g2d);
            /**绘制槽子**/
            drawSlots(g);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            this.repaint();
        }

        public void drawTruck(Graphics g,Graphics2D g2d){
            Color curColor = new Color(0,90,255);
            g.setColor(Color.white);
            int posx = (int)(1500*ww);
            int posy = (int)(300*wh);
            truck.setX(posx+(int)(50*ww));
            truck.setY(posy-(int)(10*wh));
            g.drawLine(posx+(int)(15*ww),posy,posx,posy+(int)(30*wh));
            g.drawLine(posx+(int)(15*ww),posy,posx+(int)(30*ww),posy);
            g.drawRect(posx+(int)(30*ww),posy,(int)(20*ww),(int)(30*wh));
            g.drawRect(posx,posy+(int)(30*wh),(int)(50*ww),(int)(30*wh));
            g.drawRect(truck.getX(),truck.getY(),(int)(90*ww),(int)(70*wh));
            g.drawArc(posx+(int)(15*ww),posy+(int)(50*wh),(int)(22*ww),(int)(22*wh),180,180);
            g.drawArc(posx+(int)(95*ww),posy+(int)(50*wh),(int)(22*ww),(int)(22*wh),180,180);
            g.drawLine(slots[8].getX()+slots[8].getWidth(),slots[8].getY()+slots[8].getHeight()/2,posx+(int)(95*ww),slots[8].getY()+slots[8].getHeight()/2);
            g.drawLine(posx+(int)(95*ww),slots[8].getY()+slots[8].getHeight()/2,posx+(int)(95*ww),posy-(int)(10*wh));
            if(truck.getLiquid()>truck.getCapacity()){
                g.drawString(truck.getLiquid()+"",truck.getX()+(int)(110*ww),truck.getY());
                g.setColor(curColor);
                g.fillRect(truck.getX()+2,truck.getY(),(int)(86*ww),(int)(70*wh));
            }else {
                int tmp = (int) truck.getLiquid() * (int)(70*wh) / truck.getCapacity();
                g.drawString(truck.getLiquid() + "", truck.getX()+(int)(110*ww), truck.getY() + (int)(70*wh) - tmp);
                g.setColor(curColor);
                g.fillRect(truck.getX()+2, truck.getY() + (int)(70*wh) - tmp, (int)(86*ww), tmp);
            }
            BasicStroke stroke2 = new BasicStroke(6.0f);
            g2d.setStroke(stroke2);
            int threshold = (int)(slots[8].getCapacity() * 0.8);
            int l1 = (truck.getX()+(int)(45*ww) -(slots[8].getX()+slots[8].getWidth()))/12;
            int l2 = (truck.getY() - (slots[8].getY()+slots[8].getHeight()/2))/8;
            if(slots[8].getLiquid() > threshold) {
                if(truckCount<=12){
                    g.drawLine(slots[8].getX()+slots[8].getWidth(),slots[8].getY()+slots[8].getHeight()/2,slots[8].getX()+slots[8].getWidth()+l1*truckCount,slots[8].getY()+slots[8].getHeight()/2);
                }else if(truckCount<=20){
                    g.drawLine(slots[8].getX()+slots[8].getWidth(),slots[8].getY()+slots[8].getHeight()/2,truck.getX()+(int)(45*ww),slots[8].getY()+slots[8].getHeight()/2);
                    g.drawLine(truck.getX()+(int)(45*ww),slots[8].getY()+slots[8].getHeight()/2,truck.getX()+(int)(45*ww),slots[8].getY()+slots[8].getHeight()/2+l2*(truckCount-12));
                }else{
                    truck.setLiquid(truck.getLiquid() + slots[8].getLiquid() - slots[8].getCapacity() * 0.2);
                    slots[8].setLiquid(slots[8].getCapacity() * 0.2);
                    truckCount=1;
                }
                truckCount++;
            }
            g.setColor(Color.white);
        }

        public void drawSlots(Graphics g){
            for(int i=0;i<slots.length;++i) {
                g.setColor(Color.white);
                g.drawLine(slots[i].getX(),slots[i].getY(),slots[i].getX(),slots[i].getY()+slots[i].getHeight());
                g.drawLine(slots[i].getX(),slots[i].getY()+slots[i].getHeight(),slots[i].getX()+slots[i].getWidth(),slots[i].getY()+slots[i].getHeight());
                g.drawLine(slots[i].getX()+slots[i].getWidth(),slots[i].getY()+slots[i].getHeight(),slots[i].getX()+slots[i].getWidth(),slots[i].getY());
                g.drawLine(slots[i].getX(),slots[i].getY(),slots[i].getX()+slots[i].getWidth()/2,slots[i].getY()-20);
                g.drawLine(slots[i].getX()+slots[i].getWidth()/2,slots[i].getY()-20,slots[i].getX()+slots[i].getWidth(),slots[i].getY());
                g.drawString(slots[i].getName(), slots[i].getX(), slots[i].getY()-25);
                if(slots[i].getLiquid()>slots[i].getCapacity()){
                    g.drawString(slots[i].getLiquid()+"",slots[i].getX()-50,slots[i].getY());
                    if(i==slots.length-1)
                        g.setColor(new Color(0,80,255));
                    else
                        g.setColor(new Color(0,191,255));
                    g.fillRect(slots[i].getX()+2,slots[i].getY(),slots[i].getWidth()-4,slots[i].getHeight());
                }else {
                    int tmp = (int) slots[i].getLiquid() * slots[i].getHeight() / (int)slots[i].getCapacity();
                    g.drawString(slots[i].getLiquid() + "", slots[i].getX() - 50, slots[i].getY() + slots[i].getHeight() - tmp);
                    if(i==slots.length-1)
                        g.setColor(new Color(0,80,255));
                    else
                        g.setColor(new Color(0,191,255));
                    g.fillRect(slots[i].getX()+2, slots[i].getY() + slots[i].getHeight() - tmp, slots[i].getWidth()-4, tmp);
                }
            }
        }

        public void drawBoxes(Graphics g,Graphics2D g2d){
            BasicStroke stroke = new BasicStroke(4.0f);
            for(int i=0;i<boxes.length;++i) {
                g.setColor(Color.white);
                g.drawRect(boxes[i].getX(),boxes[i].getY(), boxes[i].getWidth(),boxes[i].getWidth());
                g.drawString(boxes[i].getName(), boxes[i].getX()+(int)(30*ww), boxes[i].getY()+(int)(120*wh));
                g.drawString(boxes[i].getState(),boxes[i].getX()+5,boxes[i].getY()-15);
                g.drawString(boxes[i].getOperation(),boxes[i].getX()+(int)(30*ww),boxes[i].getY()-15);
                g.drawString("装填率：",boxes[i].getX()+(int)(105*ww),boxes[i].getY()+(int)(10*wh));
                double tmpRate =(double) Math.round(boxes[i].getFillRate() * 100) / 100;
                g.drawString(tmpRate+"%",boxes[i].getX()+(int)(105*ww),boxes[i].getY()+(int)(30*wh));
                int tmp=(int)boxes[i].getLiquid()*boxes[i].getWidth()/(int)boxes[i].getCapacity();
                g.drawString(boxes[i].getLiquid()+"",boxes[i].getX()-(int)(50*ww),boxes[i].getY()+boxes[i].getWidth()-tmp);
                g.setColor(new java.awt.Color(0,191,255));
                g.fillRect(boxes[i].getX()+2,boxes[i].getY()+boxes[i].getWidth()-tmp,boxes[i].getWidth()-4,tmp);
                /**绘制管道**/
                BasicStroke stroke3 = new BasicStroke(8.0f);
                g2d.setStroke(stroke3);
                g.setColor(Color.white);
                for(int m=0;m<boxes[i].getRelatedSlots().length;++m) {
                    int tmpS = boxes[i].getRelatedSlots()[m];
                    g.drawLine(boxes[i].getX() + boxes[i].getWidth(), boxes[i].getY() + boxes[i].getWidth()/2, slots[tmpS].getX() + slots[tmpS].getWidth()/2, boxes[i].getY() + boxes[i].getWidth()/2);
                    g.drawLine(slots[tmpS].getX() + slots[tmpS].getWidth()/2, boxes[i].getY() + boxes[i].getWidth()/2, slots[tmpS].getX() +slots[tmpS].getWidth()/2, slots[tmpS].getY() +slots[tmpS].getHeight());
                    if (m != boxes[i].getRelatedSlots().length-1) {
                        g.fillOval(slots[tmpS].getX()+slots[tmpS].getWidth()/2-10, boxes[i].getY()+boxes[i].getWidth()/2-10, 20, 20);
                    }
                }
                if(i==1){
                    for(int m=3;m<=4;++m){
                        g.drawLine(boxes[i].getX()+boxes[i].getWidth(), boxes[i].getY()+boxes[i].getWidth()/2, slots[m].getX() +slots[m].getWidth()/2, boxes[i].getY()+boxes[i].getWidth()/2);
                        g.drawLine(slots[m].getX()+slots[m].getWidth()/2, boxes[i].getY()+boxes[i].getWidth()/2, slots[m].getX()+slots[m].getWidth()/2, boxes[0].getY()+boxes[0].getWidth()/2);
                        g.fillOval(slots[m].getX()+slots[m].getWidth()/2-10, boxes[i].getY()+boxes[i].getWidth()/2-10, 20, 20);
                    }
                }
                if(i==2){
                    g.drawLine(boxes[i].getX()+boxes[i].getWidth(), boxes[i].getY()+boxes[i].getWidth()/2, slots[2].getX()+slots[2].getWidth()/2, boxes[i].getY() + boxes[i].getWidth()/2);
                    g.drawLine(slots[2].getX() +slots[2].getWidth()/2, boxes[i].getY() +boxes[i].getWidth()/2, slots[2].getX() +slots[2].getWidth()/2, boxes[0].getY()+boxes[0].getWidth()/2);
                    g.fillOval(slots[2].getX()+slots[2].getWidth()/2-10, boxes[i].getY()+boxes[i].getWidth()/2-10, 20, 20);
                    for(int m=3;m<=5;++m){
                        g.drawLine(boxes[i].getX()+boxes[i].getWidth(), boxes[i].getY()+boxes[i].getWidth()/2, slots[m].getX()+slots[m].getWidth()/2, boxes[i].getY()+boxes[i].getWidth()/2);
                        g.drawLine(slots[m].getX()+slots[m].getWidth()/2, boxes[i].getY()+boxes[i].getWidth()/2, slots[m].getX()+slots[m].getWidth()/2, boxes[1].getY()+boxes[1].getWidth()/2);
                        if (m != 5) {
                            g.fillOval(slots[m].getX()+slots[m].getWidth()/2-10, boxes[i].getY()+boxes[i].getWidth()/2-10, 20, 20);
                        }
                    }
                }
            }
            for(int i=0;i<boxes.length;++i){
                g.setColor(new java.awt.Color(0,191,255));
                BasicStroke stroke2 = new BasicStroke(6.0f);
                g2d.setStroke(stroke2);
                if(isFlowIn(boxes[i].getLast())){
                    /**绘制液体流动**/
                    int last = boxes[i].getLast()-10;
                    int length1 = (slots[last].getX()+slots[last].getWidth()/2) - (boxes[i].getX()+boxes[i].getWidth());
                    int length2 = (boxes[i].getY()+boxes[i].getWidth()/2) - (slots[last].getY()+slots[last].getHeight());
                    int l = (length1+length2)/20;
                    int vertical = length2/l;
                    if(flowInCount[i]<=vertical){
                        g.drawLine(slots[last].getX()+slots[last].getWidth()/2, slots[last].getY()+slots[last].getHeight(), slots[last].getX()+slots[last].getWidth()/2, slots[last].getY()+slots[last].getHeight()+flowInCount[i]*l);
                    }else{
                        g.drawLine(slots[last].getX()+slots[last].getWidth()/2, slots[last].getY()+slots[last].getHeight(), slots[last].getX()+slots[last].getWidth()/2, boxes[i].getY()+boxes[i].getWidth()/2);
                        if((flowInCount[i]-vertical)*l<length1)
                            g.drawLine(slots[last].getX()+slots[last].getWidth()/2,boxes[i].getY()+boxes[i].getWidth()/2,slots[last].getX()+slots[last].getWidth()/2-(flowInCount[i]-vertical)*l,boxes[i].getY()+boxes[i].getWidth()/2);
                        else
                            g.drawLine(slots[last].getX()+slots[last].getWidth()/2,boxes[i].getY()+boxes[i].getWidth()/2,boxes[i].getX()+boxes[i].getWidth(),boxes[i].getY()+boxes[i].getWidth()/2);
                    }
                    flowInCount[i]++;
                }else if(isFlowOut(boxes[i].getNext())){
                    /**绘制液体流动**/
                    int next = boxes[i].getNext()-10;
                    int length1 = (slots[next].getX()+slots[next].getWidth()/2) - (boxes[i].getX()+boxes[i].getWidth());
                    int length2 = (boxes[i].getY()+boxes[i].getWidth()/2) - (slots[next].getY()+slots[next].getHeight());
                    int l =(length1+length2)/20;
                    int horizontal = length1/l;
                    if (flowOutCount[i]<=horizontal){
                        g.drawLine(boxes[i].getX()+boxes[i].getWidth(),boxes[i].getY()+boxes[i].getWidth()/2,boxes[i].getX()+boxes[i].getWidth()+flowOutCount[i]*l,boxes[i].getY()+boxes[i].getWidth()/2);
                    }else{
                        g.drawLine(boxes[i].getX()+boxes[i].getWidth(),boxes[i].getY()+boxes[i].getWidth()/2,slots[next].getX()+slots[next].getWidth()/2,boxes[i].getY()+boxes[i].getWidth()/2);
                        if((flowOutCount[i]-horizontal)*l<length2)
                            g.drawLine(slots[next].getX()+slots[next].getWidth()/2,boxes[i].getY()+boxes[i].getWidth()/2,slots[next].getX()+slots[next].getWidth()/2,boxes[i].getY()+boxes[i].getWidth()/2-(flowOutCount[i]-horizontal)*l);
                        else
                            g.drawLine(slots[next].getX()+slots[next].getWidth()/2,boxes[i].getY()+boxes[i].getWidth()/2,slots[next].getX()+slots[next].getWidth()/2,slots[next].getY()+slots[next].getHeight());

                    }
                    flowOutCount[i]++;
                }
                g2d.setStroke(stroke);
                if(!isFlowIn(boxes[i].getLast()))
                    flowInCount[i]=1;
                if(!isFlowOut(boxes[i].getNext()))
                    flowOutCount[i]=1;
            }
        }
    }
}