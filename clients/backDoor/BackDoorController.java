package clients.backDoor;


import clients.PosOnScrn;
import clients.newitem.NewItemController;
import clients.newitem.NewItemModel;
import clients.newitem.NewItemView;
import middle.LocalMiddleFactory;
import middle.MiddleFactory;

import javax.swing.*;
import java.awt.*;

/**
 * The BackDoor Controller
 */

public class BackDoorController
{
  private BackDoorModel model = null;
  private BackDoorView  view  = null;
  /**
   * Constructor
   * @param model The model
   * @param view  The view from which the interaction came
   */
  public BackDoorController( BackDoorModel model, BackDoorView view )
  {
    this.view  = view;
    this.model = model;
  }

  /**
   * Query interaction from view
   * @param pn The product number to be checked
   */
  public void doQuery( String pn )
  {
    model.doQuery(pn);
  }

  /**
   * RStock interaction from view
   * @param pn       The product number to be re-stocked
   * @param quantity The quantity to be re-stocked
   */
  public void doRStock( String pn, String quantity )
  {
    model.doRStock(pn, quantity);
  }

  /**
   * Clear interaction from view
   */
  public void doClear()
  {
    model.doClear();
  }


  public void doNew() {
    MiddleFactory mlf = new LocalMiddleFactory();  // Direct access
    startNewItemGUI_MVC( mlf );
  }

  public void startNewItemGUI_MVC(MiddleFactory mlf )
  {
    JFrame window = new JFrame();

    window.setTitle( "NewItem Client MVC");
    window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    Dimension pos = PosOnScrn.getPos();

    NewItemModel model      = new NewItemModel(mlf);
    NewItemView view        = new NewItemView( window, mlf, pos.width, pos.height );
    NewItemController cont  = new NewItemController( model, view );
    view.setController( cont );

    model.addObserver( view );       // Add observer to the model
    window.setVisible(true);         // Make window visible
  }
}

