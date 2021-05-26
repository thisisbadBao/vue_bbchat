package com.bbchat.websocket;

import com.bbchat.dao.entity.Account;
import com.bbchat.dao.entity.Message;
import com.bbchat.dao.entity.Room;
import com.bbchat.service.AccountService;
import com.bbchat.service.MessageService;
import com.bbchat.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author thisisbadBao
 * @Date 2021--24-4:18 PM
 */
@ServerEndpoint("/websocket/{msg_source}/{nickname}")
@Component
public class MyWebSocket {
    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<MyWebSocket>();

    private static Map<String,String> users = new HashMap<String,String>();

    private Session session;

    private String nickname;

    private String msg_source;

    private static AccountService accountService;

    private static MessageService messageService;

    private static RoomService roomService;

    @Autowired
    public void setApplicationContext(AccountService accountService,MessageService messageService,RoomService roomService){
        MyWebSocket.accountService = accountService;
        MyWebSocket.messageService = messageService;
        MyWebSocket.roomService = roomService;
    }


    /**
     * 有新用户连接
     * @param session
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("nickname") String nickname, @PathParam("msg_source") String msg_source){
        this.session = session;
        this.nickname = nickname;
        this.msg_source = msg_source;

        Iterator<String> iterator ;
//                = users.keySet().iterator();

//        while(iterator.hasNext()){
//            String name = iterator.next();
//            if( nickname.equals( name ) && msg_source.equals( users.get(name) ) ){
//                System.out.println("发现重复连接同意聊天室");
//                this.session.getAsyncRemote().sendText("10001/"+nickname);//当前用户已连接入当前聊天室,不可重新连接！
//                //this.session.getAsyncRemote().sendObject(new UserResult(101, nickname));
//                return;
//            }
//        }

        webSocketSet.add(this);
        users.put(nickname,msg_source);
        Room room = new Room();
        room.setName(nickname);
        room.setRoomid(msg_source);
        roomService.insert(room);

        System.out.println("欢迎"+nickname+"加入聊天室"+msg_source+"!");
        broadcast("10002/"+nickname);//("恭喜你成功连接上webSocket-->当前人数"+webSocketSet.size());

        iterator = users.keySet().iterator();
        while(iterator.hasNext()){
            String name = iterator.next();
            //打印所有登录聊天室的用户的信息
            System.out.println(name);
            System.out.println(users.get(name));
        }

//        for(int i = 0;i < names.length; i ++){
//            if( msg_source.equals( users.get(names[i]) ) ){
                //this.session.getAsyncRemote().sendText("10004/"+"names[i]");
//            }
//        }
        String userMessage = "10004/";
        String[] userOfRoom = roomService.getUserOfRoom(msg_source);
        for(int i=0;i<userOfRoom.length;i++){
            System.out.println(userOfRoom[i]);
            //this.session.getAsyncRemote().sendText("10004/"+userOfRoom[i]);
            userMessage += userOfRoom[i];//整合消息格式为“10004/xxxx-xxxx……“
            if(i!=userOfRoom.length-1){
                userMessage += "-";
            }
        }

        this.session.getAsyncRemote().sendText(userMessage);
    }




    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
        webSocketSet.remove(this);
        users.remove(nickname,msg_source);

        Room room = new Room();
        room.setRoomid(msg_source);
        room.setName(nickname);
        roomService.remove(room);

        System.out.println("用户"+nickname+"退出！");

        Iterator<String> iterator = users.keySet().iterator();
        while(iterator.hasNext()){
            String name = iterator.next();
            //打印所有聊天室的用户的信息
            System.out.println(name);
            System.out.println(users.get(name));
        }
        //broadcast("有一用户退出！当前在线人数为"+webSocketSet.size());
        broadcast("10003/"+nickname);  //用户退出该房间
    }

    /**
     *收到客户端的消息后调用的方法
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("nickname") String nickname, @PathParam("msg_source") String msg_source){
        //判断当前用户是否已被禁言
        Account account = accountService.getUser(nickname);
        if(!account.getAble()){
            this.session.getAsyncRemote().sendText("10007/你已被禁言，无法发送消息");
            return;
        }

        System.out.println("来自客户端的消息-->"+nickname+":"+message);
        //群发消息
        //broadcast(nickname+":"+message);
        String avatar = accountService.getAvatarOfUser(nickname);
        Date current = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        String time = simpleDateFormat.format(current);
        if(msg_source.equals("1")){//格式为"10009(10010)/name:message-xxxx.xx.xx xx:xx:xx
            broadcast("10009$"+nickname+": "+message+"-"+time+"-"+avatar);  //头像也用 -  分隔
        }else{
            broadcast("10010$"+nickname+": "+message+"-"+time+"-"+avatar);
        }

        //将用户发送的消息存入数据库，方便之后读出
        Message message1 = new Message();
        message1.setContext(message);
        message1.setTime(new Date());
        message1.setMsg_source(msg_source);
        message1.setName(nickname);
        messageService.insertMessage(message1);

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
            if(item.msg_source.equals(this.msg_source))
                item.session.getAsyncRemote().sendText(message);
        }
    }

}
