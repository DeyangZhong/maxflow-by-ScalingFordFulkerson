package generate_graph;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Random;

public class BipartiteGraph{
	public static void main(String[] args) throws Exception{
		
		int n = Integer.parseInt(args[0]); 				// number of nodes on the source side
		int m = Integer.parseInt(args[1]); 				// number of nodes on the target side
		double Probability = Double.parseDouble(args[2]); 	// probability of an edge between left part and right part to be created
		int minCapacity = Integer.parseInt(args[3]); 	// minimum capacity
		int maxCapacity = Integer.parseInt(args[4]); 	// maximum capacity
		String fileName = args[5];					// file name

		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
			
			for(int i = 0; i < n; i++){
				Random ran = new Random();
				out.write("s l" + (i+1) + " " + (minCapacity+ran.nextInt(maxCapacity-minCapacity+1)) + "\n");
			}

			for(int i = 0; i < n; i++){
				for(int j = 0; j < m; j++){
					Random ran = new Random();
					if(ran.nextDouble() < Probability){
						out.write("l" + (i+1) + " r" + (j+1) + " " + (minCapacity+ran.nextInt(maxCapacity-minCapacity+1)) + "\n");
					}
				}
			}

			for(int i = 0; i < m; i++){
				Random ran = new Random();
				out.write("r" + (i+1) + " t " + (minCapacity+ran.nextInt(maxCapacity-minCapacity+1)) + "\n");
			}

			out.close();
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
}