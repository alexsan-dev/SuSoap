package Source;

public class Strings {
  // TEXTOS DE ALERTAS
  public String[] warnings = { "", "Opcion no permitida, intenta de nuevo",
      "La longitud debe ser entre 5 y 10, intenta de nuevo", "No se pueden insertar palabras repetidas",
      "Aun no existen palabras ingresadas en el banco", "Lo siento, no se encontro tu palabra, busca otra",
      "Las palabras que ingresaste no caben en el tablero", "Solo puedes ingresar un maximo de 20 palabras",
      "</3 Palabra no encontrada, intenta de nuevo", "Lo siento perdiste :(", "Palabra encontrada!, obtienes puntos",
      "Aun no existen perdedores", "Aun no existen ganadores", "Aun no existen partidas registradas",
      "Felicidades ganaste esta partida :)" };

  // TEXTOS DE MENUS
  private String exitString = " ( 0 para regresar )";
  public String[] menus = { "(1) NUEVA PARTIDA | (2) HISTORIAL | (3) CREDITOS | (4) SALIR ",
      "(1) PARTIDAS | (2) MEJORES | (3) PERDEDORES | (4) GANADORES | (5) SALIR ", "PUNTUACIONES MAS ALTAS" + exitString,
      "PERDEDORES" + exitString, "GANADORES" + exitString, "JUEGA UN POCO MAS, SEGURO QUE QUIERES SALIR (si/no)",
      "HOLA, CUAL ES TU NOMBRE", "DE QUE DIMENSION QUIERES TU TABLERO",
      "(1) INGRESO DE PALABRAS | (2) JUGAR | (3) TERMINAR PARTIDA",
      "(1) INSERTAR | (2) MODIFICAR | (3) ELIMINAR | (4) MOSTRAR | (5) SALIR", "CUANTAS PALABRAS TE GUSTARIA INGRESAR",
      "INGRESA LA PALABRA NUMERO ", "POR CUAL PALABRA DESEAS REMPLAZAR '",
      "(1) CAMBIAR PALABRAS | (2) REDIMENSIONAR TABLERO | (3) AUTOMATICO", "AHORA TU TABLERO SERA DE [",
      "], ESTAS DE ACUERDO (si/no)", "Ingresa toda palabra que encuentres\n", "|<3| Vidas: ",
      "                |*| puntos: ", "BUSCAR PALABRA EN EL BANCO DE PALABRAS",
      "JUGADOR:                                          PUNTOS FALLOS PALABRAS:\n\n",
      "\n-------------------------------------------------------------------------\n",
      "JUGADOR:                                                          PUNTOS:\n\n", "JUGADOR:\n\n",
      "TODAS LAS PARTIDAS" + exitString,
      "ALEX DANIEL SANTOS VALENZUELA\n 201904117 ceo@ibox.gt\n\n Proyectos personales:\n Github: github.com/alexsan-dev\n NPM: https://www.npmjs.com/~alexsantos\n Medium: https://medium.com/@as62971\n\n Proyectos para distribucion:\n Github: https://github.com/BlobySoftware\n NPM: https://www.npmjs.com/~blobysoftware\n\n Prueba mi nueva aplicacion oficial:\n https://mate.ingenieria.usac.app\n\n << SPAM SPAM SPAM >>\n PRUEBA LA VERSION BETA DE MI TIENDA: https://ibox.gt\n\n MUCHAS GRACIAS POR JUGAR :)\n escribe mi nombre para salir.\n.     ",
      "                 |!| Fallos: ", "BANCO DE PALABRAS " + exitString };

  // RETORNAR UNA ALERTA
  public String getWarning(int index) {
    return warnings[index];
  }

  // RETORNAR TITULO DE MANU
  public String getMenu(int index) {
    return menus[index];
  }
}