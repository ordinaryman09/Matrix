import java.util.Scanner;
import java.io.*;

public class Masimo {
  
  int c, n;
  float[][] masimoMatrix;
  float[] dataInputArray;
  
  public void printMatrix() {
    
    generateMatrix();
    
    for(int row = 0; row <= c; row++) {    
      StringBuilder sb = new StringBuilder();
      //print row by row
      for(int col = 0; col <= c; col++) {
        sb.append(String.format("%.6f", masimoMatrix[row][col]) + "\t");
      } 
      System.out.println(sb);  
    }
  }
  
  //generate the matrix by computing single entry via getEntryMatrix
  public float[][] generateMatrix() {
    masimoMatrix = new float[c+1][c+1];
    
    for(int k = 0; k <= c; k++) {
      for(int j = 0; j <= c; j++) {
        masimoMatrix[k][j] = getEntryMatrix(k,j);
      }
    }
    
    return masimoMatrix;
    
  }
  
  //compute each entry by using the given algorithm
  public float getEntryMatrix(int k, int j) {
    
    float result = 0.0f;
    
    for(int i = c; i < n; i++) {  
      //the given algorithm
      result += dataInputArray[i-k] * dataInputArray[i-j];
    }
    return result;
  }
  
  //scans the input from the given file and store it into the dataInputArray
  public int scanningInput(String fileName) throws IOException {
    dataInputArray = new float[n];
    int i = 0;
    Scanner fileScanner = new Scanner(new File(fileName));
    while (fileScanner.hasNextFloat()){
      dataInputArray[i] = fileScanner.nextFloat();
      i++;
    }
    return i; 
  }
  
  public static void main (String[] args) throws IOException {
    
    Masimo masimo = new Masimo();
    
    Scanner scanner = new Scanner( System.in );
    
    boolean inputCValidation = true;
    boolean inputNValidation = true;
    boolean inputFileValidation = true;
    
    //reset the prompt to ask for n IF the given input file does not have the same # as N
    while(inputNValidation) {
      
      //validate c
      while(inputCValidation) {
        try {
          System.out.print( "Input c (i.e. 4): ");
          String inputC = scanner.nextLine();    
          int c = Integer.parseInt(inputC);
          if(c > 0) {
            masimo.c = c;
            inputCValidation = false;  
          }
        } catch(Exception e) {
          System.out.println("Please enter integer only");
        } 
      }
      
      //validate n
      while(inputNValidation) {
        
        try {
          System.out.print( "Input N (i.e. 300): ");
          String inputN = scanner.nextLine();
          int n = Integer.parseInt(inputN);
          if(n > masimo.c) {
            masimo.n = n;
            inputNValidation = false; 
          }
        } catch(Exception e) {
          System.out.println("Please enter integer only, and greater than c");
        }
      }
      
      //validate the file input
      while (inputFileValidation) {
        
        try {
          System.out.print( "Input file (i.e. test.prn): " );
          String inputFile = scanner.nextLine();
          int n = masimo.scanningInput(inputFile);
          if( n == masimo.n) {
            inputFileValidation = false;
            masimo.printMatrix();
          }
          else {
            //break from the loop to redo the prompt for # of n
            System.out.println("The number of data and n you entered does not match");
            inputNValidation = true;
            break;
          }
        } catch(ArrayIndexOutOfBoundsException e) {
          //break from the loop to redo the prompt for # of n
          System.out.println("The number of data and n you entered does not match");
          inputNValidation = true;
          break;
        } catch(Exception e) {
          System.out.println("Please a valid file input");
        }      
      }
    }
  } 
}