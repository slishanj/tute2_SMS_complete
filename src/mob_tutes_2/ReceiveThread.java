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

    ReceiveThread(MySMSMidlet aThis, boolean run) {
        this.midlet = aThis;
        this.keepRunning = run;
    }

    public void run() {
        try {
            MessageConnection mc = (MessageConnection) Connector.open("sms://:1234");
            Message msg;
            MessageConnection mc2;
            while (keepRunning) {
                msg = mc.receive();

                if (msg instanceof TextMessage) {
                    TextMessage tmsg = (TextMessage) msg;
                    System.out.println("TEXT");
                    Alert a = new Alert("this goes message here.");
                    a.setString(tmsg.getPayloadText() + " from " + msg.getAddress());
                    a.setType(AlertType.ALARM);
                    a.setTimeout(Alert.FOREVER);
                    midlet.display.setCurrent(a);
//delivery reporting
                    if (tmsg.getPayloadText().startsWith("Message")) {
                        // if its a delivery report, ignore!
                        //because when a delivery report is sent, that also,is taken as a msg,
                        // so if its a delivery report, avoid sending a delivery report,
                        //for the received delivery report msg
                        System.out.println("NewTEXTDelivery");
                    } else {
                        mc2 = (MessageConnection) Connector.open(msg.getAddress().substring(0, 15) + ":1234");
                        TextMessage delRep = (TextMessage) mc2.newMessage(mc2.TEXT_MESSAGE);
                        System.out.println("address set");
                        delRep.setPayloadText("Message Delivered!");
                        // the recipient address in the message is already correct as we are reusing the same object
                        mc2.send(delRep);
                        System.out.println("sent");
                        mc2.close();
                    }
                } else if (msg instanceof BinaryMessage) {
                    BinaryMessage tmsg = (BinaryMessage) msg;
                    System.out.println("BINARY");
                    Alert a = new Alert("New Message Received");
                    a.setString(new String(tmsg.getPayloadData()) + " from " + msg.getAddress());
                    a.setType(AlertType.ALARM);
                    a.setTimeout(Alert.FOREVER);
                    midlet.display.setCurrent(a);
//delivery reporting
                    if (new String(tmsg.getPayloadData()).startsWith("Message")) {
                        // if its a delivery report, ignore!
                        //because when a delivery report is sent, that also,is taken as a msg,
                        // so if its a delivery report, avoid sending a delivery report,
                        //for the received delivery report msg
                        System.out.println("NewBINDelivery");
                    } else {
                        mc2 = (MessageConnection) Connector.open(msg.getAddress().substring(0, 15) + ":1234");
                        BinaryMessage delRep = (BinaryMessage) mc2.newMessage(mc2.BINARY_MESSAGE);
                        System.out.println("address set");
                        delRep.setPayloadData("Message Delivered!".getBytes());
                        // the recipient address in the message is already correct as we are reusing the same object
                        mc2.send(delRep);
                        System.out.println("sent");
                        mc2.close();
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }

    }
}