/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sendORreceive;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Form;
import javax.microedition.midlet.*;

/**
 * @author Administrator
 */
public class MySMSReceive2 extends MIDlet {

    Alert a;
    Display display;
    Form f;

    public MySMSReceive2() {
        f = new Form("waiting...");
        display = Display.getDisplay(this);

    }

    public void startApp() {
        display.setCurrent(f);
        ReceiveThread rt = new ReceiveThread(this);
        Thread t = new Thread(rt);
        t.start();

    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }
}


