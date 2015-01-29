public class Percolation {
	private int n;
	private WeightedQuickUnionUF wqu, wqu2;
	private boolean[] open;

	public Percolation(int k) {
		n = k;
		wqu = new WeightedQuickUnionUF(n*n + 2);
		wqu2 = new WeightedQuickUnionUF(n*n + 1);
		open = new boolean[n*n + 2];
		for (int i = 0; i < open.length; i++){
			open[i] = false;
		}
		for (int i = 1; i < n + 1; i++){
			wqu.union(0, i);
			wqu2.union(0, i);
			wqu.union(n*n + 1, n*n + 1 - i);
		}
	}

	public void open(int i, int j) throws IndexOutOfBoundsException{ // possible to reopen
		if (i < 1 || i > n || j < 1 || j > n){
			throw new IndexOutOfBoundsException();
		}
		if (open[n * (i - 1) + j]) return;
		open[n * (i - 1) + j] = true;
		if (i > 1 && open[n * (i - 2) + j]){
			wqu.union(n * (i - 1) + j, n * (i - 2) + j);
			wqu2.union(n * (i - 1) + j, n * (i - 2) + j);
		}
		if (i < n && open[n * i + j]){
			wqu.union(n * (i - 1) + j, n * i + j);
			wqu2.union(n * (i - 1) + j, n * i + j);
		}
		if (j > 1 && open[n * (i - 1) + j - 1]){
			wqu.union(n * (i - 1) + j, n * (i - 1) + j - 1);
			wqu2.union(n * (i - 1) + j, n * (i - 1) + j - 1);
		}
		if (j < n && open[n * (i - 1) + j + 1]){
			wqu.union(n * (i - 1) + j, n * (i - 1) + j + 1);
			wqu2.union(n * (i - 1) + j, n * (i - 1) + j + 1);
		}
		return;
	}

	public boolean isOpen(int i, int j) throws IndexOutOfBoundsException {
		if (i < 1 || i > n || j < 1 || j > n){
			throw new IndexOutOfBoundsException();
		}
		return open[n * (i - 1) + j];
	}

	public boolean isFull(int i, int j) throws IndexOutOfBoundsException{
		if (i < 1 || i > n || j < 1 || j > n){
			throw new IndexOutOfBoundsException();
		}
		if (!isOpen(i, j)) return false;
		return wqu2.find(n * (i - 1) + j) == wqu2.find(0);
	}

	public boolean percolates(){
		return wqu.find(n * n + 1) == wqu.find(0);
	}

	public static void main(String[] args){

	}
}