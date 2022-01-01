package generate_graph;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Random;

public class MeshGenerator {
  public static void main(String[] args) throws Exception {

    int n = Integer.parseInt(args[0]);   // row
    int m = Integer.parseInt(args[1]);   // col        right-upper is (col, row)
    int Capacity = Integer.parseInt(args[2]);     // max capacity
    String fileName = args[3];
    Random rand = new Random();

    try {
      BufferedWriter out = new BufferedWriter(new FileWriter(fileName));

      for(int i = 1; i <= m; i++){
        out.write("s " + "(" + i + ",1) " + (rand.nextInt(Capacity)+1) + "\n");
      }

      for(int i = 1; i <= m; i++){
        for(int j = 1; j < n; j++){
          out.write("(" + i + "," + j + ") (" + i + "," + (j+1) + ") " + (rand.nextInt(Capacity)+1) + "\n");
        }
      }

      for(int i = 1; i <= m; i++){
        out.write("(" + i + "," + n + ") t " + (rand.nextInt(Capacity)+1) + "\n");
      }

      for(int i = 1; i < m; i++){
        for(int j = 1; j <= n; j++){
          out.write("(" + i + "," + j + ") (" + (i+1) + "," + j + ") " + (rand.nextInt(Capacity)+1) + "\n");
          out.write("(" + (i+1) + "," + j + ") (" + i + "," + j + ") " + (rand.nextInt(Capacity)+1) + "\n");
        }
      }

      out.close();
    } catch (Exception ex) {
      System.out.println(ex);
    }

  }
}
