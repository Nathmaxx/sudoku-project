package test.java.Model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SudokuTest {

    @Test
    public void testConstructorWithBoard() {
        int[][] board = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        Sudoku sudoku = new Sudoku(board);
        assertArrayEquals(board, sudoku.getBoard());
    }

    @Test
    public void testConstructorWithSize() {
        int size = 4;
        Sudoku sudoku = new Sudoku(size);
        assertEquals(size, sudoku.getSize());
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                assertEquals(0, sudoku.get(i, j));
            }
        }
    }

    @Test
    public void testCopyConstructor() {
        int[][] board = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        Sudoku original = new Sudoku(board);
        Sudoku copy = new Sudoku(original);
        assertArrayEquals(board, copy.getBoard());
    }

    @Test
    public void testSetAndGet() {
        int size = 3;
        Sudoku sudoku = new Sudoku(size);
        sudoku.set(1, 1, 5);
        assertEquals(5, sudoku.get(1, 1));
    }

    @Test
    public void testToString() {
        int[][] board = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        Sudoku sudoku = new Sudoku(board);
        String expected = "1 2 3 \n4 5 6 \n7 8 9 \n";
        assertEquals(expected, sudoku.toString());
    }

    @Test
    public void testAfficher() {
        int[][] board = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        Sudoku sudoku = new Sudoku(board);
        String expected =
            "------\n" +
            "|1 2 3 |\n" +
            "|4 5 6 |\n" +
            "|7 8 9 |\n" +
            "------\n";
        assertEquals(expected, sudoku.afficher());
    }
}