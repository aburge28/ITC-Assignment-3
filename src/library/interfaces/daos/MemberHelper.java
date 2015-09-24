package library.interfaces.daos;

import library.interfaces.entities.IMember;

public class MemberHelper implements IMemberHelper {
	public static int id = MemberDAO.listMembers().size() + 1;

	public static IMember makeMember(String firstName, String lastName, String contactPhone, String emailAddress, int id){
		IMember member = makeMember(firstName, lastName, contactPhone, emailAddress, id);
		MemberDAO.listMembers().add(member);
		return member;
	}
}
