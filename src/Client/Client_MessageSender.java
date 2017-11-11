package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;


public class Client_MessageSender extends Thread{
    private ObjectOutputStream out;
    private Client c;

    public Client_MessageSender(ObjectOutputStream out,Client client) {
        this.out = out;
        this.c=client;
    }

    @Override
    public void run() {
        Scanner keyboard = new Scanner(System.in);
        try {
            while (!interrupted()) {
                String str = keyboard.nextLine();
                if(str.equals("Fim")){
                    c.stop();
                }
                out.writeObject(str);
            };
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
