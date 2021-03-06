package com.brosis.doubledate.Users;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import de.hdodenhof.circleimageview.CircleImageView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.brosis.doubledate.newtab.NewTabHome;
import com.brosis.doubledate.storage.LocalStorage;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.brosis.doubledate.CodeClasses.ApiRequest;
import com.brosis.doubledate.CodeClasses.Callback;
import com.brosis.doubledate.CodeClasses.Functions;
import com.brosis.doubledate.CodeClasses.Variables;
import com.brosis.doubledate.Main_Menu.RelateToFragment_OnBack.RootFragment;
import com.brosis.doubledate.R;
import com.google.android.material.tabs.TabLayout;
import com.labo.kaji.fragmentanimations.MoveAnimation;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */

// in this class we will get all the details of spacific user and show it here
public class User_detail_F extends Fragment implements View.OnClickListener {


    View view;
    Context context;

    ImageButton  move_downbtn;

    RelativeLayout  username_layout;
    ScrollView scrollView;

    TextView username_txt, bottom_age,bottom_job_txt,bottom_school_txt,bottom_location_text,bottom_about_txt;

    TextView bottom_report_txt;


    String partner_code,personal_code,image1,image2,image3,image4,image5,image6,company,school;
    Nearby_User_Get_Set data_item;
    String first_name,last_name,ptrAge,ptrLocation,ptrBirthday,ptrGender,ptrAbout,prtImage,ptrJobTitle;

