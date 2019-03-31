package com.dev.rohith.faceattendance;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import android.app.Activity;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;


public class check extends Activity{

    int per_topple;
    double per_skid;
    ProgressBar progressbar;  //topple
    ProgressBar progressbar1; //skid
    TextView t;
    public RatingBar r;
    public RatingBar r1;


    protected void onCreate(Bundle savedInstanceState) {



        setContentView(R.layout.check);
        per_topple = check_topple(4800,1620,15,2,40);
        per_skid = check_skid(4800,0.1,15,40);
        r = (RatingBar) findViewById(R.id.ratingBar);
        r1 = (RatingBar) findViewById(R.id.ratingBar2);
        r.setRating(Float.parseFloat("2.5"));
        r1.setRating(Float.parseFloat("3.6"));
        super.onCreate(savedInstanceState);


    }












    public int check_topple(double r , double t , double phi , double h_t , double v_range){
        double h;
        int ctr=0;
        for(int i=0;i<v_range;i++){
            h = ((9.8*r*t)/2 + (Math.pow(i,2)*(t/2)*Math.tan(phi)))/(Math.pow(i,2) - (9.8*r*Math.tan(phi)));// Checking topple conditions
            if(h>h_t){
                ctr+=1;
            }

        }

        return (int)(ctr/v_range);
    }

    public int check_skid(double r , double mu , double phi , double v_range){
        double v;
        int ctr=0;
        for(int i=0;i<v_range;i++){
            v = Math.pow((10*r*(mu+Math.tan(phi)))/(1-Math.tan(phi)),0.5);// Checking skid conditions
            if(v<=i){
                ctr+=1;
            }

        }

        return (int)(ctr/v_range);
    }




}