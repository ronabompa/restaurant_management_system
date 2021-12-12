package BusinessLayer;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Rona Dumitrescu on 23.05.2019.
 */
public class CompositeProduct extends MenuItem {

    List<MenuItem> compositeProductslist = new LinkedList<MenuItem>();

    @Override
    public void computePrice()
    {

    }
}
