package clients.newitem;

import middle.MiddleFactory;
import middle.Names;
import middle.RemoteMiddleFactory;

import javax.swing.*;

public class NewItemClient {
    public static void main (String args[])
    {
        String stockURL = args.length < 1     // URL of stock RW
                ? Names.STOCK_RW      //  default  location
                : args[0];            //  supplied location
        String orderURL = args.length < 2     // URL of order
                ? Names.ORDER         //  default  location
                : args[1];            //  supplied location

        RemoteMiddleFactory mrf = new RemoteMiddleFactory();
        mrf.setStockRWInfo( stockURL );
        mrf.setOrderInfo  ( orderURL );        //
        displayGUI(mrf);                       // Create GUI
    }


    private static void displayGUI(MiddleFactory mf)
    {
        JFrame window = new JFrame();

        window.setTitle( "New Item Client (MVC RMI)");
        window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        NewItemModel model = new NewItemModel(mf);
        NewItemView view  = new NewItemView( window, mf, 0, 0 );
        NewItemController cont  = new NewItemController( model, view );
        view.setController( cont );

        model.addObserver( view );       // Add observer to the model
        window.setVisible(true);         // Display Screen
    }
}
