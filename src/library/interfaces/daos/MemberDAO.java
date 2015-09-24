package library.interfaces.daos;

import java.util.List;

import library.interfaces.entities.IBook;
import library.interfaces.entities.IMember;
import library.Member;

public class MemberDAO implements IMemberDAO {

	public MemberDAO (IMemberHelper helper) {
		if (helper == null) {
			throw new IllegalArgumentException ("Helper cannot be null");
		}
	}

	public IMember addMember (String firstName, String lastName, String contactPhone, String emailAddress) {
		IMember member = MemberHelper.makeMember(firstName, lastName, contactPhone, emailAddress, MemberHelper.id);
		MemberDAO.listMembers().add(member);
		return member;
	}

	@Override
	public IMember getMemberByID(int id) {
		if (IMemberDAO.listMembers().contains(id)) {
			int Id = IMemberDAO.listMembers().indexOf(id);
			IMember name = listMembers().get(Id);
			return name;
		}
		else {
			return null;
		}
	}

	public static List<IMember> listMembers() {
		return (List<IMember>) IMemberDAO.listMembers().iterator();
	}

	@Override
	public List<IMember> findMembersByLastName(String lastName) {
		if (listMembers().contains(lastName)) {
			int index = IMemberDAO.listMembers().indexOf(lastName);
			IMember name = listMembers().get(index);
			return (List<IMember>) name;
		}
		else {
			return null;
		}
	}

	@Override
	public List<IMember> findMembersByEmailAddress(String emailAddress) {
		if (listMembers().contains(emailAddress)) {
			int index = IMemberDAO.listMembers().indexOf(emailAddress);
			IMember email = listMembers().get(index);
			return (List<IMember>) email;
		}
		else {
			return null;
		}
	}

	@Override
	public List<IMember> findMembersByNames(String firstName, String lastName) {
		if (listMembers().contains(firstName) && listMembers().contains(lastName)) {
			int index = IMemberDAO.listMembers().indexOf(lastName);
			IMember memberByName = listMembers().get(index);
			return(List<IMember>) memberByName;
		}
		else {
			return null;
		}
	}
}
