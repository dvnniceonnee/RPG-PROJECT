package RPG.Entities.HeroTypes;

import RPG.Entities.Hero;
import RPG.Entities.NPC;
import RPG.Main;

public class Wizard extends Hero {

    public Wizard(String name, int hp, int strengh, int coinBalance) {
        super(name, hp, strengh, coinBalance);
    }

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
                this.npcAttacks(enemy, 0.9);
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
