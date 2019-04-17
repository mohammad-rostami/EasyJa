package ir.EasyJa;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import ir.EasyJa.Helper.DataBaseWrite;


//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;

public class FragmentNavigation extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private String mStr_packages;
    private Dialog dialog;
    private LinearLayout mLinearLayout_Main;
    private static TextView Premium;
    private TextView mTextView_ProShot;
    private String[] OrderTableFieldNames = new String[]{"ID", "NAME", "CURRENT_PRICE", "PREVIOUS_PRICE", "QUANTITY", "COMMENT"};
    private String[] OrderTableFieldTypes = new String[]{"TEXT", "TEXT", "INT", "INT", "INT", "TEXT", "TEXT"};
    private String[] HistoryTableFieldNames = new String[]{"JSON", "DATE", "Empty1", "Empty2", "Empty3", "TIME"};
    private String[] HistoryTableFieldTypes = new String[]{"TEXT", "TEXT", "INT", "INT", "INT", "TEXT"};
    private Dialog dialog_confirm;
    public static TextView S_TEXTVIEW_CREDIT;
    private LinearLayout mLinearLayout_Fifth;
    private LinearLayout mLinearLayout_Second;
    private LinearLayout mLinearLayout_One;
    private LinearLayout mLinearLayout_First;
    private LinearLayout mLinearLayout_Third;
    private LinearLayout mLinearLayout_Forth;
    private LinearLayout mLinearLayout_Sixth;
    private LinearLayout mLinearLayout_seventh;
    private TextView mTextView_First;
    private TextView mTextView_Second;
    private TextView mTextView_Third;
    private TextView mTextView_Forth;
    private TextView mTextView_Fifth;
    private TextView mTextView_Sixth;
    private TextView mTextView_seventh;
    private TextView mTextView_eighth;
    private LinearLayout mLinearLayout_eighth;
    private LinearLayout mLinearLayout_credit_layout;
    private TextView mTextView_zero;
    private LinearLayout mLinearLayout_zero;
    private CircleImageView img;


    public FragmentNavigation() {
        // Required empty public constructor
    }

    public static FragmentNavigation newInstance(String param1, String param2) {
        FragmentNavigation fragment = new FragmentNavigation();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_navigation, container, false);

        // Dialog confirm
        dialog_confirm = new Dialog(getActivity());
        dialog_confirm.setContentView(R.layout.dialog_confirm);
        TextView dialog_confirm_text = (TextView) dialog_confirm.findViewById(R.id.dialog_txt);
        dialog_confirm_text.setText("آیا از خروج خود اطمینان دارد؟");
        TextView dialog_confirm_cancel = (TextView) dialog_confirm.findViewById(R.id.cancel);
        TextView dialog_confirm_register = (TextView) dialog_confirm.findViewById(R.id.register);

        dialog_confirm_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String sessionId = G.AUTHENTICATIONS_SESSION.getString("SESSION", "x");
//                String userToken = G.AUTHENTICATIONS_TOKEN.getString("TOKEN", "x");
//                String customerId = G.CUSTOMER_ID.getString("CUSTOMER_ID", "x");
//                new AsyncLogout().execute(Urls.BASE_URL + "/Logout", sessionId, userToken, customerId);

                G.IS_REGISTERED.edit().putBoolean("IS_REGISTERED", false).apply();
                G.CUSTOMER_NAME.edit().remove("CUSTOMER_NAME").apply();
                G.CUSTOMER_ID.edit().remove("CUSTOMER_ID").apply();
                G.CUSTOMER_IMAGE.edit().remove("CUSTOMER_IMAGE").apply();



                try {
                    clearDateBase();
                } catch (Exception e) {
                }

                G.FIRST_TIME_REQUEST.edit().putBoolean("FIRST_TIME_CONNECTION", false).apply();
                G.AUTHENTICATIONS_SESSION.edit().remove("SESSION").apply();
                G.AUTHENTICATIONS_TOKEN.edit().remove("TOKEN").apply();

