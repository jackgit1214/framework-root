package com.framework.DaoBaseTest;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class PropertyChangeSupportTest {

	transient protected PropertyChangeSupport listeners = new PropertyChangeSupport( this );

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        listeners.addPropertyChangeListener(listener);
    }

    protected void firePropertyChange(String prop, Object old, Object newValue) {
        listeners.firePropertyChange(prop, old, newValue);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        listeners.removePropertyChangeListener(l);
    }
	


}
