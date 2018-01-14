package mythgame.files;

import java.io.File;

public class Delete {

public static void removeDirectory(File folder) {


  String[] contents = folder.list();


  if (contents != null) {
  for (int i = 0; i < contents.length; i++) {
  File inside = new File(folder, contents[i]);
   
  if (inside.isDirectory()){
  removeDirectory(inside);
  }
  inside.delete();
  }
 folder.delete();
}
}


}