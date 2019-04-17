package ir.EasyJa;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;


@SuppressLint("ValidFragment")
public class FragmentCategory2 extends android.support.v4.app.Fragment {

    private JSONArray attributes= new JSONArray();
    private RecyclerView rvAttribList;
    private LinearLayoutManager glmAttribList;
    private Adapter_Recycler_Ads ADAPTER_RECYCLER_Attrib;
    private ArrayList<AdsModel> alAttribs = new ArrayList<>();
    private String names[] = new String[]{"تالار", "کلاس آموزشی"};

    @SuppressLint("ValidFragment")
    public FragmentCategory2() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.fragment_category, container, false);

        TextView textView = (TextView)view.findViewById(R.id.text);
        textView.setText("2");

        for (int i = 0; i < 2; i++) {
            AdsModel adsModel = new AdsModel();
            adsModel.name = names[i];
            adsModel.AdsShift1_ID = 2 ;
            alAttribs.add(adsModel);
        }

        rvAttribList = (RecyclerView) view.findViewById(R.id.sub_category);
        glmAttribList= new LinearLayoutManager(getContext());
        ADAPTER_RECYCLER_Attrib = new Adapter_Recycler_Ads(getContext(), alAttribs, new OnItemListener() {

            @Override
            public void onItemSelect(int position) {

            }

            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onItemDelete(int position) {

            }

            @Override
            public void onCardClick(int position) {
                G.GOING_TO_RENT.edit().putBoolean("GOING_TO_RENT", false).apply();
                Intent intent = new Intent(getActivity(), CategoryActivity.class);
                intent.putExtra("item", "4");
                getActivity().startActivity(intent);
            }

            @Override
            public void onEdit(int position) {

            }
        }, 8, false);
        rvAttribList.setAdapter(ADAPTER_RECYCLER_Attrib);
        rvAttribList.setLayoutManager(glmAttribList);



        return view;


    }

}