package RPG;

import RPG.Entities.Hero;
import RPG.Entities.HeroTypes.*;
import RPG.Entities.NPC;
import RPG.Enums.Difficulties;
import RPG.Enums.HeroTypes;
import RPG.HeroItems.Consumable;
import RPG.HeroItems.Consumables.InfinityStoneConsumable;
import RPG.HeroItems.Weapon;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import static RPG.Main.*;

public class Game {
    private Difficulties gameDifficulty;
    private Planet planet;
    private Seller seller;

    public Game() throws FileNotFoundException {
        seller = new Seller();
        planet = new Planet();
    }

    /**
     * Method that makes the user choose the Strengh and the Health of his Hero depending on the difficulty of the game
     *
     * @return Array of ints <br>[0] - Choosen Strengh <br> [1] - Choosen Health <br> [2] - Coins Available for the Hero
     */
    private int[] userChooseStrenghAndHealthAndCoins() {
        Scanner input = new Scanner(System.in);
        int[] chossenStrenghAndHealth = {0, 0, 0};
        switch (gameDifficulty) {
            case Hard -> {
                Main.printStringWithSpacesSlow("Existem 220 pontos de distribuição para a vida e força da personagem" +
                        "\nCada valor de força equivale a 5 pontos e o restante será o hp inicial da personagem" +
                        "\nInsira a quantidade de pontos que seja atribuir à força da personagem (Max 140 pontos) : ");
                chossenStrenghAndHealth[0] = returnIntMultipleofFive(0, 141);
                chossenStrenghAndHealth[1] = 220 - (chossenStrenghAndHealth[0] * 5);
                chossenStrenghAndHealth[2] = 15;
                break;
            }
            case Medium -> {
                Main.printStringWithSpacesSlow("Existem 260 pontos de distribuição para a vida e força da personagem" +
                        "\nCada valor de força equivale a 5 pontos e o restante será o hp inicial da personagem" +
                        "\nInsira a quantidade de pontos que seja atribuir à força da personagem (Max 180 pontos) : ");
                chossenStrenghAndHealth[0] = returnIntMultipleofFive(0, 181);
                chossenStrenghAndHealth[1] = 260 - (chossenStrenghAndHealth[0] * 5);
                chossenStrenghAndHealth[2] = 18;
                break;
            }
            case Easy -> {
                Main.printStringWithSpacesSlow("Existem 300 pontos de distribuição para a vida e força da personagem" +
                        "\nCada valor de força equivale a 5 pontos e o restante será o hp inicial da personagem" +
                        "\nInsira a quantidade de pontos que seja atribuir à força da personagem (Max 220 pontos) : ");
                chossenStrenghAndHealth[0] = returnIntMultipleofFive(0, 221);
                chossenStrenghAndHealth[1] = 300 - (chossenStrenghAndHealth[0] * 5);
                chossenStrenghAndHealth[2] = 20;
                break;
            }
        }
        return chossenStrenghAndHealth;
    }

