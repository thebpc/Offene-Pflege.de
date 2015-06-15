package gui.interfaces;

import gui.events.DataChangeEvent;
import gui.events.DataChangeListener;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;

/**
 * Created by tloehr on 11.05.15.
 */
public abstract class EditPanelDefault<T> extends JPanel implements EditPanelInterface<T>, Reloadable {
    protected T data;
    protected HashSet<DataChangeListener<T>> listDCL;
    private final DataProvider<T> dataProvider;
    protected final EditPanelDefault<T> thisPanel = this;
    protected boolean edited = false;

    public EditPanelDefault(DataChangeListener dcl, DataProvider<T> dataProvider) {
        this(dataProvider);
        addDataChangeListener(dcl);
    }

    public EditPanelDefault(DataProvider<T> dataProvider) {
        super();
        listDCL = new HashSet<>();
        this.dataProvider = dataProvider;
        data = dataProvider.getData();
    }

    @Override
    public abstract void setStartFocus();

//    @Override
//    public void setDataObject(T data) {
//        this.data = data;
//    }

    @Override
    public abstract String doValidation();

    @Override
    public void addDataChangeListener(DataChangeListener<T> dcl) {
        listDCL.add(dcl);
    }

    @Override
    public void removeDataChangeListener(DataChangeListener<T> dcl) {
        listDCL.remove(dcl);
    }

    @Override
    public void cleanup() {
        edited = false;
        data = null;
        listDCL.clear();
        removeAll();
    }

    public void broadcast(DataChangeEvent<T> dce) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        for (DataChangeListener<T> dcl : listDCL) {
            dcl.dataChanged(dce);
        }
    }

    public void reload(T data) {
            this.data = data;
            edited = false;
    //        SwingUtilities.invokeLater(() -> {
    //
    //            edited = false;
    //            refreshDisplay();
    //            revalidate();
    //            repaint();
    //        });
        }

    @Override
    public void reload() {
        data = dataProvider.getData();
        edited = false;
//        SwingUtilities.invokeLater(() -> {
//
//            edited = false;
//            refreshDisplay();
//            revalidate();
//            repaint();
//        });
    }

    public abstract void refreshDisplay();

}
