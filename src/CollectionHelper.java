import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CollectionHelper {

    public String readFromFile (File FILENAME) {
        BufferedReader br = null;
        FileReader fr = null;
        String fileContent="";
        try {
            //br = new BufferedReader(new FileReader(FILENAME));
            fr = new FileReader(FILENAME);
            br = new BufferedReader(fr);
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                fileContent+= sCurrentLine;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
                if (fr != null)
                    fr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return fileContent;
    }


    public ArrayList<Character> stringerToChar (String string){
        // lowercase the string
        string = string.toLowerCase();

        // Make the fileContent all attached lower cases
        ArrayList<Character> finalFileContent = new ArrayList<Character>();
        for (int i = 0; i < string.length(); i++) {
            char ch = string.charAt(i);
            if (Character.isLetter(ch))
                finalFileContent.add(ch);
        }
        return finalFileContent;
    }

    public void writeToFile(String fileContent, String fileName) {
        BufferedWriter bw = null;
        String fileNameFromUser = fileName;
        try {
            String mycontent = fileContent;
            //String fileNameFromUser =" ";
            //Specify the file name and path here
            File  folder = new File(Main.FILEPATH);
            File  file = new File(folder, fileNameFromUser + ".txt");
            /* This logic will make sure that the file
             * gets created if it is not present at the
             * specified location*/
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            bw.write(mycontent);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        finally
        {
            try{
                if(bw!=null)
                    bw.close();
            }catch(Exception ex){
                System.out.println("Error in closing the BufferedWriter"+ex);
            }
        }

    }
}

