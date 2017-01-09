package com.framework.DaoBaseTest;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class BeanTestListener implements PropertyChangeListener{

    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals("name"))
            System.out.println("BeanTest 的 name 属性变化！");
       
    }
    public static void main(String[] args){
        BeanTest test = new BeanTest();
        test.addPropertyChangeListener(new BeanTestListener());
        test.setName("fuxueliang");
        test.setName("fuxueliang111");
        System.out.println(test.getName());
    }

}