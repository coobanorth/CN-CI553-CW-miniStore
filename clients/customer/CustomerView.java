package clients.customer;

import catalogue.Basket;
import catalogue.BetterBasket;
import clients.Logo;
import clients.Picture;
import middle.MiddleFactory;
import middle.StockReader;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Implements the Customer view.
 */

public class CustomerView implements Observer
{
  class Name                              // Names of buttons
  {
    public static final String CHECK  = "Search Product Number";
    public static final String CHECKName  = "Search Product Name";
    public static final String CLEAR  = "Clear Results";
  }

  private static final int H = 324;       // Height of window pixels
  private static final int W = 501;       // Width  of window pixels

  private final JLabel      pageTitle  = new JLabel();
  private final JLabel      theAction  = new JLabel();
  private final JTextField  theInput   = new JTextField();
  private final JTextArea   theOutput  = new JTextArea();
  private final JScrollPane theSP      = new JScrollPane();
  private final JButton     theBtCheck = new JButton( Name.CHECK );
  private final JButton     theBtCheckName = new JButton( Name.CHECKName );
  private final JButton     theBtClear = new JButton( Name.CLEAR );

  private Picture thePicture = new Picture(80,80);
  private Logo theLogo = new Logo(192,40);
  private StockReader theStock   = null;
  private CustomerController cont= null;

  /**
   * Construct the view
   * @param rpc   Window in which to construct
   * @param mf    Factor to deliver order and stock objects
   * @param x     x-cordinate of position of window on screen 
   * @param y     y-cordinate of position of window on screen  
   */
  
  public CustomerView( RootPaneContainer rpc, MiddleFactory mf, int x, int y )
  {
    try                                             // 
    {      
      theStock  = mf.makeStockReader();             // Database Access
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
    pageTitle.setText( "Search products" );
    pageTitle.setForeground( Color.WHITE );
    cp.add( pageTitle );

    theBtCheck.setBounds( 4, 50+40+10, 192, 40 );    // Check button
    theBtCheck.setBackground(mainblue);             //set background to blue
    theBtCheck.setForeground( Color.WHITE );        //set text to white
    theBtCheck.addActionListener(                   // Call back code
      e -> cont.doCheck( theInput.getText() ) );
    cp.add( theBtCheck );                           //  Add to canvas

    theBtCheckName.setBounds( 4, 50, 192, 40 );    // Check button
    theBtCheckName.setBackground(mainblue);             //set background to blue
    theBtCheckName.setForeground( Color.WHITE );        //set text to white
    theBtCheckName.addActionListener(                   // Call back code
            e -> cont.doCheckByName( theInput.getText() ) );
    cp.add( theBtCheckName );                           //  Add to canvas

    theBtClear.setBounds( 4, 100+40+10, 192, 40 );    // Clear button
    theBtClear.setBackground(mainblue);             //set background to blue
    theBtClear.setForeground( Color.WHITE );        //set text to white
    theBtClear.addActionListener(                   // Call back code
      e -> cont.doClear() );
    cp.add( theBtClear );                           //  Add to canvas

    theAction.setBounds( 210, 25 , 270, 20 );       // Message area
    theAction.setBackground(maingray);             //set background to gray
    theAction.setForeground(Color.WHITE);        //set text to white
    theAction.setText( "" );                       // blank
    cp.add( theAction );                            //  Add to canvas

    theInput.setBounds( 210, 50, 270, 40 );         // Product no area
    theInput.setBackground(maingray);             //set background to gray
    theInput.setForeground(Color.WHITE);        //set text to white
    theInput.setText("Search");                           // Blank
    cp.add( theInput );                             //  Add to canvas
    
    theSP.setBounds( 210, 100, 270, 160 );          // Scrolling pane
    theSP.setBackground(maingray);             //set background to gray
    theSP.setForeground(Color.WHITE);        //set text to white
    theOutput.setText( "" );                        //  Blank
    theOutput.setFont( f );                         //  Uses font  
    cp.add( theSP );                                //  Add to canvas
    theSP.getViewport().add( theOutput );           //  In TextArea

    thePicture.setBounds( 60, 150+40+10, 80, 80 );   // Picture area
    cp.add( thePicture );                           //  Add to canvas
    thePicture.clear();

    theLogo.setBounds( 4, 0, 192, 40 );   // Picture area
    cp.add(theLogo);                           //  Add to canvas

    
    rootWindow.setVisible( true );                  // Make visible);
    theInput.requestFocus();                        // Focus is here
  }

   /**
   * The controller object, used so that an interaction can be passed to the controller
   * @param c   The controller
   */

  public void setController( CustomerController c )
  {
    cont = c;
  }

  /**
   * Update the view
   * @param modelC   The observed model
   * @param arg      Specific args 
   */
   
  public void update( Observable modelC, Object arg )
  {
    CustomerModel model  = (CustomerModel) modelC;
    String        message = (String) arg;
    theAction.setText( message );
    ImageIcon image = model.getPicture();  // Image of product
    if ( image == null )
    {
      thePicture.clear();                  // Clear picture
    } else {
      thePicture.set( image );             // Display picture
    }
    theOutput.setText( model.getBasket().getDetails() );
    theInput.requestFocus();               // Focus is here
  }

}
