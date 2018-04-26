package assignment7;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.util.*;

public class Cheaters {

    static Scanner kb;

    public static void main(String[] args) {

        kb = new Scanner(System.in);
        String[] input = parse(kb);

        Map<String, List<Integer>> map = new HashMap<>();
        Map<Integer, String> numToFile = new HashMap<>();
        int fileNum = 0;

        int sizeOfChunk = Integer.parseInt(input[1]);
        final File folder = new File(input[0]);
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
                for (int temp = i; temp < i + sizeOfChunk-1; temp++) {
                    String tempWord = listOfAllWords.get(temp);
                    tempWord = tempWord.replaceAll("[^A-Za-z0-9]", "");
                    s.append(tempWord.toUpperCase());
                }

                // chunks.add(s.toString());
                String chunk = s.toString();
                List<Integer> names = map.get(chunk);
                if (names == null) {
                    names = new LinkedList<Integer>();
                    map.put(chunk, names);
                }
                names.add(fileNum);

            }
            numToFile.put(fileNum, file.getName());
            fileNum++;
        }

//        for (String key : map.keySet()) {
//            if (map.get(key).size() > 50)
//                System.out.println(key);
//        }

        int matrix[][] = new int[files.size()][files.size()];


        for (String key : map.keySet()) {
            List<Integer> listOfFiles = map.get(key);
            for (int i = 0; i < listOfFiles.size(); i++) {
                for (int j = i + 1; j < listOfFiles.size(); j++) {
                    matrix[listOfFiles.get(i)][listOfFiles.get(j)]++;
                }
            }
        }

        System.out.println();

        List<Node> nodes = new ArrayList<>();
        for (int row = 0; row < files.size(); row++) {
            for (int col = 0; col < files.size(); col++) {
                nodes.add(new Node(row, col, matrix[row][col]));
            }
        }

        Collections.sort(nodes);
        Collections.reverse(nodes);

        System.out.println();

        int index = 0;
        int bound = Integer.parseInt(input[2]);
        while(nodes.get(index).commonChunks > bound){
            Node temp = nodes.get(index);
            if(!(numToFile.get(temp.firstFile).equals(numToFile.get(temp.secondFile)))){
                System.out.println(numToFile.get(temp.firstFile) + " is similar to " + numToFile.get(temp.secondFile) + " in " + temp.commonChunks + " ways");
            }
            index++;
        }

    }


    public static String[] parse(Scanner keyboard) {
            String input = keyboard.nextLine();
            String split[] = input.split(" ");
            return split;
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
