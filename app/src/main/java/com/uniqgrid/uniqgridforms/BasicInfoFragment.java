package com.uniqgrid.uniqgridforms;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class BasicInfoFragment extends Fragment {



    Spinner spSegment,spIsSolarised;
    EditText etTimestamp,etNameOfEst,etSancLoad,etStreet,etPlace,etCity,etState;
    EditText etPincode,etFirstName,etLastName,etAccOwnerPhone,etAccOwnerEmail;
    EditText etPocFirstName,etPocLastName,etPocPhone,etPocEmail;
    ImageView ivLocation,ivAccOwnerPhone,ivPocPhone;


    ArrayList<String> segmentSpinnerItemsList = new ArrayList<String>();
    String[] segmentSpinnerItems = {"Select","Institutional","Commercial","Industrial","Residential","Others"};


    ArrayList<String> solarisedSpinnerItemsList = new ArrayList<String>();
    String[] solarisedSpinnerItems = {"Select","Yes","No","In talk with other Vendors"};


    public static final int ACC_OWNER_PHONE = 104;
    public static final int POC_PHONE = 105;
    public static final int PLACE_PICKER_REQUEST = 1;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View customView =  inflater.inflate(R.layout.fragment_basic_information, container, false);


        etTimestamp = (EditText) customView.findViewById(R.id.etTimestamp);

        spSegment = (Spinner) customView.findViewById(R.id.spSegment);
        spIsSolarised = (Spinner) customView.findViewById(R.id.spIsSolarised);


        etNameOfEst = (EditText) customView.findViewById(R.id.etNameOfEst);
        etSancLoad = (EditText) customView.findViewById(R.id.etSancLoad);

        etStreet = (EditText) customView.findViewById(R.id.etStreet);
        etPlace = (EditText) customView.findViewById(R.id.etPlace);
        etCity = (EditText) customView.findViewById(R.id.etCity);
        etState = (EditText) customView.findViewById(R.id.etState);
        etPincode = (EditText) customView.findViewById(R.id.etPincode);

        etFirstName = (EditText) customView.findViewById(R.id.etFirstName);
        etLastName = (EditText) customView.findViewById(R.id.etLastName);
        etAccOwnerPhone = (EditText) customView.findViewById(R.id.etAccOwnerPhone);
        etAccOwnerEmail = (EditText) customView.findViewById(R.id.etAccOwnerEmail);

        etPocFirstName = (EditText) customView.findViewById(R.id.etPocFirstName);
        etPocLastName = (EditText) customView.findViewById(R.id.etPocLastName);
        etPocPhone = (EditText) customView.findViewById(R.id.etPocPhone);
        etPocEmail = (EditText) customView.findViewById(R.id.etPocEmail);

        ivLocation = (ImageView) customView.findViewById(R.id.ivLocation);
        ivAccOwnerPhone = (ImageView) customView.findViewById(R.id.ivAccOwnerPhone);
        ivPocPhone = (ImageView) customView.findViewById(R.id.ivPocPhone);



        // Getting Current time
        Calendar cal = Calendar.getInstance();
        Date currentLocalTime = cal.getTime();

        DateFormat date = new SimpleDateFormat("dd-MM-yyy HH:mm:ss z");
        String localTime = date.format(currentLocalTime);

        etTimestamp.setText(localTime);

        populateSpinnerItems();

        // Segment Spinner
        ArrayAdapter spSegmentAdapter=new ArrayAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item,segmentSpinnerItems);
        spSegmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSegment.setAdapter(spSegmentAdapter);

        // Solarised Spinner
        ArrayAdapter spSolarisedAdapter=new ArrayAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item,solarisedSpinnerItems);
        spSegmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spIsSolarised.setAdapter(spSolarisedAdapter);



       ivAccOwnerPhone.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               //Getting Phone Number
               Intent intent= new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
               startActivityForResult(intent, ACC_OWNER_PHONE);
           }
       });

       ivPocPhone.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               //Getting Phone Number
               Intent intent= new Intent(Intent.ACTION_PICK,  ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
               startActivityForResult(intent, POC_PHONE);
           }
       });


       ivLocation.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
               try {
                   startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
               } catch (GooglePlayServicesRepairableException e) {
                   e.printStackTrace();
               } catch (GooglePlayServicesNotAvailableException e) {
                   e.printStackTrace();
               }
           }
       });


        return  customView;
    }


    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (ACC_OWNER_PHONE) :
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    String name = "";
                    String number = "";
                        try{
                            Cursor c =  getContext().getContentResolver().query(contactData, null, null, null, null);
                            if (c.moveToFirst()) {
                                name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                                number =  c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            }
                        }catch (Exception e){
                            Toast.makeText(getContext(),"Not able to pick contact",Toast.LENGTH_SHORT).show();
                        }
                        etAccOwnerPhone.setText(number);
                        //  Fetch other Contact details as you want to use

                    }
                break;

            case (POC_PHONE) :
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    String name = "";
                    String number = "";
                    try{
                        Cursor c =  getContext().getContentResolver().query(contactData, null, null, null, null);
                        if (c.moveToFirst()) {
                             name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                             number = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            //  Fetch other Contact details as you want to use

                        }
                        c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    }catch (Exception e){
                        Toast.makeText(getContext(),"Not able to pick contact",Toast.LENGTH_SHORT).show();
                    }
                    etPocPhone.setText(number);
                    //  Fetch other Contact details as you want to us
                }
                break;


            case (PLACE_PICKER_REQUEST) :
                if(data!=null) {
                    Place place = PlacePicker.getPlace(getContext(), data);
                    setLocation(place);
                }else{
                    Toast.makeText(getContext(),"Not able to pick place",Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    String stAddress1="",stAddress2="",address="",city="",state="",pincode="";

    public void setLocation(Place place){
        Log.d("Location Selected ",place!=null?place.getName().toString():"null");
        if(place !=null) {


            getAddressDetails(place);

            etStreet.setText(stAddress1);
            etPlace.setText(stAddress2);
            etCity.setText(city);
            etState.setText(state);
            etPincode.setText(pincode);

        }
    }

    public void getAddressDetails(Place place) {
        String country="";
        String latitude="",longitude="";

        if (place.getAddress() != null) {
            String[] addressSlice = place.getAddress().toString().split(", ");
            country = addressSlice[addressSlice.length - 1];
            if (addressSlice.length > 1) {
                String[] stateAndPostalCode = addressSlice[addressSlice.length - 2].split(" ");
                if (stateAndPostalCode.length > 1) {
                    pincode = stateAndPostalCode[stateAndPostalCode.length - 1];
                    state = "";
                    for (int i = 0; i < stateAndPostalCode.length - 1; i++) {
                        state += (i == 0 ? "" : " ") + stateAndPostalCode[i];
                    }
                } else {
                    state = stateAndPostalCode[stateAndPostalCode.length - 1];
                }
            }
            if (addressSlice.length > 2)
                city = addressSlice[addressSlice.length - 3];
            if (addressSlice.length == 4)
                stAddress1 = addressSlice[0];
            else if (addressSlice.length > 3) {
                stAddress2 = addressSlice[addressSlice.length - 4];
                stAddress1 = "";
                for (int i = 0; i < addressSlice.length - 4; i++) {
                    stAddress1 += (i == 0 ? "" : ", ") + addressSlice[i];
                }
            }
        }
        if(place.getLatLng()!=null)
        {
            latitude = "" + place.getLatLng().latitude;
            longitude = "" + place.getLatLng().longitude;
        }

        address = stAddress1+" "+ stAddress2;
    }

    public void populateSpinnerItems(){
        segmentSpinnerItemsList.addAll(Arrays.asList(segmentSpinnerItems));



    }

    public void createSegmentDialog(){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("Select a Choice");
        dialog.setSingleChoiceItems(segmentSpinnerItems, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                spSegment.setContentDescription(segmentSpinnerItemsList.get(i));
            }
        });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialog.create();
    }

}
