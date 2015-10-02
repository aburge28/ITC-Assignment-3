package Tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import library.entities.Book;
import library.interfaces.daos.BookHelper;
import library.interfaces.daos.IBookHelper;
import library.interfaces.entities.IBook;

public class BookHelperTest {
	IBookHelper testBookHelper;
	String testAuthor = "author";
	String testTitle = "title";
	String testCallNumber = "callNumber";
	int ID = 1;
	IBook testBook;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		testBookHelper = Mockito.mock(BookHelper.class);
		testBook = Mockito.mock(Book.class);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMakeBook() {
		testBookHelper.makeBook(testAuthor, testTitle, testCallNumber, ID);
		assertTrue(testBook instanceof BookHelper);
	}

}