    /**
     * Method to create a new Hero, The user will have to choose many things like the type of Hero he wants to play, the game difficultie and attributes of the Hero
     *
     * @return Object of the class Hero
     */
    public Hero createCaracter() {
        Main.printStringWithSpacesSlow("A proxima etapa é criar um heroi");
        Scanner input = new Scanner(System.in);
        printStringWithSpaces("*** Tipo de personagens ***");
        String[] heroTypesIcons = {"\uD83E\uDDD9\u200D♂\uFE0F", "\uD83D\uDD31", "\uD83E\uDDD1", "\uD83D\uDC31", "\uD83D\uDC7D"};
        String heroTypesString = "";
        for (int i = 0; i < HeroTypes.values().length; i++) {
            heroTypesString += (i + 1) + ". " + heroTypesIcons[i] + " " + HeroTypes.values()[i] + "\n";
        }
        Main.printStringWithSpaces(heroTypesString);
        Main.printStringWithSpacesSlow("Escolha o tipo de personagem desejada : ");
        HeroTypes optionTypeHero = HeroTypes.values()[chooseInt(1, HeroTypes.values().length + 1) - 1];
        for (int i = 0; i < Difficulties.values().length; i++) {
            Main.printStringWithSpaces((i + 1) + ". \uD83E\uDDE0 " + Difficulties.values()[i] + "\t");
        }
        Main.printStringWithSpacesSlow("Escolha a dificultade desejada : ");
        gameDifficulty = Difficulties.values()[chooseInt(1, Difficulties.values().length + 1) - 1];
        Main.printStringWithSpacesSlow("Insira o nickname para a sua personagem : ");
        String name = input.next() + input.nextLine();
        int[] capabilities = userChooseStrenghAndHealthAndCoins();
        int heroStrengh = capabilities[0];
        int heroHealth = capabilities[1];
        int coinBalance = capabilities[2];
        printStringWithSpacesSlow("Estas são as caracteristicas que escolheste para o teu heroi");
        printStringWithSpaces("\nNome : " + name +
                "\nTipo de Heroi : " + optionTypeHero +
                "\n❤\uFE0F Inicial : " + heroHealth +
                "\n\uD83D\uDCAA Inicial : " + heroStrengh);
        pressEnterToContinue();
        switch (optionTypeHero) {
            case Human -> {
                return new Human(name, heroHealth, heroStrengh, coinBalance);
            }
            case Mutant -> {
                return new Mutant(name, heroHealth, heroStrengh, coinBalance);
            }
            case SemiGod -> {
                return new SemiGod(name, heroHealth, heroStrengh, coinBalance);
            }
            case Wizard -> {
                return new Wizard(name, heroHealth, heroStrengh, coinBalance);
            }
            case SuperAnimal -> {
                return new SuperAnimal(name, heroHealth, heroStrengh, coinBalance);
            }
        }
        return null;
    }

    /**
     * Method that will ask the user which option he wants depending on the state of the boolean 'newPlanet' and the level of the Hero "levelHero"
     *
     * @param newPlanet boolean
     * @return int with the option chose
     */
    public int menuGame(boolean newPlanet, Hero hero) {
        String menuOptions = "";
        if (newPlanet) {
            menuOptions = "1. Procurar as pedras infinitas" +
                    "\n2. Usar uma poção" +
                    "\n3. Visualizar o Inventário" +
                    "\n4. Visualizar o heroi";
            printStringWithSpaces(menuOptions);
            return chooseInt(1, 5) + 1;
        } else {
            if (hero.getLevel() > 5) {
                menuOptions = "1. Viajar para um novo planeta" +
                        "\n2. Continuar a procurar as pedras infinitas" +
                        "\n3. Usar uma poção" +
                        "\n4. Visualizar o Inventário" +
                        "\n5. Visualizar o heroi" +
                        "\n6. Enfrentar Thanos";
                printStringWithSpaces(menuOptions);
                return chooseInt(1, 7);
            } else {
                menuOptions = "1. Viajar para um novo planeta" +
                        "\n2. Continuar a procurar as pedras infinitas neste planeta" +
                        "\n3. Usar uma poção" +
                        "\n4. Visualizar o Inventário" +
                        "\n5. Visualizar o heroi";
                printStringWithSpaces(menuOptions);
                return chooseInt(1, 6);
            }
        }
    }

    /**
     * First time menu that will appear
     *
     * @return
     */
    public int firstMenuGame() {
        String menuOptions = "1. Viajar para um planeta" +
                "\n2. Usar o vendedor (One-time Use)";
        printStringWithSpaces(menuOptions);
        return chooseInt(1, 3);
    }


    /**
     * Method that prints a random set of strings of the file "Content.txt" and returns a random Int that represents the damage that will be inflected to the hero
     *
     * @param level int of the level of the Hero
     * @return int with the damage
     * @throws FileNotFoundException
     */
    private int getRandomTrap(int level) {
        Random random = new Random();
        Main.printStringWithSpaces(planet.getRandomTrapDesign());
        int damageTrap = random.nextInt(5) + (level * 4);
        printStringWithSpacesSlow("Após uma longa busca encontraste pelo caminho uma armadilha...\n" +
                "O teu heroi tentou escapar mas não escapou ileso... Perdeste " + damageTrap + " de Hp!");
        return damageTrap;
    }

