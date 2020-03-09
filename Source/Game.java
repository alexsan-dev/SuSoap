package Source;

import java.util.Scanner;

public class Game {
  // VARIABLES GLOBALES
  private int withMessage, matrixSize, points, life;
  private String[] menus, words;
  private int[] globalStatus;
  private int[][] orderList;
  private boolean exitGame;
  private char[][] matrix;
  private Scanner input;
  private String name;

  // INICIALIZAR VARIABLES
  public Game() {
    input = new Scanner(System.in);
    menus = new Strings().menus;
    globalStatus = new int[3];
    words = new String[0];
    exitGame = false;
    withMessage = 0;
    matrixSize = 0;
    points = 20;
    name = "";
    life = 3;
  }

  // VERIFICIAR SI LAS PALABRAS CABEN EL EL TABLERO
  private Boolean verifyDimensions(int maxLength, int lengthSum, int wordsSize) {
    // DECLARAR SALID E INICIALIZAR MENU
    boolean out = false;
    int customMessage = 0;
    boolean breakVerifyDimensions = false;

    while (!breakVerifyDimensions) {
      // VERIRICAR SI LAS PALBRAS OCUPAN UN ALTO MAXIMO
      if (matrixSize < (maxLength + 5) || matrixSize < (wordsSize + 5) || (matrixSize * matrixSize) < (lengthSum + 5)) {
        withMessage = (customMessage == 0) ? 6 : customMessage;

        // PREGUNTAR SI QUIERE REDIMENSIONAR Y OBTENER RESPUESTA
        Utils.printMenu(menus[13], withMessage);

        // OBTENER OPCION
        switch (Utils.getOption(name, input)) {
          case 1:
            // REGRESAR AL MENU ANTERIOR
            out = false;
            breakVerifyDimensions = true;
            break;
          case 2:
            // PREGUNTAR OTRA VES POR LAS DIMENSIONES
            setDimensions();
            customMessage = 0;
            break;
          case 3:
            // PREGUNTAR SI QUIERE LA DIMENSION PROPUESTA
            int recomendedSize = (((maxLength + 1) * (maxLength + 1)) < lengthSum ? maxLength + 2 : maxLength + 1) + 4;

            // MOSTRAR MENU
            withMessage = 0;
            Utils.printMenu(menus[14] + (recomendedSize) + "]" + "[" + (recomendedSize) + menus[15], 0);

            // SI CONFIRMO ENTONCES REDIMENSIONAR
            if (Utils.confirm(name, input)) {
              matrix = new char[recomendedSize][recomendedSize];
              breakVerifyDimensions = true;
              matrixSize = recomendedSize;
              out = true;
            }

            // REINICIAR MENSAJES
            customMessage = 0;
            break;

          // ALERTA DE ERROR 1
          default:
            customMessage = 1;
        }
      }
      // SINO SALIR SIN ERRORES
      else {
        breakVerifyDimensions = true;
        withMessage = 0;
        out = true;
      }
    }

    // RETORNAR BOOLEAN
    return out;
  }

  // INSERTAR PALABRAS EN LA MATRIZ
  private void setWordsinMatrix() {
    // LLENARLA ALEATORIAMENTE
    Utils.fillMatrix(matrix);

    // OBTENER LAS PALABRAS CON LOGITUG MAXIMA
    int[] maxLargeWords = Utils.maxLengthWord(words, matrixSize - 5);

    // GENERAR UNA LISTA ALETORIA NO REPETIDA
    int[] rRange = Utils.randomList(maxLargeWords.length);
    int[] mainRange = Utils.randomList(matrix.length - 5);

    // SELCCIONAR DOS PALABRAS CON LONGITUD MAXIMA
    int firstLineWord = (maxLargeWords.length > 1 && rRange.length > 1) ? maxLargeWords[rRange[0]] : -1;
    int firstColWord = (maxLargeWords.length > 1 && rRange.length > 1) ? maxLargeWords[rRange[1]] : -1;

    // GUARDAR POSICION DE CADA PALABRA
    orderList = new int[words.length][3];

    if (firstLineWord >= 0 && firstColWord >= 0) {
      // PALABRA DE LONGITUD GRANDE EN PRIMERA FILA Y PRIMERA COLUMNA
      Utils.insertonMatrix(0, 0, 0, words[firstLineWord], matrix);
      Utils.insertonMatrix(1, 0, 1, words[firstColWord], matrix);

      // ASIGNAR LAS POSICIONES
      int[] firstLine = { 0, 0, 0 };
      int[] secondLine = { 1, 0, 1 };
      orderList[firstLineWord] = firstLine;
      orderList[firstColWord] = secondLine;

      // INSERTAR LAS DEMAS PALABRAS
      for (int i = 0; i < words.length; i++) {
        if (i != firstLineWord && i != firstColWord) {
          // CREAR NUMEROS ALETORIOS PARA LAS POSICIONES
          int space = Utils.random(1, (matrixSize - words[i].length()));
          int mode = Utils.random(0, 1);
          int position = 1 + mainRange[i];

          // INSERTAR EN LA MATRIZ
          Utils.insertonMatrix(mode, position, space, words[i], matrix);

          // INSERTAR POSICIONES DE PALABRA
          orderList[i][0] = mode;
          orderList[i][1] = position;
          orderList[i][2] = space;
        }
      }
    } else {
      for (int i = 0; i < words.length; i++) {
        // CREAR NUMEROS ALETORIOS PARA LAS POSICIONES
        int space = Utils.random(1, (matrixSize - words[i].length()));
        int mode = Utils.random(0, 1);
        int position = 1 + mainRange[i];

        // INSERTAR EN LA MATRIZ
        Utils.insertonMatrix(mode, position, space, words[i], matrix);

        // INSERTAR POSICIONES DE PALABRA
        orderList[i][0] = mode;
        orderList[i][1] = position;
        orderList[i][2] = space;
      }
    }
  }

