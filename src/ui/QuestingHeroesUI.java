package ui;

import java.util.Scanner;
import model.Controller;

public class QuestingHeroesUI {

    // Atributos
    private Scanner input;
    private Controller control;

    public static void main(String[] args) {
        QuestingHeroesUI ui = new QuestingHeroesUI();
        ui.menu();
    }

    // Constructor
    public QuestingHeroesUI() {
        input = new Scanner(System.in);
        control = new Controller();
    }

    /**
     * Descripcion: Permite mostrar al usuario el menu principal de opciones del
     * sistema
     * pre: El Scanner input debe estar inicializado
     */
    public void menu() {

        System.out.println("Bienvenido a Questing Heroes!\n");
        System.out.println("Para empezar, registre su jugador");
        registerPlayer();

        int option = 0;
        do {
            System.out.println("\nMENU PRINCIPAL");
            System.out.println("Digite una opcion");
            System.out.println(" 1) Registrar monstruos");
            System.out.println(" 2) Registrar mapa");
            System.out.println(" 3) Simular el combate");
            System.out.println(" 4) Simular el movimiento en el mapa");
            System.out.println(" 5) Modificar monstruo");
            System.out.println(" 6) Eliminar monstruo");
            System.out.println(" 0) Salir");
            option = input.nextInt();

            switch (option) {
                case 1:
                    registerMonster();
                    break;
                case 2:
                    registerMap();
                    break;
                case 3:
                    combatMenu();
                    break;
                case 4:
                    movePlayerInMap();
                    break;
                case 5:
                    // Llamar al metodo para modificar
                    break;
                case 6:
                    deleteMonster();
                    break;
                case 0:
                    System.out.println("Gracias por jugar. Adios");
                    break;
                default:
                    System.out.println("Opcion invalida. Intente nuevamente");
                    break;
            }
        } while (option != 0);
    }

    /**
     * Descripcion: Permite solicitar la informacion necesaria para poder registrar
     * un jugador en el sistema
     * pre: El Scanner input debe estar inicializado
     * pre: El Controller control debe estar inicializado
     */
    public void registerPlayer() {
        System.out.println("Digite su nickname");
        String nickname = input.nextLine();

        int role = -1;
        while (role < 0 || role > control.roleListSize() - 1) { // Repetitiva para controlar la entrada del usuario para
                                                                // el rol
            System.out.println("Seleccione su rol");
            System.out.println(control.getRoleList());
            role = input.nextInt() - 1;
        }

        input.nextLine();

        control.registerPlayer(nickname, role);
    }

    /**
     * Descripcion: Permite solicitar la informacion necesaria para poder registrar
     * un monstruo en el sistema
     * pre: El Scanner input debe estar inicializado
     * pre: El Controller control debe estar inicializado
     */
    public void registerMonster() {
        input.nextLine();
        System.out.println("Digite el nombre del monstruo");
        String name = input.nextLine();

        System.out.println("Digite los puntos de vida del monstruo");
        int healthPoints = input.nextInt();

        System.out.println("Digite el poder de ataque del monstruo");
        int attackPower = input.nextInt();

        int role = -1;
        while (role < 0 || role > control.roleListSize() - 1) {
            System.out.println("Seleccione el rol del monstruo");
            System.out.println(control.getRoleList());
            role = input.nextInt() - 1;
        }

        int race = -1;
        while (race < 0 || race > control.raceListSize() - 1) {
            System.out.println("Seleccione la raza del monstruo");
            System.out.println(control.getRaceList());
            race = input.nextInt() - 1;
        }

        if (control.registerMonster(name, healthPoints, attackPower, role, race)) {
            System.out.println("Operacion exitosa, Monstruo registrado");
        } else {
            System.out.println("Error, memoria llena");
        }

        System.out.println("El estado del monstruo es: " + control.getMonsterStatus(name));
    }

