package es.lnsd.example.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import co.moonmonkeylabs.realmsearchview.RealmSearchAdapter;
import co.moonmonkeylabs.realmsearchview.RealmSearchViewHolder;
import es.lnsd.example.models.City;
import es.lnsd.example.R;
import io.realm.Realm;

/**
 * RealmSearchview
 * Copyright (C) 2016 Lorenzo Delgado.
 * http://LNSD.es
 * <p/>
 * Licensed under The MIT License (MIT). See LICENSE file for more information.
 */
public class CitiesSearchViewAdapter extends RealmSearchAdapter<City, CitiesSearchViewAdapter.ViewHolder> {

    private OnItemClickListener listener;

    public CitiesSearchViewAdapter(
            Context context,
            Realm realm,
            String filterColumnName) {
        super(context, realm, filterColumnName);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RealmSearchViewHolder implements View.OnClickListener{
        RoomItemView container;
        OnItemClickListener mListener;

        public ViewHolder(RoomItemView container, OnItemClickListener listener) {
            super(container);
            this.container = container;
            this.mListener = listener;

            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(container, container.getCity(), container.getIndex());
            }
        }
    }

    @Override
    public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int viewType) {
        return new ViewHolder(new RoomItemView(viewGroup.getContext()), this.listener);
    }

    @Override
    public void onBindRealmViewHolder(ViewHolder viewHolder, int position) {
        final City city = realmResults.get(position);
        viewHolder.container.bind(city, position);
    }

    //region TODO: List manipulation and animation
    /*public void animateTo(List<City> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<City> newModels) {
        for (int i = realmResults.size() - 1; i >= 0; i--) {
            final City model = realmResults.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<City> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final City model = newModels.get(i);
            if (!realmResults.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<City> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final City model = newModels.get(toPosition);
            final int fromPosition = realmResults.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public City removeItem(int position) {
        final City model = realmResults.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, City model) {
        realmResults.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final City model = realmResults.remove(fromPosition);
        realmResults.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }*/
    //endregion

    /**
    * View for a {@link City} model.
    */
    class RoomItemView extends RelativeLayout {

        @Bind(R.id.roomItem_name) TextView name;
        @Bind(R.id.roomItem_description) TextView description;
        private City city;
        private int index;

        public RoomItemView(Context context) {
            super(context);
            init(context);
        }

        private void init(Context context) {
            inflate(context, R.layout.main_room_item, this);
            ButterKnife.bind(this);
        }

        public void bind(City city, int index) {
            this.city = city;
            this.index = index;
            name.setText(city.getName());
            description.setText(city.getDescription());
        }

        public City getCity() {
            return city;
        }

        public int getIndex() {
            return index;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, City city, int index);
    }
}