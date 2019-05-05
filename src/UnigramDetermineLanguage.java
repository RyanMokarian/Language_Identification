import java.io.IOException;
import java.util.Hashtable;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;


public class UnigramDetermineLanguage {

    // The list of hashtable keys
    public ArrayList<String> keys;

    // The hashtable of unigrams
    public Hashtable<String, Unigram> unigrams;

    public UnigramDetermineLanguage() {
        keys = new ArrayList<String>();
        unigrams = new Hashtable<String, Unigram>();
    }

    // Records a unigram hashtable key for later use
    public void addKey(String key) {
        keys.add(key);
    }

    //Evaluates a string and decides the langauge of the string
    public String evaluate(String s) {
        String fileContent="";
        // create total holders for each language
        double totals[] = new double[keys.size()];
        Unigram unigrams[] = new Unigram[keys.size()];
        for(int i = 0; i < keys.size(); i++) {
            totals[i] = 0;
            unigrams[i] = this.unigrams.get(keys.get(i));
        }

        // Give a fileContent/string and get an array list of lower case characters without any space
        CollectionHelper collectionHelper = new CollectionHelper();
        ArrayList<Character> charArrayList = collectionHelper.stringerToChar(s);

        // iterate through the tokens
        fileContent += "UNIGRAM MODEL: \n\n";
        for(int i = 0; i < charArrayList.size(); i++) {
            char char1 = charArrayList.get(i);

            if(char1 != ' ') {
                fileContent +="UNIGRAM: " + char1+" \n";
                // evaluation of probability for output file
                for (int j = 0; j < unigrams.length; j++) {
                    double prob = unigrams[j].probability(char1);
                    totals[j] += Math.log10(prob);
                    fileContent += keys.get(j).toUpperCase() + ": P(" + char1 + ") = " + prob + " ==> log prob of sentence so far: " + totals[j];
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
        fileContent +="According to the unigram model, the sentence is in " + keys.get(maxIndex);
        System.out.println("According to the unigram model, the sentence is in " + keys.get(maxIndex));
        return fileContent;
    }


    //Trains one of the languages
    public void train(String key, File file) throws FileNotFoundException, IOException {
        // If the unigram doesn't exist, create it
        if(!unigrams.containsKey(key)) {
            unigrams.put(key, new Unigram(file, key));
            addKey(key);
        }
        // If the unigram does exist, update the corpus
        else
            unigrams.get(key).train(file, key);
    }
}

