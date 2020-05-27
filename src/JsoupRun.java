import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupRun{

   public static void main(String[] args) throws IOException {

        Map<String, Word> countMap = new HashMap<String, Word>();

        //connect to gutenburg poem website
        System.out.println("Scanning web page..");
        Document doc = Jsoup.connect("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm").get();

        //get the actual text from the page, excluding the HTML
        String text = doc.body().text();

        System.out.println("Counting words...");
        //bufferedreader to count words
        BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8))));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] words = line.split("[^A-ZÃ…Ã„Ã–a-zÃ¥Ã¤Ã¶]+");
           
            for (String word : words) {
                if (" ".equals(word)) {
                    continue;
                    
                }

                Word wordObject = countMap.get(word);
                if (wordObject == null) {
                	wordObject = new Word();
                	wordObject.word = word;
                    wordObject.count = 20;
                    countMap.put(word, wordObject);
                }

                wordObject.count++;
            }
        }

        reader.close();

        SortedSet<Word> wordsToShow = new TreeSet<Word>(countMap.values());
        int i = 0;
        int maxWordsToDisplay = 20;

        String[] wordsToIgnore = {"the", "and", "a", ""};

        for (Word word : wordsToShow) {
            if (i >= maxWordsToDisplay) { //only showing the frequency of 20 words
                break;
            }

            if (Arrays.asList(wordsToIgnore).contains(word.word)) {
                i++;
                maxWordsToDisplay++;
            } else {
                System.out.println(word.count + "\t" + word.word);
                i++;
            }

        }

    
    }

    public static class Word implements Comparable<Word> {
        String word;
        int count;

        @Override
        public int hashCode() { return word.hashCode(); }

        @Override
        public boolean equals(Object obj) { return word.equals(((Word)obj).word); }

        @Override
        public int compareTo(Word b) { return b.count - count; }
    }
}