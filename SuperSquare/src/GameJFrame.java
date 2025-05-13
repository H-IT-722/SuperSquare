import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameJFrame extends JFrame implements KeyListener, ActionListener {

    private boolean isWin=false;
    private int a = 10;
    private int PX = 20, PY = 930;
    private int[][] ArrayP = new int[18][2];
    private boolean isJumping = false;
    private boolean isQ=false;
    private boolean isFB=false;
    private boolean isFH=false;
    private int velocityY = 0;
    private int gravity = 2;
    private int g = 7;
    private int direction=1;    //1表示现在角色朝右   2表示现在角色朝左
    private Timer timer;
    private boolean[] keys = new boolean[256];
    private JPanel jPanel;
    private int colorNum = 0;
    private static int topY;
    private static int bottomY;
    private static int rightX;
    private static int leftX;
    private static int topFY;
    private static int bottomFY;
    private static int rightFX;
    private static int leftFX;
    private static int rightBX;
    private static int leftBX;
    //临时果子
    // 通过专用变量跟踪临时果子
    private int[] currentThrownFruit;
    private int velocityFY=0;
    private int velocityFX=0;
    private int gravityF;
    private int FY;
    private int FX;
    private int colorFNum;//临时果子颜色
    //蓝色果子F键技能释放的蓝块
    private boolean checkh=true;
    private int b=20;
    private int BX=1800;
    private int BY;
    private int velocityBX;
    //黑色果子F键技能释放的黑块
    private int HX=1800;
    private int HY=0;
    private int velocityHX;
    //小果子
    int c1=0,c2=0,c3=0,c4=0;

    private int[][] landsA = {

            {0, 1000, 300, 40},
            {400, 1000, 300, 40},
            {1000, 1000, 300, 40},
            {1400, 1000, 350, 40},

            {0, 850, 100, 20},
            {200, 850, 300, 20},
            {1200, 850, 300, 20},
            {1600, 850, 100, 20},

            {0, 700, 300, 20},
            {400, 700, 200, 20},
            {1100, 700, 700, 20},
    };
    private int[][] landsB = {
            {800, 850, 100, 20},
            {100, 550, 1400, 20},
            {0, 400, 200, 20},
            {200, 400, 20, 150},

            {1500, 350, 300, 20},
            {1600, 300, 100, 50},

            {0, 250, 100, 20},

            {1600, 100, 100, 20}
    };
    private int[][] landsC = {
            {300, 350, 100, 20},
            {500, 350, 100, 20},
            {700, 350, 100, 20},
            {900, 350, 100, 20},
            {1100, 350, 100, 20},
            {1300, 350, 100, 20},

            {100, 850, 100, 20},
            {1500, 850, 100, 20}
    };
    private int[][] landsD = {
            {1650, 90, 50, 10},
            {1674, 40, 2, 50},
            {1674, 40, 20, 10}
    };
    private int[][] landsE = {
            {850, 120, 10, 80}
    };
    ArrayList<int[]> fruit = new ArrayList<>();


    public GameJFrame() {

        //初始化界面
        initJFrame();

        timer = new Timer(15, this);
        timer.start();

        if(isWin){
            dispose();
            new KingJFrame();
        }

        this.createP();

        this.createFruit();

        this.eat();

        //绘制
        this.initGamePanel();

        //让界面显示
        this.setVisible(true);
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

    private void createFruit() {
        fruit.add(new int[]{500, 975, 25, 25, 1});
        fruit.add(new int[]{1500, 975, 25, 25, 2});
        fruit.add(new int[]{50, 825, 25, 25, 3});
        fruit.add(new int[]{150, 525, 25, 25, 4});
        fruit.add(new int[]{1550, 325, 25, 25, 4});
        fruit.add(new int[]{1650, 275, 25, 25, 5});
        fruit.add(new int[]{20, 225, 25, 25, 4});
        fruit.add(new int[]{800, 525, 25, 25, 2});
        fruit.add(new int[]{1000, 525, 25, 25, 3});
        fruit.add(new int[]{1200, 525, 25, 25, 2});
    }

    private void initGamePanel() {
        jPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                //给背景涂色
                g.setColor(new Color(229, 229, 229));
                g.fillRect(0, 0, 1800, 1080);

                //对地面上色
                g.setColor(new Color(10, 130, 10));
                for (int i = 0; i < landsA.length; i++) {
                    g.fillRect(landsA[i][0], landsA[i][1], landsA[i][2], landsA[i][3]);
                }
                g.setColor(Color.GREEN);
                for (int i = 0; i < landsB.length; i++) {
                    g.fillRect(landsB[i][0], landsB[i][1], landsB[i][2], landsB[i][3]);
                }
                g.setColor(Color.CYAN);
                for (int i = 0; i < landsC.length; i++) {
                    g.fillRect(landsC[i][0], landsC[i][1], landsC[i][2], landsC[i][3]);
                }
                g.setColor(Color.RED);
                for (int i = 0; i < landsD.length; i++) {
                    g.fillRect(landsD[i][0], landsD[i][1], landsD[i][2], landsD[i][3]);
                }
                g.setColor(Color.BLACK);
                for (int i = 0; i < landsE.length; i++) {
                    g.fillRect(landsE[i][0], landsE[i][1], landsE[i][2], landsE[i][3]);
                }

                //点缀
                g.setColor(Color.BLACK);
                g.fillRect(280, 1020, 20, 20);
                g.fillRect(400, 1020, 20, 20);
                g.fillRect(1280, 1020, 20, 20);
                g.fillRect(1400, 1020, 20, 20);
                g.fillRect(330, 855 , 15, 15);
                g.fillRect(355, 855 , 15, 15);
                g.fillRect(1330, 855, 15, 15);
                g.fillRect(1355, 855, 15, 15);


                //给果子上色
                for (int i = 0; i < fruit.size(); i++) {
                    if (fruit.get(i)[4] == 1) {
                        g.setColor(Color.YELLOW);
                        g.fillRect(fruit.get(i)[0], fruit.get(i)[1], fruit.get(i)[2], fruit.get(i)[3]);
                    } else if (fruit.get(i)[4] == 2) {
                        g.setColor(Color.CYAN);
                        g.fillRect(fruit.get(i)[0], fruit.get(i)[1], fruit.get(i)[2], fruit.get(i)[3]);
                    } else if (fruit.get(i)[4] == 3) {
                        g.setColor(new Color(30, 190, 30));
                        g.fillRect(fruit.get(i)[0], fruit.get(i)[1], fruit.get(i)[2], fruit.get(i)[3]);
                    } else if (fruit.get(i)[4] == 4) {
                        g.setColor(Color.BLUE);
                        g.fillRect(fruit.get(i)[0], fruit.get(i)[1], fruit.get(i)[2], fruit.get(i)[3]);
                    } else if (fruit.get(i)[4] == 5) {
                        g.setColor(Color.BLACK);
                        g.fillRect(fruit.get(i)[0], fruit.get(i)[1], fruit.get(i)[2], fruit.get(i)[3]);
                    }
                }

                //对角色上色
                //选择上什么颜色
                if (colorNum == 0) {
                    g.setColor(Color.RED);
                } else if (colorNum == 1) {
                    g.setColor(Color.YELLOW);
                } else if (colorNum == 2) {
                    g.setColor(Color.CYAN);
                } else if (colorNum == 3) {
                    g.setColor(new Color(30, 190, 30));
                } else if (colorNum == 4) {
                    g.setColor(Color.BLUE);
                } else if (colorNum == 5) {
                    g.setColor(Color.BLACK);
                }

                //上色
                for (int i = 0; i < 18; i++) {
                    g.fillRect(ArrayP[i][0], ArrayP[i][1], a, a);
                }

                //对蓝色技能释放的蓝块进行上色
                g.setColor(Color.BLUE);
                g.fillRect(BX,BY,b,b);
                //对黑色技能释放的黑块进行上色
                g.setColor(Color.BLACK);
                g.fillRect(HX,HY,200,20);

                if (isWin) {
                    g.setColor(Color.RED);
                    g.setFont(new Font("宋体", Font.BOLD, 400));

                    // 获取文字尺寸用于居中
                    FontMetrics fm = g.getFontMetrics();
                    String text = "胜利";
                    int x = (getWidth() - fm.stringWidth(text)) / 2;
                    int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();

                    g.drawString(text, x, y);
                }
            }
        };
        jPanel.setBounds(0, 0, 1800, 1080);
        add(jPanel);
    }


    //判断脚有没有踩到地板
    private boolean checkCollisionDown() {
        Rectangle PFeet = new Rectangle(PX, PY + 4 * a, 5 * a, 1);
        int p = 0;
        for (int[] l : landsA) {
            Rectangle land = new Rectangle(l[0], l[1], l[2], l[3]);
            if (PFeet.intersects(land)) {
                bottomY = land.y;//得到所踩地板的Y坐标
                p++;
                break;
            }
        }
        for (int[] l : landsB) {
            Rectangle land = new Rectangle(l[0], l[1], l[2], l[3]);
            if (PFeet.intersects(land)) {
                bottomY = land.y;//得到所踩地板的Y坐标
                p++;
                break;
            }
        }
        if(PY>=HY-4*a && PY<=HY-4*a+10 && PX>=HX && PX<=HX+200){
            int velocityX = velocityHX;
            PX+=velocityX;
            p++;
        }
        if (p != 0) {
            return true;
        }
        return false;
    }


    //判断头有没有撞到天花板
    private boolean checkTopUp() {
        Rectangle PHead = new Rectangle(PX, PY, 5 * a, 1);
        int p = 0;
        for (int[] l : landsA) {
            Rectangle land = new Rectangle(l[0], l[1], l[2], l[3]);
            if (PHead.intersects(land)) {
                topY = land.y;//得到所碰天花板的Y坐标
                p++;
                break;
            }
        }
        for (int[] l : landsB) {
            Rectangle land = new Rectangle(l[0], l[1], l[2], l[3]);
            if (PHead.intersects(land)) {
                topY = land.y;//得到所碰天花板的Y坐标
                p++;
                break;
            }
        }
        if (p != 0) {
            return true;
        }
        return false;
    }


    //判断有没有撞到侧边
    private int checkRightOrLeft() {
        Rectangle PRight = new Rectangle(PX+5*a, PY, 1, 4 * a);
        Rectangle PLeft = new Rectangle(PX, PY, 1, 4 * a);
        int type = 0;
        for (int[] l : landsA) {
            Rectangle land = new Rectangle(l[0], l[1], l[2], l[3]);
            if (PRight.intersects(land)) {
                rightX = land.x;
                type = 1;
                break;
            }
        }
        for (int[] l : landsB) {
            Rectangle land = new Rectangle(l[0], l[1], l[2], l[3]);
            if (PRight.intersects(land)) {
                rightX = land.x;
                type = 1;
                break;
            }
        }
        for (int[] l : landsA) {
            Rectangle land = new Rectangle(l[0], l[1], l[2], l[3]);
            if (PLeft.intersects(land)) {
                leftX = land.x + l[2];
                type = 2;
                break;
            }
        }
        for (int[] l : landsB) {
            Rectangle land = new Rectangle(l[0], l[1], l[2], l[3]);
            if (PLeft.intersects(land)) {
                leftX = land.x + l[2];
                type = 2;
                break;
            }
        }
        return type;
    }


    //判断脚有没有踩到蓝块(角色)
    private boolean checkCollisionDownBlue() {
        Rectangle PFeet = new Rectangle(PX, PY + 4 * a, 5 * a, 1);
        int p = 0;
        for (int[] l : landsC) {
            Rectangle land = new Rectangle(l[0], l[1], l[2], l[3]);
            if (PFeet.intersects(land)) {
                bottomY = land.y;//得到所踩蓝块的Y坐标
                p++;
                break;
            }
        }
        if (p != 0) {
            return true;
        }
        return false;
    }


    //判断头有没有撞到蓝块
    private boolean checkTopUpBlue() {
        Rectangle PHead = new Rectangle(PX, PY, 5 * a, 1);
        int p = 0;
        for (int[] l : landsC) {
            Rectangle land = new Rectangle(l[0], l[1], l[2], l[3]);
            if (PHead.intersects(land)) {
                topY = land.y;//得到所碰蓝块的Y坐标
                p++;
                break;
            }
        }
        if (p != 0) {
            return true;
        }
        return false;
    }


    //判断临时果子有没有碰到地板
    private boolean checkCollisionDownFruit() {
        int p = 0;
        Rectangle FFeet = new Rectangle(FX,FY+25, 25, 1);
        for (int[] l : landsA) {
            Rectangle land = new Rectangle(l[0], l[1], l[2], l[3]);
            if (FFeet.intersects(land)) {
                bottomFY = land.y;//得到所踩地板的Y坐标
                p++;
                break;
            }
        }
        for (int[] l : landsB) {
            Rectangle land = new Rectangle(l[0], l[1], l[2], l[3]);
            if (FFeet.intersects(land)) {
                bottomFY = land.y;//得到所踩地板的Y坐标
                p++;
                break;
            }
        }
        if (p != 0) {
            velocityFX=0;
            velocityFY=0;
            return true;
        }
        return false;
    }


    private void eat(){
        for(int i=0;i<fruit.size();i++){
            if((fruit.get(i)[0]+12)>=PX && (fruit.get(i)[0]+12)<=PX+6*a && (fruit.get(i)[1]+12)>=PY && (fruit.get(i)[1]+12)<=PY+6*a && colorNum==0){
                colorNum=fruit.get(i)[4];
                if(colorNum==5){
                    HX=1800;
                    HY=1080;
                }
                fruit.remove(i);
                break;
            }
        }
    }

