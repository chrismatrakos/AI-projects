import java.lang.*;
import java.util.Random;
import java.util.ArrayList;
enum Player {
	MAX,
	MIN,
}

class Utility {
	protected int Move;
	protected double Score;
	public Utility(int move, double score) {
		this.Move = move;
		this.Score = score;
	}
}

public class MiniMax {
	private static boolean maxAndMinChoiceTest = false;
	private static boolean possibleMovesTest = false;
	private static boolean pruningTest = false;
	private static boolean gameOverTest = false;
	private static boolean evaluationTest = true;
	private static boolean test = false;

	public static int run(int[][] state) { // returns a move

		// Call Search
		Utility res = search(3, Player.MAX, state, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
		//System.out.println(res.Move);
		return res.Move;

	}
	static boolean isGameOver(int[][] state) {
		if(Evaluation.checkForStreak(state, 1, 4) >= 1) {
			return true;
		} else if(Evaluation.checkForStreak(state, 2, 4) >= 1) {
			return true;
		} else {
			return false;
		}
	}
	static Utility search(int depth, Player player, int[][] state, double alpha, double beta) {
		// Generate Possible Moves
		ArrayList<Integer> possibleMoves = generateMoves(state);
		if(possibleMovesTest) {
			System.out.println(player.name()+" can go.. ");
			System.out.println(possibleMoves);
		}

		int playerId = (player.name().equals(Player.MAX)) ? 1 : 2;

		// Check for Game Over // Base Case
		if(possibleMoves.size() == 1 || depth == 0 || isGameOver(state)) {
			Utility res = new Utility(possibleMoves.get(0), Evaluation.value(state, playerId)); // Returns UTILITY
			if(evaluationTest) {
				System.out.println("_______"+player.name()+"________");
				System.out.println("");
				System.out.println("Potential State:");
				printState(state);
				System.out.println("Value: "+res.Score);
				System.out.println("__________________");
			}
			return res;
		}

		// Who is playing?
		if(player.equals(Player.MAX)) {
			//System.out.println("MAX's Choices");

			Utility best = new Utility(-1, Double.NEGATIVE_INFINITY);
			Utility newBest;

			if(maxAndMinChoiceTest) {
				System.out.println("=======================~MAX~==========================");
			}
			// TESTING ARRAY
			ArrayList<Utility> options = new ArrayList<Utility>();

			// Loop through Moves
			for(int i : possibleMoves) {
				newBest = new Utility(i, Math.max(best.Score, search(depth-1, Player.MIN, updateState(state, Player.MIN, i), alpha, beta).Score));

				if(maxAndMinChoiceTest) {
					options.add(newBest);
				}

				if(best.Score < newBest.Score) {
					best = newBest;
				}
				// Pruning!
				//if(best.Score > alpha) alpha = best.Score;
				//if(alpha >= beta) {
					//if(pruningTest)	System.out.println("Beta Cut off done");
					//break; // Doing a Cut
				//}
			}
			if(maxAndMinChoiceTest) {
				for(Utility u : options) {
					System.out.println("Choice: "+u.Score+" with move: "+u.Move);
				}
				System.out.println("MAX chose: "+best.Score+" - move: "+best.Move);
				System.out.println("---------------------------------------------");
			}

			return best;

		} else { // Player == Player.MIN
			//System.out.println("MIN's Choices");
			Utility best = new Utility(-1, Double.POSITIVE_INFINITY);
			Utility newBest;


			if(maxAndMinChoiceTest) {
				System.out.println("=======================~MIN~==========================");
			}

			// TESTING ARRAY
			ArrayList<Utility> options = new ArrayList<Utility>();

			// Loop through Moves
			for(int i : possibleMoves) {
				newBest = new Utility(i, Math.min(best.Score, search(depth-1, Player.MAX, updateState(state, Player.MAX, i), alpha, beta).Score));
				// TEST
				if(maxAndMinChoiceTest) {
					options.add(newBest);
				}

				if(best.Score > newBest.Score) {
					best = newBest;
				}
				// Pruning!
				//if(best.Score < beta) beta = best.Score;
				//if(alpha >= beta) {
					//if(pruningTest) System.out.println("Alpha Cut off done");
					//break; // Doing a Cut
				//}
			}
			if(maxAndMinChoiceTest) {
				for(Utility u : options) {
					System.out.println("Choice: "+u.Score+" with move: "+u.Move);
				}
				System.out.println("MIN chose: "+best.Score+" - move: "+best.Move);
				System.out.println("---------------------------------------------");
			}

			return best;

		}

	}

	static int[][] updateState(int[][] state, Player player, int col) {
		int p = (player.equals(Player.MAX)) ? 1 : 2;
		int[][] newState = copyMatrix(state);
		newState[whichRow(col, state)][col] = p;

		// TESTING
		//printState(newState);

		return newState;
	}

	static int[][] copyMatrix(int[][] matrix) {
		int [][] copy = new int[matrix.length][];
		for(int i = 0; i < matrix.length; i++)
		{
		  int[] aMatrix = matrix[i];
		  int   aLength = aMatrix.length;
		  copy[i] = new int[aLength];
		  System.arraycopy(aMatrix, 0, copy[i], 0, aLength);
		}
		return copy;
	}

	private static int whichRow(int col, int[][] state) {
		// Checking for filled out spaces in given column 
		int row = state.length-1;
		for(int i = row; i >= 0; i--) {
			if(state[i][col] == 0) {
				row = i;
				break;
			}
		}
		return row;

	}

	public static ArrayList<Integer> generateMoves(int[][] state) {
		ArrayList<Integer> possibleMoves = new ArrayList<Integer>();

		// Look for empty cells
		for(int col = 0; col < state[0].length; col++) {
			if(state[0][col] == 0) { // column is not yet full! 
				possibleMoves.add(col);
				//if(test) System.out.println("col "+col+" is valid");
			}
		}

		return possibleMoves;
	}

	static void printState(int[][] state) {
		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state[0].length; j++) {
				System.out.print(state[i][j] + " ");
			}
			System.out.print("\n");
		}
	}
}
