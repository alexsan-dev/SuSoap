public class Strings {
  String[] warnings = { "", "Opcion no permitida, intenta de nuevo",
      "La longitud debe ser entre 5 y 10, intenta de nuevo", "No se pueden insertar palabras repetidas",
      "Aun no existen palabras ingresadas en el banco", "Lo siento, no se encontro tu palabra, busca otra",
      "Las palabras que ingresaste no caben en el tablero", "Solo puedes ingresar un maximo de 30 palabras",
      "</3 Palabra no encontrada, intenta de nuevo", "Partida terminada, sigue jugando",
      "Palabra encontrada!, obtienes puntos" };

  public String getWarning(int index) {
    return warnings[index];
  }
}