import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
/* This application is prepared for calculate the most used words for each whatsapp user in a group.
 * Input file must be a text file of whatsapp chat history which is exported by email. 
 * Here is a link that explain how to export chat history. 
 * https://www.whatsapp.com/faq/en/android/23756533
 * 
 * To do:
 * 	- Integrated for Turkish language, should be modified for English
 * 	- For future development, application should be implemented as object oriented
 * 	- A graphical user interface may be added
 * 	- Shows the words created by Whatsapp as if user's word (sound file is not added etc.)
 * 	- 
 * */
public class main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	//We do not want to add system words to dictionary. So we need some control Strings to check if the word is created by the system or not
	public static final String PICTURE_CONTROL = "dahil edilmedi>"; 
	//Since characters spesified for Turkish, they cannot be (?) typed in compiler,
	//I decided the check a substring of it which does not contain Turkish characters
	
	public static final String SOUND_CONTROL="";
	//
	public static void main(String[] args) throws IOException 
	{
		  FileInputStream fstream = new FileInputStream("C:\\test.txt");
		  // Exported chat history file is added to FileInputStream object. 
		  DataInputStream in = new DataInputStream(fstream);
		  BufferedReader br = new BufferedReader(new InputStreamReader(in));
		  String strLine;
		  String name;
		  HashMap<String, HashMap<String,Integer>> dict;
		  //The data structure that is used for counting words is HashMap.
		  //First parameter of HashMap is represent the name of a person.
		  //Second parameter of HashMap is also a HashMap which represent a word and count of the word
		  //Here is a example of it
		  // <"John", <"I",1>
		  //	    , <"am",1>
		  //	    , <"testing",1>>
		  // <"Jack", <"demo",2>>
		  dict= new HashMap<String, HashMap<String,Integer>>();
		  
		  StringBuffer lineBuffer=new StringBuffer();
		  //Since string manipulation is needed StringBuffer is used instead of String
		  
		  
		  
		  String token;
		  while ((strLine = br.readLine()) != null)   {
			  lineBuffer.append(strLine);
			  System.out.println(strLine);
			  lineBuffer.delete(0,lineBuffer.indexOf(":")+1);
			  lineBuffer.delete(0,lineBuffer.indexOf(":")+1);
			  lineBuffer.delete(0,lineBuffer.indexOf(":")+2);
			  //Find an easy way
			  if(lineBuffer.indexOf(":")==-1)
			  {
				  br.readLine();// \n\r 
				  continue;
			  }
			  name =lineBuffer.substring(0, lineBuffer.indexOf(":"));
			  //name 
			 if(!dict.containsKey(name)) //dictionary does not contain name
			 {
				dict.put(name, new HashMap<String,Integer>());
			 }
			 
			 
			  lineBuffer.delete(0, lineBuffer.indexOf(":")+2);
			  System.out.println(lineBuffer.length()>=PICTURE_CONTROL.length());
			  if(lineBuffer.length()>=PICTURE_CONTROL.length()+10&&lineBuffer.substring(10,10+PICTURE_CONTROL.length()).toString().equals(PICTURE_CONTROL))
			  {
				  br.readLine();// \n\r
				  continue;
			  }
			  StringTokenizer strToken = new StringTokenizer(lineBuffer.toString()," ");
			  
			  while(strToken.countTokens()!=0)// for(int i=0;i<strToken.countTokens();i++) hata !
			  {
				  token = strToken.nextToken().toLowerCase();
				  if(!(dict.get(name).containsKey(token)))
				  {
					  dict.get(name).put(token, 0);
				  }
				  dict.get(name).put(token,dict.get(name).get(token)+1);
			  }
			  strToken = null;
			  strLine = null;
			  lineBuffer.delete(0,lineBuffer.length());
			  
			  br.readLine();// \n\r 
		  }
		  //http://stackoverflow.com/questions/5176771/sort-hashtable-by-values
		 
		  String most="";
		  Set<String> keyMap = dict.keySet();
		  for(String keyName: keyMap){
			 // Set<String> words = dict.get(keyName).keySet();
			  int num=0;
			  System.out.println(keyName);
			  for(int i=0;i<40;i++)
			  {
				 Set<String> keYwords = dict.get(keyName).keySet();
				  for(String word:keYwords)
				  {
					  if(dict.get(keyName).get(word)>num)
					  {
						  num=dict.get(keyName).get(word);
						  most=word;
					  }
				  }
				  System.out.print("   ");
				  System.out.print(most);
				  System.out.print(" ");
				  System.out.println(num);
				  dict.get(keyName).put(most,0);
				  num=0;//bug !!!!!
			  }
	       }
		  
	}

}