    ImageButton profile_menu;
    private ImageView ivRemovePartner;
    String from_where;
    //partner ids
    CircleImageView user_image;
    LinearLayout partner_bottom_layout;
    private TextView partner_bottom_school_txt,tvPartnerName,tvPartnerAge,partner_bottom_job_txt,partner_school_layout,partner_bottom_location_txt,partner_bottom_gender,partner_bottom_about_txt;
    public User_detail_F() {

    }

/*    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_detail_);

        context = getApplicationContext();
        Bundle extras = getIntent().getExtras();
        Log.d("Bundle", "TopCategoryProductList");
        if (extras != null) {
            data_item= (Nearby_User_Get_Set) extras.getSerializable("data");

        }
        from_where=extras.getString("from_where");
        scrollView = findViewById(R.id.scrollView);
        username_layout = findViewById(R.id.username_layout);

        move_downbtn = findViewById(R.id.move_downbtn);
        move_downbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();


            }
        });
        YoYo.with(Techniques.BounceInDown)
                .duration(800)
                .playOn(move_downbtn);

        init_bottom_view();


        if(from_where!=null && from_where.equals("user_list")){
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)scrollView.getLayoutParams();
            params.setMargins(0, 0, 0, 0);
            scrollView.setLayoutParams(params);
        }
        Set_Slider();
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_detail_, container, false);
        context = getContext();



        Bundle bundle=getArguments();
        if(bundle!=null)
        {
         data_item= (Nearby_User_Get_Set) bundle.getSerializable("data");
            from_where=bundle.getString("from_where");
            first_name=bundle.getString("first_name");
            last_name=bundle.getString("last_name");
            ptrAbout=bundle.getString("about_me");
            ptrJobTitle=bundle.getString("job_title");
            ptrGender=bundle.getString("gender");
            ptrBirthday=bundle.getString("birthday");
            company=bundle.getString("company");
            school=bundle.getString("school");
            ptrAge=bundle.getString("age");
            partner_code=bundle.getString("partner_code");
            personal_code=bundle.getString("personal_code");
            image1=bundle.getString("image1");
            image2=bundle.getString("image2");
            image3=bundle.getString("image3");
            image4=bundle.getString("image4");
            image5=bundle.getString("image5");
            image6=bundle.getString("image6");
            Log.e("first_name",": "+first_name);
            //Log.e("last_name",": "+last_name);
        }

        partner_bottom_layout = view.findViewById(R.id.partner_bottom_layout);
        scrollView = view.findViewById(R.id.scrollView);
        username_layout = view.findViewById(R.id.username_layout);
        Set_Slider();
        move_downbtn = view.findViewById(R.id.move_downbtn);
        move_downbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 getActivity().onBackPressed();


            }
        });



        YoYo.with(Techniques.BounceInDown)
                .duration(800)
                .playOn(move_downbtn);

        init_bottom_view();


        if(from_where!=null && from_where.equals("user_list")){
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)scrollView.getLayoutParams();
            params.setMargins(0, 0, 0, 0);
            scrollView.setLayoutParams(params);
        }


        return view;

    }


    // this method will initialize all the views and set the data in that view
    public void init_bottom_view() {

        ivRemovePartner=view.findViewById(R.id.ivRemovePartner);
        if(LocalStorage.getPartnerCode(getActivity()).equals(partner_code)){
            ivRemovePartner.setVisibility(View.VISIBLE);
        }else{
            ivRemovePartner.setVisibility(View.GONE);
        }
        ivRemovePartner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JSONObject parameters = new JSONObject();
                try {
                    parameters.put("fb_id", NewTabHome.user_id);
                    //parameters.put("partner_code",partner_code);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Functions.Show_loader(context,false,false);
                ApiRequest.Call_Api(context, Variables.remove_partner_info, parameters, new Callback() {
                    @Override
                    public void Responce(String resp) {
                        Functions.cancel_loader();
                        //Parse_edit_data(resp);
                        try {
                            JSONObject response = new JSONObject(resp);
                            JSONArray jsonArray=response.optJSONArray("msg");
                            Toast.makeText(context, ""+jsonArray.optJSONObject(0).optString("response"), Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        profile_menu=view.findViewById(R.id.profile_menu);
        profile_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayPopupWindow(v);
            }
        });

        //partner ids
        user_image = view.findViewById(R.id.user_image);
        partner_bottom_school_txt = view.findViewById(R.id.partner_bottom_school_txt);
        if(first_name == null){
            partner_bottom_layout.setVisibility(View.GONE);
        }else{
            partner_bottom_layout.setVisibility(View.VISIBLE);
        }
        if(company != null) {

            if (company.equals("")) {
                partner_bottom_school_txt.setText(company);
            } else if (school.equals("")) {
                partner_bottom_school_txt.setText(school);
            } else {
                partner_bottom_school_txt.setText(getString(R.string.school_college));
            }
        }


        //

        tvPartnerName = view.findViewById(R.id.tvPartnerName);
        tvPartnerName.setText(first_name+" "+last_name);
        tvPartnerAge = view.findViewById(R.id.tvPartnerAge);
        tvPartnerAge.setText(ptrAge);
        partner_bottom_job_txt = view.findViewById(R.id.partner_bottom_job_txt);
        partner_bottom_job_txt.setText(ptrJobTitle);
        partner_bottom_location_txt = view.findViewById(R.id.partner_bottom_location_txt);
        partner_bottom_gender = view.findViewById(R.id.partner_bottom_gender);
        partner_bottom_gender.setText(ptrGender);
        partner_bottom_about_txt = view.findViewById(R.id.partner_bottom_about_txt);
        partner_bottom_about_txt.setText(ptrAbout);
        //http://doubledate.brosisbiz.com/android/API/images/3220684711312769_1255059775.jpg
        Glide.with(context)
                .load(Variables.image_base_url+image1)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)
                        .placeholder(context.getResources().getDrawable(R.drawable.image_placeholder)).centerCrop())
                .skipMemoryCache(true)
                .into(user_image);


        username_txt = view.findViewById(R.id.username_txt);
        username_txt.setText(data_item.getFirst_name());


        bottom_age = view.findViewById(R.id.bottom_age);


        if(!data_item.getBirthday().equals(""))
        bottom_age.setText(", "+data_item.getBirthday());


        bottom_job_txt=view.findViewById(R.id.bottom_job_txt);
        if(data_item.getJob_title().equals("") & !data_item.getCompany().equals("")){

            bottom_job_txt.setText(data_item.getJob_title());

        }else if(data_item.getCompany().equals("") && !data_item.getJob_title().equals("") ){

            bottom_job_txt.setText(data_item.getJob_title());

        }
        else if(data_item.getCompany().equals("") && data_item.getJob_title().equals("") ){
            view.findViewById(R.id.job_layout).setVisibility(View.GONE);
        }
        else {
            bottom_job_txt.setText(data_item.getJob_title()+" at "+data_item.getSchool());

        }


        bottom_school_txt=view.findViewById(R.id.bottom_school_txt);
        if(data_item.getSchool().equals("")){
            view.findViewById(R.id.school_layout).setVisibility(View.GONE);
        }else {
            bottom_school_txt.setText(data_item.getSchool());
        }




        bottom_location_text=view.findViewById(R.id.bottom_location_txt);

       // String[] splited = data_item.getLocation().split("\\s+");
       // float kilomtr = convertMilesToKilom(Float.parseFloat(splited[0]));
       // String s = String.format("%.2f", kilomtr);
        bottom_location_text.setText(data_item.getLocation());
       // bottom_location_text.setText(s+" Km. away");

        bottom_about_txt=view.findViewById(R.id.bottom_about_txt);
        if(data_item.getAbout().equals("")){
            bottom_about_txt.setVisibility(View.GONE);
        }
        bottom_about_txt.setText(data_item.getAbout());


        bottom_report_txt=view.findViewById(R.id.bottom_report_txt);
        bottom_report_txt.setText(context.getResources().getString(R.string.report)+" "+data_item.getFirst_name());
        bottom_report_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Report_User_alert();
            }
        });
    }

    public float convertMilesToKilom(float kms){
        //  float miles=0.621371f;
        //float miles = 0.621371f * kms;//use if kilo to miles
        float miles = kms / 0.621371f ;//use if miles to kilo
        return miles;
    }

    // when all the animation is done then we will place a data into the view
    /*@Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter) {
            Animation anim= MoveAnimation.create(MoveAnimation.UP, enter, 200);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}
                @Override
                public void onAnimationEnd(Animation animation) {
                    // when animation is done then we will show the picture slide
                    // becuase animation in that case will show fulently

                    Set_Slider();
                    //fill_data();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {}
            });
            return anim;

        } else {
            return MoveAnimation.create(MoveAnimation.DOWN, enter, 200);
        }
    }*/



