import java.util.ArrayList;

public class Account {
	
	// name or type of an account
	private String name;
	
	// universal unique id of the account
	private String uuid;
	
	// User of the account
	private User holder;
	
	// list of transactions the Account has
	private ArrayList<Transaction> transactions; 
	
	// Account constructor
	public Account(String name, User holder, Bank theBank) {
		
		// set name and User for the Account
		this.name = name;
		this.holder = holder;
		
		// get new UUID for the Account
		this.uuid = theBank.getNewAccountUUID();
		
		// make an empty list of transactions
		this.transactions = new ArrayList<Transaction>();
		
	}
	
	// get UUID of the Account
	public String getUUID() {
		return this.uuid;
	}
	
	// return the summary of an Account
	public String getSummaryLine() {
		
		// get the Account's balance
		double balance = this.getBalance();
		
		// format the summary line depending on whether the balance is negative
		if (balance >= 0) {
			return String.format("%s : $%.02f : %s", this.uuid, balance, this.name);
		}
		else {
			return String.format("%s : $(%.02f) : %s", this.uuid, balance, this.name);
		}
		
	}
	
	// returning the balance of the Account
	public double getBalance() {
		
		double balance = 0;
		for (Transaction t : this.transactions) {
			balance += t.getAmount();
		}
		return balance;
	}
	
	// printing the Transaction history of a given Account
	public void printTransactionHistory() {
		
		System.out.printf("\nTransaction history for account %s\n", this.uuid);
		for (int t = this.transactions.size() -1; t>=0; t--) {
			System.out.printf(this.transactions.get(t).getSummaryLine());
		}
		System.out.println();
	}
	
	// adding a transaction to an account
	public void addTransaction(double amount, String memo) {
		
		// create a new transaction object and add it to the list
		Transaction newTrans = new Transaction(amount, this, memo);
		this.transactions.add(newTrans);
	}
}
