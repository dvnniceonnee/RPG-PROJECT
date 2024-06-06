package RPG.HeroItems;

import RPG.HeroItem;
import RPG.Main;

public abstract class Consumable extends HeroItem {

    public Consumable(String name, int coinPrice) {
        super(name, coinPrice);
    }

    @Override
    public abstract String getDetails(boolean sellerMode);
}

