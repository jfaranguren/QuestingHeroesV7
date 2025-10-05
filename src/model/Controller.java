package model;

import java.util.ArrayList;
import java.util.Random;

public class Controller {

    private Player myPlayer;
    private ArrayList<MapZone> maps;
    private ArrayList<Monster> monsters;

    public Controller() {

        myPlayer = null;
        maps = new ArrayList<MapZone>();
        monsters = new ArrayList<Monster>();
        loadPresetMonsters();

    }

    /**
     * Permite buscar un mapa por el nombre
     * @param mapName El nombre del mapa
     * @return MapZone el mapa encontrado o null en caso que no exista 
     */
    public MapZone searchMapZone(String mapName) {

        for (MapZone mapZone : maps) {
            if (mapZone.getName().equals(mapName)) {
                return mapZone;
            }

        }

        return null;
    }

    /**
     * Permite registrar un mapa en el sistema
     * @param mapName El nombre del mapa
     * @return boolean, true en caso exitoso, false en caso contrario
     */
    public boolean registerMap(String mapName) {

        MapZone newMap = new MapZone(mapName);
        newMap.addMonster(searchMonster("Slime"));
        newMap.setPlayerInMap(0, 0, 0, 0);
        return maps.add(newMap);

    }

    /*
     * Permite mover los monstruos en el mapa
     */
    public void moveMonstersInMap(String mapName) {

        MapZone map = searchMapZone(mapName);
        Random numberGenerator = new Random();

        for (Monster toMoveMonster : map.getMonsters()) {

            int numberOfMovements = numberGenerator.nextInt(3) + 1;
            int i = 0;

            while (i < numberOfMovements) {

                int xPosition = toMoveMonster.getxPosition();
                int yPosition = toMoveMonster.getyPosition();
                int direction = numberGenerator.nextInt(4) + 1;

                int[] coordinates = map.getMonsterInMap(toMoveMonster);

                // 1 Arriba, 2 Abajo, 3 Izquierda, 4 Derecha
                switch (direction) {
                    case 1:
                        xPosition--;
                        break;
                    case 2:
                        xPosition++;
                        break;
                    case 3:
                        yPosition--;
                        break;
                    case 4:
                        yPosition++;
                        break;

                    default:
                        break;
                }

                boolean result = searchMapZone(mapName).setMonsterInMap(xPosition, yPosition, coordinates[0],
                        coordinates[1]);

                if (result) {
                    toMoveMonster.setxPosition(xPosition);
                    toMoveMonster.setyPosition(yPosition);
                }
                i++;
            }

        }

    }

    /**
     * Permite mover al jugador en el mapa
     * 
     * @param direction La direccion de movimiento seleccionada
     * @param mapName   El mapa donde se va a mover el jugador
     * @return @return String, Movimiento en caso de poder moverse a una celda
     *         vacia, Combate en caso de moverse a una celda de Monstruo, Error en
     *         cualquier otro caso
     */
    public String movePlayerInMap(int direction, String mapName) {

        int xPosition = myPlayer.getxPosition();
        int yPosition = myPlayer.getyPosition();

        // 1 Arriba, 2 Abajo, 3 Izquierda, 4 Derecha
        switch (direction) {
            case 1:
                xPosition--;
                break;
            case 2:
                xPosition++;
                break;
            case 3:
                yPosition--;
                break;
            case 4:
                yPosition++;
                break;

            default:
                break;
        }

        String result = searchMapZone(mapName).setPlayerInMap(xPosition, yPosition, myPlayer.getxPosition(),
                myPlayer.getyPosition());

        if (result.equals("Movimiento")) {
            myPlayer.setxPosition(xPosition);
            myPlayer.setyPosition(yPosition);
            
            // moveMonstersInMap(mapName);
        }else if (result.equals("Combate")){
             //Modulo de combate
            searchMapZone(mapName).removeMonster(xPosition, yPosition);
            myPlayer.setxPosition(xPosition);
            myPlayer.setyPosition(yPosition);
        
        }

        return result;

    }

    /**
     * Permite obtener la representacion del mapa en cadena de texto
     * @param mapName El nombre del mapa
     * @return El mapa en forma de cadena de texto
     */
    public String getMapInfo(String mapName) {

        return searchMapZone(mapName).printMap();

    }