    private ViewPager mPager;
    public void Set_Slider(){

        mPager = (ViewPager)view. findViewById(R.id.image_slider_pager);

        try {

            mPager.setAdapter(new Images_sliding_adapter(getActivity(),data_item.getImagesurl()));
        }
        catch (NullPointerException e){
            e.getCause();
        }

         mPager.setCurrentItem(0);

        TabLayout indicator = (TabLayout)view.findViewById(R.id.indicator);
        indicator.setupWithViewPager(mPager, true);


    }



    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
        }
    }


    // this will show an alert will is show when we click to Report a User
    public void Report_User_alert(){
        final AlertDialog.Builder alert=new AlertDialog.Builder(context,R.style.DialogStyle);
        alert.setTitle(context.getResources().getString(R.string.report))
                .setMessage(context.getResources().getString(R.string.are_you_sure_report_user))
                .setNegativeButton(context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Send_report();
                    }
                });

        alert.setCancelable(true);
        alert.show();
    }


    // below two method will get all the  new user that is nearby of us  and parse the data in dataset
    // in that case which has a name of Nearby get set
    private void Send_report() {


        JSONObject parameters = new JSONObject();
        try {
            parameters.put("my_id", NewTabHome.user_id);
            parameters.put("fb_id", data_item.getFb_id());

        } catch (JSONException e) {
            e.printStackTrace();
        }


        Functions.Show_loader(context,false,false);
        ApiRequest.Call_Api(context, Variables.flat_user, parameters, new Callback() {
            @Override
            public void Responce(String resp) {
                Functions.cancel_loader();

                try {
                    JSONObject response = new JSONObject(resp);
                    JSONArray jsonArray=response.optJSONArray("msg");
                    Toast.makeText(context, ""+jsonArray.optJSONObject(0).optString("response"), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });


    }


    PopupWindow popup;
    private void displayPopupWindow(View anchorView) {
        popup = new PopupWindow(context);
        View layout = LayoutInflater.from(context).inflate(R.layout.item_menu_popup_option, null);

        TextView report=layout.findViewById(R.id.report);
        popup.setContentView(layout);

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
               Report_User_alert();
            }
        });


        popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.setOutsideTouchable(true);
        popup.setBackgroundDrawable(new BitmapDrawable());
        popup.showAsDropDown(anchorView,anchorView.getWidth(),anchorView.getHeight()- (Functions.convertDpToPx(context,60)));

    }


}