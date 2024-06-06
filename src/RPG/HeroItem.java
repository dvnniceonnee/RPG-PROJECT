package RPG;

import RPG.Enums.HeroTypes;

import java.util.ArrayList;

public abstract class HeroItem {
    private String name;
    protected final int coinPrice;
    protected ArrayList<HeroTypes> allowHeros;

    /**
     * Constructor of the class HeroItem
     * @param name Name of the item
     * @param coinPrice Price of the item
     */
    public HeroItem(String name, int coinPrice) {
        this.name = name;
        this.coinPrice = coinPrice;
        allowHeros = new ArrayList<HeroTypes>();
    }
    public abstract String getDetails(boolean sellerMode);

    public String details(boolean sellerMode){
        if(sellerMode)
            return this.name + " | \uD83D\uDCB2 (" + this.coinPrice + "\uD83D\uDFE1) ";
        else
            return this.name;
    }

    public String getName() {
        return name;
    }


}
