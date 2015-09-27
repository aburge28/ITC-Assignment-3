package library.interfaces.daos;

import library.Member;
import library.interfaces.entities.IMember;

public class MemberHelper implements IMemberHelper {
	
	public IMember makeMember(String firstName, String lastName, String contactPhone, String emailAddress, int id) {
		return new Member(firstName, lastName, contactPhone, emailAddress, id);		
	}
}
