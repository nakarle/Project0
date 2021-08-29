import java.util.*;

public class Game {
	//java.util stuff
	Scanner sc;
	Random r;
	
	//game variables
	int fish;
	int bait;
	int hooks;
	int money;
	
	//stats tracking
	int fishSold;
	int fishCaught;
	int baitUsed;
	int baitBought;
	int hooksUsed;
	int hooksBought;
	int moneyGained;
	int moneySpent;

	//constructor
	public Game() {
		sc = new Scanner(System.in);
		r = new Random();
		startMenu();
		
		sc.close();
	}
	
	public static void clearScreen() {
		for (int k = 0; k < 100; k++) System.out.println();
	}
	
	private void startGame() {
		clearScreen();
		System.out.println("Fishing Game");
		String input = "";
		
		fish = 0; money = 10; bait = 10; hooks = 10;
		fishSold = 0; fishCaught = 0; baitUsed = 0; baitBought = 0;
		hooksUsed = 0; hooksBought = 0; moneyGained = 0; moneySpent = 0;
		
		while (!input.equals("6")) {
			clearScreen();
			System.out.print("Fish:  " + fish);
			System.out.print("       ");
			System.out.print("Money: " + money);
			System.out.print("       ");
			System.out.print("Bait:  " + bait);
			System.out.print("       ");
			System.out.print("Hooks: " + hooks + "\n\n");
			System.out.println("Moves");
			System.out.println("1. Fish without Bait");
			System.out.println("2. Fish with Bait");
			System.out.println("3. Buy Bait");
			System.out.println("4. Buy Hooks");
			System.out.println("5. Sell Fish");
			System.out.println("6. Quit");
			input = trimString(askInput("Input: "));
			
			if (input.equals("1")) {
				if (hooks >= 1) {
					/*
					 * 40% chance to catch some fish
					 * if fish are caught
					 * 50% chance a hook is lost
					 * 50% chance to catch 1 fish
					 * 		25% chance to catch 2 fish
					 * 		25% chance to catch 3 fish
					 * 		avg: 1.75
					 * if fish are not caught
					 * 20% chance a hook is lost
					 * */
					
					if (chance(40)) {
						if (chance(50)) removeHooks(1);
						if (chance(50)) {
							addFish(1);
						} else if (chance(50)) {
							addFish(2);
						} else {
							addFish(3);
						}
					} else {
						if (chance(20)) removeHooks(1);
					}
				} else {
					System.out.println("No hooks available.");
				}
			} else if (input.equals("2")) {
				if (hooks >= 1 && bait >= 1) {
					/*
					 * 40% chance to catch some fish
					 * if fish are caught
					 * a bait is lost
					 * 50% chance a hook is lost
					 * 30% chance to catch 1 fish
					 * 		35% chance to catch 2 fish
					 * 		17.5% chance to catch 3 fish
					 * 		17.5% chance to catch 4 fish
					 * 		avg: 2.225
					 * if fish are not caught
					 * 20% chance a hook is lost
					 * */
					
					if (chance(60)) {
						if (chance(50)) removeHooks(1);
						removeBait(1);
						if (chance(30)) {
							addFish(1);
						} else if (chance(50)) {
							addFish(2);
						} else if (chance(50)) {
							addFish(3);
						} else {
							addFish(4);
						}
					} else {
						if (chance(20)) removeHooks(1);
					}
				} else {
					if (hooks == 0 && bait == 0) {
						System.out.println("No hooks or bait available.");
					} else if (hooks == 0) {
						System.out.println("No hooks available.");
					} else {
						System.out.println("No bait available.");
					}
				}
			} else if (input.equals("3")) {
				
				if (money >= 10) {
					removeMoney(10);
					addBait(10);
				} else System.out.println("Not enough money to buy bait, you need 10 dollars.");
				
			} else if (input.equals("4")) {
				
				if (money >= 10) {
					removeMoney(10);
					addHooks(5);
				} else System.out.println("Not enough money to buy hooks, you need 10 dollars.");
				
			} else if (input.equals("5")) {
				
				if (fish >= 1) {
					int total = 0;
					int sellPrice = 0;
					for (int k = 0; k < fish; k++) {
						sellPrice = r.nextInt(15);
						total += sellPrice;
						if (k % 3 == 0) System.out.println();
						System.out.print("Sold 1 fish for " + sellPrice + " dollars.     ");
					}
					System.out.println();
					removeFish(fish);
					addMoney(total);
				} else System.out.println("You have no fish to sell.");
				
			} else if (input.equals("6")) {
				System.out.println("Game Finished");
				endScreen();
				break;
			}
			System.out.println("\nPress Enter to Continue.");
			sc.nextLine();
		}
	}
	
