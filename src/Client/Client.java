package Client;

import Server.SimpleServer;

import java.io.*;
import java.net.*;

public class Client {
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket socket;
    private Client_MessageReceiver messageReceiver;
    private Client_MessageSender messageSender;
    private boolean running=true;

    public static void main(String[] args) {
        Client c = new Client();
        c.runClient();


    }

    private void runClient() {
        try {
            connectToServer();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            startThreads();
            run();
            System.out.println("A Fechar...");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }



    private void connectToServer() throws IOException {
        try {
            InetAddress adress = InetAddress.getByName(null);
            System.out.println("Endereco=" + adress);
            socket = new Socket(adress, SimpleServer.PORT);
            System.out.println(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            System.out.println("Ligado a " + socket);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private void startThreads(){
        messageReceiver = new Client_MessageReceiver(in);
        messageSender=new Client_MessageSender(out,this);
        messageReceiver.start();
        messageSender.start();
    }
    private void interruptThreads() {
        System.out.println("threads interrupted");
        try {
            in.close();
            out.close();
        } catch (IOException e) {
        }
        messageReceiver.interrupt();
        messageSender.interrupt();
    }
    private void run(){
        while(running) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        interruptThreads();
    }
    public void stop(){
        System.out.println("stop called");
        running=false;
    }
}