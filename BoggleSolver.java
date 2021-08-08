/* *****************************************************************************
 *  Name: Nicholas Yap
 *  Date: 08/08/2021
 *  Description: Boggle Solver. Score 86/100.
 **************************************************************************** */


import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.TST;

import java.util.LinkedList;

public class BoggleSolver {
    private boolean[][] marked;
    private String pathString;
    private TST<Integer> tst;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        tst = new TST<>();
        for (String s : dictionary) {
            tst.put(s, 1);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        SET<String> words = new SET<>();

        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                marked = new boolean[board.rows()][board.cols()];
                pathString = "";
                int[] root = { i, j };
                dfs(root, words, board);
            }
        }

        return words;
    }

    private void dfs(int[] pos, SET<String> words, BoggleBoard board) {
        marked[pos[0]][pos[1]] = true;
        char letter = board.getLetter(pos[0], pos[1]);
        if (letter == 'Q') pathString += "QU";
        else pathString += letter;
        if (pathString.length() >= 3 && tst.get(pathString) != null) {
            words.add(pathString);
        }

        Iterable<int[]> adjacent = adjacent(pos, board.rows(), board.cols());
        for (int[] adj : adjacent) {
            if (marked[adj[0]][adj[1]]) continue;

            String next = pathString + board.getLetter(adj[0], adj[1]);
            if (!tst.keysWithPrefix(next).iterator().hasNext()) continue;

            dfs(adj, words, board);

        }

        marked[pos[0]][pos[1]] = false;
        if (letter == 'Q') pathString = pathString.substring(0, pathString.length() - 2);
        else pathString = pathString.substring(0, pathString.length() - 1);
    }

    private Iterable<int[]> adjacent(int[] pos, int rows, int cols) {
        LinkedList<int[]> adj = new LinkedList<>();
        for (int i = pos[0] - 1; i <= pos[0] + 1; i++) {
            for (int j = pos[1] - 1; j <= pos[1] + 1; j++) {
                if (i < 0 || i >= rows || j < 0 || j >= cols) continue;
                if (i == pos[0] && j == pos[1]) continue;
                int[] dice = { i, j };
                adj.add(dice);
            }
        }
        return adj;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (tst.get(word) == 1) return word.length();
        return 0;
    }

    public static void main(String[] args) {

    }
}
