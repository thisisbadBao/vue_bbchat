package com.bbchat.websocket;

import com.bbchat.dao.mapper.AccountMapper;
import com.bbchat.dao.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
<<<<<<< HEAD

=======
>>>>>>> parent of c0d8852 (asd)
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author thisisbadBao
 * @Date 2021--24-4:18 PM
 */
@ServerEndpoint("/websocket/{nickname}")
@Component
public class MyWebSocket {
    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<MyWebSocket>();

    private static ArrayList<String> users = new ArrayList<String>();

    private Session session;

    private String nickname;

    //用于判断该用户是否又权限发消息
    @Autowired
    private AccountMapper accountMapper;

    //用于存入已发出的消息
    @Autowired
    private MessageMapper messageMapper;

    /**
     * 有新用户连接
     * @param session
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("nickname") String nickname){
        this.session = session;
        this.nickname = nickname;

        for(int i = 0;i < users.size(); i ++){
            if(nickname.equals( users.get(i))){
                this.session.getAsyncRemote().sendText("当前用户已登录");
                return;
            }
        }

        webSocketSet.add(this);
        users.add(nickname);
        System.out.println("欢迎"+nickname+"加入！当前在线人数为"+webSocketSet.size());
        broadcast("恭喜你成功连接上webSocket-->当前人数"+webSocketSet.size());
    }


    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
        webSocketSet.remove(this);
        users.remove(nickname);
        System.out.println("有一用户退出！当前在线人数为"+webSocketSet.size());
        broadcast("有一用户退出！当前在线人数为"+webSocketSet.size());
    }

    /**
     *收到客户端的消息后调用的方法
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("nickname") String nickname){
        System.out.println("来自客户端的消息-->"+nickname+":"+message);
        //群发消息
        broadcast(nickname+":"+message);
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 发送消息的方法
     * @param message
     */
    public void broadcast(String message){
        for(MyWebSocket item : webSocketSet){

            item.session.getAsyncRemote().sendText(message);
        }
    }

}
