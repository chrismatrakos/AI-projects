import java.util.ArrayList;
public class Group1 implements IGameLogic {
    private int x = 0;
    private int y = 0;
    private int playerID;
    private int[][] gameBoard;
    private static boolean test = false;

    public Group1() {
        //TODO Write your implementation for this method
    }

    public void initializeGame(int x, int y, int playerID) {
        this.x = x;
        this.y = y;
        this.playerID = playerID;
        // Make a fresh board - Initialized to have zero's
        this.gameBoard = new int[y][x];

    }

    public Winner gameFinished() {
			ArrayList<Integer> ms = MiniMax.generateMoves(gameBoard);
			if(ms.size() < 1) {
				System.out.println("TIE!");
				return Winner.TIE;
			} else if(Evaluation.checkForStreak(gameBoard, 1, 4) >= 1) {
				System.out.print("p1 WON");
				return Winner.PLAYER1;
			} else if(Evaluation.checkForStreak(gameBoard, 2, 4) >= 1) {
				System.out.print("p2 WON");
				return Winner.PLAYER2;
			} else {
				return Winner.NOT_FINISHED;
			}
    }

    public void insertCoin(int column, int playerID) {
			this.gameBoard[whichRow(column)][column] = playerID;

			// JUST PRINTING OUT THE MATRIX FOR DEBUGGING
			if(test){
				for (int i = 0; i < gameBoard.length; i++) {
					for (int j = 0; j < gameBoard[0].length; j++) {
						System.out.print(gameBoard[i][j] + " ");
					}
					System.out.print("\n");
				}
			}
    }
		private int whichRow(int col) {
			
			MiniMax.printState(this.gameBoard);
			System.out.println(col);
			System.out.println(gameBoard.length + " " + gameBoard[0].length);
			col = col--;
			// Checking for filled out spaces in given column 
			int row = this.gameBoard.length-1;
			System.out.println(row);
			for(int i = row; i >= 0; i--) {
				if(this.gameBoard[i][col] == 0) {
					row = i;
					break;
				}
			}
			return row;

		}

    public int decideNextMove() {
			//System.out.println("Decision Made, defenders first");
			// Is this the first move?
			if(boardEmpty(gameBoard)) {
				if(test) System.out.println("First Move!");
				return (gameBoard[0].length)/2;
			} else {
				int m = MiniMax.run(this.gameBoard);
				return m;
			}
    }

		static boolean boardEmpty(int[][] state) {
			boolean res = true;
			for(int i = 0; i < state[0].length; i++) {
				if(state[state.length-1][i] != 0)
					res = false;
			}
			return res;
		}

    public boolean terminalTest(int[][] s) {
			return false;
    }

}
