import java.util.Scanner;

public class MainMenu {
  private int withMessage = 0;
  private Scanner input = new Scanner(System.in);
  private int[][] status = new int[3][100];
  private int statusCount = -1;

  private void showHistory(int[][] status, String[] names) {
    boolean breakShowHistory = false;
    withMessage = 0;

    while (!breakShowHistory) {
      Utils.printMenu("(1) PUNTUACION | (2) PERDEDORES | (3) GANADORES | (4) SALIR ", withMessage);
      int historyOption = Utils.getOption("", input);
      switch (historyOption) {
        case 1:
          int[][] newStatus = new int[3][statusCount + 1];
          for (int j = 0; j < statusCount + 1; j++) {
            newStatus[j] = status[j];
          }

          int[] order = Utils.orderByPoints(newStatus, statusCount + 1);
          for (int i = 0; i < statusCount + 1; i++) {
            Utils.print(order[i] + "\n");
          }
          int historyOption2 = Utils.getOption("", input);
          break;
      }
    }
  }

  public void printMenu() {
    boolean breakMenu = false;
    String names = "";
    withMessage = 0;

    while (!breakMenu) {
      Utils.printMenu("(1) NUEVA PARTIDA | (2) HISTORIAL | (3) CREDITOS | (4) SALIR ", withMessage);
      int mainOption = Utils.getOption("", input);
      switch (mainOption) {
        case 1:
          withMessage = 0;
          Game soap = new Game();
          soap.start();
          statusCount++;
          status[statusCount] = soap.getStatus();
          names += soap.getName() + ",";
          break;
        case 2:
          showHistory(status, names.split(","));
          break;
        case 3:
          break;
        case 4:
          breakMenu = true;
          break;
        default:
          withMessage = 1;
      }
    }
  }
}