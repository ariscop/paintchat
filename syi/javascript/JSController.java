package syi.javascript;

import java.applet.Applet;
import java.awt.Rectangle;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import netscape.javascript.JSObject;

/**
 * 
 * @author shi-chan
 * @author Phase4
 *
 * interface to netscape.javascript.JSObject
 *
 */

public class JSController
{    
    private JSObject oJava;
    private Applet applet;
    
    private SimpleDateFormat dateFormat;
    /*private static final String STR_NO_SUPPORT = "No support JavaScript";
    private static final String STR_VERSION = "JavaScript Controller (C)shi-chan 2001";*/

    public JSController(Applet applet1)
        throws IOException
    {
//        isCash = false;
//        tables = null;
        dateFormat = null;
        System.out.println("JavaScript Controller (C)shi-chan 2001");
        applet = applet1;
        setup();
    }

    public void alert(String s)
        throws IOException
    {
        callFunction("alert", new Object[] {
            s
        });
    }

    public String callFunction(String s, Object aobj[])
        throws IOException
    {
        try
        {
            return oJava.call(s, aobj).toString();
        }
        catch(Throwable _ex)
        {
            throw new IOException("Can't call " + s);
        }
    }

    public boolean confirm(String s)
        throws IOException
    {
        return (new Boolean(callFunction("confirm", new Object[] {
            s
        }))).booleanValue();
    }

    public String[][] dataLoad()
        throws IOException
    {
        Hashtable<String, String> hashtable = new Hashtable<String, String>();
        dataLoad(hashtable);
        int i = hashtable.size();
        String as[][] = new String[i][2];
        int j = 0;
        for(Enumeration<String> enumeration = hashtable.keys(); enumeration.hasMoreElements();)
        {
            Object obj = enumeration.nextElement();
            as[j][0] = (String)obj;
            as[j][1] = hashtable.get(obj).toString();
            j++;
        }

        return as;
    }

    /**
     * Retrives cookies
     * 
     * @param hashtable
     * to store cookies in
     * @throws IOException
     */
    public void dataLoad(Hashtable<String, String> hashtable)
        throws IOException
    {
        String s = getProperty("document.cookie");
        int i = s.length();
        for(int j = 0; j < i; j++)
        {
            StringBuffer stringbuffer = new StringBuffer();
            for(; j < i; j++)
            {
                char c = s.charAt(j);
                if(c == '=')
                {
                    j++;
                    break;
                }
                stringbuffer.append(c);
            }

            String s1 = unEscape(stringbuffer.toString());
            stringbuffer = new StringBuffer();
            for(; j < i; j++)
            {
                char c1 = s.charAt(j);
                if(c1 == ';')
                {
                    j++;
                    break;
                }
                stringbuffer.append(c1);
            }

            String s2 = unEscape(stringbuffer.toString());
            if(!s1.equals("expires"))
            {
                hashtable.put(s1, s2);
            }
        }

    }

    public void dataSave(String as[][], int i)
        throws IOException
    {
        try
        {
            Hashtable<String, String> hashtable = new Hashtable<String, String>();
            for(int j = 0; j < as.length; j++)
            {
                hashtable.put(as[j][0], as[j][1]);
            }

            dataSave(hashtable, i);
        }
        catch(RuntimeException _ex)
        {
            throw new RuntimeException("Format error String[*][2]");
        }
    }

    public void dataSave(Hashtable<String, String> hashtable, int i)
        throws IOException
    {
        if(hashtable.size() <= 0)
        {
            return;
        }
        StringBuffer stringbuffer = new StringBuffer();
        for(Enumeration<String> enumeration = hashtable.keys(); enumeration.hasMoreElements(); stringbuffer.append("; "))
        {
            String s = (String)enumeration.nextElement();
            stringbuffer.append(escape(s));
            stringbuffer.append('=');
            stringbuffer.append(escape((String)hashtable.get(s)));
        }

        if(dateFormat == null)
        {
            dateFormat = new SimpleDateFormat("EEE', 'dd' 'MMM' 'yyyy' 'HH:mm:ss' GMT'", Locale.ENGLISH);
        }
        stringbuffer.append("expires=" + dateFormat.format(new Date(System.currentTimeMillis() + (long)(0x5265c00 * i))) + "; ");
        setProperty("document.cookie", stringbuffer.toString());
    }

    public String escape(String s)
    {
        try
        {
            return callFunction("escape", new Object[] {
                s
            });
        }
        catch(IOException _ex)
        {
            return "";
        }
    }

    private final JSObject getArray(JSObject obj, String s)
        throws IOException
    {
        int i = s.indexOf('[');
        String s1 = s.substring(0, i);
        obj = getMember(obj, s1);
        return (JSObject)obj.getSlot(
        	Integer.decode(s.substring(i + 1, s.indexOf(']')))
        );
    }

