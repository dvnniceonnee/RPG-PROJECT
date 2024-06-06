package RPG;

import RPG.Entities.Hero;

import javax.print.attribute.standard.Media;
import javax.sound.sampled.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class Main {


    /**
     * Method that asks a user a number, which can only go from a minValue(included) to a maxValue(excluded) otherwise asks again until the user inserts a correct number
     * @param minValue int
     * @param maxValue int
     * @return the first correct number that the user inserted as a int
     */
    public static int chooseInt(int minValue, int maxValue){
        Scanner input = new Scanner(System.in);
        int option = -1;
        do{
            try{
                option = input.nextInt();
                if(option < minValue || option >= maxValue){
                    System.out.println("Opção inválida!");
                    option = -1;
                }
            }catch (InputMismatchException ex1){
                Main.printStringWithSpaces("Opção inválida!");
                input.next();
            }
        }while (option == -1);
        return option;
    }

    /**
     * Method to play a audio
     * @param audioPath Path of the file audio (WAV format only)
     * @param playContinuously Boolean to put it playing continuously
     * @return Clip object
     */
    public static Clip playAudio(String audioPath, boolean playContinuously){
        try{
            File audio = new File(audioPath);
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(audio);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
            if(playContinuously){
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            audioInput.close();
            return clip;
        }catch (Exception ex){
            System.out.println("Audio " + audioPath + " corrompido!");
        }
        return null;
    }

    /**
     * Method to increase or decrease the volume of a clip
     * @param volume float that can go from 0 to 1
     * @param clip Clip object
     */
    public static void setVolume(float volume, Clip clip) {
        if (volume < 0f || volume > 1f)
            throw new IllegalArgumentException("Volume not valid: " + volume);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }

    /**
     * Method that makes the user write "y" or "n" to continue the game
     * @return String with "y" or "n"
     */
    public static String chooseYorN(){
        Scanner input = new Scanner(System.in);
        String userChoice = input.next();
        while (!userChoice.equalsIgnoreCase("y") && !userChoice.equalsIgnoreCase("n")){
            System.out.println("Opção inválida!");
            userChoice = input.next();
        }
        return userChoice;
    }

    /**
     * Method that waits for the user to press "Enter"
     */
    public static void pressEnterToContinue() {
        Scanner scanner = new Scanner(System.in);
        printStringWithSpaces("=========================================\n" +
                                   "|       ⌛ Continuar (press Enter)?     |\n" +
                                   "=========================================");
        scanner.nextLine();

    }

    /**
     * Method void that prints on console the name of the Hero, the level, the HP/MaxHp and the Coin Balance
     * @param hero Object Hero
     */
    public static void printHeroBar(Hero hero){
        String heroInfo = "#  \uD83C\uDFAE" + hero.getName() + " (⏫" + hero.getLevel() +
                ")  #  ❤\uFE0F " + hero.getHp() + "/" + hero.getMaxHp() +
                "  #  \uD83D\uDFE1 " + hero.getCoinBalance() + "  #";
        String separatorLine = "\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t";
        for(int i = 0; i <= heroInfo.length() +1; i++){
            separatorLine += "=";
        }
        System.out.println(separatorLine + "\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + heroInfo + separatorLine );
    }

    /**
     * Method to print a String which on console before every New line will write a "\t"
     * @param text
     */
    public static void printStringWithSpaces(String text) {
        Scanner textScanner = new Scanner(text);
        while (textScanner.hasNextLine()){
            System.out.println("\t" + textScanner.nextLine());
        }
    }

    /**
     * Method that prints a String which before every New line it will write on console a "\t" in slow mode by using the Thread.Sleep()
     * @param text String
     */
    public static void printStringWithSpacesSlow(String text) {
        Scanner textScanner = new Scanner(text);
        Clip typingSound = playAudio("files/sound/typing.wav", true);
        while (textScanner.hasNextLine()){
            String nextLine = textScanner.nextLine();
            System.out.print("\t");
            for(int i = 0; i < nextLine.length(); i++){
                System.out.print(nextLine.charAt(i));
                try{
                    Thread.sleep(15);
                }catch (Exception ex1){

                }
            }
            System.out.println();
        }
        typingSound.stop();
    }
    public static void printStringWithSpaces(File file) throws FileNotFoundException {
        Scanner textScanner = new Scanner(file);
        while (textScanner.hasNextLine()){
            System.out.println("\t" + textScanner.nextLine());
        }
    }

    /**
     * Method that asks a user a number, which can only go from a minValue to a maxValue otherwise asks again until the user inserts a correct number
     * @param minValue
     * @param maxValue
     * @return int that if we multiply by 5 it will be the same as the number inserted by the user (if the inserted number is not divided by 5 it will return the closest multiple of 5 of that number)
     */
    public static int returnIntMultipleofFive(int minValue, int maxValue){
        Scanner input = new Scanner(System.in);
        int option = -1;
        do{
            try{
                option = input.nextInt();
                if(option < minValue || option >= maxValue){
                    System.out.println("Quantidade escolhida Inválida!");
                    option = -1;
                }
                else {
                    option /= 5;
                }
            }catch (InputMismatchException ex1){
                System.out.println("Quantidade escolhida inválida!");
                input.next();
            }
        }while (option == -1);
        return option;
    }

    public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException {

        printStringWithSpaces(new File("files/Artes"));
        String newGameYorN = "";
        do{
            String firstMenu = "=======================================\n" +
                    "| Deseja começar um novo jogo (y/n)?  |\n" +
                    "=======================================";
            printStringWithSpaces(firstMenu);
            if(chooseYorN().equalsIgnoreCase("y")){
                printStringWithSpacesSlow("Recomendações de jogo\n" +
                        "-> Usa a tela em full screen \uD83D\uDDA5\uFE0F \n" +
                        "-> Liga o som do jogo para obteres uma melhor experiencia \uD83D\uDD0A \n" +
                        "-> Diverte-te a jogar \uD83C\uDFAE \n");
                Main.pressEnterToContinue();
                Clip music = playAudio("files/sound/musicBackGround.wav", true);
                setVolume(0.5F,music);
                try{
                    Game game = new Game();
                    Hero newHero = game.createCaracter();
                    game.defeatThanos(newHero);
                }catch (Exception ex){
                    Main.printStringWithSpacesSlow("Ficheiros de jogo corrompidos!!!");
                }
            }
            else {
                System.out.println("\n\tGame developed by Miguel madureira");
            }
        }while (newGameYorN .equalsIgnoreCase("n"));
    }
}
