import java.util.Scanner;

public class ATM {
	public static void main(String[] args) {

		// initialise Scanner
		Scanner sc = new Scanner(System.in);

		// initialise Bank
		Bank theBank = new Bank("Bank Of Dank");

		// add a User to the Bank which also creates a savings account
		User aUser = theBank.addUser("John", "Wick", "7542");

		// create a checking account for the User
		Account newAccount = new Account("checking", aUser, theBank);
		aUser.addAccount(newAccount);
		theBank.addAccount(newAccount);

		User curUser;
		while (true) {

			// stay in login prompt until successful login
			curUser = ATM.mainMenuPrompt(theBank, sc);

			// stay in main menu until user quits
			ATM.printUserMenu(curUser, sc);

		}

	}

	// keep the login prompt until a correct user is found
	public static User mainMenuPrompt(Bank theBank, Scanner sc) {

		String userID;
		String pin;
		;
		User authUser;

		// prompt the User for userID & pin until a correct one is reached
		do {

			System.out.printf("\n\nWelcome to %s\n\n", theBank.getName());
			System.out.print("Enter user ID: ");
			userID = sc.nextLine();
			System.out.print("Enter pin: ");
			pin = sc.nextLine();

			// try to get the User object corresponding to the ID & pin
			authUser = theBank.userLogin(userID, pin);
			if (authUser == null) {
				System.out.print("Incorrect userID or pin, please try again");
			}

		} while (authUser == null); // keep looping until a User has entered correct userID & pin

		return authUser;

	}

	// print the user main menu
	public static void printUserMenu(User theUser, Scanner sc) {

		// print a summary of the User's account
		theUser.printAccountsSummary();

		// initialise
		int choice;

		// looping the user menu until a valid choice is made
		do {

			System.out.printf("\n\nWelcome %s %s what would you like to do?\n\n", theUser.getFirstName(),
					theUser.getLastName());
			System.out.println("  1) Show account transaction history");
			System.out.println("  2) Make a withdraw");
			System.out.println("  3) Make a Deposit");
			System.out.println("  4) Make a transaction");
			System.out.println("  5) Quit");
			System.out.print("\nEnter your choice: ");
			choice = sc.nextInt();

			if (choice < 1 || choice > 5) {
				System.out.println("You better enter a goddamn valid number!");
			}

		} while (choice < 1 || choice > 5);

		// calling method corresponding to the choice
		switch (choice) {
		case 1:
			ATM.showTransactionHistory(theUser, sc);
			break;

		case 2:
			ATM.withdrawFunds(theUser, sc);
			break;

		case 3:
			ATM.depositFunds(theUser, sc);
			break;

		case 4:
			ATM.transferFunds(theUser, sc);
			break;

		}

		// redisplay the menu unless the User wants to quit
		if (choice != 5) {
			ATM.printUserMenu(theUser, sc);
		}
	}

	// showing the transaction history
	public static void showTransactionHistory(User theUser, Scanner sc) {

		int theAcct;

		// get the account to show transactions of
		do {
			System.out.printf("Enter the number of the account (1 - %d)\n", theUser.numAccounts());
			theAcct = sc.nextInt() - 1;

			if (theAcct < 0 || theAcct >= theUser.numAccounts()) {
				System.out.println("No such account exists!");
			}
		} while (theAcct < 0 || theAcct >= theUser.numAccounts());

		// if we get a correct account print the transaction history
		theUser.printAcctTransactionHistory(theAcct);
	}

	// Transfer funds method
	public static void transferFunds(User theUser, Scanner sc) {

		// initialise
		int fromAcct;
		int toAcct;
		double amount;
		double acctBal;

		// get the account to transfer from
		do {
			System.out.printf("Enter the number of the account (1 - %d)\n", theUser.numAccounts());
			fromAcct = sc.nextInt() - 1;
			if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
				System.out.println("No such account exists!");
			}

		} while (fromAcct < 0 || fromAcct >= theUser.numAccounts());

		// getting the balance of the Account
		acctBal = theUser.getAcctBalance(fromAcct);

		// get the account to transfer to
		do {
			System.out.printf("Enter the number of the account (1 - %d)\n", theUser.numAccounts());
			toAcct = sc.nextInt() - 1;
			if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
				System.out.println("No such account exists!");
			}

		} while (toAcct < 0 | toAcct >= theUser.numAccounts());

		// getting the amount to transfer
		do {
			System.out.printf("Enter amount to transfer, max transferable amount = $%.02f\n", acctBal);
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("The amount to transfer cannot be negativge!");
			} else if (amount > acctBal) {
				System.out.println("Transfer amount exceeded!, you aint got that much money in your account");
			}
		} while (amount < 0 || amount > acctBal);

		// finally do the transfer
		String memo1 = String.format("Transfer to account : %s", theUser.getAcctUUID(toAcct));
		String memo2 = String.format("Transfer to account : %s", theUser.getAcctUUID(fromAcct));
		theUser.addAcctTransaction(fromAcct, -1 * amount, memo1);
		theUser.addAcctTransaction(toAcct, 1 * amount, memo2);

	}

	// withdraw funds from an Account
	public static void withdrawFunds(User theUser, Scanner sc) {

		// initialise
		int fromAcct;
		String memo;
		double amount;
		double acctBal;

		// get the account to withdraw from
		do {
			System.out.printf("Enter the number of the account (1 - %d)\n", theUser.numAccounts());
			fromAcct = sc.nextInt() - 1;
			if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
				System.out.println("No such account exists!");
			}

		} while (fromAcct < 0 | fromAcct >= theUser.numAccounts());

		// get the amount to withdraw
		acctBal = theUser.getAcctBalance(fromAcct);

		do {
			System.out.printf("Enter amount to withdraw, max transferable amount = $%.02f\n", acctBal);
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("The amount to withdraw cannot be negativge!");
			} else if (amount > acctBal) {
				System.out.println("Withdraw amount exceeded!, you aint got that much money in your account");
			}
		} while (amount < 0 || amount > acctBal);

		// gobble up the rest of the previous input lines?
		sc.nextLine();

		// get the memo
		System.out.print("Enter memo if you want :");
		memo = sc.nextLine();

		// finally do the withdraw
		theUser.addAcctTransaction(fromAcct, -1 * amount, memo);

	}

	// deposit funds to an Account
	public static void depositFunds(User theUser, Scanner sc) {

		// initialise
		int toAcct;
		String memo;
		double amount;
		double acctBal;

		// get the account to deposit to
		do {
			System.out.printf("Enter the number of the account (1 - %d)\n", theUser.numAccounts());
			toAcct = sc.nextInt() - 1;
			if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
				System.out.println("No such account exists!");
			}

		} while (toAcct < 0 | toAcct >= theUser.numAccounts());

		// get the amount to withdraw
		acctBal = theUser.getAcctBalance(toAcct);

		// get the amount to deposit
		do {
			System.out.printf("Enter amount to deposit, current amount = $%.02f\n", acctBal);
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("The amount to deposit cannot be negativge!");
			}
		} while (amount < 0);

		// gobble up the rest of the previous input lines?
		sc.nextLine();

		// get the memo
		System.out.print("Enter memo if you want :");
		memo = sc.nextLine();

		// finally make the deposit
		theUser.addAcctTransaction(toAcct, 1 * amount, memo);

	}
}

