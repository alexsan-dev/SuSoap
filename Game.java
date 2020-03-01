import java.util.Scanner;

public class Game {
  // VARIABLES GLOBALES
  private Scanner input = new Scanner(System.in);
  private String words[] = new String[0];
  private boolean exitGame = false;
  private char matrix[][];
  private int matrixSize = 0;
  private int withMessage = 0;
  private int life = 3;
  private int[] globalStatus = new int[3];
  private int points = 20;
  private String name = "";

  private Boolean verifyDimensions(int maxLength, int lengthSum, int wordsSize, int selfError) {
    boolean out = false;
    boolean breakVerifyDimensions = false;
    while (!breakVerifyDimensions) {
      if (matrixSize < maxLength || matrixSize < wordsSize || (matrixSize * matrixSize) < lengthSum) {
        // PREGUNTAR SI QUIERE REDIMENSIONAR
        Utils.printMenu("(1) CAMBIAR PALABRAS | (2) REDIMENSIONAR TABLERO | (3) AUTOMATICO", selfError);
        int redimOptions = Utils.getOption(name, input);
        switch (redimOptions) {
          case 1:
            // REGRESAR AL MENU ANTERIOR
            out = false;
            breakVerifyDimensions = true;
            break;
          case 2:
            // PREGUNTAR OTRA VES POR LAS DIMENSIONES
            setDimensions();
            break;
          case 3:
            // PREGUNTAR SI QUIERE LA DIMENSION PROPUESTA
            int recomendedSize = (((maxLength + 1) * (maxLength + 1)) < lengthSum ? maxLength + 2 : maxLength + 1);

            withMessage = 0;
            Utils.printMenu("AHORA TU TABLERO SERA DE [" + (recomendedSize) + "]" + "[" + (recomendedSize)
                + "], ESTAS DE ACUERDO (si/no)", 0);
            String confirm = Utils.getOptionStr(name, input);

            // SI CONFIRMO ENTONCES REDIMENSIONAR
            if (confirm.equals("si") || confirm.equals("Si") || confirm.equals("SI")) {
              matrix = new char[recomendedSize][recomendedSize];
              matrixSize = recomendedSize;
              out = true;
              breakVerifyDimensions = true;
            }
            break;
          default:
            withMessage = 1;
        }
      } else {
        out = true;
        breakVerifyDimensions = true;
      }
    }
    return out;
  }

  private void setWordsinMatrix() {
    Utils.fillMatrix(matrix);
    int[] maxLargeWords = Utils.maxLengthWord(words, matrixSize - 1);
    int[] rRange = Utils.randomList(maxLargeWords.length);
    int firstLineWord = (maxLargeWords.length > 1 && rRange.length > 1) ? maxLargeWords[rRange[0]] : -1;
    int firstColWord = (maxLargeWords.length > 1 && rRange.length > 1) ? maxLargeWords[rRange[1]] : -1;
    // PALABRA DE LONGITUD GRANDE EN PRIMERA FILA

    if (firstLineWord >= 0 && firstColWord >= 0) {
      Utils.insertonMatrix(0, 0, 0, words[firstLineWord], matrix);
      Utils.insertonMatrix(1, 0, 0, words[firstColWord], matrix);

      for (int i = 0; i < words.length; i++) {
        if (i != firstLineWord && i != firstColWord)
          Utils.insertonMatrix(Utils.random(0, 1), Utils.random(1, matrix.length - 1), 0, words[i], matrix);
      }
    } else {
      for (int i = 0; i < words.length; i++) {
        Utils.insertonMatrix(Utils.random(0, 1), Utils.random(1, matrix.length - 1), 1, words[i], matrix);
      }
    }

  }

