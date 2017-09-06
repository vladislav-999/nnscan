package com.example.vladislav.nnscan;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.Arrays;

import static android.graphics.Color.rgb;

/**
 * Created by Vladislav_PC on 13.06.2017.
 */

public class filters {

    private static long MediumColor = 0;

    public static int[][] originalImg = null;
    public static int[][] imgBuff;

    public filters(Bitmap image){
        MedianFilter(image);
        MonohromFilter(imgBuff);
        // MedianFilter(imgBuff);
    }

    public static void MedianFilter(Bitmap image)
    {
       /* int[] pixel=new int[9];
        int[] R=new int[9];
        int[] B=new int[9];
        int[] G=new int[9];
        int[] alpha=new int[9];
        int pixels[][] = new int[image.getHeight()][image.getWidth()];
        int[][] pixBuff = new int[image.getHeight()][image.getWidth()];*/
        originalImg = new int[image.getHeight()][image.getWidth()];
        MediumColor = 0;

        for (int j = 1; j < image.getHeight() - 1; j++) {
            for(int i = 1; i < image.getWidth() - 1; i++){
            /*    pixel[0] = rgb(Color.red(image.getPixel(i - 1, j - 1)), Color.green(image.getPixel(i - 1, j - 1)), Color.blue(image.getPixel(i - 1, j - 1)));
                pixel[1] = rgb(Color.red(image.getPixel(i - 1, j)), Color.green(image.getPixel(i - 1, j)), Color.blue(image.getPixel(i - 1, j)));
                pixel[2] = rgb(Color.red(image.getPixel(i - 1, j + 1)), Color.green(image.getPixel(i - 1, j + 1)), Color.blue(image.getPixel(i - 1, j + 1)));
                pixel[3] = rgb(Color.red(image.getPixel(i, j + 1)), Color.green(image.getPixel(i, j + 1)), Color.blue(image.getPixel(i, j + 1)));
                pixel[4] = rgb(Color.red(image.getPixel(i + 1, j + 1)), Color.green(image.getPixel(i + 1, j + 1)), Color.blue(image.getPixel(i + 1, j + 1)));
                pixel[5] = rgb(Color.red(image.getPixel(i + 1, j)), Color.green(image.getPixel(i + 1, j)), Color.blue(image.getPixel(i + 1, j)));
                pixel[6] = rgb(Color.red(image.getPixel(i + 1, j - 1)), Color.green(image.getPixel(i + 1, j - 1)), Color.blue(image.getPixel(i + 1, j - 1)));
                pixel[7] = rgb(Color.red(image.getPixel(i, j - 1)), Color.green(image.getPixel(i, j - 1)), Color.blue(image.getPixel(i, j - 1)));
                pixel[8] = rgb(Color.red(image.getPixel(i, j)), Color.green(image.getPixel(i, j)), Color.blue(image.getPixel(i, j)));
                for (int k = 0; k < 9; k++) {
                    R[k] = Color.red(pixel[k]);
                    B[k] = Color.blue(pixel[k]);
                    G[k] = Color.green(pixel[k]);
                    alpha[k] = Color.alpha(pixel[k]);
                }
                Arrays.sort(R);
                Arrays.sort(G);
                Arrays.sort(B);
                pixBuff[j][i] = Color.argb(alpha[4], R[4], B[4], G[4]);*/
                //pixBuff[j][i] = image.getPixel(i,j);
                originalImg[j][i] = image.getPixel(i,j);
                //Log.d(TAG, "pixBuff[j][i]: " + pixBuff[j][i]);
                // MediumColor+=pixBuff[i][j];
                // Log.d(TAG, "MediumColor[j][i]: " + MediumColor);
            }

        }

        imgBuff = new int[image.getHeight()][image.getWidth()];
        imgBuff = originalImg;

    }