    /**
     * Descripcion: Permite cargar en el sistema monstruos por defecto
     * pre: El arreglo monsters de tipo Monster debe estar inicializado
     * pos: El el arreglo monsters qued actualizado
     */
    public void loadPresetMonsters() {
        registerMonster("Slime", 10, 1, 1, 3);
    }

    /**
     * Descripcion: Permite obtener la cantidad de roles registrados en el sistema
     */
    public int roleListSize() {
        return Role.values().length;
    }

    /**
     * Descripcion: Permite obtener el listado de roles registrados en el sistema
     * 
     * @return String, el listado de roles registrados como cadena de texto
     */
    public String getRoleList() {

        String rolesString = "";

        Role[] systemRoles = Role.values();

        for (int i = 0; i < systemRoles.length; i++) {

            rolesString += (i + 1) + ") " + systemRoles[i] + "\n";

        }

        return rolesString;
    }

    /**
     * Descripcion: Permite calcular un Role a partir de un consecutivo entero
     * 
     * @param role int, el consecutivo del rol registrado en el sistema 1 para Mage,
     *             2 para Warrior, etc.
     * @return Role, el rol calculado
     */
    public Role calculateRole(int role) {
        Role myRole = Role.values()[role];
        return myRole;
    }

    /**
     * Descripcion: Permite obtener la cantidad de razas registradas en el sistema
     */
    public int raceListSize() {
        return Race.values().length;
    }

    /**
     * Descripcion: Permite obtener el listado de razas registradas en el sistema
     * 
     * @return String, el listado de racas registradas como cadena de texto
     */
    public String getRaceList() {

        String racesString = "";

        Race[] systemRaces = Race.values();

        for (int i = 0; i < systemRaces.length; i++) {

            racesString += (i + 1) + ") " + systemRaces[i] + "\n";

        }

        return racesString;
    }

    /**
     * Descripcion: Permite calcular una Race a partir de un consecutivo entero
     * 
     * @param race int, el consecutivo de la raza registrada en el sistema 1 para
     *             Orc, 2 para Dragon, etc.
     * @return Race, la raza calculada
     */
    public Race calculateRace(int race) {
        Race myRace = Race.values()[race];
        return myRace;
    }

    /**
     * Descripcion: Permite registrar un jugador en el sistema
     * pos: El jugador queda registrado en el sistema en la variable myPlayer
     * 
     * @param nickname String, el nickname del jugador
     * @param role     int, el consecutivo del rol registrado en el sistema 1 para
     *                 Mage, 2 para Warrior, etc.
     * @return boolean, true si la operacion fue exitosa
     */
    public boolean registerPlayer(String nickname, int role) {
        Role myRole = calculateRole(role);
        myPlayer = new Player(nickname, myRole);
        return true;
    }

    /**
     * Descripcion: Permite registrar un monstruo en el sistema
     * pre: El arreglo monsters de tipo Monster debe estar inicializado
     * pos: El monstruo queda registrado en el sistema en el arreglo monsters de
     * tipo Monster
     * 
     * @param name         String, el nombre del monstruo
     * @param healthPoints int, los puntos de vida del monstruo
     * @param attackPower  int, el puntos de ataque del monstruo
     * @param role         int, el rol del monstruo
     * @param race         int, la raza del monstruo
     * @return boolean, true si la operacion fue exitosa, false en caso contrario
     */
    public boolean registerMonster(String name, int healthPoints, int attackPower, int role, int race) {
        Monster myMonster = new Monster(name, healthPoints, attackPower, calculateRole(role), calculateRace(race));

        return monsters.add(myMonster);
    }

    /**
     * Permite obtener la lista de monstruos registrados en el sistema
     * @return String, la lista de monstruos registrados
     */
    public String getMonsterList() {

        String monsterString = "";

        if (!monsters.isEmpty()) {

            for (int i = 0; i < monsters.size(); i++) {

                monsterString += (i + 1) + ") " + monsters.get(i).toString() + "\n";

            }
        }

        return monsterString;
    }

    /**
     * Permite obtener la lista de mapas registrados en el sistema
     * @return String, la lista de mapas registrados
     */
    public String getMapList() {

        String mapList = "";

        if (!maps.isEmpty()) {

            for (int i = 0; i < maps.size(); i++) {

                mapList += maps.get(i).getName() + "\n";

            }
        }

        return mapList;
    }

