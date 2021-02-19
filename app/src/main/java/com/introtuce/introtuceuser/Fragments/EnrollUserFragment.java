package com.introtuce.introtuceuser.Fragments;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.introtuce.introtuceuser.Model.DatabaseUser;
import com.introtuce.introtuceuser.R;

import java.io.FileNotFoundException;
import java.io.IOException;

public class EnrollUserFragment extends Fragment {
    private EditText first_name, last_name, dob, gender, country, state, hometown, telephone_number, phone_number;
    private CardView add_user;
    private ImageView select_image;
    private TextView textView;
    StorageReference storageReference;
    Uri Imageuri, SuccessUri;
    public static final int GET_FROM_GALLERY = 1;


    private static final String ARG_SECTION_NUMBER = "section_number";

    public static EnrollUserFragment newInstance(int index) {
        EnrollUserFragment fragment = new EnrollUserFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_enroll_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //EditText
        first_name = view.findViewById(R.id.first_name);
        last_name = view.findViewById(R.id.last_name);
        dob = view.findViewById(R.id.dob);
        gender = view.findViewById(R.id.gender);
        country = view.findViewById(R.id.county);
        state = view.findViewById(R.id.state);
        hometown = view.findViewById(R.id.home_town);
        telephone_number = view.findViewById(R.id.telephone);
        phone_number = view.findViewById(R.id.phone);
        select_image = view.findViewById(R.id.profile_image);
        textView = view.findViewById(R.id.select_image_text);

        storageReference = FirebaseStorage.getInstance().getReference();


        //CardView
        add_user = view.findViewById(R.id.add_user_button);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_image.performClick();
            }
        });

        add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkinput()){
                    save_profile();

                }
            }
        });

        select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);

            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Imageuri = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), Imageuri);
                select_image.setImageBitmap(bitmap);
                uploadtofirebase(Imageuri);
                //sharedPreferences1();
            } catch (FileNotFoundException e) {
                //  Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                //  Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void uploadtofirebase(Uri imageuri){

        StorageReference storageReference1 = storageReference.child(System.currentTimeMillis()+ "."+ getFileExtension(imageuri));

        storageReference1.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        SuccessUri = uri;
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void save_profile() {

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//
//        myRef.setValue("Hello, World!");

        String data="";
        if(SuccessUri!=null){
            data =  SuccessUri.getPath();
        }
        else {
            data ="";
        }
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
        String userId = mDatabase.push().getKey();

        DatabaseUser user = new DatabaseUser(first_name.getText().toString(),
                                                last_name.getText().toString(),
                                                dob.getText().toString(),
                                                gender.getText().toString(),
                                                country.getText().toString(),
                                                state.getText().toString(),
                                                hometown.getText().toString(),
                                                phone_number.getText().toString(),
                                                telephone_number.getText().toString(),
                                                SuccessUri.toString());
        mDatabase.child(userId).setValue(user);
        Toast.makeText(getContext(), "User Updated Successfully", Toast.LENGTH_SHORT).show();
    }

    private boolean checkinput() {

        if (first_name.length() == 0) {
            first_name.setError("Enter First Name");
            return false;
        }

        if (last_name.length() == 0) {
            last_name.setError("Enter Last Name");
            return false;
        }

        if (dob.length() == 0) {
            dob.setError("Enter Date of Birth");
            return false;
        }

        if (gender.length() == 0) {
            gender.setError("Please select Gender");
            return false;
        }

        if (country.length() == 0) {
            country.setError("Enter Country");
            return false;
        }

        if (state.length() == 0) {
            state.setError("Enter State");
            return false;
        }

        if (hometown.length() == 0) {
            hometown.setError("Enter Hometown");
            return false;
        }

        if (phone_number.length() == 0) {
            phone_number.setError("Please enter Phone Number");
            return false;
        }

        if (telephone_number.length() == 0) {
            telephone_number.setError("Please enter Telephone Number");
            return false;
        }

        if (SuccessUri == null) {
            Toast.makeText(getContext(), "Please Select a Image to Continue", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}