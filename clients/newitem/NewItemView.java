package clients.newitem;

import clients.Logo;
import middle.MiddleFactory;
import middle.StockReadWriter;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class NewItemView implements Observer {

    private static final String SUBMIT    = "Submit";


    private static final int H = 374;       // Height of window pixels
    private static final int W = 501;       // Width  of window pixels

    private final JLabel pageTitle  = new JLabel();
    private final JLabel      theAction  = new JLabel();
    private final JTextField  theInputPrice   = new JTextField();
    private final JTextField  theInputStock = new JTextField();
    private final JTextField  theInputDesc   = new JTextField();
    private final JTextArea   theOutput  = new JTextArea();
    private final JScrollPane theSP      = new JScrollPane();
    private final JButton theBtSubmit = new JButton( SUBMIT );


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
        pageTitle.setText( "Add New Item Of Stock" );
        pageTitle.setForeground( Color.WHITE );
        cp.add( pageTitle );


        theBtSubmit.setBounds( 4, 200+40+10, 192, 40 );    // clear button
        theBtSubmit.setBackground(mainblue);             //set background to blue
        theBtSubmit.setForeground( Color.WHITE );        //set text to white
        theBtSubmit.addActionListener(                   // Call back code
                e -> cont.doClear() );
        cp.add(theBtSubmit);                           //  Add to canvas


        theAction.setBounds( 210, 25 , 270, 20 );       // Message area
        theAction.setBackground(maingray);             //set background to gray
        theAction.setForeground(Color.WHITE);        //set text to white
        theAction.setText( "" );                        // Blank
        cp.add( theAction );                            //  Add to canvas

        theInputPrice.setBounds( 210, 50, 130, 40 );         // input area
        theInputPrice.setBackground(maingray);             //set background to gray
        theInputPrice.setForeground(Color.WHITE);        //set text to white
        theInputPrice.setText("Product Price");                           // Blank
        cp.add( theInputPrice );                             //  Add to canvas

        theInputStock.setBounds( 350, 50, 130, 40 );         // Input Area
        theInputStock.setBackground(maingray);             //set background to gray
        theInputStock.setForeground(Color.WHITE);        //set text to white
        theInputStock.setText("Amount In Stock");                        // 0
        cp.add( theInputStock );                           //  Add to canvas

        theInputDesc.setBounds( 210, 100, 270, 40 );         // input area
        theInputDesc.setBackground(maingray);             //set background to gray
        theInputDesc.setForeground(Color.WHITE);        //set text to white
        theInputDesc.setText("Product Description");                           // Blank
        cp.add( theInputDesc );                             //  Add to canvas

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
        theInputPrice.requestFocus();                        // Focus is here
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
        theInputPrice.requestFocus();
    }

}
