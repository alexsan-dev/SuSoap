package Source;

public class Soap {
  public static void main(String[] args) {
    // EMPEZAR ANIMACION DE INICIO
    Splash splash = new Splash();
    splash.start();

    // LANZAR MENU
    MainMenu menu = new MainMenu();
    menu.printMenu();
  }
}