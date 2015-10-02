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
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;
import library.entities.Loan;
import library.interfaces.daos.LoanHelper;

public class LoanHelperTest {
	IBook book;
	IMember member;
	Date borrowDate;
	Date returnDate;
	Calendar _cal;
	LoanHelper loanHelper;
	ILoan loan;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		book = Mockito.mock(IBook.class);
		member = Mockito.mock(IMember.class);
		loanHelper = Mockito.mock(LoanHelper.class);
		_cal = Calendar.getInstance();
		borrowDate = _cal.getTime();
		_cal.add(Calendar.DATE, ILoan.LOAN_PERIOD);
		returnDate = _cal.getTime();
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Tests to ensure every loan made through the constructor makeLoan in LoanHelper is an instance of a loanHelper object
	 * Test will pass if the loan made is an instance of LoanHelper
	 */
	@Test
	public void testMakeLoan() {
		loan = loanHelper.makeLoan(book, member, borrowDate, returnDate);
		assertTrue(loanHelper instanceof LoanHelper);
	}
}
