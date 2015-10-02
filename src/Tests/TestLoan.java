
package Tests;
import org.mockito.*;
import org.junit.*;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import library.interfaces.entities.IBook;
import library.Member;
import library.entities.BookStub;
import library.entities.Loan;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;
import library.interfaces.entities.EBookState;
import library.interfaces.entities.ELoanState;

public class TestLoan {
	IBook book;
	IMember borrower;
	Date borrowDate;
	Date returnDate;
	Calendar _cal;
	static ILoan _loan;
	Loan loan_;
	ELoanState state;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		book = Mockito.mock(IBook.class);
		borrower = Mockito.mock(IMember.class);
		_cal = Calendar.getInstance();
		borrowDate = _cal.getTime();
		_cal.add(Calendar.DATE, ILoan.LOAN_PERIOD);
		returnDate = _cal.getTime();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * Tests the constructor in class Loan that each object made is a Loan object.
	 * Test should pass if the create loan is an instance of Loan class.
	 */
	@Test
	public void testCreate() {
		ILoan loan = new Loan(book, borrower, borrowDate, returnDate);
		assertTrue(loan instanceof Loan);
	}
	
	/**
	 * Tests the Illegal argument exception of the book being borrowed is null.
	 * Test should pass if the book being null throws an exception.
	 */
	@Test (expected = IllegalArgumentException.class)
	public void testBookNull() {
		ILoan loan = new Loan(null, borrower, borrowDate, returnDate);
	}
	
	/**
	 * Tests the Illegal argument exception of the borrower borrowing is null.
	 * Test should pass if the borrower being null throws an exception.
	 */
	@Test (expected = IllegalArgumentException.class)
	public void testBorrowerNull() {
		ILoan loan = new Loan(book, null, borrowDate, returnDate);
	}
	
	/**
	 * Tests the Illegal argument exception of when the date the book is being borrowed is null.
	 * Test should pass if the borrowing date being null throws an exception.
	 */
	@Test (expected = IllegalArgumentException.class)
	public void borrowDateNull() {
		ILoan loan = new Loan(book, borrower, null, returnDate);
	}
	
	/**
	 * Tests the Illegal argument exception of the book being returned on a null date.
	 * Test should pass if the returning date being null throws an exception.
	 */
	@Test (expected = IllegalArgumentException.class)
	public void returnDateNull() {
		ILoan loan = new Loan(book, borrower, borrowDate, null);
	}
	
	/**
	 * Tests the Illegal argument exception of the instance where the book is returned before it's borrowed.
	 * Test should pass if the book being returned before it's borrowed throws an exception.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void returnedBeforeBorrowDate() {
		ILoan loan = new Loan(book, borrower, returnDate, borrowDate);
	}
	
	/**
	 * Tests what will happen if the borrow date is the same as the return date
	 * Since there's no exceptions in the Loan class, the the loan will still be processed
	 */
	@Test()
	public void sameReturnDate() {
		ILoan loan = new Loan(book, borrower, returnDate, returnDate);
		assertTrue(loan instanceof Loan);
	}

	/**
	 * Tests what will happen if the loan ID entered is negative
	 * Test should pass if it encounters a Runtime Exception
	 */
	@Test(expected = RuntimeException.class)
	public void testLoanWithNegativeLoanID() {
	loan_.commit(-1);
	}
	
	/**
	 * Tests the state being overdue when it's not will return an exception.
	 * Test should pass if it encounters a RuntimeException
	 */
	@Test(expected = RuntimeException.class)
	public void overDueTest() {
		loan_.checkOverDue(returnDate);
		state = state.OVERDUE;
	}
	
	/**
	 * Tests the loan class when it's just passed the complete method.
	 * In the complete method, the loan state needs to be in state.COMPLETE
	 * Test passes if it encounters a Runtime Exception
	 */
	@Test(expected = RuntimeException.class)
	public void testCurrentPending() {
		_loan.complete();
		state = state.PENDING;
	}
	
	/**
	 * Tests the loan class on an overdue loan.
	 * The state is set to current, when it should be set to state.OVERDUE
	 * Test passes if it encounters a Runtime Exception
	 */
	@Test(expected = RuntimeException.class)
	public void testOverDue() {
		loan_.isOverDue();
		state = state.CURRENT;
	}
}
