package models;

import java.util.HashMap;
import java.util.Map;

public class NGramModel {
    private final Map<InputData,Integer> tokenToOccurrence = new HashMap<InputData, Integer>();
    private final Map<InputData,Double> tokenToProbability = new HashMap<InputData, Double>();
    private InputData[] tokens;

    public Map<InputData, Double> getTokenToProbability() {
        return tokenToProbability;
    }

    public NGramModel(InputData[] tokens) {
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
        for (InputData inputData : tokens) {
            if(allPreviousWordsMatch(token, inputData))
                counter++;
        }
        return (double) tokenToOccurrence.get(token)/ counter;
    }

    private boolean allPreviousWordsMatch(InputData token, InputData inputData) {
        boolean allTokenMatch = true;
        for( int i=0; i<inputData.getInputs().length-1 ; i++ ){
            if(! inputData.getInputs()[i].equals(token.getInputs()[i]) ){
                allTokenMatch = false;
                break;
            }
        }
        return allTokenMatch;
    }

    public Object findNextPossibleWord(InputData token) throws Exception {

        if(tokenToProbability.isEmpty())
            throw new Exception("Ngram model is not initialized");

        double probability=0;
        Object possibleWord=null;

        int gramModel = tokens[0].getInputs().length;
        if(token.getInputs().length != gramModel-1)
            throw new IllegalArgumentException("Incorrect input data");

        for (InputData inputData : tokenToProbability.keySet()) {
            if(allPreviousWordsMatch(token,inputData)){
                Double probabilityOfCurrentToken = tokenToProbability.get(inputData);
                if(probability < probabilityOfCurrentToken){
                    probability = probabilityOfCurrentToken;
                    possibleWord = inputData.getInputs()[inputData.getInputs().length-1];
                }
            }

        }
        return possibleWord;
    }

    public Integer getWhichGramModle() {
        return tokens[0].getInputs().length;
    }
}
