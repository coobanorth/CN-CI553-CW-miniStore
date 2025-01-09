package clients.newitem;

import dbAccess.DBAccess;
import dbAccess.DBAccessFactory;

import catalogue.NewItemOutput;
import catalogue.Product;
import debug.DEBUG;
import middle.MiddleFactory;
import middle.StockException;
import middle.StockReadWriter;
import dbAccess.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Observable;

public class NewItemModel extends Observable {
    private NewItemOutput NewItemOutput = null;            // view all
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

    public void doSubmit(String num,String desc, String price, String stock) throws StockException {
        doClear();
        String theAction = "";
        boolean exist = ProductExistCheck(num);

        if (exist == false){
            AddProduct(num, desc, price, stock);
            theAction = "Product Added Successfully";
        }

        else{
            theAction = "Product Number Already Exists";
        }

        setChanged();
        notifyObservers(theAction);
    }

    public boolean ProductExistCheck(String Num) throws StockException {
        boolean NumLookup = LookupPN(Num);

        if (NumLookup == true) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean LookupPN(String Num) throws StockException {
        StockR sr = new StockR();
        boolean state = sr.exists(Num);

        if (state == true) {
            return true;
        }
        else {
            return false;
        }
    }

    public void AddProduct(String num,String desc, String price, String stock) throws StockException {
        String SQLProduct = "insert into ProductTable values " + "('"+num+"', '"+desc+"', '', "+price+")";
        String SQLStock = "insert into StockTable values " + "('"+num+"', '"+stock+")";

        StockRW srw = new StockRW();
        srw.addProduct(num,desc,price,stock);

    }

    public void doAll(){
        String productNum = "0001";
        NewItemOutput.clear();                          // Clear s. list
        String theAction = "";
        pn  = productNum.trim();                    // Product no.
        int    amount  = 1;                         //  & quantity
        for(int x=1;x<101;x++){
            if(x<10) {
                pn = "000" + String.valueOf(x);
            }
            else if(x<100){
                pn = "00" + String.valueOf(x);
            }
            else if(x<1000){
                pn = "0" + String.valueOf(x);
            }
            else {
                pn = String.valueOf(x);
            }


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
