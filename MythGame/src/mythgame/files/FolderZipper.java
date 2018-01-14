package mythgame.files;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import mythgame.main.Main;

public class FolderZipper {
 /**
 * @param srcFolder path to the folder to be zipped
 * @param destZipFile path to the final zip file
 */
 public FolderZipper() {
 	 String os = Main.os;
	 if (os.contains("Windows")) {
		 unzipFolder(System.getProperty("user.home")+"/AppData/Roaming/MythGame.zip", System.getProperty("user.home")+"/AppData/Roaming");
 	 } else if(os.contains("Mac")){
 		unzipFolder(System.getProperty("user.home")+"/Library/Application Support/MythGame.zip", System.getProperty("user.home")+"/Library/Application Support");
 	 } else {
 		unzipFolder(System.getProperty("user.home")+"/MythGame.zip",System.getProperty("user.home"));
 	 }
 }

 /**
 * @param zipFile the zip file that needs to be unzipped
 * @param destFolder the folder into which unzip the zip file and create the folder structure
 */
 public static void unzipFolder( String zipFile, String destFolder ) {
 try {
 ZipFile zf = new ZipFile(zipFile);
 Enumeration< ? extends ZipEntry> zipEnum = zf.entries();
 String dir = destFolder;

 while( zipEnum.hasMoreElements() ) {
 ZipEntry item = (ZipEntry) zipEnum.nextElement();

 if (item.isDirectory()) {
 File newdir = new File(dir + File.separator + item.getName());
 newdir.mkdir();
 } else {
 String newfilePath = dir + File.separator + item.getName();
 File newFile = new File(newfilePath);
 if (!newFile.getParentFile().exists()) {
 newFile.getParentFile().mkdirs();
 }

 InputStream is = zf.getInputStream(item);
 FileOutputStream fos = new FileOutputStream(newfilePath);
 int ch;
 while( (ch = is.read()) != -1 ) {
 fos.write(ch);
 }
 is.close();
 fos.close();
 }
 }
 zf.close();
 } catch (Exception e) {
 e.printStackTrace();
 }
 new File(zipFile).delete();
 }
}