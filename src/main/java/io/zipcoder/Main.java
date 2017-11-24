package io.zipcoder;

import org.apache.commons.io.IOUtils;

import java.util.ArrayList;


public class Main {

    public String readRawDataToString() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        String result = IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
        return result;
    }

    public static void main(String[] args) throws Exception{
        String output = (new Main()).readRawDataToString();
        System.out.println(output);

        MyItemParser differentParser = new MyItemParser();
        differentParser.parse(output);

        System.out.println(differentParser);

//        ItemParser itemParser = new ItemParser();
//        ArrayList<String> outputSeparated = itemParser.parseRawDataIntoStringArray(output);
//
//        for(String itemString: outputSeparated){
//            System.out.println(itemString);
//        }
        // TODO: parse the data in output into items, and display to console.
    }
}
