package busy.util;

/**
 * A class implements the <code>Replicable</code> interface to indicate to the
 * {@link busy.util#replicate()} method that it is legal for that method to make a field-for-field
 * copy of instances of that class.
 * 
 * @author malkomich
 *
 * @param <T>
 *            object to replicate with the same properties
 */
public interface Replicable<T> {

    T replicate();
}
