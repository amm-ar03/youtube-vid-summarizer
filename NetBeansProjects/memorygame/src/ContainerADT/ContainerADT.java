package ContainerADT;
import exception.*;
/**
 *
 * @author ammar
 */
/**
 *
 * @author ammar
 * @param <T>
 */
public interface ContainerADT<T> {

    /**
     * adds element to the container
     *
     * @param element
     */
    public void add(T element);

    /**
     * clears contents of the container
     */
    public void clear();

    /**
     *
     * @param target
     * @return true if containers contains parameter
     */
    public boolean contains(T target);

    /**
     *
     * @return true if container contains no elements
     */
    public boolean isEmpty();

    /**
     * 
     * @return max size of the container
     */
    public int maxSize();
    
    /**
     * 
     * @param element
     * @return
     * @throws NoSuchElementException
     * @throws EmptyContainerException 
     */
    public T remove(T element) throws NoSuchElementException,
        EmptyContainerException;
    
    /**
     * 
     * @return the 
     * @throws EmptyContainerException 
     */
    public T removeRandom() throws EmptyContainerException;
    
    /**
     * 
     * @return the size of the container 
     */
    public int size();
    
    /**
     * 
     * @return string representation of the container
     */
    @Override
    public String toString();
              
}
