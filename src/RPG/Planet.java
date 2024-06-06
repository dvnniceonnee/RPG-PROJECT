package RPG;

import RPG.Entities.NPC;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Planet {
    private String name;
    private ArrayList<String> trapDesigns;
    private ArrayList<String> treasureDesigns;
    private ArrayList<String> planetDesigns;
    private ArrayList<String> enemyDesigns;
    private ArrayList<String> enemyNames;
    private ArrayList<String> friendMessages;
    private ArrayList<String> friendDesigns;
    private ArrayList<String> planetNames;
    private ArrayList<String> planetMessages;
    private String spaceShipDesign,gameOverDesign, gameEntryMessage, thanosDesign, thanosFightMessage, thanosEndFightMessage, deathDesign, secondEntryMessage;

    public Planet() throws FileNotFoundException {
        this.name = name;
        this.trapDesigns = new ArrayList<String>();
        this.treasureDesigns = new ArrayList<String>();
        this.planetDesigns = new ArrayList<>();
        this.enemyDesigns = new ArrayList<>();
        this.enemyNames = new ArrayList<>();
        this.friendMessages = new ArrayList<>();
        this.friendDesigns = new ArrayList<String>();
        this.planetNames = new ArrayList<String>();
        this.planetMessages = new ArrayList<String>();
        loadContentFiles();
    }

    public String getName() {
        return name;
    }

    public String getGameOverDesign() {
        return gameOverDesign;
    }

    public String getSecondEntryMessage() {
        return secondEntryMessage;
    }

    public String getRandomPlanetDesign() {
        Random random = new Random();
        return this.planetDesigns.get(random.nextInt(planetDesigns.size()));
    }

    public String getThanosFightMessage() {
        return thanosFightMessage;
    }

    public void setRandomName() {
        Random random = new Random();
        this.name = this.planetNames.get(random.nextInt(planetNames.size()));
        this.planetNames.remove(name);
    }

    public String getRandomMessage() {
        Random random = new Random();
        return this.planetMessages.get(random.nextInt(this.planetMessages.size()));
    }

    public String getSpaceShipDesign() {
        return spaceShipDesign;
    }

    public String getGameEntryMessage() {
        return gameEntryMessage;
    }

    public String getThanosDesign() {
        return thanosDesign;
    }

    public String getThanosEndFightMessage() {
        return thanosEndFightMessage;
    }

    public String getDeathDesign() {
        return deathDesign;
    }

    /**
     * Method that returns a random enemyDesign from the ArrayList of enemyDesigns
     *
     * @return String
     */
    public String getRandomEnemyDesign() {
        Random random = new Random();
        return enemyDesigns.get(random.nextInt(enemyDesigns.size()));
    }

    /**
     * Method that returns a random NPC which characteristics depends on the level of the Hero
     *
     * @param levelHero int representing the level of the Hero playing
     * @return NPC object
     */
    public NPC getRandomNPC(int levelHero) {
        Random random = new Random();
        int randomNpcHealth = random.nextInt(70, 100) + levelHero * 15;
        int randomNpcStrength = random.nextInt(10, 20) + levelHero * 4;
        int randomNpcCoins = random.nextInt(5, 15) + levelHero * 5;
        String randomNpcName = this.enemyNames.get(random.nextInt(enemyNames.size()));
        return new NPC(randomNpcName, randomNpcHealth, randomNpcStrength, randomNpcCoins);
    }

    /**
     * Method that returns a Array with a random Friend Design aswell as a random message
     *
     * @return Array with 2 positions <br> [0] - friend design <br> [1] - friend message
     */
    public String[] getRandomFriendDesignAndMessage() {
        Random random = new Random();
        return new String[]{friendDesigns.get(random.nextInt(friendDesigns.size())), friendMessages.get(random.nextInt(friendMessages.size()))};
    }

    /**
     * Method that returns a random String from the list "treasureDesigns"
     *
     * @return String
     */
    public String getRandomTreasureDesign() {
        Random random = new Random();
        return treasureDesigns.get(random.nextInt(treasureDesigns.size()));
    }

    /**
     * Method that randomly returns a trap design from the ArrayList of trapDesigns
     *
     * @return String
     */
    public String getRandomTrapDesign() {
        Random random = new Random();
        return this.trapDesigns.get(random.nextInt(this.trapDesigns.size()));
    }

    /**
     * Method that loads all the required designs and content to the corresponded arrayLists
     *
     * @throws FileNotFoundException
     */
    private void loadContentFiles() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("files/Content.txt"));
        String test = "";
        while (scanner.hasNextLine()) {
            test += scanner.nextLine() + "\n";
        }
        String[] designs = test.split("0000");
        for (String item : designs) {
            String[] contentItem = item.split("1111");
            if (contentItem[0].equals("Trap")) {
                trapDesigns.add(contentItem[1]);
            } else if (contentItem[0].equals("Treasure")) {
                treasureDesigns.add(contentItem[1]);
            } else if (contentItem[0].equals("Enemy")) {
                enemyDesigns.add(contentItem[1]);
            } else if (contentItem[0].equals("Planet")) {
                planetDesigns.add(contentItem[1]);
            } else if (contentItem[0].equals("PlanetName")) {
                String[] planetNamesStrings = contentItem[1].replace("\n", "").split(";");
                Collections.addAll(planetNames, planetNamesStrings);
            } else if (contentItem[0].equals("PlanetMessage")) {
                planetMessages.add(contentItem[1]);
            } else if (contentItem[0].equals("FriendMessage")) {
                friendMessages.add(contentItem[1]);
            } else if (contentItem[0].equals("Friend")) {
                friendDesigns.add(contentItem[1]);
            } else if (contentItem[0].equals("EnemyName")) {
                String[] enemyNamesStrings = contentItem[1].replace("\n", "").split(";");
                Collections.addAll(enemyNames, enemyNamesStrings);
            } else if (contentItem[0].equals("SpaceShip")) {
                spaceShipDesign = contentItem[1];
            } else if (contentItem[0].equals("GameOver")) {
                gameOverDesign = contentItem[1];
            } else if ((contentItem[0].equals("EntryMessage"))) {
                gameEntryMessage = contentItem[1];
            } else if ((contentItem[0].equals("ThanosImage"))) {
                thanosDesign = contentItem[1];
            } else if ((contentItem[0].equals("ThanosFightMessage"))) {
                thanosFightMessage = contentItem[1];
            } else if ((contentItem[0].equals("Death"))){
                deathDesign = contentItem[1];
            } else if ((contentItem[0].equals("ThanosEndFightMessage"))){
                thanosEndFightMessage = contentItem[1];
            } else if ((contentItem[0].equals("SecondEntryMessage"))){
                secondEntryMessage = contentItem[1];
            }
        }
    }

}
