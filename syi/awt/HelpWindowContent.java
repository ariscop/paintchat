package syi.awt;

import java.awt.Image;
import java.awt.Point;
import java.util.Hashtable;

import paintchat.Res;

public class HelpWindowContent
{

    public Point point;
    public String string;
    public Image image;
    boolean isResource;
    public Hashtable<String, Object> res;
    public int timeStart;
    public int timeEnd;
    private boolean isEnableVisited;
    private boolean isVisit;

    public HelpWindowContent(Image image1, String s, boolean flag, Point point1, Hashtable<String, Object> hashtable)
    {
        string = null;
        image = null;
        isResource = false;
        timeStart = 2000;
        timeEnd = 15000;
        isEnableVisited = false;
        isVisit = false;
        point = point1;
        string = s;
        res = hashtable;
        isResource = flag;
        image = image1;
    }

    public HelpWindowContent(String s, boolean flag, Point point1, Res res2)
    {
        string = null;
        image = null;
        isResource = false;
        timeStart = 2000;
        timeEnd = 15000;
        isEnableVisited = false;
        isVisit = false;
        point = point1;
        string = s;
        res = res2;
        isResource = flag;
    }

    public String getText()
    {
        if(string == null || string.length() == 0)
        {
            return "";
        }
        if(isResource)
        {
            String s = (String)res.get(string + "_Com");
            return s != null ? s : res.get(string).toString();
        } else
        {
            return string;
        }
    }

    public boolean isVisible(boolean flag)
    {
        return isEnableVisited ? isVisit : flag;
    }

    public void setText(String s, boolean flag)
    {
        string = s;
        isResource = flag;
    }

    public void setVisit(boolean flag)
    {
        isEnableVisited = true;
        isVisit = flag;
    }
}
