package model;

public class Monster {

    private String name;
    private int healthPoints;
    private int attackPower;
    private Role role;
    private Race race;
    private int xPosition;
    private int yPosition;

    public Monster(String name, int healthPoints, int attackPower, Role role, Race race) {
        this.name = name;
        this.healthPoints = healthPoints;
        this.attackPower = attackPower;
        this.role = role;
        this.race = race;
    }

    public String toString() {

        return name + " - " + healthPoints + " - " + attackPower + " - " + role + " - " + race;

    }

    public String getName() {
        return name;
    }

    /**
     * Descripcion: Retorna el poder de ataque del monstruo
     * 
     * @return int, el poder de ataque del monstruo
     */
    public int attack() {
        return attackPower;
    }

    /**
     * Descripcion: Permite "hacer danio" al monstruo modificando sus puntos de vida
     * 
     * @param damage int, el danio que recibe el monstruo
     */
    public void takeDamage(int damage) {

        healthPoints -= damage;

    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

}
