// Implementation of Minesweeper game in Java.
import java.util.*;

class Minesweeper{
    static final int SIZE = 10;
    static final int ODDS = 10;
    static final char MINE  = '*';
    static final char EMPTY = '_';

    public static void main(String[] args){
		if(args.length < 2){
		    System.err.println("usage: Minesweeper [seed] [size]");
	    	System.exit(0);
		}	
		System.out.println("Welcome to Java Minesweeper");
		int row = 0; 
		int col = 0;

		int seed = Integer.parseInt( args[0] );
		int size = Integer.parseInt( args[1] );

		System.out.println("Seed = " + seed);
		System.out.println("Size = " + size);

		if(size == 0) System.exit(0);
		if(size > 10) size = 10;
		if(size < 1 ) size = 1;
	
		char[][] yard = new char[size][size];
		boolean[][] exposed = new boolean[size][size];
	
		int mineCount = scatterSomeMines(yard, seed, size);
		findMines(yard);
	
		if(yard.length == 1) 
			checkMines(yard, exposed, row, col, mineCount);
		printYard(yard, exposed, size);
		
		Scanner in = new Scanner(System.in);

		for(;;){
			System.out.print("\nEnter tile to expose by coordinates\n");

			row = in.nextInt(); 
			col = in.nextInt(); 
			System.out.println("Row = " + row);
			System.out.println("Col = " + col);

		    if(row >= size || col >= size) 
		    	continue;
		    
		    checkMines(yard, exposed, row, col, mineCount);
	    	expose(yard, exposed, row, col);
	    	printYard(yard, exposed, size);
		}
    }
    static void checkMines(char[][] yard, boolean[][] exposed, int row, int col, int mineCount){
		System.out.println("checkMines()");
		System.out.println("Row = " + row);
		System.out.println("Col = " + col);
	
		if(yard[row][col] == MINE && yard.length > 1){
	    	printExposedYard(yard, yard.length);
	    	System.out.println("\nYou Lost!");
	    	System.exit(0);
		}
		int numExposed = 1;
		for(int y = 0; y < exposed.length; y++){
	    	for(int x = 0; x < exposed.length; x++){
				if(exposed[y][x] == true) numExposed++;
	    	}
		}
		if( (yard.length * yard.length) - mineCount == numExposed || yard.length == 1){
	    	printExposedYard(yard, yard.length);
	    	System.out.println("\nYou Won!");
	    	System.exit(0);
		}
    }
    public static void expose(char[][] yard, boolean[][] exposed, int row, int col){
    	System.out.println("expose()");
		System.out.println("Row = " + row);
		System.out.println("Col = " + col);
	
		if(yard[row][col] == '0')
	    	exposeNeighbors(yard, exposed, row, col);
		else 
			exposed[row][col] = true;
    }
    static void exposeNeighbors(char[][] yard, boolean[][] exposed, int row, int col){
    	System.out.println("exposeNeighbors()");
		System.out.println("Row = " + row);
		System.out.println("Col = " + col);
	
		if(exposed[row][col]) return;
		
		exposed[row][col] = true;
		for(int y = row - 1; y <= row + 1; y++){
	    	for(int x = col - 1; x <= col + 1; x++){ 
				if(y >= 0 && y < yard.length && x >=0 && x < yard[y].length) {
		    		if(yard[y][x] == '0') 
						exposeNeighbors(yard, exposed, y, x); 
		    		else exposed[y][x] = true;
				}
	    	}
		}
    }
    static int scatterSomeMines(char[][] yard, int seed, int size) {
		int mineCount = 0;
		Random rand = new Random(seed);
		
		for(int row = 0; row < yard.length; row++) {
	    	for(int col = 0; col < yard[row].length; col++) {
			if(rand.nextInt(size) == 0) {
		    	mineCount++;
		    	yard[row][col] = MINE; 
			}
			else
		    	yard[row][col] = EMPTY;
	    	}
		}
		return mineCount;
    }
    static void findMines(char[][] yard){
		int mineCount = 0;
		for(int row = 0; row < yard.length; row++){
		    for(int col = 0; col < yard[row].length; col++){
				if(yard[row][col] != MINE){
		    		for(int y = row - 1; y <=row + 1; y++){
						for(int x = col - 1; x <= col+1; x++){
			    			if( y >= 0 && y < yard.length && x >= 0 && x < yard[y].length){
								if(yard[y][x] == MINE)
				    				mineCount++;
			    			}
						}
		    		}
		    		yard[row][col] = (char)(mineCount+48);
		    		mineCount = 0;
				}
           	}
       	}
   	}
	static void printYard(char[][] yard, boolean[][] exposed, int size){
		System.out.print("\n   ");

		for(int x = 0; x < size; x++) 
			System.out.print(x +" ");

		System.out.println();	
		for(int row = 0; row < yard.length; row++){
	    	System.out.print(row+"  ");
	    	for(int col = 0; col < yard[row].length; col++){
				if(exposed[row][col])
		    		System.out.print(yard[row][col]);
				else
		    		System.out.print(EMPTY);
				System.out.print(" ");
	    	}
	    	System.out.println();
		}
    }
    static void printExposedYard(char[][] yard, int size){
		System.out.print("\n   ");
		for(int x = 0; x < size; x++) 
			System.out.print(x +" ");
		System.out.println();
		
		for(int row = 0; row < yard.length; row++){
	    	System.out.print(row+"  ");
	   		for(int col = 0; col < yard[row].length; col++){
				System.out.print(yard[row][col]);
				System.out.print(" ");
	    	}
	   		System.out.println();
		}
   	}
} // End Minesweeper



