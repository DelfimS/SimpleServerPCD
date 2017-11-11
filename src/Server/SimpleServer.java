package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {
    public static final int PORT=5555;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public static void main(String[] args) {
        try {
            new SimpleServer().startServing();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startServing(){
        ServerSocket s = null;
        try {
            s = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Lancou ServerSocket:"+s);
        try{
            assert s != null;
            try (Socket socket = s.accept()) {
                System.out.println("Conexao aceite:" + socket);
                doConnections(socket);
                serve();
            } finally {
                System.out.println("Fechar...");

            }
        } catch (IOException ignored) {
        } finally{
            try {
                assert s != null;
                s.close();
            } catch (IOException ignored) {
            }
        }
    }

    private void doConnections(Socket socket) {
        try {
            out=new ObjectOutputStream(socket.getOutputStream());
            in=new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void serve() throws IOException {
        while (true) {
            try {
                String readFromClient = (String) (in.readObject());
                if(readFromClient.equals("Fim")){
                    break;
                }
                System.out.println(readFromClient);
                out.writeObject(readFromClient);
                } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}