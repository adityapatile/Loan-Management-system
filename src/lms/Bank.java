package lms;

import java.util.ArrayList;
import java.util.Date;

public class Bank {
	// Creating array to store all statement objects for the bank
	private ArrayList<Statement> bank_statement = new ArrayList<Statement>();
	private  String name = "Bank of America";
	private long cash ;
	public void addcash (long cash){
		// method to add cash in the lender's Bank account
		this.cash += cash;
		System.out.println("Your transaction was successful ! ");

		// Logging activity in bank statement
		Statement s = new Statement(new Date(),"Self Funding..........","Credit",cash,this.cash);
		bank_statement.add(s);
	}

	public void repayment (long cash){
		//This module should only be used for repayments coming from customer
		this.cash += cash;
		System.out.println("Your repayment was successful ! ");
		// Logging repayment activity
		Statement s = new Statement(new Date(),"Repayment...........","Credit",cash,this.cash);
		bank_statement.add(s);

	}
	public void disburse(long cash){
		// Module called to disburse
		this.cash -=cash;
		//logging disbursal activity
		Statement s = new Statement(new Date(),"Disbursement........","Debit ",cash,this.cash);
		bank_statement.add(s);
	}
	public void withdraw(long cash){
		// method to be called for money withdrawal request from lender
		this.cash -=cash;
		//logging withdrawal activity
		Statement s = new Statement(new Date(),"Self withdrawal.....","Debit ",cash,this.cash);
		bank_statement.add(s);

	}
	public long balance(){
		return cash;
	}
	public String name(){
		return name;
	}
	public void print_statement(){
		for (Statement s : bank_statement){
			System.out.println(s.getD() + "    ::   "+s.getNarration() + "    ::   "+s.getType() + "    ::  value  $ "+s.getAmount()+ "    :: Closing balance   $ "+s.getBalance());

		}

	}

}