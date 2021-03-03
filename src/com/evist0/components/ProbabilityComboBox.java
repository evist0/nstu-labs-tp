package com.evist0.components;

import javax.swing.*;
import java.util.ArrayList;

class ProbabilityModel {
    private final String _string;
    private final float _value;

    public ProbabilityModel(String string, float value) {
        _string = string;
        _value = value;
    }

    public float getValue() {
        return _value;
    }

    @Override
    public String toString() {
        return _string;
    }
}

/*
class ProbabilityComboBoxModel extends DefaultComboBoxModel<ProbabilityModel> {
    private final ArrayList<ProbabilityModel> _items = new ArrayList<>();

    public ProbabilityComboBoxModel() {
        for (int i = 0; i <= 100; i += 10) {
            var string = i + "%";
            var value = (float) i / 10;

            _items.add(new ProbabilityModel(string, value));
        }

        setSelectedItem(_items.get(10));
    }

    @Override
    public void setSelectedItem(Object anObject) {
        super.setSelectedItem(anObject);
    }

    @Override
    public Object getSelectedItem() {
        return super.getSelectedItem();
    }

    @Override
    public int getSize() {
        return _items.size();
    }

    @Override
    public ProbabilityModel getElementAt(int index) {
        return _items.get(index);
    }
}
*/

public class ProbabilityComboBox extends JComboBox<ProbabilityModel> {
    private final ArrayList<ProbabilityModel> _items = new ArrayList<>();

    public ProbabilityComboBox() {
        super();

        for (int i = 0; i <= 100; i += 10) {
            var string = i + "%";
            var value = (float) i / 100;

            var probability = new ProbabilityModel(string, value);

            _items.add(probability);
            addItem(probability);
        }

        setSelectedIndex(0);
    }

    public float getValue() {
        var selected = getSelectedItem();

        assert selected != null;
        return ((ProbabilityModel) selected).getValue();
    }

    public void setSelectedItem(float value) {
        for (var probability : _items) {
            if (probability.getValue() == value) {
                setSelectedItem(probability);
            }
        }
    }
}
