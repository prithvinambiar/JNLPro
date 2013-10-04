package models;

import org.junit.Test;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BigramModelTest {
    @Test
    public void calculate_bigram_model(){
        ArrayList<InputData> arrayList = getArrayOfWords();

        InputData[] objects = new InputData[arrayList.size()];
        objects = arrayList.toArray(objects);
        BigramModel bigramModel = new BigramModel(objects);


        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        assertThat(decimalFormat.format(bigramModel.getTokenToProbability().get(new InputData("i", "do"))), is("0.33"));
        assertThat(decimalFormat.format(bigramModel.getTokenToProbability().get(new InputData("sam", "</s>"))), is("0.5"));
        assertThat(decimalFormat.format(bigramModel.getTokenToProbability().get(new InputData("i", "do"))), is("0.33"));
    }

    private ArrayList<InputData> getArrayOfWords() {
        String paragraph = "I am Sam.Sam I am.I do not like green eggs and ham";
        String[] sentences = paragraph.split("\\.");

        ArrayList<InputData> arrayList = new ArrayList<InputData>();
        for (String sentence : sentences) {
            sentence = sentence.toLowerCase();
            String[] words = sentence.split(" ");
            for (int i=0; i< words.length-1; i++) {
                arrayList.add(new InputData(words[i],words[i+1]));
            }
            arrayList.add(new InputData("<s>",words[0]));
            arrayList.add(new InputData(words[words.length-1],"</s>"));
        }
        return arrayList;
    }

    @Test
    public void find_possible_next_word_based_on_bigram_model() throws Exception {
        ArrayList<InputData> arrayList = getArrayOfWords();

        InputData[] objects = new InputData[arrayList.size()];
        objects = arrayList.toArray(objects);
        BigramModel bigramModel = new BigramModel(objects);

        assertThat((String) bigramModel.findNextPossibleWord("i"),is("am"));
        assertThat((String) bigramModel.findNextPossibleWord("<s>"),is("i"));
    }
}
