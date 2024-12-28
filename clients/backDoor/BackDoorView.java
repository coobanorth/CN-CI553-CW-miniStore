package clients.backDoor;

import clients.Logo;
import middle.MiddleFactory;
import middle.StockReadWriter;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Implements the Customer view.
 */

public class BackDoorView implements Observer
{
  private static final String RESTOCK  = "Add Stock";
  private static final String CLEAR    = "Clear";
  private static final String QUERY    = "Query Stock";
  private static final String NEW    = "New Sellable Item";
 
  private static final int H = 374;       // Height of window pixels
  private static final int W = 501;       // Width  of window pixels

  private final JLabel      pageTitle  = new JLabel();
  private final JLabel      theAction  = new JLabel();
  private final JTextField  theInput   = new JTextField();
  private final JTextField  theInputNo = new JTextField();
  private final JTextArea   theOutput  = new JTextArea();
  private final JScrollPane theSP      = new JScrollPane();
  private final JButton     theBtClear = new JButton( CLEAR );
  private final JButton     theBtRStock = new JButton( RESTOCK );
  private final JButton     theBtQuery = new JButton( QUERY );
  private final JButton     theBtNew = new JButton( NEW );


  private Logo theLogo = new Logo(192,40);

  private StockReadWriter theStock     = null;
  private BackDoorController cont= null;

  /**
   * Construct the view
   * @param rpc   Window in which to construct
   * @param mf    Factor to deliver order and stock objects
   * @param x     x-cordinate of position of window on screen 
   * @param y     y-cordinate of position of window on screen  
   */
  public BackDoorView(  RootPaneContainer rpc, MiddleFactory mf, int x, int y )
  {
    try                                             // 
    {      
      theStock = mf.makeStockReadWriter();          // Database access
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
    pageTitle.setText( "Staff check and manage stock" );
    pageTitle.setForeground( Color.WHITE );
    cp.add( pageTitle );
    
    theBtQuery.setBounds( 4, 50, 192, 40 );    // Buy button
    theBtQuery.setBackground(mainblue);             //set background to blue
    theBtQuery.setForeground( Color.WHITE );        //set text to white
    theBtQuery.addActionListener(                   // Call back code
      e -> cont.doQuery( theInput.getText() ) );
    cp.add( theBtQuery );                           //  Add to canvas

    theBtRStock.setBounds( 4, 50+40+10, 192, 40 );   // Bought Button
    theBtRStock.setBackground(mainblue);             //set background to blue
    theBtRStock.setForeground( Color.WHITE );        //set text to white
    theBtRStock.addActionListener(                  // Call back code
      e -> cont.doRStock( theInput.getText(),
                          theInputNo.getText() ) );
    cp.add( theBtRStock );                          //  Add to canvas


    theBtClear.setBounds( 4, 100+40+10, 192, 40 );    // clear button
    theBtClear.setBackground(mainblue);             //set background to blue
    theBtClear.setForeground( Color.WHITE );        //set text to white
    theBtClear.addActionListener(                   // Call back code
      e -> cont.doClear() );
    cp.add( theBtClear );                           //  Add to canvas

    theBtNew.setBounds( 4, 150+40+10, 192, 40 );    // clear button
    theBtNew.setBackground(mainblue);             //set background to blue
    theBtNew.setForeground( Color.WHITE );        //set text to white
    theBtNew.addActionListener(                   // Call back code
            e -> cont.doNew() );
    cp.add( theBtNew );                           //  Add to canvas


    theAction.setBounds( 210, 25 , 270, 20 );       // Message area
    theAction.setBackground(maingray);             //set background to gray
    theAction.setForeground(Color.WHITE);        //set text to white
    theAction.setText( "" );                        // Blank
    cp.add( theAction );                            //  Add to canvas

    theInput.setBounds( 210, 50, 130, 40 );         // input area
    theInput.setBackground(maingray);             //set background to gray
    theInput.setForeground(Color.WHITE);        //set text to white
    theInput.setText("Product Number");                           // Blank
    cp.add( theInput );                             //  Add to canvas

    theInputNo.setBounds( 350, 50, 130, 40 );         // Input Area
    theInputNo.setBackground(maingray);             //set background to gray
    theInputNo.setForeground(Color.WHITE);        //set text to white
    theInputNo.setText("Stock To Add");                        // 0
    cp.add( theInputNo );                           //  Add to canvas

    theSP.setBounds( 210, 100, 270, 160 );          // Scrolling pane
    theSP.setBackground(maingray);             //set background to gray
    theSP.setForeground(Color.WHITE);        //set text to white
    theOutput.setText( "" );                        //  Blank
    theOutput.setFont( f );                         //  Uses font  
    cp.add( theSP );                                //  Add to canvas
    theSP.getViewport().add( theOutput );           //  In TextArea

    theLogo.setBounds( 4, 0, 192, 40 );   // Picture area
    cp.add(theLogo);                           //  Add to canvas

    rootWindow.setVisible( true );                  // Make visible
    theInput.requestFocus();                        // Focus is here
  }
  
  public void setController( BackDoorController c )
  {
    cont = c;
  }

  /**
   * Update the view, called by notifyObservers(theAction) in model,
   * @param modelC   The observed model
   * @param arg      Specific args 
   */
  @Override
  public void update( Observable modelC, Object arg )  
  {
    BackDoorModel model  = (BackDoorModel) modelC;
    String        message = (String) arg;
    theAction.setText( message );
    
    theOutput.setText( model.getBasket().getDetails() );
    theInput.requestFocus();
  }

}