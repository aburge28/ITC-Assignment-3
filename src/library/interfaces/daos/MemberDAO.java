package library.interfaces.daos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import library.interfaces.entities.IBook;
import library.interfaces.entities.IMember;
import library.Member;

public class MemberDAO implements IMemberDAO {
	private int nextID;
	private IMemberHelper helper;
	private static Map<Integer, IMember> memberMap;

	public MemberDAO (IMemberHelper helper) {
		if (helper == null) {
			throw new IllegalArgumentException ("Helper cannot be null");
		}
		nextID = 1;
		this.helper = helper;
		memberMap = new HashMap<Integer, IMember>();
	}

	public MemberDAO(IMemberHelper helper, Map<Integer, IMember> memberMap) {
		this(helper);
		if (helper == null ) {
			throw new IllegalArgumentException(String.format("MemberDAO : constructor : bookMap cannot be null."));
		}
		this.memberMap = memberMap;
	}

	public IMember addMember (String firstName, String lastName, String contactPhone, String emailAddress) {
		int id = getNextId();
		IMember member = IMemberHelper.makeMember(firstName, lastName, contactPhone, emailAddress, id);
		memberMap.put(Integer.valueOf(id), member);
		return member;
	}

	@Override
	public IMember getMemberByID(int id) {
		if (memberMap.containsKey(Integer.valueOf(id))) {
			return memberMap.get(Integer.valueOf(id));
		}
		return null;
	}

	public static List<IMember> listMembers() {
		List<IMember> list = new ArrayList<IMember>(memberMap.values());
		return Collections.unmodifiableList(list);
	}

	public List<IMember> findMembersByLastName(String lastName) {
		if ( lastName == null || lastName.isEmpty()) {
			throw new IllegalArgumentException(
					String.format("MemberDAO : findMembersByLastName : lastName cannot be null or blank"));
		}
		List<IMember> list = new ArrayList<IMember>();
		for (IMember m : memberMap.values()) {
			if (lastName.equals(m.getLastName())) {
				list.add(m);
			}
		}
		return Collections.unmodifiableList(list);
	}

	public List<IMember> findMembersByEmailAddress(String emailAddress) {
		if ( emailAddress == null || emailAddress.isEmpty()) {
			throw new IllegalArgumentException(
					String.format("MemberDAO : findMembersByEmailAddress : emailAddress cannot be null or blank"));
		}
		List<IMember> list = new ArrayList<IMember>();
		for (IMember m : memberMap.values()) {
			if (emailAddress.equals(m.getEmailAddress())) {
				list.add(m);
			}
		}
		return Collections.unmodifiableList(list);
	}

	@Override
	public List<IMember> findMembersByNames(String firstName, String lastName) {
		if ( firstName == null || firstName.isEmpty() ||  lastName == null || lastName.isEmpty()) {
			throw new IllegalArgumentException(
					String.format("MemberDAO : findMembersByNames : firstName and lastName cannot be null or blank"));
		}
		List<IMember> list = new ArrayList<IMember>();
		for (IMember m : memberMap.values()) {
			if (firstName.equals(m.getFirstName()) && lastName.equals(m.getLastName())) {
				list.add(m);
			}
		}
		return Collections.unmodifiableList(list);
	}

	private int getNextId() {
		return nextID++;
	}
}
