package paintchat_client;

import java.io.IOException;
import java.util.zip.Inflater;
import paintchat.M;
import paintchat_server.PaintChatTalker;
import syi.util.ByteStream;
import syi.util.ExceptionHandler;
import syi.util.ThreadPool;

// Referenced classes of package paintchat_client:
//            Data

/**
 * This class handles the 'paint'
 * 
 * @author shi-chan
 * @author Phase4
 *
 */


public class TLine extends PaintChatTalker
{

    public Data data;
    private ByteStream bSendCash;
    private ByteStream stmIn;
    private ByteStream workSend;
    private M mgOut;
    private M mgDraw;
    private boolean isCompress;
    private boolean isRunDraw;
    Inflater inflater;

    public TLine(Data data1, M m)
    {
        bSendCash = new ByteStream();
        stmIn = new ByteStream();
        workSend = new ByteStream();
        mgOut = null;
        mgDraw = null;
        isCompress = false;
        isRunDraw = false;
        inflater = new Inflater(false);
        data = data1;
        mgDraw = m;
    }

    protected void mDestroy()
    {
        isRunDraw = false;
        super.iSendInterval = 0;
    }

    protected void mRead(ByteStream bytestream)
        throws IOException
    {
        if(bytestream.size() <= 1)
        {
            switch(bytestream.getBuffer()[0])
            {
            case 0: // '\0'
                synchronized(stmIn)
                {
                    stmIn.writeInt(0);
                }
                break;

            case 1: // '\001'
                isCompress = true;
                break;
            }
            return;
        }
        try
        {
            int i = bytestream.size();
            if(isCompress)
            {
                isCompress = false;
                if(inflater == null)
                {
                    inflater = new Inflater(false);
                }
                inflater.reset();
                inflater.setInput(bytestream.getBuffer(), 0, i);
                synchronized(this)
                {
                    byte abyte0[] = workSend.getBuffer();
                    synchronized(stmIn)
                    {
                        int j;
                        for(; !inflater.needsInput(); stmIn.write(abyte0, 0, j))
                        {
                            j = inflater.inflate(abyte0, 0, abyte0.length);
                        }

                    }
                }
            } else
            {
                if(inflater != null)
                {
                    inflater.end();
                    inflater = null;
                }
                synchronized(stmIn)
                {
                    bytestream.writeTo(stmIn);
                }
            }
        }
        catch(Exception exception)
        {
            ExceptionHandler.handleException(exception);
        }
    }

    public void mInit()
    {
    }

    protected void mIdle(long l)
        throws IOException
    {
    }

    protected void mWrite()
        throws IOException
    {
        if(bSendCash.size() <= 0)
        {
            return;
        }
        synchronized(bSendCash)
        {
            write(bSendCash);
            bSendCash.reset();
        }
    }

    public void send(M m) {
        synchronized (bSendCash) {
            if (m == null) {
                try {
                    super.canWrite = true;
                    bSendCash.reset();
                    bSendCash.writeInt(2);
                    write(bSendCash);
                    flush();
                } catch (IOException _ex) {
                }
                return;
            }
            m.get(bSendCash, workSend, mgOut);
            if (mgOut == null) {
                mgOut = new M();
            }
            mgOut.set(m);
        }
    }

    public void run() {
        if (!isRunDraw) {
            isRunDraw = true;
            ThreadPool.poolStartThread(this, 'd');
            super.run();
            return;
        }
        try {
            while (this.isRunDraw) {
                if (stmIn.size() >= 2) {
                    int j;
                    synchronized (stmIn) {
                        byte abyte0[] = stmIn.getBuffer();
                        int i = (abyte0[0] & 0xff) << 8 | abyte0[1] & 0xff;
                        if (i <= 2) {
                            mgDraw.newUser(null).wait = 0;
                            data.addTextComp();
                            stmIn.reset(i + 2);
                            continue;
                        }
                        j = mgDraw.set(stmIn.getBuffer(), 0);
                        stmIn.reset(j);
                    }
                    if (mgDraw.iLayer >= data.info.L) {
                        data.info.setL(mgDraw.iLayer + 1);
                    }
                    mgDraw.draw();
                } else {
                    Thread.currentThread();
                    Thread.sleep(3000L);
                }
            }
        } catch (InterruptedException localInterruptedException) {
        }
    }
    
    public synchronized void mRStop()
    {
        send(null);
        mStop();
    }
}
