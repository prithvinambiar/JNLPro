package models;

import java.util.HashMap;
import java.util.Map;

public class UnigramModel<$Token> {
    private final Map<$Token,Integer> tokenToOccurrence = new HashMap<$Token, Integer>();
    private final Map<$Token,Double> tokenToProbability = new HashMap<$Token, Double>();
    private $Token[] tokens;

    public Map<$Token, Double> getTokenToProbability() {
        return tokenToProbability;
    }

    public UnigramModel($Token[] tokens) {
        this.tokens = tokens;
        if( tokens != null ){
            for ($Token token : tokens) {
                Integer occurrence = tokenToOccurrence.get(token);
                tokenToOccurrence.put(token, occurrence == null ? 1 : ++occurrence);
            }

            for ($Token token : tokens) {
                if(!tokenToProbability.containsKey(token))
                    tokenToProbability.put(token, findProbability(token));
            }
        }

    }

    private double findProbability($Token token) {
        return (double) tokenToOccurrence.get(token)/ tokens.length;
    }

    public $Token[] getTokens() {
        return tokens;
    }
}
