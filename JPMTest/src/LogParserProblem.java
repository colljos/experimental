import static org.junit.Assert.assertEquals;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.Test;

/*
 * A LogParser interface can only return a single event at a time:   
               Event Parser.readNext()
   
   In the pull parser that you’re implementing, you’ll be getting two events at a time instead of one event at a time. 
   You can think of this as  after reading a single physical line in a log file, two events result instead of one. 
   Knowing that other parsers can have this problem, 
   how can you embed the logic for returning two events instead of one 
   w/o changing the interface you’re implementing all the while avoiding inheritance? 
   Please write up this class so that it calls two private methods: 
   “Event getFirst()” and “Event getLast()”  to get the two events while implementing the LogParser interface.
   
 */

public class LogParserProblem {

	public interface ILogParser {

		public Event readNext();
	}

	public static class Event {

		private String name;
		private Event next;

		public Event(String name_) {
			this.name = name_;
		}
		  
		public void setNext(Event event) {
			next = event;
		}

		public Event getFirst() {
			return this;
		}

		private Event getNext() {
			return next;
		}

		public Event getLast() {
			Event prev = next;
			while (next != null) {
				next = next.getNext();
			}
			return prev;
		}

		public String getVal() {
			return name;
		}
	}
	
	public static class LogParser implements ILogParser {
		
		private Queue<Event> queue = new LinkedBlockingQueue<Event>();

		// Singleton instance
		private static LogParser instance = new LogParser();
		
		private LogParser() {
		}

		public synchronized static LogParser getInstance() {
			return instance;
		}		
		
		public Event readNext() {
			return queue.poll();
		}

		public void feedEvent(String... strings_) {
			Event event = buildEvent(strings_);
			queue.add(event);
		}

		private Event buildEvent(String... strings_) {
			Event head = new Event(strings_[0]);
			Event tail = head;
			for (int i = 1; i < strings_.length; i++) {
				tail.setNext(new Event(strings_[i]));
				tail = tail.getNext();
			}
			return head;
		}

	}

	@Test
	public void SimpleTest() {

		LogParser lp = new LogParser();
		
		// setup
		lp.feedEvent("EVENT PART 1", "EVENT PART 2");
		
		Event event = lp.readNext();
	    assertEquals("EVENT PART 1", event.getFirst().getVal());
	    assertEquals("EVENT PART 2", event.getLast().getVal());		

		// setup
		lp.feedEvent("SINGLE EVENT");
		
		event = lp.readNext();
	    assertEquals("SINGLE EVENT", event.getFirst().getVal());
	    assertEquals(null, event.getLast());		

	}
}
