import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
	
	// first name of the user
	private String firstName;
	
	// last name of the user
	private String lastName;
	
	// universal unique id
	private String uuid;
	
	// hash form of the pin MD5 hashing algorithm
	private byte pinHash[];
	
	// list of accounts the User has
	private ArrayList<Account> accounts;
	
	// User constructor
	public User(String firstName, String lastName, String pin, Bank theBank) {
		
		// set users first and last name
		this.firstName = firstName;
		this.lastName = lastName;
		
		// store the pin in hash form using MD5 algorithm
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			this.pinHash = md.digest(pin.getBytes());
		} catch (NoSuchAlgorithmException e) {
			System.err.println("NoSuchAlgorithmException caught");
			System.exit(1);
			e.printStackTrace();
		}
		
		// get new UUID for the User
		this.uuid = theBank.getNewUserUUID();
		
		// create empty list of accounts
		this.accounts = new ArrayList<Account>();
		
		// print log message
		System.out.printf("New user %s %s with ID %s created", firstName, lastName, uuid);
	}
	
	// add an Account to the User's accounts list
	public void addAccount(Account anAccount) {
		
		this.accounts.add(anAccount);
		
	}
	
	// get UUID of the User
	public String getUUID() {
		return this.uuid;
	}
	
	// validate the pin of the User
	public boolean validatePin(String aPin) {
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash);
		} catch (NoSuchAlgorithmException e) {
			System.err.println("NoSuchAlgorithmException caught");
			System.exit(1);
			e.printStackTrace();
		}
		return false;
	}
	
	// getting the first name of the User
	public String getFirstName() {
		return this.firstName;
	}
	
	// getting the last name
	public String getLastName() {
		return this.lastName;
	}
	
	// print account summary
	public void printAccountsSummary() {
		
		System.out.printf("\n\n%s %s's accounts summary\n\n", this.firstName, this.lastName);
		for (int a=0; a<this.accounts.size(); a++) {
			System.out.printf("\n  %d) %s", a+1, this.accounts.get(a).getSummaryLine());
		}
	}
	
	// return the number of accounts the user has
	public int numAccounts() {
		return this.accounts.size();
	}
	
	// printing the transaction history of a given Account
	public void printAcctTransactionHistory(int AcctIdx) {
		
		this.accounts.get(AcctIdx).printTransactionHistory();
	}
	
	// getting the balance of an Account
	public double getAcctBalance(int AcctIdx) {
		
		return this.accounts.get(AcctIdx).getBalance();
		
	}
	
	// getting the UUID of an Account
	public String getAcctUUID(int AcctIdx) {
		return this.accounts.get(AcctIdx).getUUID();
	}
	
	// adding a Transaction to an Account
	public void addAcctTransaction(int AcctIdx, double amount, String memo) {
		this.accounts.get(AcctIdx).addTransaction(amount, memo);
	}
}
