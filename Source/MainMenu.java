package Source;

import java.util.Scanner;

public class MainMenu {
  // DECLARAR VARIABLES GLOBALES
  private int statusCount, alertCode;
  private String[] menus, names;
  private String rPlayer;
  private int[][] status;
  private Scanner input;

  // INICIALIZAR VARIABLES
  public MainMenu() {
    rPlayer = "Jugador ID:#" + Utils.randomString(Utils.random(1, 5)) + Utils.random(0, 10000);
    input = new Scanner(System.in);
    menus = new Strings().menus;
    status = new int[100][3];
    names = new String[100];
    statusCount = -1;
    alertCode = 0;
  }

  // MOSTRAR MENU SI SALE
  private Boolean showExitMenu() {
    // MOSTRAR MENU DE SALIR
    alertCode = 0;
    Utils.printMenu(menus[5], 0);

    // RETORNAR SI ES SI
    return (Utils.confirm(rPlayer, input));
  }

  // MOSTRAR JUGADORES ORDENADOS
  private void showOrderPlayers(int mode, String[] orderNames, int[][] orderPoints, int index, int customIndex) {
    // DECLAR LIMITES PARA IMPRIMIR
    String modeFormat = "";
    int space = orderNames[index].length();
    int wins = customIndex < 0 ? index : customIndex;

    // MOSTRAR ESTADO
    if (mode == 0) {
      modeFormat = (orderPoints[index][0] < 10 ? 0 : "") + Integer.toString(orderPoints[index][0]) + "      "
          + Integer.toString(orderPoints[index][2]) + "        " + Integer.toString(orderPoints[index][1]);
      space = 50;
    }

    // MOSTRAR SOLO LOS PUNTOS
    else if (mode == 3) {
      modeFormat = Integer.toString(orderPoints[index][0]);
      space = 67;
    }

    // MOSTRAR NOMBRES
    Utils.print(Integer.toString(wins + 1) + ". " + orderNames[index] + " ");

    // SEPARAR EN LA PANTALLA
    Utils.print(Utils.repeatString("-", (space - orderNames[index].length() - 3)));

    // IMPRIMIR COMPLETO
    Utils.print(((mode == 1 || mode == 2) ? "" : "-> ") + modeFormat + "\n");
  }

  // MOSTRAR JUGADORES
  public void showPlayers(int mode, String title, String[] orderNames, int[][] orderPoints) {
    // NUEVO CONTADOR
    int winnersCount = 0;
    int loosersCount = 0;
    alertCode = 0;

    // IMPRIMIR MENU
    Utils.printMenu(title, alertCode);
    Utils.print(mode == 0 ? menus[20] : mode == 3 ? menus[22] : menus[23]);

    // RECORRER ESTADOS
    for (int satusIndex = 0; satusIndex < statusCount + 1; satusIndex++) {
      // SI EL MODO ES 0 MOSTRAR ORDENADOS
      if (mode == 0)
        showOrderPlayers(0, orderNames, orderPoints, satusIndex, -1);

      // MOSTRAR 3 PUNTUACIONES MAS ALTAS
      else if (mode == 3 && satusIndex < 3)
        showOrderPlayers(3, orderNames, orderPoints, satusIndex, -1);

      // SINO MOSTRAR CONDICIONALMENTE
      else if (mode == 1) {
        if (status[satusIndex][2] == 3 || status[satusIndex][0] == 0) {
          showOrderPlayers(2, orderNames, orderPoints, satusIndex, loosersCount);
          loosersCount++;
        }

      } else if (mode == 2 && status[satusIndex][0] > 0 && status[satusIndex][2] != 3) {
        showOrderPlayers(2, orderNames, orderPoints, satusIndex, winnersCount);
        winnersCount++;
      }
    }

    // ERROR 11 SI NO EXISTEN PERDEDORES
    if (mode == 1 && loosersCount == 0)
      alertCode = 11;

    // ERROR 12 SI NO EXISTEN GANADORES
    else if (mode == 2 && winnersCount == 0)
      alertCode = 12;

    else {
      // SALIR CON CUALQUIER PALABRA
      Utils.print("\n");
      Utils.getOption(rPlayer, input);
    }
  }

