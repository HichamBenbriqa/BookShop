package com.example.hp.bookshop;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.hp.bookshop.Adapters.BookAdapter;
import com.example.hp.bookshop.Model.Book;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Catalogue extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private List<Book> bookList;
    private FirebaseFirestore db;
    private Context context;
    private  CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appBarLayout;

    public static Catalogue newInstance(int position) {
        Catalogue fragment = new Catalogue();
        Bundle args = new Bundle();
        args.putInt("pos", position);
        fragment.setArguments(args);
        return fragment;
    }
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.book_catalogue, container, false);
        recyclerView = rootView.findViewById(R.id.recycler_view);
       collapsingToolbar = (CollapsingToolbarLayout)rootView.findViewById(R.id.collapsing_toolbar);
        appBarLayout = (AppBarLayout) rootView.findViewById(R.id.appbar);
        context=getActivity().getApplicationContext();
        bookList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        prepareBooks();

        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        initCollapsingToolbar();
        try {
            Glide.with(this).load(R.drawable.cover).into((ImageView) rootView.findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }


        return rootView;
    }

    private void initCollapsingToolbar() {

        collapsingToolbar.setTitle("Books");

        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }


    private void prepareBooks() {
        System.out.println("hereee");
        db.collection("books")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            System.out.println("heree2");
                            for (QueryDocumentSnapshot document : task.getResult()) {
                               // Book b= new Book(document.get("iSBN").toString(),document.get("title").toString(),document.get("bookfile").toString(),document.get("summary").toString(),Integer.valueOf(document.get("edition").toString()).intValue()
                                //,Integer.valueOf(document.get("prix").toString()).intValue(),document.get("author").toString(),document.get("genre").toString(),document.get("image").toString());
                                document.get("iSBN").toString();
                                document.get("image").toString();
                                System.out.println("image"+ document.get("image").toString());
                                document.get("title").toString();

                                Book b = new Book(document.get("iSBN").toString(),document.get("title").toString(),document.get("image").toString());
                                System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+b);
                                bookList.add(b);
                                System.out.println("booklist"+bookList);
                            }
                        } else {
                            Log.w("error", "Error getting documents.", task.getException());
                        }
                        // use this setting to improve performance if you know that changes
                        // in content do not change the layout size of the RecyclerView
                        recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 2);
                        recyclerView.setLayoutManager(mLayoutManager);
                        // specify an adapter (see also next example)
                        adapter = new BookAdapter(context, bookList);

                        recyclerView.setAdapter(adapter);

                    }
                });



    }

    @Override
    public void onClick(View v) {

    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}