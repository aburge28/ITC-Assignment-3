package Tests;

import static org.junit.Assert.*;
import org.junit.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.*;



import static org.mockito.Mockito.*;


import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.InjectMocks;



import library.BorrowUC_CTL;
import library.Member;
import library.entities.Book;
import library.entities.Loan;
import library.interfaces.daos.LoanMapDAO;
import library.interfaces.daos.MemberDAO;
import library.interfaces.entities.EBookState;
import library.interfaces.entities.EMemberState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;
import library.interfaces.hardware.ICardReader;

import library.interfaces.EBorrowState;
import library.interfaces.IBorrowUI;
import library.interfaces.daos.BookHelper;
import library.interfaces.daos.BookMapDAO;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.ILoanDAO;
import library.interfaces.daos.IMemberDAO;
import library.interfaces.hardware.ICardReader;
import library.interfaces.hardware.IDisplay;
import library.interfaces.hardware.IPrinter;
import library.interfaces.hardware.IScanner;

import library.interfaces.entities.IMember;
import library.interfaces.daos.IMemberDAO;

import java.util.List;
import java.util.Calendar;
import java.util.Date;


public class BorrowUC_CTLTest {
	static IMember member;
	static IBook book;
	static ILoan loan;
	static BorrowUC_CTL ctl;
	private static ICardReader reader;
	private IScanner scanner;
	private IPrinter printer;
	private IDisplay display;
	private int count = 1;
	private static IBookDAO bookDao;
	private static IMemberDAO memberDAO;
	private ILoanDAO loanDAO;
	static Calendar cal;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		member = Mockito.mock(IMember.class);
		memberDAO = Mockito.mock(IMemberDAO.class);
		book = Mockito.mock(IBook.class);
		loan = Mockito.mock(ILoan.class);
		reader = Mockito.mock(ICardReader.class);
		ctl = Mockito.mock(BorrowUC_CTL.class);
		bookDao = Mockito.mock(IBookDAO.class);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test for unrestricted users
	 * None of the booleans are true, meaning the member has nothing preventing them from borrowing
	 */
	@Test ()
	public void testBorrowUC_CTL() {
		//Returns values for the booleans in the control class
		IMember mem = Mockito.mock(IMember.class);
		when(memberDAO.getMemberByID(1)).thenReturn(mem);
		when(mem.hasOverDueLoans()).thenReturn(false);
		when (mem.hasFinesPayable()).thenReturn(false);
		when(mem.hasReachedFineLimit()).thenReturn(false);
		when(mem.hasReachedLoanLimit()).thenReturn(false);
		
		//Asserts the booleans tested for the class
		assertFalse(mem.hasOverDueLoans());
		assertFalse(mem.hasFinesPayable());
		assertFalse(mem.hasReachedFineLimit());
		assertFalse(mem.hasReachedLoanLimit());
	}
	
	/**
	 * Test for a retricted user
	 * In this example, a user has everything pevernting them form borrowing, meaning the borrowing state should be in BORROWING_RESTRICTED
	 */
	@Test ()
	public void testBorrowUC_CTLRestrictedUser() {
		//Returns values for the booleans in the control class
		IMember mem = Mockito.mock(IMember.class);
		when(memberDAO.getMemberByID(1)).thenReturn(mem);
		when(mem.hasOverDueLoans()).thenReturn(true);
		when(mem.hasFinesPayable()).thenReturn(true);
		when(mem.hasReachedFineLimit()).thenReturn(true);
		when(mem.hasReachedLoanLimit()).thenReturn(true);
		when(mem.getState()).thenReturn(EMemberState.BORROWING_DISALLOWED);
		
		//Asserts the booleans tested for the class
		assertTrue(mem.hasOverDueLoans());
		assertTrue(mem.hasFinesPayable());
		assertTrue(mem.hasReachedFineLimit());
		assertTrue(mem.hasReachedLoanLimit());
		EMemberState state = mem.getState();
		EMemberState cannotBorrow = EMemberState.BORROWING_DISALLOWED;
		assertEquals(state, cannotBorrow);
	}
	
	/**
	 * Test for borrowing book
	 * Test for borrowing a book which cannot be found
	 */
	@Test
	public void borrowBookWhichCannotBeFound() {
		IBook book = Mockito.mock(IBook.class);
		when(bookDao.getBookByID(1)).thenReturn(book);
		when(book.getState()).thenReturn(EBookState.LOST);
		assertEquals(book.getState(), EBookState.LOST);
	}
	
	/**
	 * Test for borrowing a book which is unavailable
	 */
	@Test
	public void borrowUnavailableBook() {
		//IBook book = Mockito.mock(IBook.class);
		when(bookDao.getBookByID(1)).thenReturn(book);
		when(book.getState()).thenReturn(EBookState.ON_LOAN);
		assertEquals(book.getState(), EBookState.ON_LOAN);
	}
	
	/**
	 * Test for borrowing a book when the user has less books on loan than the limit
	 */
	@Test
	public void borrowCommonCase() {
		IMember mem = Mockito.mock(IMember.class);
		IBook book = Mockito.mock(IBook.class);
		when(bookDao.getBookByID(1)).thenReturn(book);
		when(mem.hasReachedLoanLimit()).thenReturn(false);
		when(book.getState()).thenReturn(EBookState.AVAILABLE);
		
		assertFalse(mem.hasReachedLoanLimit());
		assertEquals(book.getState(), EBookState.AVAILABLE);
	}
	
	/**
	 * Test for borrowing a book when it reaches the scan limit
	 */
	@Test
	public void borrowOnLimit() {
		IMember mem = Mockito.mock(IMember.class);
		IBook book = Mockito.mock(IBook.class);
		when(mem.hasReachedLoanLimit()).thenReturn(true);
		when(book.getState()).thenReturn(EBookState.AVAILABLE);
		
		assertTrue(mem.hasReachedLoanLimit());
		assertEquals(book.getState(), EBookState.AVAILABLE);
	}
	
	/**
	 * Tests the constructor of BorrowUC_CTL
	 * Test should pass if the constructed object ctl is an instance of the class the contructor exists in
	 */
	@Test
	public void testCon() {
		ctl = new BorrowUC_CTL(reader, scanner, printer, display, bookDao, loanDAO, memberDAO);
		assertTrue(ctl instanceof BorrowUC_CTL);
	}
	
	/**
	 * Tests if the user can borrow a book if they are in the state of BORROWING_ALLOWED
	 * test should pass if the user can borrow a book
	 */
	@Test
	public void correctState() {
		Member mem = Mockito.mock(Member.class);
		when(memberDAO.getMemberByID(1)).thenReturn(mem);
		when (mem.getState()).thenReturn(EMemberState.BORROWING_ALLOWED);
		LoanMapDAO loanDao = Mockito.mock(LoanMapDAO.class);
		IMember member = memberDAO.addMember("firstName", "lastName", "ContactPhone", "emailAddress");
		ILoan loan = loanDao.createLoan(member, book);
		mem.addLoan(loan);
	}
}
