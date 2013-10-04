package models;

import java.util.HashMap;
import java.util.Map;

public class BigramModel {
    private final Map<InputData,Integer> tokenToOccurrence = new HashMap<InputData, Integer>();
    private final Map<InputData,Double> tokenToProbability = new HashMap<InputData, Double>();
    private InputData[] tokens;

    public Map<InputData, Double> getTokenToProbability() {
        return tokenToProbability;
    }

    public BigramModel(InputData[] tokens) {
        this.tokens = tokens;
        if( tokens != null ){
            for (InputData token : tokens) {
                Integer occurrence = tokenToOccurrence.get(token);
                tokenToOccurrence.put(token, occurrence == null ? 1 : ++occurrence);
            }

            for (InputData token : tokens) {
                if(!tokenToProbability.containsKey(token))
                    tokenToProbability.put(token, findProbability(token));
            }
        }

    }

    private double findProbability(InputData token) {
        int counter=0;
        for (InputData InputData : tokens) {
            if(InputData.getInputs()[0].equals(token.getInputs()[0]))
                counter++;
        }
        return (double) tokenToOccurrence.get(token)/ counter;
    }

    public Object findNextPossibleWord(Object previousWord) throws Exception {
        if(tokenToProbability.isEmpty())
            throw new Exception("Bigram model is not initialized");

        double probability=0;
        Object possibleWord=null;

        for (InputData InputData : tokenToProbability.keySet()) {
            if(InputData.getInputs()[0].equals(previousWord)){
                Double probabilityOfCurrentToken = tokenToProbability.get(InputData);
                if(probability < probabilityOfCurrentToken){
                    probability = probabilityOfCurrentToken;
                    possibleWord = InputData.getInputs()[1];
                }
            }

        }
        return possibleWord;
    }
}
