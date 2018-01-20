package com.webgalaxie.blischke.bachelortakesix.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.webgalaxie.blischke.bachelortakesix.R;
import com.webgalaxie.blischke.bachelortakesix.models.AttachmentUpload;
import com.webgalaxie.blischke.bachelortakesix.other.Constants;

import java.util.ArrayList;

public class SwipeRecyclerViewAdapter extends RecyclerSwipeAdapter<SwipeRecyclerViewAdapter.SimpleViewHolder> {

    DatabaseReference pictureDataRef;
    FirebaseAuth auth;
    FirebaseUser user;
    String user_id, attachmentID, immo_id;
    private Context mContext;
    private ArrayList<AttachmentUpload> attachmentUploads;

    public SwipeRecyclerViewAdapter(Context context, ArrayList<AttachmentUpload> objects) {
        this.mContext = context;
        this.attachmentUploads = objects;

    }


    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_attachment_list, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {
        final AttachmentUpload item = attachmentUploads.get(position);


        viewHolder.attachmentName.setText(item.getName());
        Glide.with(mContext).load(item.getUrl()).into(viewHolder.attachmentImageView);


        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);


        //dari kanan
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.bottom_wraper));


        viewHolder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {

            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });


        viewHolder.Delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                AttachmentUpload attachmentUpload = attachmentUploads.get(position);
                auth = FirebaseAuth.getInstance();
                user = auth.getCurrentUser();
                user_id = user.getUid();

                attachmentID = attachmentUpload.getId();
                immo_id = attachmentUpload.getImmo_ID();


                mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                // attachmentUploads.remove(position);


                pictureDataRef = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS)
                        .child(user_id)
                        .child(immo_id)
                        .child(Constants.DATABASE_PATH_ATTACHMENTS)
                        .child(attachmentID);


                pictureDataRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        pictureDataRef.removeValue();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                notifyItemRemoved(position);
                notifyItemRangeChanged(position, attachmentUploads.size());
                mItemManger.closeAllItems();
            }
        });


        mItemManger.bindView(viewHolder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return attachmentUploads.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public SwipeLayout swipeLayout;
        public TextView attachmentName;
        public TextView Delete;
        public ImageView attachmentImageView;

        public SimpleViewHolder(View itemView) {
            super(itemView);

            // get references to the view elements in the layout for populating the data
            attachmentName = itemView.findViewById(R.id.attachmentImageNameDisplay);
            attachmentImageView = itemView.findViewById(R.id.attachmentImageView);

            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            Delete = (TextView) itemView.findViewById(R.id.Delete);

        }
    }
}
