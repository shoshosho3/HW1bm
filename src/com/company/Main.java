package com.company;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static Scanner scanner;  // Note: Do not change this line.
    final static int MAX_SEMESTERS = 1000;


    public static void main(String[] args) throws IOException {
        String path = args[0];

        scanner = new Scanner(new File(path));
        int numberOfGames = scanner.nextInt();
        scanner.nextLine();

        for (int i = 1; i <= numberOfGames; i++) {
            System.out.println("Game number " + i + " starts.");
            theStudentsGame();
            System.out.println("Game number " + i + " ended.");
            System.out.println("-----------------------------------------------");
        }
        System.out.println("All games are ended.");
    }

    /**
     * The function activates the student game
     */
    static void theStudentsGame() {

        boolean[][] studentMatrix = boardSizeInput();
        initMatrix(studentMatrix);
        boolean didChange = runSemesters(studentMatrix);
        printEndMessages(didChange, studentMatrix);

    }

    /**
     * The function gets input of board size and creates new board
     *
     * @return pointer of new board defined by board size
     */
    static boolean[][] boardSizeInput() {

        System.out.println("Dear president, please enter the board’s size.");
        int rows = scanner.nextInt();
        scanner.next();
        int columns = scanner.nextInt();
        scanner.nextLine();

        return new boolean[rows][columns];
    }

    /**
     * The function initializes student matrix according to input
     *
     * @param studentMatrix matrix of students to be filled
     */
    static void initMatrix(boolean[][] studentMatrix) {

        System.out.println("Dear president, please enter the cell’s indexes.");
        String indexes = scanner.nextLine();

        while (!indexes.equals("Yokra")) {
            fillStudent(indexes, studentMatrix);
            indexes = scanner.nextLine();
        }

    }

    /**
     * @param indexes       string of indexes of tab to be filled if indexes in matrix
     * @param studentMatrix boolean matrix of students, tab of indexes will become true if in matrix
     */
    static void fillStudent(String indexes, boolean[][] studentMatrix) {

        int commaIndex = indexes.indexOf(",");
        int row = Integer.parseInt(indexes.substring(0, commaIndex));
        int column = Integer.parseInt(indexes.substring((commaIndex + 2)));

        if (inMatrix(studentMatrix, row, column)) {
            studentMatrix[row][column] = true;
            System.out.println("Dear president, please enter the cell’s indexes.");
        } else {
            System.out.println("The cell is not within the board’s boundaries, enter a new cell.");
        }

    }

    /**
     * The function checks if row and column are in matrix bounds
     *
     * @param matrix matrix being tested
     * @param row    row received in input
     * @param column column received in input
     * @return true if tab in row and column is in matrix bounds, otherwise false
     */
    static boolean inMatrix(boolean[][] matrix, int row, int column) {

        return row >= 0 && row < matrix.length
                && column >= 0 && column < matrix[0].length;
    }

    /**
     * The function runs the semesters-
     * prints each semester and moves to the next
     *
     * @param studentMatrix matrix of students, each tab true if student valid
     * @return true if last semester was changes, otherwise false
     */
    static boolean runSemesters(boolean[][] studentMatrix) {

        boolean didChange = true;
        int semester = 1;

        while (didChange && semester <= MAX_SEMESTERS) {
            System.out.println("Semester number " + semester + ":");
            printMatrix(studentMatrix);
            System.out.println("Number of students: " + validNumber(studentMatrix) + "\n");
            didChange = updateSemester(studentMatrix);
            semester++;
        }

        return didChange;
    }

    /**
     * The function prints the matrix:
     * true symbolized by ▮
     * false symbolized by ▯
     *
     * @param matrix matrix being printed
     */
    static void printMatrix(boolean[][] matrix) {

        for(boolean[] column:matrix){
            for(boolean tab:column){
                System.out.print(tab ? "▮" : "▯");
            }
            System.out.println();
        }

    }

    /**
     * This function counts number of valid students(true tabs)
     *
     * @param studentMatrix matrix of students,
     *                      each tab true if student valid, false if not
     * @return number of valid students(true tabs)
     */
    static int validNumber(boolean[][] studentMatrix) {

        int valid = 0;

        for(boolean[] column:studentMatrix){
            for(boolean tab:column){
                valid += tab ? 1 : 0;
            }
        }

        return valid;
    }

    /**
     * The function updates the semester to next semester
     *
     * @param studentMatrix matrix of student- each tab true if student valid
     * @return true if changed studentMatrix, otherwise false
     */
    static boolean updateSemester(boolean[][] studentMatrix) {

        int rows = studentMatrix.length;
        int columns = studentMatrix[0].length;
        boolean didChange = false;
        boolean[][] newSemester = new boolean[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int k = 0; k < columns; k++) {
                newSemester[i][k] = isValid(studentMatrix, i, k);
                if (newSemester[i][k] != studentMatrix[i][k])
                    didChange = true;
            }
        }

        if (!didChange) return didChange;

        copy(newSemester, studentMatrix);

        return didChange;
    }

    /**
     * The function checks if given student will is valid in new semester
     *
     * @param studentMatrix matrix of students, each tab true if student is valid
     * @param row           row of student being tested
     * @param column        column of student being tested
     * @return true if student valid in new semester, otherwise false
     */
    static boolean isValid(boolean[][] studentMatrix, int row, int column) {

        int validFriends = 0;

        for (int i = row - 1; i <= row + 1; i++) {
            for (int k = column - 1; k <= column + 1; k++) {
                if ((i == row && k == column) || !inMatrix(studentMatrix, i, k)) {
                    continue;
                }
                if (studentMatrix[i][k]) {
                    validFriends++;
                }
            }
        }

        if (studentMatrix[row][column]) {
            return validFriends == 2 || validFriends == 3;
        }

        return validFriends == 3;
    }

    /**
     * The function copies source matrix to destination
     *
     * @param source      source matrix copied from
     * @param destination destination matrix copied into
     */
    static void copy(boolean[][] source, boolean[][] destination) {

        for (int i = 0; i < source.length; i++) {
            for (int k = 0; k < source[0].length; k++) {
                destination[i][k] = source[i][k];
            }
        }

    }

    /**
     * The function prints end message
     *
     * @param didChange     true if matrix changed in last semester, otherwise false
     * @param studentMatrix matrix of students, each tab true if student valid
     */
    static void printEndMessages(boolean didChange, boolean[][] studentMatrix) {

        if (validNumber(studentMatrix) == 0) {
            System.out.println("There are no more students.");
        } else if (!didChange) {
            System.out.println("The students have stabilized.");
        } else {
            System.out.println("The semesters limitation is over.");
        }

    }


}
