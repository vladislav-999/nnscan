package com.example.vladislav.nnscan;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import static android.content.ContentValues.TAG;

/**
 * Created by Vladislav_PC on 13.06.2017.
 */

public class Segmentation {


    public static int[][] pixBuff;
    final int NWIDTH = 16;

    public Segmentation(int[][] pixBuff, NeiroNet nw, TextView mResult, ImageView ivPhoto)
    {
        boolean lineFlag = false;
        boolean symbolFlag = false;
        boolean lineStartFlag = false;
        int coordinate = 0;
        int coordinate1 = 0;
        int startLine = 0;
        int startColumn = 0;
        int spaceCount = 0;
        int spaceWidth = 0;
        int pixels[][] = new int[pixBuff.length][pixBuff[0].length];
        int pixels1[][] = new int[pixBuff.length][pixBuff[0].length];
        int[][] pixelsChenged;
        int[][] arr;
        int count = 0;
        int k2= 0;
        int k3 = 0;

        String s = "";

        for (int j = 0; j < pixBuff.length; j++) {
            lineFlag = false;
            for(int i = 0; i < pixBuff[0].length; i++){

                if(pixBuff[j][i] == Color.BLACK){
                    lineFlag = true;
                    if(coordinate == 0){
                        coordinate = j;
                    }
                }
            }
            if(lineFlag == true){
                startLine++;
            }else if(startLine > 0){

                pixels = new int[startLine][pixBuff[0].length];
                count = 0;

                for(k2 = 0; k2 < startLine; k2++){
                    pixels[k2] = pixBuff[coordinate+k2];
                    count++;
                }
                for (int k1 = 0; k1 < pixels[0].length; k1++) {
                    symbolFlag = false;
                    for ( k2 = 0; k2 < count; k2++) {
                        if(pixels[k2][k1] == Color.BLACK){
                            symbolFlag = true;
                            if(coordinate1 == 0){
                                coordinate1 = k1;
                            }
                        }
                    }
                    if(symbolFlag == false){
                        spaceCount++;
                    }
                    if(symbolFlag == true){
                        startColumn++;
                    }else if(startColumn > 0) {
                        if(spaceWidth != 0){
                            int sCount = spaceCount / spaceWidth;
                            for(int space = 0; space < sCount; space++){
                                s += " ";
                            }
                        }
                        else{
                            spaceWidth = startColumn /2;
                            int sCount = spaceCount / spaceWidth;
                            for(int space = 0; space < sCount; space++){
                                s += " ";
                            }
                        }

                        if(startColumn > NWIDTH && count > NWIDTH) {
                            pixels1 = new int[count][startColumn];
                            pixelsChenged = new int[count][startColumn];

                            for (k2 = 0; k2 < startColumn; k2++) {
                                for (k3 = 0; k3 < count; k3++) {
                                    pixels1[k3][k2] = pixels[k3][coordinate1 + k2];
                                    if (pixels1[k3][k2] == -16777216) {
                                        pixelsChenged[k3][k2] = 1;
                                    } else {
                                        pixelsChenged[k3][k2] = 0;
                                    }
                                }
                            }

                            int[][] arr1 = CutImage(pixelsChenged);
                            arr = NeiroGraphUtils.LeadArray(arr1, new int[NWIDTH][NWIDTH]);

                            s += nw.CheckLitera(arr);
                        }

                        startColumn = 0;
                        spaceCount = 0;
                        coordinate1 = 0;
                    }
                }
                startLine = 0;
                coordinate = 0;
                s += "\n";
            }
        }
        mResult.setText(s);
    }

    public int[][] CutImage(int[][] arr){
        int[][] res = null;

        boolean lineFlag = false;
        int coordinate = 0;
        int startLine = 0;

        Log.d(TAG, "l: " + arr.length);
        for (int j = 0; j < arr.length; j++) {
            lineFlag = false;
            for (int i = 0; i < arr[0].length; i++) {

                if (arr[j][i] != 0) {
                    lineFlag = true;
                    if (coordinate == 0) {
                        coordinate = j;
                    }
                }
            }
            if(j == arr.length -1){
                lineFlag=false;
            }
            if (lineFlag == true) {
                startLine++;
            } else if (startLine > 0 ) {
                res = new int[startLine][arr[0].length];

                for (int k2 = 0; k2 < startLine; k2++) {
                    res[k2] = arr[coordinate + k2];
                }

            }
        }
        return res;
    }

    public Bitmap makeImg(int[][] pixels, int[][] arr){
        Bitmap bitmap = Bitmap.createBitmap(arr[0].length, arr.length,
                Bitmap.Config.ARGB_8888);

        for(int i=0; i< arr.length -1;i++) {
            for (int j = 0; j < arr[0].length - 1; j++) {
                if(arr[i][j] == 1) {
                    bitmap.setPixel(j, i, Color.BLACK);
                }else{
                    bitmap.setPixel(j, i, Color.WHITE);
                }
            }
        }

        return bitmap;
    }

}
