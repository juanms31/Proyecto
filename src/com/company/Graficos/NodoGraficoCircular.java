package com.company.Graficos;

public class NodoGraficoCircular {

    //region SETTER y GETTER

    public String getComparableKey() {
        return comparableKey;
    }

    public void setComparableKey(String comparableKey) {
        this.comparableKey = comparableKey;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }


    //endregion

    //region Atributos

    private String comparableKey;
    private Double value;

    //endregion

}