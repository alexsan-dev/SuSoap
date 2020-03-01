public class StrandInt {
  // DECLARAR LOS DOS TIPOS DE DATOS QUE RETORNA
  private String[] value;
  private int[][] index;

  // ASIGNAR EN EL CONSTRUCTOR
  public StrandInt(String[] str, int[][] index) {
    this.value = str;
    this.index = index;
  }

  // RETORNAR STRING[]
  public String[] getString() {
    return value;
  }

  // RETORNAR INT[][]
  public int[][] getInt() {
    return index;
  }
}
