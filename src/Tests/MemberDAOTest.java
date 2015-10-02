package Tests;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import library.interfaces.daos.MemberDAO;
import library.Member;
import library.interfaces.daos.IMemberDAO;
import library.interfaces.daos.IMemberHelper;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

public class MemberDAOTest {
	IMemberHelper _helper;
	IMemberDAO _dao;

	@Before
	public void setUp() throws Exception {
		_helper = mock(IMemberHelper.class);
		_dao = new MemberDAO(_helper);
	}

	@After
	public void tearDown() throws Exception {
		_helper = null;
		_dao = null;
	}

	/**
	 * Tests to see if the constructor creates a dao object which is an instance of the IMemberDAO class
	 * Tests should pass if an object which is an instance of an IMemberDAo class is created
	 */
	@Test
	public void testMemberDAOConstructor() {
		MemberDAO dao = new MemberDAO(_helper);
		assertTrue(dao instanceof IMemberDAO);
	}
	
	/**
	 * Tests if the Illegal argument exception is thrown if the inputted MemberDAO is null
	 * Test will pass if the Illegal argument exception is thrown
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorHelperNull() {
		MemberDAO dao = new MemberDAO(null);
	}
	
	/**
	 * Tests the creation of a loan and if it uses the helper input to successfully create a loan.
	 * Test should pass if the created loan equals to expected loan
	 */
	@Test
	public void testCreateLoan() {
		String firstName = "firstName";
		String lastName = "lastName";
		String contactPhone = "contactPhone";
		String emailAddress = "emailAddress";
		int ID = 1;
		IMember expectedMember = mock(IMember.class);
		
		//execute, where a created expectedMember is returned
		when(_helper.makeMember(eq(firstName), eq(lastName), eq(contactPhone), eq(emailAddress), eq(ID))).thenReturn(expectedMember);
		
		IMember actualMember = _dao.addMember(firstName, lastName, contactPhone, emailAddress);
		
		//verifies the makeMember method
		verify (_helper).makeMember(eq(firstName), eq(lastName), eq(contactPhone), eq(emailAddress), eq(ID));
		
		//asserts both the created loan and the expected loan are equal
		assertEquals(expectedMember, actualMember);		
	}
	
	/**
	 * Tests for MemberDAO constructor when Member object is null
	 * Test passes if entering null for member throws an Illegal argument exception
	 */
	@Test (expected = IllegalArgumentException.class)
	public void testMemberNull() {
		MemberDAO member = new MemberDAO(_helper, null);
	}
	
	/**
	 * Tests if the program can find members by their last name
	 * Test should pass if the last name is not null
	 */
	@Test ()
	public void searchLastName() {
		String lastName = "lastName";
		_dao.findMembersByLastName(lastName);
	}
	
	/**
	 * Tests if the Illegal Argument Exception is thrown when searching the last name if the last name entered is null
	 * Test should pass if the Illegal argument exception is thrown.
	 */
	@Test (expected = IllegalArgumentException.class)
	public void nullLastName() {
		_dao.findMembersByLastName(null);
	}
	
	/**
	 * Tests if the class can find members by their email
	 */
	@Test
	public void searchEmailAddress() {
		String testEmail = "email";
		_dao.findMembersByEmailAddress(testEmail);
	}
	
	/**
	 * Tests if the Illegal argument exception is thrown when searching by the email address if the email address is thrown
	 * Test passes if the the Illegal argument exception is thrown
	 */
	@Test (expected = IllegalArgumentException.class)
	public void nullEmailAddressSearch() {
		_dao.findMembersByEmailAddress(null);
	}
	
	/**
	 * Tests if the class can search by first name and last name
	 */
	@Test
	public void searchingByNames() {
		String firstName = "firstName";
		String secondName = "secondName";
		_dao.findMembersByNames(firstName, secondName);
	}
	
	/**
	 * Tests if the Illegal argument exception is thrown when searching by the first and last names if the first name is null
	 * Test passes if the Illegal argument exception is thrown
	 */
	@Test (expected = IllegalArgumentException.class)
	public void searchingByNamesFirstNameNull() {
		String secondName = "secondName";
		_dao.findMembersByNames(null, secondName);
	}
	
	/**
	 * Tests if the Illegal argument exception is thrown when searcjing by the first and last names if the last name is null
	 * Test passes if the Illegal argument is thrown.
	 */
	@Test (expected = IllegalArgumentException.class)
	public void searchingByNamesLastNameNull() {
		String firstName = "firstName";
		_dao.findMembersByNames(firstName, null);
	}
}
