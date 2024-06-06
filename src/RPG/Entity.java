package RPG;

public abstract class Entity {
    protected String name;
    protected int maxHp;
    protected int hp;
    protected int strengh;

    /**
     * Constructor of the class Entity
     * @param name name of the entity
     * @param hp maximum health of the entity (the atual Hp will be the same)
     * @param strengh Strengh of the entity
     */
    public Entity(String name, int hp, int strengh) {
        this.name = name;
        this.maxHp = hp;
        this.hp = hp;
        this.strengh = strengh;
    }

    public String detailsEntity(){
        return "Nome : " + this.name +
                " | \uD83D\uDCAA : " +  this.strengh +
                " | ❤\uFE0F : " + this.hp;
    }

    public String getName() {
        return name;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getStrengh() {
        return strengh;
    }

    public void setStrengh(int strengh) {
        this.strengh = strengh;
    }
}
