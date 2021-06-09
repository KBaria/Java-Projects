import java.util.ArrayList;
import java.util.Random;

public class Bank {
	
	// name of the bank
	private String name;
	
	// list of the User(s)
	private ArrayList<User> users;
	
	// list of the Account(s)
	private ArrayList<Account> accounts;
	
	// Bank constructor
	public Bank(String name) {
		
		this.name = name;
		
		// initialize an empty list for the users and accounts 
		this.users = new ArrayList<User>();
		this.accounts = new ArrayList<Account>();
		
	}
	
	// generate a new UUID for the User
	public String getNewUserUUID() {
		
		String uuid;
		Random rng = new Random();
		int len = 6;
		boolean nonUnique;
		
		do {
			uuid = "";
			for (int c=0; c<len; c++) {
				uuid += ((Integer)rng.nextInt(10)).toString();
			}
			nonUnique = false;
			for (User u : this.users) {
				if (uuid.compareTo(u.getUUID()) == 0) {
					nonUnique = true;
					break;
				}
			}
		
		}while(nonUnique == true);
		
		return uuid;
	}
	
	// generate a new UUID for an Account
	public String getNewAccountUUID() {
		
		String uuid;
		Random rng = new Random();
		int len = 10;
		boolean nonUnique;
		
		do {
			uuid = "";
			for (int c=0; c<len; c++) {
				uuid += ((Integer)rng.nextInt(10)).toString();
			}
			nonUnique = false;
			for (Account a : this.accounts) {
				if (uuid.compareTo(a.getUUID()) == 0) {
					nonUnique = true;
					break;
				}
			}
		
		}while(nonUnique == true);
		
		return uuid;
	}
	
	// add an Account to the Bank's accounts list
	public void addAccount(Account anAccount) {
		
		this.accounts.add(anAccount);
		
	}
	
	// add a User to the users list
	public User addUser(String firstName, String lastName, String pin) {
		
		// create a new User and add it to the list
		User newUser = new User(firstName, lastName, pin, this);
		this.users.add(newUser);
		
		// create a savings account for the User
		Account newAccount = new Account("savings", newUser, this);
	
		// add this Account to the User and Bank list
		newUser.addAccount(newAccount);
		this.addAccount(newAccount);
		
		return newUser;
	}
	
	// User log in
	public User userLogin(String userID, String pin) {
		
		// search through list of users
		for (User u : this.users) {
			if (u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)) {
				return u;
			}	
		}
		
		return null;
	}
	
	// return the name of the Bank
	public String getName() {
		return this.name;
	}
}
