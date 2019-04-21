package ar.uba.fi.armariovirtual.utils;
/*
 * Copyright (C) 2012 Kris Wong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

/**
 * A Spinner view that does not dismiss the dialog displayed when the control is "dropped down"
 * and the user presses it. This allows for the selection of more than one option.
 */
public class MultiSelectSpinner extends android.support.v7.widget.AppCompatSpinner implements OnMultiChoiceClickListener
{
    private String[] _items = null;
    private boolean[] _selection = null;

    private ArrayAdapter<String> _proxyAdapter;

    public void setTextoVacio(String textoVacio) {
        this.textoVacio = textoVacio;
        actualizar();
    }

    private String textoVacio = "[NO HAY OPCIONES ELEGIDAS]";

    /**
     * Constructor for use when instantiating directly.
     * @param context
     */
    public MultiSelectSpinner(Context context) {
        super(context);

        _proxyAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item);
        super.setAdapter(_proxyAdapter);
    }

    /**
     * Constructor used by the layout inflater.
     * @param context
     * @param attrs
     */
    public MultiSelectSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);

        _proxyAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item);
        super.setAdapter(_proxyAdapter);
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        if (_selection != null && which < _selection.length) {
            _selection[which] = isChecked;

            actualizar();
            notificarClick(which, isChecked);

        }
        else {
            throw new IllegalArgumentException("Argument 'which' is out of bounds.");
        }
    }

    private void notificarClick(int which, boolean isChecked) {

        if (getOnItemSelectedListener() != null) {
            getOnItemSelectedListener().onItemSelected(this, getSelectedView(), which, getSelectedItemId());
        }
    }

    private void updateProxyAdapter() {
        _proxyAdapter.clear();
        _proxyAdapter.add(buildSelectedItemString());

        setSelection(0);
    }

    public void actualizar()
    {
        Log.d("msg", "actualizar multispinner");
        updateProxyAdapter();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean performClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMultiChoiceItems(_items, _selection, this);
        AlertDialog dialog = builder.show();
        dialog.setCanceledOnTouchOutside(true);
        return true;
    }

    /**
     * MultiSelectSpinner does not support setting an adapter. This will throw an exception.
     * @param adapter
     */
    @Override
    public void setAdapter(SpinnerAdapter adapter) {
        throw new RuntimeException("setAdapter is not supported by MultiSelectSpinner.");
    }

    /**
     * Sets the options for this spinner.
     * @param items
     */
    public void setItems(String[] items) {
        _items = items;
        _selection = new boolean[_items.length];

        Arrays.fill(_selection, false);

        actualizar();
    }

    /**
     * Sets the options for this spinner.
     * @param items
     */
    public void setItems(List<String> items) {
        _items = items.toArray(new String[items.size()]);
        _selection = new boolean[_items.length];

        Arrays.fill(_selection, false);

        actualizar();
    }

    /**
     * Sets the selected options based on an array of string.
     * @param selection
     */
    public void setSelection(String[] selection) {
        for (String sel : selection) {
            for (int j = 0; j < _items.length; ++j) {
                if (_items[j].equals(sel)) {
                    _selection[j] = true;
                }
            }
        }

        actualizar();
    }

    /**
     * Sets the selected options based on a list of string.
     * @param selection
     */
    public void setSelection(List<String> selection) {
        for (String sel : selection) {
            for (int j = 0; j < _items.length; ++j) {
                if (_items[j].equals(sel)) {
                    _selection[j] = true;
                }
            }
        }

        actualizar();
    }

    /**
     * Sets the selected options based on an array of positions.
     * @param selectedIndicies
     */
    public void setSelection(int[] selectedIndicies) {
        for (int index : selectedIndicies) {
            if (index >= 0 && index < _selection.length) {
                _selection[index] = true;
            }
            else {
                throw new IllegalArgumentException("Index " + index + " is out of bounds.");
            }
        }

        actualizar();
    }

    /**
     * Returns a list of strings, one for each selected item.
     * @return
     */
    public List<String> getSelectedStrings() {
        List<String> selection = new LinkedList<>();
        for (int i = 0; i < _items.length; ++i) {
            if (_selection[i]) {
                selection.add(_items[i]);
            }
        }
        return selection;
    }

    /**
     * Returns a list of positions, one for each selected item.
     * @return
     */
    public List<Integer> getSelectedIndicies() {
        List<Integer> selection = new LinkedList<>();
        for (int i = 0; i < _items.length; ++i) {
            if (_selection[i]) {
                selection.add(i);
            }
        }
        return selection;
    }

    public boolean isSelected(String value)
    {
        for (int i = 0; i < _items.length; ++i) {
            if (_selection[i] && _items[i].equals(value)) {
                return true;
            }
        }
        return false;
    }

    public boolean isSelected(int idx)
    {
        return _selection[idx];
    }

    /**
     * Builds the string for display in the spinner.
     * @return comma-separated list of selected items
     */
    private String buildSelectedItemString()
    {
        if(_items != null)
        {
            StringBuilder sb = new StringBuilder();

            boolean foundOne = false;

            for (int i = 0; i < _items.length; ++i) {
                if (_selection[i]) {
                    if (foundOne) {
                        sb.append(", ");
                    }
                    foundOne = true;

                    sb.append(_items[i]);
                }
            }

            if(!foundOne)
            {
                return textoVacio;
            }

            return sb.toString();
        }

        return textoVacio;
    }
}