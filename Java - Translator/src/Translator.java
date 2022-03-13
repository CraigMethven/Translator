import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Class that takes plain text and separates it to test to be able to compare it to the nodes in the tree class
 * @author AJ && something to do with Craig
 *
 */
public class Translator {
    private String translatedSentence[];
    private String sentence[];
    private Tree gerwordTree;
    private Tree engwordTree;
    private Tree gerphraseTree;
    private Tree engphraseTree;
    private boolean skip = false;
    private boolean engOrGer;       //true = eng // false = ger


/**
 * constructor that sends the trees into the correct places
 * @param Tree1 gerword tree
 * @param Tree2 engword tree
 * @param Tree3 gerphrase tree
 * @param Tree4 engphrase tree
 */
   Translator(Tree Tree1, Tree Tree2, Tree Tree3, Tree Tree4) {
       gerwordTree = Tree1;
       engwordTree = Tree2;
       gerphraseTree = Tree3;
       engphraseTree = Tree4;
   }

    /**
     * when the menu chooses a language then set the language we need
     * @param engOrGer
     */
    public void setEngOrGer(boolean tempBoolean) {
        engOrGer = tempBoolean;
    }


    /**
     * take in the file containing the sentence to be translated
     * @param filename
     */
    public void takeInFile(String filename) {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        int i = 0;

        int sentenceSize = 0;
        //Normal file read but only being used to count the size of it so we can set the size of the array
		try {
			FileReader reader1 = new FileReader(filename);
			BufferedReader buffer1 = new BufferedReader(reader1); 
			String nextLine = buffer1.readLine();
			
			while (nextLine != null)
			{
				nextLine = buffer1.readLine();
				sentenceSize++;
			}
			buffer1.close();
		} catch (FileNotFoundException e) {

		} catch (IOException e) {
			
		}
		
		//Sets size of the array
		sentence = new String[sentenceSize];
        
		//Reads in the file again, this time saving it to the sentence array
        try {
            fileReader = new FileReader(filename);
            bufferedReader = new BufferedReader(fileReader);

            for(String nextLine = bufferedReader.readLine(); nextLine != null; nextLine = bufferedReader.readLine()) {
                sentence[i] = nextLine;
                i+= 1;
            }

            bufferedReader.close();
        } catch (IOException var4) {
            System.out.println("Error reading from file: " + var4);
        }
        //for if we're taking a sentence from a file and assigning it to a variable
        
        //Runs method to translate file that was just read
        SeparateString();
    }

    /**
     * Sends the output to a file
     */
    public void output() {
        Scanner s1 = new Scanner(System.in);
        FileOutputStream outputStream = null;
        PrintWriter printWriter = null;

        System.out.println("What is the name of the file you want to output the translation to? :-)");
        String outFilename = s1.nextLine();
        outFilename += ".txt";

        //Normal save to file with arrays
        try {
            outputStream = new FileOutputStream(outFilename);
            printWriter = new PrintWriter(outputStream);

            for(int i=0; i<translatedSentence.length; i++) {
                printWriter.println(translatedSentence[i]);
            }

            printWriter.close();
        }
        catch (IOException var6) {
            System.out.println("Error writing to file: " + var6);
        }
    }
    
    /**
     * prints the translated sentence to console
     */
    public void outputToScreen() {
    	for(int i=0; i<translatedSentence.length; i++) {
    		System.out.println(translatedSentence[i]);
        }
    }

    /**
     * checks to see if what is passed is in the phrase list
     * @param sentence the phrase being checked
     * @return tree if it is a phrase, false if not
     */
    public boolean checkIfPhrase(String sentence) {
            if (findPhraseTree().findItemStart(sentence) != null) {
                return true;
            } else {
                return false;
            }
    }

    /**
     * Translated a sentence given a sentence
     * @param word a sentence
     */
    public void translateSentence(String word) {
    	sentence = new String[1];
    	sentence[0] = word;
    	SeparateString();
    	outputToScreen();
    }
    
    /**
     * Takes in a string to translate
     */
    public void takeInString() {
        Scanner s1 = new Scanner(System.in);
        translateSentence(s1.nextLine());
        //taken from the user input (it was written by the user)
        //take the string from where/how ever the user enters their sentence
    }

