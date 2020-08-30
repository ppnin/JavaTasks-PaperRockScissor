

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class GameOptions {

	Scanner sc = new Scanner(System.in);
	
	//---------------------------
	// The default game settings
	//---------------------------
	String paperName = "Paper";
	String rockName = "Rock";
	String scissorName = "Scissor";	
	
	// Player 1 profile
	String playerOneName = "Player_1";	
	String[] playerOneAllowedInput = {"a", "s", "x"};	
	List<String> controlsPlayerOne = Arrays.asList(playerOneAllowedInput );		
	
	// Player 1 profile
	String playerTwoName = "Player_2";
	String[] playerTwoAllowedInput = {"m", "k", "l"};	
	List<String> controlsPlayerTwo = Arrays.asList(playerTwoAllowedInput );


	// PC player profile
	String playerPcName = "PC";	
	String[] playerPcAllowedInput = {" ", " ", " "};	
	List<String> controlsPlayerPc = Arrays.asList(playerPcAllowedInput );

	
	
	// Global options - symbol names
	public void renamePaper() {
		System.out.println("Enter new name for Paper: ");
		
		String inputPaper = sc.nextLine();
		if( !inputPaper.isBlank() ) {
			paperName = inputPaper;
		}	
	}
	
	public void renameRock() {
		System.out.println("Enter new name for Rock: ");
		
		String inputRock = sc.nextLine();
		if( !inputRock.isBlank() ) {
			rockName = inputRock;
		}	
	}
	

	public void renameScissor() {
		System.out.println("Enter new name for Scissor: ");
		
		String inputScissor = sc.nextLine();
		if( !inputScissor.isBlank() ) {
			scissorName = inputScissor;
		}	
	}
	
	
	// --------------------------------
	//     PLAYER SPECIFIC OPTIONS
	// --------------------------------
		
	
	// Player 1 name	
	public void renamePlayerOne(String newName) {
		
		/*
		System.out.println("Enter a new player name: ");
		String inputName = sc.nextLine();		
		if (!inputName.isBlank() ) {
			playerOneName = inputName;			
		}		
	    */
		playerOneName = newName;	

	}
	
	
	// Player 1 
	public void editControlsPlayerOne(){
		
			controlsPlayerOne.set(0, "1");
			controlsPlayerOne.set(1, "2");
			controlsPlayerOne.set(2, "3");
				
	}
	

	// Player 2
	//public String renamePlayerTwo() {
	public void renamePlayerTwo(String newName) {
		/*
		System.out.println("Enter a new player name: ");	
		String inputName = sc.nextLine();
		if (!inputName.isBlank() ) {
			playerTwoName = inputName;
		}
		*/
		playerTwoName = newName;

	  }


	//public List<String> editControlsPlayerTwo(){
	public void editControlsPlayerTwo(){

			controlsPlayerTwo.set(0, "8");
			controlsPlayerTwo.set(1, "9");
			controlsPlayerTwo.set(2, "0");
									
	}
	

	public void setControlsPlayerPc(String humanPlayer){

		if (Objects.equals(humanPlayer, playerOneName )) {
			
			controlsPlayerPc.set(0, "8");
			controlsPlayerPc.set(1, "9");
			controlsPlayerPc.set(2, "0");
			
		} else {
			controlsPlayerPc.set(0, "1");
			controlsPlayerPc.set(1, "2");
			controlsPlayerPc.set(2, "3");
			
		}
								
     }
	
	

}






















