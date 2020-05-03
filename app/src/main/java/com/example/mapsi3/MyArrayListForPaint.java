package com.example.mapsi3;

public  class MyArrayListForPaint {
    public  int[] data;
    public  int size;
    public MyArrayListForPaint(int n) {
        data = new int[n];
    }

    public  int get(int i) {
        return data[i];
    }

    public  void set(int i, int obj) {
        data[i] = obj;
    }
    public  void add (int obj)
    {
        if (size == data.length){
            int[] nData = new int[size * 2];
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
