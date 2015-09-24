package library.interfaces.daos;

import java.util.List;

import library.interfaces.entities.IMember;

public interface IMemberDAO {

		public IMember addMember(String firstName, String lastName, String ContactPhone, String emailAddress);
		
		public IMember getMemberByID(int id);
		
		public static List<IMember> listMembers() {
			// TODO Auto-generated method stub
			return null;
		}
		
		public List<IMember> findMembersByLastName(String lastName);

		public List<IMember> findMembersByEmailAddress(String emailAddress);
		
		public List<IMember> findMembersByNames(String firstName, String lastName);

}