  // MOSTRAR PARTIDAS
  private void showGames() {
    alertCode = 0;
    showPlayers(0, menus[24], names, status);
  }

  // MOSTRAR ORDENADOS POR PUNTOS
  private void showPoints() {
    alertCode = 0;

    // REDUCIR ARRAYS
    int[][] newStatus = Utils.reduce(status, statusCount + 1, 3);
    String[] newNames = Utils.reduceString(names, statusCount + 1);

    // ORDENAR PUNTOS
    StrandInt order = Utils.orderByPoints(newStatus, newNames, statusCount + 1);
    int[][] orderPoints = order.index;
    String[] orderNames = order.value;

    // MOSTRAR JUGADORES ORDENADOS
    showPlayers(3, menus[2], orderNames, orderPoints);
  }

  private void showCredits() {
    // IMRPIMIR INFORMACION DE DESARROLADOR
    Utils.printMenu(menus[25], 0);

    // SALIR
    Utils.getOption(rPlayer, input);
  }

  // MENU DE HISTORIAL
  private void showHistory() {
    // SIN ALERTAS
    alertCode = 0;

    // VERIFICAR SI EXISTEN PARTIDAS
    if (statusCount >= 0) {
      // INICIALIZAR MENU
      boolean breakShowHistory = false;

      // INICIAR MENU
      while (!breakShowHistory) {
        // MOSTRAR MENU
        Utils.printMenu(menus[1], alertCode);
        int historyOption = Utils.getOption(rPlayer, input);

        // VERIFICAR OPCION
        switch (historyOption) {
          case 1:
            // MOSTRAR JUGADORES ORDENADOS
            showGames();
            break;
          case 2:
            showPoints();
            break;
          case 3:
            // MOSTRAR JUGADORES QUE PERDIERON
            showPlayers(1, menus[3], names, status);
            break;
          case 4:
            // MOSTRAR JUGAFORES QUE ENCONTRARON TODAS LAS PALABRAS
            showPlayers(2, menus[4], names, status);
            break;
          case 5:
            // SALIR
            breakShowHistory = true;
          default:
            alertCode = 1;
        }
      }

      // SIN ALERTAS
      alertCode = 0;
    }

    // SINO MOSTRAR ERROR 4
    else
      alertCode = 13;
  }

  // MENU PRINCIPAL
  public void printMenu() {
    // INICIALIZAR MENU
    boolean breakMenu = false;
    alertCode = 0;

    while (!breakMenu) {
      // MOSTRAR MENU
      Utils.printMenu(menus[0], alertCode);
      int mainOption = Utils.getOption(rPlayer, input);

      switch (mainOption) {
        case 1:
          // INICIAR UN NUEVO JUEGO
          Game soap = new Game();

          // AGREGAR NOMBRE Y EMPEZAR
          soap.setPlayer(rPlayer);
          soap.start();

          // ASIGNAR VALORES DE LAS PARTIDAS
          statusCount++;
          int[] currentStatus = soap.getStatus();
          String currentNames = soap.getName();
          status[statusCount] = currentStatus;
          names[statusCount] = currentNames;

          // SIN ALERTAS
          alertCode = currentStatus[2] == 3 ? 9 : currentStatus[0] == 0 ? 9 : 14;
          break;
        case 2:
          // MENU DE PARTIDAS
          showHistory();
          break;
        case 3:
          showCredits();
          break;
        case 4:
          // MENU DE SALIDA
          breakMenu = showExitMenu();
          break;

        // MENSAJE DE ERROR
        default:
          alertCode = 1;
      }
    }
  }
}