  // OPCION DE INGRESAR PALABRAS
  private void insertWords() {
    boolean breakInsertWords = false;

    while (!breakInsertWords) {
      // MOSTRAR MENU Y OBTENER LONGITUD
      Utils.printMenu("CUANTAS PALABRAS TE GUSTARIA INGRESAR", withMessage);
      int wordsSize = Utils.getOption(name, input);
      int wordCount = 0;
      int maxLength = 0;
      int lengthSum = 0;

      // ASIGNAR LA LONGITU AL BANCO
      words = new String[wordsSize];

      if (wordsSize < 30) {
        // MENU DE INSERTAR
        withMessage = 0;

        while (wordCount < wordsSize) {
          // MOSTRAR MENU DE PALABRAS Y OBTENER ENTRADA
          Utils.printMenu("INGRESA LA PALABRA NUMERO " + (wordCount + 1), withMessage);
          String currentWord = Utils.getOptionStr(name, input);

          // VERIFICAR LA LONGITUD
          if (Utils.wordOverflow(currentWord)) {
            // EVITAR EXCEPCION EN 0 POR QUE LA PRIMERA PALABRA NO ES REPETIDA
            if (wordCount > 0) {
              // VERIFICAR SI SE REPITIO UNA PALABRA
              int breakRepeat = Utils.isWordRepeats(currentWord, wordCount, words);

              // SINO ESTA REPETIDA AGREGAR AL BANCO Y SALIR
              if (breakRepeat < 0) {
                withMessage = 0;
                words[wordCount] = currentWord;

                // ASIGNAR VALOR MAXIMO DE LONGITUD
                maxLength = Math.max(maxLength, currentWord.length());
                lengthSum += currentWord.length();
                wordCount++;
              }

              // SINO MOSTRAR ERROR 3
              else
                withMessage = 3;
            }

            // SI ES LA PRIMERA PALABRA ASIGNAR DIRECTAMENTE
            else {
              withMessage = 0;
              words[0] = currentWord;

              // ASIGNAR VALOR MAXIMO DE LONGITUD
              maxLength = Math.max(maxLength, currentWord.length());
              wordCount++;
            }
          }

          // SINO MOSTRAR ERROR 2
          else
            withMessage = 2;
        }

        // VERIFICAR SI LAS PALABRAS CABEN O NO
        breakInsertWords = verifyDimensions(maxLength, lengthSum, wordsSize, 6);

        // SALIR SIN ERRORES
        withMessage = 0;
      } else
        withMessage = 7;
    }

    setWordsinMatrix();
  }

  // OPCION DE ACTUALIZAR PALABRAS
  private void updateWords() {
    // VERIFICAR SI EL BANCO NO ESTA VACIO
    if (!Utils.isEmpty(words)) {
      boolean breakSearch = false;
      withMessage = 0;

      while (!breakSearch) {
        // BUSCAR UNA PALABRA PARA MODIFICAR
        String searchedWord = Utils.searchWord(withMessage, name, input);

        // SI SE ENCONTRO EN EL BANCO MOSTRAR MENU
        if (Utils.wordOverflow(searchedWord)) {
          if (searchedWord.length() > 0) {
            // OBTENER LA POSICION DE LA BUSQUEDA
            int foundSearch = Utils.isWordRepeats(searchedWord, 0, words);

            // SI EXISTE LA POSICION MOSTRAR MENU
            if (foundSearch >= 0) {
              withMessage = 0;
              boolean breakReplace = false;

              // OBTENER DATOS
              while (!breakReplace) {
                // MOSTRAR MENU DE REMPLAZO Y OBTENER PALABRA
                Utils.printMenu("POR CUAL PALABRA DESEAS REMPLAZAR '" + words[foundSearch] + "'", 0);
                String replaceWord = Utils.getOptionStr(name, input);

                // VERIFICAR LA LONGITUD
                if (Utils.wordOverflow(replaceWord)) {

                  // VERIFICIAR SI LA PALABRA DE REMPLAZO NO SE REPITE
                  if (Utils.isWordRepeats(replaceWord, 0, words) < 0) {
                    // ASIGNAR AL BANCO LA NUEVA PALABRA Y SALIR
                    withMessage = 0;
                    words[foundSearch] = replaceWord;
                    breakSearch = true;
                    breakReplace = true;
                  }

                  // SINO MOSTRAR ERROR 3
                  else
                    withMessage = 3;
                }

                // SINO MOSTRAR ERROR 2
                else
                  withMessage = 2;
              }
            }

            // SINO MOSTRAR ERROR 5
            else {
              withMessage = 5;
            }
          }
        } else {
          withMessage = 2;
        }
      }
    } else
      withMessage = 4;
  }

