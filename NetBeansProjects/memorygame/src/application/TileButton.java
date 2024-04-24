
package application;
import java.awt.*;
//import javax.swing.*;

/**
 *
 * @author ammar
 */


/** This class creates a button that has two labels, one
*  which will be displayed when the button is "visible"
*  and the other will be displayed when the button is
* "invisible".
*/
class TileButton extends Button{
  private String labelForHiddenState = "xxxxxxxxx";
  private String labelForVisibleState = "";

  /** Default constructor. The "hidden" state label is set to
  * a string of 'X''s, the "visible" state string is set
  * to the empty string. 
  */
  public TileButton(){
    setLabel(labelForHiddenState); // set in invisible state
  }

  /** Constructs a TileButton and uses then given string to 
  * use as the label in its "visible" state.
  * The "hidden" state label is set to
  * the value of labelForHiddenState, initialized above.
  */
  public TileButton(String label){
    labelForVisibleState = label;  // remember visible string
    setLabel(labelForHiddenState); // set in invisible state
  }

  /** Put the button in the "visible" state. 
  */
  public void showLabel(){
    setLabel(labelForVisibleState); 
  }

  /** Put the button in the "hidden" state. 
  */
  public void hideLabel(){
    setLabel(labelForHiddenState);
  }

  /** return the button's "hidden" state string
  */
  public String getLabelForHiddenState(){
    return labelForHiddenState;
  }

  /** return the button's "visible" state string
  */
  public String getLabelForVisibleState(){
    return labelForVisibleState;
  }

  /** set the button's "hidden" state string
  */
  public void setLabelForHiddenState(String label){
    labelForHiddenState = label;
  }

  /** set the button's "visible" state string
  */
  public void setLabelForVisibleState(String label){
    labelForVisibleState = label;
  }
  
  /** Return true if the button is not in its "hidden" state.
  */
  public boolean isTileVisible() {
    return (getLabel().equals( labelForVisibleState));
  } // end isVisible()
} // end class TileButton

