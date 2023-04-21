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
            if (name.equals("co0kies")) {
                name = "cookies";
            }
            if (!finalMap.containsKey(name)) {
                Map<String, Integer> priceMap = new HashMap<>();
                priceMap.put(price, 1);
                finalMap.put(name, priceMap);
            } else {            //(finalMap.get(name).containsKey(price)){
                Map<String, Integer> priceMap = finalMap.get(name);
                if (!priceMap.containsKey(price)) {
                    priceMap.put(price, 1);
                } else {
                    priceMap.put(price, priceMap.get(price) + 1);
                }
            }
            if (name.length() == 0 || price.length() == 0) { // !finalMap.get(name).containsKey(price)){
                errorCount++;
            }
        }
        return finalMap;
    }
    public static void printMap(Map<String, Map<String, Integer>> input){
        StringBuilder sb1 = new StringBuilder();

        for (String name : input.keySet()) {
            int count = 0;
            Map<String, Integer> priceMap = input.get(name);
            StringBuilder sb2 = new StringBuilder();
            for (String price : priceMap.keySet()) {
                count += priceMap.get(price);
                sb2.append("Price: " + price + "        seen: " + priceMap.get(price) + " times\n");
                sb2.append("-------------      -------------\n");
            }
            sb1.append("\nname: " + name + "          seen: " + priceMap.values().stream().mapToInt(Integer::intValue).sum() + " times\n");
            sb1.append("=============       =============\n")
                    .append(sb2);
        }
        System.out.println(sb1);
        System.out.println("Errors           seen: " + errorCount + " times");
    }

    public static void main(String[] args) throws Exception{
        String output = (new Main()).readRawDataToString();
        Map<String, Map<String,Integer>> mappy = splitToMap(output);
        printMap(mappy);
    }
}
