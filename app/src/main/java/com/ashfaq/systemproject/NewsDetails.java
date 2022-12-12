package com.ashfaq.systemproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashfaq.systemproject.Models.NewsHeadlines;
import com.squareup.picasso.Picasso;


public class NewsDetails extends Fragment {

    NewsHeadlines headlines;
    TextView title, author, time, detail, content;
    ImageView image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_details, container, false);
        headlines = (NewsHeadlines) getArguments().getSerializable("headline");
        title = view.findViewById(R.id.news_title);
        author = view.findViewById(R.id.news_author);
        time = view.findViewById(R.id.news_time);
        detail = view.findViewById(R.id.news_detail);
        content = view.findViewById(R.id.news_detail_content);
        image = view.findViewById(R.id.news_image);

        title.setText(headlines.getTitle());
        author.setText(headlines.getAuthor());
        time.setText(headlines.getPublishedAt());
        detail.setText(headlines.getDescription());
        content.setText(headlines.getContent());
        Picasso.with(getActivity()).load(headlines.getUrlToImage()).into(image);
        return view;
    }
}