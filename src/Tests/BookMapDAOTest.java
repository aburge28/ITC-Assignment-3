package Tests;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import library.interfaces.daos.BookMapDAO;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.IBookHelper;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

public class BookMapDAOTest {
	
	IBookHelper testHelper;
	IBookDAO testDao;

	int testID = 1;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		testHelper = Mockito.mock(IBookHelper.class);
		testDao = new BookMapDAO(testHelper);

	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testInstance() {
		BookMapDAO dao = new BookMapDAO(testHelper);
		assertTrue (dao instanceof IBookDAO);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testNullHelper() {
		BookMapDAO dao = new BookMapDAO(null);
	}
	
	/**
	@Test
	public void testCreateLoanDAO() {
		
		//setup
			String author = "author";
			String testTitle = "Title";
			String callNumber = "callNumber";
			int ID = 2;
		
		IBook expectedBook = mock(IBook.class);
		
		
		when(testHelper.makeBook(eq(author), eq(testTitle), eq(callNumber), eq(ID))).thenReturn(expectedBook);
		
		//execute
		IBook actualBook = testDao.addBook(author, testTitle, callNumber);
		
		//verifies and asserts
		verify(testHelper).makeBook(eq(author), eq(testTitle), eq(callNumber), eq(ID));
		assertEquals(expectedBook, actualBook);	
	}
	*/
	
	/**
	 * Tests if the class can search books by author name
	 * Test passes if Author isn't null
	 */
	@Test
	public void booksByAuthor() {
		String testAuthor = "author";
		testDao.findBooksByAuthor(testAuthor);
	}
	
	/**
	 * Tests if the illegal argument exception is thrown when the author is null when searchign books by author
	 * Test passes if the illegal argument exception is thrown
	 */
	@Test (expected = IllegalArgumentException.class)
	public void booksByAuthorNull() {
		testDao.findBooksByAuthor(null);
	}
	
	/**
	 * Tests if the class can search by books given the book title.
	 * Test passes if title isn't null.
	 */
	@Test
	public void booksByTitle() {
		String title = "title";
		testDao.findBooksByTitle(title);
	}
	
	/**
	 * Tests if the illegal argument exception is thrown when searching for books with a null title.
	 * Test passes if the illegal argument exception si thrown.
	 */
	@Test (expected = IllegalArgumentException.class)
	public void booksByTitleNull() {
		testDao.findBooksByTitle(null);
	}
	
	/**
	 * Tests if the class can search for books given the author and the title
	 * Test passes if the author nor the title isn't null
	 */
	@Test
	public void booksByTitleAuthor() {
		String author = "author";
		String title = "title";
		testDao.findBooksByAuthorTitle(author, title);
	}
	
	/**
	 * Tests if the illegal argument exception is thrown when provided a null title when searching by author and title.
	 * Test passes if the illegal argument exception is thrown.
	 */
	@Test (expected = IllegalArgumentException.class)
	public void booksByTitleAuthorNull() {
		String author = "author";
		testDao.findBooksByAuthorTitle(author, null);
	}
	
	/**
	 * Tests if the illegal argument exception is thrown when provided a null author when searching by author and title.
	 * Test passes if the illegal argument exception is thrown.
	 */
	@Test (expected = IllegalArgumentException.class)
	public void booksByTitleAuthorNull2() {
		String title = "title";
		testDao.findBooksByAuthorTitle(null, title);
	}
}
