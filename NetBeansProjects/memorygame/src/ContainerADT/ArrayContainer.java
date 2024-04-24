package ContainerADT;

import java.util.Random;
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
public class ArrayContainer<T> implements ContainerADT<T> {

    /**
     * A generic myArray for storing objects
     */
    private T[] myArray;
    
    /**
     * Points to next open space in container
     */
    private int top;

    /**
     * Constructor with no parameter and myArray with 100 space
     */
    public ArrayContainer() {
        myArray = (T[]) new Object[100];
    }

    /**
     * array creating container with the size that is set/specified by user
     *
     * @param elements
     */
    public ArrayContainer(int elements) {
        myArray = (T[]) new Object[elements];
    }

    @Override
    public void add(T element) {
        if (size() == maxSize()) {
            T[] temp = (T[]) new Object[myArray.length * 2];
            for (int i = 0; i < myArray.length; i++) {
                temp[i] = myArray[i];
            }
            myArray = temp;
            myArray[top] = element;
            top++;
        } else {
            myArray[top] = element;
            top++;
        }
    }
    
    /**
     * Uses an myArray to set all values in container to null
     */
    @Override
    public void clear() {
        for (int i = 0; i < myArray.length; i++) {
            myArray[i] = null;
        }
    }
    
    /**
     * 
     * @param point
     * @return true if the container has element specified by parameter or else
     * false
     */
    @Override
    public boolean contains(T point) {
        for (int i = 0; i < myArray.length; i++) {
            if (myArray[i] == point) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Checks if container is empty
     * @return the top element as 0
     */
    @Override
    public boolean isEmpty(){
        return top == 0;
    }
    
    /**
     * returns size of the container
     * @return size of the container
     */
    @Override
    public int maxSize(){
        return myArray.length;
    }

    
    /**
     * This removes the element that is chosen according to the user
     * @param element
     * @return
     * @throws EmptyContainerException
     * @throws NoSuchElementException 
     */
    @Override
    public T remove(T element) throws EmptyContainerException,
            NoSuchElementException{
        
        if (isEmpty()) {
            throw new EmptyContainerException();
        }
        for (int i = 0; i < top; i++) {
            if (myArray[i] == element) {
                T returnValue = myArray[i];
                myArray[i] = myArray[top - 1];
                myArray[top - 1] = null;
                top--;
                return returnValue;
            }

        }
        return null;
    }
    
    
    /**
     * Removes random value from the container. this random value is chosen
     * by generating a random value. Everything done randomly
     * @return the random value that's removed
     * @throws EmptyContainerException that displays a relevant message
     * when container is empty
     */
    @Override
    public T removeRandom() throws EmptyContainerException {
        
        Random random  = new Random();
        int randomValue = random.nextInt(top);
        T returnValue = myArray[randomValue];
        if (isEmpty()) {
            throw new EmptyContainerException();
            
        } else {
            myArray[randomValue] = myArray[top - 1];
            myArray[top - 1] = null;
            top--;
        }
        return returnValue;
    }

    /**
     * Returns size of the container
     * @return size of the container
     */
    @Override
    public int size(){
        return top;
    }
    
    /**
     * returns string representation of the array
     * @return string representation of the array
     */
    @Override
    public String toString(){
        String returnString  = "";
        for(int i = 0; i < top; i++){
            returnString  = returnString + myArray[i] + "";
        }
        return returnString;
    } 
}