  // OPCION DE INGRESAR PALABRAS
  private void insertWords() {
    // INICIALIZAR MENU
    boolean breakInsertWords = false;

    while (!breakInsertWords) {
      // MOSTRAR MENU Y OBTENER LONGITUD
      Utils.printMenu(menus[10], withMessage);
      int wordsSize = Utils.getOption(name, input);
      int wordCount = 0;
      int maxLength = 0;
      int lengthSum = 0;

      // ASIGNAR LA LONGITUD AL BANCO
      words = new String[wordsSize];

      // NO SE PUEDEN INGRESAR MAS DE 30 PALABRAS
      if (wordsSize <= 20) {
        // MENU DE INSERTAR
        withMessage = 0;

        while (wordCount < wordsSize) {
          // MOSTRAR MENU DE PALABRAS Y OBTENER ENTRADA
          Utils.printMenu(menus[11] + (wordCount + 1), withMessage);
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
        breakInsertWords = verifyDimensions(maxLength, lengthSum, wordsSize);

        // SALIR SIN ERRORES
        withMessage = 0;
      }

      // SINO ALERTA 7
      else
        withMessage = 7;
    }
  }

  // OPCION DE ACTUALIZAR PALABRAS
  private void updateWords() {
    // VERIFICAR SI EL BANCO NO ESTA VACIO
    if (!Utils.isEmpty(words)) {
      boolean breakSearch = false;
      withMessage = 0;

      while (!breakSearch) {
        // BUSCAR UNA PALABRA PARA MODIFICAR
        String searchedWord = Utils.searchWord(withMessage, menus[19], name, input);

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
                Utils.printMenu(menus[12] + words[foundSearch] + "'", 0);
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

            // SINO MOSTRAR ERROR 5 Y SALIR
            else {
              withMessage = 5;
              breakSearch = true;
            }
          }
        }

        // SINO MOSTRAR ERROR 2
        else {
          withMessage = 2;
        }
      }
    }

    // SINO MOSTRAR ERROR 4
    else
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
        String searchDelWord = Utils.searchWord(withMessage, menus[19], name, input);

        if (Utils.wordOverflow(searchDelWord)) {
          // VERIFICIAR SI EXISTE EN EL BANCO
          if (searchDelWord.length() > 0) {
            // OBTENER LA POSICION DE LA PALABRA
            int delWordIndex = Utils.isWordRepeats(searchDelWord, 0, words);

            // SI EXISTE ASIGNAR CADENA VACIA EN ESA POSICION Y SALIR
            if (delWordIndex >= 0) {
              // CORRER EL ARRAY 1 POSICION
              words = Utils.translate(1, delWordIndex, words);

              // SALIR
              breaDelkSearch = true;
              withMessage = 0;
            }

            // SINO MOSTRAR ERROR 5 Y SALIR
            else {
              withMessage = 5;
              breaDelkSearch = true;
            }
          }
        }

