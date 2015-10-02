package library;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import library.interfaces.daos.ILoanDAO;
import library.interfaces.entities.EBookState;
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
	private List<ILoan> loanList_;

	public Member (String firstName, String lastName, String contactPhone, String emailAddress, int memberID) {
		if ( !sane(firstName, lastName, contactPhone, emailAddress, memberID)) {
			throw new IllegalArgumentException("Member: constructor : bad parameters");
		}
		firstName_ = firstName;
		lastName_ = lastName;
		contactPhone_ = contactPhone;
		emailAddress_ = emailAddress;
		Id_ = memberID;
		loanList_ = new ArrayList<ILoan>();
		totalFines_ = 0.0f;
		state_ = EMemberState.BORROWING_ALLOWED;
	}

	private boolean sane(String firstName, String lastName, String contactPhone,
			String emailAddress, int memberID) {
		return  ( firstName != null    && !firstName.isEmpty()    &&
				lastName != null     && !lastName.isEmpty()     &&
				contactPhone != null && !contactPhone.isEmpty() &&
				emailAddress != null && !emailAddress.isEmpty() &&
				memberID > 0 
				);
	}

	@Override
	public boolean hasOverDueLoans() {
		for (ILoan loan : loanList_) {
			if (ILoan.isOverDue()) {
				return true;
			}
		}
		return false;
	} 

	@Override
	public boolean hasReachedLoanLimit() {
		boolean bl = loanList_.size() >= IMember.LOAN_LIMIT;
		return bl;
	}

	@Override
	public boolean hasFinesPayable() {
		boolean bl = totalFines_ > 0.0f;
		return bl;
	}

	@Override
	public boolean hasReachedFineLimit() {
		boolean bl = totalFines_ >= IMember.FINE_LIMIT;
		return bl;
	}

	@Override
	public float getFineAmount() {
		return totalFines_;
	}

	@Override
	public void addFine(float fine) {
		if (fine < 0) {
			throw new RuntimeException(String.format("The fine cannot be negative"));
		}
		totalFines_ += fine;
		updateState();
	}

	@Override
	public void payFine(float payment) {
		if (payment < 0 || payment > totalFines_) {
			throw new RuntimeException(String.format("Payment cannot be grater than the fine or negative"));
		}
		totalFines_ -= payment;
		updateState();
	}

	@Override
	public void addLoan(ILoan loan) {
		if (!borrowingAllowed()) {
			throw new RuntimeException(String.format("Member: addLoan: Illegal operation in state: %s", state_));
		}
		loanList_.add(loan);
		updateState();
	}

	@Override
	public List<ILoan> getLoans() {
		return Collections.unmodifiableList(loanList_);
	}

	@Override
	public void removeLoan(ILoan loan) {
		if (loan == null || !loanList_.contains(loan)) {
			throw new RuntimeException(String.format("loan is null or cannot be found in loanList"));
		}
		loanList_.remove(loan);
		updateState();
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

	public int getID() {
		return Id_;
	}

	@Override
	public String toString() {
		return String.format(
				"Id: %d\nName: %s %s\nContact Phone: %s\nEmail: %s\nOutstanding Charges: %0.2f", Id_,
				firstName_, lastName_, contactPhone_, emailAddress_, totalFines_);
	}

	private boolean borrowingAllowed() {
		boolean bl = !hasOverDueLoans() &&
				!hasReachedFineLimit() &&
				!hasReachedLoanLimit();
		return bl;

	}

	private void updateState() {
		if (borrowingAllowed()) {
			state_ = EMemberState.BORROWING_ALLOWED;
		}
		else {
			state_ = EMemberState.BORROWING_DISALLOWED;
		}
	}
}
