package io.zipcoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyItemParser {
    int errorCount = 0;
    ArrayList<String> stringList = new ArrayList<String>();

    HashMap<String,HashMap<Double,Integer>> itemMap = new HashMap<String, HashMap<Double, Integer>>();

    void parse(String input){
        StringBuilder builder = new StringBuilder();
        for(int i = 0;i<input.length();i++){
            char currentChar = input.charAt(i);
            if(currentChar != '#'){
                builder.append(currentChar);
            }else{
                i+=1;
                stringList.add(builder.toString());
                formattingCheck(builder.toString());
                builder = new StringBuilder();
            }
        }
    }

    void formattingCheck(String str) {
        if(match(str)){
            MyItem createdItem = createItem(str);
            addItemToItemMap(createdItem);
        }else{
            errorCount ++;
        }
    }

    boolean match(String str){
        Pattern p = Pattern.compile("[nN][aA][mM][eE].[a-zA-Z].+.[pP][rR][iI][cC][eE].(?:\\d*\\.)?\\d+." +
                "[tT][yY][pP][eE].[a-zA-Z].+.[eE][xX][pP][iI][rR][aA][tT][iI][oO][nN].\\d+/\\d+/\\d+");
        Matcher m = p.matcher(str);
        return m.matches();
    }


    MyItem createItem(String str){
        StringBuilder partString = new StringBuilder();
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i = 0; i < str.length(); i++) {
            char currentChar = str.charAt(i);
            if (separator(currentChar) || i == str.length() - 1) {
                arrayList.add(partString.toString());
                partString = new StringBuilder();
            } else {
                partString.append(currentChar);
            }
        }
        return new MyItem(arrayList.get(1),arrayList.get(3),arrayList.get(5),arrayList.get(7));
    }

    // Separators are anything but numbers letter and /(slash) or .(period)
    boolean separator(char c){
        int numMax = (int)'9';
        int upperCaseMin = (int)'A';
        int upperCaseMax = (int)'Z';
        int lowerCaseMin = (int)'a';
        int lowerCaseMax = (int)'z';
        int period = (int)'.';

        return ( c < period || c > numMax && c < upperCaseMin || c > upperCaseMax && c < lowerCaseMin || c > lowerCaseMax);
    }

    void addItemToItemMap(MyItem item){
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


    public String toString(){
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
        builder.append("\nErrors\t\t\t\tseen: "+errorCount+" times");
        return builder.toString();
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


