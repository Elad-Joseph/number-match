
import java.util.Random;
public class createBoard {
    Random rd = new Random();
    int[][] board = new int[3][9];
    public int[] numbers(){
        int numbersAmount = rd.nextInt(6,10);
        int[] numbers = new int[numbersAmount];
        int i = 0;
        while(i<numbersAmount){
            int num = rd.nextInt(1,10);
            if(!inlist(num , numbers)){
                numbers[i] = num;
                i++;
            }
        }
        return numbers;
    }
    
    public boolean inlist(int obj,int[] lst){
        boolean inlist = false;
        for(int i = 0;i < lst.length;i++){
            if(lst[i] == obj){
                inlist = true;
            }
        }
        return inlist;
    }
    public int[][] constractBoard(){
        int[] numbers = numbers();
        int range = numbers.length;
        for(int i = 0;i<3;i++){
            for(int j = 0;j<9;j++){
                board[i][j] = numbers[rd.nextInt(range)];
            }
        }
        override();
        return board;
    }

    public void override(){
        int[][] num = {{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}};
        board = num;
    }

}
