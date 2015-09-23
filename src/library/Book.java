package library;

import library.interfaces.entities.EBookState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;

public class Book implements IBook {
	public String bookAuthor;
	private String bookTitle;
	private String bookCallNumber;
	private int bookID;
	
	
	public Book (String author, String title, String callNumber, int Id) {
		author = bookAuthor;
		title = bookTitle;
		callNumber = bookCallNumber;
		Id = bookID;
		if (author == null || title == null || callNumber == null) {
			throw new IllegalArgumentException("Author, title, callNumber or bookID cannot be null");
		}
		if (Id < 0) {
			throw new IllegalArgumentException("ID cannot be less than zero");
		}
	}

	@Override
	public void borrow(ILoan loan) {
		//loan = new ILoan( bookID);
		if (((IBook) loan).getState() != EBookState.AVAILABLE) {
			throw new RuntimeException("This book cannot be borrowed, it's not available");
		}
	}

	@Override
	public ILoan getLoan() {
		return null;
		//return loan;
	}

	@Override
	public void returnBook(boolean damaged) {
	//	if (loan = EBookState.DAMAGED) {
			
		}
	//}

	@Override
	public void lose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void repair() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		
		
	}

	@Override
	public EBookState getState() {
		return null;
	}

	@Override
	public String getAuthor() {
		return bookAuthor;
	}

	@Override
	public String getTitle() {
		return bookTitle;
	}

	@Override
	public String getCallNumber() {
		return bookCallNumber;
	}

	@Override
	public int getID() {
		return bookID;
	}

}