    /**
     * Descripcion: Permite mostrar al usuario el menu principal de opciones de
     * combate
     * pre: El Scanner input debe estar inicializado
     */
    public void combatMenu() {
        int option = 0;
        do {
            System.out.println("\nMENU DE COMBATE");
            System.out.println("Digite una opcion");
            System.out.println(" 1) Combatir un monstruo");
            System.out.println(" 2) Curarse");
            System.out.println(" 0) Volver al Menu Principal");
            option = input.nextInt();
            switch (option) {
                case 1:
                    showMonsterAttack(showPlayerAttack());
                    break;
                case 2:
                    showPlayerHealing();
                    break;
                case 0:
                    System.out.println("Volviendo al menu principal");
            }
        } while (option != 0);
    }

    /**
     * Descripcion: Muestra el ataque del jugador
     * pre: El Controller control debe estar inicializado
     * 
     * @return String, el nombre del monstruo
     */
    public String showPlayerAttack() {
        input.nextLine();
        System.out.println("\nLa lista de Monstruos es la siguiente:");
        System.out.println(control.getMonsterList());

        System.out.println("Digite el nombre del monstruo que quiere atacar");
        String name = input.nextLine();

        System.out.println("\nHas atacado. Hiciste " + control.playerAttackMonster(name) + " puntos de danio");
        System.out.println("El estado del monstruo es: " + control.getMonsterStatus(name));

        return name;
    }

    /**
     * Descripcion: Muestra el ataque del monstruo
     * pre: El Controller control debe estar inicializado
     * 
     * @param name String, el nombre del monstruo
     */
    public void showMonsterAttack(String name) {
        System.out.println("\nEl monstruo " + name + " te ha atacado. Te hizo " + control.monsterAttackPlayer(name)
                + " puntos de danio");
        System.out.println("El estado del jugador es: " + control.getPlayerStatus());
    }

    /**
     * Descripcion: Muestra la curacion del jugador
     * pre: El Controller control debe estar inicializado
     * 
     */
    public void showPlayerHealing() {
        System.out.println("\nTe has curado " + control.playerHeal() + " puntos de vida.");
        System.out.println("El estado del jugador es: " + control.getPlayerStatus());
    }

    public void deleteMonster() {
        String monsterList = control.getMonsterList();
        if (!monsterList.equals("")) {
            input.nextLine();
            System.out.println("\nEsta es la lista de monstruos registrados:\n");
            System.out.println(monsterList);

            System.out.println("Digite el nombre del monstruo a borrar");
            String monster = input.nextLine();

            if (control.deleteMonster(monster)) {
                System.out.println("Monstruo eliminado exitosamente!");
            } else {
                System.out.println("Error. El monstruo no pudo ser eliminado");
            }
        } else {
            System.out.println("No hay monstruos registrados en el sistema.");
        }
    }

    public void registerMap() {

        input.nextLine();
        System.out.println("Digite el nombre del mapa");
        String name = input.nextLine();

        if (control.registerMap(name)) {
            System.out.println("Mapa registrado exitosamente");
        } else {
            System.out.println("Error al momento de registrar el mapa");
        }
    }

    public void movePlayerInMap() {

        String mapList = control.getMapList();
        if (!mapList.equals("")) {
            System.out.println("\nEsta es la lista de mapas registrados:\n");
            System.out.println(mapList);
            input.nextLine();
            System.out.println("Digite el nombre del mapa a explorar");
            String mapName = input.nextLine();

            System.out.println("Este es el estado del mapa: ");
            System.out.println(control.getMapInfo(mapName));

            int option = 0;
            do {
                System.out.println("\nMENU DE MOVIMIENTO");
                System.out.println("Digite una opcion");
                System.out.println("1) Arriba");
                System.out.println("2) Abajo");
                System.out.println("3) Izquierda");
                System.out.println("4) Derecha");
                System.out.println("0) Volver al Menu Principal");
                option = input.nextInt();
                switch (option) {
                    case 1, 2, 3, 4:
                        String result = control.movePlayerInMap(option, mapName);
                       
                        if (result.equals("Error")) {
                            System.out.println("Error movimiento invalido");
                        } else if (result.equals("Combate")) {
                            System.out.println(result);
                        }
                        System.out.println(control.getMapInfo(mapName));
                        break;
                    case 0:
                        System.out.println("Volviendo al menu principal");
                }

            } while (option != 0);
        } else {
            System.out.println("No hay mapas registrados aun");
        }

    }

}
