package com.example.vladislav.nnscan;

/**
 * Created by Vladislav_PC on 13.06.2017.
 */

public class Neiron {
    public  String      name;
    public double[][] veight;
    public int countTrainig;

    public Neiron() {}
    public String GetName() { return name; }

    public void Clear(String name, int x, int y)
    {
        this.name = name;
        veight = new double[x][y];
        for (int n = 0; n < veight.length; n++)
            for (int m = 0; m < veight[0].length; m++) veight[n][m] = 0;
        countTrainig = 0;
    }

    public double GetRes(int[][] data){
        if (veight.length != data.length || veight.length != data.length) return -1;
        double res = 0.50;
        double somVar;
        for (int n = 0; n < veight.length; n++)
            for (int m = 0; m < veight[0].length; m++) {
                res += 1 - Math.abs(veight[n][m] - data[n][m]);
                somVar= 1 - Math.abs(veight[n][m] - data[n][m]);
            }
        res =  res / (veight.length * veight[1].length);
        return res;
    }

    public int Training(int[][] data)
    {
        if (data == null || veight.length != data.length || veight[0].length != data[1].length) return countTrainig;
        countTrainig++;
        for (int n = 0; n < veight.length; n++)
            for (int m = 0; m < veight[1].length; m++)
            {
                double v = data[n][m] == 0 ? 0 : 1;
                veight[n][m] += 2 * (v - 0.5f) / countTrainig;
                if (veight[n][m] > 1) veight[n][m] = 1;
                if (veight[n][m] < 0) veight[n][m] = 0;
            }
        return countTrainig;
    }
}
