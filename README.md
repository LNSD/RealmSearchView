#SearchView with result list powered by Realm (WIP)

The `RealmSearchView` extends Android's `SearchView` widget. This is an much simpler and more versatile approach than [Thorbenprimke's original](https://github.com/thorbenprimke/realm-searchview) `RealmSearchView` which implements a wrapper around a `EditText` and `RealmRecyclerView`. It's build on top of `RealmRecyclerView` and `RealmBasedRecyclerViewAdapter`.

It's easily customizable via layout attributes and adapter parameters.

##Example
<p align="center">
	<img src="https://raw.githubusercontent.com/LNSD/realm-searchview/master/artwork/screencast-demo-app.gif"/ width="320">
</p>


##How to get started:

The `RealmSearchView` extends Android's `SearchView` widget. The `RealmSearchAdapter` has added functionality for the filtering of the Realm. The snippet below shows how to include the `RealmSearchView` in your  layout file.

``` xml
    <es.lnsd.realmsearchview.RealmSearchView
        android:id="@+id/search_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:rsvHint="@string/search_hint"
        />
```

A `RealmRecyclerView` is required to show the results list.

``` xml
    <co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView
            android:id="@+id/realm_recycler_view"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:layout_behavior="@string/appbar_scrolling_view_behavior"
            app:rrvLayoutType="LinearLayout"
            app:rrvEmptyLayoutId="@layout/empty_view" />
```

The `associateRealmRecyclerView()` method **MUST** be called before calling the `setAdapter()`.

``` java
    @Bind(R.id.search_view) RealmSearchView searchView;
    @Bind(R.id.realm_recycler_view) RealmRecyclerView recyclerView;
```

``` java
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	[…]
        searchView.associateRealmRecyclerView(recyclerView);
        searchView.setAdapter(adapter);
    }
```

##The `RealmSearchView` attributes:

* `rsvHint`: This is the search box hint. It a string reference.

##RealmSearchAdapter

The adapter does all the filtering. The following list of parameters are available to customize the filtering.

* `filterKey`: The filterKey is required as it is the columnName that the results are filtered by.

* `useContains`: If true, uses `contains`, otherwise uses `beginsWith`.

* `useCaseSensitive`: If true, ensures that the filter is respecting casing. If false, ignores any casing.

* `sortAscending`: If true, ascending, otherwise descending.

* `sortKey`: The columnName by which the results should be sorted.

* `basePredicate`: The basePredicate is used to filter the results whenever the searchBar is empty.

The `RealmSearchAdapter` has two constructors. One that only takes the `filterKey` parameter and one that takes all parameters.
In addition, the adapter has to be provided with a valid instance of Realm. It is used throughout the life of view to requery the results.

##Work in progress!
This library is currently under develpment. I would love to hear your feedback. Do you find the `RealmSearchView` useful? What functionality are you missing? Open a `Github` issue and let me know. Thanks!

##License
```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
