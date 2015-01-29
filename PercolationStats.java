public class PercolationStats {
	private double[] data;
	private double mean, stddev, sum, confidenceLo, confidenceHi;
	private int len, times;

	public PercolationStats(int n, int t) throws IllegalArgumentException{
		if (n < 1 || t < 1){
			throw new IllegalArgumentException();
		}
		len = n;
		times = t;
		data = new double[t];
		for (int i = 0; i < t; i++){
			long seed = System.currentTimeMillis();
			StdRandom.setSeed(seed);
			Percolation p = new Percolation(n);
			int x, y;
			int numOpen = 0;
			while (!p.percolates()){
				do {
					x = StdRandom.uniform(1, n + 1);
					y = StdRandom.uniform(1, n + 1);
				} while (p.isOpen(x, y));
				numOpen++;
				p.open(x, y);
			}
			data[i] = (double)(numOpen)/(double)(n*n);
		}
	}
	public double mean(){
		sum = 0;
		for (int i = 0; i < times; i++){
			sum += data[i];
		}
		return mean = sum/(double)(times);
    }
    public double stddev(){
    	if (times < 2) return 0;
    	sum = 0;
    	for (int i = 0; i < times; i++){
    		sum += (data[i] - mean) * (data[i] - mean);
    	}
    	return stddev = Math.sqrt(sum/(double)(times - 1));
    }
    public double confidenceLo(){
    	return confidenceLo = mean - 1.96 * stddev / Math.sqrt(times);
    }
    public double confidenceHi(){
    	return confidenceHi = mean + 1.96 * stddev / Math.sqrt(times);
    }

    public static void main(String[] args){
    	int n = Integer.parseInt(args[0]); 
    	int t = Integer.parseInt(args[1]);
//    	System.out.println(n + " " + t);

    	PercolationStats ps = new PercolationStats(n, t);
    	System.out.println("mean = " + ps.mean());
    	System.out.println("stddev = " + ps.stddev());
    	System.out.println("95% confidence interval = " + ps.confidenceLo() + ", " + ps.confidenceHi());

    }
}