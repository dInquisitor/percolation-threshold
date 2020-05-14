import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private boolean grid [][];
  private final int numSites;
  private final int gridLength;
  private int numberOfOpenSites = 0;
  private final WeightedQuickUnionUF unionData;

  // creates n-by-n grid, with all sites initially blocked
  // assume n = 5; unionData goes from 0 to 24 then 25 and 26
  public Percolation(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException();
    }
    gridLength = n;
    numSites = n * n;
    unionData = new WeightedQuickUnionUF(numSites + 2); // 2 extra for virtual top and bottom; creates UF from 0 to numsites + 1
    grid = new boolean[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        grid[i][j] = false;
        if (i == 0) {
          // connect first row to virtual top
          unionData.union(calculatePosition(i, j), numSites);
        }
        if (i == n - 1) {
          // connect last row to virtual bottom
          unionData.union(calculatePosition(i, j), numSites + 1);
        }
      }
    }
  }

  private int calculatePosition (int row, int col) {
    // row and column are from 0 to n - 1 just like the union find data
    return (row * gridLength) + col;
  }

  // opens the site (row, col) if it is not open already
  public void open(int row, int col) {
    if (row < 1 || row > gridLength || col < 1 || col > gridLength) {
      throw new IllegalArgumentException();
    }
    // to open a site, union it with the open sites around it
    // normalize row and col
    row--;
    col--;
    // open already
    if(grid[row][col]) { 
      return;
    }
    grid[row][col] = true;
    // check top, bottom, left and right and connect them
    if(row - 1 > -1 && grid[row - 1][col]) { // if top is open
      unionData.union(calculatePosition(row, col), calculatePosition(row - 1, col));
    }
  
    if(row + 1 < gridLength && grid[row + 1][col]) { // if bottom is open
      unionData.union(calculatePosition(row, col), calculatePosition(row + 1, col));
    }
    if(col - 1 > -1 && grid[row][col - 1]) { // if left is open
      unionData.union(calculatePosition(row, col), calculatePosition(row, col - 1));
    }
    if(col + 1 < gridLength && grid[row][col + 1]) { // if right is open
      unionData.union(calculatePosition(row, col), calculatePosition(row, col + 1));
    }
    numberOfOpenSites++; 
  }

  // is the site (row, col) open?
  public boolean isOpen(int row, int col) {
    if (row < 1 || row > gridLength || col < 1 || col > gridLength) {
      throw new IllegalArgumentException();
    }
    return grid[row - 1][col - 1];
  }

  // is the site (row, col) full?
  public boolean isFull(int row, int col) {
    if (row < 1 || row > gridLength || col < 1 || col > gridLength) {
      throw new IllegalArgumentException();
    }
    row--;
    col--;
    return isOpen(row + 1, col + 1) && (unionData.find(calculatePosition(row, col)) == unionData.find(numSites));
}

  // returns the number of open sites
  public int numberOfOpenSites() {
    return numberOfOpenSites;
  }

  // does the system percolate?
  public boolean percolates() {
    if (gridLength == 1) return true;
    return unionData.find(numSites) == unionData.find(numSites + 1);
  }

  // private void printPercolation() {
  //   for (int i = 0; i < gridLength; i++) {
  //     for (int j = 0; j < gridLength; j++) {
  //       System.out.print(grid[i][j] + " ");
  //     }
  //     System.out.println("\n");
  //   }
  // }


  // test client (optional)
//   public static void main(String[] args) {
//     Percolation tester = new Percolation(10);
//     // tester.printPercolation();
//     tester.open(5, 11);
//     System.out.println("------------------------------------------------------------\n");
//     tester.printPercolation();
    
//  }
}