import java.util.Arrays;
import java.util.HashMap;
//import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
//import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class GameFlow {

	// We need a keyboard input
	Scanner sc = new Scanner(System.in);
	
	// access the game options (stored as a class in a separate file)
	GameOptions options = new GameOptions();
	
	
	// Game modes:  if singlePlayer == true the opponent is the PC, otherwise two 
	//               human players play against each other
	boolean singlePlayer;
	
	
	/* ----------------------------------
	 * Place-holders for chosen settings
	 * ---------------------------------- 
	 * In order to make more of the code re-usable, I use "generic" variables, lists
	 * and hash maps which contain settings instructions after they are chosen.    
	 * 
	 * For example, in the Two Player Mode "controlsGamePlayerOne" and 
	 * "controlsGamePlayerTwo" keep information about players ([Player 1] 
	 * is always in a structure that has "..PlayerOne.." in the name, etc).
	 * 
	 * In the Single Player mode, the second player is a PC. After the human
	 * player configures hers/his profile, the PC is assigned the profile of 
	 * "the other" (opposite) player. In the code, "PC player" is always
	 * in the variables/lists/etc that have "..PlayerTwo.." in their name.
	 * The human player is those with "..PlayerOne..", regardless if he/she
	 * chooses to play with [Player 1] or [Player 2] controls       
	 *  
	 * Finally - in the Two Player mode the program assumes that players . 
	 * provide input at the same time.  
	 *  
	 */
	
	String gamePlayerOneName;
	String gamePlayerTwoName;
	// Chosen controls (input keys): can be letters or numbers	 
	List<String> controlsGamePlayerOne; 
	// Mapping the controls to corresponding game symbols
	HashMap<String, String> mapSymbolsGamePlayerOne = new HashMap<String, String>(); 
	
	List<String> controlsGamePlayerTwo; 
	HashMap<String, String> mapSymbolsGamePlayerTwo = new HashMap<String, String>(); 
	
	// Map the console input to corresponding players  	
	HashMap<String, String> mapEnteredKeys = new HashMap<String, String>(); 
	
	/* This was used in alternative evaluation function, where I tested
	 HashSets and HashMaps (instead of if-else statements) to get the game result.
	 Although the method "findWinner()" is correct, it will not work as-is now 
	 because I haven't changed it to work in both modes  
	*/
	//HashMap<String, String> mapSymbolhumanPlayer = new HashMap<String, String>();
	//HashMap<String, String> mapSymbolPlayerOne = new HashMap<String, String>(); 
	//HashMap<String, String> mapSymbolPlayerTwo = new HashMap<String, String>(); 
	
	
	
	public void displayStartMessage() {
		// Start with a short intro about the game and the default settings
		System.out.println( "\n======================================================");
		System.out.println( "    WELCOME TO THE  \"PAPER, ROCK, SCISSOR\" GAME!" );
		System.out.println( "======================================================");
		System.out.println( "\nNever played? No problem, it's simple :) " );
		System.out.println("\nINTRO:");
		//System.out.println( "------------------------------------------------------" );
		System.out.println( "\nThere are two players and three symbols: "
				          + "\n       [Paper,  Rock,  Scissor].   "
				          + "\nEach player randomly selects one of the symbols, and"
				          + "\nthe player with the \"stronger\" symbol winns the game."
				          + "\n\nThe rules are: "
				          + "\n    Paper beats Rock"
				          + "\n    Rock beats Scissor"
				          + "\n    Scissor beats Paper");
		System.out.println( "\n\nBefore start, check the default options: " );
		System.out.println( "\nGame symbols:  Paper, Rock, Scissors" );
		System.out.println( "Player names:  Player 1, Player 2" );		
		System.out.println( "Player control keys:  "
				          + "\n                        Paper  |  Rock | Scissors "
				          + "\n                        ------+--------+--------- "
				          + "\n            Player 1:     a   |    s   |    x     " 
				          + "\n            Player 2:     m   |    k   |    l     ");
	
		System.out.println("\n\nAny of the players may also play with numeric keys"
				          + "\ninstead. In that case the controls are: "
				          + "\n\n            Player 1:     1 (a),   2 (s),   3 (x)"
				          + "\n            Player 2:     8 (m),   9 (k),   0 (l)");				
		System.out.println("\nEach player can choose a name, you may even change "
				         + "\nthe game symbols."
				         + "\nNOTE: keep in mind, however, that renaming the symbols"
				         + "\ndoes not change the rules. For example, if you rename"
				         + "\nPaper to Icecream and Rock to Boat, the winner will be"
				         + "\nthe player with Icecream. (Actually... you *could* even"
				         + "\nchange the rules by renaming Paper to Scissor or Rock"
				         + "\nto Paper. The game won't stop you.. but it doesn't make "
				         + "much sense, does it? )");
		System.out.println( "------------------------------------------------------" );
		
	}
	
	//---------------------------------------------
	// GAME SETTINGS
	//---------------------------------------------

	public void configureOptions() {
		// First set the game mode. The default is Two Players	
		System.out.println("\nSETTINGS:  press [Enter] for accepting the default option, "
		                 + "\n           or [any other key + Enter] to make changes)");
         
		System.out.println("\n\nThe default game mode is [Two players]. Change to [Single player] mode?");
		//         + "\nPress Enter to continue, or any other key to enter [Single player] mode");
		
		String qMode = sc.nextLine();
		if (!qMode.isBlank()) {
			singlePlayer = true;			
		}
		
		
		// EDIT GLOBAL OPTIONS (symbol's names)
		System.out.println("\nThe default symbols are [Paper, Rock, Scissor]. Change to smth else?");
		String qOptionsSymbols = sc.nextLine();
		if (!qOptionsSymbols.isBlank()) {
			 options.renamePaper();
			 options.renameRock();
			 options.renameScissor();
		}
        
		//Edit Player's profiles (names and controls) 
		if(singlePlayer == false) {
						
			// Player 1, change name
			System.out.println("Player 1: type a new name and press [Enter], or just hit [Enter] to keep the default)");
					         
			String qOptionsplayerOneName = sc.nextLine();
			if (!qOptionsplayerOneName.isBlank()) {
				 options.renamePlayerOne( qOptionsplayerOneName );
			}
			
			// Player 1, switch from [a,s,x] to [1,2,3]
			System.out.println("Player 1: switch from default controls to numeric keys? (Leave blank for NO)");
			String qOptionsplayerOneKeys = sc.nextLine();
			if (!qOptionsplayerOneKeys.isBlank()) {
				options.editControlsPlayerOne();
			}
			
			// Player 2, change name
			System.out.println("Player 2: type a new name and press [Enter], or just hit [Enter] to keep the default)");
			String qOptionsplayerTwoName = sc.nextLine();
			if (!qOptionsplayerTwoName.isBlank()) {
				 options.renamePlayerTwo( qOptionsplayerTwoName );
			}
			
			// Player 1, switch from [m,k,l] to [8,9,0]
			System.out.println("Player 2: switch from default controls to numeric keys? (Leave blank for NO)");
			String qOptionsplayerTwoKeys = sc.nextLine();
			if (!qOptionsplayerTwoKeys.isBlank()) {
				options.editControlsPlayerTwo();
			}
			
			// Map the names and configuration to PlayerOne / Two
			gamePlayerOneName = options.playerOneName;
			gamePlayerTwoName = options.playerTwoName;
			// Control keys
			controlsGamePlayerOne = options.controlsPlayerOne;
			controlsGamePlayerTwo = options.controlsPlayerTwo;
						
		 } 
		 else {
					
			// Give player option to chose the profile of Player 1 or Player 2
			System.out.println("Your default name and controls are those of \"Player 1\".");	
			System.out.println("Would you like to switch to Player 2?" );
			
			String qHumanPlayerControls = sc.nextLine();
			if (qHumanPlayerControls.isBlank()) {
				System.out.println("You chose Player 1's profile. "
						+ "\nTo change your name type a new name and press Enter" );
				
				String qHumanPlayerName = sc.nextLine();
				if (qHumanPlayerName.isBlank()) {
					gamePlayerOneName = options.playerOneName;
				}
				else {
					gamePlayerOneName = qHumanPlayerName;
				}
				
				gamePlayerTwoName = options.playerPcName;
				
				System.out.println("Keep the default controls or press [any key + Enter] to change to numeric keys" );
				
				String qNumericControls = sc.nextLine();
				if (qNumericControls.isBlank()) {
					controlsGamePlayerOne = options.controlsPlayerOne;	
				}
				else {
					
					options.editControlsPlayerOne();
					controlsGamePlayerOne = options.controlsPlayerOne;
				}
				// PC is assigned the numeric controls of the opposite player				
				options.setControlsPlayerPc(options.playerOneName);
				controlsGamePlayerTwo = options.controlsPlayerPc;
			} 
			
			else {
				System.out.println("You chose Player 2's profile. "
						+ "\nTo change your name type a new name and press Enter");	
				
				String qHumanPlayerName = sc.nextLine();
				if (qHumanPlayerName.isBlank()) {
					gamePlayerOneName = options.playerTwoName;
				}
				else {
					gamePlayerOneName = qHumanPlayerName;
				}
				
				gamePlayerTwoName = options.playerPcName;
				
				System.out.println("Keep the default controls or press [any key + Enter] to change to numeric keys" );
				
				String qNumericControls = sc.nextLine();
				if (qNumericControls.isBlank()) {
					controlsGamePlayerOne = options.controlsPlayerTwo;	
				}
				else {
					options.editControlsPlayerTwo();
					controlsGamePlayerOne = options.controlsPlayerTwo;
				}
				// Again, PC is assigned the numeric controls of the opposite player				
				options.setControlsPlayerPc(options.playerTwoName);
				controlsGamePlayerTwo = options.controlsPlayerPc;								
			}
		
		}
		
		
		// Use configured options, map input keys to symbols for each player		
		mapSymbolsGamePlayerOne.put( controlsGamePlayerOne.get(0),  options.paperName);
		mapSymbolsGamePlayerOne.put( controlsGamePlayerOne.get(1),  options.rockName);
		mapSymbolsGamePlayerOne.put( controlsGamePlayerOne.get(2),  options.scissorName);
		
		mapSymbolsGamePlayerTwo.put( controlsGamePlayerTwo.get(0),  options.paperName);
		mapSymbolsGamePlayerTwo.put( controlsGamePlayerTwo.get(1),  options.rockName);
		mapSymbolsGamePlayerTwo.put( controlsGamePlayerTwo.get(2),  options.scissorName);
		
		// Checking who the players are and how they are configured
		System.out.println("\nPLAYERS & CONTROLS");
		System.out.println("[Player 1]: " + gamePlayerOneName + "  [CTRL]: " + mapSymbolsGamePlayerOne);
		System.out.println("[Player 2]: " + gamePlayerTwoName + "  [CTRL]: " + mapSymbolsGamePlayerTwo);
		
	}
	
	
	// Counter
	public void displayCounter() {
		
		
		String[] readyGo = {"\nPaper..", " Rock..", " Scissors.."};	
		for (int i = 0; i < 3; ++i) {			
			System.out.print( readyGo[i] );			
			try {
				TimeUnit.MILLISECONDS.sleep(600);
			    }
			catch (InterruptedException e) {
				e.printStackTrace();
		     	}						
		}		
        System.out.println("\n>> GO! <<");
	}
	

	// Take and evaluate the players' input
	public void startRound()
	{		

		String playerInputs; 

		
		if (singlePlayer == true) {
			
			String humanInput = sc.nextLine();
			// PC input is a random number from [0 - 2], used to get
			// the corresponding symbol from the controls' list
			int pcInput = new Random().nextInt(3) ;
			
			// Concatenate the input so that it's in the same form as in the Two Player mode
			String pcMappedInput = controlsGamePlayerTwo.get(pcInput);			
			playerInputs = humanInput + pcMappedInput;

			
		}
		else {
			playerInputs = sc.nextLine();	
		}
		
		// Merge the lists with the available keys
		// ref: https://howtodoinjava.com/java/array/string-to-string-array/
		List<String> ctrBothPlayers = Stream.of(controlsGamePlayerOne, controlsGamePlayerTwo)
				                            .flatMap(List::stream)
				                            .collect(Collectors.toList());
		
		
		// We can use a while loop to check if the users' input is correct, for example:
		//      - check if there are exactly two keys
		while (playerInputs.length() != 2    || 
		//		- check the same key is not pressed twice
			   Objects.equals( playerInputs.charAt(0), playerInputs.charAt(1))  || 
        //		- check that keys are allowed			   
			   ctrBothPlayers.containsAll(Arrays.asList( playerInputs.split("") ))  != true  ||  
		//		- check that all keys are not from one player only 
			   controlsGamePlayerOne.containsAll(Arrays.asList( playerInputs.split("") ))  ||
        //		- check that all keys are not from one player only 
			   controlsGamePlayerTwo.containsAll(Arrays.asList( playerInputs.split("") )) 
				)  {

			System.out.println("Incorrect input! "
					       + "\nBoth players have to enter ONLY one sign, try again.");
			playerInputs = sc.nextLine();
		}
	    		

		String[] inputKeys = playerInputs.split("");	
		
		if (controlsGamePlayerOne.stream().anyMatch( inputKeys[0] :: contains) ) {
			mapEnteredKeys.put(gamePlayerOneName, inputKeys[0] ); 
			mapEnteredKeys.put(gamePlayerTwoName, inputKeys[1] ); 
		} 
		else {
			mapEnteredKeys.put(gamePlayerOneName, inputKeys[1] ); 
			mapEnteredKeys.put(gamePlayerTwoName, inputKeys[0] ); 			
		}
		
		System.out.println("[ Entered keys mapped to players:  " +  mapEnteredKeys + " ]");
		
	}
	
	public String findWinnerSimplerWayAlt() {

		String playerOneKey = mapEnteredKeys.get(gamePlayerOneName);			
		String playerOneChoice = mapSymbolsGamePlayerOne.get(playerOneKey);
		System.out.println( gamePlayerOneName + " played:  [" +  playerOneKey + "] --> " + playerOneChoice);
		
		String playerTwoKey = mapEnteredKeys.get(gamePlayerTwoName);
		String playerTwoChoice = mapSymbolsGamePlayerTwo.get(playerTwoKey);
		System.out.println( gamePlayerTwoName + " played:  [" +  playerTwoKey + "] --> " + playerTwoChoice);	
				
		// And find the winner.. 
		
		String result;
		
		if ( Objects.equals( mapSymbolsGamePlayerOne.get(playerOneKey),  options.paperName )  &
		     Objects.equals( mapSymbolsGamePlayerTwo.get(playerTwoKey),  options.rockName )   ||
		     
		     Objects.equals( mapSymbolsGamePlayerOne.get(playerOneKey),  options.rockName )   &
	         Objects.equals( mapSymbolsGamePlayerTwo.get(playerTwoKey),  options.scissorName ) ||
	         
	         Objects.equals( mapSymbolsGamePlayerOne.get(playerOneKey),  options.scissorName )   &
	         Objects.equals( mapSymbolsGamePlayerTwo.get(playerTwoKey),  options.paperName )
			
				)
			{
			   result = gamePlayerOneName;
			}
		
		else if ( Objects.equals( mapSymbolsGamePlayerOne.get(playerOneKey),  mapSymbolsGamePlayerTwo.get(playerTwoKey)) ) 
		{ 
			  result = "Tie";
		}
		
		else {
			  result = gamePlayerTwoName;	
		}
	
		   
		System.out.print( "The winner is:  " + result + "\n\n");
		return result;			   
		
	}
	
    
    /*  The alternative way of getting the result, using HashMaps and HashSets 
     * 
     * 
	public String findWinner() {
		
		//-------------------------------------
		//PRE-ASSIGNED FUNCTION KEYS TO PLAYERS
		//-------------------------------------
		HashMap<String, String> userOneDict = new HashMap<String, String>(); 
		userOneDict.put( options.controlsPlayerOne.get(0),  options.paperName);
		userOneDict.put( options.controlsPlayerOne.get(1),  options.rockName);
		userOneDict.put( options.controlsPlayerOne.get(2),  options.scissorName);
		
		HashMap<String, String> userTwoDict = new HashMap<String, String>(); 
		userTwoDict.put( options.controlsPlayerTwo.get(0), options.paperName);
		userTwoDict.put( options.controlsPlayerTwo.get(1), options.rockName);
		userTwoDict.put( options.controlsPlayerTwo.get(2), options.scissorName);
		//-------------------------------------
		
		  
		
		// ------------------------
		//  SETTING THE GAME RULES
		// ------------------------
		// using Map with String and Set
		// ref: https://stackoverflow.com/questions/44241813/how-to-add-multiple-values-to-single-key-in-java
		
		HashSet<String> paperRockSet = new HashSet<String>();
		paperRockSet.add( options.paperName );
		paperRockSet.add( options.rockName );
		
		HashSet<String> rockScissorsSet = new HashSet<String>();
		rockScissorsSet.add( options.rockName );
		rockScissorsSet.add( options.scissorName );
		
		HashSet<String> scissorsPaperSet = new HashSet<String>();	
		scissorsPaperSet.add( options.scissorName );
		scissorsPaperSet.add( options.paperName );
		
		
		HashMap<String, HashSet<String> > gameRules = new HashMap<String, HashSet<String> >(); 
		gameRules.put( options.paperName ,  paperRockSet);
		gameRules.put( options.rockName ,   rockScissorsSet);
		gameRules.put( options.scissorName, scissorsPaperSet);
		
		// ------------------------
		
		System.out.println("Chosen symbols: ");
		System.out.print("Player 1 = ");
		System.out.println(  userOneDict.get(  mapEnteredKeys.get(options.playerOneName)   ) );
		System.out.print("Player 2 = ");
		System.out.println(  userTwoDict.get(  mapEnteredKeys.get(options.playerTwoName)   ) );
		
		// Get key from value
		HashSet<String> playersSignsSet = new HashSet<String>();
		playersSignsSet.add( userOneDict.get(  mapEnteredKeys.get(options.playerOneName) ));
		playersSignsSet.add( userTwoDict.get(  mapEnteredKeys.get(options.playerTwoName) ));
		

		
		//ref: https://www.programiz.com/java-programming/examples/get-key-from-hashmap-using-value
		String winningRule = "tie";
		for(Entry<String, HashSet<String> > entry:gameRules.entrySet() ) {

			if (entry.getValue().equals(playersSignsSet)  ) {
				winningRule = entry.getKey();
				break;
            }
		}
		
		
		
		String theWinner = " ";
		
		if (Objects.equals( winningRule,  "Tie" ) ) {
			theWinner = winningRule;
		}
		else if (Objects.equals( userOneDict.get(  mapEnteredKeys.get(options.playerOneName) ) , winningRule) ) {
			//System.out.print( "Player 1" );
			theWinner = options.playerOneName;
		}
		else {
			//System.out.print( "Player 2" );
			theWinner = options.playerTwoName;
		}
		
		return theWinner;				
		
	}
	*/
	
	
	public static void main(String[] args) {
				
		GameFlow game = new GameFlow();

		game.displayStartMessage();
		game.configureOptions();
		
		boolean resultKnown = false;		
		String[] roundResult = new String[3];
		
		for (int i = 0; i < 3; ++i) {
			
			game.displayCounter();
			game.startRound();
			
			int roundNumber = 1 + i; 
			
		    System.out.println( "\n---------------------------------------");
			System.out.println( "Round " + roundNumber);
			System.out.println( "---------------------------------------");
			
			roundResult[i] = game.findWinnerSimplerWayAlt();
			
			
			// Check if we have a winner or a tie in the first two rounds, and finish the game
			if ( i == 1 & Objects.equals(roundResult[0], "Tie") & Objects.equals(roundResult[1], "Tie")) {
				System.out.println("There is no winner this time..");
				resultKnown = true;
				break;
			}
			else if (i == 1 & Objects.equals(roundResult[0], roundResult[1]) ) {
				System.out.println("The Game winner is:  " + roundResult[0] );
				resultKnown = true;
				break;
			}   
			
		}
		
		System.out.println( "=======================================");
		// All three rounds are played, check the outcome.
		// (I am sure there is a better way to do this.. )
		if( resultKnown == false ) {
			
			int playerOneFreq = 0;
			int playerTwoFreq = 0;
			
			for (int j = 0; j < 3; ++j) {
				
				if (Objects.equals(roundResult[j], game.gamePlayerOneName)) {
					playerOneFreq += 1;
				} 
				else if (Objects.equals(roundResult[j], game.gamePlayerTwoName) ) {
					playerTwoFreq +=1;
				} 
			}
			
			if(playerOneFreq == playerTwoFreq) {
				System.out.println("Game result: Tie " );
			}
			
			else if(playerOneFreq > playerTwoFreq) {
				System.out.println("THE GAME WINNER: " + game.gamePlayerOneName );
			} 
			else {
				System.out.println("THE GAME WINNER: " + game.gamePlayerTwoName );
			}
			
		}
		System.out.println( "=======================================");

	}
		
}
