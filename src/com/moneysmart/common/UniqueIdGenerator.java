package com.moneysmart.common;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.moneysmart.URLShortnerApplication;

public class UniqueIdGenerator {
    public static final UniqueIdGenerator uniqueIdGenerator = new UniqueIdGenerator();
    
    private static final String HASHING_STRING = URLShortnerApplication.properties.getProperty("UNIQUE_ID_KEY");

    private UniqueIdGenerator() {
    }
    
    
    public static void main(String[] args) {
		//System.out.println(createUniqueID(1000L));
    	System.out.println(uniqueIdGenerator.getDictionaryKeyFromUniqueID("qi"));
	}

    public String createUniqueID(Long id) {
        List<Integer> baseXID = convertBase10ToBaseXID(id);
        StringBuilder uniqueURLID = new StringBuilder();
        for (int digit: baseXID) {
            uniqueURLID.append(HASHING_STRING.charAt(digit));
        }
        return uniqueURLID.toString();
    }

    private List<Integer> convertBase10ToBaseXID(Long id) {
        List<Integer> digits = new LinkedList<>();
        while(id > 0) {
            int remainder = (int)(id % HASHING_STRING.length());
            ((LinkedList<Integer>) digits).addFirst(remainder);
            id /= HASHING_STRING.length();
        }
        return digits;
    }

    public Long getDictionaryKeyFromUniqueID(String uniqueID) {
        List<Character> baseXIDs = new ArrayList<>();
        for (int i = 0; i < uniqueID.length(); ++i) {
            baseXIDs.add(uniqueID.charAt(i));
        }
        Long dictionaryKey = convertBaseXToBase10ID(baseXIDs);
        return dictionaryKey;
    }

    private static Long convertBaseXToBase10ID(List<Character> ids) {
        long id = 0L;
        for (int i = 0, exp = ids.size() - 1; i < ids.size(); ++i, --exp) {
            int base10 = HASHING_STRING.indexOf(ids.get(i));
            id += (base10 * Math.pow(HASHING_STRING.length(), exp));
        }
        return id;
    }
}