//                Intent intent = new Intent(getActivity(), LoginActivity.class);
//                getActivity().startActivity(intent);
//                ActivityMain.finisher();
                dialog_confirm.dismiss();

                Intent intent = getActivity().getIntent();
                getActivity().finish();
                startActivity(intent);
            }
        });
        dialog_confirm_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_confirm.dismiss();
            }
        });

        img = (CircleImageView) view.findViewById(R.id.img);
        try {
            String profileImage = G.CUSTOMER_IMAGE.getString("CUSTOMER_IMAGE","0");
            Glide.with(G.CONTEXT).load(profileImage).placeholder(R.drawable.place_holder).into(img);
        } catch (Exception e) {
        }


        mLinearLayout_credit_layout = (LinearLayout) view.findViewById(R.id.credit_layout);


        mLinearLayout_zero = (LinearLayout) view.findViewById(R.id.fragment_nav_item_zero);
        mLinearLayout_First = (LinearLayout) view.findViewById(R.id.fragment_nav_item_first);
        mLinearLayout_Second = (LinearLayout) view.findViewById(R.id.fragment_nav_item_second);
        mLinearLayout_Third = (LinearLayout) view.findViewById(R.id.fragment_nav_item_third);
        mLinearLayout_Forth = (LinearLayout) view.findViewById(R.id.fragment_nav_item_forth);
        mLinearLayout_Fifth = (LinearLayout) view.findViewById(R.id.fragment_nav_item_fifth);
        mLinearLayout_Sixth = (LinearLayout) view.findViewById(R.id.fragment_nav_item_sixth);
        mLinearLayout_seventh = (LinearLayout) view.findViewById(R.id.fragment_nav_item_seventh);
        mLinearLayout_eighth = (LinearLayout) view.findViewById(R.id.fragment_nav_item_eighth);



        mTextView_zero = (TextView) view.findViewById(R.id.fragment_nav_txt_zero);
        mTextView_First = (TextView) view.findViewById(R.id.fragment_nav_txt_first);
        mTextView_Second = (TextView) view.findViewById(R.id.fragment_nav_txt_second);
        mTextView_Third = (TextView) view.findViewById(R.id.fragment_nav_txt_third);
        mTextView_Forth = (TextView) view.findViewById(R.id.fragment_nav_txt_forth);
        mTextView_Fifth = (TextView) view.findViewById(R.id.fragment_nav_txt_fifth);
        mTextView_Sixth = (TextView) view.findViewById(R.id.fragment_nav_txt_sixth);
        mTextView_seventh = (TextView) view.findViewById(R.id.fragment_nav_txt_seventh);
        mTextView_eighth = (TextView) view.findViewById(R.id.fragment_nav_txt_eighth);

        mLinearLayout_zero.setEnabled(false);
        mLinearLayout_First.setEnabled(false);
        mLinearLayout_Second.setEnabled(false);
        mLinearLayout_Third.setEnabled(false);
        mLinearLayout_Forth.setEnabled(false);
        mLinearLayout_Fifth.setEnabled(false);
        mLinearLayout_Sixth.setEnabled(false);
        mLinearLayout_seventh.setEnabled(false);
        mLinearLayout_eighth.setEnabled(false);

        mTextView_zero.setTextColor(getResources().getColor(R.color.lightGray));
        mTextView_First.setTextColor(getResources().getColor(R.color.lightGray));
        mTextView_Second.setTextColor(getResources().getColor(R.color.lightGray));
        mTextView_Third.setTextColor(getResources().getColor(R.color.lightGray));
        mTextView_Forth.setTextColor(getResources().getColor(R.color.lightGray));
        mTextView_Fifth.setTextColor(getResources().getColor(R.color.lightGray));
        mTextView_Sixth.setTextColor(getResources().getColor(R.color.lightGray));
        mTextView_seventh.setTextColor(getResources().getColor(R.color.lightGray));
        mTextView_eighth.setTextColor(getResources().getColor(R.color.lightGray));


        TextView credit_str = (TextView)view.findViewById(R.id.credit_str);
        String creditStr = G.CUSTOMER_CREDIT.getString("CUSTOMER_CREDIT", "0");
        credit_str.setText(creditStr + " تومان ");

        Boolean isRegistered = G.IS_REGISTERED.getBoolean("IS_REGISTERED", false);
        if (!isRegistered){
//            mTextView_First.setTextColor(getResources().getColor(R.color.lightGray));
//            mTextView_Forth.setTextColor(getResources().getColor(R.color.lightGray));
            mLinearLayout_credit_layout.setVisibility(View.GONE);
        }else {
            mLinearLayout_credit_layout.setVisibility(View.VISIBLE);
            mLinearLayout_zero.setEnabled(true);
            mLinearLayout_First.setEnabled(true);
            mLinearLayout_Second.setEnabled(true);
            mLinearLayout_Forth.setEnabled(true);
            mLinearLayout_Sixth.setEnabled(true);
            mLinearLayout_seventh.setEnabled(true);
            mLinearLayout_eighth.setEnabled(true);
            mTextView_zero.setTextColor(getResources().getColor(R.color.darkGray));
            mTextView_First.setTextColor(getResources().getColor(R.color.darkGray));
            mTextView_Second.setTextColor(getResources().getColor(R.color.darkGray));
            mTextView_Forth.setTextColor(getResources().getColor(R.color.darkGray));
            mTextView_Sixth.setTextColor(getResources().getColor(R.color.darkGray));
            mTextView_seventh.setTextColor(getResources().getColor(R.color.darkGray));
            mTextView_eighth.setTextColor(getResources().getColor(R.color.darkGray));
        }




        mLinearLayout_zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean isRegistered = G.IS_REGISTERED.getBoolean("IS_REGISTERED", false);
                if (isRegistered) {
                    Intent intent = new Intent(getActivity(), CreditActivity.class);
                    getActivity().startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivity(intent);
                }


            }
        });
        mLinearLayout_First.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean isRegistered = G.IS_REGISTERED.getBoolean("IS_REGISTERED", false);
                if (isRegistered) {
                    Intent intent = new Intent(getActivity(), ProfileActivity.class);
                    getActivity().startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivity(intent);
                }


            }
        });
        mLinearLayout_Second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean isRegistered = G.IS_REGISTERED.getBoolean("IS_REGISTERED", false);
                if (isRegistered) {
                    Intent intent = new Intent(getActivity(), FutureReservedAdsActivity.class);
                    getActivity().startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivity(intent);
                }


            }
        });

        mLinearLayout_Third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean isRegistered = G.IS_REGISTERED.getBoolean("IS_REGISTERED", false);
                if (isRegistered) {
//                    Intent intent = new Intent(getActivity(), CustomerActivity.class);
//                    getActivity().startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivity(intent);
                }

            }
        });

        mLinearLayout_Forth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean isRegistered = G.IS_REGISTERED.getBoolean("IS_REGISTERED", false);
                if (isRegistered) {
                    Intent intent = new Intent(getActivity(), AccountActivity.class);
                    getActivity().startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivity(intent);
                }


            }
        });
        mLinearLayout_Fifth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean isRegistered = G.IS_REGISTERED.getBoolean("IS_REGISTERED", false);
                if (isRegistered) {
//                    Intent intent = new Intent(getActivity(), DashBoardActivity.class);
//                    getActivity().startActivity(intent);
                    getActivity().finish();
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivity(intent);
                }


            }
        });
        mLinearLayout_Sixth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean isRegistered = G.IS_REGISTERED.getBoolean("IS_REGISTERED", false);
                if (isRegistered) {
                    Intent intent = new Intent(getActivity(), PastReservedAdsActivity.class);
                    getActivity().startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivity(intent);
                }


            }
        });
        mLinearLayout_seventh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "ایزی جا، اولین سایت اجاره کوتاه مدت مکان!");
                startActivity(Intent.createChooser(shareIntent, "  به اشتراک گذاری  "));
            }
        });

        mLinearLayout_eighth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "09215529462", null));
                startActivity(intent);
            }
        });


        final TextView Name = (TextView) view.findViewById(R.id.name);
        final LinearLayout exit = (LinearLayout) view.findViewById(R.id.fragment_nav_item_exit);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_confirm.show();
            }
        });
        Name.setText("ورود / ثبت نام");

        Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (exit.getVisibility() == View.VISIBLE) {
//                    dialog_confirm.show();
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivity(intent);
//                    ActivityMain.finisher();
                }

            }
        });

        if (G.IS_REGISTERED.getBoolean("IS_REGISTERED", false)) {
            String name = G.CUSTOMER_NAME.getString("CUSTOMER_NAME", "empty");
            if (!name.equals("empty")) {
                Name.setText(name);
                exit.setVisibility(View.VISIBLE);
                refreshCredit();
            } else {
                exit.setVisibility(View.GONE);
                Name.setText("ورود / ثبت نام");
            }
        } else {
            exit.setVisibility(View.GONE);
            Name.setText("ورود / ثبت نام");
        }
        return view;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);

