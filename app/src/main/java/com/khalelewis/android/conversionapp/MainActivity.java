package com.khalelewis.android.conversionapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    private Spinner unitSpinnerTo, unitSpinnerFrom;
    private EditText amountEntered;
    private TextView outputAmount, outputUnit;
    public String from = "grams",to = "grams";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amountEntered = (EditText) findViewById(R.id.userAmount);
        outputAmount = (TextView) findViewById(R.id.outputText);
        outputUnit = (TextView) findViewById(R.id.unitsId);

        amountEntered.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (getEnteredAmountString().length() == 0 || getEnteredAmountString().equals(".")) {
                    outputAmount.setText("");
                    outputUnit.setText("");
                }
                else {
                    addItemsToUnitSpinner();
                    addListenerToSpinnerFrom();
                    addListenerToSpinnerTo();
                }
            }
        });
    }

    public void addItemsToUnitSpinner() {
        unitSpinnerFrom = (Spinner) findViewById(R.id.metric_from);
        unitSpinnerTo = (Spinner) findViewById(R.id.metric_to);

        ArrayAdapter<CharSequence> unitTypeSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.conversion_types,
                android.R.layout.simple_spinner_item);

        unitTypeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        unitSpinnerTo.setAdapter(unitTypeSpinnerAdapter);
        unitSpinnerFrom.setAdapter(unitTypeSpinnerAdapter);
    }

    public void addListenerToSpinnerFrom() {
        unitSpinnerFrom = (Spinner) findViewById(R.id.metric_from);
        unitSpinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemSelectedInSpinner = parent.getItemAtPosition(position).toString();
                from = itemSelectedInSpinner; }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
        });
    }

    public void addListenerToSpinnerTo() {
        unitSpinnerTo = (Spinner) findViewById(R.id.metric_to);
        unitSpinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemSelectedInSpinner = parent.getItemAtPosition(position).toString();
                to = itemSelectedInSpinner;
                Convert(from, to);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    public void Convert(String UnitFrom, String UnitTo){
        Quantity FromUnit;

        switch(UnitFrom){
            case "ounces":
                FromUnit = new Quantity(Double.parseDouble(amountEntered.getText().toString()), Quantity.Unit.ounces);
                break;
            case "pounds":
                FromUnit = new Quantity(Double.parseDouble(amountEntered.getText().toString()), Quantity.Unit.pounds);
                break;
            case "kilograms":
                FromUnit = new Quantity(Double.parseDouble(amountEntered.getText().toString()), Quantity.Unit.kilograms);
                break;
            case "stones":
                FromUnit = new Quantity(Double.parseDouble(amountEntered.getText().toString()), Quantity.Unit.stones);
                break;
            default:
                FromUnit = new Quantity(Double.parseDouble(amountEntered.getText().toString()), Quantity.Unit.grams);
                break;
        }
        if(UnitTo.equals("grams")){
            updateUnitTypeUsingOther(FromUnit,Quantity.Unit.grams);
        }
        else if(UnitTo.equals("ounces")){
            updateUnitTypeUsingOther(FromUnit,Quantity.Unit.ounces);
        }
        else if(UnitTo.equals("pounds")){
            updateUnitTypeUsingOther(FromUnit,Quantity.Unit.pounds);
        }
        else if(UnitTo.equals("kilograms")){
            updateUnitTypeUsingOther(FromUnit,Quantity.Unit.kilograms);
        }
        else{
            updateUnitTypeUsingOther(FromUnit,Quantity.Unit.stones);
        }
    }

    public void updateUnitTypeUsingOther(Quantity from, Quantity.Unit currentUnit){
        Quantity valueInGrams = from;
        if(!from.unit.equals("grams")) {
            valueInGrams = from.to(Quantity.Unit.grams);
        }
        Quantity unitToQuantity = valueInGrams.to(currentUnit);

        outputAmount.setText(unitToQuantity.value+"");
        outputUnit.setText(unitToQuantity.unit.toString());
    }

    public boolean checkIfAmountEntered(){
        if(getEnteredAmountString().length() == 0){
            return false;
        }
        else if(getEnteredAmountString().equals(".")){
            return false;
        }
        return true;
    }

    public String getEnteredAmountString(){
        return amountEntered.getText().toString();
    }
}