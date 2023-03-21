package gui;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Pink starfish
 * @date 2023/2/26
 * @apiNote
 *      getWindow 检查窗口集合中是否有所需窗口
 *                  如果没有：创建窗口线程，存入集合，并返回ChatThread对象
 *                  如果有  ：显示窗口、返回ChatThread对象
 * 该类用于管理窗口，使用ConcurrentHashMap更加安全
 **/
public class ManageWindows {


    /**管理窗口的map*/
    private static ConcurrentHashMap<String, ChatThread> threadHashMap =new ConcurrentHashMap<>();
    /**显示窗口的类*/
    public static ChatThread getWindow(String userId,String getter){
        if (threadHashMap.get(getter)==null) {
            ChatThread chatThread = new ChatThread(userId,getter);
            threadHashMap.put(getter,chatThread);
            new Thread(chatThread).start();
            return chatThread;
        }else {
            ChatThread chatThread = threadHashMap.get(getter);
            chatThread.getJFrame().setVisible(true);
            return chatThread;

        }
    }
}
