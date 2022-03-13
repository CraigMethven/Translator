import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;

    /**
     * @author Elliot Kinkead && Fraser Rossetter
     * This is the main menu of the English to German Translator. This menu allows you to translate text documents, small texts aswell as add 
     * and delete words from the dictionary.
     */
    public class Menu {

        FileReader filereader;
        BufferedReader BufferedReader;
        private Translator translation;
        private final String englishDoc = "English.txt";
        private final String germanDoc = "German.txt";
        private final String germanPhraseDoc = "GermanPhrases.txt";
        private final String englishPhraseDoc = "EnglishPhrases.txt";
        private boolean engOrGer;

        /**
         * @param args
         */
        public static void main(String[] args) {
            // Runs Java Program
            Menu newMenu = new Menu();
            newMenu.Welcome();
        }


        public void Welcome()
        {
        	/**
        	 * Language preferences Menu
        	 * This allows you to chose which language you would prefer to use.
        	 * English or German obviously
        	 */
            translation = new Translator(new Tree(germanDoc,englishDoc), new Tree(englishDoc,germanDoc), new Tree(germanPhraseDoc,englishPhraseDoc),new Tree(englishPhraseDoc,germanPhraseDoc));
            // Language Preferences
            System.out.println("Welcome to Translation Program please select an langauge");
            System.out.println("Willkommen beim Übersetzungsprogramm, bitte wählen Sie eine Sprache aus");
            Scanner sc = new Scanner(System.in);
            String userInput = sc.nextLine();

               if (userInput.equals("English") || userInput.equals("english"))
                {
                   System.out.println("=========================================================");
                   System.out.println("English Selected");
                   engOrGer = true;
                   translation.setEngOrGer(engOrGer);
                   runMenu();
               }
                else if (userInput.equals("German") || userInput.equals("german"))
               {
                   System.out.println("=========================================================");
                   translation.translateSentence("German Selected *RUNS ENGLISH ANYWAY*");
                   engOrGer = false;
                   translation.setEngOrGer(engOrGer);
                   runMenu();
               }
       }


        private void runMenu() {
            
                /**
                 * Main Menu 
                 * From Here you can either Translate a text file, Access the dictionary options or
                 * translate a sentence that or even end the program
                 */
            boolean loop = true;
            do {
            	if(engOrGer==true) {
            		 System.out.println("Please choose from the following options");
                     System.out.println("1 - Translate a txt file");
                     System.out.println("2 - Do something with the dictionary");
                     System.out.println("3 - Enter your own thing to be translated");
                     System.out.println("4 - End Program");
            	}
            	else {
            		translation.translateSentence("Please choose from the following options");
            		translation.translateSentence("1 - Translate a txt file");
            		translation.translateSentence("2 - Do something with the dictionary");
            		translation.translateSentence("3 - Enter your own thing to be translated");
            		translation.translateSentence("4 - End Program");
            	}
               
                Scanner sc = new Scanner(System.in);
                String userInput = sc.nextLine();
                    if (userInput.equals("1"))
                    {
                        System.out.println("=========================================================");
                        System.out.println("Translate a txt file");
                        translatetext();
                        translation.output();
                    }
                    else if (userInput.equals("2"))
                    {
                        System.out.println("=========================================================");
                        System.out.println("Do something with the dictionary");
                        dictionary();
                        }
                    else if (userInput.equals("3"))
                    {
                        System.out.println("=========================================================");
                        System.out.println("Enter what you want translated :)");
                        findENGorGer();
                        translation.takeInString();
                    }
                    else if(userInput.contentEquals("4"))
                    {
                        System.out.println("Good bye    *wave*");
                        loop=false;
                    }
                    else {
                    	System.out.println("Please insert a valid input");
                    }
            }while(loop==true);
            
        }


        private void findENGorGer()
        {
        Scanner sc = new Scanner(System.in);
        /**
         * Language Settings for "Enter what you want translated"
         * This allows you to choose between English or German
         * 
         */
        if(engOrGer==true) {
        System.out.println("Please select the following options");
        System.out.println("1 -English to German");
        System.out.println("2 -German to English");
        }
    	else
    	{
    		translation.translateSentence("Please choose from the following options");
    		translation.translateSentence("1 -English to German");
    		translation.translateSentence("2 -German to English");
    	}
    	
        String userInput = sc.nextLine();         
            if (userInput.equals("1"))

            {
                System.out.println("=========================================================");
                System.out.println("English to German");
                translation.setEngOrGer(true);          
            }

            else if (userInput.equals("2"))
            {
                System.out.println("=========================================================");
                System.out.println("German to English");
                translation.setEngOrGer(false);
            }

            else
            {
                System.out.println("Invalid Option. Please try again");
            }
        }
        
        private void dictionary () {
        	/**
        	 * Dictionary Menu
        	 * From here you can Print the dictionary to a text file, Add or dictionary and return to the main menu.
        	 */
        	if(engOrGer==true) {
            System.out.println("Please select the following options");
            System.out.println("1 - Print Dictonary");
            System.out.println("2 - Add to dictionary");
            System.out.println("3 - Delete dictionary");
            System.out.println("4 - Return to main menu");
        	}
        	else
        	{
        		translation.translateSentence("Please choose from the following options");
        		translation.translateSentence("1 - Print Dictonary");
        		translation.translateSentence("2 - Add to dictonary");
        		translation.translateSentence("3 - Delete dictonary");
        		translation.translateSentence("4 - Return to main menu");	
        	}

            Scanner sc = new Scanner(System.in);
            String userInput = sc.nextLine();

                if (userInput.equals("1"))

                {
                    System.out.println("=========================================================");
                    System.out.println("Print Dictionary");
                    translation.printDictionary();
                }
                else if (userInput.equals("2"))
                {
                    System.out.println("=========================================================");
                    System.out.println("Add a word to the current dictionary");
                    addToDictionary();
                }
                else if (userInput.equals("3"))
                {
                    System.out.println("=========================================================");
                    System.out.println("Delete a word to the current dictionary");
                    deleteFromDictionary();
                }
                else if (userInput.equals("4"))
                {
                    System.out.println("=========================================================");
                    return;
                }
                else
                {
                    System.out.println("Please only insert valid numbers");
                }

        }

        private void translatetext() {
            Scanner sc = new Scanner(System.in);
            /**
             * This option allows you to translate a text file
             * The First set of options allow you to chose either English to German or German to English
             */
            if(engOrGer==true) {
            System.out.println("Please select the following options");
            System.out.println("1 -English to German");
            System.out.println("2 -German to English");
            }
        	else
        	{
        		translation.translateSentence("Please choose from the following options");
        		translation.translateSentence("1 -English to German");
        		translation.translateSentence("2 -German to English");
        	}
        	
            String userInput = sc.nextLine();         
                if (userInput.equals("1"))

                {
                    System.out.println("=========================================================");
                    System.out.println("English to German");

                    translation.setEngOrGer(true);

                    System.out.println("Name of the File you would like to open");
                    String fileName = sc.nextLine();

                    translation.takeInFile(fileName);
                    
                }

                else if (userInput.equals("2"))
                {
                    System.out.println("=========================================================");
                    System.out.println("German to English");
                    translation.setEngOrGer(false);
                    System.out.println("Name of the File you would like to open");
                    String fileName = sc.nextLine();

                    translation.takeInFile(fileName);
                }

                else
                {
                    System.out.println("Invalid Option. Please try again");;
                }
        }



        /**
         * Add to dictionary function
         * This method adds to the dictionary by inputting its English and German translations.
         * 
         */
        public void addToDictionary()
        {
        	try {
            Scanner s = new Scanner(System.in);
            Scanner n = new Scanner(System.in);

            String engWord = null;
            String gerWord = null;
            int phrase = 0;
            boolean loop=true;

            System.out.println("Are you inputting a phrase (1) or a word (2)");
            
            do {
            	
	            try {
	            	
	            	loop=true;
	            	
	            	phrase = n.nextInt();
	            	
	            		} catch (InputMismatchException e) {
	            			
	            		loop=false;
	            	}
	            }
	        while(loop==false);
            System.out.println("Please input the English translation of the word you want to add to the dictionary");

            engWord = s.nextLine();

            System.out.println("Please input the German translation of the word you want to add to the dictonary.");
            
    	    gerWord = s.nextLine();

            translation.addWord(engWord, gerWord, phrase);           
            } catch (InputMismatchException e) {
        	 System.out.println("Do not enter a letter. Please enter a number");
            } 
        }
        
        /**
         * Delete a word from dictionary function
         * This methods deletes a word from the dictionary by either inputting its English or German translation.
         * 
         */
        public void deleteFromDictionary() {
            Scanner f = new Scanner(System.in);

            System.out.println("Please input the word you want to delete.");

            String word = f.nextLine();

            translation.deleteFromDictionary(word);
        }
        
    }