    /**
     * separates the original sentence into an array which holds each word (and non word character), translates the word and saves it to the translated sentence array
     *
     */
    public void SeparateString() {
    	//Sets size of the translated sentence array to the size of the normal sentence array so they can store the same number of lines
    	translatedSentence = new String[sentence.length];
    	String[] wordsArray;
    	String[] groupOfWords;
    	//For each line in text being translated
        for(int j=0; j<sentence.length; j++) {
        	translatedSentence[j]="";
        	//Split up wherever there is a new sentence and saves in groupOfWords array
        	groupOfWords = sentence[j].split("\\.");
        	//for the length of the group of words array
        	for(int index = 0; index<groupOfWords.length; index++) {
        		//search to see if each sentence is in the phrases list and if so use that translation
        		String temp = null;
        		temp = searchPhrases(groupOfWords[index]);
        		if(temp!=null && temp!="") {
        			translatedSentence[j]+=temp;
        		}
        		//translate each word
        		else {
        			//Split up the words into the words array. They are being split by any non word character
        			wordsArray=null;
        			wordsArray = groupOfWords[index].split("((?<=\\W)|(?=\\W))");
        			//for each word in the array
        	        for (int i = 0; i < wordsArray.length; i++) {
        	        	//If word array is a non word character then it just adds it to the translated sentence
        	        	if (wordsArray[i] == "\\W") {
        	               	translatedSentence[j]+= wordsArray[i];
        	             } 
        	        	//else it translates the word  and adds it to the tree
        	           	else {
        	               	String tempString = null;
        	               	tempString = sendToTree(wordsArray[i]);
        	               	//if word doesn't have a translation ask if they want one and if so what it is.
        	               	if(tempString==null&&wordsArray[i]!=null&&wordsArray[i]!=""&&wordsArray[i]!=" ") {
        	               		if(skip==false) {
        		               		Scanner s1 = new Scanner(System.in);
        		               		System.out.println("Please enter the word that translates " + wordsArray[i] + " to German (enter -1 to skip)");
        		               		String newTrans = s1.nextLine();
        		               		if(newTrans.equals("-1")) {
        		               			skip = true;
        		               			translatedSentence[j]+=wordsArray[i];
        		               		}
        		               		else {
        		               			addWord(wordsArray[i], newTrans, 2);
        		               			translatedSentence[j]+=newTrans;
        		               		}
        	               		}
        	               		//If they don't want it translated then just add the normal word in its place
        	               		else {
        	               			translatedSentence[j]+=wordsArray[i];
        	               		}
        	               	}
        	               	else {
        	               		translatedSentence[j]+= sendToTree(wordsArray[i]);
        	               		}
        	               }
        	           }
        		}
        	}
        }
    }
    
    /**
     * searches the phrase tree and returns the translation if it has one
     * @param word the phrase
     * @return the translated phrase
     */
    public String searchPhrases(String word) {
    	String tempString = null;
    	tempString = findPhraseTree().findItemStart(word);
    	return tempString;
    }

    /**
     * finds the word from the sentence in one language tree and uses its index to find the translation in the other language tree
     *
     * @param word the word to be translated
     * @return the translated word
     */
    public String sendToTree(String word) {
    	String tempString = "";
    	tempString = findWordTree().findItemStart(word);
    	return tempString;
    	/* 
    	 * 
    	 *
            if (checkIfPhrase(word)) {

                String translation = findPhraseTree().findItemStart(word, findPhraseTree().getRoot());

                return translation;

            } else if(!checkIfPhrase(word)){

                String translation = findWordTree().findItemStart(word, findWordTree().getRoot());

                return translation;
            }
            else if(findPhraseTree().findItemStart(word, findPhraseTree().getRoot()) == null && findWordTree().findItemStart(word, findWordTree().getRoot()) == null) {
                String tempString = askTranslation(word);
                if(!tempString.equals(null)) {
                	return tempString;
                }
            }
        return "error";
        */
    }

    /**
     * returns the correct tree corresponding to if the user is translating from english to german or german to english
     * @return tree wanted
     */
    public Tree findWordTree(){
        if(engOrGer){
            return engwordTree;
        }
        else {
            return gerwordTree;
        }
    }

    /**
     * returns the correct tree corresponding to if the user is translating from english to german or german to english
     * @return tree wanted
     */
    public Tree findPhraseTree() {
        if(engOrGer){
            return engphraseTree;
        }
        else {
            return gerphraseTree;
        }
    }
    
    /**
     * Adds a word to the dictionary
     * @param engWord the english word
     * @param gerWord the german word
     * @param phrase if it is a phrase or word. 1 = phrase, 2 = word
     */
    public void addWord(String engWord, String gerWord, int phrase) {
	    if(phrase==1) {
	    	engphraseTree.addNode(engWord, gerWord);
	    	addWordToFile(engWord,engphraseTree.getNormalWordDoc());
	    	gerphraseTree.addNode(gerWord,engWord);
	    	addWordToFile(gerWord,gerphraseTree.getNormalWordDoc());
	    }
	    else {
	    	engwordTree.addNode(engWord, gerWord);
	    	addWordToFile(engWord,engwordTree.getNormalWordDoc());
	    	gerwordTree.addNode(gerWord,engWord);
	    	addWordToFile(gerWord,gerwordTree.getNormalWordDoc());
	    }
    }
    
    /**
     * prints the dictionary 
     */
    public void printDictionary() {
    	findWordTree().printTreeInOrder();
    }
    
    /**
     * deletes a word from the dictionary
     * @param deleteWord the word to be deleted
     * @param engOrGerm 1 means english 2 means german
     */
    public void deleteFromDictionary(String deleteWord) {
    	engwordTree.deleteNode(deleteWord);
    	engphraseTree.deleteNode(deleteWord);
    	gerwordTree.deleteNode(deleteWord);
    	gerphraseTree.deleteNode(deleteWord);
    	System.out.println("Word deleted");
    }
    
    /**
     * adds a word to a file
     * @param addingWord the word to be added
     * @param Doc the file it is to be added to
     */
    public void addWordToFile(String addingWord, String Doc) {
    	try(FileWriter fw = new FileWriter(Doc, true);
    		    BufferedWriter bw = new BufferedWriter(fw);
    		    PrintWriter out = new PrintWriter(bw))
    		{
    		    out.println(addingWord);
    		    //more code
    		} catch (IOException e) {
    		    //exception handling left as an exercise for the reader
    		}
    }

}