    /**
     * Method to randomly get a treasure( It can be a weapon, a consumable item or coins)
     *
     * @param hero Object of the class hero
     */
    private void getRandomTreasure(Hero hero) {
        Random random = new Random();
        Main.printStringWithSpaces(planet.getRandomTreasureDesign());
        int randomTreasure = random.nextInt(20);
        if (randomTreasure < 5) {                         // If random number is less than 5 treasure with be a item (consumable or weapon)
            printStringWithSpacesSlow("Após uma busca intensa no planeta " + planet.getName() + " parece que a tua sorte mudou...\n" +
                    "Encontraste um tesouro com o item seguinte ... \n");
            HeroItem itemGotten = seller.getRandomItem(HeroTypes.valueOf(hero.getClass().getSimpleName()));
            Main.printStringWithSpaces(itemGotten.getDetails(false));
            if (itemGotten instanceof Weapon) {
                hero.setMainWeapon((Weapon) itemGotten);
            } else {
                printStringWithSpacesSlow("Desejas adicionar o item ao teu inventário (y/n) ? ");
                String addToInventory = chooseYorN();
                if (addToInventory.equalsIgnoreCase("y")) {
                    hero.addConsumableToInventory((Consumable) itemGotten);
                }
            }
        } else {                  // If the random number is bigger than 5 will get coins
            int randomNumberOfCoins = random.nextInt(6) + hero.getLevel() * 3;
            printStringWithSpacesSlow("Após uma busca intensa parece que a tua sorte mudou...\n" +
                    "Encontraste um tesouro recheado!!! Ganhaste " + randomNumberOfCoins + " moedas...");
            hero.setCoinBalance(hero.getCoinBalance() + randomNumberOfCoins);
        }
    }

    /**
     * Method void that random will give health or strenght to the Hero(it will randomly show a String containing art and a random message to the user)
     *
     * @param hero Object Hero
     */
    public void getRandomFriend(Hero hero) {
        Random random = new Random();
        String[] friendDesignAndMessage = planet.getRandomFriendDesignAndMessage();
        printStringWithSpaces(friendDesignAndMessage[0]); // print a design
        printStringWithSpacesSlow(friendDesignAndMessage[1]); // prints a message
        int randomNumber = random.nextInt(10);
        if (randomNumber < 5) {
            int randomHealthIncrease = random.nextInt(1, 15) + (hero.getLevel() * 5);
            printStringWithSpaces("A tua vida foi aumentada em " + hero.increaseHp(randomHealthIncrease) + "...");
        } else {
            int randomStrenghIncrease = random.nextInt(1, 4) + (hero.getLevel() * 2);
            printStringWithSpaces("A tua força foi aumentada em " + randomStrenghIncrease + "...");
            hero.setStrengh(hero.getStrengh() + randomStrenghIncrease);
        }
    }

    /**
     * Method that randomly gives a random enemy to fight and makes the fight with it
     *
     * @param hero Object Hero
     */
    public void getRandomEnemy(Hero hero) {
        Main.printStringWithSpaces(planet.getRandomEnemyDesign());
        Main.printStringWithSpacesSlow("Durante a tua jornada no planeta encontraste um inimigo que te impede de avançar...!" +
                "\nAgora para conseguires continuar tens de derrotar o inimigo...");
        NPC newNpc = planet.getRandomNPC(hero.getLevel());
        Main.printStringWithSpaces("\n" + newNpc.detailsEntity() + "\n\n");
        Main.pressEnterToContinue();
        if (hero.attack(newNpc)) {
            Main.printStringWithSpacesSlow("Excelente luta " + hero.getName() +
                    "...\nDerrotaste o \uD83D\uDC7E" + newNpc.getName() + " e recebeste " + newNpc.getCoinBalance() + "\uD83D\uDFE1...");
        } else {
            Main.printStringWithSpacesSlow("Lutaste bem " + hero.getName() + " contudo não foi suficiente para ganhares a luta...");
        }
    }

