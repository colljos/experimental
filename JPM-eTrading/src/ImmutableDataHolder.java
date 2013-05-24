
/*
 * 	Conditions:
 * 	1. Object is Safely Published (eg. this reference does not escape during construction)
 * 	   Do not:
 * 		- register a callback within a Constructor
 * 		- start a Thread within a Constructor
 * 	2. Declare all fields final
 * 	3. Object reference fields must not allow modifications anywhere in the object graph reachable from the fields after construction
 * 	4. Declare Class as final (to prevent a subclass subverting these rules)
 */

final public class ImmutableDataHolder<E> {

	private final E data;

	public ImmutableDataHolder(E data) {
		this.data = data;
	}
	
	E getData() {
		return data;
	}
}