        // SINO MOSTRAR ERROR 2
        else
          withMessage = 2;
      }
    }

    // SINO MOSTRAR ERROR 4
    else
      withMessage = 4;
  }

  private void showWords() {
    // VERIFICAR SI EXISTEN PALABRAS EN EL BANCO
    if (!Utils.isEmpty(words)) {
      // LIMPIAR PANTALLA Y MOSTRAR MENU
      Utils.clearScreen(withMessage);
      Utils.print("\n");
      Utils.printCenter(menus[27], " ");
      Utils.print(menus[21]);

      // MOSTRAR TODAS LAS PALABRAS
      for (int wordsIndex = 0; wordsIndex < words.length; wordsIndex++)
        Utils.print((wordsIndex + 1) + ". " + words[wordsIndex] + "\n");

      // SALIR
      Utils.getOption(name, input);
    }

    // SINO SALIR
    else
      withMessage = 4;
  }

  // MENU DE INSERTAR PALABRAS INICIALES
  private void insertWordsMenu() {
    boolean breakInsert = false;
    while (!breakInsert) {
      // MOSTRAR MENU SIN ERRORES
      Utils.printMenu(menus[9], withMessage);
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
          showWords();
          break;
        case 5:
          // DESPUES DE ACTUALIZAR EL BANCO, INSERTARLO EN LA MATRIZ
          setWordsinMatrix();
          breakInsert = true;
          break;
        default:
          // MOSTRAR ERROR SINO ES LA OPCION CORRECTA
          withMessage = 1;
          break;
      }
    }
  }

  private String printPlayMenu(int fails) {
    // LIMPIAR PANTALLA Y MOSTRAR MATRIZ
    Utils.clearScreen(withMessage);
    Utils.printMatrix(matrix);
    Utils.print("\n");

    // MOSTRAR MENU
    Utils.printCenter(menus[16], " ");
    Utils.print(menus[21]);

    // MOSTRAR VIDAS Y PUNTOS
    Utils.print(menus[17] + life + menus[18] + points + menus[26] + fails + "\n");

    // RETORNAR LAS PALABRAS QUE ENCUENTRE
    return Utils.getOptionStr(name, input);
  }

  // MENU DE JUGAR
  private void playMenu() {
    // INICIALIZAR VARIABLES
    int foundWords = words.length;
    String firstWord = "";
    points = 20;
    life = 3;

    // VERIFICAR SI EL BANCO NO ESTA VACIO
    if (!Utils.isEmpty(words)) {
      // SALIR CUANDO LA VIDA SEA 0 O ENCONTRO TODAS LAS PALABRAS
      while (life > 0 && foundWords > 0) {
        // OBTENER PALABRA QUE INGRESO
        String word = printPlayMenu(3 - life);

        // VERIFICAR SI LA PALABRA QUE INGRESO ES REPETIDA
        if (Utils.isWordRepeats(word, 0, firstWord.split(",")) < 0) {
          // AUMANTAR BANCO SECUNDARIO
          firstWord += "," + word;

          // SI LA PALABRA EXISTE EN EL BANCO SUMAR LA LONGITUD DE LA PALABRA
          int foundWord = Utils.isWordRepeats(word, 0, words);
          if (foundWord >= 0) {
            Utils.insertonMatrix(orderList[foundWord][0], orderList[foundWord][1], orderList[foundWord][2],
                Utils.repeatString("$", word.length()), matrix);
            points += word.length();
            withMessage = 10;
            foundWords--;
          }

          // SINO RESTAR VIDA
          else {
            withMessage = 8;
            points -= 5;
            life--;
          }
        }

        // SINO RESTAR VIDA
        else {
          withMessage = 3;
          life--;
        }
      }

      // ASIGNAR PUNTOS AL ESTATUS
      globalStatus[0] = points;
      globalStatus[1] = words.length - foundWords;
      globalStatus[2] = 3 - life;
    }

    // SINO MOSTRAR ERROR 4
    else
      withMessage = 4;

    // SALIR DEL JUEGO
    exitGame = true;
  }

  // MENU DE NUEVA PARTIDA
  private void menu() {
    while (!exitGame) {
      // MOSTRAR MENU PRINCIPAL SIN ERRORES
      Utils.printMenu(menus[8], withMessage);
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
          // OPCION 3 SALIR
          exitGame = true;
          break;
        default:
          // MOSTRAR ERROR DE OPCION INCORRECTA
          withMessage = 1;
      }
    }
  }

  // MENU INICIAL
  private void setData() {
    // PREGUNTAR NOMBRE
    withMessage = 0;
    Utils.printMenu(menus[6], 0);
    name = Utils.getOptionStr(name, input);

    // MENU DE DIMENSION
    setDimensions();
  }

  private void setDimensions() {
    // PREGUNTAR QUE DIMENSION DESEA
    boolean breakSetDimensions = false;
    withMessage = 0;

    // INICIALIZAR MENU
    while (!breakSetDimensions) {
      // MOSTRAR MENU
      Utils.printMenu(menus[7], withMessage);
      matrixSize = Utils.getOption(name, input);

      // LIMITAR A UNA DIMENSION DE 100 (YA NO CABE EN LA PANTALLA)
      if (matrixSize < 100 && matrixSize > 0) {
        // ASIGNAR NUEVAS DIMENSIONES DE LA MATRIZ
        matrix = new char[matrixSize][matrixSize];
        breakSetDimensions = true;
        withMessage = 0;
      }

      // SINO MOSTRAR ERROR 1
      else
        withMessage = 1;
    }
  }

  // INICIAR JUEGO
  public void start() {
    setData();
    menu();
  }

  // ======== GETTERS ========
  // OBTENER PUNTOS
  public int[] getStatus() {
    return globalStatus;
  }

  // OBTENER NUEVO NOMBRE
  public String getName() {
    return name;
  }

  // ======== SETTERS ========
  // ASIGNAR NOMBRE TEMPORAL
  public void setPlayer(String name) {
    this.name = name;
  }
}