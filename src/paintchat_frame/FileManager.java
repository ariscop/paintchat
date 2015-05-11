package paintchat_frame;

import java.io.File;
import java.io.IOException;
import paintchat.Config;
import syi.util.Io;

public class FileManager
{

    Config config;

    public FileManager(Config config1)
    {
        config = config1;
    }

    public static void copyFile(File file, File file1)
        throws IOException
    {
        Io.copyFile(file, file1);
    }

    public static void copyFile(String s, String s1)
    {
        copyFile(s, s1);
    }

    public static void copyFiles(String as[], String s)
    {
        Io.copyFiles(as, s);
    }

    public void templateToWWW()
    {
        try
        {
            String s1 = Io.getCurrent();
            String template = "cnf" + File.separatorChar + "template" + File.separatorChar;
            File www = new File(config.getString("File_PaintChatClient_Dir", "www"));
            if(!www.isDirectory())
                www.mkdirs();

            String files[] = {
                "pchat.jar",
                "pchat_user_list.swf",
                "entrance_normal.html",
                "entrance_pro.html",
                "index.html"
            };
            for (String file : files) {
                if (file != "index.html" || config.getBool("App_Get_Index", true)) {
                    Io.copyFile(Io.makeFile(s1, template + file), new File(www, file));
                }
            }

        }
        catch(Throwable throwable)
        {
            throwable.printStackTrace();
        }
    }
}
