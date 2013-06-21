/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mob_tutes_2;

import javax.microedition.io.Connector;
import javax.wireless.messaging.BinaryMessage;
import javax.wireless.messaging.MessageConnection;

/**
 *
 * @author Administrator
 */
public class senderThread implements Runnable {

    MySMSMidlet midlet;

    public senderThread(MySMSMidlet midlet1) {
        this.midlet = midlet1;
    }

    public  void run() {
        try {
            
            MessageConnection con2 = (MessageConnection) Connector.open("sms://"+midlet.no.getString()+":1234");
            BinaryMessage tm = (BinaryMessage) con2.newMessage(con2.BINARY_MESSAGE);
            tm.setPayloadData(midlet.msg.getString().getBytes());
            con2.send(tm);
            con2.close();
          
        } catch (Exception e) {
            System.out.println(e);
        }
 
    }
    
}
