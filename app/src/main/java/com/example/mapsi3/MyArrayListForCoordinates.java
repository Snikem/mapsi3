package com.example.mapsi3;

public  class MyArrayListForCoordinates {
    public  double[] data;
    public  int size;
    public MyArrayListForCoordinates(int n) {
        data = new double[n];
    }

    public  double get(int i) {
        return data[i];
    }

    public  void  set(int i, double obj) {
        data[i] = obj;
    }
    public  void add (double obj)
    {
        if (size == data.length){
            double[] nData = new double[size * 2];
            System.arraycopy(data, 0, nData, 0, size);
            data = nData;
        }
        data[size] = obj;
        size++;
    }
    public  void remove(int i) {
        if (i != size - 1)
            System.arraycopy(data, i + 1, data, i, size - i);

        data[size - 1] = 0;
        size--;
    }
    public  int getSize() {
        return size;
    }
}
