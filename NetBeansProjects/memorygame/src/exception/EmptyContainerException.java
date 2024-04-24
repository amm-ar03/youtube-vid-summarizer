
package exception;

/**
 * create an exception that works when container is empty
 * @author ammar
 */
public class EmptyContainerException extends Exception {
    
    /**
     * an exception that displays a specified message when container is empty
     */
    public EmptyContainerException(){
        super("No Element");
    }
    
}
