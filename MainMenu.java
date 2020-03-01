import java.util.Scanner;

public class MainMenu {
  // DECLARAR VARIABLES GLOBALES
  private String rPlayer = "Jugador#" + Utils.random(0, 10000);
  private Scanner input = new Scanner(System.in);
  private String[] menus = new Strings().menus;
  private String[] names = new String[100];
  private int[][] status = new int[100][3];
  private int statusCount = -1;
  private int alertCode = 0;

  // MOSTRAR MENU SI SALE
  private Boolean showExitMenu() {
    // MOSTRAR MENU DE SALIR
    alertCode = 0;
    Utils.printMenu(menus[5], 0);

    // RETORNAR SI ES SI
    return (Utils.confirm(rPlayer, input));
  }

  // MOSTRAR JUGADORES ORDENADOS
  private void showOrderPlayers(String[] orderNames, int[][] orderPoints, int index) {
    // MOSTRAR NOMBRES
    Utils.print(orderNames[index] + " ");

    // SEPARAR EN LA PANTALLA
    Utils.print(Utils.repeatString("-", (61 - orderNames[index].length())));

    // MOSTRAR PUNTOS
    Utils.print("-> " + orderPoints[index][0] + "\n");
  }

  // MOSTRAR JUGADORES
  public void showPlayers(int mode, String title, String[] orderNames, int[][] orderPoints) {
    // IMPRIMIR MENU
    Utils.printMenu(title, alertCode);
    Utils.print(menus[20]);

    // RECORRER ESTADOS
    for (int satusIndex = 0; satusIndex < statusCount + 1; satusIndex++) {
      // SI EL MODO ES 0 MOSTRAR ORDENADOS
      if (mode == 0)
        showOrderPlayers(orderNames, orderPoints, satusIndex);

      // SINO MOSTRAR CONDICIONALMENTE
      else if (status[satusIndex][mode] == 1)
        showOrderPlayers(orderNames, orderPoints, satusIndex);
    }

    // SALIR CON CUALQUIER PALABRA
    Utils.print("\n");
    Utils.getOption(rPlayer, input);
  }

  // MOSTRAR ORDENADOS POR PUNTOS
  private void showPoints() {
    // REDUCIR ARRAYS
    int[][] newStatus = Utils.reduce(status, statusCount + 1, 3);
    String[] newNames = Utils.reduceString(names, statusCount + 1);

    // ORDENAR PUNTOS
    StrandInt order = Utils.orderByPoints(newStatus, newNames, statusCount + 1);
    int[][] orderPoints = order.index;
    String[] orderNames = order.value;

    // MOSTRAR JUGADORES ORDENADOS
    showPlayers(0, menus[2], orderNames, orderPoints);
  }

  // MENU DE HISTORIAL
  private void showHistory() {
    // VERIFICAR SI EXISTEN PARTIDAS
    if (statusCount > -1) {
      // INICIALIZAR MENU
      alertCode = 0;
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
            showPoints();
            break;
          case 2:
            // MOSTRAR JUGADORES QUE PERDIERON
            showPlayers(2, menus[3], names, status);
            break;
          case 3:
            // MOSTRAR JUGAFORES QUE ENCONTRARON TODAS LAS PALABRAS
            showPlayers(1, menus[4], names, status);
            break;
          case 4:
            // SALIR
            breakShowHistory = true;
          default:
            alertCode = 1;
        }
      }
    } else
      alertCode = 4;
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
          status[statusCount] = soap.getStatus();
          names[statusCount] = soap.getName();

          // SIN ALERTAS
          alertCode = 0;
          break;
        case 2:
          // MENU DE PARTIDAS
          showHistory();
          alertCode = 0;
          break;
        case 3:
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