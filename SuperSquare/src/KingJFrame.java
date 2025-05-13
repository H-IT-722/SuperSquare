import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KingJFrame extends JFrame implements ActionListener {

    private Timer timer;
    private JPanel jPanel;
    private int a = 30;
    private int PX = 20, PY = 800;
    private int[][] ArrayP = new int[18][2];
    private int FYellowX=-100;
    private int FYellowY=-100;
    private int FblueX=-100;
    private int FblueY=-100;
    private int FGreenX=-100;
    private int FGreenY=-100;
    private int FBlueX=-100;
    private int FBlueY=-100;
    private int FBlackX=-100;
    private int FBlackY=-100;
    private int f=60;
    private double num=0;
    private int[][] D={
            {150,-200},
            {350,-200},
            {550,-200},
            {750,-200},
            {950,-200},
            {1150,-200},
            {1350,-200},
            {1550,-200},
    };
    private int[] vDY= {0,0,0,0,0,0,0,0};
    private int vPX=9;
    private int vPY=27;
    private int gravity=1;
    private boolean startD=false;
    private boolean startF=false;
    private int centerX=830-20;
    private int centerY=686-50;
    private double angle1=0;
    private double angle2=72;
    private double angle3=144;
    private double angle4=216;
    private double angle5=288;
    private int r=286;

    public KingJFrame() {

        //初始化界面
        initJFrame();

        timer = new Timer(15, this);
        timer.start();

        this.createP();

        //绘制
        this.initGamePanel();

        //让界面显示
        this.setVisible(true);
    }

    private void initJFrame() {
        //设置宽高
        setSize(1800, 1080);
        //设置标题
        setTitle("帅气的男生制作的游戏");
        //设置界面置顶
        this.setAlwaysOnTop(true);
        //设置界面居中
        this.setLocationRelativeTo(null);
        //设置关闭模式
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        //取消默认的居中放置
        this.setLayout(null);
        //聚焦
        setFocusable(true);
    }

    private void createP() {
        for (int i = 0; i < 5; i++) {
            ArrayP[i][0] = PX + i * a;
            ArrayP[i][1] = PY;
        }
        for (int i = 0; i < 3; i++) {
            ArrayP[i + 5][0] = PX + 2 * i * a;
            ArrayP[i + 5][1] = PY + a;
        }
        for (int j = 2; j <= 3; j++) {
            for (int i = 0; i < 5; i++) {
                ArrayP[i + 8 + 5 * (j - 2)][0] = PX + i * a;
                ArrayP[i + 8 + 5 * (j - 2)][1] = PY + j * a;
            }
        }
    }

    private void initGamePanel() {
        jPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.setColor(Color.YELLOW);
                for(int i=0;i<8;i++){
                    g.fillRect(D[i][0],D[i][1],20,200);
                }

                g.setColor(new Color(255,215,0));
                g.fillRect(700,700,300,220);
                g.fillRect(660,780,40,140);
                g.fillRect(1000,780,40,140);
                g.fillRect(570,850,50,70);
                g.fillRect(1080,850,50,70);

                g.setColor(Color.RED);
                g.fillRect(570,800,50,50);
                g.fillRect(1080,800,50,50);
                g.fillRect(720,690,260,10);

                g.setColor(new Color(255,230,0));
                g.fillRect(700,600,20,100);
                g.fillRect(980,600,20,100);
                g.fillRect(720,450,260,240);

                g.setColor(Color.RED);
                for (int i = 0; i < 18; i++) {
                    g.fillRect(ArrayP[i][0], ArrayP[i][1], a, a);
                }
                g.setColor(Color.WHITE);
                g.fillRect(PX+a,PY+a,a,a);
                g.fillRect(PX+3*a,PY+a,a,a);

                g.setColor(Color.YELLOW);
                g.fillRect(FYellowX,FYellowY,f,f);

                g.setColor(Color.CYAN);
                g.fillRect(FblueX,FblueY,f,f);

                g.setColor(new Color(30, 190, 30));
                g.fillRect(FGreenX,FGreenY,f,f);

                g.setColor(Color.BLUE);
                g.fillRect(FBlueX,FBlueY,f,f);

                g.setColor(Color.BLACK);
                g.fillRect(FBlackX,FBlackY,f,f);

                g.setColor(Color.DARK_GRAY);
                g.fillRect(0,920,1800,180);
            }
        };
        jPanel.setBounds(0, 0, 1800, 1080);
        add(jPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(startD){
            if(num>40){
                num=4;
            }
            num+=0.5;
            if (num == 5) {
                vDY[0]=20;
            } else if (num == 10) {
                vDY[5]=20;
            } else if (num == 15) {
                vDY[3]=20;
            } else if (num == 20) {
                vDY[7]=20;
            } else if (num == 25) {
                vDY[1]=20;
            } else if (num == 30) {
                vDY[4]=20;
            } else if (num == 35) {
                vDY[2]=20;
            } else if (num == 40) {
                vDY[6]=20;
            }
            for(int i=0;i<8;i++){
                D[i][1]+=vDY[i];
                if(D[i][1]>=880){
                    D[i][1]=-200;
                    vDY[i]=0;
                }
            }
        }

        if(startF){
            FBlackX=820+10-20;
            FBlackY=400-50;
            FblueX=560-20;
            FblueY=600-50;
            FBlueX=1100-20;
            FBlueY=600-50;
            FGreenX=662-20;
            FGreenY=917-50;
            FYellowX=998-20;
            FYellowY=917-50;
        }

        PX+=vPX;
        if(PX>=380){
            PY-=vPY;
            vPY-=gravity;
        }

        if(PY>=690-120 && PX>=720 && PX<=980){
            vPX=0;
            PY=690-120;
            vPY=0;
            gravity=0;
            startD=true;
            startF=true;
        }

        if(startF){
            angle1+=3;
            angle2+=3;
            angle3+=3;
            angle4+=3;
            angle5+=3;
            if(angle1==360){
                angle1=0;
            }
            if(angle2==360){
                angle2=0;
            }
            if(angle3==360){
                angle3=0;
            }
            if(angle4==360){
                angle4=0;
            }
            if(angle5==360){
                angle5=0;
            }
            double radian = Math.toRadians(angle1);
            FBlackX = (int) (centerX + r * Math.cos(radian));
            FBlackY = (int) (centerY + r * Math.sin(radian));
            radian = Math.toRadians(angle2);
            FblueX = (int) (centerX + r * Math.cos(radian));
            FblueY = (int) (centerY + r * Math.sin(radian));
            radian = Math.toRadians(angle3);
            FGreenX = (int) (centerX + r * Math.cos(radian));
            FGreenY = (int) (centerY + r * Math.sin(radian));
            radian = Math.toRadians(angle4);
            FYellowX = (int) (centerX + r * Math.cos(radian));
            FYellowY = (int) (centerY + r * Math.sin(radian));
            radian = Math.toRadians(angle5);
            FBlueX = (int) (centerX + r * Math.cos(radian));
            FBlueY = (int) (centerY + r * Math.sin(radian));
        }

        createP();
        repaint();
    }
}