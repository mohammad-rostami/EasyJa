package ir.EasyJa;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;

import ir.EasyJa.Helper.DataBaseWrite;


//*************************************************************** THIS CLASS IS THE ADAPTER OF RECYCLERVIEWS
public class Adapter_Recycler_Ads extends RecyclerView.Adapter<Adapter_Recycler_Ads.ViewHolder> {
    public static boolean checked;
    private OnItemListener onItemListener;
    private Context context;
    private ArrayList<AdsModel> structs;
    private boolean isGrid;
    private int Tab;
    private Struct selectedGroupPosition;
    private Boolean x;
    private Boolean m;
    private RadioButton lastCheckedRadio;
    private LinearLayout LastSelected;
    private LinearLayout LastFontSelected;
    private Typeface typeFace_Light;
    private Typeface typeFace_Medium;
    private Typeface typeFace_Bold;
    ArrayList<String> selected = new ArrayList<>();
    private String selectedId;
    private LinearLayout selectedField1;
    private TextView selectedLastPrice;
    private TextView selectedOff1;
    private LinearLayout selectedField2;
    private TextView selectedPrice2;
    private LinearLayout selectedField3;
    private TextView selectedTime1;

    public Adapter_Recycler_Ads(Context context, ArrayList<AdsModel> structs, OnItemListener onItemListener, int Tab, boolean isGrid) {
        this.onItemListener = onItemListener;
        this.context = context;
        this.structs = structs;
        this.isGrid = isGrid;
        this.Tab = Tab;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //CHOOSE WITCH XML (CARD LAYOUT) TO SHOW (INFLATE) FOR EATCH RECYCLERVIEW
        View view = null;

        if (Tab == 1 && !isGrid) {
            view = inflater.inflate(R.layout.item_academy, parent, false);
        }
        if (Tab == 1 && isGrid) {
            view = inflater.inflate(R.layout.item_academy_vertic, parent, false);
        }
        if (Tab == 3) {
            view = inflater.inflate(R.layout.item_shift, parent, false);
        }
        if (Tab == 4) {
            view = inflater.inflate(R.layout.item_reserved, parent, false);
        }
        if (Tab == 5) {
            view = inflater.inflate(R.layout.item_my_ads, parent, false);
        }
        if (Tab == 6) {
            view = inflater.inflate(R.layout.item_my_ads_rented, parent, false);
        }
        if (Tab == 7) {
            view = inflater.inflate(R.layout.item_main, parent, false);
        }
        if (Tab == 8) {
            view = inflater.inflate(R.layout.item_type, parent, false);
        }

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        if (Tab == 1) {
            holder.name.setText(structs.get(position).Name);
            holder.location.setText(structs.get(position).Location);
            holder.date.setText(structs.get(position).CreateTime);
            holder.capacity.setText(String.valueOf(structs.get(position).ClassSize) + " متر ");
            holder.time1.setText(structs.get(position).AdsShift1_FromTime + " - " + structs.get(position).AdsShift1_ToTime);
            holder.price1.setText(String.valueOf(structs.get(position).AdsShift1_Fee));
            holder.last_price_1.setText(String.valueOf(structs.get(position).AdsShift1_FeeAfterOff));
            holder.off1.setText(String.valueOf(structs.get(position).AdsShift1_Off));

            try {
                holder.time2.setText(structs.get(position).AdsShift2_FromTime + " - " + structs.get(position).AdsShift2_ToTime);
                holder.price2.setText(String.valueOf(structs.get(position).AdsShift2_Fee));
                holder.last_price_2.setText(String.valueOf(structs.get(position).AdsShift2_FeeAfterOff));
                holder.off2.setText(String.valueOf(structs.get(position).AdsShift2_Off));
            } catch (Exception e) {

            }

            try {
                holder.time3.setText(structs.get(position).AdsShift3_FromTime + " - " + structs.get(position).AdsShift3_ToTime);
                holder.price3.setText(String.valueOf(structs.get(position).AdsShift3_Fee));
                holder.last_price_3.setText(String.valueOf(structs.get(position).AdsShift3_FeeAfterOff));
                holder.off3.setText(String.valueOf(structs.get(position).AdsShift3_Off));
            } catch (Exception e) {
            }


            holder.item_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemListener.onCardClick(position);
                }
            });

            try {
                Glide.with(G.CONTEXT).load(structs.get(position).image).placeholder(R.drawable.place_holder).into(holder.item_image);
            } catch (Exception e) {
            }


        }
        if (Tab == 3) {
            holder.time1.setText(structs.get(position).AdsShift1_FromTime + " - " + structs.get(position).AdsShift1_ToTime);
            holder.price1.setText(String.valueOf(structs.get(position).AdsShift1_Fee));
            holder.last_price_1.setText(String.valueOf(structs.get(position).AdsShift1_FeeAfterOff));
            holder.off1.setText(String.valueOf(structs.get(position).AdsShift1_Off));

            if (structs.get(position).AdsShift1_IsReserve) {
                holder.line.setVisibility(View.VISIBLE);
            } else {
                holder.line.setVisibility(View.GONE);
            }

            holder.item_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemListener.onCardClick(position);

                    try {
                        selectedField1.setBackgroundResource(R.drawable.bg_purple_stroke);
                        selectedLastPrice.setBackgroundResource(R.drawable.bg_white_back);
                        selectedLastPrice.setTextColor(G.CONTEXT.getResources().getColor(R.color.lightGray));
                        selectedOff1.setTextColor(G.CONTEXT.getResources().getColor(R.color.pink));

                        selectedField2.setBackgroundResource(R.drawable.bg_pink_stroke);
                        selectedPrice2.setTextColor(G.CONTEXT.getResources().getColor(R.color.black));

                        selectedField3.setBackgroundResource(R.drawable.bg_white_round_button);
                        selectedTime1.setTextColor(G.CONTEXT.getResources().getColor(R.color.black));

                    } catch (Exception e) {
                    }


                    holder.field1.setBackgroundResource(R.drawable.xml_rectangle_pink_round);
                    holder.last_price_1.setBackgroundResource(R.drawable.xml_rectangle_gray_round);
                    holder.last_price_1.setTextColor(G.CONTEXT.getResources().getColor(R.color.white));
                    holder.off1.setTextColor(G.CONTEXT.getResources().getColor(R.color.white));

                    holder.field2.setBackgroundResource(R.drawable.xml_rectangle_purple_round);
                    holder.price1.setTextColor(G.CONTEXT.getResources().getColor(R.color.white));

                    holder.field3.setBackgroundResource(R.drawable.xml_rectangle_green_round);
                    holder.time1.setTextColor(G.CONTEXT.getResources().getColor(R.color.white));

                    try {
                        selectedField1 = holder.field1;
                        selectedLastPrice = holder.last_price_1;
                        selectedOff1 = holder.off1;
                        selectedField2 = holder.field2;
                        selectedPrice2 = holder.price1;
                        selectedField3 = holder.field3;
                        selectedTime1 = holder.time1;
                    } catch (Exception e) {
                    }
                }
            });


        }
        if (Tab == 4) {
            holder.time1.setText(structs.get(position).AdsShift1_FromTime + " - " + structs.get(position).AdsShift1_ToTime);
            holder.date.setText(structs.get(position).AdsShift1_Date);
            holder.price1.setText(String.valueOf(structs.get(position).AdsShift1_Fee));
            holder.last_price_1.setText(String.valueOf(structs.get(position).AdsShift1_FeeAfterOff));
            holder.off1.setText(String.valueOf(structs.get(position).AdsShift1_Off));
            try {
                Glide.with(G.CONTEXT).load(structs.get(position).image).placeholder(R.drawable.place_holder).into(holder.item_image);
            } catch (Exception e) {
            }
            holder.item_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemListener.onCardClick(position);
                }
            });
        }
        if (Tab == 5) {
            holder.time1.setText(structs.get(position).AdsShift1_FromTime + " - " + structs.get(position).AdsShift1_ToTime);
            holder.date.setText(structs.get(position).AdsShift1_Date);
            holder.price1.setText(String.valueOf(structs.get(position).AdsShift1_Fee));
            holder.last_price_1.setText(String.valueOf(structs.get(position).AdsShift1_FeeAfterOff));
            holder.off1.setText(String.valueOf(structs.get(position).AdsShift1_Off));
            holder.location.setText(String.valueOf(structs.get(position).address));
            holder.name.setText(String.valueOf(structs.get(position).name));
            try {
                Glide.with(G.CONTEXT).load(structs.get(position).image).placeholder(R.drawable.place_holder).into(holder.item_image);
            } catch (Exception e) {
            }
        }
        if (Tab == 6) {
            holder.time1.setText(structs.get(position).AdsShift1_FromTime + " - " + structs.get(position).AdsShift1_ToTime);
            holder.date.setText(structs.get(position).AdsShift1_Date);
            holder.price1.setText(String.valueOf(structs.get(position).AdsShift1_Fee));
            holder.last_price_1.setText(String.valueOf(structs.get(position).AdsShift1_FeeAfterOff));
            holder.off1.setText(String.valueOf(structs.get(position).AdsShift1_Off));
            holder.location.setText(String.valueOf(structs.get(position).address));
            holder.name.setText(String.valueOf(structs.get(position).name));
            holder.renter_name.setText(structs.get(position).renterName);
            holder.renter_mobile.setText(structs.get(position).renterMobile);
            try {
                Glide.with(G.CONTEXT).load(structs.get(position).image).placeholder(R.drawable.place_holder).into(holder.item_image);
            } catch (Exception e) {
            }
            holder.renter_mobile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", holder.renter_mobile.getText().toString(), null));
                    G.CONTEXT.startActivity(intent);
                }
            });
        }
        if (Tab == 7) {
            try {
                holder.item_image.setImageResource(structs.get(position).drawable);
            } catch (Exception e) {
            }
            holder.item_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemListener.onCardClick(position);
                }
            });

            if (structs.get(position).isSelected) {
                holder.item_card.setScaleX(1.5f);
                holder.item_card.setScaleY(1.5f);
            } else {
                holder.item_card.setScaleX(1f);
                holder.item_card.setScaleY(1f);
            }
        }
        if (Tab == 8) {
            holder.title.setText(structs.get(position).name);
            holder.type.setText(String.valueOf(position + 1));
            holder.item_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                onItemListener.onCardClick(position);
                }
            });
            if (position == 0) {
                holder.item_card.setEnabled(false);
                holder.type.setBackground(G.CONTEXT.getResources().getDrawable(R.drawable.circle_disabled));
            } else {
                holder.item_card.setEnabled(true);

                if (structs.get(position).AdsShift1_ID == 1) {
                    holder.type.setBackground(G.CONTEXT.getResources().getDrawable(R.drawable.circle_green));
                } else {
                    holder.type.setBackground(G.CONTEXT.getResources().getDrawable(R.drawable.circle_yellow));

                }
            }

