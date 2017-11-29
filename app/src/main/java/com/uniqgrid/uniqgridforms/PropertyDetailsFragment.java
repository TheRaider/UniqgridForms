package com.uniqgrid.uniqgridforms;


import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.provider.CalendarContract.CalendarCache.URI;


/**
 * A simple {@link Fragment} subclass.
 */
public class PropertyDetailsFragment extends Fragment {



    int mYear,mMonth,mDay;
    String YearOfConstDate;
    Long dateValue;
    DatePickerDialog.OnDateSetListener dateSetListener;


    EditText etNoOfBuildings;
    Spinner spRoofType;
    TextView tvYearOfConstDate;
    LinearLayout llYearOfConstDate;
    RatingBar ratingRoofStrength;

    ImageView ivMCBPanel,ivElectricityMeter,ivBuildingFrontView,ivRooftop;

    public static final int MCB_PANEL_IMG_RESULT = 101;
    public static final int ELECTRICITY_METER_IMG_RESULT = 103;
    public static final int BUILDING_FRONT_VIEW_IMG_RESULT = 105;
    public static final int ROOF_TOP_IMG_RESULT = 107;



    Uri outputFileUri;
    Uri selectedImageUri = null;

    Uri outputFileUriMCBPanel;
    Uri selectedImageUriMCBPanel = null;

    Uri outputFileUriElectricityMeter;
    Uri selectedImageUriElectricityMeter = null;

    Uri outputFileUriBuildingFrontView;
    Uri selectedImageUriBuildingFrontView = null;

    Uri outputFileUriRooftop;
    Uri selectedImageUriRooftop = null;

    PickImageObject mcbPanel = new PickImageObject(MCB_PANEL_IMG_RESULT);
    PickImageObject electricityMeter = new PickImageObject(ELECTRICITY_METER_IMG_RESULT);
    PickImageObject builidingFrontView = new PickImageObject(BUILDING_FRONT_VIEW_IMG_RESULT);
    PickImageObject roofTop = new PickImageObject(ROOF_TOP_IMG_RESULT);


    TextView tvRemoveImgMCBPanel,tvRemoveImgElectricityMeter;
    TextView tvRemoveImgBuildingFrontView,tvRemoveImgRooftop;






    // Spinner Items for roof top
    ArrayList<String> roofTypeSpinnerItemsList = new ArrayList<String>();
    String[] roofTypeSpinnerItems = {"Select","Reinforced Concrete(RCC)","Asbestos Cement Sheet","Polycarbonate(uPVC)",
            "Metal Roofing"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View customView = inflater.inflate(R.layout.fragment_property_details, container, false);

        ivMCBPanel = (ImageView) customView.findViewById(R.id.ivMCBPanel);
        ivElectricityMeter = (ImageView) customView.findViewById(R.id.ivElectricityMeter);
        ivBuildingFrontView = (ImageView) customView.findViewById(R.id.ivBuildingFrontView);
        ivRooftop = (ImageView) customView.findViewById(R.id.ivRooftop);

        tvRemoveImgMCBPanel = (TextView) customView.findViewById(R.id.tvRemoveImgMCBPanel);
        tvRemoveImgElectricityMeter = (TextView) customView.findViewById(R.id.tvRemoveImgElectricityMeter);
        tvRemoveImgBuildingFrontView = (TextView) customView.findViewById(R.id.tvRemoveImgBuildingFrontView);
        tvRemoveImgRooftop = (TextView) customView.findViewById(R.id.tvRemoveImgRooftop);




        etNoOfBuildings = (EditText) customView.findViewById(R.id.etNoOfBuildings);
        spRoofType = (Spinner) customView.findViewById(R.id.spRooftype);


        tvYearOfConstDate = (TextView) customView.findViewById(R.id.tvYearOfConstDate);
        llYearOfConstDate = (LinearLayout) customView.findViewById(R.id.llYearOfConstDate);

        ratingRoofStrength = (RatingBar) customView.findViewById(R.id.ratingRoofStrength);


        setYearOfConstruction();
        etNoOfBuildings.setText("1");

        // Solarised Spinner
        ArrayAdapter spRoofTypeAdapter=new ArrayAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item,
                roofTypeSpinnerItems);
        spRoofTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRoofType.setAdapter(spRoofTypeAdapter);


