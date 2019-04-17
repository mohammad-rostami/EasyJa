package ir.EasyJa;


import android.graphics.drawable.Drawable;

import org.json.JSONObject;

import java.util.ArrayList;

public class AdsModel {

    public String image;
    public String ID;
    public String Address;
    public String Name;
    public double Lat;
    public double Lng;
    public int Capacity;
    public String Location;
    public String CreateTime;
    public int ClassSize;
    public String Email;
    public String PhoneNumber;
    public int EstateTypeID;
    public int UserID;
    public int AdsOption1_id;
    public String AdsOption1_name;
    public boolean AdsOption1_hasOption;
    public int AdsOption2_id;
    public String AdsOption2_name;
    public boolean AdsOption2_hasOption;
    public int AdsOption3_id;
    public String AdsOption3_name;
    public boolean AdsOption3_hasOption;

    public int AdsShift1_ID;
    public String AdsShift1_FromTime;
    public String AdsShift1_ToTime;
    public String AdsShift1_Date;
    public int AdsShift1_Fee;
    public int AdsShift1_FeeAfterOff;
    public int AdsShift1_Off;
    public boolean AdsShift1_IsReserve;
    public int AdsShift1_AdsId;

    public int AdsShift2_ID;
    public String AdsShift2_FromTime;
    public String AdsShift2_ToTime;
    public String AdsShift2_Date;
    public int AdsShift2_Fee;
    public int AdsShift2_FeeAfterOff;
    public int AdsShift2_Off;
    public boolean AdsShift2_IsReserve;
    public int AdsShift2_AdsId;

    public int AdsShift3_ID;
    public String AdsShift3_FromTime;
    public String AdsShift3_ToTime;
    public String AdsShift3_Date;
    public int AdsShift3_Fee;
    public int AdsShift3_FeeAfterOff;
    public int AdsShift3_Off;
    public boolean AdsShift3_IsReserve;
    public int AdsShift3_AdsId;
    public JSONObject object;
    public int AdsShift1_UserId;
    public int AdsShift1_RentId;
    public ArrayList<String> imageArray = new ArrayList<>();
    public String address;
    public String name;
    public String renterName;
    public String renterMobile;
    public int drawable;
    public boolean isSelected;
}
