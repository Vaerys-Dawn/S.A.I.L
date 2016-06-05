import java.io.File;
import java.io.IOException;

/**
 * Created by Vaerys on 22/05/2016.
 */
public class SailFileManager {

    public void createDirectory(File file){
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public void createFile(File file){
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeToFile(String text, File file, boolean appends){

    }

    public String readFromFile(File file, int line){
        return null;
    }


}
