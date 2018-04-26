package assignment7;

public class Node implements Comparable {

    protected int firstFile;
    protected int secondFile;
    protected int commonChunks;

    public Node (int firstFile, int secondFile, int commonChunks) {
        this.firstFile = firstFile;
        this.secondFile = secondFile;
        this.commonChunks = commonChunks;
    }

    @Override
    public int compareTo(Object o) {
        Node other = (Node) o;
        if (this.commonChunks > other.commonChunks)
            return 1;
        if (other.commonChunks > this.commonChunks)
            return -1;
        return 0;
    }


    public boolean equals(Object o) {
        Node other = (Node) o;
        return (this.commonChunks ==  other.commonChunks);
    }
}