    /**
     * Permite actualizar la informacion de un monstruo a partir de su nombre
     * @param name el nombre por el cual se buscara al monstruo
     * @param choice el atributo a cambiar
     * @param value el valor a cambiar
     * @return boolean, true en caso de exito, false en caso contrario
     */
    public boolean modifyMonster(String name, int choice, int value) {

        Monster myMonster = searchMonster(name);

        if (myMonster != null) {

            switch (choice) {
                case 1: // healthPoints
                    myMonster.setHealthPoints(value);
                    return true;
                case 2: // attackPower
                    myMonster.setAttackPower(value);
                    return true;
                case 3: // role
                    myMonster.setRole(calculateRole(value));
                    return true;
                case 4: // race
                    myMonster.setRace(calculateRace(value));
                    return true;
            }
        }
        return false;
    }

    /**
     * Permite eliminar un monstruo del sistema
     * @param name El nombre del monstruo
     * @return boolean, true en caso de exito, false en caso contrario 
     */
    public boolean deleteMonster(String name) {

        Monster myMonster = searchMonster(name);

        if (myMonster != null) {
            return monsters.remove(myMonster);
        }

        return false;
    }

    /**
     * Permite buscar un monstruo por su nombre
     * @param name El nombre del monstruo a buscar
     * @return Monster, el monstruo encontrado, null en caso contrario
     */
    public Monster searchMonster(String name) {
        for (Monster monster : monsters) {
            if (monster.getName().equalsIgnoreCase(name)) {
                return monster;
            }
        }
        return null;

    }

    /**
     * Descripcion: Permite obtener el estado del jugador en el sistema
     * 
     * @return String, el estado del jugador como cadena de texto
     */
    public String getPlayerStatus() {
        return myPlayer.toString();
    }

    /**
     * Descripcion: Permite obtener el estado de un monstruo en el sistema
     * pre: El arreglo monsters de tipo Monster debe estar inicializado
     * 
     * @param name String, el nombre del monstruo
     * @return String, el estado del monstruo como cadena de texto
     */
    public String getMonsterStatus(String name) {
        String status = null;

        for (Monster monster : monsters) {

            if (monster.getName().equalsIgnoreCase(name)) {
                status = monster.toString();
                return status;
            }
        }
        return status;
    }

    /**
     * Descripcion: Permite obtener el listado de monstruos registrados en el
     * sistema
     * pre: El arreglo monsters de tipo Monster debe estar inicializado
     * 
     * @return String, el listado de monstruos registrados como cadena de texto
     */
    public String getMonsterListAsString() {
        String monsterList = "";
        for (Monster monster : monsters) {
            if (monster != null) {
                monsterList += monster.getName() + "\n";
            }
        }
        return monsterList;
    }

    /**
     * Descripcion: Permite atacar a un monstruo, actualizando su estado en el
     * sistema
     * pre: El arreglo monsters de tipo Monster debe estar inicializado
     * 
     * @param name String, el nombre del monstruo
     * @return int, el danio realizado al monstruo, 0 en caso de error
     */
    public int playerAttackMonster(String name) {
        for (Monster monster : monsters) {
            if (monster.getName().equalsIgnoreCase(name)) {
                monster.takeDamage(myPlayer.attack());
                return myPlayer.attack();
            }
        }
        return 0;
    }

    /**
     * Descripcion: Permite atacar al jugador, actualizando su estado en el sistema
     * pre: El arreglo monsters de tipo Monster debe estar inicializado
     * 
     * @param name String, el nombre del monstruo
     * @return int, el danio realizado al jugador, 0 en caso de error
     */
    public int monsterAttackPlayer(String name) {
        for (Monster monster : monsters) {
            if (monster.getName().equalsIgnoreCase(name)) {
                myPlayer.takeDamage(monster.attack());
                return monster.attack();
            }
        }
        return 0;
    }

    /**
     * Descripcion: Permite obtener el estado del jugador en el sistema
     * 
     * @return String, el estado del jugador como cadena de texto
     */
    public int playerHeal() {
        final int HEALING = 20; // Por defecto curarse tiene un valor fijo de 20;
        myPlayer.heal(HEALING);
        return HEALING;

    }

}