    /**
     * Method will generate a random NPC with strengh and health dependents on the level of the hero
     *
     * @param hero Hero Object
     */
    public void getRandomInfinitiStone(Hero hero) {
        Main.printStringWithSpacesSlow("Excelente...! Encontraste uma \uD83D\uDC8E pedra infinita...." +
                "\nContudo para a obteres vais ter de derrotar um \uD83D\uDC7E inimigo que a está a guardar...");
        NPC newNpc = planet.getRandomNPC(hero.getLevel());
        Main.printStringWithSpaces(newNpc.detailsEntity());
        Main.pressEnterToContinue();
        if (hero.attack(newNpc) && hero.getNumberOfInfinityStones() < 4) {
            Main.printStringWithSpacesSlow("Fantástico... Conseguiste derrotar o inimigo e conseguiste encontrar uma \uD83D\uDC8E pedra infinita!...");
            InfinityStoneConsumable newInfinityStone = new InfinityStoneConsumable();
            hero.addConsumableToInventory(newInfinityStone);
        } else if (hero.attack(newNpc) && hero.getNumberOfInfinityStones() == 4) {
            Main.printStringWithSpacesSlow("Wow... Após uma luta intensa conseguiste encontrar a ultima \uD83D\uDC8E pedra infinita!..." +
                    "\nDefinitivamente estás pronto para derrotar o Thanos... Boa sorte guerreiro! \uD83D\uDCAA");
            InfinityStoneConsumable newInfinityStone = new InfinityStoneConsumable();
            hero.addConsumableToInventory(newInfinityStone);
        }

    }

    /**
     * Method that will generate a random int from 1 to 6 inclusive <br> 1
     * @param hero Hero Object
     * @param numberOfSearchsForStones
     * @return
     */
    public int randomAction(Hero hero, int numberOfSearchsForStones) {
        Random random = new Random();
        int randomAction;
        if (hero.getLevel() > 1 && hero.getNumberOfInfinityStones() < 5) { //
            if (numberOfSearchsForStones > 4) {
                randomAction = random.nextInt(1, 12); // Increase the odd's to get a seller because the number of play's without a seller is more than 4
                if (randomAction > 6) {
                    randomAction = 5;
                }
            } else {
                randomAction = random.nextInt(1, 7);
            }

        } else {
            if (numberOfSearchsForStones > 4) {
                randomAction = random.nextInt(1, 11); // Increase the odd's to get a seller because the number of play's without a seller is more than 4
                if (randomAction > 5) {
                    randomAction = 5;
                }
            } else {
                randomAction = random.nextInt(1, 6);
            }
        }
        return randomAction;
    }

    /**
     * Method that will randomly do a action when the user chooses to look for the infinite stones
     *
     * @param hero Hero Object
     */
    private int searchForInfinityStones(Hero hero, int numberOfSearchsForStones) {
        int randomAction = randomAction(hero, numberOfSearchsForStones);
        switch (randomAction) {
            case 1: {
                hero.takeDamageOnHp(getRandomTrap(hero.getLevel()));
                pressEnterToContinue();
                break;
            }
            case 2: {
                getRandomTreasure(hero);
                pressEnterToContinue();
                break;
            }
            case 3: {
                getRandomEnemy(hero);
                pressEnterToContinue();
                break;
            }
            case 4: {
                getRandomFriend(hero);
                pressEnterToContinue();
                break;
            }
            case 5: {
                seller.sellerStoreRandom10items(hero);
                numberOfSearchsForStones = -1;
                break;
            }
            case 6: {
                getRandomInfinitiStone(hero);
                pressEnterToContinue();
                break;
            }
        }
        return numberOfSearchsForStones+1;
    }

