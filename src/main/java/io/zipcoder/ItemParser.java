package io.zipcoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemParser {
    HashMap<String,HashMap<Double,Integer>> itemMap = new HashMap<String, HashMap<Double, Integer>>();
    private int errorCount = 0;

    ItemParser(String rawData){
        ArrayList<String> outputSeparated = parseRawDataIntoStringArray(rawData);
        for(String str: outputSeparated){
            try {
                addItemToItemMap(parseStringIntoItem(str));
            }catch (ItemParseException exception){
                errorCount ++;
            }
        }
    }

    void addItemToItemMap(Item item){
        String name = item.getName().toLowerCase();
        if(!itemMap.containsKey(name)){
            HashMap<Double,Integer> startMap = new HashMap<Double, Integer>();
            startMap.put(item.getPrice(),1);
            itemMap.put(name,startMap);
        }else{
            HashMap<Double,Integer> priceMap = getNewPriceMap(itemMap.get(name),item.getPrice());
            itemMap.put(name,priceMap);
        }
    }

    private HashMap<Double,Integer> getNewPriceMap(HashMap<Double,Integer> priceMap , double priceToAdd){
        if(!priceMap.containsKey(priceToAdd)){
            priceMap.put(priceToAdd,1);
        }else{
            priceMap.put(priceToAdd,priceMap.get(priceToAdd)+1);
        }
        return priceMap;
    }


    ArrayList<String> parseRawDataIntoStringArray(String rawData){
        String stringPattern = "##";
        ArrayList<String> response = splitStringWithRegexPattern(stringPattern , rawData);
        return response;
    }

    Item parseStringIntoItem(String rawItem) throws ItemParseException{
        ArrayList<String> keyValuePairs = findKeyValuePairsInRawItemData(rawItem);
        String namePattern = "[nN][aA][mM][eE].[a-zA-Z].+";
        String name = formattingCheck(namePattern,keyValuePairs.get(0));
        String pricePattern = "[pP][rR][iI][cC][eE].(?:\\d*\\.)?\\d+.";
        String price = formattingCheck(pricePattern,keyValuePairs.get(1));
        String typePattern = "[tT][yY][pP][eE].[a-zA-Z].+";
        String type = formattingCheck(typePattern,keyValuePairs.get(2));
        String expirationPattern = "[eE][xX][pP][iI][rR][aA][tT][iI][oO][nN].\\d+/\\d+/\\d+";
        String expiration = formattingCheck(expirationPattern,keyValuePairs.get(3));

        return new Item(convertAllToLetters(name),Double.parseDouble(price),convertAllToLetters(type),expiration);

    }

    private String formattingCheck(String pattern, String keyValuePair) throws ItemParseException{
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(keyValuePair);
        String value;
        if(m.matches()){
            value = splitStringWithRegexPattern(":",keyValuePair).get(1).toLowerCase();
        }else{
            throw new ItemParseException();
        }
        return value;
    }

    String convertAllToLetters(String str){
        StringBuilder builder = new StringBuilder();
        for(int i = 0;i<str.length();i++){
            char currentChar = str.charAt(i);
            if(!Character.isLetter(currentChar)){
                currentChar = convertToLetter(currentChar);
            }
            builder.append(currentChar);
        }
        return builder.toString();
    }

    private char convertToLetter(char c){
        if(c == '0'){
            return 'o';
        }else if(c == '1'){
            return 'l';
        }
        return '?';
    }

    ArrayList<String> findKeyValuePairsInRawItemData(String rawItem){
        String stringPattern = "[;|^|*|!|@|%]";
        ArrayList<String> response = splitStringWithRegexPattern(stringPattern , rawItem);
        return response;
    }

    private ArrayList<String> splitStringWithRegexPattern(String stringPattern, String inputString){
        return new ArrayList<String>(Arrays.asList(inputString.split(stringPattern)));
    }


    public void showAll(){
        StringBuilder builder = new StringBuilder();
        for(Map.Entry<String,HashMap<Double,Integer>> entry : itemMap.entrySet()){
            int appearances = getAppearances(entry);
            builder.append("\nname:"+correctSpacing(entry.getKey(),8)+entry.getKey()+"\t\tseen: "+ appearances +timePluralCheck(appearances) +
                    "=============\t\t=============\n");
            for(Map.Entry<Double,Integer> entry2: entry.getValue().entrySet()){
                builder.append("Price:\t "+entry2.getKey()+"\t\tseen: "+entry2.getValue()+ timePluralCheck(entry2.getValue()) +
                        "-------------\t\t-------------\n");
            }
        }
        builder.append("\nErrors\t\t\t\tseen: "+errorCount+timePluralCheck(errorCount));
        System.out.println(builder.toString());
    }

    private String timePluralCheck(int num){
        return num == 1 ? "  time\n":" times\n";
    }

    private String correctSpacing(String str,int spaceNumber){
        StringBuilder spaces = new StringBuilder();
        for (int i=0;i<spaceNumber-str.length();i++){
            spaces.append(" ");
        }
        return spaces.toString();
    }

    private int getAppearances(Map.Entry<String,HashMap<Double,Integer>> entry){
        int counter = 0;
        for(Map.Entry<Double,Integer> price : entry.getValue().entrySet()){
            counter += price.getValue();
        }
        return counter;
    }

}
