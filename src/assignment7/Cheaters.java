package assignment7;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.util.*;

public class Cheaters {

    public static void main(String[] args) {

        Map<String, List<String>> map = new HashMap<>();

        int sizeOfChunk = 6;
        final File folder = new File("src/big_doc_set");
        List<File> files = listFilesForFolder(folder);
        System.out.println(files.size());

        ArrayList<String> chunks = new ArrayList<>();
        for (File file : files) {
            ArrayList<String> listOfAllWords = new ArrayList<>();
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(file));
                String word;
                while((word = br.readLine()) != null) {
                    Scanner s = new Scanner(word);
                    while (s.hasNext()) {
                        listOfAllWords.add(s.next());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (int i = 0; i < listOfAllWords.size()-5; i++) {
                StringBuilder s = new StringBuilder();
                for (int temp = i; temp < i + 5; temp++) {
                    String tempWord = listOfAllWords.get(temp);
                    tempWord = tempWord.replaceAll("[^A-Za-z0-9]", "");
                    s.append(tempWord.toUpperCase());
                }

                // chunks.add(s.toString());
                String chunk = s.toString();
                List<String> names = map.get(chunk);
                if (names == null) {
                    names = new LinkedList<String>();
                    map.put(chunk, names);
                }
                names.add(file.getName());

            }
        }

        for (String key : map.keySet()) {
            if (map.get(key).size() > 50)
                System.out.println(key);
        }

    }

    public static List<File> listFilesForFolder(final File folder) {
        List<File> files = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            files.add(fileEntry);
        }
        return files;
    }

    private static Set<String> posible2(String posLoc) {
        Set<String> result = new TreeSet<String>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(new File(posLoc)));
            String availalbe;
            while((availalbe = br.readLine()) != null) {
                result.add(availalbe);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

}
