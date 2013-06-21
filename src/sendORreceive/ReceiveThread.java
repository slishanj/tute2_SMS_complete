/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sendORreceive;

import java.io.IOException;
import javax.microedition.io.Connector;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.wireless.messaging.BinaryMessage;
import javax.wireless.messaging.MessageConnection;

/**
 *
 * @author Administrator
 */
public class ReceiveThread implements Runnable {

    MySMSReceive2 midlet;

    ReceiveThread(MySMSReceive2 aThis) {
        this.midlet = aThis;
    }

    public void run() {
        try {
            MessageConnection mc = (MessageConnection) Connector.open("sms://:1234");
            BinaryMessage msg = (BinaryMessage) mc.receive();
            Alert a = new Alert("this goes message here.");
            a.setString(new String(msg.getPayloadData()));
            a.setType(AlertType.ALARM);
            a.setTimeout(Alert.FOREVER);
            midlet.display.setCurrent(a);
            mc.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
               
    }
}