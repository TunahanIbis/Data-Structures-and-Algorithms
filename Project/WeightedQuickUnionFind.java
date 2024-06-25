// Written by Tunahan İbiş, 122200077

// *** PLEASE run the Percolation.java in order to get the expected output. ***

// Used by Percolation.java to store information about which open sites are connected or not.
public class WeightedQuickUnionFind {
    private int[] parent; // Parent of each element in a component
    private int[] size; // Size of each component

    // Constructor:
    public WeightedQuickUnionFind(int n) {
        parent = new int[n]; // Initialize parent array
        size = new int[n]; // Initialize size array

        for (int i = 0; i < n; i++) {
            parent[i] = i; // Each element initially is its own root
            size[i] = 1; // Each component initially has a size of 1
        }
    }

    // Find the root of the component
    public int find(int p) {
        while (p != parent[p]) {
            parent[p] = parent[parent[p]]; // Make every other node point to its grandparent
            p = parent[p]; // Move to the parent's parent
        }
        return p; // Return the root of the component
    }

    // Check if two elements are connected
    public boolean connected(int p, int q) {
        return find(p) == find(q); // Check if the roots of the elements are the same
    }

    // Union of two components
    public void union(int p, int q) {
        int rootP = find(p); // Find the root of the component that includes p
        int rootQ = find(q); // Find the root of the component that includes q

        if (rootP == rootQ)
            return; // Return if they already belong to the same component

        // Connect smaller tree to larger tree
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ; // Make rootQ the parent of rootP
            size[rootQ] += size[rootP]; // Update the size of rootQ
        } else {
            parent[rootQ] = rootP; // Make rootP the parent of rootQ
            size[rootP] += size[rootQ]; // Update the size of rootP
        }
    }
}
