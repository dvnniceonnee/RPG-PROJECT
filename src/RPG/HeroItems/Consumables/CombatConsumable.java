package RPG.HeroItems.Consumables;

import RPG.HeroItems.Consumable;
import RPG.Main;

public class CombatConsumable extends Consumable {
    private final int immediateDamage;

    public CombatConsumable(String name, int coinPrice, int immediateDamage) {
        super(name, coinPrice);
        this.immediateDamage = immediateDamage;
    }

    public int getImmediateDamage() {
        return immediateDamage;
    }

    @Override
    public String getDetails(boolean sellerMode) {
        return "\uD83D\uDCA3 Combat item -> " + super.details(sellerMode) + " | ⚔\uFE0F (" + this.immediateDamage + ")";
    }
}