//        void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second);
//
//        void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth);
    }

    private void activityFinisher() {

    }


    private class AsyncLogout extends Webservice.logOutSession {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(final String result) {
            Toast.makeText(G.CONTEXT, result, Toast.LENGTH_LONG).show();

        }
    }

    //
    public static void refreshCredit() {
        int CREDIT = G.CREDIT.getInt("CREDIT", 0);
        String convertedCredit = String.format("%,d", CREDIT);
//        S_TEXTVIEW_CREDIT.setText(convertedCredit + " ریال");

    }

    public void clearDateBase() {
        DataBaseWrite dataBaseHelper = new DataBaseWrite(getContext(), "ORDER_DATABASE", "ORDER_TABLE", OrderTableFieldNames, OrderTableFieldTypes);
        try {
            dataBaseHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        SQLiteDatabase sqld = dataBaseHelper.getWritableDatabase();
        sqld.execSQL("delete from " + "ORDER_TABLE");

        DataBaseWrite db = new DataBaseWrite(getContext(), "HISTORY_DATABASE", "HISTORY_TABLE", HistoryTableFieldNames, HistoryTableFieldTypes);
        try {
            db.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        SQLiteDatabase sql = db.getWritableDatabase();
        sql.execSQL("delete from " + "HISTORY_TABLE");

    }



    @Override
    public void onResume() {
        super.onResume();
//        if (G.ACTIVITY_NAME.equals("DashBoardActivity")) {
//            mLinearLayout_Fifth.setVisibility(View.GONE);
//        } else if (G.ACTIVITY_NAME.equals("ServicesActivity")) {
//            mLinearLayout_Second.setVisibility(View.GONE);
//        } else if (G.ACTIVITY_NAME.equals("ProjectsActivity")) {
//            mLinearLayout_Forth.setVisibility(View.GONE);
//        } else if (G.ACTIVITY_NAME.equals("CustomerActivity")) {
//            mLinearLayout_Third.setVisibility(View.GONE);
//        } else {
//            mLinearLayout_Fifth.setVisibility(View.VISIBLE);
//
//        }
    }


}
