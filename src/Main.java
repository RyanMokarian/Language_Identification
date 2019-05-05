/*
    This project builds and experiments with a probabilistic language identification system to identify
    the language of a sentence. First, unigram and bigram character-based language are modelled for
    English, French and German languages. Then, the language models are used for the identification of
    the most probable language of 30 sentences given as input (filename: 30Sentences.txt).
    For the training set, the following three files (trainFR.txt, trainEN.txt and trainGE.txt) and used.
    All four files should be located inside a folder called "files".
    Note: modify the FILEPATH address in the Main class to where you locate the project.
    Current project default path is "C:\\Users\\NGram_Language_Identification".
*/


import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

public class Main {
    public static final String FILEPATH = "C:\\FAMILY\\SHAHRAM_MOKARIAN\\COMPUTER_CONCORDIA\\OTHERS\\FALL2018\\COMP6721\\Mini_Project\\MP3\\";

    public static void main(String args[]) {
        BufferedReader br = null;
        FileReader fr = null;
        CollectionHelper collectionHelper = new CollectionHelper();
        UnigramDetermineLanguage unigramDetermineLanguage = new UnigramDetermineLanguage();
        BigramDetermineLanguage bigramDetermineLanguage = new BigramDetermineLanguage();

        // Training section
        try {
            unigramDetermineLanguage.train("English", new File(FILEPATH + "files/trainEN.txt"));
            unigramDetermineLanguage.train("French", new File(FILEPATH + "files/trainFR.txt"));
            unigramDetermineLanguage.train("German", new File(FILEPATH + "files/trainGE.txt"));
            bigramDetermineLanguage.train("English", new File(FILEPATH + "files/trainEN.txt"));
            bigramDetermineLanguage.train("French", new File(FILEPATH + "files/trainFR.txt"));
            bigramDetermineLanguage.train("German", new File(FILEPATH + "files/trainGE.txt"));
        } catch (Exception e) {
            System.out.println("There is an error in the training text: " + e);
        }

        // Evaluating section
        String Sentence="";
        int counter = 0;
        try {
        br = new BufferedReader(new FileReader(FILEPATH + "files/30Sentences.txt"));
          while ((Sentence = br.readLine()) != null) {
//              To Test a Sentence manually, comment While and uncomment the below line
//              Sentence = "J’aime l’IA."; counter =30;
                System.out.println(Sentence+"\n");
                String outputFileContent="";
                counter ++;
                String unigramFileContent = unigramDetermineLanguage.evaluate(Sentence);
                String bigramFileContent = bigramDetermineLanguage.evaluate(Sentence);
                String outputFile = "out"+counter;
                outputFileContent= Sentence+"\n\n"+unigramFileContent+bigramFileContent;
                collectionHelper.writeToFile(outputFileContent, outputFile);
        }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
