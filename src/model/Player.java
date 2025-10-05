package model;

public class Player {

    // Atributos
    private String nickname;
    private Role role;
    private int healthPoints;
    private int power;
    private int xPosition;
    private int yPosition;

    public Player(String nickname, Role role) {

        this.nickname = nickname;
        this.role = role;
        healthPoints = 100;
        power = 1;
        xPosition = 0;
        yPosition = 0;

    }

    public String toString() {

        return nickname + " - " + healthPoints + " - " + role;

    }

    /**
     * Descripcion: Retorna el poder de ataque del jugador
     * 
     * @return int, el poder de ataque del jugador
     */
    public int attack() {
        return power;
    }

    /**
     * Descripcion: Permite "recuperar danio" al jugador modificando sus puntos de
     * vida
     * 
     * @param healing int, la curacion que recibe el jugador
     */
    public void heal(int healing) {
        healthPoints += healing;
    }

    /**
     * Descripcion: Permite "hacer danio" al jugador modificando sus puntos de vida
     * 
     * @param damage int, el danio que recibe el jugador
     */
    public void takeDamage(int damage) {
        healthPoints -= damage;
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

    // Pending
    public void interact() {

    }

}
