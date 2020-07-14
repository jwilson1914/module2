import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
	public class Jdbcdemo
	{  
public static void main(String[] args)
{
int choice;
//Word Storing Section.
//A do while loop to take multiple words from the user.
do{
//A scanner object to take input word from user.
Scanner in = new Scanner(System.in);
//Try catch to handle checked exception like sqlexception and classnotfound exception.
try
{
//Asking user to enter a word.
System.out.println("Enter a word:");
String word = in.next();
//Loading the jdbc driver.
Class.forName("com.mysql.jdbc.Driver");
//Establishing Connection.
Connection con = DriverManager.getConnection("jdbc:mysql://localhost/wordoccurences","root","123456");
//Creating statement.
PreparedStatement ps = (PreparedStatement) con.prepareStatement("insert into word (words) values(?)");
//Setting the word entered by the user in the query.
ps.setString(1,word);
//Executing the query.
ps.executeUpdate();
//Showing the database table word after addition of the user word.
System.out.println("Database after addition of your word");
Statement st = con.createStatement();
ResultSet rs = st.executeQuery("select * from word");
while(rs.next()){
System.out.println(rs.getString(1));
}

con.close();
}
catch(ClassNotFoundException |SQLException c){
System.out.println(c.getMessage());
}
System.out.println("Do you want to enter more word(1 for yes)");
choice = in.nextInt();
}while(choice==1);
//Calculating Word Frequency Section.
System.out.println("Frequency of each word present in the database is:");
//Creating a map to store the word and corresponding frequency.
Map<String,Integer> frequency = new LinkedHashMap<>();
//Same as word storing section accessing the database and get the resultset.
try{
Class.forName("com.mysql.jdbc.Driver");
Connection con = DriverManager.getConnection("jdbc:mysql://localhost/wordoccurences","root","123456");
Statement st = con.createStatement();
ResultSet rs = st.executeQuery("Select * from word");
while(rs.next()){
//Getting the word from the database.
String s = rs.getString(1);
//Checking if the word already present in map or not.
if(frequency.get(s)==null){
//If not present then adding the word with frequency 1 in the map.
frequency.put(s,1);
}
else{
//Else increasing the frequency if it's already present in the map.
frequency.put(s,frequency.get(s)+1);
}
}
//Closing the database connection.
con.close();
}
catch(ClassNotFoundException | SQLException s){
System.out.println(s.getMessage());
}
//Printing the words with it's frequency.
Set<String> key = frequency.keySet();
for(String k:key){
System.out.println("Word: "+k+" frequency: "+frequency.get(k));
}
}
}

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