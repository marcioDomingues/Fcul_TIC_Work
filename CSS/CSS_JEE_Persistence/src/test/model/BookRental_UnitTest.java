package test.model;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import model.rentals.BookRental_RefactorForTests;
import model.rentals.Page;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;


@RunWith(MockitoJUnitRunner.class)
public class BookRental_UnitTest {
	
	@Mock private Page page;
	@Mock private Map<Integer, Page> pagesMock;
	
	private int lastAdded; 
	
	private Map<Integer, Page> pages;
	
	private BookRental_RefactorForTests bookRental;	
	private BookRental_RefactorForTests bookRentalMock;
	
	@Before
    public void prepare() {
		
		//Page page= mock(Page.class);
		lastAdded = 10;
		pages = new TreeMap<Integer, Page> ();
		//given( new TreeMap<Integer, Page> () ).willReturn((TreeMap<Integer, Page>) pages);
		bookRental = new BookRental_RefactorForTests( pages , lastAdded );

    }
	
	///////////////////////////////////////////////////
	//ANNOTATION METHODS TESTS

	@Test
	public void addAnnotationSucess(){
		//given
		int pageToAnnotate = 2;
		String annotationTest = "teste anotacao";
		String annotationResult = null;
		
		//when
		bookRental.addAnnotation( pageToAnnotate, annotationTest );
		
		
		LinkedList<String> listResult = (LinkedList<String>)bookRental.getAnnotations( pageToAnnotate );
		annotationResult = listResult.getFirst();
		
		//then
		assertEquals( annotationResult, annotationTest );
	}
	
	@Test
	public void addAnnotationSameAnnotationSucess(){
		//given
		int pageToAnnotate = 2;
		String annotationTest = "teste anotacao";
		
		int annotationResult;
		
		//when
		bookRental.addAnnotation( pageToAnnotate, annotationTest );
		bookRental.addAnnotation( pageToAnnotate, annotationTest );
		
		LinkedList<String> listResult = (LinkedList<String>)bookRental.getAnnotations( pageToAnnotate );
		annotationResult = listResult.size();
		
		//then
		assertEquals( 2, annotationResult );
	}
	
	@Test
	public void removeAnnotationSucess() {
		//given
		int pageToAnnotate = 2;
		String annotationTest = "teste anotacao";
		
		//annotate
		bookRental.addAnnotation( pageToAnnotate, annotationTest );
		
		//get annotation number
		LinkedList<String> sTry = (LinkedList<String>)bookRental.getAnnotations( pageToAnnotate );

		//when
		bookRental.removeAnnotation( pageToAnnotate, sTry.indexOf( annotationTest ) );
		
		// then
		assertEquals ( false , bookRental.hasAnnotations( pageToAnnotate ) );
	}
	
	@Test
	public void removeAnnotationOneFromMiddleSucess() {
		
		//given
		int pageToAnnotate = 2;
		String annotationTest1 = "teste 0";
		String annotationTest2 = "teste 1";
		String annotationTest3 = "teste 2";
		String annotationTest4 = "teste 3";
		
		//annotation index to remove
		//remove "text 1"
		
		//annotate
		bookRental.addAnnotation( pageToAnnotate, annotationTest1 );
		bookRental.addAnnotation( pageToAnnotate, annotationTest2 );
		bookRental.addAnnotation( pageToAnnotate, annotationTest3 );
		bookRental.addAnnotation( pageToAnnotate, annotationTest4 );
		
		//get annotation number
		//from the one we want to remove
		LinkedList<String> sTry = (LinkedList<String>)bookRental.getAnnotations( pageToAnnotate );
		
		//when
		bookRental.removeAnnotation( pageToAnnotate, sTry.indexOf( annotationTest2 ) );
		
		LinkedList<String> sFinal = (LinkedList<String>)bookRental.getAnnotations( pageToAnnotate );
		
		// then
		assertEquals ( false , sFinal.contains( annotationTest2 ) );		
	}
	
	@Test
	public void getAnnotationsSucess(){
		//given
		int pageToAnnotate = 1;
		String annotationTest = "teste anotacao";
		bookRental.addAnnotation( pageToAnnotate, annotationTest );

		//when
		LinkedList<String> listResult = (LinkedList<String>)bookRental.getAnnotations( pageToAnnotate );
		
		//then
		assertEquals( true, listResult.contains(annotationTest) );
	}
	
	@Test
	public void getAnnotationsNoAnnotationsSucess(){
		//given
		int pageToAnnotate = 1;

		//when
		LinkedList<String> listResult = (LinkedList<String>)bookRental.getAnnotations( pageToAnnotate );
		
		//then
		assertEquals( 0, listResult.size() );
	}
	
