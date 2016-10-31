/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author chm
 */
public  class Evaluation {
    
    private static int col;
    private static int row;
    private static int myplayer;
    private static int opp; //opponent player
    /*
     Simple heuristic to evaluate board configurations
     Heuristic is (num of 4-in-a-rows)*99999 + (num of 3-in-a-rows)*100 + 
     (num of 2-in-a-rows)*10 - (num of opponent 4-in-a-rows)*99999 - (num of opponent
     3-in-a-rows)*100 - (num of opponent 2-in-a-rows)*10
    */
   public static int value(int[][]board, int player)
    {
    	  //System.out.println("Player passed was: "+player);
        myplayer=player;
        if(myplayer==1)
        {
            opp=2;
        }
        else{   
        	opp=1; 
        }
		
				
        int my_fours  = checkForStreak(board, myplayer, 4);//check for a 4 streak win
        int my_threes = checkForStreak(board, myplayer , 3);//check for a 3 streak
        int my_twos   = checkForStreak(board, myplayer, 2);//check for a 2 streak
        int opp_fours = checkForStreak(board, opp, 4);//check for an opponent win 4 streak 
        int opp_threes = checkForStreak(board, opp, 3);
        int opp_twos = checkForStreak(board, opp, 2);

        //System.out.println("my fours: " + my_fours + "\n" + "threes " + my_threes + "\n" + "twos " + my_twos);
        //System.out.println("opponent fours: " + opp_fours + "\n" + "threes " + opp_threes + "\n" + "twos " + opp_twos);
				if (my_fours > 0) {
					//System.out.println("My fours was over 0: " + my_fours); 
				}
				if (opp_fours > 0) {
					System.out.println("opponent mythrees was over 0: " + opp_fours); 
					return -100000;
				}
				else{
					return (my_fours*100000 + my_threes*100 + my_twos)-(opp_fours*100000 + opp_threes*100 + opp_twos);
				}
		}
   public static int checkForStreak(int[][]board,int playerValue, int streak)
   {


       //check for each piece in the board
       /*row=board.length;
       col=board[0].length;
       System.out.println(row + "_"+col);
       return 0;*/
       int count=0;
       
       //go through every position in the board
       for(int i=0;i<board.length;i++)
       {
           for(int j=0;j<board[i].length;j++)
           {
               if(board[i][j]==playerValue)
               {
                   //check for vertical streak at position i,j
                   count+=verticalStreak(i,j,board,streak);
                   
                   //check for horizontal streak at position i,j
                   count+=horizontalStreak(i,j,board,streak);
                  
                   //check diagonal streaks at position i,j
                   count+=diagonalStreak(i,j,board,streak);

                   
               }
           }
       }
       //the sum of number of streaks of length 'streak'
       return count;
   }
   
   public static int verticalStreak(int row,int col , int[][]board,int streak)
   {
        int consecutiveCount = 0;
        //check for vertical streaks
        for(int i=row;i<board.length;i++)
        {
                if(board[i][col]==board[row][col])
                {
                    consecutiveCount+=1;
                }
                else{break;}
        }
        //return if a vertical streak was found or not
        if(consecutiveCount >= streak)
        {
            return 1;
        }
        else{
            return 0;
        }
     
   }
   
   public static int horizontalStreak(int row,int col,int[][]board,int streak)
   {
       int consecutiveCount=0;
       
       //check for horizontal streaks
       for(int j=col;j<board[row].length;j++)
       {
           if(board[row][j]==board[row][col])
           {
               consecutiveCount+=1;
           }
           else {
           	 break;
           }
       }
       
       //return if a streak was found or not
        if(consecutiveCount >= streak)
        {
           return 1;
        }
        else{
            return 0;
        }
       
   }
   
   
   public static int diagonalStreak(int row,int col, int board[][],int streak)
   {
       int total=0;
       //check for diagonals like '\' 
       int consecutiveCount=0;
       int j = col;
       //System.out.println(j + " " + col);
       
       for(int i=row;i<board.length;i++)
       {
         	 //System.out.println(i + " " + j + " " + col + " " + row);
           if(j>board[0].length-1)
           {
               break;
           }
           else if(board[i][j]==board[row][col]) 
           {
               consecutiveCount++;
           }
           else
           {
               break;
           }
           j+=1;
       }
       
       if (consecutiveCount >= streak)
       {
            total += 1;
       }
       
       
       //check for diagonals like '/'
       
       int j2=0;
       int consecutiveCount2=0;
       
       for(int i=row;i>-1;i--)
       {
           if(j2>board.length-1)
           {
               break;
           }
           else if(board[i][j2]==board[row][col])
           {
               consecutiveCount2 += 1;
           }
           else{
               break;
           }
           
           j2 += 1;
           
       }
       
       
         
       if (consecutiveCount2 >= streak)
       {
            total += 1;
       }
       
       return total;
   }
   
}
