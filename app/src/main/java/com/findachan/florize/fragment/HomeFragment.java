package com.findachan.florize.fragment;


import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.findachan.florize.R;
import com.findachan.florize.models.Type;
import com.findachan.florize.adapter.TypesAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ArrayList<Type> mTypesData;
    private TypesAdapter mAdapter;
    private AppCompatActivity appCompatActivity;

    public HomeFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
//
//        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
//        mRecyclerView.setHasFixedSize(true);
//
//        mTypesData = new ArrayList<>();
//
//        //Initialize the adapter and set it ot the RecyclerView
//        mAdapter = new TypesAdapter(new String[]{"Example One", "Example Two", "Example Three", "Example Four", "Example Five" , "Example Six" , "Example Seven"});
//        mRecyclerView.setAdapter(mAdapter);
//
//        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
//        mRecyclerView.setLayoutManager(llm);
//
//        return rootView;
//    }

//    @SuppressLint("ValidFragment")
//    public HomeFragment(AppCompatActivity appCompatActivity) {
//        super();
//        this.appCompatActivity = appCompatActivity;
//        // Required empty public constructor
//    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mTypesData = new ArrayList<>();

        mAdapter = new TypesAdapter(getActivity(), mTypesData);
        mRecyclerView.setAdapter(mAdapter);

        initializeData();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private void initializeData() {
        String[] typesList = getResources().getStringArray(R.array.types_titles);
        String[] typesInfo = getResources().getStringArray(R.array.types_info);

        mTypesData.clear();

        TypedArray typesImageResources =
                getResources().obtainTypedArray(R.array.types_images);

        for(int i=0;i<typesList.length;i++){
            mTypesData.add(new Type(typesList[i],typesInfo[i],
                    typesImageResources.getResourceId(i,0)));
        }

        mAdapter.notifyDataSetChanged();

        typesImageResources.recycle();
    }
}
