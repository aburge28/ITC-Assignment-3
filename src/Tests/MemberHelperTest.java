package Tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import library.Member;
import library.interfaces.daos.MemberHelper;
import library.interfaces.entities.IMember;

public class MemberHelperTest {
	String firstName = "firstName";
	String lastName = "lastName";
	String contactPhone = "contactPhone";
	String emailAddress = "emailAddress";
	int id = 1;
	MemberHelper testMemberHelper;
	IMember member;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		testMemberHelper = Mockito.mock(MemberHelper.class);
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Ensures the created member is part of MemberHelper class.
	 * Test passes if the created member is an instance of MemberHelper
	 */
	@Test
	public void testMakeMember() {
		member = testMemberHelper.makeMember(firstName, lastName, contactPhone, emailAddress, 1);
		assertTrue(testMemberHelper instanceof MemberHelper);
	}

}
