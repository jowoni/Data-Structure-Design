/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demonstration.parade.route;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Michael
 */
public class Controller 
{
    View v;
    Controller(View v) //View 객체를 만들고 View에있는 Button들을 Controller의 Listener에 추가시키는 메소드들을 사용한다.
    {
        this.v = v;
        
        this.v.addButtonTimeListener(new ButtonTimeListener());
    }
    
    class ButtonTimeListener implements ActionListener //도형 버튼 리스너
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            
        }
    }
}
