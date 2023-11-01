import static org.junit.Assert.*;

public class MatrixTest {

    @org.junit.Test
    public void Matrix(){
        Matrix ma= new Matrix(3,1);
        int rows= ma.getRows();
        int cols = ma.getCols();
        double delta =0.001;
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                assertEquals(0, ma.get(i, j), delta);
            }
        }
        assertEquals(3, rows);
        assertEquals(cols, 1);

        assertThrows(IllegalArgumentException.class, ()->new Matrix (3, -1));

    }

    @org.junit.Test
    public void getRows() {
        Matrix matrix = new Matrix(new double [][]{{1,2,3},{1,2,3},{1,0,5}});
        int rows=matrix.getRows();
        int expectedRows=3;
        assertEquals(expectedRows, rows);

    }

    @org.junit.Test
    public void getCols() {
        Matrix matrix= new Matrix (new double [][]{{1,2,3,4}, {1,2,3,4}, {1,2}, {1,2,3}});
        int cols = matrix.getCols();
        int expectedCols=4;
        assertEquals(expectedCols, cols);
    }

    @org.junit.Test
    public void asArray() {
        double [][] irr = {{1,2,3,4}, {1,2,3,4}, {1,2,3}, {1,1,1}};
        Matrix matrix = new Matrix(irr);
        int expectedRows=4;
        int expectedCols=4;

        double[][] result = matrix.asArray();
        assertEquals(expectedRows, result.length);
        for(int i=0; i<result.length; i++){
            assertEquals(expectedCols, result[i].length);
        }
    }

    @org.junit.Test
    public void get() {
        Matrix matrix = new Matrix(new double[][]{{1,2,3,4,5}, {1,2,3}, {1,2,3,9}, {1,1,1}, {0,1,2}});
        double result = matrix.get(4, 4);
        double expectedValue=0;
        double delta = 0.01;
        assertEquals(expectedValue, result, delta);

        assertThrows(IllegalArgumentException.class, ()-> matrix.get(7, 7));
    }

    @org.junit.Test
    public void set() {
        Matrix matrix = new Matrix(new double [][] {{1,2,3,4},
                                                    {1,2,3,5},
                                                    {0,1,2,1},
                                                    {1,2,3}});
        matrix.set(1, 3, 8);
        double expectedValue = 8;
        double result = matrix.get(1, 3);
        double delta = 0.01;
        assertEquals(expectedValue, result, delta);
        assertThrows(IllegalArgumentException.class, ()-> matrix.set(7, 7, 1));
    }

    @org.junit.Test
    public void testToString() {
        Matrix matrix = new Matrix(new double[][]{{1.0, 2.3, 4.56}, {12.3, 45, 21.8}});
        String expectedString = "[[1.0, 2.3, 4.56],\n[12.3, 45.0, 21.8]]";
        String actualString = matrix.toString();
        System.out.println(actualString);

        assertEquals(expectedString, actualString);
    }

    @org.junit.Test
    public void reshape() {
        Matrix matrix = new Matrix(new double[][]{{1, 2, 3}, {4, 5, 6}});
        matrix.reshape(3, 2);
        assertEquals(3, matrix.getRows());
        assertEquals(2, matrix.getCols());

        double[][] expectedArray = new double[][]{{1, 2}, {3, 4}, {5, 6}};
        double[][] actualArray = matrix.asArray();

        assertEquals(expectedArray, actualArray);
        assertThrows(RuntimeException.class, ()-> matrix.reshape(4,1));

    }

    @org.junit.Test
    public void shape() {
        Matrix matrix = new Matrix(new double[][]{{1,2,3,4}, {1,1,1,1}, {0,0,1}, {1,2,3}});
        int expectedRows=4;
        int expectedCols=4;
        int [] tab = matrix.shape();
        assertEquals(expectedRows, tab[0]);
        assertEquals(expectedCols, tab[1]);
    }

    @org.junit.Test
    public void add() {
        Matrix matrix = new Matrix(new double[][]{{1,2,3,4}, {1,2,3,4}, {1,1,1}, {0,1,2}});
        double var =5;
        double delta = 0.001;

        Matrix result = matrix.add(var);
        Matrix exp = new Matrix(new double[][]{{6,7,8,9}, {6,7,8,9}, {6,6,6,5}, {5,6,7,5}});
        for (int i = 0; i < matrix.getRows(); i++) {
            for(int j=0; j<matrix.getCols(); j++){
                assertEquals(exp.get(i, j), result.get(i, j), delta);
            }
        }

    }

    @org.junit.Test
    public void add_m() {
        Matrix matrix = new Matrix(new double[][]{{1, 2, 3}, {1, 2, 3}, {1, 2, 3}});
        Matrix other = new Matrix(new double[][]{{1, 2, 3}, {1, 2, 3}, {1, 2, 3}});
        Matrix m = new Matrix(new double[][]{{1,2,3}, {1}});
        double[][] expected = new double[][]{{2, 4, 6}, {2, 4, 6}, {2, 4, 6}};

        Matrix result = matrix.add_m(other);

        assertArrayEquals(expected, result.asArray());
        assertThrows(IllegalArgumentException.class, ()-> matrix.add_m(m));
    }

    @org.junit.Test
    public void sub() {
        Matrix matrix = new Matrix(new double[][]{{1,2,3,4}, {1,2,3,4}, {1,1,1}, {0,1,2}});
        double var = 3;
        double delta = 0.001;

        Matrix result = matrix.sub(var);
        Matrix exp = new Matrix(new double[][]{{-2,-1,0,1}, {-2,-1,0,1}, {-2,-2,-2,-3}, {-3,-2,-1,-3}});
        for (int i = 0; i < matrix.getRows(); i++) {
            for(int j=0; j<matrix.getCols(); j++){
                assertEquals(exp.get(i, j), result.get(i, j), delta);
            }
        }
    }

    @org.junit.Test
    public void sub_m() {
        Matrix matrix = new Matrix(new double[][]{{1, 2, 3}, {1, 2, 3}, {1, 2, 3}});
        Matrix other = new Matrix(new double[][]{{1, 2, 3}, {1, 2, 3}, {1, 1, 3}});
        Matrix m = new Matrix(new double[][]{{1,2,3},{1}});
        double[][] expected = new double[][]{{0, 0, 0}, {0, 0, 0}, {0, 1, 0}};

        Matrix result = matrix.sub_m(other);

        assertArrayEquals(expected, result.asArray());

        assertThrows(IllegalArgumentException.class, ()-> matrix.add_m(m));
    }

    @org.junit.Test
    public void mul() {
        Matrix matrix = new Matrix(new double[][]{{1,2,3,4}, {1,2,3,4}, {1,1,1}, {0,1,2}});
        double var = 3;
        double delta = 0.001;


        Matrix result = matrix.mul(var);
        Matrix exp = new Matrix(new double[][]{{3,6,9,12}, {3,6,9,12}, {3,3,3,0}, {0,3,6,0}});
        for (int i = 0; i < matrix.getRows(); i++) {
            for(int j=0; j<matrix.getCols(); j++){
                assertEquals(exp.get(i, j), result.get(i, j), delta);
            }
        }
    }

    @org.junit.Test
    public void mull(){
        Matrix matrix_1 = new Matrix(new double[][]{{1,2,3}, {1,2,3}, {3,4,5}});
        Matrix matrix_2 = new Matrix(new double[][]{{0,1,2},{1,2,3}, {3,3,3}});
        Matrix result=matrix_1.mull(matrix_2);

        double [][]expected = new double[][]{{0,2,6}, {1,4,9}, {9,12,15}};
        assertEquals(expected, result.asArray());

        Matrix m_1 = new Matrix(new double[][]{{1,1,1},{2}, {2}});
        Matrix m_2 = new Matrix(new double[][]{{1,1,1},{1,2}});
        assertThrows(IllegalArgumentException.class, ()->m_1.mull(m_2));
    }

    @org.junit.Test
    public void dot() {
        Matrix matrix_1 = new Matrix(new double[][]{{1,2,3,4}, {1,2,3,3}, {3,4,5,6}, {9,8}});
        Matrix matrix_2 = new Matrix(new double[][]{{0,1,2},{1,2,3,4}, {3,3,3},{1}});

        Matrix result=matrix_1.dot(matrix_2);
        double [][]expected = new double[][]{{15,14,17,8},{14,14,17,8},{25,26,33,16},{8,25,42,32}};

        assertArrayEquals(expected, result.asArray());

        Matrix m_1 = new Matrix(new double[][]{{1,2,3},{1}, {2}});
        Matrix m_2 = new Matrix(new double[][]{{1,1,1},{1,2}});

        assertThrows(IllegalArgumentException.class, ()->m_1.dot(m_2));
    }

    @org.junit.Test
    public void div() {
        Matrix matrix = new Matrix(new double[][]{{3,6,9,6}, {1,2,3,4}, {1,1,1}, {0,1,2}});
        double var = 3;
        double delta = 0.001;


        Matrix result = matrix.div(var);
        Matrix exp = new Matrix(new double[][]{{1,2,3,2}, {0.333,0.666,1,1.333}, {0.333,0.333,0.333,0}, {0, 0.333,0.666,0}});
        for (int i = 0; i < matrix.getRows(); i++) {
            for(int j=0; j<matrix.getCols(); j++){
                assertEquals(exp.get(i, j), result.get(i, j), delta);
            }
        }
    }

    @org.junit.Test
    public void divv(){
        Matrix matrix = new Matrix(new double[][]{{1,2,3}, {1,1,1}, {1,2,3}});
        Matrix m_1 = new Matrix(new double[][]{{1,2,1},{1,2,1}, {1,1,1}});
        Matrix m_2 = new Matrix(new double[][]{{1,1,1},{1,2}});
        Matrix m_3 = new Matrix(new double[][]{{0,0,1}, {1,2,3}, {1,1,1}});

        Matrix result = matrix.divv(m_1);

        double delta = 0.001;
        Matrix exp = new Matrix(new double[][]{{1,1,3}, {1,0.5,1}, {1,2,3}});

        for (int i = 0; i < matrix.getRows(); i++) {
            for(int j=0; j<matrix.getCols(); j++){
                assertEquals(exp.get(i, j), result.get(i, j), delta);
            }
        }

        assertThrows(IllegalArgumentException.class, ()->m_1.divv(m_2));
        assertThrows(IllegalArgumentException.class, ()->matrix.divv(m_3));
    }


    @org.junit.Test
    public void frobenius() {
        Matrix matrix = new Matrix(new double[][]{{1,2,3}, {1,1,1}, {1}});
        double expected = 4.2426;
        double result = matrix.frobenius();
        double delta = 0.001;
        assertEquals(expected, result, delta);
    }

    @org.junit.Test
    public void random() {
        Matrix matrix = new Matrix(4, 5);
        int exp_r=4;
        int exp_c=5;
        int res_r=matrix.getRows();
        int res_c=matrix.getCols();
        assertEquals(exp_r, res_r);
        assertEquals(exp_c, res_c);
    }

    @org.junit.Test
    public void eye() {
        int n = 3;
        Matrix eyeMatrix = Matrix.eye(n);
        assertEquals(n, eyeMatrix.getRows());
        assertEquals(n, eyeMatrix.getCols());

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    assertEquals(1.0, eyeMatrix.get(i, j), 0.001);
                } else {
                    assertEquals(0.0, eyeMatrix.get(i, j), 0.001);
                }
            }
        }
    }

    @org.junit.Test
    public void gauss() {
        double[][] data = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {1}};
        Matrix matrix = new Matrix(data);
        assertThrows(IllegalArgumentException.class, ()->matrix.Gauss()); ///niekwadratowa

        double[][] da = {{1, 2, 3}, {2, 4, 6}, {3, 6, 9}};
        Matrix mat = new Matrix(da);
        assertThrows(IllegalArgumentException.class, () -> mat.Gauss()); ///wyznacznik  rowny 0

        Matrix identityMatrix = Matrix.eye(3);
        double result = identityMatrix.Gauss();
        double expectedDeterminant = 1.0;
        assertEquals(expectedDeterminant, result, 0.001);


    }

    @org.junit.Test
    public void inv() {
        double[][] da = {{1, 2, 3}, {2, 4, 6}, {3, 6, 9}};
        Matrix mat = new Matrix(da);
        assertThrows(IllegalArgumentException.class, () -> mat.inv());

        double[][] d = {{1, 2, 3}, {4, 5, 6}};
        Matrix m = new Matrix(d);
        assertThrows(IllegalArgumentException.class, () -> m.inv());

    }

    @org.junit.Test
    public void swapRows() {
        double[][] data = {{1, 2, 3}, {4, 5, 6}, {7, 8, 10}};
        Matrix matrix = new Matrix(data);
        double[][] expectedData = {{4, 5, 6}, {1, 2, 3}, {7, 8, 10}};
        matrix.swapRows(0, 1);
        assertArrayEquals(expectedData, matrix.asArray());
    }

    @org.junit.Test
    public void solveLinearEquation(){
        double[][] coefficients = {{2, -1, 1}, {1, 1, -1}, {3, -2, 1}};
        double[] constants = {1, 2, 3};
        Matrix coefficientMatrix = new Matrix(coefficients);
        double[] solution = coefficientMatrix.solveLinearEquation(constants);
        double[] expectedSolution = {1.0, 3.0, 2.0};
        assertArrayEquals(expectedSolution, solution, 1e-6);

        double [][]coeff = {{1,1,1}, {1,2}};
        Matrix c = new Matrix(coeff);
        assertThrows(IllegalArgumentException.class, ()->c.solveLinearEquation(constants));
    }

}