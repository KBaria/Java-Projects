import java.util.Date;

public class Transaction {
	
	// amount of the Transaction
	private double amount;
	
	// date and time of the transaction
	private Date timestamp;
	
	// details of the transactions
	private String memo;
	
	// the Account on which the transaction is made
	private Account inAccount;
	
	// Transaction constructor if a memo is given
	public Transaction(double amount, Account inAccount) {
		
		this.amount = amount;
		this.inAccount = inAccount;
		this.timestamp = new Date();
		this.memo = "";
	}
	
	// Transaction constructor if a memo is given
	public Transaction(double amount, Account inAccount, String memo) {
		
		// call the two arguments constructor
		this(amount, inAccount);
		
		// set the details of the transaction in memo
		this.memo = memo;
		
	}
	
	// returning the amount of a Transaction
	public double getAmount() {
		return this.amount;
	}
	
	// summary of a Transaction made on an Account 
	public String getSummaryLine() {
		
		if(this.amount >= 0) {
			return String.format("\n%s :  $%.02f : %s\n", this.timestamp.toString(), this.amount, this.memo);
		}
		else {
			return String.format("\n%s :  -$%.02f : %s\n", this.timestamp.toString(), -this.amount, this.memo);
		}
	}
	 
}
