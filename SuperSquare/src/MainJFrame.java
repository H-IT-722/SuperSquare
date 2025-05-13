import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainJFrame extends JFrame implements ActionListener {

    public MainJFrame(){
        initJFrame();

        initGamePanel();

        createButton();

        setVisible(true);
    }

    private void initJFrame(){
        setSize(800,400);
        setTitle("帅气的男生制作的小游戏");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
    }

    private void initGamePanel() {
        JPanel jPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.setFont(new Font("宋体", Font.BOLD, 40));

                // 获取文字尺寸用于居中
                FontMetrics fm = g.getFontMetrics();
                String text1 = "A D  控制左右移动";
                String text2 = "Space  跳跃";
                String text3 = "Q  丢弃果子";
                String text4 = "F  使用果子技能(只有深蓝、黑色果子有该键技能)";
                g.drawString(text1, 50, 50 );
                g.drawString(text2, 50, 120 );
                g.drawString(text3, 50, 190 );
                g.setFont(new Font("宋体", Font.BOLD, 30));
                g.drawString(text4, 50, 260 );
            }
        };
        jPanel.setBounds(0, 0, 800, 400);
        add(jPanel);
    }

    private void createButton(){
        JButton jButton1=new JButton("我已知晓操作");
        jButton1.setBounds(550,300,200,50);
        jButton1.addActionListener(this);
        add(jButton1);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command=e.getActionCommand();

        switch (command){
            case "我已知晓操作":
                new GameJFrame();
        }
    }
}
