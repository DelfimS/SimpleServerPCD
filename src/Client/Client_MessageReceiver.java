package Client;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Client_MessageReceiver extends Thread {
   private ObjectInputStream in;

    public Client_MessageReceiver(ObjectInputStream in) {
        this.in = in;
    }

    @Override
    public void run() {
        try {
            while (!interrupted()) {
                try {
                    String str2 = (String)(in.readObject());
                    System.out.println("STR2: " + str2);
                } catch (EOFException e){
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
