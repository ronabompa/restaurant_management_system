package BusinessLayer;

import java.io.Serializable;

/**
 * Created by Rona Dumitrescu on 23.05.2019.
 */
public  class MenuItem implements Serializable{

    private Double price;
    private String name;

    // GETTERS & SETTERS

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public  void  computePrice()
    {

    }


}
