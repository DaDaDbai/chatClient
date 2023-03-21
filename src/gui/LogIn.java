package gui;

import service.UserClintService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * @author Pink starfish
 * @date 2023/2/26
 * @apiNote
 * 这个页面作为登录页面而存在，其主要的作用是读入用户账户与密码，
 * 并传入UserClintService的checkUser方法验证消息的发送
 * 成功：调用Menu
 * 失败：exit
 **/
public class LogIn  extends JFrame{
    private static  String userId;
    private static  String userPw;
    private  UserClintService userClintService=new UserClintService();
    private int i=0;
    public LogIn(){
        JFrame jFrame = new JFrame("缺德聊天登录("+i+"/3)");
        //关闭设置
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //位置设置
        jFrame.setBounds(700,300,600,350);
        jFrame.setLocationRelativeTo(null);
        //固定大小
        jFrame.setResizable(false);
        jFrame.setIconImage(new ImageIcon("src/resources/OIP-C.jpeg").getImage());

        JPanel jPanel = new JPanel();
        jPanel.setBounds(0,0,600,350);
        jPanel.setLayout(null);

        JLabel userLabel = new JLabel("用户名:");
        userLabel.setBounds(60,50,100,50);
        JTextField userTextField = new JTextField(24);
        userTextField.setBounds(130,50,350,50);

        JLabel pwLabel = new JLabel("密码:");
        pwLabel.setBounds(60,120,100,50);
        JPasswordField pwField = new JPasswordField(24);
        pwField.setBounds(130,120,350,50);

        JButton logIn = new JButton("登录");
        logIn.setBounds(150,200,120,50);

        JButton reset = new JButton("重置");
        reset.setBounds(320,200,120,50);

//字体设置
        Font font = new Font("黑体", Font.PLAIN, 20);
        Font font1 = new Font("楷体", Font.PLAIN, 20);
        userTextField.setFont(font);
        pwField.setFont(font);
//设置字体颜色
        pwField.setForeground(Color.RED);
// 设置密码框默认显示的密码字符
        pwField.setEchoChar('*');
        userLabel.setFont(font1);
        pwLabel.setFont(font1);
        logIn.setFont(font1);
        reset.setFont(font1);


        /**登录按钮onclick事件设置*/
        logIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userId = userTextField.getText();
                userPw =new String(pwField.getPassword());
                boolean checkUser = userClintService.checkUser(userId, userPw);
                if (i==2){
                    System.exit(0);
//                    退出
                }else if (checkUser){
                    jFrame.setVisible(false);
                    new Menu(userId);
                }else {
                    i++;
                    jFrame.setTitle("缺德聊天登录("+i+"/3)");
                }
            }
        });
        /**重置按钮监听*/
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userTextField.setText("");
                pwField.setText("");
            }
        });


//        添加到面板
        jPanel.add(userLabel);
        jPanel.add(userTextField);
        jPanel.add(pwLabel);
        jPanel.add(pwField);
        jPanel.add(logIn);
        jPanel.add(reset);

        jFrame.add(jPanel);
        jFrame.setVisible(true);


    }


}
