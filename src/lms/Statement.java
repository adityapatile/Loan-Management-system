package lms;

import java.util.Date;

public class Statement {
	// object for logging bank statement and customer statement of account
	private Date d;
	private String narration;
	private String type;
	private long amount, balance;
	public Statement(Date d,String narration, String type,long amount, long balance){
		this.d= d;
		this.narration=narration;
		this.type=type;
		this.balance= balance;
		this.amount=amount;
	}
	public Date getD() {
		return d;
	}
	public String getType() {
		return type;
	}
	public long getBalance() {
		return balance;
	}
	public String getNarration() {
		return narration;
	}

	public long getAmount() {
		return amount;
	}
}