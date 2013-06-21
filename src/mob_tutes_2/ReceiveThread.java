/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mob_tutes_2;

import java.io.IOException;
import javax.microedition.io.Connector;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.wireless.messaging.BinaryMessage;
import javax.wireless.messaging.Message;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;

/**
 *
 * @author Administrator
 */
public class ReceiveThread implements Runnable {

    MySMSMidlet midlet;
    boolean keepRunning;

    ReceiveThread(MySMSMidlet aThis,boolean run) {
        this.midlet = aThis;
        this.keepRunning=run;
    }

    public void run() {
        try {

//            MessageConnection mc = (MessageConnection) Connector.open("sms://:1234");
//            BinaryMessage msg = (BinaryMessage) mc.receive();
//            
//            Alert a = new Alert("this goes message here.");
//            a.setString(new String(msg.getPayloadData()));
//            a.setType(AlertType.ALARM);
//            a.setTimeout(Alert.FOREVER);
//            midlet.display.setCurrent(a);
            MessageConnection mc = (MessageConnection) Connector.open("sms://:1234");
            Message msg;

            while (keepRunning) {
                msg = mc.receive();
                if (msg instanceof TextMessage) {
                    TextMessage tmsg = (TextMessage) msg;
                    Alert a = new Alert("this goes message here.");
                    a.setString(tmsg.getPayloadText());
                    a.setType(AlertType.ALARM);
                    a.setTimeout(Alert.FOREVER);
                    midlet.display.setCurrent(a);
                    tmsg.setPayloadText("Message Delivered!");
                    // the recipient address in the message is already correct as we are reusing the same object
                    mc.send(tmsg);
                } else if (msg instanceof BinaryMessage) {
                } else {
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}