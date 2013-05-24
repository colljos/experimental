import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SingleLinkedListProblem
{

  public static interface ISingleLinkedList
  {
    ISingleLinkedList getNext();

    void setNext(ISingleLinkedList next_);

    String getVal();
  }
  
  public static class SingleLinkedListImpl implements ISingleLinkedList {
	String name;
	
	ISingleLinkedList next;
	  
	public SingleLinkedListImpl(String name_) {
		name = name_;
	}

	public SingleLinkedListImpl(String name_, ISingleLinkedList next_) {
		name = name_;
		next = next_;
	}

	@Override
	public ISingleLinkedList getNext() {
		return next;
	}

	@Override
	public void setNext(ISingleLinkedList next_) {
		next = next_;
	}

	@Override
	public String getVal() {
		return name;
	}
	  
  }
  
  /**
   * Write this
   * 
   * @param ssl_
 * @return 
   */
  public ISingleLinkedList reverse(ISingleLinkedList ssl_)
  {
	  if (ssl_ == null || ssl_.getNext() == null) 
		  return ssl_;
	  
	  ISingleLinkedList nextItem = ssl_.getNext();
	  ssl_.setNext(null);
	  
	  ISingleLinkedList reverseRest = reverse(nextItem);
	  nextItem.setNext(ssl_);
	  
	  return reverseRest;
  }
  
  /**
   * Write this
   * 
   * @param strings_
   * @return
   */
  public static ISingleLinkedList buildList(String... strings_)
  {
	  ISingleLinkedList head = new SingleLinkedListImpl(strings_[0]);
	  ISingleLinkedList tail = head;
	  for (int i = 1; i < strings_.length; i++) {
		  tail.setNext(new SingleLinkedListImpl(strings_[i]));
		  tail = tail.getNext();
	  }
	  return head;
  }

  /**
   * Write a recursive constant memory method that reverses a single linked list
   */
  @Test
  public void SimpleTest()
  {
    ISingleLinkedList ABC = buildList("A", "B", "C");
    assertEquals("A", ABC.getVal());
    assertEquals("B", ABC.getNext().getVal());
    assertEquals("C", ABC.getNext().getNext().getVal());
    assertEquals(null, ABC.getNext().getNext().getNext());

    ISingleLinkedList A = buildList("A");
    assertEquals("A", A.getVal());
    assertEquals(null, A.getNext());

    ISingleLinkedList ABCDEF = buildList("A", "B", "C", "D", "E", "F");
    assertEquals("A", ABCDEF.getVal());
    assertEquals("C", ABCDEF.getNext().getNext().getVal());
    assertEquals("E", ABCDEF.getNext().getNext().getNext().getNext().getVal());
    assertEquals(null, ABCDEF.getNext().getNext().getNext().getNext().getNext().getNext());

    ABC = reverse(ABC);
    assertEquals("C", ABC.getVal());
    assertEquals("B", ABC.getNext().getVal());
    assertEquals("A", ABC.getNext().getNext().getVal());
    assertEquals(null, ABC.getNext().getNext().getNext());

    ABCDEF = reverse(ABCDEF);
    assertEquals("F", ABCDEF.getVal());
    assertEquals("D", ABCDEF.getNext().getNext().getVal());
    assertEquals("B", ABCDEF.getNext().getNext().getNext().getNext().getVal());
    assertEquals(null, ABCDEF.getNext().getNext().getNext().getNext().getNext().getNext());

    ISingleLinkedList CAB = buildList("C", "A", "B");
    CAB = sort(CAB);
    assertEquals("A", CAB.getVal());
    assertEquals("B", CAB.getNext().getVal());
    assertEquals("C", CAB.getNext().getNext().getVal());
    assertEquals(null, CAB.getNext().getNext().getNext());

    ISingleLinkedList FBDCEA = buildList("F", "B", "D", "C", "E", "A");
    FBDCEA = sort(FBDCEA);
    assertEquals("A", FBDCEA.getVal());
    assertEquals("C", FBDCEA.getNext().getNext().getVal());
    assertEquals("E", FBDCEA.getNext().getNext().getNext().getNext().getVal());
    assertEquals(null, FBDCEA.getNext().getNext().getNext().getNext().getNext().getNext());

  }
  
  /*
   * 	1. Write an algorithm that recursively sorts a singly linked list in linear time and constant space.
   * 
   *  	QuickSort
   */
	public ISingleLinkedList sort(ISingleLinkedList ssl_) {
		if (ssl_ == null)
			return ssl_;
		String pivot = ssl_.getVal();
		ISingleLinkedList less = sort(less(pivot, ssl_.getNext()));
		ISingleLinkedList more = sort(more(pivot, ssl_.getNext()));
		return join(join(less, new SingleLinkedListImpl(pivot)), more);
	}

	private ISingleLinkedList less(String s, ISingleLinkedList n) {
		if (n == null)
			return null;
//		System.out.println("less: comparing <" + s + "> with <" + n.getVal() + ">");
		if (s.compareToIgnoreCase(n.getVal()) > 0)
			return new SingleLinkedListImpl(n.getVal(), less(s, n.getNext()));
		return less(s, n.getNext());
	}

	private ISingleLinkedList more(String s, ISingleLinkedList n) {
		if (n == null)
			return null;
//		System.out.println("more: comparing <" + s + "> with <" + n.getVal() + ">");
		if (s.compareToIgnoreCase(n.getVal()) < 0)
			return new SingleLinkedListImpl(n.getVal(), more(s, n.getNext()));
		return more(s, n.getNext());
	}

	private ISingleLinkedList join(ISingleLinkedList n, ISingleLinkedList l) {
		if (l == null)
			return n;
		if (n == null)
			return l;
		ISingleLinkedList start = n;
		while (n.getNext() != null) {
			n = n.getNext();
		}
		n.setNext(l);
		return start;
	}
}

// EOF
