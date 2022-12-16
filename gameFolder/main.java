package gameFolder;
import javax.swing.*;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;

public class main{
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
    int buttonsleft = ycount*xcount;
    int scorepanelY = 30;
    int Yoffset = 100 - buttonsize + scorepanelY;
    int xOffset = (int)(double)(windowWidth - buttonsize*xcount)/2;
    Map<JButton, ButtonData> ButtonDataMap = new HashMap<JButton, ButtonData>();

    public void game(){
        wn = new JFrame();
        wn.setSize(windowWidth,windowHeight);
        wn.setLayout(null);
        wn.setLocationRelativeTo(null);
        wn.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        for(int i= 0;i<xcount; i++)
            for(int j = addRowStart;j<ycount; j++){
                
                createbutton(i, j);
            }
        AddNumbersOfOtherKind();
        wn.setVisible(true);
    }

    public void createbutton(int x,int y){
        int value = rd.nextInt(1,10);
        var button = new JButton(Integer.toString(value));
        wn.add(button);
        button.setBounds(buttonsize*x+xOffset,buttonsize*y+Yoffset,buttonsize,buttonsize);
        clickbutton(button);

        ButtonData buttonData = new ButtonData(button, x, y, value);
        ButtonDataMap.put(button, buttonData);
    }

    public void AddNumbersOfOtherKind(){
        wn.add(addNumbers);
        wn.add(hint);
        wn.add(scorepanel);
        addNumbers.setBounds((int)(double)Math.ceil(buttonsize*(xcount/2)+xOffset),676,buttonsize,buttonsize);
        hint.setBounds(500,676,buttonsize,buttonsize);
        scorepanel.setBounds(390,scorepanelY,50,20);
        scorepanel.setText(Integer.toString(score));
        scorepanel.setEditable(false);
        scorepanel.setBackground(null);
        AddNumbersClicked(addNumbers);
    }

    public void AddRow(){
        addRowStart +=3;
        ycount += 3;
        int revolutionscounter = 0;
        for(int j = addRowStart;j<ycount;j++){
            for(int i = 0;i<xcount;i++){
                if(revolutionscounter==buttonsleft){
                    ycount -= addRowStart - j + 2;
                    addRowStart -= addRowStart - j + 2;
                    buttonsleft += revolutionscounter;
                    break;
                }
                revolutionscounter++;
                createbutton(i, j);
            }
            if(revolutionscounter==buttonsleft)
                    break;
        }
    }

    public void AddNumbersClicked(JButton addnumbers){
        addnumbers.addMouseListener(new java.awt.event.MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e) {
                AddRow();
            }
        });
    }

    public void clickbutton(JButton button){
        button.addMouseListener(new java.awt.event.MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e) {
                if(clickcounter>0){
                    usedbuttons[clickcounter] = button;
                    button.setBackground(Color.green);
                    clickcounter -= 1;
                    if(clickcounter == 0)
                        if(usedbuttons[1] == usedbuttons[2]){
                            usedbuttons[1].setBackground(null);
                            clickcounter = 2;
                        }
                        else if(findmatch() && checkpos()){
                            usedbuttons[1].setVisible(false);
                            usedbuttons[2].setVisible(false);
                            score += addedScore();;
                            scorepanel.setText(Integer.toString(score));
                            clickcounter = 2;
                            buttonsleft -= 2;
                            ButtonDataMap.get(usedbuttons[1]).Visablilty = false;
                            ButtonDataMap.get(usedbuttons[2]).Visablilty = false;
                        }
                        else{
                            usedbuttons[1].setBackground(null);
                            usedbuttons[2].setBackground(null);
                            clickcounter = 2;
                        }
                }               
            }
        });
    }
    public int addedScore(){
        if((Math.abs(usedbuttons[1].getX() - usedbuttons[2].getX())>100 && usedbuttons[1].getY() == usedbuttons[2].getY())||
           (Math.abs(usedbuttons[1].getY() - usedbuttons[2].getY())>100 && usedbuttons[1].getX() == usedbuttons[2].getX())||
           (Math.abs(usedbuttons[1].getX() - usedbuttons[2].getX())>100 && Math.abs(usedbuttons[1].getY() - usedbuttons[2].getY())>100)){
            return 4;
        }
        return 2;
    }
    public boolean findmatch(){
        int num1 = Integer.parseInt(usedbuttons[1].getText());
        int num2 = Integer.parseInt(usedbuttons[2].getText());
        if((num1 == num2) || (num1 + num2 == 10))
            return true;

        return false;
    }
    public boolean checkpos(){ 
        JButton higher = (usedbuttons[1].getY()<usedbuttons[2].getY()) ? usedbuttons[1] : usedbuttons[2];
        JButton lower = (usedbuttons[1].getY()>usedbuttons[2].getY()) ? usedbuttons[1] : usedbuttons[2];
        if((Math.abs(usedbuttons[1].getX()-usedbuttons[2].getX())<buttonsize + 1)&&
          (Math.abs(usedbuttons[1].getY()-usedbuttons[2].getY())<buttonsize + 1)||
          (Math.abs(usedbuttons[1].getY()-usedbuttons[2].getY())==Math.abs(usedbuttons[1].getX()-usedbuttons[2].getX())))
            return true;
        else if((Math.abs(usedbuttons[1].getX()-usedbuttons[2].getX())==700)&&
               (Math.abs(usedbuttons[1].getY()-usedbuttons[2].getY())==50))
            return true;
        else if((higher.getX() == buttonsize*(xcount-1)+100 && lower.getX() == 100)&&
               (Math.abs(usedbuttons[1].getY()-usedbuttons[2].getY())<100 && Math.abs(usedbuttons[1].getY()-usedbuttons[2].getY())>0))
            return true;    
        return false;
    }
}   