package model;

import java.util.ArrayList;
import java.util.Random;

public class MapZone {

    // Convenciones del Mapa
    private final char PLAYER = 'P';
    private final char MONSTER = 'M';
    private final char OBSTACLE = 'O';
    private final char EMPTY = '\0';

    private String name;
    private char[][] map;
    private ArrayList<Monster> monsters;
    private ArrayList<Obstacle> obstacles;

    public MapZone(String name) {

        this.name = name;
        map = new char[5][5];
        monsters = new ArrayList<Monster>();
        obstacles = new ArrayList<Obstacle>();
        generateObstacles();

    }

    /**
     * Permite actualizar la posicion del jugador en el mapa
     * 
     * @param xNewPosition La nueva coordenada x
     * @param yNewPosition La nueva coordenada y
     * @param xOldPosition La anterior coordenada x
     * @param yOldPosition La anterior coordenada y
     * @return String, Movimiento en caso de poder moverse a una celda vacia,
     *         Combate en caso de moverse a una celda de Monstruo, Error en
     *         cualquier otro caso
     */

    public String setPlayerInMap(int xNewPosition, int yNewPosition, int xOldPosition, int yOldPosition) {

        // Rango de filas y columnas 0-4
        if (xNewPosition >= 0 && xNewPosition < map.length && yNewPosition >= 0 && yNewPosition < map[0].length) {

            if (!isThereAnObstacle(xNewPosition, yNewPosition)) {

                if (isThereAMonster(xNewPosition, yNewPosition)) {
                    map[xOldPosition][yOldPosition] = EMPTY;
                    map[xNewPosition][yNewPosition] = PLAYER;
                    return "Combate";

                } else {
                    map[xOldPosition][yOldPosition] = EMPTY;
                    map[xNewPosition][yNewPosition] = PLAYER;
                    return "Movimiento";
                }
            }
        }
        return "Error";

    }

    /**
     * Permite obtener la informacion del mapa como una cadena de caracteres
     * 
     * @return String, el mapa como una cadena de caracteres
     */

    public String printMap() {

        String mapString = name + "\n";

        for (int i = 0; i < map.length; i++) { // Recorrido sobre las filas .length da el tamaño en filas
            for (int j = 0; j < map[0].length; j++) { // Recorrido sobre las columnas [0].length da el tamaño en
                                                      // columnas
                if (map[i][j] == '\0') {
                    mapString += "-" + "\t";
                } else {
                    mapString += map[i][j] + "\t";
                }

            }
            mapString += "\n";
        }

        return mapString;

    }

    /**
     * Genera una posicion aleatoria (x,y), esta nunca puede ser 0,0
     * 
     * @return int[] Arreglo con la posicion aleatoria
     */
    public int[] generateRandomPositionInMap() {

        Random numberGenerator = new Random();
        int[] coordinates = new int[2];

        int xPosition = 0;
        int yPosition = 0;

        while (xPosition == 0 && yPosition == 0) {

            xPosition = numberGenerator.nextInt(map.length - 1);
            yPosition = numberGenerator.nextInt(map.length - 1);
        }
        coordinates[0] = xPosition;
        coordinates[1] = yPosition;

        return coordinates;

    }

    /**
     * Permite determinar si una posicion de la matriz tiene un monstruo
     * 
     * @param xPosition La coordenada x
     * @param yPosition La coordenada y
     * @return boolean true en caso que afirmativo, false en caso contrario
     */
    public boolean isThereAMonster(int xPosition, int yPosition) {

        for (Monster monsterAdded : monsters) {

            if (monsterAdded.getxPosition() == xPosition && monsterAdded.getyPosition() == yPosition) {
                return true;
            }

        }
        return false;

    }

    /**
     * Permite adicionar un monstruo al mapa, proporcionandole una posicion x,y
     * 
     * @param newMonster El monstruo a adicionar
     * @return boolean, true en caso de exito, false en caso contrario
     */