//    private boolean checkFruitIsSame(int FX,int FY,int colorFNum){
//        for(int i=0;i<fruit.size();i++){
//            if(FX==fruit.get(i)[0] && FY==fruit.get(i)[1] && colorFNum==fruit.get(i)[4]){
//                return true;
//            }
//        }
//        return false;
//    }

    private boolean checkFruitIsBorder(){
        if(FX<=0 || FX>=1698-25){
            return true;
        }
        return false;
    }

    private int checkPIsBorder(){
        if(PX<=0){
            return 2;
        }
        if(PX>=1705-4*a){
            return 1;
        }
        return 0;
    }

    //判断蓝色技能释放的蓝块是否撞到墙或边界
    private int checkB(){
        int type = 0;
        Rectangle BRight = new Rectangle(BX+b, BY, 1, b);
        Rectangle BLeft = new Rectangle(BX, BY, 1, b);
        if(velocityBX>0){
            for (int[] l : landsA) {
                Rectangle land = new Rectangle(l[0], l[1], l[2], l[3]);
                if (BRight.intersects(land)) {
                    rightBX=land.x;
                    type=1;
                    break;
                }
            }
            for (int[] l : landsB) {
                Rectangle land = new Rectangle(l[0], l[1], l[2], l[3]);
                if (BRight.intersects(land)) {
                    rightBX=land.x;
                    type=1;
                    break;
                }
            }
            for (int[] l : landsE) {
                Rectangle land = new Rectangle(l[0], l[1], l[2]+30, l[3]);
                if (BRight.intersects(land)) {
                    rightBX=land.x;
                    type=1;
                    break;
                }
            }
            if(BX>=1700){
                rightBX=1700;
                type=1;
            }
        }
        if(velocityBX<0){
            for (int[] l : landsA) {
                Rectangle land = new Rectangle(l[0], l[1], l[2], l[3]);
                if (BLeft.intersects(land)) {
                    leftBX=land.x;
                    type=2;
                    break;
                }
            }
            for (int[] l : landsB) {
                Rectangle land = new Rectangle(l[0], l[1], l[2], l[3]);
                if (BLeft.intersects(land)) {
                    leftBX=land.x;
                    type=2;
                    break;
                }
            }
            for (int[] l : landsE) {
                Rectangle land = new Rectangle(l[0], l[1], l[2]-30, l[3]);
                if (BRight.intersects(land)) {
                    rightBX=land.x;
                    type=2;
                    break;
                }
            }
            if(BX<=0){
                leftBX=0;
                type=2;
            }
        }
        return type;
    }


    //判断黑色技能释放的黑块是否撞到墙或边界
    private boolean checkH(){
        int p=0;
        Rectangle HRight = new Rectangle(HX+200, HY, 1, 20);
        Rectangle HLeft = new Rectangle(HX, HY, 1, 20);
        if(velocityHX>0){
            for (int[] l : landsA) {
                Rectangle land = new Rectangle(l[0], l[1], l[2], l[3]);
                if (HRight.intersects(land)) {
                    p++;
                    break;
                }
            }
            for (int[] l : landsB) {
                Rectangle land = new Rectangle(l[0], l[1], l[2], l[3]);
                if (HRight.intersects(land)) {
                    p++;
                    break;
                }
            }
            if(HX>=1600){
                p++;
            }
        }
        if(velocityHX<0){
            for (int[] l : landsA) {
                Rectangle land = new Rectangle(l[0], l[1], l[2], l[3]);
                if (HLeft.intersects(land)) {
                    p++;
                    break;
                }
            }
            for (int[] l : landsB) {
                Rectangle land = new Rectangle(l[0], l[1], l[2], l[3]);
                if (HLeft.intersects(land)) {
                    p++;
                    break;
                }
            }
            if(HX<=0){
                p++;
            }
        }
        if(p!=0){
            return true;
        }
        return false;
    }


    private void initJFrame() {
        //设置宽高
        setSize(1800,1080);
        //设置标题
        setTitle("帅气的男生制作的游戏");
        //设置界面居中
        this.setLocationRelativeTo(null);
        //设置关闭模式
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        //取消默认的居中放置
        this.setLayout(null);
        //聚焦
        setFocusable(true);
        //加入键盘监听事件
        this.addKeyListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //判断胜利
        if(PX>=1650 && PX<=1700 && PY>=90-5*a && PY<=90){
            isWin=true;
        }
        //处理移动
        if(checkPIsBorder()==1){
            if(colorNum==1){
                if (keys[KeyEvent.VK_D]) PX+=0;
                if (keys[KeyEvent.VK_A]) PX-=20;
                if(checkRightOrLeft()==1){
                    PX=rightX-4*a;
                } else if (checkRightOrLeft()==2) {
                    PX=leftX;
                }
            }else {
                if (keys[KeyEvent.VK_D]) PX+=0;
                if (keys[KeyEvent.VK_A]) PX-=10;
                if(checkRightOrLeft()==1){
                    PX=rightX-4*a;
                } else if (checkRightOrLeft()==2) {
                    PX=leftX;
                }
            }
        } else if (checkPIsBorder()==2) {
            if(colorNum==1){
                if (keys[KeyEvent.VK_D]) PX+=20;
                if (keys[KeyEvent.VK_A]) PX-=0;
                if(checkRightOrLeft()==1){
                    PX=rightX-4*a;
                } else if (checkRightOrLeft()==2) {
                    PX=leftX;
                }
            }else {
                if (keys[KeyEvent.VK_D]) PX+=10;
                if (keys[KeyEvent.VK_A]) PX-=0;
                if(checkRightOrLeft()==1){
                    PX=rightX-4*a;
                } else if (checkRightOrLeft()==2) {
                    PX=leftX;
                }
            }
        }else{
            if(colorNum==1){
                if (keys[KeyEvent.VK_D]) PX+=20;
                if (keys[KeyEvent.VK_A]) PX-=20;
                if(checkRightOrLeft()==1){
                    PX=rightX-5*a;
                } else if (checkRightOrLeft()==2) {
                    PX=leftX;
                }
            }else {
                if (keys[KeyEvent.VK_D]) PX+=10;
                if (keys[KeyEvent.VK_A]) PX-=10;
                if(checkRightOrLeft()==1){
                    PX=rightX-5*a;
                } else if (checkRightOrLeft()==2) {
                    PX=leftX;
                }
            }
        }

        //跳跃物理
        if (isJumping) {
            PY-=velocityY;
            velocityY-=gravity;
            if (checkCollisionDown()) {
                isJumping=false;
                PY=bottomY-4*a;//踩地直接把坐标变为地板Y坐标
                velocityY=0;
            }
            if(checkTopUp()){
                PY=topY+20;//碰头直接把坐标变为天花板Y坐标
                velocityY=-3;
            }
            //如果角色是浅蓝色
            if(colorNum==2){
                if(checkCollisionDownBlue()){
                    isJumping=false;
                    PY=bottomY-4*a;
                    velocityY=0;
                }
                if(checkTopUpBlue()){
                    PY=topY-4*a;
                }
            }
        }

        //重力系统
        if (!isJumping && !checkCollisionDown()) {
            PY+=gravity*g;
        }
        if(!isJumping && checkCollisionDown()){
            PY=bottomY-4*a;
        }
        //如果角色是浅蓝色
        if(colorNum==2){
            if(!isJumping && checkCollisionDownBlue()){
                PY=bottomY-4*a;
            }
        }

        //丢果子行为
        if(direction==1 && isQ && colorNum!=0){
            FX=PX+5*a;
            FY=PY+a;
            colorFNum=colorNum;
            colorNum=0;
            velocityFX=14;
            velocityFY=1;
            gravityF=2;
        } else if (direction==2 && isQ && colorNum!=0) {
            FX=PX-2*a;
            FY=PY+a;
            colorFNum=colorNum;
            colorNum=0;
            velocityFX=-14;
            velocityFY=1;
            gravityF=2;
        }
        isQ=false;
        if(checkFruitIsBorder()){
            velocityFX=-velocityFX;
            velocityFY+=3;
        }
        FX+=velocityFX;
        FY+=velocityFY;
        velocityFY+=gravityF;
        if(FX>=300 && FX<=375 && FY>=1000){
            FY=825;
            FX=337;
        } else if (FX>=1300 && FX<=1375 && FY>1000) {
            FY=825;
            FX=1337;
        }
        if(FX>=700 && FX<=1000-25 && FY>=1000) {
            Random random = new Random();
            int colorFNumR = 0;
            if(colorFNum==1){
                c1++;
            } else if (colorFNum==2){
                c2++;
            } else if (colorFNum==4) {
                c4++;
            } else if (colorFNum==3) {
                c3++;
            }
            if(c1==1 && c3==1){
                colorFNumR=2;
                c1=0;
                c3=0;
            }
            if(c1==1 && c2==1){
                c1=0;
                c2=0;
            }
            if(c1==1 && c4==1){
                c1=0;
                c4=0;
            }
            if(c2==2){
                colorFNumR=4;
                c2=0;
            }
            if(c2==1 && c3==1){
                c2=0;
                c3=0;
            }
            if(c2==1 && c4==1){
                c2=0;
                c4=0;
            }
            if(c3==1 && c4==1){
                c3=0;
                c4=0;
            }
            colorFNum=colorFNumR;
            FX=random.nextInt(1200)+250;
            FY=0;
            velocityFX=0;
        }


        //每帧更新前清理旧位置
        fruit.removeIf(f -> Arrays.equals(f, currentThrownFruit));
        //丢果子时初始化
        currentThrownFruit = new int[]{FX, FY, 25, 25, colorFNum};
        //更新坐标并重新添加
        fruit.add(currentThrownFruit);

        //落地时转为永久果子
        if(checkCollisionDownFruit()){
            fruit.removeIf(f -> Arrays.equals(f, currentThrownFruit));
            fruit.add(new int[]{FX, bottomFY-25, 25, 25, colorFNum});
            FX=2001;
            FY=0;
            velocityFY=0;
            gravityF=0;
            currentThrownFruit = null; //停止跟踪
        }

//        //删除上一个临时果子
//        fruit.removeIf(f -> f[0] == FX-velocityFX&& f[1] == FY-velocityFY+gravityF);
//        //将临时果子添加到果子集合中
//        fruit.add(new int[]{FX,FY,25,25,colorFNum});
//        //临时果子落地后
//        if(checkCollisionDownFruit()){
//            //删除上一个临时果子
//            fruit.removeIf(f -> f[0] == FX&& f[1] == FY);
//            FY=bottomFY-25;
//            fruit.add(new int[]{FX,FY,25,25,colorFNum});
//        }

        //F键技能使用
        if(isFB){
            if(colorNum==4 && checkh){
                if(direction==1){
                    colorNum=0;
                    velocityBX=30;
                    BY=PY+a;
                    BX=PX+4*a;
                    checkh=false;
                }
                if(direction==2){
                    colorNum=0;
                    velocityBX=-30;
                    BY=PY+a;
                    BX=PX-b;
                    checkh=false;
                }
            }
            if (checkB()==1){
                PX=rightBX-5*a;
                PY=BY-a;
                isFB=false;
                checkh=true;
            } else if (checkB()==2) {
                PX=leftBX;
                PY=BY-a;
                isFB=false;
                checkh=true;
            }
        }
        BX+=velocityBX;
        if(isFH){
            if(colorNum==5){
                if(direction==1){
                    colorNum=0;
                    HX=PX+5*a-20;
                    HY=PY+4*a;
                    velocityHX=12;
                    isFH=false;
                }
                if(direction==2){
                    colorNum=0;
                    HX=PX-220;
                    HY=PY+4*a;
                    velocityHX=-12;
                    isFH=false;
                }
            }
        }
        HX+=velocityHX;
        if(checkH()) {
            velocityHX = -velocityHX;
        }


        eat();
        createP();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(isWin){
            return;
        }
        keys[e.getKeyCode()] = true;
        //按下D，角色朝右    按下A，角色朝左
        if(e.getKeyCode()==KeyEvent.VK_D){
            direction=1;
        } else if (e.getKeyCode()==KeyEvent.VK_A) {
            direction=2;
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE && !isJumping) {
            isJumping = true;
            if(colorNum==3 && !((PX>=775 && PX<=925 && PY>=700 && PY<=850)||(PX>=0 && PX<=1600 && PY>=300 && PY<=550))){
                velocityY = 25;
            } else if (colorNum==3 && (PX>=775 && PX<=925 && PY>=700 && PY<=850)||(PX>=0 && PX<=1600 && PY>=300 && PY<=550)) {
                velocityY=20;
            }
            if(colorNum!=3){
                velocityY = 20;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_Q){
            isQ=true;
        }
        if(e.getKeyCode()==KeyEvent.VK_F){
            if(colorNum==4){
                isFB=true;
            } else if (colorNum==5) {
                isFH=true;
            }
        }
        keys[e.getKeyCode()] = false;
    }
}

