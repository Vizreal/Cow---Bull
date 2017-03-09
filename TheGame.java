import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class TheGame {
	private static final String FILE1 = ".\\word3.txt";
	private static final String FILE2 = ".\\word4.txt";
	private static final String FILE3 = ".\\word5.txt";
	private static final String FILERules = ".\\Rules.txt";
	private static int size ;
	private static int score ;
	private static int difSize ;
	private static char difficulty ;
	private static boolean success ;
	private static Scanner sc=new Scanner(System.in);  
	
	public TheGame(){
		score = 0;
	}
	
	private static String SetWord(String FILENAME){
		 	BufferedReader br = null;
			FileReader fr = null;
			int count=0;
			String chosen="";
			Random rand = new Random();

			int  n = rand.nextInt(size) + 1;

			try {
				fr = new FileReader(FILENAME);
				br = new BufferedReader(fr);
				String sCurrentLine;
				br = new BufferedReader(new FileReader(FILENAME));
				while ((sCurrentLine = br.readLine()) != null) {
					count=count+1;
					if (count==n)
						chosen=sCurrentLine;
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (br != null)
						br.close();
					if (fr != null)
						fr.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
				//System.out.print("Word is " + chosen);
			return chosen;
	}
	
	private static void Rules(){
		BufferedReader br = null;
		FileReader fr = null;

		try {
			fr = new FileReader(FILERules);
			br = new BufferedReader(fr);
			String sCurrentLine;
			br = new BufferedReader(new FileReader(FILERules));
			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	private static int Again(){
		System.out.println("\n\nWanna play again ? Y/N");	
		String str = sc.next();  
	    if (str.toLowerCase().charAt(0)=='y'){
	    	System.out.println("Wanna change difficulty ? Y/N");	
			str = sc.next();  
			if (str.toLowerCase().charAt(0)=='y'){
				SetDifficulty();
			}
			return 1;
	    }
		return 0;
	}
	
	private static void SetDifficulty(){
		System.out.println("\nChoose Difficulty");
		System.out.println("E for Easy");
		System.out.println("M for Medium");
		System.out.println("D for Difficult");
		
		String str = sc.next();  
	    difficulty = str.toLowerCase().charAt(0);  
	}
	
	private static String ChooseFile(){
		String word="";
		if ( difficulty == 'e'){
			difSize = 3;
			size = 500;
			word = SetWord(FILE1);
		}
		else if ( difficulty == 'm'){
			difSize = 4;
			size = 503;
			word = SetWord(FILE2);
		}
		else if ( difficulty == 'd' || difficulty == 'h'){
			difSize = 6;
			size = 500;
			word = SetWord(FILE3);
		}
		else{
			System.out.println("Invalid Difficulty Selected");
			SetDifficulty();
			ChooseFile();
		}
		return word;
	}
	
	private static void StartGame(){
		String word = ChooseFile(); 
	    for ( int i=0 ; i<10 ; i++){
	    	System.out.printf("\n\n(Trial - " + (i+1) + ") Enter the word - ");
			String str = sc.next();
	    	if (Compare(word,str.toLowerCase())){
	    		score = score + 10;
	    		System.out.println("					Score - " + score);
	    		success = true;
	    		break;
	    	}
	    }
	    if (!success){
			System.out.println("\nHAHAHAHA!!!! The word was - " + word);
		}
	}
	
	private static boolean Compare(String ans, String user){
		int bull=0;
		int cow=0;
		ans=filterAns(ans);
		user=filterInput(user);
		//System.out.println(difSize);
		for(int i=0;i<difSize;i++){
			//System.out.println("loop" + i);
			if (user.indexOf(ans.charAt(i)) != -1)
				cow=cow+1;
			if ( user.charAt(i) == ans.charAt(i) )
				bull=bull+1;
		} 
		cow=cow-bull;
		//System.out.print(cow + " " + bull);
		System.out.print("\t\t\t\t");
		for(int i=0;i<cow;i++){
			System.out.print("C");
		} 
		for(int i=cow;i<bull+cow;i++){
			System.out.print("B");
		}
		if (bull==difSize)
			return true;
		return false;
	}
	
	private static String filterAns(String word){
		int check = 0;
		int count = 0;
		while (check != 1){
			for(int i=0;i<difSize;i++){
				for ( int j=i+1;j<difSize;j++){
					if ( word.charAt(i) == word.charAt(j) ){
						count = count + 1;
					}
				}
			}
			if ( count > 0)
				word=ChooseFile();
			else
				check = 1;
		}
		return word;
	}
	
	private static String filterInput(String word){
		int check = 0;
		int count = 0;
		while (check != 1){
			for(int i=0;i<difSize;i++){
				for ( int j=i+1;j<difSize;j++){
					if ( word.charAt(i) == word.charAt(j) ){
						count = count + 1;
					}
				}
			}
			if ( word.length()!=difSize){
				System.out.print("Invalid input size!!! Enter the word again - ");
				word = sc.next();
			}
			else if ( count > 0){
				System.out.print("Letters cannot be repeated!!! Enter the word again - ");
				word = sc.next();
				count=0;
			}
			else
				check = 1;
		}
		return word;
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("\n\n\t\t*** COW - BULL ***\n\n");
		Rules();
		System.out.println("\n\n\t\t*** LET THE GAME BEGIN ***");
		SetDifficulty();
		StartGame();
		while(Again()==1){
			StartGame();
		}
		System.out.println("Your score is " + score);
		System.out.println("Thank you for playing!!!");
		sc.close();
		
	}
}
