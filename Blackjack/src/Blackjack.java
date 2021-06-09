import java.util.Scanner;

public class Blackjack {

	public static void main(String[] args) {
		
		System.out.println("Welcome");
		
		Deck playingDeck = new Deck();
		playingDeck.createFullDeck();
		playingDeck.shuffle();
		
		Deck playerDeck = new Deck();
		Deck dealerDeck = new Deck();
		
		double playermoney = 100.00;
		
		Scanner uip = new Scanner(System.in);
		
		while(playermoney > 0) {
			System.out.println("You have" + playermoney + ", How much would you like to bet?");
			double playerbet = uip.nextDouble();
			if (playerbet > playermoney){
				System.out.println("You dont have that much money!");
				break;
			}
			
			boolean endRound = false;
			
			playerDeck.draw(playingDeck);
			playerDeck.draw(playingDeck);
			
			dealerDeck.draw(playingDeck);
			dealerDeck.draw(playingDeck);
			
			
			
			while(true) {
				System.out.println("Your Hand:");
				System.out.println(playerDeck.toString());
				System.out.println("Your hand value: " + playerDeck.cardsValue());
				
				System.out.println("Dealer Hand: " + dealerDeck.getCard(0).toString() + "[Hidden]");
				
				System.out.println("Would you like to (1) Hit or (2) Stand");
				int response = uip.nextInt();
				
				if (response == 1) {
					playerDeck.draw(playingDeck);
					System.out.println("You draw: " + playerDeck.getCard(playerDeck.deckSize()-1).toString());
					if(playerDeck.cardsValue() > 21) {
						System.out.println("Bust : " + playerDeck.cardsValue() + "You lose!");
						playermoney -= playerbet;
						endRound = true;
						break;
					}
				}
				if (response == 2) {
					break;
				}
			}
			System.out.println("Dealer Cards: "+ dealerDeck.toString());
			if((dealerDeck.cardsValue() > playerDeck.cardsValue()) && endRound == false){
				System.out.println("Dealer beats you!");
				playermoney -= playerbet;
				endRound = true;
			}
			while((dealerDeck.cardsValue() < 17)&& endRound == false) {
				dealerDeck.draw(playingDeck);
				System.out.println("Dealer Draws: " + dealerDeck.getCard(dealerDeck.deckSize()-1).toString());
			}
			System.out.println("Dealer's hand value: " + dealerDeck.cardsValue());
			
			if((dealerDeck.cardsValue() > 21) && endRound == false) {
				System.out.println("Dealer busted, you win!");
				playermoney += playerbet;
				endRound = true;
			}
			
			if ((playerDeck.cardsValue() == dealerDeck.cardsValue() && endRound == false)){
				System.out.println("Push!");
				endRound = true;	
			}
			
			if ((playerDeck.cardsValue() > dealerDeck.cardsValue() && endRound == false)){
				System.out.println("You win!");
				playermoney += playerbet;
				endRound = true;	
			}
			
			playerDeck.moveAllToDeck(playingDeck);
			dealerDeck.moveAllToDeck(playingDeck);
			System.out.println("End of hand");
		}
		System.out.println("You are out of money!");

	}

}
