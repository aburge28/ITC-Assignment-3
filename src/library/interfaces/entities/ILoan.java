package library.interfaces.entities;

import java.util.Date;

public interface ILoan {
	
	public static final int LOAN_PERIOD = 14;
	
	
	public void commit(int id);
	
	public void complete();
	
	public static boolean isOverDue() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean checkOverDue(Date currentDate);
	
	public IMember getBorrower();
	
	public IBook getBook();
	
	public int getID();
	

}
