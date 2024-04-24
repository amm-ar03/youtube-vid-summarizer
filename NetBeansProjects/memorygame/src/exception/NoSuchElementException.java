
 
package exception;

/**
 *creates an exception that works when a certain element is not in the container 
 * @author ammar
 */
public class NoSuchElementException extends Exception {
    
    /**
     *exception that displays a specified message when certain element is not 
     * in the container
     * @param msg 
     */
    public NoSuchElementException(String msg){  
        super(msg);
    }
    
        
   
}