  // OPCION DE BORRAR PALABRAS
  private void deleteWords() {
    // VERIFICAR SI EL BANCO NO ESTA VACIO
    if (!Utils.isEmpty(words)) {
      boolean breaDelkSearch = false;
      // EMPEZAR MENU
      withMessage = 0;

      while (!breaDelkSearch) {
        // BUSCAR PALABRA
        String searchDelWord = Utils.searchWord(withMessage, name, input);

        if (Utils.wordOverflow(searchDelWord)) {
          // VERIFICIAR SI EXISTE EN EL BANCO
          if (searchDelWord.length() > 0) {
            // OBTENER LA POSICION DE LA PALABRA
            int delWordIndex = Utils.isWordRepeats(searchDelWord, 0, words);

            // SI EXISTE ASIGNAR CADENA VACIA EN ESA POSICION Y SALIR
            if (delWordIndex >= 0) {
              withMessage = 0;
              words[delWordIndex] = "";
              breaDelkSearch = true;
            }

            // SINO MOSTRAR ERROR 5
            else
              withMessage = 5;
          }
        } else
          withMessage = 2;
      }
    } else
      withMessage = 4;
  }

  // MENU DE INSERTAR PALABRAS INICIALES
  private void insertWordsMenu() {
    boolean breakInsert = false;
    while (!breakInsert) {
      // MOSTRAR MENU SIN ERRORES
      Utils.printMenu("(1) INSERTAR | (2) MODIFICAR | (3) ELIMINAR | (4) SALIR", withMessage);
      withMessage = 0;

      switch (Utils.getOption(name, input)) {
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
          withMessage = 1;
          break;
      }
    }
  }

  private String printPlayMenu() {
    Utils.clearScreen(withMessage);
    Utils.printMatrix(matrix);
    Utils.print("\n");
    Utils.printCenter("Ingresa todas las palabras que encuentres\n", " ");
    Utils.print("\n-------------------------------------------------------------------\n");
    Utils.print("<3 Vidas: " + life + "                                          <*> puntos: " + points + "\n");
    return Utils.getOptionStr(name, input);
  }

  // MENU DE JUGAR
  private void playMenu() {
    int foundWords = words.length;
    String firstWord = "";
    points = 20;
    life = 3;

    // VERIFICAR SI EL BANCO NO ESTA VACIO
    if (!Utils.isEmpty(words)) {
      while (life > 0 && foundWords > 0) {

        String word = printPlayMenu();

        if (Utils.isWordRepeats(word, 0, firstWord.split(",")) < 0) {
          firstWord += "," + word;
          if (Utils.isWordRepeats(word, 0, words) >= 0) {
            withMessage = 10;
            points += word.length();
            foundWords--;
          } else {
            withMessage = 8;
            life--;
          }
        } else {
          withMessage = 3;
          life--;
        }
      }
      withMessage = 9;

      globalStatus[0] = points;
      globalStatus[1] = foundWords == 0 ? 1 : 0;
      globalStatus[2] = life == 0 ? 1 : 0;
    } else
      withMessage = 4;
  }

  // MENU DE NUEVA PARTIDA
  private void menu() {
    while (!exitGame) {
      // LIMPIAR PANTALLA, MOSTRAR MENU PRINCIPAL SIN ERRORES
      Utils.printMenu("(1) INGRESO DE PALABRAS | (2) JUGAR | (3) TERMINAR PARTIDA", withMessage);
      withMessage = 0;

      switch (Utils.getOption(name, input)) {
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
          withMessage = 1;
      }
    }
  }

  public int[] getStatus() {
    return globalStatus;
  }

  public String getName() {
    return name;
  }

  private void setData() {
    // PREGUNTAR NOMBRE Y DIMENSION
    withMessage = 0;
    Utils.printMenu("HOLA, CUAL ES TU NOMBRE", 0);
    name = Utils.getOptionStr(name, input);
    setDimensions();
  }

  private void setDimensions() {
    // PREGUNTAR QUE DIMENSION DESEA
    boolean breakSetDimensions = false;
    withMessage = 0;
    while (!breakSetDimensions) {
      Utils.printMenu("DE QUE DIMENSION QUIERES TU TABLERO", withMessage);
      matrixSize = Utils.getOption(name, input);

      if (matrixSize < 100) {
        // ASIGNAR DIMENSIONES DE LA MATRIZ
        withMessage = 0;
        matrix = new char[matrixSize][matrixSize];
        breakSetDimensions = true;
      } else
        withMessage = 1;
    }
  }

  public void start() {
    setData();
    menu();
  }

  public void setPlayer(String name) {
    this.name = name;
  }
}