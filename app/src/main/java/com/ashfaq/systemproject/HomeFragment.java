package com.ashfaq.systemproject;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ashfaq.systemproject.Models.NewsApiResponse;
import com.ashfaq.systemproject.Models.NewsHeadlines;
import com.ashfaq.systemproject.Models.OnFetchDataListener;
import com.ashfaq.systemproject.Models.RequestApiManager;
import com.ashfaq.systemproject.Models.SelectListeners;

import java.util.List;


public class HomeFragment extends Fragment implements SelectListeners,View.OnClickListener {

    RecyclerView recyclerView;
    NewsAdapter adapter;
    ProgressDialog dialog;
    TextView t1,t2,t3,t4,t5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView=view.findViewById(R.id.recycler_home);
        t1=view.findViewById(R.id.general);
        t2=view.findViewById(R.id.technology);
        t3=view.findViewById(R.id.health);
        t4=view.findViewById(R.id.business);
        t5=view.findViewById(R.id.news_current);

        t1.setOnClickListener(this);
        t2.setOnClickListener(this);
        t3.setOnClickListener(this);
        t4.setOnClickListener(this);


        dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Preparing Home Screen");
        dialog.show();

        RequestApiManager manager = new RequestApiManager(getActivity());
        manager.getNewsHeadlines(listener,"business",null);

        return view;
    }

    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<NewsHeadlines> list, String message) {
            showNews(list);
            dialog.dismiss();
        }

        @Override
        public void onError(String message) {

        }
    };

    private void showNews(List<NewsHeadlines> list) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
        adapter = new NewsAdapter(getActivity(),list,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onNewsClicked(NewsHeadlines headlines) {
        NewsDetails newsDetails = new NewsDetails();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, newsDetails ,"null");
        Bundle args = new Bundle();
        args.putSerializable("headline",headlines);
        newsDetails.setArguments(args);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        TextView tv = (TextView) v;
        TextView cv = t5;
        //int color = Integer.parseInt("custom", 16)+0xFF000000;

        if(tv.getText().toString().equals("General")){
            dialog.setTitle("Loading news related General...");
            dialog.show();
            //tv.setBackgroundColor(Color.parseColor("#d4d446"));
            cv.setText("Showing General related news");
            RequestApiManager manager = new RequestApiManager(getActivity());
            manager.getNewsHeadlines(listener,"general",null);
        }
        else if (tv.getText().toString().equals("Health")){
            dialog.setTitle("Loading news related Health...");
            dialog.show();
            //tv.setBackgroundColor(Color.parseColor("#d4d446"));
            cv.setText("Showing Health related news");
            RequestApiManager manager = new RequestApiManager(getActivity());
            manager.getNewsHeadlines(listener,"health",null);
        }
        else if (tv.getText().toString().equals("Tech")){
            dialog.setTitle("Loading news related Technology...");
            dialog.show();
            //tv.setBackgroundColor(Color.parseColor("#d4d446"));
            cv.setText("Showing Technology related news");
            RequestApiManager manager = new RequestApiManager(getActivity());
            manager.getNewsHeadlines(listener,"technology",null);
        }
        else if (tv.getText().toString().equals("Business")){
            dialog.setTitle("Loading news related Business...");
            dialog.show();
            //tv.setBackgroundColor(Color.parseColor("#d4d446"));
            cv.setText("Showing Business related news");
            RequestApiManager manager = new RequestApiManager(getActivity());
            manager.getNewsHeadlines(listener,"business",null);
        }
    }
}