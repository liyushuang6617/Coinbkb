package com.example.checkbox;

public class Data {

    private String name;
    private boolean isCheck;

    public Data(String name, boolean isCheck) {
        this.name = name;
        this.isCheck = isCheck;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    @Override
    public String toString() {
        return "Data{" +
                "name='" + name + '\'' +
                ", isCheck=" + isCheck +
                '}';
    }
}
