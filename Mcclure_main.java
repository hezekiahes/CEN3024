// Author Name: Walter McClure
// Date: 07/13/2021
// Program Name: Mcclure_module7_word_occurrence
// Purpose: Display JavaDoc for WordOccurrence 

package mcclure_WordCounter;

import org.junit.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Main method that extends the Application Class
 * @author Walter McClure
 *
 */
public class Mcclure_main extends Application {

		// Static variables
	   static File file;
       static Map<String, Integer> words = new HashMap<String, Integer>();
       static Map<String, Integer> sortedWords = new TreeMap<String, Integer>();
       static LinkedHashMap<Integer, String> sortedHashMap = new LinkedHashMap<>();
       static Map<String, Integer> unsortMap = new HashMap<>();
       
       @Override
       public void start(Stage primaryStage) throws Exception {
              // Main Window to contain all nodes
              VBox mainWindow = new VBox(20);
                           
              // Create Header to contain Title
              HBox header = new HBox(10);
              Label headerTitle = new Label("Word Counter");
              header.setAlignment(Pos.CENTER);
              header.getChildren().add(headerTitle);
              header.setPadding(new Insets(20));
              headerTitle.setFont(Font.font("Arial", 22));
              
              // Create main window to display stats
              VBox displayWindow = new VBox(40);
              Text displayArea = new Text("Use the button below to upload a file to perform a Word Count");
              displayArea.setWrappingWidth(350);
              Label fileLabel = new Label("No file selected");
              displayWindow.setAlignment(Pos.CENTER);
              displayWindow.getChildren().addAll(displayArea, fileLabel);
              
              
              //Create Footer Box
              HBox footer = new HBox(10);
              FileChooser chooseFile = new FileChooser();
              Button chooseFileButton = new Button("Choose File");
              Button countWordsButton = new Button("Count Words");
              Label footerLabel = new Label("Developed by: Walter McClure © 2021");
              footerLabel.setPadding(new Insets(60, 0, 0, 0));
              footer.getChildren().addAll(chooseFileButton, countWordsButton);
              footer.setAlignment(Pos.CENTER);
              
              // Event Handler for File Chooser
              EventHandler<ActionEvent> openFileDialogWindow = new EventHandler<ActionEvent>() {
                     public void handle(ActionEvent e) {
                           // Let chooser find file
                           file = chooseFile.showOpenDialog(primaryStage);
                           
                           // Display selected file path
                           if (file != null) {
                                  fileLabel.setText(file.getAbsolutePath() + " selected");
                           }
                     }
              };  // End of event handler

              // Event Handler for Word Counter
                           EventHandler<ActionEvent> countWords = new EventHandler<ActionEvent>() {
                                  public void handle(ActionEvent e) {
                                  
                                  // Try catch block to resolve File IO Errors
                                  try {  
                                  CountWords(file, words);
                                  }
                                  catch (Exception ex) {
                                         System.out.println("Error in Count Words Event Handler");
                                         }
                                  
                                  // Inititial hashmap sort and limit to 20 values
                                  Map<String, Integer> sortedWords = words.entrySet().stream()
                                	        .sorted((o1, o2) -> o2.getValue() - o1.getValue())
                                	        .limit(20)
                                	        .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
                                  
                  
                                  
                                  // Display word counts
                                 displayArea.setText("The top 20 occurring words are:\n\n" + sortByValues(sortedWords).toString());
                                  words.clear();
                                  }      
                           };  // End of event handler
              
              
              
              // Set button actions
              chooseFileButton.setOnAction(openFileDialogWindow);
              countWordsButton.setOnAction(countWords);
              
              
              // Setup Scene
              footerLabel.setAlignment(Pos.CENTER);
              
              mainWindow.getChildren().addAll(header, displayWindow, footer, footerLabel);
              mainWindow.setAlignment(Pos.CENTER);
              Scene scene = new Scene(mainWindow, 500, 400);
              primaryStage.setScene(scene);
              primaryStage.show();
              
       }
       
       
       
       
       /** 
        * Main method
        * @param args
        */
       public static void main(String[] args) {
              launch(args);
       }
       
       
       /**
        * Method to count words from file and add them to HashMap
        * @param file - file to read words from
        * @param words - HashMap containing word and frequencies
        * @throws FileNotFoundException
        */
       public static void CountWords(File file, Map< String, Integer> words) throws FileNotFoundException {
    // Create scanner to read file	   
   	   Scanner inputFile =new Scanner (file);
       
   	   // WHile loop to count words in file
   	   while(inputFile.hasNext()){
       String word = inputFile.next();
       Integer count = words.get(word);
       // If count is not 0, increment count
       if(count!=null)
       count++;
       else // if count was 0, make count 1
       count=1;
       words.put(word,count);
       
       
            }
   	System.out.print(words.size());
       inputFile.close();  // close file
       }


       
       /**
        * Method to sort HashMap by values and return sorted list
        * @param <K>
        * @param <V>
        * @param map
        * @return
        */
       public static <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
    	    Comparator<K> valueComparator =  new Comparator<K>() {
    	        public int compare(K k1, K k2) {
    	            int compare = map.get(k2).compareTo(map.get(k1));
    	            if (compare == 0) return 1;
    	            else return compare;
    	        }
    	    };
    	    Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
    	    sortedByValues.putAll(map);
    	    return sortedByValues;
    	}
       
}