    /**
     * Method that represents the final game which the Hero will fight againts a NPC (Thanos)
     * @param hero Hero Oject
     * @return Boolean (<br> True if the Hero wins the fight <br> False if loses the fight
     */
    public boolean fightWithThanos(Hero hero) {
        Main.printStringWithSpaces(planet.getThanosDesign());
        Main.printStringWithSpacesSlow(planet.getThanosFightMessage() +
                "\nExistem no teu inventário " + hero.getNumberOfInfinityStones() + " \uD83D\uDC8E pedras infinitas o que te dá um aumento de \uD83D\uDCAA de " + hero.getTotalStrenghIncreaseOfInfinityStones() +
                "\nBoa sorte guerreiro...\n");
        NPC thanos = new NPC("Thanos", 500, 50, 0);
        hero.setStrengh(hero.getStrengh() + hero.getTotalStrenghIncreaseOfInfinityStones());
        Main.pressEnterToContinue();
        return hero.attack(thanos);
    }

    public void defeatThanos(Hero hero) {
        Main.printStringWithSpaces(planet.getSpaceShipDesign());
        Main.printHeroBar(hero);
        Main.printStringWithSpacesSlow("Olá, " + hero.getName() + "...\n" + planet.getGameEntryMessage());
        pressEnterToContinue();
        printStringWithSpacesSlow("\nEstás na tua nave especial pronto para procurar as \uD83D\uDC8E pedras infinitas!" +
                "\nRecomendo que passes por um \uD83D\uDED2 vendedor para realizar alguns upgrades a fim de melhorar o teu \uD83E\uDDB8\u200D♂ Heroi");
        if (firstMenuGame() == 2) {
            seller.sellerStoreRandom10items(hero);
            printStringWithSpacesSlow("Excelente compras " + hero.getName() + "...\n" +
                    "Realizaste as tuas primeiras compras na tua jornada, o teu próximo passo é procurar as \uD83D\uDC8E pedras infinitas... \n");
        }
        printStringWithSpacesSlow(planet.getSecondEntryMessage());
        Main.pressEnterToContinue();
        boolean firstPlayOnPlanet = true;
        boolean firstTimeOnPlanet = true;
        int numberOfSearchsForStones = 0;
        boolean gameWon = false;
        do {
            if (firstTimeOnPlanet) {
                planet.setRandomName();
                Main.printStringWithSpaces(planet.getRandomPlanetDesign());
                Main.printStringWithSpacesSlow(planet.getRandomMessage());
                Main.printStringWithSpacesSlow("Estás situado no planeta 🪐 " + planet.getName() + " ... Boa sorte 🤞🤞🤞");
            } else {
                Main.printStringWithSpacesSlow("Estás situado no planeta 🪐 " + planet.getName() + " ... \n");
            }
            Main.printHeroBar(hero);
            int menuGameOption = menuGame(firstPlayOnPlanet, hero);
            firstTimeOnPlanet = false;
            switch (menuGameOption) {
                case 1: {
                    firstTimeOnPlanet = true;
                    firstPlayOnPlanet = true;
                    break;
                }
                case 2: {
                    numberOfSearchsForStones = searchForInfinityStones(hero, numberOfSearchsForStones);
                    firstPlayOnPlanet = false;
                    break;
                }
                case 3: {
                    hero.usePotionConsumable();
                    pressEnterToContinue();
                    break;
                }
                case 4: {
                    hero.printInventory();
                    pressEnterToContinue();
                    break;
                }
                case 5: {
                    hero.printHero();
                    pressEnterToContinue();
                    break;
                }
                case 6: {
                    if (fightWithThanos(hero)) {
                        gameWon = true;
                        Main.printStringWithSpacesSlow(planet.getThanosEndFightMessage());
                    }
                    break;
                }
            }
        } while (hero.getHp() > 0 && !gameWon);
        if (gameWon) {
            Main.printStringWithSpaces(planet.getGameOverDesign());
        } else {
            Main.printStringWithSpaces(planet.getDeathDesign());
            Main.printStringWithSpaces("Morreste... Perdeste o jogo!");
        }
    }
}
