import java.io.IOException;
import java.util.Scanner;

public class Game {
  // VARIABLES GLOBALES
  private Scanner input = new Scanner(System.in);
  private String words[] = new String[0];
  private boolean exitGame = false;
  private char matrix[][];
  private int matrixSize = 0;
  private int withError = 0;
  private int life = 3;
  private String name;

  // ============UTILIDADES PARA CONSOLA===============
  // SIMPLIFICAR SYSTEM.OUT.PRINTLN
  private void print(String msg) {
    System.out.print(msg);
  }

  // MENSAJES DE ERROR
  private void errorHandler() {
    switch (withError) {
      case 1:
        printCenter("Opcion no permitida, intenta de nuevo", "-");
        break;
      case 2:
        printCenter("La longitud debe ser entre 5 y 10, intenta de nuevo", "-");
        break;
      case 3:
        printCenter("No se pueden insertar palabras repetidas", "-");
        break;
      case 4:
        printCenter("Aun no existen palabras ingresadas en el banco", "-");
        break;
      case 5:
        printCenter("Lo siento, no se encontro tu palabra, busca otra", "-");
        break;
      case 6:
        printCenter("Las palabras que ingresaste no caben en el tablero", "-");
        break;
      case 7:
        printCenter("Solo puedes ingresar un maximo de 30 palabras", "-");
        break;
    }
  }

  // DAR FORMATO A LOS MENUS
  public void clearScreen() {
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
    errorHandler();
  }

  // OPTENER OPCION DE CUALQUIER MENU COMO NUMERO
  private int getOption() {
    print("\n" + this.name + ": ");
    return input.nextInt();
  }

  // OPTENER TEXTO DE ENTRADA
  private String getOptionStr() {
    print("\n" + this.name + ": ");
    return input.next();
  }

  // MOSTRAR TEXTO CENTRADO
  private void printCenter(String text, String sperator) {
    int spaces = Math.round((60 - text.trim().length()) / 2);

    print("\n");
    for (int spaceIndex = 0; spaceIndex < spaces - 1; spaceIndex++)
      print(sperator);
    print(" " + text.trim() + " ");
    for (int spaceIndex2 = 0; spaceIndex2 < spaces; spaceIndex2++)
      print(sperator);
  }

  // MOSTRAR CABECERA DE MENUS
  private void printMenu(String title) {
    clearScreen();
    print("\n");
    printCenter(title, " ");
    print("\n-------------------------------------------------------------\n");
  }

