package com.khalelewis.android.conversionapp;

import java.text.DecimalFormat;

public class Quantity {

    final double value;
    final Unit unit;

    public static enum Unit{
        grams(1.0d),kilograms(.001d),pounds(.0022d), ounces(.0353d), stones(.000157d);

        final static Unit baseUnit = grams;

        final double byBaseUnit;

        private Unit(double inGrams){

            this.byBaseUnit = inGrams;
        }

        private double toBaseUnit(double value){
            return value/byBaseUnit;
        }

        private double fromBaseUnit(double value){
            return  value * byBaseUnit;
        }
    }

    public Quantity(double value, Unit unit){
        super();
        this.value = value;
        this.unit = unit;
    }

    public Quantity to(Unit newUnit){
        Unit oldUnit = this.unit;
        return  new Quantity(newUnit.fromBaseUnit(oldUnit.toBaseUnit(value)),newUnit);
    }

    public String toString(){
        DecimalFormat df = new DecimalFormat("#.0000");
        return df.format(value)+" " +this.unit;
    }
}
