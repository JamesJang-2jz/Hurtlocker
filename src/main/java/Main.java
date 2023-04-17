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

    public String readRawDataToString() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        String result = IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
        return result;
    }
    public static  Map<String, Map<String,Integer>> splitToMap(String input) { // Split and use regex to add item to map
        Map<String,Map<String,Integer>> finalMap = new HashMap<>();
        int errorCount = 0;
//        ;price:([\d.]+);type:([^;]+);expiration:(\d{1,2}/\d{1,2}/\d{4})
        Pattern regex = Pattern.compile("\\b(\\w+):([^\\s;]+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = regex.matcher(input);
        while (matcher.find()){
            String name = matcher.group(1);
            String value = matcher.group(2);
            if (!finalMap.containsKey(name)){
                finalMap.put(name, new HashMap<>());
            }
            Map<String, Integer> priceMap = finalMap.get(name);
            if (!priceMap.containsKey(value)){
                priceMap.put(value, 0);
            }
            priceMap.put(value, priceMap.get(value) + 1);
            if ()
        }
        return finalMap;
    }
    public static void printMap(Map<String, Map<String, Integer>> input){
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        for (String name : input.keySet()) {
            Map<String, Integer> priceMap = input.get(name);
            for (String price : priceMap.keySet()) {
                int count = 0;
                sb1.append("name: " + name + "          seen: " + input.get(name).size() + " times\n");
                sb2.append("Price: " + price + "        seen: " + priceMap.get(price) + " times\n");
            }
            System.out.println(sb1);
            System.out.println("=============          =============");
            System.out.println(sb2);
            System.out.println("-------------         -------------");
        }

    }
//        Pattern regex = Pattern.compile(pattern);
//        // Create a map to store the grocery items and their counts
//        Map<String, Map<String, Integer>> groceryMap = new HashMap<>();
//
//        // Iterate through each item in the list
//        for (String item : itemList) {
//            Matcher matcher = regex.matcher(item);
//            if (matcher.find()) {
//                String name = matcher.group(1).toLowerCase();
//                String price = matcher.group(2);
//                // Add item to map or update its count
//                if (!groceryMap.containsKey(name)) {
//                    Map<String, Integer> priceMap = new HashMap<>();
//                    priceMap.put(price, 1);
//                    groceryMap.put(name, priceMap);
//                } else {
//                    Map<String, Integer> priceMap = groceryMap.get(name);
//                    if (!priceMap.containsKey(price)) {
//                        priceMap.put(price, 1);
//                    } else {
//                        int count = priceMap.get(price);
//                        priceMap.put(price, count + 1);
//                    }
//                }
//            } else {
//                // Handle any errors in input
//                if (!groceryMap.containsKey("Errors")) {
//                    Map<String, Integer> errorMap = new HashMap<>();
//                    errorMap.put("1", 1);
//                    groceryMap.put("Errors", errorMap);
//                } else {
//                    Map<String, Integer> errorMap = groceryMap.get("Errors");
//                    int count = errorMap.getOrDefault("1", 0);
//                    errorMap.put("1", count + 1);
//                }
//            }
//        }
//        // Print the grocery items and their counts
//        return result;
//    }
    public static void main(String[] args) throws Exception{
        String output = (new Main()).readRawDataToString();
        Map<String, Map<String,Integer>> mappy = splitToMap(output);
        printMap(mappy);
    }
}
