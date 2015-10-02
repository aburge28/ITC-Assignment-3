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
import library.entities.Book;
import library.entities.BookStub;
import library.entities.Loan;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;
import library.interfaces.entities.EBookState;
import library.interfaces.entities.ELoanState;


import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BookTest {
	Book book;
	EBookState state;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		book = Mockito.mock(Book.class);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBook() {
		IBook book = new Book ("author", "title", "callNumber", 1);
		assertTrue(book instanceof Book);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void bookWithNullAuthor() {
		IBook book = new Book(null, "title", "callNumber", 1);
	}

	@Test (expected = IllegalArgumentException.class)
	public void bookWithNullTitle() {
		IBook book = new Book("author", null, "callNumber", 1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void bookWithNullCallNumber() {
		IBook book = new Book("author", "title", null, 1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void bookWithNegativeID() {
		IBook book = new Book("author", "title", "callNumber", -1);
	}
}
