import java.awt.Button;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javafx.scene.Scene;
import javafx.stage.Stage;

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

do{

Scanner in = new Scanner(System.in);

try
{
	
	
		Class.forName("com.mysql.jdbc.Driver");
	
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/wordoccurences","root","123456");
	
		PreparedStatement ps = (PreparedStatement) con.prepareStatement("insert into word (words) values(?)");
		
		String word;
		ps.setString(1,word);
		
		ps.executeUpdate();
		
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
		
		System.out.println("Frequency of each word present in the database is:");
		
		Map<String,Integer> frequency = new LinkedHashMap<>();
		
			try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/wordoccurences","root","123456");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("Select * from word");
		while(rs.next()){
	
		String s = rs.getString(1);
	
		if(frequency.get(s)==null){
		
		frequency.put(s,1);
		}
		else{
		
		frequency.put(s,frequency.get(s)+1);
		}
		}
		con.close();
		}
		catch(ClassNotFoundException | SQLException s){
		System.out.println(s.getMessage());
		}
		
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

  
        String text = " " ;
        		Elements poemElements = doc.select("div.poem"); 
        		Elements poemElements1  = doc.getElementsByClass("poem"); 
        		for (org.jsoup.nodes.Element thisElement : poemElements1) {
        		   text = text + thisElement; 
        }
        	
        	

        String test = "eo21jüdjüqw=realString";
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

        SortedSet<Word> wordsToShow = new TreeSet<Word>(countMap.values());
        int i = 0;
        int maxWordsToDisplay = 20;

        String[] wordsToIgnore = {"the", "and", "a", "", "x","p","I","br","i"};

        for (Word word : wordsToShow) {
            if (i >= maxWordsToDisplay) {
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
   