	@Test
	public void getAnnotationTextSucess(){
		//given
		int pageToAnnotate = 2;
		String sTry = "teste anotacao";
		String sResult=null;
		//annotate
		bookRental.addAnnotation( pageToAnnotate, sTry );
		//get all anotations
		LinkedList<String> annotationsList = (LinkedList<String>)bookRental.getAnnotations( pageToAnnotate );
		int indexOf = annotationsList.indexOf(sTry);
		
		//when
		sResult = bookRental.getAnnotationText( pageToAnnotate, indexOf);
		
		//then
		assertEquals ( sTry , sResult );
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void getAnnotationTextOutOfArray(){
		//given
		int pageToAnnotate = 2;
		String sTry = "teste anotacao";
		//index with no annotation
		int indexOf = 2;
		String sResult;
		
		//annotate
		bookRental.addAnnotation( pageToAnnotate, sTry );

		//when
		sResult = bookRental.getAnnotationText( pageToAnnotate, indexOf);

	}
	
	@Test
	public void getAnnotationTextAnnotationNotFound(){
		//given
		int pageToAnnotate = 2;
		String sTry1 = "teste anotacao 1";
		String sTry2 = "teste anotacao 2";
		String notThere = "not There";
		
		//index with no annotation
		int indexOf;
		String sResult;
		
		//annotate
		bookRental.addAnnotation( pageToAnnotate, sTry1 );
		bookRental.addAnnotation( pageToAnnotate, notThere );
		
		//get all anotations
		LinkedList<String> annotationsList = (LinkedList<String>)bookRental.getAnnotations( pageToAnnotate );
		indexOf = annotationsList.indexOf(notThere);
		
		bookRental.removeAnnotation( pageToAnnotate , indexOf);
		bookRental.addAnnotation( pageToAnnotate, sTry2 );
		
		
		//when
		sResult = bookRental.getAnnotationText( pageToAnnotate, indexOf);
		
		//then
		assertNotEquals ( notThere , sResult );
	}
	
	@Test
	public void hasAnnotationsSucess(){
		//given
		int pageToAnnotate = 2;
		String sTry1 = "teste anotacao 1";
		String sTry2 = "teste anotacao 2";

		//annotate
		bookRental.addAnnotation( pageToAnnotate, sTry1 );
		bookRental.addAnnotation( pageToAnnotate, sTry2 );
		
		//then
		assertEquals ( true , bookRental.hasAnnotations(pageToAnnotate) );
	}
	
	@Test
	public void hasAnnotationsNoAnnotations(){
		//given
		int pageToAnnotate = 2;
		
		//then
		assertEquals ( false , bookRental.hasAnnotations(pageToAnnotate) );
	}

	///////////////////////////////////////////////////
	//END ANNOTATION METHODS TESTS
	
	
	///////////////////////////////////////////////////
	//Bookmarks METHODS TESTS
	
	@Test
	public void isBookmarkedSucess(){
		//given
		int pageToBookMark = 2;
		
		//when
		bookRental.toggleBookmark(pageToBookMark);
		//then
		assertEquals(true, bookRental.isBookmarked(pageToBookMark));
	}
	
	@Test
	public void isBookmarkedNotBookmarked(){
		//given
		int pageToBookMark = 2;
		int pageNotBookMark = 10;

		//when
		bookRental.toggleBookmark(pageToBookMark);

		//then
		assertEquals(false, bookRental.isBookmarked(pageNotBookMark));
	}
	
	@Test
	public void getBookmarksSucess(){
		//given
		int pageToBookMark1 = 2;
		int pageToBookMark2 = 10;
		
		//when
		bookRental.toggleBookmark(pageToBookMark1);
		bookRental.toggleBookmark(pageToBookMark2);
		
		//get all anotations
		List<Integer> bookMarksList = bookRental.getBookmarks();
	
		//then
		assertEquals( 2, bookMarksList.size() );
	}
	
	@Test
	public void getBookmarksNoBookmarks(){
	
		//get all anotations
		List<Integer> bookMarksList = bookRental.getBookmarks();
	
		//then
		assertEquals( 0, bookMarksList.size() );
	}
	
	@Test
	public void toggleBookmarkSucess(){
		//given
		int pageToBookMark1 = 2;
		int pageToBookMark2 = 10;

		List<Integer> noBookMarksList = bookRental.getBookmarks();
		
		//when
		bookRental.toggleBookmark(pageToBookMark1);
		bookRental.toggleBookmark(pageToBookMark2);
		
		List<Integer> bookMarksList = bookRental.getBookmarks();
		
		//then
		assertNotEquals( noBookMarksList, bookMarksList);	
	}
	///////////////////////////////////////////////////
	//END Bookmarks METHODS TESTS
	
	
	///////////////////////////////////////////////////
	//PAGE METHODS TESTS

	@Test
	public void getLastPageVisitedSucess(){
		//given
		int setLastPage = 3;
		bookRental.setLastPageVisited( setLastPage );

		//then
		assertEquals( setLastPage , bookRental.getLastPageVisited());
	}
	
	@Test
	public void getLastPageVisitedWhenFirstCalled(){
		//given
		int sentToConstructor = lastAdded;
		
		//then
		assertEquals( sentToConstructor, bookRental.getLastPageVisited());
	}
	
	@Test
	public void setLastPageVisitedSucess(){
		//given
		int setLastPage = 3;
		
		//when
		bookRental.setLastPageVisited( setLastPage );

		//then
		assertEquals( setLastPage , bookRental.getLastPageVisited());
	}
	
	
	///////////////////////////////////////////////////
	//END PAGE METHODS TESTS
	
	@Test
	public void getCreatePageSucess(){
		int pageNum=2; 
		String text="Test Pages";
		
		//given
		Page p = mock(Page.class);
		given( pagesMock.get( 1 ) ).willReturn( p );
		//when
		
		bookRentalMock.addAnnotation( pageNum, text );
		
		//then
		verify(p).addAnnotation( text ); 
	}
	
	
}



/*package test.model;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import model.EMedium;
import model.EMediumAttribute;
import model.EMediumPropertiesData;
import model.EMediumType;
import model.lendables.Lendable;
import model.lendables.Library;
import model.rentals.BookRental;
import model.rentals.BookRental_forTests;
import model.rentals.Page;
import model.rentals.Rental;
import model.rentals.RentalFactory;
import model.transactions.DBHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Lob;
import javax.persistence.PersistenceException;
import javax.persistence.PostLoad;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.swing.event.EventListenerList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import adts.Pair;

@RunWith(MockitoJUnitRunner.class)
public class BookRental_UnitTest {

	@Mock private EntityManagerFactory emf;
	@Mock private EntityManager em;
	
	@Mock private TypedQuery<Rental> query;
	
	private int lastAdded;
	
	@Mock private RentalFactory rf;
	@Mock private TreeMap<Integer, Page> pages;
	@Mock private Page p;
	
	//@Mock private EMediumType typeM;
	@Mock private EMediumPropertiesData propertiesM;
	
	@Mock private EMedium m; 
	
	@Mock private Rental rental;
	@Mock private Lendable book;
	@Mock private Library lib;
	
	
	
	private BookRental_forTests bookRental;

	//@Before
	public void setUp() {
		
		//create one of the strings in the ENUM EMediumType 
		//nao pode ser um Document por causa da persistencia da class Pages
		//Ou enviar null no construtor do Lendable
		String eMediumTypeString = "Image";
		//instantiate EMediumType  
		EMediumType type = EMediumType.valueOf(eMediumTypeString.toUpperCase());

		//instantiate EMediumType EMediumPropertiesData
		EMediumPropertiesData properties = new EMediumPropertiesData();
		//fill some attributes PATH and LICENSES is mandatory
		properties.addAttribute( EMediumAttribute.PATH, "SOME FILE PATH");
		properties.addAttribute( EMediumAttribute.TITLE, "css");
		properties.addAttribute( EMediumAttribute.MIME_TYPE, null);
		properties.addAttribute( EMediumAttribute.AUTHOR, "Marcio Domingues");
		properties.addAttribute( EMediumAttribute.LICENSES, 10);

		//criar um Lendable
		//book = new Lendable( type,  properties);
		given( new Lendable( type,  propertiesM) ).willReturn(book);
		//criar um Rental
		//rental = new Rental ( book );
		given( new Rental ( book ) ).willReturn(rental);
		//System.out.println("LENDABLE FOR TESTS "+rental.getAuthor());


		
		
		//
		//fazer refactor do construtor disto
		//para receber os componentes da pagina  
		//new BookRental ( integer , page )
		// pages = new TreeMap<Integer, Page> (); 
		//given( lib.getLastAddedLendable() ).willReturn(book);
		
		lastAdded = 1;
		//pages = new TreeMap<Integer, Page> ();
		given( new TreeMap<Integer, Page> () ).willReturn(pages);
		//bookRental = new BookRental_forTests( pages , lastAdded );
		
		//lastAdded = 1;
		//bookRental = new BookRental( book ); 
		//bookRental = new BookRental( 1 , p ); 
		given(emf.createEntityManager()).willReturn(em);
		
		
		bookRental = new BookRental_forTests( book );
		
		
	}

	//@After
	public void tearDown() throws Exception {
	}

	//@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	//////////////////////////////////////////////////////////
	//test anotations interaction methods
	//
	@Test
	public void getAnnotationsSucess() {
		String sResult = "aaa";
		given( pages.get( 1 ) ).willReturn( p );
		//given(em.createNamedQuery(Rental.FIND_BY_ID, Rental.class)).willReturn(query);
    	//given(query.getSingleResult()).willReturn(rental);
    	
		
    	Iterable<String> result = bookRental.getAnnotations( 3 );
    	
    	// then
    	//verify(query).setParameter(Rental.FIND_ID, 1);
		
	}
	
	
	
	
	
	
	
	
	//@Test
	public void addAnnotationSucess() {
		//fail("Not yet implemented");
		
		// given
		String sResult = "aaa";
		given( pages.get( 1 ) ).willReturn( p );
		//when
		bookRental.addAnnotation( 1, sResult );
		
		//
		//times testa n vezes que foi chamada
		// verifica sobre p o numero de vezes que addAnotations foi chamada
		//verifica se o metodo void model.rentals.Page.addAnnotation(String text)
		//esta a ser chamado pelo Bookrental
		verify( p, times(1) ).addAnnotation( sResult ); 
		

		
		
	}
	 
	
	
//ORIGINAL	
//	public void removeAnnotation(int pageNum, int annotNum) {
//		Page page = pages.get(pageNum);
//		if (page != null) {
//			page.removeAnnotation (annotNum);
//			DBHelper.INSTANCE.update(this);
//		}
//	}
	//@Test
	public void removeAnnotationSucess( ) {
		String sMock = "aaa";
		String sResult = "aaa";
		//given
		given( pages.get( 1 ) ).willReturn( p );
		//p.removeAnnotation( 1 );
		//given( p.getAnnotationText( 1 ) ).willReturn( sMock );
		
		// when 
    	bookRental.removeAnnotation( 1, 1 );
    	//sResult = bookRental.getPages().get(1).getAnnotationText( 1 );
		
    	// then
    	verify( p, times(1) ).removeAnnotation( 1 );
    	//assertEquals( sResult , sMock );	
	}
	
	
	//@Test(expected=Exception.class)
	public void removeAnnotationNotFound( ) throws Exception {
		String sMock = "";
		String sResult = "";
		//given
		
	}
	
	
	
//ORIGINAL
//	public Iterable<String> getAnnotations(int pageNum) {
//		Page p = pages.get(pageNum);
//		if (p != null) 
//			return p.getAnnotations();
//		return new LinkedList<String>();
//	}
	//@Test
	public void getAnnotationsSucess1() {
		List listMock = new LinkedList<String>();
		List listResult = new LinkedList<String>();
		
		given( pages.get( 1 ) ).willReturn( p );
		given( p.getAnnotations() ).willReturn( listMock );
		
		listResult = (List) bookRental.getAnnotations( 1 );
		
		
		assertEquals( "" , listMock , listMock );
	}
	
	
	
	
//ORIGINAL	
//	public String getAnnotationText(int pageNum, int annotNum) {
//		Page p = pages.get(pageNum);
//		return p != null ? p.getAnnotationText(annotNum) : "";
//	}
	//@Test
	public void getAnnotationTextSucess() {
//		// given
//		s="";
//    	given( pages.get( 2 ) ).willReturn( p );
//    	given( p.getAnnotationText( 1 ) ).willReturn( s );
//    	
//    	// when
//    	String result = bookRental.getAnnotationText( 2, 1 );
//    	
//    	// then
//    	assertEquals( "null" , result, s);	
	}
	
//	public boolean hasAnnotations(int pageNum) {
//		Page p = pages.get(pageNum);
//		return p != null && p.hasAnnotations ();
//	}
	
	//////////////////////////////////////////////////////////
	//end test anotations interaction methods
	
	
	
	//////////////////////////////////////////////////////////
	//test Bookmarks interaction methods
	
	//////////////////////////////////////////////////////////
	//end test Bookmarks interaction methods	

	
	//////////////////////////////////////////////////////////
	//test Page interaction methods

	//////////////////////////////////////////////////////////
	//end test Page interaction methods 	
	
}

*/