package clients.newitem;

import catalogue.Basket;
import catalogue.Product;
import debug.DEBUG;
import middle.MiddleFactory;
import middle.StockException;
import middle.StockReadWriter;

import java.util.Observable;

public class NewItemModel extends Observable {
    private Basket NewItemOutput = null;            // Bought items
    private String pn = "";                      // Product being processed

    private StockReadWriter theStock = null;

    public NewItemModel(MiddleFactory mf) {
        try                                           //
        {
            theStock = mf.makeStockReadWriter();        // Database access
        } catch (Exception e) {
            DEBUG.error("CustomerModel.constructor\n%s", e.getMessage());
        }

        NewItemOutput = makeBasket();                     // Initial Basket
    }

    public Basket getNewItemOutput() {
        return NewItemOutput;
    }

    protected Basket makeBasket() {
        return new Basket();
    }

    /**
     * Clear the product()
     */
    public void doClear() {
        String theAction = "";
        NewItemOutput.clear();                        // Clear s. list
        theAction = "Enter Product Number";       // Set display
        setChanged();
        notifyObservers(theAction);  // inform the observer view that model changed
    }

    public void doSubmit(String Num,String desc, String price, String stock) {
        NewItemOutput.clear();                          // Clear s. list
        String theAction = "";
        pn  = Num.trim();                    // Product no.
        int amount = 1;                         //  & quantity
        try {
            if (theStock.exists(pn))              // Stock Exists?
            {                                         // T
                Product pr = theStock.getDetails(pn); //  Product
                if (pr.getQuantity() >= amount)       //  In stock?
                {
                    theAction =                           //   Display
                            String.format("%s : %7.2f (%2d) ", //
                                    pr.getDescription(),              //    description
                                    pr.getPrice(),                    //    price
                                    pr.getQuantity());               //    quantity

                    NewItemOutput.add(pr);                  //   Add to basket
                } else {                                //  F
                    theAction =                           //   Inform
                            pr.getDescription() +               //    product not
                                    " not in stock";                   //    in stock
                }
            } else {                                  // F
                theAction =                             //  Inform Unknown
                        "Unknown product number " + pn;       //  product number
            }
        } catch (StockException e) {
            DEBUG.error("CustomerClient.doCheck()\n%s",
                    e.getMessage());
        }
        setChanged();
        notifyObservers(theAction);
    }
}
