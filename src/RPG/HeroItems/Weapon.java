package RPG.HeroItems;

import RPG.HeroItem;
import RPG.Main;

public class Weapon extends HeroItem {
    private final int damage;
    private final int specialDamage;

    /**
     * Constructor for the class Weapon
     * @param name string for the name of the Weapon
     * @param coinPrice int for the price of the Weapon
     * @param damage int Damage that the weapon will take
     * @param specialDamage int for damage that the Special Attack will take
     */
    public Weapon(String name, int coinPrice, int damage, int specialDamage) {
        super(name, coinPrice);
        this.damage = damage;
        this.specialDamage = specialDamage;
    }

    public int getDamage() {
        return damage;
    }

    public int getSpecialDamage() {
        return specialDamage;
    }

    @Override
    public String getDetails(boolean sellerMode){
        return "\uD83D\uDDE1️ Weapon -> " + super.details(sellerMode) + " | ⚔\uFE0F (" + this.damage + ") | ⚔\uFE0F✨ (" + this.specialDamage + ")";
    }
}