    private final JSObject getEndObject(String s, StringBuffer stringbuffer)
        throws IOException
    {
        JSObject obj1 = oJava;
        int i = 0;
        int k = 0;
        s.length();
        while((k = s.indexOf('.', i)) >= 0) 
        {
            String s2 = s.substring(i, k);
            obj1 = getMember(obj1, s2);
            i = k + 1;
        }
        stringbuffer.append(s.substring(i));
        return obj1;
    }

    private final JSObject getMember(JSObject obj, String s)
        throws IOException
    {
        try
        {
            if(s.indexOf('[') >= 0)
            {
                return getArray(obj, s);
            } else
            {
                return (JSObject)obj.getMember(s);
            }
        }
        catch(Throwable _ex)
        {
            throw new IOException("can't get " + s);
        }
    }

    public String getProperty(String s)
        throws IOException
    {
        StringBuffer stringbuffer = new StringBuffer();
        JSObject obj = getEndObject(s, stringbuffer);
        return getMember(
        		obj, stringbuffer.toString()
        ).toString();
    }

    public void openWindow(String s, String s1, Rectangle rectangle, boolean flag, boolean flag1, boolean flag2, boolean flag3, 
            boolean flag4)
        throws IOException
    {
        StringBuffer stringbuffer = new StringBuffer();
        if(s == null)
        {
            s = "";
        }
        if(s1 == null || s1.length() == 0)
        {
            s1 = "sub";
        }
        if(!flag4 && rectangle != null)
        {
            stringbuffer.append("left=" + rectangle.x + ",top=" + rectangle.y + ",width=" + rectangle.width + ",height=" + rectangle.height + ",");
        }
        int i = flag ? 1 : 0;
        stringbuffer.append("toolbar=" + i + ",location" + i + ",directories=" + i + ",status=" + i + ",menubar=" + i + ',');
        i = flag1 ? 1 : 0;
        stringbuffer.append("titlebar=" + i + ',');
        i = flag2 ? 1 : 0;
        stringbuffer.append("scrollbars=" + i + ',');
        i = flag3 ? 1 : 0;
        stringbuffer.append("resizable=" + i + ',');
        i = flag4 ? 1 : 0;
        stringbuffer.append("fullscreen=" + i);
        runScript(s1 + "=window.open('" + s + "','" + s1 + "','" + stringbuffer.toString() + "');");
    }

    public final String runScript(String s)
        throws IOException
    {
        try
        {
            Object obj = oJava.eval(s);
            return obj != null ? obj.toString() : "";
        }
        catch(Throwable _ex)
        {
            throw new IOException("ScriptError");
        }
    }

    protected String scriptString(String s)
    {
        StringBuffer stringbuffer = new StringBuffer();
        int i = s.length();
        for(int j = 0; j < i; j++)
        {
            char c = s.charAt(j);
            switch(c)
            {
            case 39: // '\''
                stringbuffer.append('\\');
                break;

            case 34: // '"'
                stringbuffer.append('\\');
                break;

            case 92: // '\\'
                stringbuffer.append('\\');
                break;

            case 13: // '\r'
                stringbuffer.append('\\');
                c = 'r';
                break;

            case 10: // '\n'
                stringbuffer.append('\\');
                c = 'n';
                break;
            }
            stringbuffer.append(c);
        }

        return stringbuffer.toString();
    }
    
    public void setProperty(String s, Object obj)
        throws IOException
    {
        StringBuffer stringbuffer = new StringBuffer();
        JSObject obj1 = getEndObject(s, stringbuffer);
        String s1 = stringbuffer.toString();
        stringbuffer = null;
        try
        {
            if(s1.indexOf('[') >= 0)
            {
            	//TODO: imposible code?
                //obj1.setSlot(s1, obj);
            	throw new Exception("Invalid argument for setSlot");
            }
            else
            {
                obj1.setMember(s1, obj);
            }
            return;
        }
        catch(Throwable _ex)
        {
            throw new IOException("can't set " + s1);
        }
    }

    public final synchronized void setup()
        throws IOException
    {
        try
        {
            oJava = JSObject.getWindow(applet);
        }
        catch(Throwable _ex)
        {
            oJava = null;
            throw new IOException("No support JavaScript");
        }
    }

    public void showDocument(String s, String s1, String s2)
        throws IOException
    {
        if(s == null || s.equals("_self") || s.equals("_parent"))
        {
            s = "";
        }
        if(!s.endsWith("document"))
        {
            if(s.length() > 0)
            {
                s = s.concat(".");
            }
            s = s.concat("document");
        }
        if(s1 == null || s1.length() == 0)
        {
            s1 = "text/plain";
        }
        runScript("var doc=" + s + ".open('" + s1 + "');\ndoc.write('" + scriptString(s2) + "');\ndoc.close();");
    }

    public String unEscape(String s)
    {
        try
        {
            return callFunction("unescape", new Object[] {
                s
            });
        }
        catch(IOException _ex)
        {
            return "";
        }
    }
}
