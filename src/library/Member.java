package library;

import java.util.List;

import library.interfaces.daos.ILoanDAO;
import library.interfaces.entities.EMemberState;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

public class Member implements IMember {
	private String firstName_;
	private String lastName_;
	private String contactPhone_;
	private String emailAddress_;
	private int Id_;
	private float totalFines_;
	private EMemberState state_;
	private ILoan loanList_;


	public Member (String firstName, String lastName, String contactPhone, String emailAddress, int memberID) {
		firstName_ = firstName;
		lastName_ = lastName;
		contactPhone = contactPhone_;
		emailAddress = emailAddress_;
		memberID = Id_;
		if (firstName_== null || lastName_ == null || contactPhone_ == null || emailAddress_ == null || Id_ < 0) {
			throw new IllegalArgumentException ("Values cannot be null and Id cannot be less than zero");
		}
	}

	@Override
	public boolean hasOverDueLoans() {
		if (ILoan.isOverDue() == true) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean hasReachedLoanLimit() {
		if (((List<ILoan>) loanList_).size() > LOAN_LIMIT) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public boolean hasFinesPayable() {
		if (totalFines_ > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean hasReachedFineLimit() {
		if (totalFines_ > IMember.FINE_LIMIT) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public float getFineAmount() {
		return totalFines_;
	}

	@Override
	public void addFine(float fine) {
		fine = totalFines_++;
		if (fine < 0) {
			throw new IllegalArgumentException ("Amount is negative");
		}
	}

	@Override
	public void payFine(float payment) {
		payment = totalFines_--;
		if (payment < 0) {
			throw new IllegalArgumentException ("Amount paid is negative");
		}
		if (payment > totalFines_) {
			throw new IllegalArgumentException("Amount paid is more than the fines");
		}
	}

	@Override
	public void addLoan(ILoan loan) {
		((List<ILoan>) loanList_).add(loan);

	}

	@Override
	public List<ILoan> getLoans() {
		return (List<ILoan>) loanList_;
	}

	@Override
	public void removeLoan(ILoan loan) {
		((List<ILoan>) loanList_).remove(loan);

	}

	@Override
	public EMemberState getState() {
		return state_;
	}

	@Override
	public String getFirstName() {
		return firstName_;
	}

	@Override
	public String getLastName() {
		return lastName_;
	}

	@Override
	public String getContactPhone() {
		return contactPhone_;
	}

	@Override
	public String getEmailAddress() {
		return emailAddress_;
	}

	@Override
	public int getID() {
		return Id_;
	}
}
