package clients.cashier;

import catalogue.Basket;
import clients.Logo;
import middle.MiddleFactory;
import middle.OrderProcessing;
import middle.StockReadWriter;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;


/**
 * View of the model 
 */
public class CashierView implements Observer
{
  private static final int H = 374;       // Height of window pixels
  private static final int W = 501;       // Width  of window pixels
  
  private static final String CHECK  = "Search Product Number";
  private static final String BUY    = "Add To Basket";
  private static final String BOUGHT = "Checkout";

  private final JLabel      pageTitle  = new JLabel();
  private final JLabel      theAction  = new JLabel();
  private final JTextField  theInput   = new JTextField();
  private final JTextField  buyQuantity   = new JTextField();
  private final JTextArea   theOutput  = new JTextArea();
  private final JScrollPane theSP      = new JScrollPane();
  private final JButton     theBtCheck = new JButton( CHECK );
  private final JButton     theBtBuy   = new JButton( BUY );
  private final JButton     theBtBought= new JButton( BOUGHT );

  private Logo theLogo = new Logo(192,40);

  private StockReadWriter theStock     = null;
  private OrderProcessing theOrder     = null;
  private CashierController cont       = null;
  
  /**
   * Construct the view
   * @param rpc   Window in which to construct
   * @param mf    Factor to deliver order and stock objects
   * @param x     x-coordinate of position of window on screen 
   * @param y     y-coordinate of position of window on screen  
   */
          
  public CashierView(  RootPaneContainer rpc,  MiddleFactory mf, int x, int y  )
  {
    try                                           // 
    {      
      theStock = mf.makeStockReadWriter();        // Database access
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
    pageTitle.setText( "Thank You for Shopping at MiniStrore" );
    pageTitle.setForeground( Color.WHITE );
    cp.add( pageTitle );  
    
    theBtCheck.setBounds( 4, 50, 192, 40 );    // Check Button
    theBtCheck.setBackground(mainblue);             //set background to blue
    theBtCheck.setForeground( Color.WHITE );        //set text to white
    theBtCheck.addActionListener(                   // Call back code
      e -> cont.doCheck( theInput.getText(), Integer.parseInt(buyQuantity.getText()) ) );
    cp.add( theBtCheck );                           //  Add to canvas

    theBtBuy.setBounds( 4, 50+40+10, 192, 40 );      // Buy button
    theBtBuy.setBackground(mainblue);             //set background to blue
    theBtBuy.setForeground( Color.WHITE );        //set text to white
    theBtBuy.addActionListener(                     // Call back code
      e -> cont.doBuy() );
    cp.add( theBtBuy );                             //  Add to canvas

    theBtBought.setBounds( 4, 100+40+10, 192, 40 );   // Bought Button
    theBtBought.setBackground(mainblue);             //set background to blue
    theBtBought.setForeground( Color.WHITE );        //set text to white
    theBtBought.addActionListener(                  // Call back code
      e -> cont.doBought() );
    cp.add( theBtBought );                          //  Add to canvas

    theAction.setBounds( 210, 25 , 270, 20 );       // Message area
    theAction.setBackground(maingray);             //set background to gray
    theAction.setForeground(Color.WHITE);        //set text to white
    theAction.setText( "" );                       // blank
    cp.add( theAction );                            //  Add to canvas

    theInput.setBounds( 210, 50, 130, 40 );         // Product no area
    theInput.setBackground(maingray);             //set background to gray
    theInput.setForeground(Color.WHITE);        //set text to white
    theInput.setText("Search");                           // Blank
    cp.add( theInput );                              //  Add to canvas

    buyQuantity.setBounds( 350, 50, 130, 40 );         // Input Area
    buyQuantity.setBackground(maingray);             //set background to gray
    buyQuantity.setForeground(Color.WHITE);        //set text to white
    buyQuantity.setText("1");                           // Blank
    cp.add( buyQuantity );                             //  Add to canvas

    theSP.setBounds( 210, 100, 270, 160 );          // Scrolling pane
    theSP.setBackground(maingray);             //set background to gray
    theSP.setForeground(Color.WHITE);        //set text to white
    theOutput.setFont( f );                         //  Uses font  
    cp.add( theSP );                                //  Add to canvas
    theSP.getViewport().add( theOutput );           //  In TextArea

    theLogo.setBounds( 4, 0, 192, 40 );   // Picture area
    cp.add(theLogo);                           //  Add to canvas

    rootWindow.setVisible( true );                  // Make visible
    theInput.requestFocus();                        // Focus is here
  }

  /**
   * The controller object, used so that an interaction can be passed to the controller
   * @param c   The controller
   */

  public void setController( CashierController c )
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
    CashierModel model  = (CashierModel) modelC;
    String      message = (String) arg;
    theAction.setText( message );
    Basket basket = model.getBasket();
    if ( basket == null )
      theOutput.setText( "Customers order" );
    else
      theOutput.setText( basket.getDetails() );
    
    theInput.requestFocus();               // Focus is here

    if(message.equals("!!! Not in stock") || message.contains("Purchased ")){
      buyQuantity.setText("1");
  }
  }

}
