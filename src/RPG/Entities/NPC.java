package RPG.Entities;

import RPG.Entity;
import RPG.Main;

public class NPC extends Entity {
    private final int coinBalance;

    public NPC(String name, int hp, int strengh, int coinBalance) {
        super(name, hp, strengh);
        this.coinBalance = coinBalance;
    }
    public int getCoinBalance() {
        return coinBalance;
    }

    @Override
    public String detailsEntity(){
        return "\uD83D\uDC7E : " + this.name +
                " | ⚔\uFE0F : " + this.strengh +
                " | ❤\uFE0F : " + this.hp +
                " | \uD83D\uDFE1 : " + this.coinBalance;
    }

    public void printNpcBar(){
        String npcInfo = "| \uD83D\uDC7E " + this.name + " [❤\uFE0F" + this.hp + "] |";
        String separatorLine = "\t";
        for(int i = 0; i <= npcInfo.length() ; i++){
            if(i == 0 || i == npcInfo.length() || i % 2 == 0){
                separatorLine += "-";
            }
            else {
                separatorLine += "=";
            }
        }
        System.out.print(separatorLine + "\n\t" + npcInfo + "\n" + separatorLine + "\n\n");
    }
}
