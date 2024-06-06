package RPG;

import RPG.HeroItems.Consumable;
import RPG.Entities.Hero;
import RPG.Enums.HeroTypes;
import RPG.HeroItems.Consumables.CombatConsumable;
import RPG.HeroItems.Consumables.PotionConsumable;
import RPG.HeroItems.Weapon;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Seller {
    private ArrayList<HeroItem> itemsSellList;
    private String sellerDesign;
    private ArrayList<String> sellerNames;

    public Seller() throws FileNotFoundException {
        this.itemsSellList = new ArrayList<HeroItem>();
        this.sellerNames = new ArrayList<String>();
        loadSellerContent();
    }

    /**
     * Method that will load all items that the game has, as Objects to the corresponded ArrayLists
     *
     * @throws FileNotFoundException
     */
    private void loadSellerContent() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("files/Items/Items.csv"));
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            String[] newLine = scanner.nextLine().split(";");
            HeroItem newItem = null;
            if(newLine[0].equals("Weapon")){
                newItem = new Weapon(newLine[1], Integer.parseInt(newLine[2]), Integer.parseInt(newLine[3]),Integer.parseInt(newLine[4]));
                String[] listWeaponTypeHeros = newLine[5].split(",");
                for(String item: listWeaponTypeHeros){
                    newItem.allowHeros.add(HeroTypes.valueOf(item));
                }
            }else if(newLine[0].equals("PotionConsumable")){
                newItem = new PotionConsumable(newLine[1], Integer.parseInt(newLine[2]), Integer.parseInt(newLine[3]), Integer.parseInt(newLine[4]));
                newItem.allowHeros.addAll(Arrays.asList(HeroTypes.values()));
                this.addItemToSeller(newItem);
            }else if(newLine[0].equals("CombatConsumable")){
                newItem = new CombatConsumable(newLine[1], Integer.parseInt(newLine[2]), Integer.parseInt(newLine[3]));
                newItem.allowHeros.addAll(Arrays.asList(HeroTypes.values()));
                this.addItemToSeller(newItem);
            }
            this.addItemToSeller(newItem);
        }
        scanner = new Scanner(new File("files/Content.txt"));
        String test = "";
        while (scanner.hasNextLine()) {
            test += scanner.nextLine() + "\n";
        }
        String[] designs = test.split("0000");
        for (String item : designs) {
            String[] contentItem = item.split("1111");
            if (contentItem[0].equals("Seller")) {
                sellerDesign = contentItem[1];
            }else if(contentItem[0].equals("SellerName")){
                String[] sellerNamesString = contentItem[1].replace("\n", "").split(";");
                Collections.addAll(sellerNames, sellerNamesString);
            }
        }
    }

    /**
     * Method to add a new Item to the store ("Seller")
     * @param newItem HeroItem Object
     */
    public void addItemToSeller(HeroItem newItem){
        if(newItem != null)
            itemsSellList.add(newItem);
    }

    public String getRandomSellerName(){
        Random random = new Random();
        return this.sellerNames.get(random.nextInt(sellerNames.size()));
    }

    /**
     * Method to return a random Item on Arraylist itemsSellList
     * @param heroType HeroType enum representing the type of hero
     * @return HeroItem Object
     */
    public HeroItem getRandomItem(HeroTypes heroType){
        Random random = new Random();
        int randomType = random.nextInt(1,4);
        if(randomType == 1){
            return getRandomPotion();
        }else if(randomType == 2){
            return getRandomCombatPotion();
        }else if(randomType == 3){
            return getRandomWeapon(heroType);
        }
        return null;
    }

    /**
     * Method that returns a Random Combat Comsumable from the Items list
     * @return Combat Consumable Object
     */
    public CombatConsumable getRandomCombatPotion(){
        Random random = new Random();
        ArrayList<CombatConsumable> listCombatePotions = getCombatPotions();
        return listCombatePotions.get(random.nextInt(listCombatePotions.size()));
    }

    /**
     * Method that returns a random Potion from the list of potions
     * @return Potion Consumable object
     */
    public PotionConsumable getRandomPotion(){
        Random random = new Random();
        ArrayList<PotionConsumable> listPotions = getPotionsList();
        return listPotions.get(random.nextInt(listPotions.size()));
    }

    /**
     * Method that returns a random Weapon from the list of weapons that are allowed to the HeroType
     * @param heroType HeroType enum
     * @return Weapon Object
     */
    public Weapon getRandomWeapon(HeroTypes heroType){
        Random random = new Random();
        ArrayList<Weapon> listWeapons = getWeaponList(heroType);
        return listWeapons.get(random.nextInt(listWeapons.size()));
    }

    /**
     * Method that returns all combat potions from the list of items
     * @return ArrayList with the combat potions
     */
    public ArrayList<CombatConsumable> getCombatPotions(){
        ArrayList<CombatConsumable> potionList = new ArrayList<CombatConsumable>();
        for(HeroItem item: itemsSellList){
            if(item instanceof CombatConsumable){
                potionList.add((CombatConsumable) item);
            }
        }
        return potionList;
    }

    /**
     * Method that returns all potions from the list of items
     * @return ArrayList with the potions
     */
    public ArrayList<PotionConsumable> getPotionsList(){
        ArrayList<PotionConsumable> potionList = new ArrayList<PotionConsumable>();
        for(HeroItem item: itemsSellList){
            if(item instanceof PotionConsumable){
                potionList.add((PotionConsumable) item);
            }
        }
        return potionList;
    }

    /**
     * Method that returns a ArrayList of Weapon Object Type
     * @param heroType HeroType Enum
     * @return ArrayList
     */
    public ArrayList<Weapon> getWeaponList(HeroTypes heroType){
        ArrayList<Weapon> weaponsForHero = new ArrayList<Weapon>();
        for(HeroItem item: itemsSellList){
            if(item instanceof Weapon){
                if(item.allowHeros.contains(heroType)){
                    weaponsForHero.add((Weapon) item);
                }
            }
        }
        return weaponsForHero;
    }

    /**
     * Method that returns all the items allowed to the hero to use
     * @param heroType
     * @return ArrayList with the items
     */
    public ArrayList<HeroItem> getListItemsForHero(HeroTypes heroType){
        ArrayList<HeroItem> itemsForSpecificHero = new ArrayList<HeroItem>();
        for(HeroItem item: itemsSellList){
            if(item.allowHeros.contains(heroType)){
                itemsForSpecificHero.add(item);
            }
        }
        return itemsForSpecificHero;
    }

    /**
     * Method to print aesthetically better 10 items from the seller
     * @param textToPrint String with the 10 items, it has to have "/n" to split the items
     */
    public void printTenItems(String textToPrint){
        Scanner textScanner = new Scanner(textToPrint);
        int biggestText = 0;
        while (textScanner.hasNextLine()){
            String firstPartOfLine = textScanner.nextLine().split("\\|")[0];
            if(firstPartOfLine.length() > biggestText){
                biggestText = firstPartOfLine.length();
            }
        }
        textScanner.close();
        textScanner = new Scanner(textToPrint);
        while (textScanner.hasNextLine()){
            String line = textScanner.nextLine();
            String firstPart = line.split("\\|")[0];
            String secondPart = line.split(firstPart)[1];
            String stringToPrint;
            if(firstPart.contains("Weapon"))
                 stringToPrint = firstPart + " ";
            else
                stringToPrint = firstPart;
            if(firstPart.length() < biggestText){
                for(int i = 0; i < (biggestText - firstPart.length()); i++){
                    stringToPrint += " ";
                }
                stringToPrint += secondPart;
            }
            else {
                stringToPrint = firstPart + secondPart;
            }
            Main.printStringWithSpaces(stringToPrint);
        }

    }

    /**
     * Method that shows a menu with 10 random items to the Hero buy
     * @param hero Object Hero
     */
    public void sellerStoreRandom10items(Hero hero){
        Random random = new Random();
        ArrayList<HeroItem> tenItems = getListItemsForHero(HeroTypes.valueOf(hero.getClass().getSimpleName()));
        Collections.shuffle(tenItems);
        tenItems.subList(10,tenItems.size()).clear();
        Main.printStringWithSpaces(sellerDesign);
        Main.printStringWithSpacesSlow("Encontraste o vendedor " + this.getRandomSellerName() + "...\n" +
                "Aqui podes comprar vários items tais como ⚗\uFE0F Poções, \uD83D\uDCA3 Consumíveis de combate ou \uD83D\uDDE1\uFE0F Armas...\n" +
                "Boas compras " + hero.getName() + "...");
        int optionMenuSeller = -1;
        do{
            Main.printHeroBar(hero);
            String tenItemsToPrint = "";
            for(int i = 0; i < tenItems.size(); i++){
                tenItemsToPrint += (i+1) + " " + tenItems.get(i).getDetails(true) + "\n";
            }
            this.printTenItems(tenItemsToPrint);
            Main.printStringWithSpaces("Escolha o item que deseja comprar (0 para sair) :");
            optionMenuSeller = Main.chooseInt(0,tenItems.size() + 1);
            if(optionMenuSeller != 0){
                HeroItem itemToBuy = tenItems.get(optionMenuSeller - 1);
                if(sellItem(hero, itemToBuy)){
                    Main.printStringWithSpaces("Adquiriste o item " + itemToBuy.getDetails(false));
                    tenItems.remove(itemToBuy);
                }
                Main.printStringWithSpaces("Queres continuar a comprar(y/n)");
                if(Main.chooseYorN().equalsIgnoreCase("n"))
                    optionMenuSeller = 0;
            }
        }while (optionMenuSeller != 0);
    }

    /**
     * Method that will add or set a item to the inventory of the Hero
     * @param hero Object Hero
     * @param itemToSell Object HeroItem
     * @return Boolean if the item was accepted by the Hero or then if it was added to the inventory of the Hero
     */
    public boolean sellItem(Hero hero, HeroItem itemToSell){
        if(itemToSell.coinPrice > hero.getCoinBalance()){
            Main.printStringWithSpaces("\uD83D\uDFE1\uD83D\uDFE1\uD83D\uDFE1 insuficientes para efetuar a compra!");
            return false;
        }
        else {
            hero.setCoinBalance(hero.getCoinBalance() - itemToSell.coinPrice);
            if(itemToSell instanceof Weapon){
                if(hero.setMainWeapon((Weapon) itemToSell)){
                    return true;
                }
                else {
                    return false;
                }
            }else if(itemToSell instanceof Consumable){
                hero.addConsumableToInventory((Consumable) itemToSell);
            }
            return true;
        }
    }
}
