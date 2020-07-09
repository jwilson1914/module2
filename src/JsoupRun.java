import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class JsoupRun{

   public static void main(String[] args) throws IOException {

        Map<String, Word> countMap = new HashMap<String, Word>();

        //connect to gutenburg poem website
        System.out.println("Scanning web page..");
        Document doc = Jsoup.connect("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm").get();

        //get the actual text from the page, excluding the HTML
        //String text = doc.body().text();String text = "" 
        String text = " " ;
        		Elements poemElements = doc.select("div.poem"); // THIS LINE OR THE NEXT ONE
        		Elements poemElements1  = doc.getElementsByClass("poem");  // THIS LINE OR THE PREV ONE NOT BOTH
        		for (org.jsoup.nodes.Element thisElement : poemElements1) {
        		   text = text + thisElement; // However you add more words to text variablee
        }

        String test = "eo21j�dj�qw=realString";
        test = test.replaceAll(".+=", "");
        System.out.println(test);

        System.out.println("Counting words...");
        //bufferedreader to count words
        BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8))));
        String line;

        Map<String, Long> counts = new HashMap<>();

        while ((line = reader.readLine()) != null) {

        String[] words = line.split("[\\s.;,?':!-()\"]+");

        for (String word : words) {

      word = word.trim();

       if (word.length() > 0) {
       if (counts.containsKey(word)) {

        	counts.put(word, counts.get(word) + 1);

        		} else {

        	counts.put(word, 1L);

        }

        }

        }

        }

        for (Map.Entry<String, Long> entry : counts.entrySet()) {
        System.out.println(entry.getKey() + ": " + entry.getValue());

          

        }

        reader.close();
        String line1;
        while ((line1 = reader.readLine()) != null) {
            String[] words = line1.split("[^A-ZÃ…Ã„Ã–a-zÃ¥Ã¤Ã¶]+");
           
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

        String[] wordsToIgnore = {"the", "and", "a", "", "x","p","I","br","i"};

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