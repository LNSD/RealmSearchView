#SearchView with result list powered by Realm

The `RealmSearchView` extends Android's `SearchView` widget. This is an much simpler and more versatile approach than [Thorbenprimke's original](https://github.com/thorbenprimke/realm-searchview) `RealmSearchView` which implements a wrapper around a `EditText` and `RealmRecyclerView`. It's build on top of `RealmRecyclerView` and `RealmBasedRecyclerViewAdapter`.

It's easily customizable via layout attributes and adapter parameters.

##Example
<p align="center">
	<img src="https://raw.githubusercontent.com/LNSD/realm-searchview/master/artwork/screencast-demo-app.gif"/ width="320">
</p>

##How to include in your project:

**Step 1.** Add the JitPack repository to your build file

Add it in your [root build.gradle](https://github.com/LNSD/RealmSearchView/blob/master/build.gradle#L22) at the end of repositories:
``` gradle
	allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```

**Step 2.** Add the dependency to your [module build.gradle](https://github.com/LNSD/RealmSearchView/blob/master/example/build.gradle#L37):

``` gradle
	dependencies {
	        compile 'com.github.lnsd:RealmSearchView:v0.1.0'
	}
```

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

##License [tl;dr](https://tldrlegal.com/license/mit-license)
```
The MIT License (MIT)
Copyright (c) 2016 Lorenzo Delgado

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```
