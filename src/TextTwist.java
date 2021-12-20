import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

// Miranda Luo
// Sep 11, 2019
// Description: TextTwist hackathon

public class TextTwist {
   
   private ArrayList<String> allWords;
   private ArrayList<String> sixLetterWords;
   private String masterWord;
   private String masterWordShuffled;
   private String guessed;
   private ArrayList<String> wordList;
   private ArrayList<String> wordsGuessed;
   private ArrayList<String> wordsLeft;
   
   public TextTwist(String wordFile) {
      
      allWords = new ArrayList<String>();
      sixLetterWords = new ArrayList<String>();
      wordsGuessed = new ArrayList<String>();
      wordsLeft = new ArrayList<String>();
      wordList = new ArrayList<String>();
      
      File filePath = new File(wordFile);
      try  {
         Scanner dataGetter = new Scanner(filePath);
         while(dataGetter.hasNext())
              allWords.add(dataGetter.nextLine());
         
         //close the file
         dataGetter.close();
      }
      //checked exception, need to handle this exception
      catch (FileNotFoundException e){
         System.out.println("Cannot find data file.");
      }
      
      for (int i = 0; i < allWords.size(); i++) {
         if (allWords.get(i).length() == 6)
            sixLetterWords.add(allWords.get(i));
      }
      
      reset();
      
   }
   
   public void reset() {
      
      masterWord = sixLetterWords.get( (int) (Math.random() * sixLetterWords.size()));
      
      for (int i = 0; i < allWords.size(); i++) {
         
         ArrayList<String> masterWordArray = new ArrayList<String>();
         for (int j = 0; j < masterWord.length(); j++) {
            masterWordArray.add(masterWord.substring(j, j + 1));
         }
         
         String currentWord = allWords.get(i);
         if (currentWord.length() >= 3) {
            int matchingLetters = 0;
            for (int j = 0; j < currentWord.length(); j++) {
               if (masterWordArray.contains(currentWord.substring(j, j + 1))) {
                  matchingLetters++;
                  masterWordArray.remove(currentWord.substring(j, j + 1));
               }
               
            }
            
            if (matchingLetters == currentWord.length()) {wordsLeft.add(currentWord); wordList.add(currentWord);}
         }
         
      }
      
      guessed = null;
      
      
      shuffle();
            
   }
   
   public void shuffle() 
   {
      
      //fix later
      
      
      String[] shuffledWordArray = new String[masterWord.length()];
      
      for (int i = 0; i < masterWord.length(); i++) {
         shuffledWordArray[i] = masterWord.substring(i, i+1);
      }
      
      
      Collections.shuffle(Arrays.asList(shuffledWordArray));
      
//      for(int i = masterWord.length(); i > 0; i--) {
//         int randomNum = (int) (Math.random() * (masterWord.length() - 1)) + 1;
////         System.out.println(randomNum);
//         String temp = shuffledWord.substring(randomNum, randomNum + 1);
//         String temp2 = shuffledWord.substring(0, 1);
//         String chunk1 = shuffledWord.substring(1, randomNum);
//         String chunk2 = shuffledWord.substring(randomNum + 1);
//         shuffledWord = temp + chunk1 + temp2 + chunk2;
////         System.out.println(shuffledWord);
//      }
      
      String shuffledWord = "";
      
      for (int i = 0; i < shuffledWordArray.length; i++) {
         shuffledWord += shuffledWordArray[i];
      }
         
      masterWordShuffled = shuffledWord;
      
   }
   
   public void run() {
      
      Scanner input = new Scanner(System.in);
      
      do {
         
         while (wordsLeft.size() > 0 && !guessed.equalsIgnoreCase("quit")) {
            
            System.out.println(masterWordShuffled);
            
            System.out.println("Words left: " + wordsLeft.size());
            
            System.out.print("Guess: ");
            guessed = input.nextLine();
            
            System.out.println();
            
            if (wordsLeft.contains(guessed)) {
               wordsGuessed.add(wordsLeft.remove(wordsLeft.indexOf(guessed)));
            }
            else if (guessed.equalsIgnoreCase("s")) {
               shuffle();
            }
            
         }
         
         System.out.println();
         
         if (wordsLeft.size() == 0)
            System.out.println("You Win!");
         else {
            System.out.println("Words Left: " + wordsLeft.size());
            System.out.println(wordsLeft);
         }
         System.out.println("Do you want to play again? y/n");
         reset();
         
      } while (input.nextLine().equals("y"));
      
      input.close();
      
   }
   
   public void update() {
      
      if (wordsLeft.contains(guessed)) {
         wordsGuessed.add(wordsLeft.remove(wordsLeft.indexOf(guessed)));
//         System.out.println(wordsLeft);
      }
      
//      else {
//         System.out.println("updated");
//      }
      
   }
   
   public boolean isGameOver() {
      return wordsLeft.size() == 0;
   }
   
   public ArrayList<String> getAllWords() {
      return allWords;
   }

   public void setAllWords(ArrayList<String> allWords) {
      this.allWords = allWords;
   }

   public ArrayList<String> getSixLetterWords() {
      return sixLetterWords;
   }

   public void setSixLetterWords(ArrayList<String> sixLetterWords) {
      this.sixLetterWords = sixLetterWords;
   }

   public String getMasterWord() {
      return masterWord;
   }

   public void setMasterWord(String masterWord) {
      this.masterWord = masterWord;
   }

   public ArrayList<String> getWordsGuessed() {
      return wordsGuessed;
   }

   public void setWordsGuessed(ArrayList<String> wordsGuessed) {
      this.wordsGuessed = wordsGuessed;
   }

   public ArrayList<String> getWordsLeft() {
      return wordsLeft;
   }

   public void setWordsLeft(ArrayList<String> wordsLeft) {
      this.wordsLeft = wordsLeft;
   }

   public String getMasterWordShuffled() {
      return masterWordShuffled;
   }

   public void setMasterWordShuffled(String masterWordShuffled) {
      this.masterWordShuffled = masterWordShuffled;
   }

   public String getGuessed() {
      return guessed;
   }

   public void setGuessed(String guessed) {
      this.guessed = guessed;
   }

   public ArrayList<String> getWordList() {
      return wordList;
   }

   public void setWordList(ArrayList<String> wordList) {
      this.wordList = wordList;
   }

//   public static void main(String[] args) 
//   {
//      TextTwist twist = new TextTwist("words.txt");
////      System.out.println(twist.wordsLeft);
////      twist.run();
////      System.out.println(twist.masterWord);
////      System.out.println();
////      System.out.println(twist.shuffle());
////      
////      System.out.println(twist.wordsLeft);
//   }

}
