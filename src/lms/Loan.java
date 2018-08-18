package lms;

import java.util.ArrayList;
import java.util.Date;

public class Loan {
	// declaring variables for loan object
	private long sanction,rate_of_interest,interest,interest_paid,principal_os,writeoff,soa_balance;
	// interest - is charged only when loan amount is disbursed
	// principal_os - is total principle  which is outstanding for the loan account

	private int customer_id;
	private String status;
	//Active & Closed will be defined for every loan

	private Date date;
	public static long total_sanction,total_interest,total_interest_paid,total_principal_os,total_write_off;
	public static int active,total_loans;

	// account statement to be logged for every loan
	private ArrayList<Statement> account_statement = new ArrayList<Statement>();

	public Loan(Date date,long sanction,long rate_of_interest,int customer_id){
		this.date =date; this.sanction =sanction; this.rate_of_interest =rate_of_interest;this.customer_id =customer_id;
		status = "Active";

		// increasing global counters;
		total_sanction+=sanction;
		active++;
		total_loans++;
	}
	public void show(){
		System.out.println("Cust_ID :"+customer_id +"| Date : "+date+"| Sanction : "+sanction+"| POS : "+principal_os+"| rate "+rate_of_interest+"%| interest : "+interest+"| Interest paid :" +interest_paid+"  ||"+status +"|write off : " +writeoff);

	}
	public int ID(){
		return this.customer_id;
	}
	public void disburse (long amount, Bank B){
		// Check if the bank has enough balance before disbursing
		if(B.balance()>amount){

			//check whether disbursal request is within sanction limit
			if ((this.sanction-this.principal_os-this.writeoff>=amount)&&(this.status.equals("Active"))){
				//update variables - increase principal outstanding amount
				principal_os+=amount;
				interest+= ((rate_of_interest*amount)/100);
				// update static variables
				total_interest +=((rate_of_interest*amount)/100);
				total_principal_os +=amount;
				//deduct money from bank
				B.disburse(amount);
				System.out.println("Congratulations !! Your disbursal was successful !");
				//recording in statement
				// transfer
				soa_balance+=amount;
				Statement s_pay= new Statement(new Date(),"Amt  Transferred     ",  "Debit ",amount,soa_balance);
				account_statement.add(s_pay);
				// billed
				soa_balance-=amount;
				Statement s_bill = new Statement(new Date(),"Billed: Principle amt","Credit",amount,soa_balance);
				account_statement.add(s_bill);


			} else {
				System.out.println("Sorry ! your disbursal request is more than the sanction limit !  ");
				System.out.println("OR you are trying to disburse from a closed account");
			}
		}else {
			System.out.println("Unfortunately, Our funds have been exhausted. Please try later");
		}
	}
	public void repay (long amount, Bank B) {
		// transfer cash in Bank
		B.repayment(amount);

		// check the condition for a valid repayment
		if (principal_os+interest-interest_paid>=amount){

			if(principal_os+interest-interest_paid==amount){
				// closing the loan account & updating count
				status = "Closed";
				active--;
			}

			//split repayment amount by interest rate and principle
			double principle_r = (amount*100)/(rate_of_interest+100);
			long interest_r = amount - (long)principle_r;
			// update loan instance variables
			principal_os -=principle_r;
			interest_paid += interest_r;

			// update static loan variables
			total_principal_os-=principle_r;
			total_interest_paid+=interest_r;

			// billed
			soa_balance+=(long)principle_r;
			Statement s_bill = new Statement(new Date(),"Part principle billed  ",  "Debit ",(long)principle_r,soa_balance);
			account_statement.add(s_bill);
			// repay principle
			soa_balance-=principle_r;
			Statement s_repay = new Statement(new Date(),"Principle repayment","Credit",(long)principle_r,soa_balance);
			account_statement.add(s_repay);

			// billed
			soa_balance+=interest_r;
			Statement s_ib = new Statement(new Date(),"Part interest amt bill",  "Debit ",interest_r,soa_balance);
			account_statement.add(s_ib);
			// repay interest
			soa_balance-=interest_r;
			Statement s_i = new Statement(new Date(),"Interest repayment","Credit",interest_r,soa_balance);
			account_statement.add(s_i);


		} else if( principal_os+interest-interest_paid<amount  ){
			// when excess payment done by customer >> it will be attributed to interest
			long excess =  amount -(principal_os+interest-interest_paid );
			soa_balance+=principal_os;
			Statement s_bill = new Statement(new Date(),"Part principle billed  ",  "Debit ",principal_os,soa_balance);
			account_statement.add(s_bill);
			// repay principle
			soa_balance-=principal_os;
			Statement s_repay = new Statement(new Date(),"Principle repayment","Credit",principal_os,soa_balance);
			account_statement.add(s_repay);

			// billed
			long interest_rp = amount - principal_os;
			soa_balance+=interest_rp;
			Statement s_ib = new Statement(new Date(),"Part interest amt bill",  "Debit ",interest_rp,soa_balance);
			account_statement.add(s_ib);
			// repay interest
			soa_balance-=interest_rp;
			Statement s_i = new Statement(new Date(),"Interest repayment","Credit",interest_rp,soa_balance);
			account_statement.add(s_i);

			total_principal_os -= principal_os;
			total_interest_paid +=(amount-principal_os);
			principal_os = 0;
			interest_paid = interest + excess;
			// closing the loan account & updating count
			if(status=="Active"){
				status = "Closed";
				active--;
			}
		}
		System.out.println("Thank you !! Your repayment was successful !");
	}
	public void write_off(){
		if(status=="Active"){
			writeoff=principal_os;
			total_write_off += writeoff;
			total_principal_os -=  principal_os;
			principal_os = 0;
			long extra = interest-interest_paid;
			total_interest -= extra;
			interest = interest_paid;
			status = "Closed";
			active--;
			System.out.println("This loan has been written-off");
		} else{
			System.out.println("The loan cannot be written-off ! It is already in a Closed state");
		}
	}

	public void print_statement(){
		System.out.println("*****Loan details ***********");
		System.out.println("Cust_ID :"+customer_id +"| Date : "+date+"| Sanction : "+sanction+"| POS : "+principal_os+"| rate "+rate_of_interest+"%| interest : "+interest+"| Interest paid :" +interest_paid+"  ||"+status +"|write off : " +writeoff);
		System.out.println("*****Account statement*****");
		for (Statement s : account_statement){
			System.out.println(s.getD() + "    ::   "+s.getNarration() + "    ::   "+s.getType() + "    ::  value  $ "+s.getAmount()+ "    :: Balance   $ "+s.getBalance());
		}
	}
}