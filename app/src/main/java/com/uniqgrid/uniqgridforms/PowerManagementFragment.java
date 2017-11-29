package com.uniqgrid.uniqgridforms;


import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class PowerManagementFragment extends Fragment {



    Spinner spDgAvailability,spDgUsage;


    ArrayList<String> dgAvailabilitySpinnerItemsList = new ArrayList<String>();
    String[] dgAvailabilitySpinnerItems = {"Select","Yes","No"};

    ArrayList<String> dgUsageSpinnerItemsList = new ArrayList<String>();
    String[] dgUsageSpinnerItems = {"Select","Frequently","Rarely","Planning to buy one","No Plans"};


    Spinner spCutoutFreqRarely, spCutoutFreqDaily,spCutoutFreqWeekly,spCutoutFreqMonthly;
    Spinner spCutoutDurRarely,spCutoutDurDaily,spCutoutDurWeekly,spCutoutDurMonthly;

    ArrayList<String> spCutoutFreqSpinnerItemsList = new ArrayList<String>();
    String[] spCutoutFreqSpinnerItems = {"Select","0-1 times","1-3 times","3-7 times",">7 times"};

    ArrayList<String> spCutoutDurSpinnerItemsList = new ArrayList<String>();
    String[] spCutoutDurSpinnerItems = {"Select","0-1 Hours","1-2 Hours","2-3 Hours",">4 Hours"};


    ImageView ivDieselGenerator;
    TextView tvRemoveImgDieselGenerator;

    Uri outputFileUri;
    Uri selectedImageUri = null;

    Uri outputFileUriDieselGenerator;
    Uri selectedImageUriDieselGenerator = null;

    public static final int DIESEL_GENERATOR_IMG_RESULT = 101;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View customView = inflater.inflate(R.layout.fragment_power_management, container, false);



        spDgAvailability = (Spinner) customView.findViewById(R.id.spDgAvailability);
        spDgUsage = (Spinner) customView.findViewById(R.id.spDgUsage);

        spCutoutFreqRarely = (Spinner) customView.findViewById(R.id.spCutoutFreqRarely);
        spCutoutFreqDaily = (Spinner) customView.findViewById(R.id.spCutoutFreqDaily);
        spCutoutFreqWeekly = (Spinner) customView.findViewById(R.id.spCutoutFreqWeekly);
        spCutoutFreqMonthly = (Spinner) customView.findViewById(R.id.spCutoutFreqMonthly);

        spCutoutDurRarely = (Spinner) customView.findViewById(R.id.spCutoutDurRarely);
        spCutoutDurDaily = (Spinner) customView.findViewById(R.id.spCutoutDurDaily);
        spCutoutDurWeekly = (Spinner) customView.findViewById(R.id.spCutoutDurWeekly);
        spCutoutDurMonthly = (Spinner) customView.findViewById(R.id.spCutoutDurMonthly);

        ivDieselGenerator = (ImageView) customView.findViewById(R.id.ivDieselGenerator);
        tvRemoveImgDieselGenerator  = (TextView) customView.findViewById(R.id.tvRemoveImgDieselGenerator);

        // Setting onClick Listeners for Image Views
        ivDieselGenerator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tvRemoveImgDieselGenerator.getVisibility() == View.GONE){
                    requestImage(DIESEL_GENERATOR_IMG_RESULT);

                }else{
                    Intent intent = new Intent(getContext(),ViewImageActivity.class);
                    intent.putExtra("Uri",selectedImageUriDieselGenerator.toString());
                    intent.putExtra("Title","Image of Main Circuit Breaker(MCB Panel)");
                    startActivity(intent);
                }


            }
        });
        // Setting onClick Listeners for text view
        tvRemoveImgDieselGenerator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedImageUriDieselGenerator = null;
                Glide.with(getContext())
                        .load(selectedImageUriDieselGenerator)
                        .apply(new RequestOptions()
                                .placeholder(R.drawable.doc)
                                .dontAnimate()
                                .dontTransform())
                        .into(ivDieselGenerator);
                tvRemoveImgDieselGenerator.setVisibility(View.GONE);
            }
        });





        // DgAvailability Spinner
        ArrayAdapter spDgAvailabilityAdapter=new ArrayAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item,dgAvailabilitySpinnerItems);
        spDgAvailabilityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDgAvailability.setAdapter(spDgAvailabilityAdapter);

        // spDgUsage Spinner
        ArrayAdapter spDgUsageAdapter=new ArrayAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item,dgUsageSpinnerItems);
        spDgUsageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDgUsage.setAdapter(spDgUsageAdapter);


        // Spinners
        // Cutout Freq Spinners
        ArrayAdapter spCutoutFreqRarelyAdapter=new ArrayAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item,
                spCutoutFreqSpinnerItems);
        spCutoutFreqRarelyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCutoutFreqRarely.setAdapter(spCutoutFreqRarelyAdapter);

        ArrayAdapter spCutoutFreqDailyAdapter=new ArrayAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item,
                spCutoutFreqSpinnerItems);
        spCutoutFreqDailyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCutoutFreqDaily.setAdapter(spCutoutFreqDailyAdapter);


        ArrayAdapter spCutoutFreqWeeklyAdapter=new ArrayAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item,
                spCutoutFreqSpinnerItems);
        spCutoutFreqWeeklyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCutoutFreqWeekly.setAdapter(spCutoutFreqWeeklyAdapter);


        ArrayAdapter spCutoutFreqMonthlyAdapter=new ArrayAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item,
                spCutoutFreqSpinnerItems);
        spCutoutFreqMonthlyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCutoutFreqMonthly.setAdapter(spCutoutFreqMonthlyAdapter);


        // Cutout Duration Spinners
        ArrayAdapter spCutoutDurRarelyAdapter=new ArrayAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item,
                spCutoutDurSpinnerItems);
        spCutoutDurRarelyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCutoutDurRarely.setAdapter(spCutoutDurRarelyAdapter);

        ArrayAdapter spCutoutDurDailyAdapter=new ArrayAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item,
                spCutoutDurSpinnerItems);
        spCutoutDurDailyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCutoutDurDaily.setAdapter(spCutoutDurDailyAdapter);


        ArrayAdapter spCutoutDurWeeklyAdapter=new ArrayAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item,
                spCutoutDurSpinnerItems);
        spCutoutDurWeeklyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCutoutDurWeekly.setAdapter(spCutoutDurWeeklyAdapter);


        ArrayAdapter spCutoutDurMonthlyAdapter=new ArrayAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item,
                spCutoutDurSpinnerItems);
        spCutoutDurMonthlyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCutoutDurMonthly.setAdapter(spCutoutDurMonthlyAdapter);








        return  customView;
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


            outputFileUriDieselGenerator = Uri.fromFile(sdImageMainDirectory);
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(resultCode == RESULT_OK ){
            if(requestCode == DIESEL_GENERATOR_IMG_RESULT ){
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

        selectedImageUriDieselGenerator = selectedImageUri;
        Glide.with(getContext())
                .load(selectedImageUriDieselGenerator)
                .into(ivDieselGenerator);
        tvRemoveImgDieselGenerator.setVisibility(View.VISIBLE);




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

}
