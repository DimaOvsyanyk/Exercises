package com.dimaoprog.exercises;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class BindingAdapters {

    @BindingAdapter({"app:url"})
    public static void loadImage(ImageView view, String url) {
        if (url != null) {
            Picasso.get()
                    .load(url)
                    .into(view);
        }
    }
}
