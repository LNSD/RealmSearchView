package es.lnsd.realmsearchview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.SearchView;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import co.moonmonkeylabs.realmsearchview.RealmSearchAdapter;

/**
 * RealmSearchview
 * Copyright (C) 2016 Lorenzo Delgado.
 * http://LNSD.es
 * <p/>
 * Licensed under The MIT License (MIT). See LICENSE file for more information.
 */
public class RealmSearchView extends SearchView {
    private static final String TAG = RealmSearchView.class.getSimpleName();

    private RealmRecyclerView realmRecyclerView;
    private RealmSearchAdapter adapter;

    public RealmSearchView(Context context) {
        super(context);
        init(context, null);
    }

    public RealmSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        // Parse attributes
        initAttrs(context, attrs);

        setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                adapter.filter(query);
                return true;
            }
        });
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RealmSearchView);

        int hintTextResId = typedArray.getResourceId(R.styleable.RealmSearchView_rsvHint, R.string.rsv_default_search_hint);
        setQueryHint(getResources().getString(hintTextResId));

        typedArray.recycle();
    }

    public void associateRealmRecyclerView(RealmRecyclerView view) {
        this.realmRecyclerView = view;
    }

    public void setAdapter(RealmSearchAdapter adapter) {
        this.adapter = adapter;
        if(realmRecyclerView != null) {
            realmRecyclerView.setAdapter(adapter);
        } else {
            throw new IllegalStateException("No RealmRecyclerView associated to RealmSearchView");
        }
        this.adapter.filter("");
    }
}