    public boolean addMonster(Monster newMonster) {

        int[] coordinates = generateRandomPositionInMap();

        for (Monster monsterAdded : monsters) {

            while (monsterAdded.getxPosition() == coordinates[0] && monsterAdded.getyPosition() == coordinates[1]) {
                coordinates = generateRandomPositionInMap();
            }

        }

        newMonster.setxPosition(coordinates[0]);
        newMonster.setyPosition(coordinates[1]);
        map[coordinates[0]][coordinates[1]] = MONSTER;

        return monsters.add(newMonster);
    }

    /**
     * Permite obtener las coordenadas de un monstruo en el mapa
     * 
     * @param toMoveMonster El monstruo al que se quieren obtener sus coordenadas
     * @return int[] El arreglo con la coordenada x,y del Monstruo
     */

    public int[] getMonsterInMap(Monster toMoveMonster) {
        int[] coordinates = new int[2];

        for (Monster monster : monsters) {
            if (monster.equals(toMoveMonster)) {
                coordinates[0] = monster.getxPosition();
                coordinates[1] = monster.getyPosition();
                return coordinates;
            }
        }
        return null;

    }

    public boolean removeMonster(int xPosition, int yPosition) {

        for (Monster monsterAdded : monsters) {
            if (monsterAdded.getxPosition() == xPosition && monsterAdded.getyPosition() == yPosition) {

                monsters.remove(monsterAdded);
                return true;
            }
        }
        return false;

    }

    /**
     * Permite actualizar la posicion del monstruo en el mapa
     * 
     * @param xNewPosition La nueva coordenada x
     * @param yNewPosition La nueva coordenada y
     * @param xOldPosition La anterior coordenada x
     * @param yOldPosition La anterior coordenada y
     * @return boolean, true en caso de exito, false en caso contrario
     */

    public boolean setMonsterInMap(int xNewPosition, int yNewPosition, int xOldPosition, int yOldPosition) {

        // Rango de filas y columnas 0-4
        if (xNewPosition >= 0 && xNewPosition < map.length && yNewPosition >= 0 && yNewPosition < map[0].length
                && !isThereAnObstacle(xNewPosition, yNewPosition)) {
            map[xOldPosition][yOldPosition] = EMPTY;
            map[xNewPosition][yNewPosition] = MONSTER;
            return true;
        }
        return false;
    }

    /**
     * Permite determinar si una posicion de la matriz tiene un obstaculo
     * 
     * @param xPosition La coordenada x
     * @param yPosition La coordenada y
     * @return boolean true en caso que afirmativo, false en caso contrario
     */

    public boolean isThereAnObstacle(int xPosition, int yPosition) {

        for (Obstacle obstacleAdded : obstacles) {

            if (obstacleAdded.getxPosition() == xPosition && obstacleAdded.getyPosition() == yPosition) {
                return true;
            }

        }
        return false;

    }

    /**
     * Genera obstaculos aleatoriamente y los adiciona al mapa
     */
    public void generateObstacles() {

        Random numberGenerator = new Random();
        int totalAvailableCells = (int) ((map.length * map.length) * 0.1);
        int obstacleCells = numberGenerator.nextInt(totalAvailableCells) + 1;
        int i = 0;

        while (i < obstacleCells) {

            Obstacle newObstacle = new Obstacle();

            int[] coordinates = generateRandomPositionInMap();

            for (Obstacle obstacleAdded : obstacles) {

                while (obstacleAdded.getxPosition() == coordinates[0]
                        && obstacleAdded.getyPosition() == coordinates[1]) {
                    coordinates = generateRandomPositionInMap();
                }

            }

            newObstacle.setxPosition(coordinates[0]);
            newObstacle.setyPosition(coordinates[1]);

            obstacles.add(newObstacle);
            map[coordinates[0]][coordinates[1]] = OBSTACLE;
            i++;
        }

    }

    public String getName() {
        return name;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

}
