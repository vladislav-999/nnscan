package com.example.vladislav.nnscan;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import static android.graphics.Color.rgb;
import static com.example.vladislav.nnscan.ScrollingActivity.Filt;
import static com.example.vladislav.nnscan.ScrollingActivity.editText;
import static com.example.vladislav.nnscan.ScrollingActivity.neironNet;
import static com.example.vladislav.nnscan.ScrollingActivity.segm;


public class TwoFragment extends Fragment {

    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_two, container, false);
        View view = inflater.inflate(R.layout.fragment_one, container, false);

        //Button buttonMedian = (Button) v.findViewById(R.id.buttonMedian);
        Button buttonMono = (Button) v.findViewById(R.id.buttonMono);
        Button negativeButton = (Button) v.findViewById(R.id.negativeButton);
        Button scanButton = (Button) v.findViewById(R.id.scanButton);

        FloatingActionButton fab;
        fab = (FloatingActionButton) v.findViewById(R.id.turnButton);

        final ImageView ivPhoto;
        ivPhoto = (ImageView) v.findViewById(R.id.ivPhoto);

//        final EditText editText = (EditText) view.findViewById(R.id.editText2);

        fab.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(Filt.originalImg != null) {
                    ivPhoto.setImageBitmap(Filt.turnImg(Filt.originalImg));
                }else{
                    Snackbar.make(v, "Спочатку відкрийте зображення", Snackbar.LENGTH_LONG).show();
                }
            }
        });

     /*   buttonMedian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Filt.originalImg != null) {
                    Filt.MedianFilter(Filt.originalImg);
                    ivPhoto.setImageBitmap(Filt.ToImg(Filt.originalImg));
                }else{
                    Snackbar.make(v, "Спочатку відкрийте зображення", Snackbar.LENGTH_LONG).show();
                }
            }
        });*/


        buttonMono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Filt.originalImg != null) {
                    for (int j = 1; j < Filt.originalImg.length - 1; j++) {
                        for (int i = 1; i < Filt.originalImg[0].length - 1; i++) {
                            int sr = rgb(Color.red(Filt.originalImg[j][i]), Color.green(Filt.originalImg[j][i]), Color.blue(Filt.originalImg[j][i])) / 3;
                            Filt.originalImg[j][i] =  sr <  -4000000 ? Integer.valueOf(Color.BLACK) : Integer.valueOf(Color.WHITE); /*   -MediumColor  */

                        }
                    }
                    ivPhoto.setImageBitmap(Filt.ToImg(Filt.originalImg));
                }else{
                    Snackbar.make(v, "Спочатку відкрийте зображення", Snackbar.LENGTH_LONG).show();
                }
            }
        });


        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Filt.originalImg != null) {
                    for (int j = 1; j < Filt.originalImg.length - 1; j++) {
                        for (int i = 1; i < Filt.originalImg[0].length - 1; i++) {
                            int sr = rgb(Color.red(Filt.originalImg[j][i]), Color.green(Filt.originalImg[j][i]), Color.blue(Filt.originalImg[j][i])) / 3;
                            Filt.originalImg[j][i] = sr > -4000000 ? Integer.valueOf(Color.BLACK) : Integer.valueOf(Color.WHITE); /*   -MediumColor  */

                        }
                    }
                    ivPhoto.setImageBitmap(Filt.ToImg(Filt.originalImg));
                }else{
                    Snackbar.make(v, "Спочатку відкрийте зображення", Snackbar.LENGTH_LONG).show();
                }
            }
        });


        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Filt.originalImg != null) {
                    segm = new Segmentation(Filt.originalImg, neironNet, editText, null);
                    Snackbar.make(v, "Зображення відскановано, перейдіть до вкладки \"Текст\"", Snackbar.LENGTH_LONG).show();
                }else{
                    Snackbar.make(v, "Спочатку відкрийте зображення", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        // Inflate the layout for this fragment
        return v;
    }

}