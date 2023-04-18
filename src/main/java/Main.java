import org.apache.commons.io.IOUtils;

import javax.sound.midi.SysexMessage;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    static int errorCount = 0;

    public String readRawDataToString() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        String result = IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
        return result;
    }
    public static  Map<String, Map<String,Integer>> splitToMap(String input) { // Split and use regex to add item to map
        Map<String,Map<String,Integer>> finalMap = new LinkedHashMap<>();
        Pattern regex = Pattern.compile("\\bnaMe:([^\\s;]+);price:([^\\s;]+);", Pattern.CASE_INSENSITIVE);
        Matcher matcher = regex.matcher(input);
        while (matcher.find()) {
            String name = matcher.group(1).toLowerCase();
            String price = matcher.group(2);
            if (!finalMap.containsKey(name)) {
                Map<String, Integer> priceMap = new HashMap<>();
                priceMap.put(price, 1);
                finalMap.put(name, priceMap);
            } else if (name.equals("") || price == null) { // !finalMap.get(name).containsKey(price)){
                errorCount++;
            } else {            //(finalMap.get(name).containsKey(price)){
                Map<String, Integer> priceMap = finalMap.get(name);
                if (!priceMap.containsKey(price)) {
                    priceMap.put(price, 1);
                } else {
                    priceMap.put(price, priceMap.get(price) + 1);
                }
            }
        }
        return finalMap;
    }
    public static void printMap(Map<String, Map<String, Integer>> input){
        for (String name : input.keySet()) {
            System.out.println("name: " + name + "          seen: " + input.get(name).entrySet().size() + " times");
            System.out.println("=============          =============");
            Map<String, Integer> priceMap = input.get(name);
            for (String price : priceMap.keySet()) {
                System.out.println("Price: " + price + "        seen: " + priceMap.get(price) + " times");
                System.out.println("-------------         -------------");
            }
        }
        System.out.println(errorCount);
    }

    public static void main(String[] args) throws Exception{
        String output = (new Main()).readRawDataToString();
        Map<String, Map<String,Integer>> mappy = splitToMap(output);
        printMap(mappy);
    }
}
