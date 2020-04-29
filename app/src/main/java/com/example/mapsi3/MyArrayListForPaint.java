package com.example.mapsi3;

import android.graphics.Paint;

public  class MyArrayListForPaint {
    public  Paint[] data;
    public  int size;
    public MyArrayListForPaint(int n) {
        data = new Paint[n];
    }
    public MyArrayListForPaint() {
        this(10);
    }
    public  Paint get(int i) {
        return data[i];
    }

    public  void set(int i, Paint obj) {
        data[i] = obj;
    }
    public  void add (Paint obj)
    {
        if (size == data.length){
            Paint[] nData = new Paint[size * 2];
            System.arraycopy(data, 0, nData, 0, size);
            data = nData;
        }
        data[size] = obj;
        size++;
    }
    public  void remove(int i) {
        if (i != size - 1)
            System.arraycopy(data, i + 1, data, i, size - i);
        // очищаем последний элемент
        data[size - 1] = null;
        size--;
    }
    public  int getSize() {
        return size;
    }
}
