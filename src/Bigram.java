import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

class Bigram {

    // The smoothing factor
    public final static double SMOOTHING = 0.5;
    // The size of the character set
    public final static int CHAR_SET_SIZE = 26;
    // The bigram matrix
    public Double[][] bigrams = new Double[CHAR_SET_SIZE][CHAR_SET_SIZE];
    // Totals for each row of bigrams (e.g. number of bigrams starting with a character)
    public int[] bigramTotals = new int[CHAR_SET_SIZE];

    public Bigram(String filePath, String key)
            throws FileNotFoundException, IOException {
        this(new File(filePath), key);
    }

    public Bigram(File file, String key)
            throws FileNotFoundException, IOException {
        train(file, key);
    }

    // Trains the bigram
    public void train(File file, String key) throws FileNotFoundException, IOException {

        String fileContent="";
        CollectionHelper collectionHelper = new CollectionHelper();
        // Read the fileContent from the file
        fileContent= collectionHelper.readFromFile(file);
        // Give a fileContent/string and get an array list of lower case characters
        ArrayList<Character> charArrayList = collectionHelper.stringerToChar(fileContent);

        // Set up the Bigram
        for(int i = 0; i < bigrams.length; i++) {
            for(int j = 0; j < bigrams[i].length; j++) {
                bigrams[i][j] = 0.0;
            }
        }
        // Build the Bigram
        for(int i = 0; i < charArrayList.size()-1; i++) {
            char token1 = charArrayList.get(i);
                incrementValue(charArrayList.get(i), charArrayList.get(i+1));
                bigramTotals[getDecimalValue(token1)]++;
        }

        //Output a dump of the language models
        languageModelOutput(key, collectionHelper);
    }

    // Increments the value of the bigram by one.  It is set to one if the bigram does not exist.
    public void incrementValue(Character first, Character second) {
        bigrams[getDecimalValue(first)][getDecimalValue(second)]++;
    }

    //Converts a character to it's array index
    public int getDecimalValue(char character) {
        return Integer.valueOf(character) - 97;
    }

    //Obtain the probability of the of the bigram
        public Double probability(char char1, char char2) {
        int row = getDecimalValue(char1);
        int col = getDecimalValue(char2);
        double val = bigrams[row][col];
        double numerator = val + SMOOTHING;
        double denominator = bigramTotals[row] + (SMOOTHING * CHAR_SET_SIZE);
        return numerator / denominator;
    }

    private void languageModelOutput(String key, CollectionHelper collectionHelper) {
        String modelContent="";

        if (key=="English") {
            modelContent+= "English language bigram model: \n\n";
            for (int k = 0; k < CHAR_SET_SIZE; k++)
                modelContent+="       "+(char) (k + 97);
            modelContent+="      Total";
            modelContent+="\n";
            for(int i = 0; i < CHAR_SET_SIZE; i++) {
                modelContent+=(char) (i + 97);
                for (int j = 0; j < CHAR_SET_SIZE; j++) {
                    modelContent +=String.format("%8.1f",(bigrams[i][j]+SMOOTHING));
                }
                modelContent += "   "+ String.format("%7.1f",(bigramTotals[i] + (SMOOTHING * CHAR_SET_SIZE)));
                modelContent +="\n";
            }
            modelContent +="\n";
            char char1, char2;
            double prob;
            for(int i = 0; i < CHAR_SET_SIZE; i++) {
                for (int j = 0; j < CHAR_SET_SIZE; j++) {
                    char1 = (char) (i + 97);
                    char2 = (char) (j + 97);
                    prob = probability(char1, char2);
                    modelContent += "P(" + char1 + "|" + char2 + ") = " + prob;
                    modelContent +="\n";
                }
            }
            collectionHelper.writeToFile(modelContent, "bigramEN");
        }
        if (key=="French") {
            modelContent+= "French language bigram model: \n\n";
            for (int k = 0; k < CHAR_SET_SIZE; k++)
                modelContent+="       "+(char) (k + 97);
            modelContent+="      Total";
            modelContent+="\n";
            for(int i = 0; i < CHAR_SET_SIZE; i++) {
                modelContent+=(char) (i + 97);
                for (int j = 0; j < CHAR_SET_SIZE; j++) {
                    modelContent +=String.format("%8.1f",(bigrams[i][j]+SMOOTHING));
                }
                modelContent += "   "+ String.format("%7.1f",(bigramTotals[i] + (SMOOTHING * CHAR_SET_SIZE)));
                modelContent +="\n";
            }
            modelContent +="\n";
            char char1, char2;
            double prob;
            for(int i = 0; i < CHAR_SET_SIZE; i++) {
                for (int j = 0; j < CHAR_SET_SIZE; j++) {
                    char1 = (char) (i + 97);
                    char2 = (char) (j + 97);
                    prob = probability(char1, char2);
                    modelContent += "P(" + char1 + "|" + char2 + ") = " + prob;
                    modelContent +="\n";
                }
            }
            collectionHelper.writeToFile(modelContent, "bigramFR");
        }

        if (key=="German") {
            modelContent+= "German language bigram model: \n\n";
            for (int k = 0; k < CHAR_SET_SIZE; k++)
                modelContent+="       "+(char) (k + 97);
            modelContent+="      Total";
            modelContent+="\n";
            for(int i = 0; i < CHAR_SET_SIZE; i++) {
                modelContent+=(char) (i + 97);
                for (int j = 0; j < CHAR_SET_SIZE; j++) {
                    modelContent +=String.format("%8.1f",(bigrams[i][j]+SMOOTHING));
                }
                modelContent += "   "+ String.format("%7.1f",(bigramTotals[i] + (SMOOTHING * CHAR_SET_SIZE)));
                modelContent +="\n";
            }
            modelContent +="\n";
            char char1, char2;
            double prob;
            for(int i = 0; i < CHAR_SET_SIZE; i++) {
                for (int j = 0; j < CHAR_SET_SIZE; j++) {
                    char1 = (char) (i + 97);
                    char2 = (char) (j + 97);
                    prob = probability(char1, char2);
                    modelContent += "P(" + char1 + "|" + char2 + ") = " + prob;
                    modelContent +="\n";
                }
            }
            collectionHelper.writeToFile(modelContent, "bigramGE");
        }

    }
}
