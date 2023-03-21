package gui;

import pojo.Message;
import service.FileClientService;
import service.MessageClientService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

//这是聊天界面的编写
/**
 * @author Pink starfish
 * @date 2023/2/26
 * @apiNote
 *      getJFrame 返回JFrame对象，利于其他类操作页面的展示
 *      getFileGetterPath 获取接收者文件路径
 *      showMessage 向窗口中添加消息
 *      getFile 文件窗口、文件路径
 * 这是一个专门处理聊天界面的类，
 **/
public class ChatThread extends JFrame implements Runnable{

    public String getFileGetterPath() {
        return fileGetterPath;
    }
    private String userId;
    private String getter;
    private int width=517;
    private int height=450;

    private  JTextPane text=new JTextPane();

    private String fileGetterPath=null;
    private String fileName=null;
    private Font font = new Font("华文行楷", Font.PLAIN, 25);

    private MessageClientService userMessageService =new MessageClientService();
    private FileClientService fileClientService=new FileClientService();
    public ChatThread(String userId,String getter){
    this.userId=userId;
    this.getter=getter;
    }

    private JFrame jFrame=null;
    /**由于我们其他类需要调用jFrame的方法来进行页面的可视
     * 故我们需要准备这个方法*/
    public JFrame getJFrame() {
        return jFrame;
    }

    @Override
    public void run() {
        Font font1 = new Font("华文宋黑", Font.PLAIN, 16);
/*       根据私聊与群聊的不同来创建标题名*/
        if (getter.equals("ALL")){
            jFrame = new JFrame(userId+"的相亲相爱一家人");
        }else {

            jFrame = new JFrame(userId+"与 "+getter+" 的聊天栏");
        }

        //DISPOSE_ON_CLOSE,隐藏并释放窗体
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //位置设置
        jFrame.setBounds(700,300,width,height);
        jFrame.setLocationRelativeTo(null);
        jFrame.setLayout(null);
        jFrame.setResizable(false);
        jFrame.setIconImage(new ImageIcon("src/resources/OIP-C.jpeg").getImage());


        //聊天框以及滚动栏
        JPanel jPanel = new JPanel();
        jPanel.setLayout(null);
        jPanel.setBounds(5,5,490,290);

        text.setFont(font1);
        text.setEditable(false);
        // 使其不可编辑
        jPanel.setLayout(new BorderLayout());
        jPanel.add(new JScrollPane(text),BorderLayout.CENTER);

        //文本输入
        JPanel jPanelIn = new JPanel();
        jPanelIn.setLayout(null);
        jPanelIn.setBounds(5,305,350,90);

        JTextPane textIn=new JTextPane();
        textIn.setFont(font1);
        textIn.setText("这是一个文本域，可以在里面有换行的操作");
        jPanelIn.setLayout(new BorderLayout());
        jPanelIn.add(new JScrollPane(textIn),BorderLayout.CENTER);

        JPanel jPanelOut = new JPanel();
        jPanelOut.setLayout(null);
        jPanelOut.setBounds(360,305,150,90);
        //发文件
        JButton fileButton = new JButton("发文件");
        fileButton.setBounds(0,0,120,40);

        /**发文件的事务*/
        fileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);//可以选择文件与文件夹
                jFileChooser.showOpenDialog(null);
                //打开文件选择
                File selectedFile = jFileChooser.getSelectedFile();
                if (selectedFile!=null){
                    String fileSenderPath=selectedFile.getPath();
                //获取到文件路径
                    fileClientService.sendFile(fileSenderPath,userId,getter);
                }
            }
        });

        jPanelOut.add(fileButton);
        //发消息
        JButton messageButton = new JButton("消息");
        messageButton.setBounds(0,45,120,40);

        //发消息的事件
        messageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //先获取到输入框的消息
                String content = textIn.getText();
                textIn.setText("");
                //文本显示
                if (getter.equals("ALL")){
                    //如果接收对象为所有人，那么就是群聊消息，反之则不是
                    userMessageService.sendMessageToAll(userId,content);
                    //传入发送者以及内容，接下来交给ClientThread，其对服务端返回的数据进行处理
                }else {
                    text.setText(text.getText()+"\n"+"你说："+content);
                    //调用私聊转发服务
                    userMessageService.sendMessageToOneUser(content,userId,getter);
                }
            }
        });

        jPanelOut.add(messageButton);

        jFrame.add(jPanel);
        jFrame.add(jPanelIn);
        jFrame.add(jPanelOut);

        jFrame.setVisible(true);

    }
    /**这个方法用于消息的展示*/
    public void showMessage(Message message){
        text.setText(text.getText()+"\n"+message.getSender()+"说："+message.getContent());
    }
    /**这个方法用于我们是否接收文件，以及文件路径的获取和文件夹与文件名的拼接*/
    public String getFile(String userId ,String src){

        String[] split = src.split("\\\\");
        fileName=split[split.length-1];
        JFrame jFrameFile = new JFrame(userId+"向你发送了文件"+fileName);
        //位置设置
        jFrameFile.setBounds(700,300,350,250);
        jFrameFile.setLocationRelativeTo(null);
        jFrameFile.setLayout(null);
        //该弹窗不允许关闭
        jFrameFile.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JPanel jPanel = new JPanel();
        jPanel.setBounds(0,0,300,180);
        jPanel.setLayout(null);

        JLabel jLabel = new JLabel(userId+"向你发送了文件");
        jLabel.setBounds(50,0,300,100);
        jLabel.setFont(font);

        JButton get = new JButton("接收");
        get.setBounds(72,130,80,30);

        /**接收文件监听*/
        get.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                //可以选择文件与文件夹
                jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                jFileChooser.showOpenDialog(null);
                //打开文件选择
                fileGetterPath = jFileChooser.getSelectedFile().getPath()+"\\"+fileName;
                // 如果选择了路径就代表接收文件，然后隐藏窗口
                jFrameFile.setVisible(false);

            }
        });
        JButton unGet = new JButton("不接受");
        unGet.setBounds(177,130,80,30);
        /**不接收文件监听*/
        unGet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileGetterPath="UN_SUCCESS";
                jFrameFile.setVisible(false);
            }
        });

        jPanel.add(get);
        jPanel.add(unGet);
        jPanel.add(jLabel);
        jFrameFile.add(jPanel);
        jFrameFile.setVisible(true);
        return fileGetterPath;
    }

}
