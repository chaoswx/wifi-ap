package com.everyoo.chaos.multibroadcast;

import android.content.Context;
import android.net.wifi.WifiManager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Created by chaos on 2016/3/25.
 */
public class BroadcastUtils {

    private Context mContext;
    public BroadcastUtils(Context context) {
        mContext = context;
    }

    /**
     * 发送广播并接收指令
     */
    public void sendAndReceiveMessage(){
        getBroadLock();
        try {
            System.out.println("begin create multisocket ");
            DatagramSocket datagramSocket = new DatagramSocket(9999);
            byte[] message = "Hi ,I am client,nice to chat with you!".getBytes();
            InetAddress inetAddress = InetAddress.getByName("255.255.255.255");
            DatagramPacket datagramPacket  = new DatagramPacket(message,message.length,inetAddress,9999);
         //   System.out.println("interface = " + NetworkInterface.getByName("wlan0"));
        //    multicastSocket.setNetworkInterface(NetworkInterface.getByName("wlan0"));
            System.out.println("ready send message to host");
            datagramSocket.send(datagramPacket);
            datagramSocket.setSoTimeout(5 * 60 * 1000);

            System.out.println("send message to host");
            while (true) {
                System.out.println("ready to receive message from host");
                byte[] receiveMessage = new byte[1024];
                datagramPacket = new DatagramPacket(receiveMessage,receiveMessage.length) ;
                datagramSocket.receive(datagramPacket);
                System.out.println("received message from host");
                String content = new String(receiveMessage,0,receiveMessage.length);
                System.out.println("content = "+content);
               // break;
            }
        //    datagramSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 接收广播并发送指令
     */
    public void receiveAndSendMessage(){
        getBroadLock();
        try {
            System.out.println("begin create multisocket");
            byte[] buf = new byte[1024];
            DatagramSocket datagramSocket = new DatagramSocket(9999);
            InetAddress inetAddress = InetAddress.getByName("255.255.255.255");
            DatagramPacket datagramPacket = new DatagramPacket(buf,buf.length);
            System.out.println("join broadcast group");
            datagramSocket.setSoTimeout(5 * 60 * 1000);
            while (true) {
                System.out.println("ready to received message from client");
                datagramSocket.receive(datagramPacket);
                String message = new String(buf,0,buf.length);
                System.out.println("received message from client and message = "+message);
                byte[] sendMessage = "Hi, I am host,nice to chat with you!".getBytes();
                 datagramPacket = new DatagramPacket(sendMessage,sendMessage.length,datagramPacket.getAddress(),9999);
                System.out.println("ready to send ack to client and client ip = "+datagramPacket.getAddress()+"port = "+datagramPacket.getPort());
                datagramSocket.send(datagramPacket);
                System.out.println("sent ack to client");
                break;
            }
            datagramSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void getBroadLock(){
        System.out.println("get broad lock");
        WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        WifiManager.WifiLock wifiLock = wifiManager.createWifiLock("GET LOCK");
        wifiLock.acquire();
    }
}
