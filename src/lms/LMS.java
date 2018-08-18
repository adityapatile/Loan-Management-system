package lms;
import java.util.*;




public class LMS {
	public static void main(String[] args)  {
		Scanner input = new Scanner(System.in);
		ArrayList <Loan> loan_records = new ArrayList<Loan>();
		Bank BOA = new Bank();
		int code = 1;
		System.out.println("Welcome to Loan Management System (LMS)");
		System.out.print("Please enter the fund you would like to start with >> ");
		BOA.addcash(input.nextLong());
		System.out.println("Setting up your LMS account . . . . . . . . . ");

		while(code !=0){
			System.out.println("Welcome to LMS ");
			System.out.println( "Press 1 :: Loan system              Press 2 :: Bank Account");
			System.out.println( "Press 3 :: Guide to LMS             Press 0 :: Exit");
			code = input.nextInt();
			switch(code) //MAIN MENU
			{

			case 0 : break;
			case 1 :
				// Loan system

				while (code!=0 && code !=9) {

					System.out.println("LMS >> Loan system");
					System.out.println( "Press 1 :: Loan booking              Press 2 :: Loan disbursal ");
					System.out.println( "Press 3 :: Loan repayment         Press 4 :: Write-Off loan  ");
					System.out.println( "Press 5 :: Loan book report       Press 6 :: All Loan records");
					System.out.println( "Press 7 :: Statement of Account for customer                   ");
					System.out.println( "Press 9 :: Main menu                  Press 0 :: EXIT");
					code = input.nextInt();
					switch (code) //LOAN MENU
					{
					case 0 : break;
					case 9 : break;
					case 1:
						// ---------------LOAN BOOKING -----------------------------
						boolean dedupe =false;
						System.out.println("Enter customer ID");
						int l_customer_ID = input.nextInt();
						for (Loan s : loan_records){
							if (s.ID()==l_customer_ID){
								dedupe= true;
								System.out.println("This Customer is already exists in database ! Please try different ID");
							} }
						if ( !dedupe){
							Date l_date;
							l_date = new Date();
							System.out.println("Enter Sanction amount");
							long l_sanction = input.nextLong();
							System.out.println("Enter rate of interest (%)");
							long l_rate = input.nextLong();

							Loan l = new Loan(l_date, l_sanction, l_rate, l_customer_ID);
							loan_records.add(l);
							System.out.println("Congrats ! your loan has been booked");}
						System.out.println("------------------------------------------------------------");
						break;


					case 2:
						// ---------------LOAN DISBURSAL -----------------------------
						System.out.print("Please enter customer ID  : ");
						int count = 0;
						int ID = input.nextInt();
						for (Loan x : loan_records) {
							if (x.ID() == ID) {
								System.out.print("Please enter the disbursement amount : ");
								long disb = input.nextLong();
								x.disburse(disb, BOA);
								break;
							} else {
								count++;
							}
						}
						if (count == loan_records.size()) {
							System.out.println("Error 404 : Customer ID not found");
						}
						System.out.println("------------------------------------------------------------");
						break;
					case 3:
						// ---------------LOAN REPAYMENT -----------------------------
						System.out.print("Please enter customer ID  : ");
						int repay_count = 0;
						int repay_ID = input.nextInt();
						for (Loan x : loan_records) {
							if (x.ID() == repay_ID) {
								System.out.print("Please enter the repayment amount : ");
								long repay = input.nextLong();
								x.repay(repay, BOA);
								break;
							} else {
								repay_count++;
							}
						}
						if (repay_count == loan_records.size()) {
							System.out.println("Error 404 : Customer ID not found");
						}
						System.out.println("------------------------------------------------------------");
						break;
					case 4:
						// ---------------WRITE OFF -----------------------------
						System.out.print("Please enter customer ID  : ");
						int wo_count = 0;
						int wo_ID = input.nextInt();
						for (Loan x : loan_records) {
							if (x.ID() == wo_ID) {
								x.write_off();
								break;
							} else {
								wo_count++;
							}
						}
						if (wo_count == loan_records.size()) {
							System.out.println("Error 404 : Customer ID not found");
						}
						System.out.println("------------------------------------------------------------");
						break;
					case 5:
						// -------------- LOAN BOOK REPORT -----------------------------
						System.out.println("Details for loan book");
						System.out.println("Total loans : " + Loan.total_loans);
						System.out.println("Active loans : " + Loan.active);
						System.out.println("Total Sanction : " + Loan.total_sanction);
						System.out.println("Loan book size :  " + Loan.total_principal_os);
						System.out.println("Total interest charged  : " + Loan.total_interest);
						System.out.println("Total interest earned  : " + Loan.total_interest_paid);
						System.out.println("Total amount written off  (bad loans): " + Loan.total_write_off);
						System.out.println("------------------------------------------------------------");
						break;

					case 6:
						for (Loan x : loan_records) {
							x.show();
						}
						System.out.println("------------------------------------------------------------");
						break;
					case 7 :
						System.out.print("Please enter customer ID  : ");
						int soa_count = 0;
						int soa_ID = input.nextInt();
						for (Loan x : loan_records) {
							if (x.ID() ==soa_ID) {
								x.print_statement();
								break;
							} else {
								soa_count++;
							}
						}
						if (soa_count == loan_records.size()) {
							System.out.println("Error 404 : Customer ID not found");
						}
						System.out.println("------------------------------------------------------------");
						break;

					}
				}
				break;
			case 2 :
				// Bank Account
				while (code!=0 && code !=9) {
					System.out.println("LMS >> Bank Account");
					System.out.println( "Press 1 :: Bank Details                Press 2 ::Add money to bank account ");
					System.out.println( "Press 3 :: Withdraw money       Press 4 :: Bank Statement  ");
					System.out.println( "Press 9 :: Main menu                  Press 0 :: Exit ");
					code = input.nextInt();

					switch (code) //BANK MENU
					{
					case 0:
						break;
					case 9:
						break;
					case 1:
						System.out.println("Bank Name : " + BOA.name());
						System.out.println("Bank Balance : " + BOA.balance());
						System.out.println("------------------------------------------------------------");
						break;
					case 2:
						System.out.println("Enter Amount you want to add to bank account : ");
						long amt = input.nextLong();
						BOA.addcash(amt);
						System.out.println("------------------------------------------------------------");
						break;
					case 3:
						System.out.println("Enter Amount you want to withdraw from your bank account : ");
						long amt_wd = input.nextLong();
						BOA.withdraw(amt_wd);
						System.out.println("------------------------------------------------------------");
						break;
					case 4:
						BOA.print_statement();
						System.out.println("------------------------------------------------------------");
						break;
					}
				}
				break;
			case 3 :
				System.out.println("Q 1 : How to disburse loan ?");
				System.out.println("Ans : first book a loan in Loan system with a sanction of lets say 5000");
				System.out.println("Then, go ahead and initiate disbursal against this sanction - amount lower than 5000." );
				System.out.println("For disbursal & repayment- the current system uses customer ID . So note customer ID you enter while doing booking");
				System.out.println("------------------------------------------------------------");
				System.out.println("Q 2 : What is the Bank Account option ?");
				System.out.println("Ans : The system is designed for a lender." );
				System.out.println("The Bank account has logs and balances of all the disbursal & repayment into the account");
				System.out.println("------------------------------------------------------------");
				System.out.println("Q 3 : What is a write off option ? ");
				System.out.println("Ans : If any customer defaults on the loan and will not repay then as a lender you can write off the loan");
				System.out.println("It will set the principal outstanding value to 0, adjust this amount in write-off value & close the loan");
			}

		}
		input.close();
	}

}