package assignment7;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.util.*;

public class Cheaters {


    public static void main(String[] args) {

        Map<String, List<Integer>> map = new HashMap<>();
        Map<Integer, String> numToFile = new HashMap<>();
        int fileNum = 0;

        int sizeOfChunk = Integer.parseInt(args[1]);
        final File folder = new File(args[0]);
        List<File> files = listFilesForFolder(folder);

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

                // Add to map. Key is the chunk. List of filenames is the value.
                String chunk = s.toString();
                List<Integer> names = map.get(chunk);
                if (names == null) {
                    names = new LinkedList<Integer>();
                    map.put(chunk, names);
                }
                names.add(fileNum);

            }
            numToFile.put(fileNum, file.getName()); // Map the file number to the filename for output purposes
            fileNum++;
        }

        // Creating the matrix
        int matrix[][] = new int[files.size()][files.size()];

        for (String key : map.keySet()) {
            List<Integer> listOfFiles = map.get(key);
            for (int i = 0; i < listOfFiles.size(); i++) {
                for (int j = i + 1; j < listOfFiles.size(); j++) {
                    matrix[listOfFiles.get(i)][listOfFiles.get(j)]++;
                }
            }
        }

        List<Node> nodes = new ArrayList<>();
        for (int row = 0; row < files.size(); row++) {
            for (int col = 0; col < files.size(); col++) {
                nodes.add(new Node(row, col, matrix[row][col]));
            }
        }

        Collections.sort(nodes); // Overriden compareTo method in the node class

        int index = 0;
        int bound = Integer.parseInt(args[2]);
        while(nodes.get(index).commonChunks > bound){
            Node temp = nodes.get(index);
            if(!(numToFile.get(temp.firstFile).equals(numToFile.get(temp.secondFile)))){
                System.out.println(temp.commonChunks + ": " + numToFile.get(temp.firstFile) + ", " + numToFile.get(temp.secondFile));
            }
            index++;
        }

    }

    private static List<File> listFilesForFolder(final File folder) {
        List<File> files = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            if (!fileEntry.getName().equals(".DS_Store"))
                files.add(fileEntry);
        }
        return files;
    }


}
