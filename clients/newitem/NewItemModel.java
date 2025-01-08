package clients.newitem;

import catalogue.Basket;
import catalogue.NewItemOutput;
import catalogue.Product;
import debug.DEBUG;
import middle.MiddleFactory;
import middle.StockException;
import middle.StockReadWriter;
import dbAccess.StockR;

import java.util.Observable;

public class NewItemModel extends Observable {
    private NewItemOutput NewItemOutput = null;            // Bought items
    private String pn = "";                      // Product being processed

    private StockReadWriter theStock = null;

    public NewItemModel(MiddleFactory mf) {
        try                                           //
        {
            theStock = mf.makeStockReadWriter();        // Database access
        } catch (Exception e) {
            DEBUG.error("CustomerModel.constructor\n%s", e.getMessage());
        }

        NewItemOutput = makeNewItemOutput();                     // Initial Basket
    }

    public NewItemOutput getNewItemOutput() {
        return NewItemOutput;
    }

    protected NewItemOutput makeNewItemOutput() {
        return new NewItemOutput();
    }

    /**
     * Clear the product()
     */
    public void doClear()
    {
        String theAction = "";
        NewItemOutput.clear();                        // Clear s. list
        theAction = "";       // Set display
        setChanged(); notifyObservers(theAction);
    }

    public void doSubmit(String Num,String desc, String price, String stock) throws StockException {
        doClear();
        String theAction = "";
        boolean exist = ProductExistCheck(Num);

        if (exist == false){
            //AddProduct();
            //OutputMessage(Num, desc, price, stock);
            theAction = "Space For New Product";
        }

        else{
            theAction = "Product Number Already Exists";
        }
        setChanged();
        notifyObservers(theAction);
    }

    public boolean ProductExistCheck(String Num) throws StockException {
        Boolean NumLookup = LookupPN(Num);
        if (NumLookup = true) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean LookupPN(String Num) throws StockException {
        StockR sr = new StockR();
        boolean state = sr.exists(Num);

        if (state = true) {
            return true;
        }
        else {
            return false;
        }
    }

    public void AddProduct(){}



    public void OutputMessage(String Num,String desc, String price, String stock) {

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

    public void doAll(){
        String productNum = "0001";
        NewItemOutput.clear();                          // Clear s. list
        String theAction = "";
        pn  = productNum.trim();                    // Product no.
        int    amount  = 1;                         //  & quantity
        for(int x=1;x<8;x++){
            pn = "000"+ String.valueOf(x);
            try {
                Product pr = theStock.getDetails(pn); //  Product
                    NewItemOutput.add( pr );                  //   Add to basket
            }

            catch (StockException e)
            {
                DEBUG.error("CustomerClient.doCheck()\n%s",
                        e.getMessage());
            }
        }
        setChanged(); notifyObservers(theAction);
    }


}
