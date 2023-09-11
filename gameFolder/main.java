package gameFolder;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.Random;
import java.util.HashMap;

public class main {
    JFrame wn;
    int windowWidth = 800;
    int windowHeight = 800;
    int buttonsize = 60;
    int xcount = 9;
    int ycount = 3;
    int addRowStart = 0;
    int clickcounter = 2;
    Random rd = new Random();
    JButton[] usedbuttons = new JButton[3];
    int score;
    JButton addNumbers = new JButton("+");
    JButton hint = new JButton("💡");
    JTextPane scorepanel = new JTextPane();
    int buttonsleft = 0;
    int scorepanelY = 30;
    int Yoffset = 100 - buttonsize + scorepanelY;
    int xOffset = (int) (double) (windowWidth - buttonsize * xcount) / 2;
    Map<JButton, ButtonData> ButtonDataMap = new HashMap<JButton, ButtonData>();
    JButton[][] board = new JButton[9][xcount];
    int addedScore;
    int addRowClicksLeft= 5;
    int[] lastButtonPos = new int[2];
    int[] numOfVisiblenumbersInRow = new int[9];

    public void game() {
        for(int i = 0;i<9;i++){numOfVisiblenumbersInRow[i] = -1;}
        wn = new JFrame();
        wn.setSize(windowWidth, windowHeight);
        wn.setLayout(null);
        wn.setLocationRelativeTo(null);
        wn.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < xcount; j++)
                board[i][j] = null;
        for (int i = 0; i < xcount; i++)
            for (int j = addRowStart; j < ycount; j++) {

                createbutton(i, j);
            }
        AddNumbersOfOtherKind();
        wn.setVisible(true);
    }

    public void createbutton(int x, int y) {
        int value = rd.nextInt(1, 10);
        var button = new JButton(Integer.toString(value));
        wn.add(button);
        button.setBounds(buttonsize * x + xOffset, buttonsize * y + Yoffset, buttonsize, buttonsize);
        clickbutton(button);
        board[y][x] = button;
        numOfVisiblenumbersInRow[y] ++;
        ButtonData buttonData = new ButtonData(button, x, y, value);
        ButtonDataMap.put(button, buttonData);
        lastButtonPos[0] = y; lastButtonPos[1] = x;
        buttonsleft++;
    }

    public void AddNumbersOfOtherKind() {
        wn.add(addNumbers);
        wn.add(hint);
        wn.add(scorepanel);
        addNumbers.setBounds((int) (double) Math.ceil(buttonsize * (xcount / 2) + xOffset), 676, buttonsize,
                buttonsize);
        hint.setBounds(500, 676, buttonsize, buttonsize);
        scorepanel.setBounds(390, scorepanelY, 50, 20);
        scorepanel.setText(Integer.toString(score));
        scorepanel.setEditable(false);
        scorepanel.setBackground(null);
        AddNumbersClicked(addNumbers);
    }

    public void AddRow() {
        ButtonData highest = ButtonDataMap.get(board[0][0]);
        for (int y = 0; y < 9; y++) {
            for (int x = 1; x < xcount; x++) {
                if (board[y][x] == null) {
                    break;
                }
                
                if((((highest.X<ButtonDataMap.get(board[y][x]).X)&&(highest.Y<ButtonDataMap.get(board[y][x]).Y))||(highest.Y<ButtonDataMap.get(board[y][x]).Y)||
                ((highest.X<ButtonDataMap.get(board[y][x]).X)&&(highest.Y==ButtonDataMap.get(board[y][x]).Y)))&&
                (ButtonDataMap.get(board[y][x]).Visablilty)){
                    highest = ButtonDataMap.get(board[y][x]);
                }
            }
        }
        int button = buttonsleft;
        int x = highest.X;
        int y = highest.Y;
        for (int i = 0; i < button; i++) {
            x++;
            if (x == 9) {
                y++;
                x = 0;
            }
            createbutton(x, y);
        }
        addRowClicksLeft--;
    }


    public void AddNumbersClicked(JButton addnumbers) {
        addnumbers.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(buttonsleft*2<82 && addRowClicksLeft!=0)
                    AddRow();
            }
        });
    }

    public void clickbutton(JButton button) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (clickcounter > 0) {
                    usedbuttons[clickcounter] = button;
                    button.setBackground(Color.green);
                    clickcounter -= 1;
                    if (clickcounter == 0)
                        if (usedbuttons[1] == usedbuttons[2]) {
                            usedbuttons[1].setBackground(null);
                            clickcounter = 2;
                        } else if (findmatch() && checkpos()) {
                            usedbuttons[1].setVisible(false);
                            usedbuttons[2].setVisible(false);
                            score += addedScore;
                            scorepanel.setText(Integer.toString(score));
                            clickcounter = 2;
                            buttonsleft -= 2;
                            ButtonDataMap.get(usedbuttons[1]).Visablilty = false;
                            ButtonDataMap.get(usedbuttons[2]).Visablilty = false;
                            checkForRemove();
                        } else {
                            usedbuttons[1].setBackground(null);
                            usedbuttons[2].setBackground(null);
                            clickcounter = 2;
                        }
                }
            }
        });
    }
    public boolean findmatch() {
        int num1 = Integer.parseInt(usedbuttons[1].getText());
        int num2 = Integer.parseInt(usedbuttons[2].getText());
        if ((num1 == num2) || (num1 + num2 == 10))
            return true;

        return false;
    }
    

    public boolean checkpos() {
        if (ButtonDataMap.get(usedbuttons[1]).Y == ButtonDataMap.get(usedbuttons[2]).Y) {
            return same_X_Axis();
        }
        else if(ButtonDataMap.get(usedbuttons[1]).X == ButtonDataMap.get(usedbuttons[2]).X){
            return same_Y_Axis();
        }
        else if(Math.abs(ButtonDataMap.get(usedbuttons[1]).X - ButtonDataMap.get(usedbuttons[2]).X) == Math.abs(ButtonDataMap.get(usedbuttons[1]).Y - ButtonDataMap.get(usedbuttons[2]).Y)){
            return diagonaly();
        }
        else if(ButtonDataMap.get(usedbuttons[1]).Y != ButtonDataMap.get(usedbuttons[2]).Y){
            return line_by_line();
        }
        return false;
    }

    public boolean same_X_Axis(){
        ButtonData button1 = (ButtonDataMap.get(usedbuttons[1]).X<ButtonDataMap.get(usedbuttons[2]).X) ? ButtonDataMap.get(usedbuttons[1]) : ButtonDataMap.get(usedbuttons[2]);
        ButtonData button2 = (ButtonDataMap.get(usedbuttons[1]).X>ButtonDataMap.get(usedbuttons[2]).X) ? ButtonDataMap.get(usedbuttons[1]) : ButtonDataMap.get(usedbuttons[2]);
        int space_counter = 0;
        for(int i = button1.X +1;i<button2.X;i++){
            if(ButtonDataMap.get(board[button1.Y][i]).Visablilty){
                return false;
            }
            space_counter++;
        }
        if(space_counter!=0)
            addedScore = 4;
        else
            addedScore = 1;

        return true;
    }

    public boolean same_Y_Axis(){
        ButtonData button1 = (ButtonDataMap.get(usedbuttons[1]).Y<ButtonDataMap.get(usedbuttons[2]).Y) ? ButtonDataMap.get(usedbuttons[1]) : ButtonDataMap.get(usedbuttons[2]);
        ButtonData button2 = (ButtonDataMap.get(usedbuttons[1]).Y>ButtonDataMap.get(usedbuttons[2]).Y) ? ButtonDataMap.get(usedbuttons[1]) : ButtonDataMap.get(usedbuttons[2]);
        int space_counter = 0;
        for(int i = button1.Y +1;i<button2.Y;i++){
            if(ButtonDataMap.get(board[i][button1.X]).Visablilty){
                return false;
            }
            space_counter++;
        }
        if(space_counter!=0)
            addedScore = 4;
        else
            addedScore = 1;
        return true;
    
    }

    public boolean diagonaly(){
        ButtonData button1 = (ButtonDataMap.get(usedbuttons[1]).Y < ButtonDataMap.get(usedbuttons[2]).Y) ? ButtonDataMap.get(usedbuttons[1]) : ButtonDataMap.get(usedbuttons[2]);
        ButtonData button2 = (ButtonDataMap.get(usedbuttons[1]).Y > ButtonDataMap.get(usedbuttons[2]).Y) ? ButtonDataMap.get(usedbuttons[1]) : ButtonDataMap.get(usedbuttons[2]);
        int i = (button1.X < button2.X) ? 1 : -1; 
        int x = button1.X+i;
        int space_counter = 0;
        for(int y = Math.abs(button1.Y - button2.Y)-1; y < button2.Y; y++){
            if(ButtonDataMap.get(board[y][x]).X == button2.X){return true;}
            if(ButtonDataMap.get(board[y][x]).Visablilty){
                return false;
            }
            x += i;
            space_counter++;
        }
        if(space_counter!=0)
            addedScore = 4;
        else
            addedScore = 1;
        return true;
    }

    public boolean line_by_line(){
        ButtonData button1 = (ButtonDataMap.get(usedbuttons[1]).Y < ButtonDataMap.get(usedbuttons[2]).Y) ? ButtonDataMap.get(usedbuttons[1]) : ButtonDataMap.get(usedbuttons[2]);
        ButtonData button2 = (ButtonDataMap.get(usedbuttons[1]).Y > ButtonDataMap.get(usedbuttons[2]).Y) ? ButtonDataMap.get(usedbuttons[1]) : ButtonDataMap.get(usedbuttons[2]);
        int x = (button1.X<8)? button1.X+1:0;int y = (button1.X != 0) ? button1.Y:+1;
        int space_counter = 0;
        for(int i = 0;i < (xcount-1 - button1.X)+button2.X;i++){
            if(ButtonDataMap.get(board[y][x]).Visablilty){
                return false;
            }
            space_counter++;
            x++;
            if(x == xcount){
                y++;x=0;
            }
        }
        if(space_counter!=0)
            addedScore = 4;
        else
            addedScore = 2;
        return true;    
    }
    public void checkForRemove(){
        boolean flag = false;
        int removedRowNum = -1;
        for(int i = 0;i<9;i++){
            if(numOfVisiblenumbersInRow[i] == 0){
                flag = true;
                removedRowNum = i;
                break;
            }
        }
        if(flag){
            bringRowUpBase(removedRowNum+1);
        }
    }
    //serves as a launcher and a base to the active method
    public void bringRowUpBase(int row){
        if(numOfVisiblenumbersInRow[row+1] != -1 || row+1 < 10){
            for(int x = 0;x<xcount;x++){
                try{
                    bringRowUpActive(row, x);
                }
                catch(Exception e){
                    break;
                }
            }
            if(lastButtonPos[0] == row){lastButtonPos[0] = row-1;}
        }
    }
    // changes the button bounds on the screen,board list and buttonDataMap in orther to bring the row up
    public void bringRowUpActive(int row, int x){
        board[row][x].setBounds(buttonsize * x + xOffset, buttonsize * row-1 + Yoffset, buttonsize, buttonsize);
        ButtonDataMap.get(board[row][x]).Y -= 1;
        board[row-1][x] = board[row][x];
        board[row][x] = null;
    }
    
}