	private void endScreen() {
		clearScreen();
		System.out.print("Fish:  " + fish);
		System.out.print("       ");
		System.out.print("Money: " + money);
		System.out.print("       ");
		System.out.print("Bait:  " + bait);
		System.out.print("       ");
		System.out.print("Hooks: " + hooks + "\n\n");
		System.out.print("Fish Sold:  " + fishSold);
		System.out.print("       ");
		System.out.print("Money Spent: " + moneySpent);
		System.out.print("       ");
		System.out.print("Bait Used:  " + baitUsed);
		System.out.print("       ");
		System.out.print("Hooks Used: " + hooksUsed + "\n\n");
		System.out.print("Fish Caught:  " + fishCaught);
		System.out.print("       ");
		System.out.print("Money Earned: " + moneyGained);
		System.out.print("       ");
		System.out.print("Bait Bought:  " + baitBought);
		System.out.print("       ");
		System.out.print("Hooks Bought: " + hooksBought + "\n\n");
		System.out.println("Press Enter to Return to Start Menu.");
		sc.nextLine();
		startMenu();
	}
	
	private void startMenu() {
		clearScreen();
		String input = "";
		while (!(input.equals("1") || input.equals("2") ||
				input.equals("startgame") || input.equals("exitgame"))) {
			System.out.println("Start Menu");
			System.out.println("1. Start Game");
			System.out.println("2. Exit Game");
			input = trimString(askInput("Input: "));
			System.out.println("\""+input+"\"");
		}
		if (input.equals("1") || input.equals("startgame")) {
			System.out.println("Starting Game");
			startGame();
		} else {
			System.out.println("Exitting Game");
		}
	}
	
	public void removeHooks(int n) {
		hooks -= n;
		hooksUsed += n;
		System.out.println("Lost " + n + " hooks.");
	}
	public void addHooks(int n) {
		hooks += n;
		hooksBought += n;
		System.out.println("Gained " + n + " hooks.");
	}
	public void removeBait(int n) {
		bait -= n;
		baitUsed += n;
		System.out.println("Lost " + n + " bait.");
	}
	public void addBait(int n) {
		bait += n;
		baitBought += n;
		System.out.println("Gained " + n + " bait.");
	}
	public void removeFish(int n) {
		fish -= n;
		fishSold += n;
		System.out.println("Lost " + n + " fish.");
	}
	public void addFish(int n) {
		fish += n;
		fishCaught += n;
		System.out.println("Gained " + n + " fish.");
	}
	public void removeMoney(int n) {
		money -= n;
		moneySpent += n;
		System.out.println("Lost " + n + " dollars.");
	}
	public void addMoney(int n) {
		money += n;
		moneyGained += n;
		System.out.println("Gained " + n + " dollars.");
	}
	
	public String askInput(String str) {
		String returnValue = "";
		System.out.print(str);
		if(sc.hasNextLine()){
		    returnValue = sc.nextLine();
		}
		
		return returnValue;
	}
	
	public boolean chance(int percent) {
		return (r.nextInt(100) < percent);
	}
	
	public String trimString(String str) {
		return str.replaceAll("\s+","").toLowerCase();
	}
}