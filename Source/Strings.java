package Source;

public class Strings {
  // TEXTOS DE ALERTAS
  public String[] warnings = { "", "Opcion no permitida, intenta de nuevo",
      "La longitud debe ser entre 5 y 10, intenta de nuevo", "No se pueden insertar palabras repetidas",
      "Aun no existen palabras ingresadas en el banco", "Lo siento, no se encontro tu palabra, busca otra",
      "Las palabras que ingresaste no caben en el tablero", "Solo puedes ingresar un maximo de 30 palabras",
      "</3 Palabra no encontrada, intenta de nuevo", "Partida terminada, sigue jugando",
      "Palabra encontrada!, obtienes puntos" };

  // TEXTOS DE MENUS
  public String[] menus = { "(1) NUEVA PARTIDA | (2) HISTORIAL | (3) CREDITOS | (4) SALIR ",
      "(1) PARTIDAS | (2) MEJORES | (3) PERDEDORES | (4) GANADORES | (5) SALIR ",
      "PUNTUACIONES MAS ALTAS ( 0 para salir )", "PERDEDORES ( 0 para salir)", "GANADORES ( 0 para salir)",
      "JUEGA UN POCO MAS, SEGURO QUE QUIERES SALIR (si/no)", "HOLA, CUAL ES TU NOMBRE",
      "DE QUE DIMENSION QUIERES TU TABLERO", "(1) INGRESO DE PALABRAS | (2) JUGAR | (3) TERMINAR PARTIDA",
      "(1) INSERTAR | (2) MODIFICAR | (3) ELIMINAR | (4) SALIR", "CUANTAS PALABRAS TE GUSTARIA INGRESAR",
      "INGRESA LA PALABRA NUMERO ", "POR CUAL PALABRA DESEAS REMPLAZAR '",
      "(1) CAMBIAR PALABRAS | (2) REDIMENSIONAR TABLERO | (3) AUTOMATICO", "AHORA TU TABLERO SERA DE [",
      "], ESTAS DE ACUERDO (si/no)", "Ingresa las palabras que encuentres\n", "<3 Vidas: ",
      "                                                <*> puntos: ", "BUSCAR PALABRA EN EL BANCO DE PALABRAS",
      "JUGADOR:                                          PUNTOS FALLOS PALABRAS:\n\n",
      "\n-------------------------------------------------------------------------\n",
      "JUGADOR:                                                          PUNTOS:\n\n", "JUGADOR:\n\n", };

  // RETORNAR UNA ALERTA
  public String getWarning(int index) {
    return warnings[index];
  }

  // RETORNAR TITULO DE MANU
  public String getMenu(int index) {
    return menus[index];
  }
}