package com.aadyasystemsllc.spark;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;

public class ItemFragment extends Fragment {

    private static final String POSITON = "position";
    private static final String SCALE = "scale";
    private static final String DRAWABLE_RESOURE = "resource";

    private int screenWidth;
    private int screenHeight;

    private int[] imageArray = new int[]{R.drawable.common_google_signin_btn_icon_dark, R.drawable.ic_launcher_background,
           };
    private static ArrayList<ParkingDetails> parkingDetails;

    public static Fragment newInstance(Context context, int pos, float scale) {
        Bundle b = new Bundle();
        b.putInt(POSITON, pos);
        b.putFloat(SCALE, scale);

        return Fragment.instantiate(context, ItemFragment.class.getName(), b);
    }

    public static Fragment newInstance(Context context, int pos, float scale, ArrayList<ParkingDetails> parkingDetailsArrayList) {
        Bundle b = new Bundle();
        b.putInt(POSITON, pos);
        b.putFloat(SCALE, scale);
        parkingDetails = parkingDetailsArrayList;

        return Fragment.instantiate(context, ItemFragment.class.getName(), b);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWidthAndHeight();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        final int postion = this.getArguments().getInt(POSITON);
        float scale = this.getArguments().getFloat(SCALE);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(screenWidth / 4, screenHeight / 4);
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.fragment_image, container, false);
TextView txtparking=(TextView) linearLayout.findViewById(R.id.txtparking);
        TextView textView = (TextView) linearLayout.findViewById(R.id.text);
        TextView number =(TextView) linearLayout.findViewById(R.id.mobilenum);
        TextView book=(TextView) linearLayout.findViewById(R.id.book);
        TextView viewinmap=(TextView)linearLayout.findViewById(R.id.viewinmap);
        CarouselLinearLayout root = (CarouselLinearLayout) linearLayout.findViewById(R.id.root_container);

        ImageView imageView = (ImageView) linearLayout.findViewById(R.id.pagerImg);

        //  textView.setText("Carousel item: " + postion);
        imageView.setLayoutParams(layoutParams);
        //imageView.setImageResource(imageArray[postion]);

        if(parkingDetails!=null)
        {
           Picasso.get().load(parkingDetails.get(postion).getImageUrl()).placeholder(R.drawable.parkingimage).error(R.drawable.parkingimage).noFade().into(imageView);
         //   Picasso.get().load(R.drawable.ic_launcher_background).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background).noFade().into(imageView);
            txtparking.setText(parkingDetails.get(postion).parkingName);
            textView.setText(parkingDetails.get(postion).parkingLocation);
            number.setText(parkingDetails.get(postion).mobileNumber);
        }



        viewinmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

              /*  String uri = "http://maps.google.com/maps?f=d&hl=en&saddr="+latitude1+","+longitude1+"&daddr="+latitude2+","+longitude2;
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(Intent.createChooser(intent, "Select an application"));*/
            }
        });


        //handling click event
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(getActivity(), ImageDetailsActivity.class);
                intent.putExtra(DRAWABLE_RESOURE, imageArray[postion]);
                startActivity(intent);*/
            }
        });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BookingActivity.class);
                if (!parkingDetails.get(postion).parkingName.isEmpty())
                {
                    intent.putExtra("parkingtitle",""+parkingDetails.get(postion).parkingName);
                    startActivity(intent);
                }else {
                    intent.putExtra("parkingtitle","Parking");
                    startActivity(intent);
                }

            }
        });

        root.setScaleBoth(scale);


        return linearLayout;    }

    /**
     * Get device screen width and height
     */
    private void getWidthAndHeight() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenHeight = displaymetrics.heightPixels;
        screenWidth = displaymetrics.widthPixels;
    }
}
