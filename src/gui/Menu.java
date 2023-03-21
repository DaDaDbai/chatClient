package gui;

import pojo.User;
import service.UserClintService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author Pink starfish
 * @date 2023/2/26
 * @apiNote
 * 这个类作为主菜单而存在，提供给成功登录的用户
 * 这个类提供对不同按钮的监听以实现不同的功能
 * 功能：
 *      私聊
 *      群聊
 *      退出
 **/
public class Menu extends JFrame {
    private static int width=330;
    private static int height=400;
    /**这个是我们从服务器端获取到的好友集合*/
    private static LinkedList<String> friends = UserClintService.getFriends();
        public Menu(String userId){
            Font font = new Font("楷体", Font.PLAIN, 18);
            Font font1 = new Font("华文宋黑", Font.PLAIN, 16);

            JFrame jFrame = new JFrame(userId+"的聊天栏");
            //关闭设置
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //位置设置
            jFrame.setBounds(700,300,width,height);
            jFrame.setLocationRelativeTo(null);
            jFrame.setLayout(null);
            //固定窗口大小
            jFrame.setResizable(false);
            jFrame.setIconImage(new ImageIcon("src/resources/OIP-C.jpeg").getImage());



            JPanel jPanel = new JPanel();
            jPanel.setLayout(null);
            JLabel top = new JLabel();
            top.setBounds(0,0,width,height/4+10);
            top.setIcon(new ImageIcon("src/resources/R-C.jpeg"));
            jFrame.add(top);

//                    这个数据等会再加入
            friends.addFirst("<---请选择你的私聊对象--->");
            String[] user = new String[friends.size()];
            for (int i = 0; i < friends.size(); i++) {
                user[i]=friends.get(i);
            }
//            String[] user={"<---请选择你的私聊对象--->","100","200","300"};
            JLabel userName1 = new JLabel(" 私聊");
            JComboBox jComboBox1 = new JComboBox(user);
            jComboBox1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
//                    获取到要对话的用户名
                    String getter = jComboBox1.getSelectedItem().toString();
                    /* 通过聊天者的身份，创建窗口或者显示窗口*/
                    ManageWindows.getWindow(userId,getter);
                }
            });

            userName1.setFont(font);
            jComboBox1.setFont(font1);
            userName1.setBounds(0,1*(60),width/4,45);
            jComboBox1.setBounds(width/4,1*(60),3*width/4,45);
            jPanel.add(userName1);
            jPanel.add(jComboBox1);

            JLabel userName2 = new JLabel(" 聊天室");
            JButton user2 = new JButton();
            userName2.setFont(font);
            userName2.setBounds(0,2*(60),width/4,45);
            user2.setBounds(width/4,2*(60),3*width/4,45);

            /**      聊天室按钮功能实现，这个事件用来处理群聊*/
            user2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    /*我们使用接收者为“ALL”来指定群聊窗口
                    * 这个“ALL“会被存放在窗口集合中，避免多余窗口的产生*/
                    ManageWindows.getWindow(userId, "ALL");
                }
            });

            jPanel.add(userName2);
            jPanel.add(user2);

            JLabel userName3 = new JLabel(" 退出");
            JButton user3 = new JButton();
            userName3.setFont(font);
            userName3.setBounds(0,3*(60),width/4,45);
            user3.setBounds(width/4,3*(60),3*width/4,45);
            jPanel.add(userName3);
            jPanel.add(user3);

            /**退出按钮的监听*/
            user3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new UserClintService().logOut();
                }
            });

            jPanel.setBounds(0,height/5,width,4*height/5);
            jFrame.add(jPanel);

            /**这是一个关闭监听.如果我们下线，那么我们必须向服务器发送下线消息，
                以便于服务器将该用户的线程从管理集合中删除*/
            jFrame.addWindowListener(new WindowAdapter(){
                @Override
                public void windowClosing(WindowEvent e) {
//                    调用logOut方法发送退出消息
                    new UserClintService().logOut();
                }
            });

            jFrame.setVisible(true);

        }

}
