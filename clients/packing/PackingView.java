package clients.packing;

import catalogue.Basket;
import clients.Logo;
import middle.MiddleFactory;
import middle.OrderProcessing;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Implements the Packing view.

 */

public class PackingView implements Observer
{
  private static final String PACKED = "Order Packed";

  private static final int H = 374;       // Height of window pixels
  private static final int W = 501;       // Width  of window pixels

  private final JLabel      pageTitle  = new JLabel();
  private final JLabel      theAction  = new JLabel();
  private final JTextArea   theOutput  = new JTextArea();
  private final JScrollPane theSP      = new JScrollPane();
  private final JButton     theBtPack= new JButton( PACKED );

  private Logo theLogo = new Logo(192,40);

  private OrderProcessing theOrder     = null;
  
  private PackingController cont= null;

  /**
   * Construct the view
   * @param rpc   Window in which to construct
   * @param mf    Factor to deliver order and stock objects
   * @param x     x-cordinate of position of window on screen 
   * @param y     y-cordinate of position of window on screen  
   */
  public PackingView(  RootPaneContainer rpc, MiddleFactory mf, int x, int y )
  {
    try                                           // 
    {      
      theOrder = mf.makeOrderProcessing();        // Process order
    } catch ( Exception e )
    {
      System.out.println("Exception: " + e.getMessage() );
    }

    Font f = new Font("Monospaced",Font.PLAIN,12);  // Font f is
    Color mainblue = new Color(16, 72, 98);
    Color maingray = new Color(116, 116, 116);

    Container cp         = rpc.getContentPane();    // Content Pane
    Container rootWindow = (Container) rpc;         // Root Window
    cp.setLayout(null);                             // No layout manager
    rootWindow.setSize( W, H );                     // Size of Window
    rootWindow.setLocation( x, y );

    cp.setBackground(maingray);             //set background to gray

    pageTitle.setBounds( 210, 0 , 270, 20 );
    pageTitle.setText( "Packing An Order" );
    pageTitle.setForeground( Color.WHITE );
    cp.add( pageTitle );

    theBtPack.setBounds( 4, 50, 192, 40 );   // Check Button
    theBtPack.setBackground(mainblue);             //set background to blue
    theBtPack.setForeground( Color.WHITE );        //set text to white
    theBtPack.addActionListener(                   // Call back code
      e -> cont.doPacked() );
    cp.add( theBtPack );                          //  Add to canvas

    theAction.setBounds( 210, 25 , 270, 20 );       // Message area
    theAction.setBackground(maingray);             //set background to gray
    theAction.setForeground(Color.WHITE);        //set text to white
    theAction.setText( "" );                        // Blank
    cp.add( theAction );                            //  Add to canvas

    theSP.setBounds( 210, 50, 270, 160 );          // Scrolling pane
    theSP.setBackground(maingray);             //set background to gray
    theSP.setForeground(Color.WHITE);        //set text to white
    theOutput.setText( "" );                        //  Blank
    theOutput.setFont( f );                         //  Uses font  
    cp.add( theSP );                                //  Add to canvas
    theSP.getViewport().add( theOutput );           //  In TextArea

    theLogo.setBounds( 4, 0, 192, 40 );   // Picture area
    cp.add(theLogo);                           //  Add to canvas

    rootWindow.setVisible( true );                  // Make visible
  }
  
  public void setController( PackingController c )
  {
    cont = c;
  }

  /**
   * Update the view
   * @param modelC   The observed model
   * @param arg      Specific args 
   */
  @Override
  public void update( Observable modelC, Object arg )
  {
	  PackingModel model  = (PackingModel) modelC;
    String        message = (String) arg;
    theAction.setText( message );
    
    Basket basket =  model.getBasket();
    if ( basket != null )
    {
      theOutput.setText( basket.getDetails() );
    } else {
      theOutput.setText("");
    }
  }

}

