package mythgame.files;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import mythgame.main.Main;

public class Downloader {
public Downloader() {
    String os = Main.os;
	String saveTo;
    if (os.contains("Windows")) {
    saveTo = System.getProperty("user.home") + "/AppData/Roaming/";
	} else if(os.contains("Mac")){
		saveTo = System.getProperty("user.home") + "/Library/Application Support/";
	} else {
		saveTo = System.getProperty("user.home")+"/";
	}
    try {
        URL url = new URL("https://sites.google.com/site/izjemythgame/newpage/MythGame.zip?attredirects=0&d=1");
        URLConnection conn1 = url.openConnection();
        InputStream in = conn1.getInputStream();
        FileOutputStream out = new FileOutputStream(saveTo + "MythGame.zip");
        byte[] b = new byte[1024];
        int count;
        while ((count = in.read(b)) >= 0) {
            out.write(b, 0, count);
        }
        out.flush(); out.close(); in.close();                   

    } catch (IOException e) {
        e.printStackTrace();
    }
}
}