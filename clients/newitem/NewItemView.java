package clients.newitem;

import clients.Logo;
import clients.backDoor.BackDoorController;
import clients.backDoor.BackDoorModel;
import middle.MiddleFactory;
import middle.StockReadWriter;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class NewItemView implements Observer {

    private static final String RESTOCK  = "Add Stock";
    private static final String CLEAR    = "Clear";
    private static final String QUERY    = "Query Stock";

    private static final int H = 374;       // Height of window pixels
    private static final int W = 501;       // Width  of window pixels

    private final JLabel pageTitle  = new JLabel();
    private final JLabel      theAction  = new JLabel();
    private final JTextField  theInput   = new JTextField();
    private final JTextField  theInputNo = new JTextField();
    private final JTextField  theInput2   = new JTextField();
    private final JTextField  theInputNo3 = new JTextField();
    private final JTextArea   theOutput  = new JTextArea();
    private final JScrollPane theSP      = new JScrollPane();
    private final JButton     theBtClear = new JButton( CLEAR );


    private Logo theLogo = new Logo(192,40);

    private StockReadWriter theStock     = null;
    private NewItemController cont= null;

    /**
     * Construct the view
     * @param rpc   Window in which to construct
     * @param mf    Factor to deliver order and stock objects
     * @param x     x-cordinate of position of window on screen
     * @param y     y-cordinate of position of window on screen
     */
    public NewItemView(RootPaneContainer rpc, MiddleFactory mf, int x, int y )
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


        theBtClear.setBounds( 4, 100+40+10, 192, 40 );    // clear button
        theBtClear.setBackground(mainblue);             //set background to blue
        theBtClear.setForeground( Color.WHITE );        //set text to white
        theBtClear.addActionListener(                   // Call back code
                e -> cont.doClear() );
        cp.add( theBtClear );                           //  Add to canvas


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

        theInput2.setBounds( 210, 100, 130, 40 );         // input area
        theInput2.setBackground(maingray);             //set background to gray
        theInput2.setForeground(Color.WHITE);        //set text to white
        theInput2.setText("Product Number");                           // Blank
        cp.add( theInput2 );                             //  Add to canvas

        theInputNo3.setBounds( 350, 100, 130, 40 );         // Input Area
        theInputNo3.setBackground(maingray);             //set background to gray
        theInputNo3.setForeground(Color.WHITE);        //set text to white
        theInputNo3.setText("Stock To Add");                        // 0
        cp.add( theInputNo3 );                           //  Add to canvas

        theSP.setBounds( 210, 150, 270, 160 );          // Scrolling pane
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

    public void setController( NewItemController c )
    {
        cont = c;
    }

    /**
     * Update the view, called by notifyObservers(theAction) in model,
     * @param modelC   The observed model
     * @param arg      Specific args
     */
    @Override
    public void update(Observable modelC, Object arg )
    {
        NewItemModel model  = (NewItemModel) modelC;
        String        message = (String) arg;
        theAction.setText( message );

        theOutput.setText( model.getBasket().getDetails() );
        theInput.requestFocus();
    }

}
