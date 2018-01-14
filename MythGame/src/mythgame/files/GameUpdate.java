package mythgame.files;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class GameUpdate {
public GameUpdate() {
  String saveTo = System.getProperty("user.dir")+"/";
 
  try {
  URL url = new URL("https://sites.google.com/site/izjemythgame/newpage/MythGame.jar?attredirects=0&d=1");
  URLConnection conn1 = url.openConnection();
  InputStream in = conn1.getInputStream();
  FileOutputStream out = new FileOutputStream(saveTo + "MythGame.jar");
  byte[] b = new byte[1024];
  int count;
  while ((count = in.read(b)) >= 0) {
  out.write(b, 0, count);
  }
  out.flush(); out.close(); in.close();  

  } catch (IOException e) {
  e.printStackTrace();
  }

//Run a java app in a separate system process
Process proc = null;
try {
	proc = Runtime.getRuntime().exec("java -jar MythGame.jar");
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
//Then retreive the process output
InputStream in = proc.getInputStream();
InputStream err = proc.getErrorStream();


System.exit(0);
}
}