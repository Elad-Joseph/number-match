package gameFolder;
import java.util.Random;
public class createBoard {
    Random rd = new Random();
    int[][] board = new int[3][9];
    public int[] numbers(){
        int numbersAmount = rd.nextInt(6,10);
        int[] numbers = new int[numbersAmount];
        while(){
            int number = rd.nextInt(1,numbersAmount+1);
            if(inlist(number, numbers)){
                continue;
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
    public void constractBoard(){
    }

}
