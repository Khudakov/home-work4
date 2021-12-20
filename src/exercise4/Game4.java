package exercise4;

import java.util.Random;
import java.util.Scanner;

public class Game4 {

    public static Random random = new Random();
    public static Scanner scanner = new Scanner(System.in);

    //Map
    public static char[][] map;
    public static int mapLength;
    public static int mapHeight;

    public static int mapSizeMin = 4;
    public static int mapSizeMax = 6;

    public static char empty = '_';
    public static char ready = '*';

    //Player
    public static char player = 'Z';
    public static String playerName = "Zero";

    public static int playerHP = 100;
    public static int playerMP = 30;

    public static int playerX = 1;
    public static int playerY = 1;

    public static final int playerMoveLeft = 4;
    public static final int playerMoveRight = 6;
    public static final int playerMoveUp = 8;
    public static final int playerMoveDown = 2;


    //Trap
    public static char trap = 'X';

    public static int trapX;
    public static int trapY;

    public static int trapAttack = 10;
    public static int trapCount = 5;
    public static int trapValueMin = 10;
    public static int trapValueMax = 30;

    public static void main(String[] args) {

        createMap();
        spawnPlayer();
        spawnTrap();

        while (true) {
            showMap();
            movePlayer();

            if (!playerAlive()) ;
            {
                System.out.println(playerName + " is dead");
                break;
            }

            if (!isFullMap()) {
                System.out.println(playerName + " win this map");
                break;
            }
            System.out.println(" Game over");

            showMap();
        }

    }



    public static void createMap() {
        mapLength = randomValue(mapSizeMin, mapSizeMax);
        mapHeight = randomValue(mapSizeMin, mapSizeMax);
        map = new char[mapLength][mapHeight];

        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapLength; x++) {
                map[y][x] = empty;
            }
        }
        System.out.println("Map size is: " + mapHeight + "x" + mapLength);
    }

    public static void showMap() {
        System.out.println("(==> MAP <==)");
        for (int y = 0; y < mapLength; y++) {
            for (int x = 0; x < mapHeight; x++) {
                System.out.print(map[y][x] + "|");
            }
            System.out.println();
        }
        System.out.println("(===========)");
    }

    public static void spawnTrap() {
        trapAttack = randomValue(trapValueMin, trapValueMax);
        trapCount = (mapHeight + mapLength) / 2;

        int trapX;
        int trapY;

        for (int i = 1; i <= trapCount; i++) {
            do {
                trapX = random.nextInt(mapLength);
                trapY = random.nextInt(mapHeight);
            } while (!isEmpty(trapX, trapY));
            map[trapX][trapY] = trap;
        }
        System.out.println(trapCount + " Trap' s Attack = " + trapAttack);
    }

    public static boolean isEmpty(int x, int y) {
        return map[x][y] == empty;
    }

    public static void spawnPlayer() {
        playerX = randomValue(0, mapLength - 1);
        playerY = randomValue(0, mapHeight - 1);
        map[playerX][playerY] = player;
        System.out.println(playerName + "Has spawn in [" + (playerX + 1) + ":" + (playerY + 1) + "]");
    }

    public static int randomValue(int min, int max) {
        return min + random.nextInt(max - min + 1);
    }

    public static boolean playerAlive() {
        return playerHP > 0;
    }

    public static void movePlayer() {
        int currentPlayerX = playerX;
        int currentPlayerY = playerY;
        int playerDestination;

        do {
            System.out.print("Enter your move: (Up: " + playerMoveUp + " | Down: " + playerMoveDown + " | Left: " + playerMoveLeft + " | Right: " + playerMoveRight);

            playerDestination = scanner.nextInt();

            switch (playerDestination) {
                case playerMoveUp:
                    playerY -= 1;
                    break;

                case playerMoveDown:
                    playerY += 1;
                    break;

                case playerMoveLeft:
                    playerX -= 1;
                    break;

                case playerMoveRight:
                    playerX += 1;
                    break;
            }

        } while (!checkValidMove(currentPlayerX, currentPlayerY, playerX, playerY));

        playerMoveAction(currentPlayerX, currentPlayerY, playerX, playerY);
    }

    public static boolean checkValidMove(int pastX, int pastY, int nextX, int nextY) {
        if (nextX >= 0 && nextX < mapLength && nextY >= 0 && nextY < mapHeight) {
            System.out.println(playerName + " move to [" + (nextX + 1) + ":" + (nextY + 1) + "] success");
            return true;
        } else {
            System.out.println(playerName + " move to [" + (nextX + 1) + ":" + (nextY + 1) + "] Please try again!");
            playerX = pastX;
            playerY = pastY;
            return false;
        }
    }

    public static void playerMoveAction(int pastX, int pastY, int nextX, int nextY) {
        if (map[nextX][nextY] == trap) {
            playerHP -= trapAttack;
            trapCount--;
            System.out.println("Alarm! " + playerName + " has been Attack. HP = " + playerHP);
        }
        map[nextX][nextY] = player;
        map[pastX][pastY] = ready;
    }

    public static boolean isFullMap() {
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapLength; x++) {
                if (map[y][x] == empty) return false;
            }
        }
        return true;
    }









}