import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Utils {
  // IMPRIMIR EN CONSOLA
  public static void print(String msg) {
    System.out.print(msg);
  }

  // IMPRIMIR TEXTO CENTRADO
  public static void printCenter(String text, String sperator) {
    int spaces = Math.round((67 - text.trim().length()) / 2);

    print("\n");
    for (int spaceIndex = 0; spaceIndex < spaces - 1; spaceIndex++)
      print(sperator);
    print(" " + text.trim() + " ");
    for (int spaceIndex2 = 0; spaceIndex2 < spaces - 1; spaceIndex2++)
      print(sperator);
  }

  // MENSAJES DE ERROR
  public static void errorHandler(int withMessage) {
    print("\n");
    switch (withMessage) {
      case 0:
        print("\n" + repeatString("=", 67));
        break;
      case 1:
        printCenter("Opcion no permitida, intenta de nuevo", "=");
        break;
      case 2:
        printCenter("La longitud debe ser entre 5 y 10, intenta de nuevo", "=");
        break;
      case 3:
        printCenter("No se pueden insertar palabras repetidas", "=");
        break;
      case 4:
        printCenter("Aun no existen palabras ingresadas en el banco", "=");
        break;
      case 5:
        printCenter("Lo siento, no se encontro tu palabra, busca otra", "=");
        break;
      case 6:
        printCenter("Las palabras que ingresaste no caben en el tablero", "=");
        break;
      case 7:
        printCenter("Solo puedes ingresar un maximo de 30 palabras", "=");
        break;
      case 8:
        printCenter("</3 Palabra no encontrada, intenta de nuevo", "=");
        break;
      case 9:
        printCenter("Partida terminada, sigue jugando", "=");
        break;
      case 10:
        printCenter("Palabra encontrada!, obtienes puntos", "=");
        break;
    }
  }

  // DAR FORMATO A LOS MENUS
  public static void clearScreen(int withMessage) {
    // EJECUTAR COMANDO CLS O CLEAR
    try {
      if (System.getProperty("os.name").contains("Windows"))
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
      else
        Runtime.getRuntime().exec("clear");
    } catch (IOException | InterruptedException ex) {
      System.out.println(ex);
    }

    // LUEGO DE LIMPIAR MOSTRAR ERROR SI LO HAY
    printName();
    errorHandler(withMessage);
  }

  // MOSTRAR CABECERA DE MENUS
  public static void printMenu(String title, int withMessage) {
    clearScreen(withMessage);
    print("\n");
    printCenter(title, " ");
    print("\n-------------------------------------------------------------------\n");
  }

  // OPTENER OPCION DE CUALQUIER MENU COMO NUMERO
  public static int getOption(String name, Scanner input) {
    print("\n" + name + " => ");
    try {
      return input.nextInt();
    } catch (InputMismatchException ex) {
      input.nextLine();
      return 101;
    }
  }

  // OPTENER TEXTO DE ENTRADA
  public static String getOptionStr(String name, Scanner input) {
    print("\n" + name + " => ");
    return input.next();
  }

  // VERIFICAR SI SE REPITEN LAS PALABRAS Y DONDE
  public static int isWordRepeats(String word, int limit, String[] words) {
    // VALOR POR DEFECTO
    int out = -1;

    // RECORRER LAS PALABRAS HASTA LENGTH O LIMITE
    for (int wordIndex = 0; wordIndex < (limit == 0 ? words.length : limit); wordIndex++) {
      if (words[wordIndex].equals(word)) {
        out = wordIndex;
        break;
      }
    }

    // RETORNAR LA POSICION SI SE ENCONTRO
    return out;
  }

  // VERIFICIAR SI EL BANCO ESTA VACIO
  public static boolean isEmpty(String[] words) {
    return words.length == 0;
  }

  // VERIFICAR SI LA LONGITUD ESTA ENTRE 5 Y 10
  public static boolean wordOverflow(String word) {
    return (word.length() >= 5 && word.length() <= 10);
  }

  // BUSCAR PALABRA EN EL BANCO
  public static String searchWord(int withMessage, String name, Scanner input) {
    // MOSTRAR MENU
    printMenu("BUSCAR PALABRA EN EL BANCO DE PALABRAS", withMessage);
    String searchedWord = getOptionStr(name, input);

    // VERIFICAR LA LONGITUD
    if (wordOverflow(searchedWord)) {
      withMessage = 0;
      return searchedWord;
    }

    // SINO MOSTRAR ERROR 2 Y DEVOLVER CADENA VACIA
    else {
      withMessage = 2;
      return "";
    }
  }

  // OBTENER LAS PALABRAS CON LONGITUD MAXIMA
  public static int[] maxLengthWord(String[] words, int matrixSize) {
    // DECLARAR ARRAY DE PALABRAS
    int searched[] = new int[words.length];
    int extCount = 0;

    // ASIGNAR LAS PALABRAS MAS GRANDES A ARRAY
    for (int wordsIndex = 0; wordsIndex < words.length; wordsIndex++) {
      if (words[wordsIndex].length() == matrixSize) {
        searched[extCount] = wordsIndex;
        extCount++;
      }
    }

    // REDUCIR LA LONGITUD DEL ARRAY RESULTANTE
    int out[] = new int[extCount];
    for (int newCount = 0; newCount < extCount; newCount++) {
      out[newCount] = searched[newCount];
    }

    // RETORNAR EL ARRAY REDUCIDO
    return out;
  }

  // OBTENER NUMERO ALEATORIO ENTRE MIN(INCLUIDO) Y MAX(INCLUIDO)
  public static int random(int min, int max) {
    return (int) (Math.random() * ((max - min) + 1)) + min;
  }

  // RELLENAR MATRIZ
  public static void fillMatrix(char[][] words) {
    for (int i = 0; i < words.length; i++) {
      for (int j = 0; j < words.length; j++) {
        words[i][j] = randomLetter();
      }
    }
  }

  // INSERTAR PALABRA EN MATRIZ
  public static void insertonMatrix(int mode, int where, int start, String word, char[][] words) {
    int space = (words.length - word.length());
    int wordCount = -1;

    if (mode == 0) {
      for (int i = random(start, space); i < words.length; i++) {
        wordCount++;
        if (wordCount < word.length())
          words[where][i] = word.charAt(wordCount);
      }
    } else if (mode == 1) {
      for (int i = random(start, space); i < words.length; i++) {
        wordCount++;
        if (wordCount < word.length())
          words[i][where] = word.charAt(wordCount);
      }
    }
  }

  // IMPIRMIR MATRIZ
  public static void printMatrix(char[][] words) {
    int space = words.length < 16 ? (16 - words.length) * 2 : 1;
    print("\n\n");
    for (int i = 0; i < words.length; i++) {
      print((repeatString(" ", space) + "-") + repeatString("----", words.length) + "\n");
      for (int j = 0; j < words.length; j++) {
        print((j == 0 ? (repeatString(" ", space) + "| ") : "") + words[i][j] + " | ");
      }
      print("\n");
    }

    print((repeatString(" ", space) + "-") + repeatString("----", words.length) + "\n");
  }

  // GENEREAR LETRA RANDOM
  public static char randomLetter() {
    char[] letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    return letters[random(0, (letters.length - 1))];
  }

  // REPETIR STRING N VECES
  public static String repeatString(String str, int n) {
    String out = "";
    for (int i = 0; i < n; i++) {
      out += str;
    }
    return out;
  }

  // GENERAR UNA LISTA DE NUMEROS ALEATORIOS NO REPETIDOS
  public static int[] randomList(int n) {
    int[] out = new int[n];
    int i = 0;
    while (i < n) {
      int r = random(0, n);
      boolean breaks = false;

      for (int j = 0; j < n; j++) {
        if (r == out[j])
          breaks = true;
      }

      if (!breaks) {
        out[i] = r;
        i++;
      }
    }

    for (int s = 0; s < n; s++) {
      out[s] = out[s] - 1;
    }
    return out;
  }

  public static void printName() {
    print(
        "    MP''''''`MM          MP''''''`MM\n    M  mmmmm..M          M  mmmmm..M\n    M.      `YM dP    dP M.      `YM .d8888b. .d8888b. 88d888b.\n    MMMMMMM.  M 88    88 MMMMMMM.  M 88'  `88 88'  `88 88'  `88\n    M. .MMM'  M 88.  .88 M. .MMM'  M 88.  .88 88.  .88 88.  .88\n    Mb.     .dM `88888P' Mb.     .dM `88888P' `88888P8 88Y888P'\n    MMMMMMMMMMM          MMMMMMMMMMM                   88\n                                                       dP  ");
  }

  public static int[] reverse(int a[]) {
    int[] b = new int[a.length];
    int j = a.length;
    for (int i = 0; i < a.length; i++) {
      b[j - 1] = a[i];
      j = j - 1;
    }

    return b;
  }

  public static int[] orderByPoints(int[][] status, int length) {
    int aux = 0;
    int[] out = new int[length];
    for (int i = 0; i < length; i++) {
      if (status[i][0] > status[i + 1][0]) {
        out[i] = i;
      } else {
        out[i] = i + 1;
      }
    }
    return reverse(out);
  }
}