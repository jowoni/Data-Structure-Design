/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demonstration.parade.route;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentListener;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import java.awt.image.BufferedImage;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
/**
 *
 * @author Michael
 */
public class View extends JFrame //View 클래스는 유저에게 화면을 보여주는 역할을 하는 클래스이다.
{
    JPanel buttonPanel;              //상단의 메뉴 및 버튼들을 보여주는 패널
    JMenuBar menuBar;                //상단의 메뉴바
    JMenu menu1, menu2, menu3;       
    JMenuItem menuList;
    JButton start, boostTime, reverseTime, slowTime, stopTime, setTime, Reset; 
    JTextField timeTextField;
    JLabel timeTextLabel;
    
    void addButtonTimeListener(ActionListener al)
    {
        boostTime.addActionListener(al);
    }
    
    public View() //고 함수는 GUI가 본격적으로 실행되게 하는 함수이다. 메인 함수에서 사용된다.
    {
        buttonPanel = new JPanel();
    }
}
