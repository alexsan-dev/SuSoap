package Source;

public class Splash {
  // DECLARAR VARIABLES
  private int space, interval;

  // INCIALIZAR VARIABLES
  public Splash() {
    space = 10;
    interval = 3;
  }

  // LIMPIAR UNA VEZ Y MOSTRAR BANNER ALEATORIO
  public void start() {
    Utils.cls();
    Utils.printRandom(space);

    // CREAR UNA ANIMACION ALEATORIA
    for (int frames = 0; frames < space + interval; frames++) {
      if (frames > interval) {
        Utils.cls();
        Utils.printRandom(space - frames);
      }
    }

    // MOSTRAR 2 FRAMES ALEATORIOS Y 5 CON EL BANNER
    for (int frames = 0; frames < 7; frames++) {
      if (frames < 2) {
        Utils.cls();
        Utils.printRandom(0);
      } else {
        Utils.cls();
        Utils.printBanner(0);
      }
    }
  }
}