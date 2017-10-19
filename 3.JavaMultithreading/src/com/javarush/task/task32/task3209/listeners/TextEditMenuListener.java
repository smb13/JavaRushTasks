package com.javarush.task.task32.task3209.listeners;

import com.javarush.task.task32.task3209.View;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Mikhail Shamanin on 08.10.2017.
 */
public class TextEditMenuListener implements MenuListener {
    private View view;

    public TextEditMenuListener(View view) {
        this.view = view;
    }

    @Override
    public void menuSelected(MenuEvent menuEvent) {
        if (menuEvent.getSource() instanceof JMenu) {
            JMenu menu = (JMenu) menuEvent.getSource();
            List<Component> cL = Arrays.asList(menu.getMenuComponents());
            boolean enM = view.isHtmlTabSelected();
            for (Component c :  cL) {
                if (c != null) {
                    c.setEnabled(enM);
                }
            }
        }
    }

    @Override
    public void menuDeselected(MenuEvent e) {

    }

    @Override
    public void menuCanceled(MenuEvent e) {

    }
}
