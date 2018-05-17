public class Fib {

	public static void main(String[] args) {

		int[] f = new int[100];

		for (int i = 0; i < 45; i++) {
			f[i] = getFib(i);
			System.out.print(f[i] + " ");
			if (i % 15 == 0)
				System.out.println();
		}
		boolean flag = true;
		for (int i = 2; i < 45; i++) {
			if (f[i] != f[i - 1] + f[i - 2]) {
				flag = false;
				break;
			}
		}
		System.out.println(flag);

		// System.out.println(getFib(1));
		// Mat m1 = new Mat(new int[][] { { 1, 1, 1 }, { 0, 1, 0 }, { 0, 0, 1 }
		// });
		// System.out.println(m1);
		// m1.View();
		// Mat m2 = new Mat(new int[][] { { 1, 0, 0 }, { 0, 1, 0 }, { 0, 0, 1 }
		// });
		// System.out.println(m2);
		// m2.View();
		// Mat m3 = Mat.MatPow(m1, 3);
		// System.out.println(m3);
		// m3.View();

	}

	private static int getFib(int n) {

		if (n == 0)
			return 0;
		else if (n == 1)
			return 1;

		Mat fir = new Mat(new int[][] { { 1, 1 }, { 1, 0 } });
		// fir.View();
		Mat sec = new Mat(new int[][] { { 1 }, { 0 } });
		// sec.View();

		Mat thi = Mat.MatPow(fir, n - 1);
		// thi.View();

		Mat ans = Mat.Mul(thi, sec);
		// ans.View();

		return ans.getMat(0, 0);
	}

}

class Mat {
	private int row;
	private int col;
	private int[][] mat;

	public Mat(int row, int col) {
		super();
		this.row = row;
		this.col = col;
		this.mat = new int[row][col];
	}

	public Mat(int[][] e) {

		this.row = e.length;

		this.col = e[0].length;

		mat = new int[this.row][this.col];

		for (int i = 0; i < this.row; i++) {
			for (int j = 0; j < this.col; j++) {
				this.mat[i][j] = e[i][j];
			}
		}
	}

	public static Mat Mul(Mat fir, Mat sec) {
		int Row = fir.getRow(), Col = sec.getCol();
		Mat ans = new Mat(Row, Col);
		for (int i = 0; i < Row; i++) {
			for (int j = 0; j < Col; j++) {
				int e = 0;
				for (int k = 0; k < fir.getCol(); k++) {
					e += fir.getMat(i, k) * sec.getMat(k, j);
				}
				ans.setMat(i, j, e);
			}
		}

		return ans;
	}

	public static Mat IdeMat(int n) {
		Mat ans = new Mat(n, n);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i == j)
					ans.setMat(i, j, 1);
				else
					ans.setMat(i, j, 0);
			}
		}
		return ans;
	}

	public void View() {
		System.out.println(this);
		for (int i = 0; i < this.row; i++) {
			for (int j = 0; j < this.col; j++) {
				System.out.print(this.mat[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public static Mat MatPow(Mat e, int n) {
		if (e.getRow() != e.getCol())
			throw new RuntimeException("can't be pow...");
		if (n < 1)
			throw new RuntimeException("n can't be negative number...");

		Mat ans = IdeMat(e.col);

		while (n != 1) {
			if ((n & 1) != 0) {
				ans = Mat.Mul(ans, e);
				n--;
			} else {
				e = Mul(e, e);
				n /= 2;
			}
		}
		return Mat.Mul(ans, e);
	}

	public int getMat(int r, int c) {
		if (r < 0 || r >= this.row || c < 0 || c >= this.col)
			throw new RuntimeException("out of range!");
		return this.mat[r][c];
	}

	public void setMat(int r, int c, int e) {
		if (r < 0 || r >= row || c < 0 || c >= col)
			throw new RuntimeException("out of range!");
		this.mat[r][c] = e;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

}
