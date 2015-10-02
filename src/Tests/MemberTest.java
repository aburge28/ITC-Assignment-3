package Tests;

import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

import library.interfaces.entities.ILoan;
import library.interfaces.entities.IBook;
import library.interfaces.entities.IMember;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.mockito.*;
import org.junit.*;

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
import library.entities.Book;
import library.entities.BookStub;
import library.entities.Loan;
import library.interfaces.daos.IMemberHelper;
import library.interfaces.daos.MemberDAO;
import library.interfaces.entities.ELoanState;

import static org.junit.Assert.*;

public class MemberTest {
	IBook book;
	String firstName = "firstName";
	String lastName = "lastName";
	String contactPhone = "contactPhone";
	String emailAddress = "emailAddress";
	int memberID = 1;
	IMember borrower;
	Date borrowDate;
	Date returnDate;
	Calendar _cal;
	static ILoan _loan;
	Loan loan_;
	ELoanState state;
	
	String author;
	String title;
	String callNumber;
	int bookID;
	
	IMemberHelper helper;

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
		helper = Mockito.mock(IMemberHelper.class);
		_cal = Calendar.getInstance();
		borrowDate = _cal.getTime();
		_cal.add(Calendar.DATE, ILoan.LOAN_PERIOD);
		returnDate = _cal.getTime();
		
		author = "author";
		title = "title";
		callNumber = "callNumber";
		bookID = 2;
		
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Tests the constructor in IMember.
	 * Test should pass if the given parameter are correct and the created constructor is an instance of Member.
	 */
	@Test
	public void testMember() {
		IMember member = new Member(firstName, lastName, contactPhone, emailAddress, memberID);
		assertTrue(member instanceof Member);
	}
	
	/**
	 * Tests if the Illegal argument exception is thrown when the first name is left null.
	 * Test should pass if the Illegal argument is thrown
	 */
	@Test (expected = IllegalArgumentException.class)
	public void firstNameNull() {
		IMember member = new Member(null, lastName, contactPhone, emailAddress, memberID);
	}
	
	/**
	 * Tests if the Illegal argument exception is thrown when the last name is left null.
	 * Test should pass if the Illegal argument is thrown
	 */
	@Test (expected = IllegalArgumentException.class)
	public void lastNameNull() {
		IMember member = new Member(firstName, null, contactPhone, emailAddress, memberID);
	}
	
	/**
	 * Tests if the Illegal argument exception is thrown when the contact phone is left null.
	 * Test should pass if the Illegal argument is thrown
	 */
	@Test (expected = IllegalArgumentException.class)
	public void contactPhoneNull() {
		IMember member = new Member(firstName, lastName, null, emailAddress, memberID);
	}
	
	/**
	 * Tests if the Illegal argument exception is thrown when the email address is left null.
	 * Test should pass if the Illegal argument is thrown
	 */
	@Test (expected = IllegalArgumentException.class)
	public void emailAddressNull() {
		IMember member = new Member(firstName, lastName, contactPhone, null, memberID);
	}
	
	/**
	 * Tests if the Illegal argument exception is thrown when the member id is less than zero.
	 * Test should pass if the Illegal argument is thrown
	 */
	@Test (expected = IllegalArgumentException.class)
	public void memberIdLessThanZero() {
		IMember member = new Member(firstName, lastName, contactPhone, emailAddress, -1);
	}
	
}
