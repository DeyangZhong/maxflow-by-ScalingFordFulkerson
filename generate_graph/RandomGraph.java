package generate_graph;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Random;

public class RandomGraph {
	public static void main(String[] args){
		int n = Integer.parseInt(args[0]);			// number of vertices
		int e = Integer.parseInt(args[1]);			// number of edges from one vertex
		int minCapacity = Integer.parseInt(args[2]); 
		int maxCapacity = Integer.parseInt(args[3]);
		String fileName = args[4];
		HashSet<Integer> s;
		Random rand = new Random();

		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(fileName));

			s = new HashSet<Integer>();
			while(s.size() < e){
				Integer Ver = Integer.valueOf(rand.nextInt(n)+1);
				if(!s.contains(Ver)){
					s.add(Ver);
					int capa = rand.nextInt(maxCapacity-minCapacity+1)+minCapacity;
					out.write("s " + "v" + Ver + " " + capa + "\n");
				}
			}
			
			s = new HashSet<Integer>();
			while(s.size() < e){
				Integer Ver = Integer.valueOf(rand.nextInt(n)+1);
				if(!s.contains(Ver)){
					s.add(Ver);
					int capa = rand.nextInt(maxCapacity-minCapacity+1)+minCapacity;
					out.write("v" + Ver + " t " + capa + "\n");
				}
			}

			for(int i = 1; i <= n; i++){
				s = new HashSet<Integer>();
				while(s.size() < e){
					Integer Ver = Integer.valueOf(rand.nextInt(n)+1);
					if(!s.contains(Ver)){
						s.add(Ver);
						int capa = rand.nextInt(maxCapacity-minCapacity+1)+minCapacity;
						out.write("v" + i + " v" + Ver + " " + capa + "\n");
					}
				}
			}

			out.close();
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
}