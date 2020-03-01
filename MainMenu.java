import java.util.Scanner;

public class MainMenu {
  private int withMessage = 0;
  private Scanner input = new Scanner(System.in);
  private int[][] status = new int[3][100];
  private String rPlayer = "Jugador#" + Utils.random(0, 10000);
  private String[] namesS = new String[100];
  private int statusCount = -1;

  private Boolean showExitMenu() {
    withMessage = 0;
    Utils.printMenu("JUEGA UN POCO MAS, SEGURO QUE QUIERES SALIR (si/no)", 0);
    String confirm = Utils.getOptionStr(rPlayer, input);
    return (confirm.contains("si") || confirm.contains("Si") || confirm.contains("SI") || confirm.contains("sI"));
  }

  private void showHistory() {
    if (namesS[0].length() > 0) {
      boolean breakShowHistory = false;
      withMessage = 0;
      while (!breakShowHistory) {
        Utils.printMenu("(1) PUNTUACION | (2) PERDEDORES | (3) GANADORES | (4) SALIR ", withMessage);
        int historyOption = Utils.getOption(rPlayer, input);
        switch (historyOption) {
          case 1:
            int[][] newStatus = new int[3][statusCount + 1];
            for (int j = 0; j < statusCount + 1; j++) {
              newStatus[j] = status[j];
            }

            String[] names = new String[statusCount + 1];
            for (int i = 0; i < statusCount + 1; i++) {
              names[i] = namesS[i];
            }

            StrandInt order = Utils.orderByPoints(newStatus, names, statusCount + 1);
            String[] orderNames = order.getString();
            int[][] orderPoints = order.getInt();

            Utils.printMenu("PUNTUACIONES MAS ALTAS ( 0 para salir )", withMessage);
            Utils.print("JUGADOR:                                                    PUNTOS:\n\n");
            for (int i = 0; i < statusCount + 1; i++) {
              Utils.print(orderNames[i] + " ");
              Utils.print(Utils.repeatString("-", (61 - orderNames[i].length())));
              Utils.print("-> " + orderPoints[i][0] + "\n");
            }
            Utils.print("\n");

            Utils.getOption(rPlayer, input);
            break;
          case 2:
            Utils.printMenu("PERDEDORES ( 0 para salir )", withMessage);
            Utils.print("JUGADOR:                                                    PUNTOS:\n\n");
            for (int i = 0; i < statusCount + 1; i++) {
              if (status[i][2] == 1) {
                Utils.print(namesS[i] + " ");
                Utils.print(Utils.repeatString("-", (61 - namesS[i].length())));
                Utils.print("-> " + status[i][0] + "\n");
              }
            }
            Utils.print("\n");

            Utils.getOption(rPlayer, input);
            break;
          case 3:
            Utils.printMenu("GANADORES ( 0 para salir )", withMessage);
            Utils.print("JUGADOR:                                                    PUNTOS:\n\n");
            for (int i = 0; i < statusCount + 1; i++) {
              if (status[i][1] == 1) {
                Utils.print(namesS[i] + " ");
                Utils.print(Utils.repeatString("-", (61 - namesS[i].length())));
                Utils.print("-> " + status[i][0] + "\n");
              }
            }
            Utils.print("\n");

            Utils.getOption(rPlayer, input);
            break;
          case 4:
            breakShowHistory = true;
          default:
            withMessage = 1;
        }
      }
    } else
      withMessage = 4;
  }

  public void printMenu() {
    boolean breakMenu = false;
    withMessage = 0;

    while (!breakMenu) {
      Utils.printMenu("(1) NUEVA PARTIDA | (2) HISTORIAL | (3) CREDITOS | (4) SALIR ", withMessage);
      int mainOption = Utils.getOption(rPlayer, input);
      switch (mainOption) {
        case 1:
          withMessage = 0;
          Game soap = new Game();
          soap.setPlayer(rPlayer);
          soap.start();
          statusCount++;
          status[statusCount] = soap.getStatus();
          namesS[statusCount] = soap.getName();
          break;
        case 2:
          showHistory();
          withMessage = 0;
          break;
        case 3:
          break;
        case 4:
          breakMenu = showExitMenu();
          break;
        default:
          withMessage = 1;
      }
    }
  }
}