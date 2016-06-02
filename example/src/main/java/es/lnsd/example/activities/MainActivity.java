package es.lnsd.example.activities;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import es.lnsd.example.adapters.CitiesSearchViewAdapter;
import es.lnsd.example.models.City;
import es.lnsd.realmsearchview.RealmSearchView;
import es.lnsd.example.R;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements CitiesSearchViewAdapter.OnItemClickListener,
                                                                AppBarLayout.OnOffsetChangedListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    //region Views
    @Bind(R.id.app_bar) AppBarLayout appBarLayout;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.search_view) RealmSearchView searchView;
    @Bind(R.id.realm_recycler_view) RealmRecyclerView recyclerView;
    //endregion

    Realm realm;
    RealmResults<City> cityList;
    CitiesSearchViewAdapter adapter;

    //region Activity lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        appBarLayout.addOnOffsetChangedListener(this);

        // Realm search box and list view initialisation
        try {
            RealmConfiguration config = new RealmConfiguration.Builder(this)
                    .name(getResources().getString(R.string.realm_db_filename))
                    .schemaVersion(0)
                    .build();

            realm = Realm.getInstance(config);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.cityList = realm.where(City.class).findAll();
        adapter = new CitiesSearchViewAdapter(getBaseContext(), realm, "name");
        adapter.setOnItemClickListener(this);

        searchView.associateRealmRecyclerView(recyclerView);
        searchView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
        realm = null;
    }
    //endregion

    @OnClick(R.id.fab)
    public void onFabClick(View view) {
        appBarLayout.setExpanded(false);
        setFocusOnSearchView();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset == 0) {
            // Not collapsed
            if (searchView.getVisibility() == View.VISIBLE) {
                hideSearchView();
            }
        } else {
            // Collapsed
            if (searchView.getVisibility() != View.VISIBLE) {
                showSearchView();
                //setFocusOnSearchView();
            }
        }
    }

   @Override
    public void onItemClick(View view, City city, int index) {
        Snackbar.make(view, "Item ("+index+"): "+ city.getName(), Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }

    //region SearchView
    private void showSearchView() {
        searchView.setVisibility(View.VISIBLE);
    }

    private void hideSearchView() {
        searchView.setVisibility(View.INVISIBLE);
    }

    private void setFocusOnSearchView() {
        searchView.setIconifiedByDefault(true);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();
    }
    //endregion
}
