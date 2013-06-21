/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mob_tutes_2;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.*;

/**
 * @author Administrator
 */
public class MySMSMidlet extends MIDlet implements CommandListener {

    Form f;
    TextField no, msg;
    Command exit, send;
    Display display;

    public MySMSMidlet() {
        display = Display.getDisplay(this);

        f = new Form("Message FOrm");
        no = new TextField("Number", null, 15, TextField.PHONENUMBER);
        msg = new TextField("Message text", null, 65535, TextField.ANY);
        exit = new Command("Exit", Command.EXIT, 0);
        send = new Command("Send", Command.EXIT, 0);
        f.addCommand(send);
        f.addCommand(exit);
        f.append(no);
        f.append(msg);
        f.setCommandListener(this);
    }

    public void startApp() {
        display.setCurrent(f);
        
        ReceiveThread rt = new ReceiveThread(this,false);
        Thread t = new Thread(rt);
        t.start();
       
        
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable d) {
        if (c == exit) {
            notifyDestroyed();
        }
        if (c == send) {
            sendSMS();
        }
    }

    private void sendSMS() {
        senderThread st = new senderThread(this);
        Thread t = new Thread(st);
        t.start();
        st=null;
        t=null;
        
    }
}