  // VERIFICAR SI SE REPITEN LAS PALABRAS Y DONDE
  private int isWordRepeats(String word, int limit) {
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
  private boolean isEmpty() {
    // SI ESTA VACIO MOSTRAR ERROR
    if (words.length == 0) {
      withError = 4;
      clearScreen();
      return true;
    }

    // SINO RETORNAR FALSE
    else
      return false;
  }

  // VERIFICAR SI LA LONGITUD ESTA ENTRE 5 Y 10
  private boolean wordOverflow(String word) {
    return (word.length() >= 5 && word.length() <= 10);
  }

  // BUSCAR PALABRA EN EL BANCO
  private String searchWord() {
    // MOSTRAR MENU
    printMenu("BUSCAR PALABRA EN EL BANCO DE PALABRAS");
    String searchedWord = getOptionStr();

    // VERIFICAR LA LONGITUD
    if (wordOverflow(searchedWord)) {
      withError = 0;
      return searchedWord;
    }

    // SINO MOSTRAR ERROR 2 Y DEVOLVER CADENA VACIA
    else {
      withError = 2;
      return "";
    }
  }

  // OPCION DE INGRESAR PALABRAS
  private void insertWords() {
    boolean breakInsertWords = false;

    while (!breakInsertWords) {
      // MOSTRAR MENU Y OBTENER LONGITUD
      printMenu("CUANTAS PALABRAS TE GUSTARIA INGRESAR");
      int wordsSize = getOption();
      int wordCount = 0;
      int maxLength = 0;

      // ASIGNAR LA LONGITU AL BANCO
      words = new String[wordsSize];

      if (wordsSize < 30) {
        // MENU DE INSERTAR
        withError = 0;

        while (wordCount < wordsSize) {
          // MOSTRAR MENU DE PALABRAS Y OBTENER ENTRADA
          printMenu("INGRESA LA PALABRA NUMERO " + (wordCount + 1));
          String currentWord = getOptionStr();

          // VERIFICAR LA LONGITUD
          if (wordOverflow(currentWord)) {
            // EVITAR EXCEPCION EN 0 POR QUE LA PRIMERA PALABRA NO ES REPETIDA
            if (wordCount > 0) {
              // VERIFICAR SI SE REPITIO UNA PALABRA
              int breakRepeat = isWordRepeats(currentWord, wordCount);

              // SINO ESTA REPETIDA AGREGAR AL BANCO Y SALIR
              if (breakRepeat < 0) {
                withError = 0;
                words[wordCount] = currentWord;

                // ASIGNAR VALOR MAXIMO DE LONGITUD
                maxLength = Math.max(maxLength, currentWord.length());
                wordCount++;
              }

              // SINO MOSTRAR ERROR 3
              else
                withError = 3;
            }

            // SI ES LA PRIMERA PALABRA ASIGNAR DIRECTAMENTE
            else {
              withError = 0;
              words[0] = currentWord;

              // ASIGNAR VALOR MAXIMO DE LONGITUD
              maxLength = Math.max(maxLength, currentWord.length());
              wordCount++;
            }
          }

          // SINO MOSTRAR ERROR 2
          else
            withError = 2;
        }

        // VERIFICAR SI LAS PALABRAS CABEN O NO
        if (matrixSize < maxLength || matrixSize < wordsSize) {
          withError = 6;
          // PREGUNTAR SI QUIERE REDIMENSIONAR
          printMenu(
              "Ahora tu tablero sera de [" + (maxLength) + "]" + "[" + (maxLength) + "], estas de acuerdo? (si/no)");
          String confirm = getOptionStr();

          // SI CONFIRMO ENTONCES REDIMENSIONAR
          if (confirm.equals("si") || confirm.equals("Si") || confirm.equals("SI")) {
            matrix = new char[maxLength][maxLength];
            matrixSize = maxLength;
          }
        }

        // SALIR SIN ERRORES
        withError = 0;
        breakInsertWords = true;
      } else {
        withError = 7;
      }
    }
  }

  // OPCION DE ACTUALIZAR PALABRAS
  private void updateWords() {
    // VERIFICAR SI EL BANCO NO ESTA VACIO
    if (!isEmpty()) {
      boolean breakSearch = false;
      withError = 0;

      while (!breakSearch) {
        // BUSCAR UNA PALABRA PARA MODIFICAR
        String searchedWord = searchWord();

        // SI SE ENCONTRO EN EL BANCO MOSTRAR MENU
        if (searchedWord.length() > 0) {
          // OBTENER LA POSICION DE LA BUSQUEDA
          int foundSearch = isWordRepeats(searchedWord, 0);

          // SI EXISTE LA POSICION MOSTRAR MENU
          if (foundSearch >= 0) {
            withError = 0;
            boolean breakReplace = false;

            // OBTENER DATOS
            while (!breakReplace) {
              // MOSTRAR MENU DE REMPLAZO Y OBTENER PALABRA
              printMenu("POR CUAL PALABRA DESEAS REMPLAZAR '" + words[foundSearch] + "'");
              String replaceWord = getOptionStr();

              // VERIFICAR LA LONGITUD
              if (wordOverflow(replaceWord)) {

                // VERIFICIAR SI LA PALABRA DE REMPLAZO NO SE REPITE
                if (isWordRepeats(replaceWord, 0) < 0) {
                  // ASIGNAR AL BANCO LA NUEVA PALABRA Y SALIR
                  withError = 0;
                  words[foundSearch] = replaceWord;
                  breakSearch = true;
                  breakReplace = true;
                }

                // SINO MOSTRAR ERROR 3
                else
                  withError = 3;
              }

              // SINO MOSTRAR ERROR 2
              else
                withError = 2;
            }
          }

          // SINO MOSTRAR ERROR 5
          else {
            withError = 5;
          }
        }
      }
    }
  }

  // OPCION DE BORRAR PALABRAS
  private void deleteWords() {
    // VERIFICAR SI EL BANCO NO ESTA VACIO
    if (!isEmpty()) {
      boolean breaDelkSearch = false;
      // EMPEZAR MENU
      withError = 0;

      while (!breaDelkSearch) {
        // BUSCAR PALABRA
        String searchDelWord = searchWord();

        // VERIFICIAR SI EXISTE EN EL BANCO
        if (searchDelWord.length() > 0) {
          // OBTENER LA POSICION DE LA PALABRA
          int delWordIndex = isWordRepeats(searchDelWord, 0);

          // SI EXISTE ASIGNAR CADENA VACIA EN ESA POSICION Y SALIR
          if (delWordIndex >= 0) {
            withError = 0;
            words[delWordIndex] = "";
            breaDelkSearch = true;
          }

          // SINO MOSTRAR ERROR 5
          else
            withError = 5;
        }
      }
    }
  }

  // MENU DE INSERTAR PALABRAS INICIALES
  private void insertWordsMenu() {
    boolean breakInsert = false;
    while (!breakInsert) {
      // MOSTRAR MENU SIN ERRORES
      printMenu("(1) INSERTAR | (2) MODIFICAR | (3) ELIMINAR | (4) SALIR");
      withError = 0;

      switch (getOption()) {
        case 1:
          // OPCION 1 INSERTAR PALABRAS
          insertWords();
          break;
        case 2:
          // OPCION 2: ACTUALIZAR PALABRAS
          updateWords();
          break;
        case 3:
          // OPCION 3: BORRAR PALABRAS
          deleteWords();
          break;
        case 4:
          // OPCION 4: SALIR
          breakInsert = true;
          break;
        default:
          // MOSTRAR ERROR SINO ES LA OPCION CORRECTA
          withError = 1;
          break;
      }
    }
  }

  // MENU DE JUGAR
  private void playMenu() {
    // VERIFICAR SI EL BANCO NO ESTA VACIO
    if (!isEmpty()) {

    }
  }

  // MENU DE NUEVA PARTIDA
  private void menu() {
    while (!exitGame) {
      // LIMPIAR PANTALLA, MOSTRAR MENU PRINCIPAL SIN ERRORES
      printMenu("(1) INGRESO DE PALABRAS | (2) JUGAR | (3) TERMINAR PARTIDA");
      withError = 0;

      switch (getOption()) {
        case 1:
          // OPCION 1 MENU DE INSERTAR PALABRAS PRINCIPALES
          insertWordsMenu();
          break;
        case 2:
          // OPCION 2 MENU DE JUGAR
          playMenu();
          break;
        case 3:
          // OPACION 3 SALIR
          exitGame = true;
          break;
        default:
          // MOSTRAR ERROR DE OPCION INCORRECTA
          withError = 1;
      }
    }
  }

  private void setData() {
    print("Hola, Cual es tu nombre?: ");
    this.name = input.nextLine();
    print("Ok " + this.name + ", De que dimension quieres tu tablero?: ");
    this.matrixSize = input.nextInt();
    matrix = new char[matrixSize][matrixSize];
  }

  public Game() {
    setData();
    menu();
  }
}