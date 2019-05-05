import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

class Unigram {

    // The smoothing factor
    public final static double SMOOTHING = 0.5;
    // The size of the character set
    public final static int CHAR_SET_SIZE = 26;
    // The unigrams array to collect frquency of each character
    public Double[] unigrams = new Double[CHAR_SET_SIZE];
    // unigramTotals to calculate total corpus size
    public int unigramTotals = 0;

    public Unigram(String filePath, String key)
            throws FileNotFoundException, IOException {
        this(new File(filePath), key);
    }

    public Unigram(File file, String key)
            throws FileNotFoundException, IOException {
        train(file, key);
    }

    // Trains the unigram
    public void train(File file, String key) throws FileNotFoundException, IOException {

        String fileContent="";
        CollectionHelper collectionHelper = new CollectionHelper();
        // Read the fileContent from the file
        fileContent= collectionHelper.readFromFile(file);
        // Give a fileContent/string and get an array list of lower case characters
        ArrayList<Character> charArrayList = collectionHelper.stringerToChar(fileContent);

        // Set up the Unigram
        for(int i = 0; i < unigrams.length; i++) {
             unigrams[i] = 0.0;
        }
        // Build the Unigram
        for(int i = 0; i < charArrayList.size(); i++) {
            incrementValue(charArrayList.get(i));
            unigramTotals++;
        }
        //Output a dump of the language models
        createModelOutputFile(key, collectionHelper);
    }

    // Increments the value of the unigram by one.  It is set to one if the unigram does not exist.
    public void incrementValue(Character first) {
        unigrams[getDecimalValue(first)]++;
    }

    //Converts a character to it's array index
    public int getDecimalValue(char character) {
        return Integer.valueOf(character) - 97;
    }

    //Obtain the probability of the of the unigram
    public Double probability(char char1) {
        int row = getDecimalValue(char1);
        double val = unigrams[row];
        double numerator = val + SMOOTHING;
        double denominator = unigramTotals + (SMOOTHING * CHAR_SET_SIZE);
        return numerator / denominator;
    }

    private void createModelOutputFile(String key, CollectionHelper collectionHelper) {

        String modelContent="";

        if (key=="English") {
            modelContent+= "English language unigram model: \n\n";
            modelContent+= "Total frequency of the characters (smoothed)= "+(unigramTotals + (SMOOTHING * CHAR_SET_SIZE))+"\n";
            modelContent+= "Character frequency (smoothed): \n";
            for (int i = 0; i < CHAR_SET_SIZE; i++) {
                modelContent+="(" + (char) (i + 97) + ") =" + (unigrams[i]+SMOOTHING) + "\n";
            }
            modelContent+= "\nCharacter probability (smoothed): \n";
            for (int i = 0; i < CHAR_SET_SIZE; i++) {
                modelContent+="(" + (char) (i + 97) + ") = " + probability((char) (i + 97)) + "\n";
            }
            collectionHelper.writeToFile(modelContent, "unigramEN");
        }

        if (key=="French") {
            modelContent+= "French language unigram model: \n\n";
            modelContent+= "Total frequency of the characters (smoothed)= "+(unigramTotals + (SMOOTHING * CHAR_SET_SIZE))+"\n";
            modelContent+= "Character frequency (smoothed): \n";
            for (int i = 0; i < CHAR_SET_SIZE; i++) {
                modelContent+="(" + (char) (i + 97) + ") =" + (unigrams[i]+SMOOTHING) + "\n";
            }
            modelContent+= "\nCharacter probability (smoothed): \n";
            for (int i = 0; i < CHAR_SET_SIZE; i++) {
                modelContent+="(" + (char) (i + 97) + ") = " + probability((char) (i + 97)) + "\n";
            }
            collectionHelper.writeToFile(modelContent, "unigramFR");
        }

        if (key=="German") {
            modelContent+= "German language unigram model: \n\n";
            modelContent+= "Total frequency of the characters (smoothed)= "+(unigramTotals + (SMOOTHING * CHAR_SET_SIZE))+"\n";
            modelContent+= "Character frequency (smoothed): \n";
            for (int i = 0; i < CHAR_SET_SIZE; i++) {
                modelContent+="(" + (char) (i + 97) + ") =" + (unigrams[i]+SMOOTHING) + "\n";
            }
            modelContent+= "\nCharacter probability (smoothed): \n";
            for (int i = 0; i < CHAR_SET_SIZE; i++) {
                modelContent+="(" + (char) (i + 97) + ") = " + probability((char) (i + 97)) + "\n";
            }
            collectionHelper.writeToFile(modelContent, "unigramGE");
        }
    }
}