    public static void MedianFilter(int pixels[][])
    {
        int[] pixel=new int[9];
        int[] R=new int[9];
        int[] B=new int[9];
        int[] G=new int[9];
        int[] alpha=new int[9];
        int[][] pixBuff = new int[pixels.length][pixels[0].length];

        for (int j = 1; j < pixels.length - 1; j++) {
            for(int i = 1; i < pixels[0].length - 1; i++){
                pixel[0] = rgb(Color.red(pixels[i - 1][j - 1]), Color.green(pixels[i - 1][j - 1]), Color.blue(pixels[i - 1][j - 1]));
                pixel[1] = rgb(Color.red(pixels[i - 1][j]), Color.green(pixels[i - 1][j]), Color.blue(pixels[i - 1][j]));
                pixel[2] = rgb(Color.red(pixels[i - 1][j + 1]), Color.green(pixels[i - 1][j + 1]), Color.blue(pixels[i - 1][j + 1]));
                pixel[3] = rgb(Color.red(pixels[i][j + 1]), Color.green(pixels[i][j + 1]), Color.blue(pixels[i][j + 1]));
                pixel[4] = rgb(Color.red(pixels[i + 1][j + 1]), Color.green(pixels[i + 1][j + 1]), Color.blue(pixels[i + 1][j + 1]));
                pixel[5] = rgb(Color.red(pixels[i + 1][j]), Color.green(pixels[i + 1][j]), Color.blue(pixels[i + 1][j]));
                pixel[6] = rgb(Color.red(pixels[i + 1][j - 1]), Color.green(pixels[i + 1][j - 1]), Color.blue(pixels[i + 1][j - 1]));
                pixel[7] = rgb(Color.red(pixels[i][j - 1]), Color.green(pixels[i][j - 1]), Color.blue(pixels[i][j - 1]));
                pixel[8] = rgb(Color.red(pixels[i][j]), Color.green(pixels[i][j]), Color.blue(pixels[i][j]));
                for (int k = 0; k < 9; k++) {
                    R[k] = Color.red(pixel[k]);
                    B[k] = Color.blue(pixel[k]);
                    G[k] = Color.green(pixel[k]);
                    alpha[k] = Color.alpha(pixel[k]);
                }
                Arrays.sort(R);
                Arrays.sort(G);
                Arrays.sort(B);
                pixBuff[j][i] = Color.argb(alpha[4], R[4], B[4], G[4]);
            }

        }
        originalImg = new int[pixels.length][pixels[0].length];
        originalImg = pixBuff;
    }



    public static void MonohromFilter(int pixels[][])
    {
        int pixel;

        for (int j = 1; j < pixels.length - 1; j++) {
            for (int i = 1; i < pixels[0].length - 1; i++) {
                int sr = rgb(Color.red(pixels[j][i]), Color.green(pixels[j][i]), Color.blue(pixels[j][i])) / 3;
                pixels[j][i] =  sr <  -4000000 ? Integer.valueOf(Color.BLACK) : Integer.valueOf(Color.WHITE); /*   -MediumColor  */

            }
        }

        imgBuff = pixels;
    }

    public static Bitmap turnImg(int[][] pixBuff){

        originalImg = new int[pixBuff[0].length][pixBuff.length];

        for(int i=0;i< pixBuff.length;i++) {
            for (int j = 0; j < pixBuff[0].length; j++) {
                originalImg[pixBuff[0].length-j-1][i] = pixBuff[i][j];
            }
        }

        return ToImg(originalImg);
    }


    public static Bitmap ToImg(int[][] originalImg){
        Bitmap bitmap = Bitmap.createBitmap(originalImg[0].length, originalImg.length,
                Bitmap.Config.ARGB_8888);

        for(int i=1;i< originalImg.length -1;i++) {
            for (int j = 1; j < originalImg[0].length - 1; j++) {
                bitmap.setPixel(j, i, originalImg[i][j]);
            }
        }

        return bitmap;
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
