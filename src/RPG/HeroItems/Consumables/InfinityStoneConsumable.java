package RPG.HeroItems.Consumables;

import RPG.HeroItem;
import RPG.HeroItems.Consumable;

import java.util.Random;

public class InfinityStoneConsumable extends Consumable {
    private final int strenghIncrease;

    public InfinityStoneConsumable() {
        super("Infinity Stone", 0);
        this.strenghIncrease = setRandomStrenghIncrease();
    }

    public int getStrenghIncrease() {
        return strenghIncrease;
    }

    public int setRandomStrenghIncrease(){
        Random random = new Random();
        return random.nextInt(30,50);
    }

    @Override
    public String getDetails(boolean sellerMode) {
        return "\uD83D\uDC8E " + super.details(sellerMode) ;
    }
}
