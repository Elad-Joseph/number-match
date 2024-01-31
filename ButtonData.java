
import javax.swing.*;

public class ButtonData
{
    public JButton Button;
    public int X;
    public int Y;
    public int Value;
    public boolean Visablilty;
    public JButton[] Matching = new JButton[8]; 

    public ButtonData(JButton button,int x,int y,int value)
    {
        Button = button;
        X = x;
        Y = y;
        Value = value;
        Visablilty = true;
    }
}