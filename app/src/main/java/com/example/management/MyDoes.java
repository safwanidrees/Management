package com.example.management;

public class MyDoes {
    private String titledoes,datedoes,descdoes;
public MyDoes(){}

    public MyDoes(String titledoes, String datedoes, String descdoes) {
        this.titledoes = titledoes;
        this.datedoes = datedoes;
        this.descdoes = descdoes;

    }

    public void setTitledoes(String titledoes) {
        this.titledoes = titledoes;
    }

    public void setDatedoes(String datedoes) {
        this.datedoes = datedoes;
    }

    public void setDescdoes(String descdoes) {
        this.descdoes = descdoes;
    }

    public String getTitledoes() {
        return titledoes;
    }

    public String getDatedoes() {
        return datedoes;
    }

    public String getDescdoes() {
        return descdoes;
    }
}

