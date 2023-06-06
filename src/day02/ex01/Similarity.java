package day02.ex01;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class Similarity {
    private static final String DICTIONARY_PATH = "src/day02/ex01/dictionary.txt";
    private final List<String> firstText;
    private final List<String> secondText;
    private final Set<String> dictionary;

    public Similarity(String file1, String file2) {
        this.firstText = fileToArray(file1);
        this.secondText = fileToArray(file2);
        this.dictionary = new HashSet<>();
        createDictionary(firstText);
        createDictionary(secondText);
    }

    public void createDictionaryFile() {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(DICTIONARY_PATH))) {
            for (String word : dictionary) {
                fileWriter.write(word);
                fileWriter.newLine();
            }
        } catch (Exception e) {
            System.err.println(e);
            System.exit(-1);
        }
    }

    public double calculateSimilarity() {
        int[] firstVector = new int[dictionary.size()];
        int[] secondVector = new int[dictionary.size()];
        int i = 0;
        for (String word : dictionary) {
            firstVector[i] = Collections.frequency(firstText, word);
            secondVector[i] = Collections.frequency(secondText, word);
            ++i;
        }
        double numerator = getNumerator(firstVector, secondVector);
        double denominator = getDenominator(firstVector, secondVector);
        return (double)((int)(numerator / denominator * 100)) / 100;
    }

    private double getDenominator(int[] firstVector, int[] secondVector) {
        double x = getSqrt(getNumerator(firstVector, firstVector));
        double y = getSqrt(getNumerator(secondVector, secondVector));
        return x * y;
    }

    private double getNumerator(int[] firstVector, int[] secondVector) {
        double res = 0;
        for (int i = 0; i < firstVector.length; i++) {
            res += firstVector[i] * secondVector[i];
        }
        return res;
    }

    private List<String> fileToArray(String file) {
        List<String> res = new ArrayList<>();
        try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
            String strTmp;
            while ((strTmp = fileReader.readLine()) != null) {
                String[] arrTmp = strTmp.replaceAll("\\p{Punct}", "").
                        split(" ");
                for (int i = 0; i < arrTmp.length; i++) {
                    res.add(arrTmp[i]);
                }
            }
        } catch (Exception e) {
            System.err.println(e);
            System.exit(-1);
        }
        return res;
    }

    private void createDictionary(List<String> arr) {
        for (String str : arr) {
            dictionary.add(str);
        }
    }

    public static double getSqrt(Double x) {
        double y = x;
        double z = (y + (x / y)) / 2;

        while (getAbs(y - z) >= 0.1) {
            y = z;
            z = (y + (x / y)) / 2;
        }
        return z;
    }

    public static double getAbs(double x) {
        return (x < 0.0) ? -x : x;
    }
}
