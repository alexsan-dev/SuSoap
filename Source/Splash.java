package Source;

public class Splash {
  private int space = 10;
  private int interval = 3;

  public void start() {
    Utils.cls();
    Utils.printRandom(space);

    for (int frames = 0; frames < space + interval; frames++) {
      if (frames > interval) {
        Utils.cls();
        Utils.printRandom(space - frames);
      }
    }

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