package com.example.vladislav.nnscan;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by Vladislav_PC on 13.06.2017.
 */

public class NeiroGraphUtils {


    public static int[][] GetArrayFromBitmap(Bitmap image)
    {
        int[][] res = new int[image.getWidth()][image.getHeight()];
        for (int n = 0; n < res.length; n++)
            for (int m = 0; m < res[0].length; m++)
            {
                int color = (Color.red(image.getPixel(n, m)) + Color.green(image.getPixel(n, m)) + Color.blue(image.getPixel(n, m))) / 3;
                res[n][m] = color > 0 ? 1 : 0;
            }
        return res;
    }

    private static int toArgb(int pixel){
        int A = (pixel >> 24) & 0xff;
        int R = (pixel >> 16) & 0xff;
        int G = (pixel >>  8) & 0xff;
        int B = (pixel      ) & 0xff;

        int color = (A & 0xff) << 24 | (R & 0xff) << 16 | (G & 0xff) << 16 | (B & 0xff);
        return color;
    }

    public static Bitmap GetBitmapFromArr(int[][] array)
    {
        Bitmap bitmap = Bitmap.createBitmap(array.length, array[0].length, Bitmap.Config.ARGB_8888);
        for (int x = 0; x < array.length; x++)
            for (int y = 0; y < array[0].length; y++)
                if (array[x][y] == 0)
                    bitmap.setPixel(x, y, Color.WHITE);
                else
                    bitmap.setPixel(x, y, Color.BLACK);
        return bitmap;
    }

    public static int[][] CutImageToArray(int[][] b, Point max)
    {
        int x1 = 0;
        int y1 = 0;
        int x2 = max.x;
        int y2 = max.y;

        Log.d(TAG, "b.length) #" + b.length);
        Log.d(TAG, "b[0].length #" + b[0].length);

        for (int y = 0; y < b.length  && y1 == 0; y++)       //h
            for (int x = 0; x < b[0].length   && y1 == 0; x++)        //w
                if (toArgb(b[x][y]) != 0) y1 = y;
        for (int y = b.length ; y >= 0 && y2 == max.y; y--)  //h
            for (int x = 0; x < b[0].length  && y2 == max.y; x++)  //w
                if (toArgb(b[x][y]) != 0) y2 = y;
        for (int x = 0; x < b[0].length  && x1 == 0; x++)        //w
            for (int y = 0; y < b.length  && x1 == 0; y++)        //h
                if (toArgb(b[x][y]) != 0) x1 = x;
        for (int x = b[0].length ; x >= 0 && x2 == max.x; x--)
            for (int y = 0; y < b.length  && x2 == max.x; y++)
                if (toArgb(b[x][y]) != 0) x2 = x;

        if (x1 == 0 && y1 == 0 && x2 == max.x && y2 == max.y) return null;

        int size = x2 - x1 > y2 - y1 ? x2 - x1 + 1 : y2 - y1 + 1;
        int dx = y2 - y1 > x2 - x1 ? ((y2 - y1) - (x2 - x1)) / 2 : 0;
        int dy = y2 - y1 < x2 - x1 ? ((x2 - x1) - (y2 - y1)) / 2 : 0;

        int[][] res = new int[size][size];
        for (int x = 0; x < res.length; x++)
            for (int y = 0; y < res[0].length; y++)
            {
                int pX = x + x1 - dx;
                int pY = y + y1 - dy;
                if (pX < 0 || pX >= max.x || pY < 0 || pY >= max.y)
                    res[x][y] = 0;
                else
                    res[x][y] = toArgb(b[x + x1 - dx][y + y1 - dy]) == 0 ? 0 : 1;
            }
        return res;
    }

    public static int[][] LeadArray(int[][] source, int[][] res)
    {
        for (int n = 0; n < res.length; n++)
            for (int m = 0; m < res[0].length; m++) res[n][m] = 0;

        double pX = (double)res.length / (double)source.length;
        double pY = (double)res[0].length / (double)source[0].length;

        for (int n = 0; n < source.length; n++)
            for (int m = 0; m < source[0].length; m++)
            {
                int posX = (int)(n * pX);
                int posY = (int)(m * pY);
                if (res[posX][posY] == 0) res[posX][posY] = source[n][m];
            }
        return res;
    }
}