package RPG.Entities.HeroTypes;

import RPG.Entities.Hero;
import RPG.Entities.NPC;
import RPG.HeroItem;
import RPG.HeroItems.Consumables.PotionConsumable;
import RPG.Main;

public class Mutant extends Hero {

    public Mutant(String name, int hp, int strengh, int coinBalance) {
        super(name, hp, strengh, coinBalance);
    }

    /**
     * Method that makes a fight between the hero and a passed NPC
     * @param enemy NPC object
     * @return boolean <br> true - hero wins <br> npc wins
     */
    @Override
    public boolean attack(NPC enemy) {
        boolean npcAtacks = false;
        boolean specialAttackUsed = false;
        while (this.getHp() > 0 && enemy.getHp()>0){
            if(!npcAtacks){
                specialAttackUsed = super.attackMenu(enemy,specialAttackUsed);
                npcAtacks = true;
            }
            else {
                this.npcAttacks(enemy, 1.1);
                npcAtacks = false;
            }
        }
        if(this.getHp() > 0){
            this.setCoinBalance(this.getCoinBalance() + enemy.getCoinBalance());
            super.levelUp();
            return true;
        }
        else {
            return false;
        }
    }
}
