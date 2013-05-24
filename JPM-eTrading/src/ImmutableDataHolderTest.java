import org.junit.Test;


public class ImmutableDataHolderTest {

	@Test
	public void testGetData() {
		
		Integer counter = new Integer(999);
		ImmutableDataHolder<Integer> data = new ImmutableDataHolder<Integer>(counter);
		System.out.println(data.getData());
		
		counter++;
		System.out.println(data.getData());
		
		counter = data.getData();
		counter++;
		System.out.println(data.getData());
	}

}
