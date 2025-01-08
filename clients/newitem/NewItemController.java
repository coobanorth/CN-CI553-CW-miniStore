package clients.newitem;

import clients.backDoor.BackDoorModel;
import clients.backDoor.BackDoorView;
import middle.StockException;

public class NewItemController {

    private NewItemModel model = null;
    private NewItemView view  = null;
    /**
     * Constructor
     * @param model The model
     * @param view  The view from which the interaction came
     */
    public NewItemController( NewItemModel model, NewItemView view )
    {
        this.view  = view;
        this.model = model;
    }

    public void doClear()
    {
        model.doClear();
    }


    public void doSubmit(String Num, String Desc, String Price, String Stock) throws StockException {
    model.doSubmit(Num,Desc,Price,Stock);
    }

    public void doAll() {
        model.doAll();
    }
}
