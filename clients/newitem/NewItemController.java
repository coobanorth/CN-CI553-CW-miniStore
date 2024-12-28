package clients.newitem;

import clients.backDoor.BackDoorModel;
import clients.backDoor.BackDoorView;

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


}
