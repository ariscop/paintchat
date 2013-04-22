package syi.util;

import java.awt.Component;
import java.awt.Image;
import java.awt.MediaTracker;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.GregorianCalendar;


public class Io
{

    public Io()
    {
    }

    public static boolean copyFile(File file, File file1)
    {
        try
        {
            File file2 = getDirectory(file1);
            if(!file2.isDirectory())
            {
                file2.mkdirs();
            }
            if(file1.isDirectory())
            {
                file1 = new File(file1, getFileName(file.toString()));
            }
            byte abyte0[] = new byte[512];
            FileInputStream fileinputstream = new FileInputStream(file);
            FileOutputStream fileoutputstream = new FileOutputStream(file1);
            int i;
            while((i = fileinputstream.read(abyte0)) != -1) 
            {
                fileoutputstream.write(abyte0, 0, i);
            }
            fileoutputstream.flush();
            fileoutputstream.close();
            fileinputstream.close();
            return true;
        }
        catch(IOException ioexception)
        {
            ioexception.printStackTrace();
        }
        return false;
    }

    public static void copyFiles(String as[], String s)
    {
        for(int i = 0; i < as.length; i++)
        {
            copyFile(new File(as[i]), new File(s));
        }

    }

    public static String getCurrent()
    {
        return System.getProperty("user.dir", "/");
    }

    public static final String getDateString(String s, String s1, String s2)
    {
        String s3 = '.' + s1;
        String s4 = null;
        File file = new File(s2);
        if(!file.isDirectory())
        {
            file.mkdirs();
        }
        try
        {
            GregorianCalendar gregoriancalendar = new GregorianCalendar();
            String s5 = s + gregoriancalendar.get(2) + '-' + gregoriancalendar.get(5) + '_';
            int i;
            for(i = 0; i < 256; i++)
            {
                File file1 = new File(s2, s5 + i + s3);
                if(file1.isFile())
                {
                    continue;
                }
                s4 = s2 + "/" + s5 + i + s3;
                break;
            }

            if(i >= 32767)
            {
                s4 = s2 + "/" + s + "over_file255" + s3;
            }
        }
        catch(RuntimeException _ex)
        {
            s4 = s + "." + s1;
        }
        return s4;
    }

    public static File getDirectory(File file)
    {
        try
        {
            return new File(getDirectory(file.getCanonicalPath()));
        }
        catch(IOException _ex)
        {
            return null;
        }
    }

    public static String getDirectory(String s)
    {
        if(s == null || s.length() <= 0)
        {
            return "./";
        }
        if(s.indexOf('\\') >= 0)
        {
            s = s.replace('\\', '/');
        }
        int i = s.lastIndexOf('/');
        if(i < 0)
        {
            return "./";
        }
        int j = s.indexOf('.', i);
        if(j < i)
        {
            if(s.charAt(s.length() - 1) != '/')
            {
                s = s + '/';
            }
        } else
        {
            s = s.substring(0, i + 1);
        }
        return s;
    }

    public static String getFileName(String s)
    {
        if(s.lastIndexOf('.') < 0)
        {
            return "";
        }
        int i = s.lastIndexOf('/');
        if(i < 0)
        {
            i = s.lastIndexOf('\\');
        }
        if(i < 0)
        {
            return s;
        } else
        {
            return s.substring(i + 1);
        }
    }

    public static Image loadImageNow(Component component, String s)
    {
        Image image = null;
        try
        {
            image = component.getToolkit().getImage(s);
            MediaTracker mediatracker = new MediaTracker(component);
            mediatracker.addImage(image, 0);
            mediatracker.waitForID(0, 10000L);
            mediatracker.removeImage(image);
            mediatracker = null;
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        return image;
    }

    public static File makeFile(String s, String s1)
    {
        File file = toDir(s);
        if(!file.exists())
        {
            file.mkdirs();
        }
        return new File(file, s1);
    }

    public static final int r(InputStream inputstream)
        throws IOException
    {
        int i = inputstream.read();
        if(i == -1)
        {
            throw new EOFException();
        } else
        {
            return i;
        }
    }

    public static final int readUShort(InputStream inputstream)
        throws IOException
    {
        int i = inputstream.read();
        int j = inputstream.read();
        if((i | j) < 0)
        {
            throw new EOFException();
        } else
        {
            return (i << 8) + j;
        }
    }

    public static final void rFull(InputStream inputstream, byte abyte0[], int i, int j)
        throws EOFException, IOException
    {
        int k;
        for(j += i; i < j; i += k)
        {
            k = inputstream.read(abyte0, i, j - i);
            if(k == -1)
            {
                throw new EOFException();
            }
        }

    }

    public static File toDir(String s)
    {
        if(s == null || s.length() <= 0)
        {
            return new File("./");
        }
        if(s.indexOf('\\') >= 0)
        {
            s = s.replace('\\', '/');
        }
        if(s.charAt(s.length() - 1) != '/')
        {
            s = s + '/';
        }
        return new File(s);
    }

    public static final void wShort(OutputStream outputstream, int i)
        throws IOException
    {
        outputstream.write(i >>> 8 & 0xff);
        outputstream.write(i & 0xff);
    }
}
