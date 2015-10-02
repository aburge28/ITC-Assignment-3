package library;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import library.entities.Loan;
import library.hardware.CardReader;
import library.interfaces.EBorrowState;
import library.interfaces.IBorrowUI;
import library.interfaces.IBorrowUIListener;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.ILoanDAO;
import library.interfaces.daos.IMemberDAO;
import library.interfaces.entities.EBookState;
import library.interfaces.entities.EMemberState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;
import library.interfaces.hardware.ICardReader;
import library.interfaces.hardware.ICardReaderListener;
import library.interfaces.hardware.IDisplay;
import library.interfaces.hardware.IPrinter;
import library.interfaces.hardware.IScanner;
import library.interfaces.hardware.IScannerListener;

public class BorrowUC_CTL implements ICardReaderListener, 
									 IScannerListener, 
									 IBorrowUIListener {
	
	private int scanCount = 0;
	private JPanel previous;
	private ICardReader reader;
	private IScanner scanner; 
	private IPrinter printer; 
	private IDisplay display;
	private IBorrowUI ui;
	public EBorrowState state;
	private IBookDAO bookDAO;
	private IMemberDAO memberDAO;
	private ILoanDAO loanDAO;
	private List<IBook> bookList;
	private List<ILoan> loanList;
	private IMember borrower;
	private IBook book;
	
	
	//private String state;

	public BorrowUC_CTL(ICardReader reader, IScanner scanner, 
			IPrinter printer, IDisplay display,
			IBookDAO bookDAO, ILoanDAO loanDAO, IMemberDAO memberDAO ) {

		this.display = display;
		this.ui = new BorrowUC_UI(this);
		state = EBorrowState.CREATED;
	}
	
	public void initialise() {
		previous = display.getDisplay();
		display.setDisplay((JPanel) ui, "Borrow UI");		
	}
	
	public void close() {
		display.setDisplay(previous, "Main Menu");
	}

	@Override
	public void cardSwiped(int memberID) {
		borrower = memberDAO.getMemberByID(memberID);
		loanList = borrower.getLoans();
		if (borrower.getState() == EMemberState.BORROWING_ALLOWED) { //If a member exists and has no restrictions
			reader.setEnabled(false); //cardReader is disabled
			scanner.setEnabled(true); //Scanner is enabled
			ui.displayMemberDetails(borrower.getID(),  borrower.toString(), borrower.getContactPhone()); //borrower details displayed
			for (ILoan loan : loanList) {
				ui.displayExistingLoan(loan.toString()); //existing loans displayed
			}
			scanCount = loanList.size(); //scan count initialized as number of existing loans
			if (borrower.hasFinesPayable()) {
				ui.displayOutstandingFineMessage(borrower.getFineAmount()); //existing dine message displayed if relevant
			}	
			state = EBorrowState.SCANNING_BOOKS; //BorrowBookCTL state == SCANNING_BOOKS
			ui.setState(state); 
		}
		
		else { //If a member exists and has restrictions
			reader.setEnabled(false); //cardReader is disabled
			scanner.setEnabled(false); //scanner is disabled
			ui.displayMemberDetails(borrower.getID(), borrower.toString(), borrower.getContactPhone()); //borrower details displayed
			for (ILoan loan : loanList) {
				ui.displayExistingLoan(loan.toString()); //existing loans displayed
			}
			if (borrower.hasFinesPayable()) {
				ui.displayOutstandingFineMessage(borrower.getFineAmount()); //existing fine message displayed if relevant
			}
			if (borrower.hasReachedFineLimit()) {
				ui.displayOverFineLimitMessage(borrower.getFineAmount()); //over fine message displayed if relevant
			}
			if (borrower.hasOverDueLoans()) {
				ui.displayOverDueMessage();  //Overdue message displayed if relevant
			}
			if (borrower.hasReachedLoanLimit()) {
				ui.displayAtLoanLimitMessage(); //atLoanLimit message displayed if relevant
			}
			ui.displayErrorMessage("Member cannot borrow"); //borrowing restricted error message displayed	
			state = EBorrowState.BORROWING_RESTRICTED; //BorrowBookCTL state == BORROWING_RESTRICTED
			ui.setState(state);
		}
	}
	
	
	
	@Override
	public void bookScanned(int barcode) {
		book = bookDAO.getBookByID(barcode);
		if(book.getState() == EBookState.LOST) { //If a book is to be borrowed and cannot be found
			ui.displayScannedBookDetails(book.toString());
			reader.setEnabled(false); //cardReader is disabled		
			scanner.setEnabled(true); //scanner is enabled
			ui.displayErrorMessage("The book cannot be found");	//book not found error message displayed		
			state = EBorrowState.SCANNING_BOOKS; //BorrowBookCTL state == SCANNING_BOOKS
			ui.setState(state);
		}
		if(book.getState() == EBookState.ON_LOAN) { //A book to be scanned is not available
			ui.displayScannedBookDetails(book.toString());
			reader.setEnabled(false); //cardReader is disabled
			scanner.setEnabled(true); //scanner is enabled
			ui.displayErrorMessage("Book is not available"); //book not available message displayed			
			state = EBorrowState.SCANNING_BOOKS; //BorrowBookCTL state == SCANNING_BOOKS
			ui.setState(state);
		}
		
		if (scanCount == Member.LOAN_LIMIT) { //Scan count == LOAN_LIMIT
			ui.displayConfirmingLoan(loanList.toString()); //ConfirmingLoans panel is displayed
			reader.setEnabled(false); //cardReader is disabled
			scanner.setEnabled(false); //scanner is disabled
			scanCount += 1; //scanCount incremented by 1
			state = EBorrowState.CONFIRMING_LOANS; //BorrowBookCTL state == CONFIRMING_LOANS
			ui.setState(state);
		}
		
		if(loanList.contains(book.getID())) { //Scan book when book aleady scanned
			reader.setEnabled(false); //cardReader is disabled
			scanner.setEnabled(true); //scanner is enabled
			ui.displayErrorMessage("The book has aready been scanned"); //book already scanned error message displayed
			state = EBorrowState.SCANNING_BOOKS; //BorrowBookCTL state == SCANNING_BOOKS
			ui.setState(state);
		}
		
		if (scanCount < Member.LOAN_LIMIT) { //User scans book while they have less books than the loan limit
			reader.setEnabled(false); //cardReader is disabled
			scanner.setEnabled(true); //scanner is enabled
			scanCount += 1; //scanCount incremented by 1
			ui.displayPendingLoan(book.toString()); //scanned books details displayed
			ILoan loan = new Loan(book, borrower, null, null); //new (pending) loan created
			loanList.add(loan); //(pending) loan added to pending loan list
			ui.displayPendingLoan(loanList.toString()); //Current pending loan list displayed
			state = EBorrowState.SCANNING_BOOKS; //BorrowBookCTL state == SCANNING_BOOKS
			ui.setState(state);
		}
	}

	
	private void setState(EBorrowState state) {
		throw new RuntimeException("Not implemented yet");
	}

	@Override
	public void cancelled() {
		close();
	}
	
	@Override
	public void scansCompleted() {
		throw new RuntimeException("Not implemented yet");
	}

	@Override
	public void loansConfirmed() {
		throw new RuntimeException("Not implemented yet");
	}

	@Override
	public void loansRejected() {
		throw new RuntimeException("Not implemented yet");
	}

	private String buildLoanListDisplay(List<ILoan> loans) {
		StringBuilder bld = new StringBuilder();
		for (ILoan ln : loans) {
			if (bld.length() > 0) bld.append("\n\n");
			bld.append(ln.toString());
		}
		return bld.toString();		
	}

}
