import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
  private double percolationThresholds[];
  private int numTries;
  // perform independent trials on an n-by-n grid
  public PercolationStats(int n, int trials) {
    if (n <= 0 || trials <= 0) {
      throw new IllegalArgumentException();
    }
    numTries = trials;
    percolationThresholds = new double[trials];
    for (int i = 0; i < trials; i++) {
      Percolation trial = new Percolation(n);
      int numOpenSites = 0;
      while (!trial.percolates()) {
        int row = StdRandom.uniform(1, n + 1);
        int col = StdRandom.uniform(1, n + 1);
        while (trial.isOpen(row, col)) {
          row = StdRandom.uniform(1, n + 1);
          col = StdRandom.uniform(1, n + 1);
        }
        // site is not open
        trial.open(row, col);
        numOpenSites++;
      }
      // it has percolated
      double percolationThreshold = numOpenSites / (n * n * 1.0);
      percolationThresholds[i] = percolationThreshold;
    }
  }

  // sample mean of percolation threshold
  public double mean() {
    return StdStats.mean(percolationThresholds);
  }

  // sample standard deviation of percolation threshold
  public double stddev() {
    return StdStats.stddev(percolationThresholds);
  }

  // low endpoint of 95% confidence interval
  public double confidenceLo() {
    return mean() - ((1.96 * stddev()) / Math.sqrt(numTries));
  }

  // high endpoint of 95% confidence interval
  public double confidenceHi() {
    return mean() + ((1.96 * stddev()) / Math.sqrt(numTries));
  }

  // test client (see below)
  public static void main(String[] args) {
    PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
    System.out.printf("%-23s %s\n", "mean", "= " + stats.mean());
    System.out.printf("%-23s %s\n", "stddev", "= " + stats.stddev());
    System.out.printf("%-23s %s\n", "95% confidence interval", "= " + "[" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
  }

}