import java.io.IOException;
import java.util.Hashtable;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;


public class BigramDetermineLanguage {

    // The list of hashtable keys
    public ArrayList<String> keys;

    // The hashtable of bigrams
    public Hashtable<String, Bigram> bigrams;

    public BigramDetermineLanguage() {
        keys = new ArrayList<String>();
        bigrams = new Hashtable<String, Bigram>();
    }

    // Records a bigram hashtable key for later use
    public void addKey(String key) {
        keys.add(key);
    }

    //Evaluates a string and decides the langauge of the string
    public String evaluate(String s) {
        String fileContent="";
        // create total holders for each language
        double totals[] = new double[keys.size()];
        Bigram bigrams[] = new Bigram[keys.size()];
        for(int i = 0; i < keys.size(); i++) {
            totals[i] = 0;
            bigrams[i] = this.bigrams.get(keys.get(i));
        }

        // Give a fileContent/string and get an array list of lower case characters
        CollectionHelper collectionHelper = new CollectionHelper();
        ArrayList<Character> charArrayList = collectionHelper.stringerToChar(s);

        // iterate through the tokens
        fileContent += "\n---------------- \nBIGRAM MODEL: \n\n";
        for(int i = 0; i < charArrayList.size() - 1; i++) {
            char char1 = charArrayList.get(i);
            char char2 = charArrayList.get(i+1);

            if(char1 != ' ' && char2 != ' ') {
                fileContent +="BIGRAM: " + char1 + char2+" \n";
                // evaluation of probability for output file
                for(int j = 0; j < bigrams.length; j++) {
                    double prob = bigrams[j].probability(char1,  char2);
                    totals[j] += Math.log10(prob);
                    fileContent += keys.get(j).toUpperCase() + ": P(" + char1 + "|" + char2 + ") = " + prob + " ==> log prob of sentence so far: " + totals[j];
                    fileContent +=" \n";
                }
                fileContent +=" \n";
            }
        }
        // choose the outcome
        double max = -Double.MAX_VALUE;
        int maxIndex = -1;
        for(int i = 0; i < totals.length; i++) {
            if(totals[i] > max) {
                max = totals[i];
                maxIndex = i;
            }
        }
        fileContent +="According to the bigram model, the sentence is in " + keys.get(maxIndex);
        System.out.println("According to the bigram model, the sentence is in " + keys.get(maxIndex)+"\n");
        return fileContent;
    }


    //Trains one of the languages
    public void train(String key, File file) throws FileNotFoundException, IOException {
        // If the bigram doesn't exist, create it
        if(!bigrams.containsKey(key)) {
            bigrams.put(key, new Bigram(file, key));
            addKey(key);
        }
        // If the bigram does exist, update the corpus
        else
            bigrams.get(key).train(file, key);
    }
}

