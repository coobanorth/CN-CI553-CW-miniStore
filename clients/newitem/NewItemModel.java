package clients.newitem;

import catalogue.Basket;
import debug.DEBUG;
import middle.MiddleFactory;
import middle.StockReadWriter;

import java.util.Observable;

public class NewItemModel extends Observable {
    private Basket      theBasket  = null;            // Bought items
    private String      pn = "";                      // Product being processed

    private StockReadWriter theStock     = null;

    public NewItemModel(MiddleFactory mf)
    {
        try                                           //
        {
            theStock = mf.makeStockReadWriter();        // Database access
        } catch ( Exception e )
        {
            DEBUG.error("CustomerModel.constructor\n%s", e.getMessage() );
        }

        theBasket = makeBasket();                     // Initial Basket
    }

    public Basket getBasket()
    {
        return theBasket;
    }

    protected Basket makeBasket()
    {
        return new Basket();
    }

    /**
     * Clear the product()
     */
    public void doClear()
    {
        String theAction = "";
        theBasket.clear();                        // Clear s. list
        theAction = "Enter Product Number";       // Set display
        setChanged(); notifyObservers(theAction);  // inform the observer view that model changed
    }

}