//            try {
//                holder.item_image.setImageResource(structs.get(position).drawable);
//            } catch (Exception e) {
//            }
//            holder.item_image.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    onItemListener.onCardClick(position);
//                }
//            });
//
//            if (structs.get(position).isSelected) {
//                holder.item_card.setScaleX(1.5f);
//                holder.item_card.setScaleY(1.5f);
//            } else {
//                holder.item_card.setScaleX(1f);
//                holder.item_card.setScaleY(1f);
//            }
        }

    }


    @Override
    public int getItemCount() {
        return structs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView renter_name;
        private final TextView renter_mobile;
        private final TextView title;
        private final TextView type;
        ImageView item_image;
        LinearLayout line;
        LinearLayout field1;
        LinearLayout field2;
        LinearLayout field3;
        TextView capacity;
        TextView name;
        TextView location;
        TextView time1;
        TextView price1;
        TextView off1;
        TextView last_price_1;
        TextView time2;
        TextView price2;
        TextView off2;
        TextView last_price_2;
        TextView time3;
        TextView price3;
        TextView off3;
        TextView last_price_3;
        TextView date;
        CardView item_card;

        public ViewHolder(final View itemView) {
            super(itemView);

            line = (LinearLayout) itemView.findViewById(R.id.line);
            item_card = (CardView) itemView.findViewById(R.id.item_card);
            date = (TextView) itemView.findViewById(R.id.date);
            capacity = (TextView) itemView.findViewById(R.id.capacity);
            name = (TextView) itemView.findViewById(R.id.name);
            location = (TextView) itemView.findViewById(R.id.location);
            title = (TextView) itemView.findViewById(R.id.title);
            type = (TextView) itemView.findViewById(R.id.no);

            time1 = (TextView) itemView.findViewById(R.id.time1);
            price1 = (TextView) itemView.findViewById(R.id.price1);
            off1 = (TextView) itemView.findViewById(R.id.off1);
            last_price_1 = (TextView) itemView.findViewById(R.id.last_price_1);

            time2 = (TextView) itemView.findViewById(R.id.time2);
            price2 = (TextView) itemView.findViewById(R.id.price2);
            off2 = (TextView) itemView.findViewById(R.id.off2);
            last_price_2 = (TextView) itemView.findViewById(R.id.last_price_2);

            time3 = (TextView) itemView.findViewById(R.id.time3);
            price3 = (TextView) itemView.findViewById(R.id.price3);
            off3 = (TextView) itemView.findViewById(R.id.off3);
            last_price_3 = (TextView) itemView.findViewById(R.id.last_price_3);

            field1 = (LinearLayout) itemView.findViewById(R.id.field1);
            field2 = (LinearLayout) itemView.findViewById(R.id.field2);
            field3 = (LinearLayout) itemView.findViewById(R.id.field3);

            item_image = (ImageView) itemView.findViewById(R.id.item_image);

            renter_name = (TextView) itemView.findViewById(R.id.renter_name);
            renter_mobile = (TextView) itemView.findViewById(R.id.renter_mobile);


        }

    }

    private boolean DataBaseChecker(String TableName, String ID, String SELECTED_ID) {
        Boolean isAvailable = false;
        DataBaseWrite dataBaseHelper = new DataBaseWrite(G.CONTEXT, "ORDER_DATABASE", "ORDER_TABLE", G.OrderTableFieldNames, G.OrderTableFieldTypes);
        try {
            dataBaseHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        SQLiteDatabase sqld = dataBaseHelper.getWritableDatabase();

        Cursor cursor = sqld.rawQuery("select * from " + TableName + " where ID = \"" + SELECTED_ID + "\"", null);
        String id;
        if (cursor.moveToFirst()) {
            do {
                Log.d("table", "item found");
                isAvailable = true;
            } while (cursor.moveToNext());
        }
        sqld.close();
        return isAvailable;
    }

}
