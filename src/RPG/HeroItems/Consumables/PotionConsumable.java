package RPG.HeroItems.Consumables;

import RPG.HeroItems.Consumable;
import RPG.Main;

public class PotionConsumable extends Consumable {
    private final int healthIncrease;
    private final int strenghIncrease;

    /**
     * Constructor of the class potion that extends Consumable
     * @param name Name of the consumable
     * @param coinPrice int Price of the item
     * @param healthIncrease health that the consumable will increase
     * @param strenghIncrease strengh that the consumable will increase
     */
    public PotionConsumable(String name, int coinPrice, int healthIncrease, int strenghIncrease) {
        super(name, coinPrice);
        this.healthIncrease = healthIncrease;
        this.strenghIncrease = strenghIncrease;
    }

    public int getHealthIncrease() {
        return healthIncrease;
    }

    public int getStrenghIncrease() {
        return strenghIncrease;
    }

    @Override
    public String getDetails(boolean sellerMode){
        return "⚗\uFE0F Potion -> " + super.details(sellerMode) + " | \uD83D\uDC97(+" + this.healthIncrease + ") | \uD83D\uDCAA(+" + this.strenghIncrease + ")";
    }


}
