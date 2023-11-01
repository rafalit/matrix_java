import java.lang.Math;
import java.util.Random;

import static java.lang.Math.*;

public class Matrix {
    int cols;
    int rows;
    int max = 0;
    double[] data;

    Matrix(double[][] d) {

        for (int i = 0; i < d.length; i++) {
            if (max < d[i].length) {
                max = d[i].length;
            }
        }
        rows = d.length;
        cols = max;

        data = new double[rows * cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (j < d[i].length) {
                    data[i * cols + j] = d[i][j];
                } else {
                    data[i * cols + j] = 0;
                }
            }
        }

    }

    Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        if(rows<1 || cols<1){
            throw new IllegalArgumentException("niepoprawne wartosci wspolrzednych");
        }
        data = new double[rows * cols];

    }

    int getRows() {
        return rows;
    }

    int getCols() {
        return cols;
    }


    double[][] asArray() {
        double[][] d_array = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                d_array[i][j] = data[j + i * cols];
            }
        }
        return d_array;
    }

    double get(int r, int c) {
        if (r * c < 0 || r * c > data.length) {
            throw new IllegalArgumentException("niepoprawne wartosci wspolrzednych");
        }
        return data[r * cols + c];
    }

    void set(int r, int c, double value) {
        if (r < 0 || r >= rows || c < 0 || c >= cols) {
            throw new IllegalArgumentException("niepoprawne wartosci wspolrzednych");
        } else {
            data[r * cols + c] = value;
        }
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("[");
        for (int i = 0; i < rows; i++) {
            buf.append("[");
            for (int j = 0; j < cols; j++) {
                buf.append(data[i * cols + j]);
                if (j < cols - 1) {
                    buf.append(", ");
                }
            }
            if (i < rows - 1) {
                buf.append("],\n");
            } else {
                buf.append("]");
            }
        }
        buf.append("]");

        return buf.toString();
    }

    void reshape(int newRows, int newCols) {
        if (rows * cols != newRows * newCols)
            throw new RuntimeException(String.format("%d x %d matrix can't be reshaped to %d x %d", rows, cols, newRows, newCols));

        if (rows == newRows && cols == newCols) {
            return;
        }
        Matrix reshapedMatrix = new Matrix(newRows, newCols);

        for (int i = 0; i < newRows; i++) {
            for (int j = 0; j < newCols; j++) {
                int index = i * newCols + j;
                int row = index / cols;
                int col = index % cols;
                reshapedMatrix.set(i, j, get(row, col));
            }
        }
        this.rows = newRows;
        this.cols = newCols;
        this.data = reshapedMatrix.data;
    }

    int[] shape() {
        int[] ShapeArray = new int[2];
        ShapeArray[0] = getRows();
        ShapeArray[1] = getCols();
        return ShapeArray;
    }

    Matrix add(double w){
        double [][] dodawanie = new double [rows] [cols];
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                dodawanie[i][j] = data[i*cols + j] + w;
            }
        }
        Matrix result = new Matrix(dodawanie);
        return result;
    }

    Matrix add_m(Matrix m) {
        if (rows != m.getRows() || cols != m.getCols()) {
            throw new IllegalArgumentException("nie mozna dodawac niewymiarowych macierzy");
        } else {
            double[][] dodawanie = new double[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    dodawanie[i][j] = m.data[i * cols + j] + data[i * cols + j];
                }
            }
            Matrix result = new Matrix(dodawanie);
            return result;
        }

    }

    Matrix sub(double w){
        double [][] odejmowanie = new double [rows] [cols];
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                odejmowanie[i][j] = data[i*cols + j] - w;
            }
        }
        Matrix result = new Matrix(odejmowanie);
        return result;
    }
    Matrix sub_m(Matrix m) {
        if(rows!=m.getRows() || cols !=m.getCols()){
            throw new IllegalArgumentException("nie mozna odejmowac niewymiarowych macierzy");
        }
        else{
            double [][] odejmowanie = new double [rows][cols];
            for(int i=0;i<rows; i++){
                for(int j=0; j<cols; j++){
                    odejmowanie[i][j]=data[i*cols + j] - m.data[i * cols + j];
                }
            }
            Matrix result = new Matrix(odejmowanie);
            return result;
        }
    }

    Matrix mul(double w){
        double [][] mnozenie = new double [rows] [cols];
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                mnozenie[i][j] = data[i*cols + j] * w;
            }
        }
        Matrix result = new Matrix(mnozenie);
        return result;
    }

    Matrix mull(Matrix m) {
        if(rows!=m.getRows() || cols !=m.getCols()){
            throw new IllegalArgumentException("nie mozna mnozyc niewymiarowych macierzy");
        }
        else{
            double [][] mnozenie = new double [rows][cols];
            for(int i=0;i<rows; i++){
                for(int j=0; j<cols; j++){
                    mnozenie[i][j]=data[i*cols + j] * m.data[i * cols + j];
                }
            }
            Matrix result = new Matrix(mnozenie);
            return result;
        }
    }

    Matrix dot(Matrix m){
        if(cols != m.getRows()){
            throw new IllegalArgumentException("nie mozna mnozyc niewymiarowych macierzy");
        }
        else{
            double [][] mnozenie = new double [rows][cols];
            for(int i=0; i<rows; i++){
                for(int j=0; j<cols; j++){
                    double suma=0;
                    for(int k=0; k<rows; k++){
                        suma+=data[i*cols + k] * m.data[j+ k*cols];
                    }
                    mnozenie[i][j]=suma;
                }
            }
            Matrix result = new Matrix(mnozenie);
            return result;
        }
    }
    Matrix div(double w){
        double [][] dzielenie = new double [rows] [cols];
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                dzielenie[i][j] = data[i*cols + j] / w;
            }
        }
        Matrix result = new Matrix(dzielenie);
        return result;
    }
    Matrix divv(Matrix m) {
        if(rows!=m.getRows() || cols !=m.getCols()){
            throw new IllegalArgumentException("nie mozna dzielic niewymiarowych macierzy");
        }
        else{
            double [][] dzielenie = new double [rows][cols];
            for(int i=0;i<rows; i++){
                for(int j=0; j<cols; j++){
                    if(m.data[i * cols + j] == 0){
                        throw new IllegalArgumentException("nie mozna dzielic przez 0");
                    }
                    else{
                        dzielenie[i][j]=data[i*cols + j] / m.data[i * cols + j];}
                    }
            }
            Matrix result = new Matrix(dzielenie);
            return result;
        }
    }

    double frobenius(){
        int suma=0;
        for(int i=0; i<data.length; i++){
            suma+=pow(data[i], 2);
        }
        double result = sqrt(suma);
        return result;
    }

    public static Matrix random(int rows, int cols){
        Matrix m = new Matrix(rows,cols);
        Random r = new Random();
        m.set(0,0,r.nextDouble());
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                m.set(i, j, r.nextDouble());
            }
        }
        return m;
    }

    public static Matrix eye(int n){
        Matrix m = new Matrix(n,n);
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(i==j){
                    m.set(i, j, 1);
                }
            }
        }
        return m;
    }

    public double Gauss(){
        if(rows!=cols){
            throw new IllegalArgumentException("nie mozna liczyc wyznacznika macierzy niekwadratowej");
        }

        Matrix copy = new Matrix(this.asArray());

        double determinant = 1.0;

        for (int i = 0; i < copy.rows; i++) {
            if (copy.data[i * copy.cols + i] == 0.0) {
                throw new IllegalArgumentException("dzielenie przez 0, wyznacznik wynosi zero");
            }
            for (int j = i + 1; j < copy.rows; j++) {
                double factor = copy.data[j * copy.cols + i] / copy.data[i * copy.cols + i];
                for (int k = i; k < copy.cols; k++) {
                    copy.data[j * copy.cols + k] -= factor * copy.data[i * copy.cols + k];
                }
            }
            determinant *= copy.data[i * copy.cols + i];
        }

        return determinant;
    }

    public Matrix inv(){
        if (rows != cols) {
            throw new IllegalArgumentException("Nie można obliczyć odwrotności macierzy niekwadratowej");
        }
        Matrix extendedMatrix = new Matrix(this.asArray());

        Matrix identityMatrix = Matrix.eye(rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                extendedMatrix.set(i, j + cols, identityMatrix.get(i, j));
            }
        }

        for (int i = 0; i < rows; i++) {
            int maxRowIndex = i;
            double maxVal = Math.abs(extendedMatrix.get(i, i));

            for (int j = i + 1; j < rows; j++) {
                double currentVal = Math.abs(extendedMatrix.get(j, i));
                if (currentVal > maxVal) {
                    maxVal = currentVal;
                    maxRowIndex = j;
                }
            }

            extendedMatrix.swapRows(i, maxRowIndex);

            double pivot = extendedMatrix.get(i, i);

            if (pivot == 0) {
                throw new IllegalArgumentException("Macierz jest osobliwa");
            }

            for (int j = i; j < 2 * cols; j++) {
                extendedMatrix.set(i, j, extendedMatrix.get(i, j) / pivot);
            }

            for (int k = 0; k < rows; k++) {
                if (k != i) {
                    double factor = extendedMatrix.get(k, i);
                    for (int j = i; j < 2 * cols; j++) {
                        extendedMatrix.set(k, j, extendedMatrix.get(k, j) - factor * extendedMatrix.get(i, j));
                    }
                }
            }
        }

        Matrix inverseMatrix = new Matrix(rows, cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                inverseMatrix.set(i, j, extendedMatrix.get(i, j + cols));
            }
        }

        return inverseMatrix;
    }

    public void swapRows(int row1, int row2) {
        for (int j = 0; j < cols; j++) {
            double temp = data[row1 * cols + j];
            data[row1 * cols + j] = data[row2 * cols + j];
            data[row2 * cols + j] = temp;
        }
        if(row1>rows || row2>rows){
            throw new IllegalArgumentException("nie ma takiego rzedu");
        }
    }

    public double[] solveLinearEquation(double[] b) {
        if (rows != cols) {
            throw new IllegalArgumentException("Macierz musi byc kwadratowa.");
        }
        if (Gauss() == 0.0) {
            throw new IllegalArgumentException("Macierz jest osobliwa.");
        }
        if (rows != b.length) {
            throw new IllegalArgumentException("Macierz i wektor nie są wymiarowe.");
        }

        int n = rows;
        double[] x = new double[n];
        double[] y = new double[n];

        Matrix L = new Matrix(n, n);
        Matrix U = new Matrix(n, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    L.set(i, j, 1);
                    U.set(i, j, data[i * cols + j]);
                } else if (i < j) {
                    U.set(i, j, data[i * cols + j]);
                } else {
                    L.set(i, j, data[i * cols + j]);
                }
            }
        }

        for (int i = 0; i < n; i++) {
            double sum = 0;
            for (int j = 0; j < i; j++) {
                sum += L.get(i, j) * y[j];
            }
            y[i] = (b[i] - sum) / L.get(i, i);
        }

        for (int i = n - 1; i >= 0; i--) {
            double sum = 0;
            for (int j = i + 1; j < n; j++) {
                sum += U.get(i, j) * x[j];
            }
            x[i] = (y[i] - sum) / U.get(i, i);
        }

        return x;
    }


    public static void main(String[] args) {
        Matrix m = new Matrix(new double[][]{{1,2,3,4},{2,3,1,2},{1,1,1,-1},{1,0,-2,-6}});
        Matrix n= new Matrix(new double[][]{{1, 5, 7, 8}, {1, 2, 3}, {4, 5, 6}, {1, 3, 0, 0}});
        Matrix s= new Matrix(3, 1);
        System.out.println(s);
        System.out.println(random(3, 4));
        System.out.println(eye(6));



    }
}
