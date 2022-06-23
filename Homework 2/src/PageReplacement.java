import java.util.ArrayList;
import java.util.HashSet;

public class PageReplacement {
    static abstract class PRAlgorithms{
        int pageFaults = 0;
        int hits = 0;
        ArrayList<Character> pages = new ArrayList<>();
        int size;

        abstract void process(String order);

        public String toString(){
            return "Page Faults: "+pageFaults+" ; Hits: "+hits;
        }
    }

    static class FIFO extends PRAlgorithms{
        public FIFO(int size) {
            this.size = size;
        }

        void process(String order){
            for(char ch:order.toCharArray()){
                if(pages.contains(ch)){
                    hits++;
                }else{
                    pageFaults++;
                    pages.add(ch);

                    if(pages.size()>size){
                        pages.remove(0);
                    }
                }
            }
        }
    }

    static class LRU extends PRAlgorithms{
        public LRU(int size) {
            this.size = size;
        }

        void process(String order){
            char[] chars = order.toCharArray();

            for(int i=0;i<chars.length;i++){
                if(pages.contains(chars[i])){
                    hits++;
                }else{
                    pageFaults++;
                    pages.add(chars[i]);

                    if(pages.size()>size){
                        ArrayList<Character> leftPages = (ArrayList<Character>) pages.clone();

                        for(int j=i;j>=0;j--){
                            if(leftPages.size()==1){
                                break;
                            }

                            if(leftPages.contains(chars[j])){
                                leftPages.remove((Character) chars[j]);
                            }
                        }
                        pages.remove(leftPages.get(0));
                    }
                }
            }
        }
    }

    static class Optimal extends PRAlgorithms{
        public Optimal(int size) {
            this.size = size;
        }

        void process(String order){
            char[] chars = order.toCharArray();

            for(int i=0;i<chars.length;i++){
                if(pages.contains(chars[i])){
                    hits++;
                }else{
                    pageFaults++;
                    pages.add(chars[i]);

                    if(pages.size()>size){
                        ArrayList<Character> leftPages = (ArrayList<Character>) pages.clone();

                        for(int j=i;j<chars.length;j++){
                            if(leftPages.size()==1){
                                break;
                            }

                            if(leftPages.contains(chars[j])){
                                leftPages.remove((Character) chars[j]);
                            }
                        }
                        pages.remove(leftPages.get(0));
                    }
                }
            }
        }
    }

    static class SecondChange extends PRAlgorithms{
        HashSet<Character> secondChance = new HashSet<>();

        public SecondChange(int size) {
            this.size = size;
        }

        void process(String order){
            char[] chars = order.toCharArray();

            for (char aChar : chars) {
                if (pages.contains(aChar)) {
                    hits++;

                    secondChance.add(aChar);
                } else {
                    pageFaults++;

                    if (pages.size() == size) {
                        boolean removed = false;
                        while (!removed) {
                            if (secondChance.contains(aChar)) {
                                secondChance.remove(aChar);
                                char temp = pages.get(0);
                                pages.remove(0);
                                pages.add(temp);
                            } else {
                                char toBeRemoved = pages.get(0);
                                pages.remove(0);
                                secondChance.remove(toBeRemoved);
                                removed = true;
                            }
                        }
                    }
                    pages.add(aChar);
                }
            }
        }
    }

    public static void  main(String[] args){
        String order = "11352268762155514977";
        int size = 5;

        FIFO fifo = new FIFO(size);
        LRU lru = new LRU(size);
        Optimal optimal = new Optimal(size);
        SecondChange secondChange = new SecondChange(size);

        fifo.process(order);
        lru.process(order);
        optimal.process(order);
        secondChange.process(order);

        System.out.println("Order: "+order);
        System.out.println("Size: "+size);
        System.out.println("Algorithm: FIFO ; "+fifo);
        System.out.println("Algorithm: Least Recently Used ; "+lru);
        System.out.println("Algorithm: Optimal ; "+optimal);
        System.out.println("Algorithm: Second Change ; "+secondChange);
    }
}