        // Setting onClick Listeners for Image Views
        ivMCBPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tvRemoveImgMCBPanel.getVisibility() == View.GONE){
                    requestImage(MCB_PANEL_IMG_RESULT);

                }else{
                    Intent intent = new Intent(getContext(),ViewImageActivity.class);
                    intent.putExtra("Uri",selectedImageUriMCBPanel.toString());
                    intent.putExtra("Title","Image of Main Circuit Breaker(MCB Panel)");
                    startActivity(intent);
                }


            }
        });

        ivElectricityMeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tvRemoveImgElectricityMeter.getVisibility() == View.GONE){
                    requestImage(ELECTRICITY_METER_IMG_RESULT);

                }else{
                   Intent intent = new Intent(getContext(),ViewImageActivity.class);
                    intent.putExtra("Uri",selectedImageUriElectricityMeter.toString());
                    intent.putExtra("Title","Image of Electricity Meter");
                   startActivity(intent);
                }

            }
        });

        ivBuildingFrontView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(tvRemoveImgBuildingFrontView.getVisibility() == View.GONE){
                    requestImage(BUILDING_FRONT_VIEW_IMG_RESULT);
                }else{
                    Intent intent = new Intent(getContext(),ViewImageActivity.class);
                    intent.putExtra("Uri",selectedImageUriBuildingFrontView.toString());
                    intent.putExtra("Title","Image of the Building's Front View");
                    startActivity(intent);
                }


            }
        });

        ivRooftop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(tvRemoveImgRooftop.getVisibility() == View.GONE){
                    requestImage(ROOF_TOP_IMG_RESULT);

                }else{
                    Intent intent = new Intent(getContext(),ViewImageActivity.class);
                    intent.putExtra("Uri",selectedImageUriRooftop.toString());
                    intent.putExtra("Title","Image of the Rooftop");
                    startActivity(intent);

                }



            }
        });

        // Setting onClick Listeners for text view
        tvRemoveImgMCBPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedImageUriMCBPanel = null;
                Glide.with(getContext())
                        .load(selectedImageUriMCBPanel)
                        .apply(new RequestOptions()
                                .placeholder(R.drawable.doc)
                                .dontAnimate()
                                .dontTransform())
                        .into(ivMCBPanel);
                tvRemoveImgMCBPanel.setVisibility(View.GONE);
            }
        });

        tvRemoveImgElectricityMeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedImageUriElectricityMeter = null;
                Glide.with(getContext())
                        .load(selectedImageUriElectricityMeter)
                        .apply(new RequestOptions()
                                .placeholder(R.drawable.doc)
                                .dontAnimate()
                                .dontTransform())
                        .into(ivElectricityMeter);
                tvRemoveImgElectricityMeter.setVisibility(View.GONE);
            }
        });

        tvRemoveImgBuildingFrontView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedImageUriBuildingFrontView = null;
                Glide.with(getContext())
                        .load(selectedImageUriBuildingFrontView)
                        .apply(new RequestOptions()
                                .placeholder(R.drawable.doc)
                                .dontAnimate()
                                .dontTransform())
                        .into(ivBuildingFrontView);
                tvRemoveImgBuildingFrontView.setVisibility(View.GONE);
            }
        });


        tvRemoveImgRooftop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedImageUriRooftop = null;
                Glide.with(getContext())
                        .load(selectedImageUriRooftop)
                        .apply(new RequestOptions()
                                .placeholder(R.drawable.doc)
                                .dontAnimate()
                                .dontTransform())
                        .into(ivRooftop);
                tvRemoveImgRooftop.setVisibility(View.GONE);
            }
        });

        return customView;
    }


    public void requestImage(int IMG_RESULT){

        if(!hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)){

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);

        }else{

            // Determine Uri of camera image to save.
            final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "Uniqgrid" + File.separator);
            root.mkdirs();
            final String fname = "img"+ System.currentTimeMillis() + ".jpg";
            final File sdImageMainDirectory = new File(root, fname);
            switch(IMG_RESULT){
                case (MCB_PANEL_IMG_RESULT):
                    outputFileUriMCBPanel = Uri.fromFile(sdImageMainDirectory);
                    break;

                case (ELECTRICITY_METER_IMG_RESULT):
                    outputFileUriElectricityMeter = Uri.fromFile(sdImageMainDirectory);
                    break;

                case (BUILDING_FRONT_VIEW_IMG_RESULT):
                    outputFileUriBuildingFrontView = Uri.fromFile(sdImageMainDirectory);
                    break;

                case (ROOF_TOP_IMG_RESULT):
                    outputFileUriRooftop = Uri.fromFile(sdImageMainDirectory);
                    break;

                default:
                    outputFileUriMCBPanel = Uri.fromFile(sdImageMainDirectory);
                    break;
            }

            outputFileUri = Uri.fromFile(sdImageMainDirectory);



            //Get all the intents from Camera.
            final List<Intent> cameraIntents = new ArrayList<Intent>();
            final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            final PackageManager packageManager = getActivity().getPackageManager();
            final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
            for(ResolveInfo res : listCam) {
                final String packageName = res.activityInfo.packageName;
                final Intent intent = new Intent(captureIntent);
                intent.setComponent(new ComponentName(packageName, res.activityInfo.name));
                intent.setPackage(packageName);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                cameraIntents.add(intent);
            }

            // Filesystem(i.e Intent to Select image from system gallery apps)
            final Intent galleryIntent = new Intent();
            galleryIntent.setType("image/*");
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

            // Chooser of filesystem options.
            final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

            // Add the camera options.
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));

            startActivityForResult(chooserIntent, IMG_RESULT);
        }
    }

    public void setYearOfConstruction(){
        // Getting Current Date and Time
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        dateValue = c.getTimeInMillis();

        // Setting Default Time and Date
        YearOfConstDate = mDay + "-" + (mMonth + 1) + "-" + mYear;
        tvYearOfConstDate.setText("");

        SimpleDateFormat f1 = new SimpleDateFormat("dd-MM-yyyy");
        Date d1 = null;
        try {
            d1 = f1.parse(YearOfConstDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        final long time_to_add = dateValue - d1.getTime();

        // Date Picker
        dateSetListener = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                mYear = year;
                mMonth = monthOfYear;
                mDay = dayOfMonth;

                YearOfConstDate = mDay + "-" + (mMonth + 1) + "-" + mYear;
                tvYearOfConstDate.setText(YearOfConstDate);

                SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date d = f.parse(YearOfConstDate);

                    dateValue = d.getTime() + time_to_add;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };

        llYearOfConstDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),dateSetListener,mYear,mMonth,mDay);
                datePickerDialog.getWindow().setWindowAnimations(R.style.DialogAnimationUpDown);
                datePickerDialog.show();
            }
        });


    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (getActivity().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }
    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(resultCode == RESULT_OK ){
            if((requestCode == MCB_PANEL_IMG_RESULT )||
                    (requestCode == ELECTRICITY_METER_IMG_RESULT )||
                    (requestCode == BUILDING_FRONT_VIEW_IMG_RESULT )||
                    (requestCode == ROOF_TOP_IMG_RESULT)){
                getImage(requestCode,data);
            }
        }

    }


    public void getImage(int IMG_RESULT , Intent data){

        final boolean isCamera;
        if (data == null) {
            isCamera = true;
        } else {
            final String action = data.getAction();
            if (action == null) {
                isCamera = false;
            } else {
                isCamera = action.equals(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            }
        }


        if (isCamera) {
            selectedImageUri = outputFileUri;
        } else {
            selectedImageUri = data == null ? null : data.getData();
        }

        Log.d("Image Url ",IMG_RESULT + " "+ selectedImageUri+" "+ isCamera);


        File imgFile = new File(selectedImageUri.getPath());

        switch(IMG_RESULT){
            case (MCB_PANEL_IMG_RESULT):
                selectedImageUriMCBPanel = selectedImageUri;
                Glide.with(getContext())
                        .load(selectedImageUriMCBPanel)
                        .into(ivMCBPanel);
                tvRemoveImgMCBPanel.setVisibility(View.VISIBLE);

                break;

            case (ELECTRICITY_METER_IMG_RESULT):
                selectedImageUriElectricityMeter = selectedImageUri;
                Glide.with(getContext())
                        .load(selectedImageUriElectricityMeter)
                        .into(ivElectricityMeter);
                tvRemoveImgElectricityMeter.setVisibility(View.VISIBLE);
                break;

            case (BUILDING_FRONT_VIEW_IMG_RESULT):
                outputFileUriBuildingFrontView = selectedImageUri;
                Glide.with(getContext())
                        .load(outputFileUriBuildingFrontView)
                        .into(ivBuildingFrontView);
                tvRemoveImgBuildingFrontView.setVisibility(View.VISIBLE);
                break;

            case (ROOF_TOP_IMG_RESULT):
                selectedImageUriRooftop = selectedImageUri;
                Glide.with(getContext())
                        .load(selectedImageUriRooftop)
                        .into(ivRooftop);
                tvRemoveImgRooftop.setVisibility(View.VISIBLE);
                break;

            default:
                break;
        }




                 /*   if( imgFile.length()< (1024*1024)){

                        Picasso.with(getActivity())
                                .load(selectedImageUri)
                                .fit()
                                .centerCrop()
                                .into(ivMCBPanel);

                    }else{
                        Toast.makeText(getContext(),"Image Size too large",Toast.LENGTH_SHORT).show();
                    }
*/



        //ivMCBPanel.setImageURI(selectedImageUri);

    }

    public Point getDisplaySize(DisplayMetrics displayMetrics) {
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        return new Point(width, height);
    }

    public Point getDisplaySize(Display display) {
        Point size = new Point();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            display.getSize(size);
        } else {
            int width = display.getWidth();
            int height = display.getHeight();
            size = new Point(width, height);
        }

        return size;
    }
}
