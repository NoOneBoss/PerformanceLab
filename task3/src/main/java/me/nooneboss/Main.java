package me.nooneboss;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static Gson gson = new Gson();

    public static void main(String[] args) throws IOException {
        String valuesPath = args[0];
        String testsPath = args[1];
        String reportPath = args[2];

        Map<Integer, String> valuesMap = readValues(valuesPath);
        List<Test> tests = readTests(testsPath);

        updateTestValues(tests, valuesMap);

        writeReport(reportPath, tests);
    }

    private static Map<Integer, String> readValues(String filePath) throws IOException {
        FileReader reader = new FileReader(filePath);
        Type valuesType = new TypeToken<Map<String, List<Map<String, Object>>>>() {}.getType();
        Map<String, List<Map<String, Object>>> valuesData = gson.fromJson(reader, valuesType);
        reader.close();

        Map<Integer, String> valuesMap = new HashMap<>();
        for (Map<String, Object> value : valuesData.get("values")) {
            int id = ((Double) value.get("id")).intValue();
            String status = (String) value.get("value");
            valuesMap.put(id, status);
        }

        return valuesMap;
    }

    private static List<Test> readTests(String filePath) throws IOException {
        FileReader reader = new FileReader(filePath);
        Type testsType = new TypeToken<Map<String, List<Test>>>() {}.getType();
        Map<String, List<Test>> testsData = gson.fromJson(reader, testsType);
        reader.close();

        return testsData.get("tests");
    }

    private static void updateTestValues(List<Test> tests, Map<Integer, String> valuesMap) {
        for (Test test : tests) {
            if (valuesMap.containsKey(test.id)) {
                test.value = valuesMap.get(test.id);
            }
            if (test.values != null) {
                updateTestValues(test.values, valuesMap);
            }
        }
    }

    private static void writeReport(String filePath, List<Test> tests) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        Map<String, List<Test>> reportData = new HashMap<>();

        reportData.put("tests", tests);
        gson = new GsonBuilder().setPrettyPrinting().create();
        gson.toJson(reportData, writer);

        writer.close();
    }
}

class Test {
    int id;
    String title;
    String value;
    List<Test> values;

    public Test(int id, String title, String value, List<Test> values) {
        this.id = id;
        this.title = title;
        this.value = value;
        this.values = values;
    }
}