import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class AdjMatrix {
    private int V;
    private int E;
    private int[][] adj;

    public AdjMatrix (String filename) {
        File file = new File(filename);
        try(Scanner scanner = new Scanner(file)) {

            V = scanner.nextInt();
            if (V < 0)
                throw new IllegalArgumentException("V must be non-negative");

            adj = new int[V][V];

            E = scanner.nextInt();
            if (E < 0)
                throw new IllegalArgumentException("E must be non-negative");

            for (int i = 0; i < E; i++) {
                int a = scanner.nextInt();
                validateVertex(a);

                int b = scanner.nextInt();
                validateVertex(b);

                if (a == b)
                    throw new IllegalArgumentException("Self loop is detected");

                if (adj[a][b] == 1)
                    throw new IllegalArgumentException("Parallel edges are detected");

                // 无向图，a b 有边意味着 b a 也有边，矩阵是可以沿对角线对折的
                adj[a][b] = 1;
                adj[b][a] = 1;
            }

        } catch (IOException e) {
            // Java will automatic class the resource if exception happens
            e.printStackTrace();
        }
    }

    public int V () {
        return V;
    }

    public int E () {
        return E;
    }

    public boolean hasEdge (int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return adj[v][w] == 1;
    }

    // 找到所有的临边
    public ArrayList<Integer> adj (int v) {
        validateVertex(v);
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            if ( adj[v][i] == 1 )
                res.add(i);
        }
        return res;
    }

    public int degree (int v) {
        return adj(v).size();
    }

    private void validateVertex (int v) {
        if ( v < 0 || v >= V )
            throw new IllegalArgumentException("Vertex " + v + " is invalid");
    }

    @Override
    public String toString () {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("V = %d, E = %d\n", V, E));
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                sb.append(String.format("%d ", adj[i][j]));
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public static void main (String[] args) {
        AdjMatrix adjMatrix = new AdjMatrix("g.txt");
        System.out.println(adjMatrix);
    }
}
