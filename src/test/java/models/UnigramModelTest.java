package models;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class UnigramModelTest {

    @Test
    public void go_ahead_and_find_number_of_words(){
        String docText = "Some papers are good";
        String[] words = docText.split(" ");
        UnigramModel unigramModel = new UnigramModel(words);

        assertThat((String[]) unigramModel.getTokens(),is(words));
    }

    @Test
    public void handle_null_if_text_in_document_is_null(){
        UnigramModel unigramModel = new UnigramModel(null);

        assertNull(unigramModel.getTokens());
    }

    @Test
    public void find_probability_of_each_words(){
        String docText = "Some papers are good And other papers are really good good good";
        UnigramModel unigramModel = new UnigramModel(docText.split(" "));

        assertNotNull(unigramModel.getTokenToProbability().get("good"));
        assertThat((Double) unigramModel.getTokenToProbability().get("good"),is(4/12d));
    }

    @Test
    public void make_document_to_take_in_generic_argument(){
        Integer intArray[] = {1,2,3,4,1};

        UnigramModel unigramModel = new UnigramModel(intArray);

        assertThat((Double) unigramModel.getTokenToProbability().get(1),is(2/5d));
    }
}

