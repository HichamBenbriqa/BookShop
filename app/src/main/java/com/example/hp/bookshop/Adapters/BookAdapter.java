package com.example.hp.bookshop.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.bookshop.GlideApp;
import com.example.hp.bookshop.Model.Book;
import com.example.hp.bookshop.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyViewHolder> {

    private Context mContext;
    private List<Book> bookList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, iSBN;
        public ImageView image, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            iSBN = (TextView) view.findViewById(R.id.count);
            image = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }


    public BookAdapter(Context mContext, List<Book> bookList) {
        this.mContext = mContext;
        this.bookList = bookList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bookcard, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.iSBN.setText(book.getiSBN());
        holder.title.setText(book.getTitle());
        /*holder.Summary.setText(book.getSummary());
        holder.Edition.setText(book.getEdition());
        holder.genre.setText(book.getGenre());
        holder.Author.setText(book.getAuthor());*/
        // loading album cover using Glide library
        System.out.println("glide"+book.getImage() );
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(book.getImage());
        // Download directly from StorageReference using Glide
        // (See MyAppGlideModule for Loader registration)
        GlideApp.with(mContext /* context */)
                .load(storageReference).centerInside()
                .fitCenter()
                .into(holder.image);
          //  Glide.with(mContext).load(book.getImage()).into(holder.image);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.book_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;

                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }
}