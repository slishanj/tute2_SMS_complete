/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sendORreceive;

import javax.microedition.io.Connector;
import javax.wireless.messaging.BinaryMessage;
import javax.wireless.messaging.MessageConnection;

/**
 *
 * @author Administrator
 */
public class senderThread implements Runnable {

    MySMSSend midlet;

    public senderThread(MySMSSend midlet1) {
        this.midlet = midlet1;
    }

    public void run() {
        try {
            MessageConnection con = (MessageConnection) Connector.open("sms://"+midlet.no.getString()+":1234");
            BinaryMessage tm = (BinaryMessage) con.newMessage(con.BINARY_MESSAGE);
            tm.setPayloadData(midlet.msg.getString().getBytes());
            con.send(tm);
            con.close();
            
        } catch (Exception e) {
            System.out.println(e);
        }
 
    }
}
