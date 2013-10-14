package models;

import org.junit.Test;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class NGramModelTest {
    @Test
    public void calculate_bigram_model(){
        ArrayList<InputData> arrayList = getArrayOfWords();

        InputData[] objects = new InputData[arrayList.size()];
        objects = arrayList.toArray(objects);
        NGramModel nGramModel = new NGramModel(objects);


        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        assertThat(decimalFormat.format(nGramModel.getTokenToProbability().get(new InputData("i", "do", "not"))), is("1"));
        assertThat(decimalFormat.format(nGramModel.getTokenToProbability().get(new InputData("i", "am","a"))), is("0.5"));
    }

    private ArrayList<InputData> getArrayOfWords() {
        String paragraph = "I am Prithvi.I am a very good boy.I do not like green eggs and ham";
        String[] sentences = paragraph.split("\\.");

        ArrayList<InputData> arrayList = new ArrayList<InputData>();
        for (String sentence : sentences) {
            sentence = sentence.toLowerCase();
            String[] words = sentence.split(" ");
            for (int i=0; i< words.length-2; i++) {
                arrayList.add(new InputData(words[i],words[i+1],words[i+2]));
            }
            arrayList.add(new InputData("<s>",words[0],words[1]));
            arrayList.add(new InputData(words[words.length-2],words[words.length-1],"</s>"));
        }
        return arrayList;
    }

    @Test
    public void find_possible_next_word_based_on_bigram_model() throws Exception {
        ArrayList<InputData> arrayList = getArrayOfWords();

        InputData[] objects = new InputData[arrayList.size()];
        objects = arrayList.toArray(objects);
        NGramModel nGramModel = new NGramModel(objects);

        assertThat((String) nGramModel.findNextPossibleWord(new InputData("i","do")),is("not"));
        assertThat((String) nGramModel.findNextPossibleWord(new InputData("<s>","i")),is("am"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void throw_illegal_argument_exception_for_incorrect_input() throws Exception {
        ArrayList<InputData> arrayList = getArrayOfWords();

        InputData[] objects = new InputData[arrayList.size()];
        objects = arrayList.toArray(objects);
        NGramModel nGramModel = new NGramModel(objects);

        assertThat((String) nGramModel.findNextPossibleWord(new InputData("i")),is("am"));
    }

    @Test
    public void get_which_gram_model_is_initialised(){
        ArrayList<InputData> arrayList = getArrayOfWords();

        InputData[] objects = new InputData[arrayList.size()];
        objects = arrayList.toArray(objects);
        NGramModel nGramModel = new NGramModel(objects);

        assertThat(nGramModel.getWhichGramModle(),is(objects[0].getInputs().length));
    }
}
