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
    int errors = 0;

    public String readRawDataToString() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        String result = IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
        return result;
    }
    public static  Map<String, Map<String,Integer>> splitToMap(String input) { // Split and use regex to add item to map
        Map<String,Map<String,Integer>> finalMap = new HashMap<>();
        int errorCount = 0;
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

    public static void main(String[] args) throws Exception{
        String output = (new Main()).readRawDataToString();
        Map<String, Map<String,Integer>> mappy = splitToMap(output);
        printMap(mappy);
    }
}
