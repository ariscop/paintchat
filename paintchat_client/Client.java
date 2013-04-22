package paintchat_client;

import java.applet.Applet;
import java.awt.BorderLayout;

import syi.util.ThreadPool;

// Referenced classes of package paintchat_client:
//            Pl

/**
 * 
 * @author shi-chan
 * @author Phase4
 *
 */

public class Client extends Applet
{

	private static final long serialVersionUID = 1L;
	
	private Pl pl;

    public Client()
    {
    }

    public void destroy()
    {
        if(pl != null)
        {
            pl.destroy();
        }
    }

    public void init()
    {
        try
        {
            setLayout(new BorderLayout());
            pl = new Pl(this);
            add(pl, "Center");
            validate();
            ThreadPool.poolStartThread(pl, 'i');
        }
        catch(Throwable throwable)
        {
            throwable.printStackTrace();
        }
    }
}
