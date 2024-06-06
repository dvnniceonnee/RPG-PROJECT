package RPG;

import RPG.Entities.Hero;
import RPG.Entities.HeroTypes.Human;
import RPG.Entities.NPC;
import RPG.Enums.Difficulties;
import RPG.Enums.HeroTypes;
import RPG.HeroItems.Weapon;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.security.Key;
import java.util.ArrayList;
import java.util.Scanner;

public class Main2 {

    public static void main(String[] args) throws IOException, InterruptedException {
        Human newHero = new Human("dvn", 100, 30, 18);
        NPC newNpc = new NPC("Boogy", 100, 20,20);
        Game newGame = new Game();
        Seller newSeller = new Seller();
        newHero.attack(newNpc);
//      newGame.defeatThanos(newHero);
//      Seller newSeller =  new Seller();
//      newSeller.sellerStoreRandom10items(newHero);
/*      Scanner scanner = new Scanner(new File("files/Content.txt"));
        String test = "";
        while (scanner.hasNextLine()){
            test += scanner.nextLine() + "\n";
        }
        String[] text1 = test.toString().split("0000");
        for(String item: text1){
            String[] items = item.split("1111");
            System.out.println(items[0]);
            System.out.println(items[1]);
        }*/
    }
}
