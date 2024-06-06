package RPG.Entities;

import RPG.Entity;
import RPG.HeroItem;
import RPG.HeroItems.Consumables.CombatConsumable;
import RPG.HeroItems.Consumable;
import RPG.HeroItems.Consumables.InfinityStoneConsumable;
import RPG.HeroItems.Consumables.PotionConsumable;
import RPG.Main;
import RPG.HeroItems.Weapon;
import jdk.jshell.spi.ExecutionControlProvider;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Hero extends Entity {

    private int level;
    private int coinBalance;
    private Weapon mainWeapon;
    protected ArrayList<Consumable> inventory;

    /**
     * Constructor of the Class hero
     *
     * @param name        String
     * @param hp          int
     * @param strengh     int
     * @param coinBalance int
     */
    public Hero(String name, int hp, int strengh, int coinBalance) {
        super(name, hp, strengh);
        this.level = 1;
        this.coinBalance = coinBalance;
        inventory = new ArrayList<Consumable>();
        mainWeapon = new Weapon("Navalha", 0, 2, 4);
    }

    /**
     * Method to get the GoldBalance of the Hero Class
     *
     * @return int goldBalance
     */
    public int getCoinBalance() {
        return coinBalance;
    }

    public int getLevel() {
        return level;
    }

    /**
     * Method to get the MainWeapon
     *
     * @return Weapon
     */
    public Weapon getMainWeapon() {
        return mainWeapon;
    }

    /**
     * Method that returns a list of combat consumables of the invetory
     *
     * @return ArrayList
     */
    public ArrayList<CombatConsumable> getCombatConsumableInventory() {
        ArrayList<CombatConsumable> listCombateComsumable = new ArrayList<CombatConsumable>();
        for (Consumable item : inventory) {
            if (item instanceof CombatConsumable) {
                listCombateComsumable.add((CombatConsumable) item);
            }
        }
        return listCombateComsumable;
    }

    /**
     * Method that returns a list of Consumable Potions of the inventory
     *
     * @return ArrayList
     */
    public ArrayList<PotionConsumable> getPotionInventory() {
        ArrayList<PotionConsumable> potionList = new ArrayList<PotionConsumable>();
        for (Consumable item : inventory) {
            if (item instanceof PotionConsumable) {
                potionList.add((PotionConsumable) item);
            }
        }
        return potionList;
    }

    public int getNumberOfInfinityStones() {
        int totalInfinityStones = 0;
        for (Consumable item : inventory) {
            if (item instanceof InfinityStoneConsumable) {
                totalInfinityStones++;
            }
        }
        return totalInfinityStones;
    }

    public int getTotalStrenghIncreaseOfInfinityStones() {
        int totalDamage = 0;
        for (Consumable item : inventory) {
            if (item instanceof InfinityStoneConsumable) {
                totalDamage += ((InfinityStoneConsumable) item).getStrenghIncrease();
            }
        }
        return totalDamage;
    }

    public int getNormalAttackDamage() {
        return this.strengh + this.mainWeapon.getDamage();
    }

    public int getSpecialAttackDamage() {
        return this.strengh + this.mainWeapon.getSpecialDamage();
    }

    /**
     * Method to set the MainWeapon of the hero Class if the user chooses "y" to replace the old weapon for the new
     *
     * @param newWeapon Weapon Object
     * @return Boolean if the Hero chose to replace the weapon
     */
    public boolean setMainWeapon(Weapon newWeapon) {
        Main.printStringWithSpaces("\n\uD83D\uDDE1 Arma equipada ->" + this.mainWeapon.getDetails(false) + "\n");
        Main.printStringWithSpacesSlow("Desejas substituir a tua arma equipada atual pela nova(y/n) ? ");
        if(Main.chooseYorN().equalsIgnoreCase("y")){
            this.mainWeapon = newWeapon;
            return true;
        }
        return false;
    }

    /**
     * Method to set the GoldBalance of the Hero Class
     *
     * @param coinBalance int goldBalance
     */
    public void setCoinBalance(int coinBalance) {
        this.coinBalance = coinBalance;
    }

    /**
     * Method that increase a level and in consequence increases the HP by 10 points aswell as the MaxHp
     */
    public void levelUp() {
        this.level++;
        this.setMaxHp(this.getMaxHp() + 10);
        this.setHp(this.getHp() + 10);
        Main.printStringWithSpacesSlow("Boa " + this.name + " ... Subiste para o nivel " + this.level + "\n" +
                "A tua ❤\uFE0F e a tua ❤\uFE0F max subiram 10 pontos...\n");
    }

    /**
     * Method to add a consumable to the inventory arrayList
     *
     * @param consumable Consumable object
     */
    public void addConsumableToInventory(Consumable consumable) {
        this.inventory.add(consumable);
    }

    /**
     * Method that increases Hp to the hero (if hp is already set to the maxHp it will increase 0 and will return 0)
     * @param hpIncrease Int with the total amount of Hp that will be increased
     * @return int with the total Hp increased
     */
    public int increaseHp(int hpIncrease){
        if ((hpIncrease + this.getHp()) > this.getMaxHp()) {
            hpIncrease = this.getMaxHp() - getHp();
            this.setHp(this.getMaxHp());
        } else {
            this.setHp(this.getHp() + hpIncrease);
        }
        return hpIncrease;
    }

    /**
     * Method that asks a user which potion he wants to use (if a potion is used returns true otherwise is false)
     */
    public void usePotionConsumable() {
        ArrayList<PotionConsumable> potionList = getPotionInventory();
        if (!potionList.isEmpty()) {
            Main.printStringWithSpaces("0. Sair\n");
            for (int i = 0; i < potionList.size(); i++) {
                Main.printStringWithSpaces((i + 1) + potionList.get(i).getDetails(false));
            }
            Main.printStringWithSpaces("Escolha o consumivel (0 para Sair) : ");
            int potionChoosen = Main.chooseInt(0, potionList.size() + 1);
            if (potionChoosen != 0) {
                PotionConsumable potion = potionList.get(potionChoosen - 1);
                int totalHpGain = increaseHp(potion.getHealthIncrease());
                Main.printStringWithSpaces("Usaste o item " + potion.getDetails(false) +
                        "\nA tua ❤\uFE0F foi aumentada em " + totalHpGain + " e a tua \uD83D\uDCAA foi aumentada em " + potion.getStrenghIncrease());
                this.setStrengh(this.getStrengh() + potion.getStrenghIncrease());
                this.inventory.remove(potion);

            }
        } else {
            Main.printStringWithSpaces("❗❗ 0 Poções no Inventário ❗❗");
        }
    }

    /**
     * Method that asks the user what combat potion he wants to use (option to leave menu also exists)
     *
     * @return int with the total damage of the item (0 if does not exist any combat comsumables or the user leaves the menu)
     */
    public int useCombatConsumable() {
        ArrayList<CombatConsumable> listCombateConsumables = getCombatConsumableInventory();
        if (!listCombateConsumables.isEmpty()) {
            for (int i = 0; i < listCombateConsumables.size(); i++) {
                Main.printStringWithSpaces((i + 1) + ". " + listCombateConsumables.get(i).getDetails(false));
            }
            Main.printStringWithSpacesSlow("Escolha o consumivel (0 para Sair) : ");
            int combatPotionChoosen = Main.chooseInt(0, listCombateConsumables.size() + 1);
            if (combatPotionChoosen != 0) {
                CombatConsumable combatPotion = listCombateConsumables.get(combatPotionChoosen - 1);
                inventory.remove(combatPotion);
                Main.printStringWithSpacesSlow("Escolheste o consumivel " + combatPotion.getDetails(false));
                return combatPotion.getImmediateDamage();
            }
        } else {
            Main.printStringWithSpaces("❗❗ 0 consumiveis de combate no inventário ❗❗");
            return 0;
        }
        return 0;
    }

    /**
     * Method that will return a int from 1-4 (menu options that will show up to the user which he can choose depending on the available options)
     *
     * @param specialAtackUsed boolean to check if the user already used a special attack
     * @return int
     */
    public int optionsUserMenuAttack(boolean specialAtackUsed) {
        String[] menuOptions = {"Usar um consumível de combate", "Usar um ataque normal", "Usar um Ataque Especial"};
        int counterOptions = 1;
        int numberOptions = 0;
        if (!specialAtackUsed) {
            numberOptions = 3;
        } else {
            numberOptions = 2;
        }
        for (int i = 0; i < numberOptions; i++) {
            if (i == 0) {
                Main.printStringWithSpaces((counterOptions++) + ". " + menuOptions[i] + " \uD83D\uDDE1\uFE0F");
            } else if (i == 1) {
                Main.printStringWithSpaces((counterOptions++) + ". " + menuOptions[i] + "(⚔\uFE0F " + this.getNormalAttackDamage() + ")");
            } else {
                Main.printStringWithSpaces((counterOptions++) + ". " + menuOptions[i] + "(⚔\uFE0F " + this.getSpecialAttackDamage() + ")");
            }
        }
        return Main.chooseInt(1, numberOptions + 1);
    }

    /**
     * Method that will show a attack menu to the user and depending on the previous choices some choices might not appear
     *
     * @param enemy             Object NPC that will fight against the Hero
     * @param specialAttackUsed boolean to check if the user already used a special attack
     * @return boolean that tell if the user used or can still use a special attack
     */
    public boolean attackMenu(NPC enemy, boolean specialAttackUsed) {
        boolean atackDone = false;
        int damage = 0;
        while (!atackDone) {
            Main.printHeroBar(this);
            enemy.printNpcBar();
            int optionMenuAtack = this.optionsUserMenuAttack(specialAttackUsed);
            switch (optionMenuAtack) {
                case 1: {
                    int damageCombatConsumable = useCombatConsumable();
                    if (damageCombatConsumable > 0) {
                        damage = damageCombatConsumable;
                        atackDone = true;
                    }
                    break;
                }
                case 2: {
                    damage = (this.getStrengh() + this.getMainWeapon().getDamage());
                    atackDone = true;
                    break;
                }
                case 3: {
                    damage = (this.getStrengh() + this.getMainWeapon().getSpecialDamage());
                    atackDone = true;
                    specialAttackUsed = true;
                    break;
                }
            }
        }
        Main.playAudio("files/sound/AttackFight.wav", false);
        if(damage > enemy.getHp()){
            damage = enemy.getHp();
        }
        enemy.setHp(enemy.getHp() - damage);
        Main.printStringWithSpacesSlow("Atacaste o inimigo " + enemy.getName() + " com o teu ataque especial...\n" +
                "Tiraste-lhe " + damage + " de ❤\uFE0F");
        return specialAttackUsed;
    }

    /**
     * Method that makes a NPC attack the Hero, however if the hero has a item "Extra Life" he can revive and continue the combat
     * @param enemy NPC object
     * @param strengthMultiplier double for the strenght multiplier of the NPC
     */
    public void npcAttacks(NPC enemy, double strengthMultiplier) {
        if (((int) (enemy.getStrengh() * strengthMultiplier) > this.hp)) {
            boolean extraLifeExists = false;
            PotionConsumable extraLife = null;
            for (HeroItem item : inventory) {
                if (item.getName().equals("Extra Life")) {
                    extraLifeExists = true;
                    extraLife = (PotionConsumable) item;
                }
            }
            Main.printStringWithSpacesSlow("O \uD83D\uDC7E" + enemy.getName() + " atacou-te e tirou-te " + this.getHp() + " de ❤\uFE0F");
            if (extraLifeExists) {
                Main.printStringWithSpacesSlow("A tua ❤\uFE0F está a 0 contudo a esperança é a ultima a morrer... No teu inventário possuis uma vida extra \uD83D\uDC9A" +
                        "\nQueres usar uma vida extra?");
                if (Main.chooseYorN().equalsIgnoreCase("y")) {
                    this.hp = maxHp;
                    this.inventory.remove(extraLife);
                }
            } else {
                this.setHp(hp - (int) (enemy.getStrengh() * strengthMultiplier));
            }
        } else {
            this.setHp(hp - (int) (enemy.getStrengh() * strengthMultiplier));
            Main.printStringWithSpacesSlow("O \uD83D\uDC7E" + enemy.getName() + " atacou-te e tirou-te " + (int) (enemy.getStrengh() * strengthMultiplier) + " de ❤\uFE0F");
        }
    }

    /**
     * Method to decrease the hp of the Object Hero
     *
     * @param damage int with the damage that will be done to the hero
     */
    public void takeDamageOnHp(int damage) {
        this.hp -= damage;
    }

    /**
     * Method that prints everything the Hero has on the inventory inclusive the Main weapon
     */
    public void printInventory() {
        Main.printStringWithSpaces(this.mainWeapon.getDetails(false));
        Main.printStringWithSpaces("\nInventory Items");
        if (this.inventory.isEmpty()) {
            Main.printStringWithSpaces("0 Items no inventário");
        } else {
            for (Consumable item : inventory) {
                Main.printStringWithSpaces(item.getDetails(false));
            }
        }
    }

    /**
     * Method that prints characteristics of the Hero
     */
    public void printHero() {
        Main.printStringWithSpaces(detailsEntity() +
                " | ❤\uFE0F max : " + this.maxHp +
                " | Tipo de Heroi : " + this.getClass().getSimpleName());
    }

    public abstract boolean attack(NPC enemy);
}
