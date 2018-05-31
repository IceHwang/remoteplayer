package com.example.hhb.remoteplayer;



import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;


public class CameraAdapter extends RecyclerView.Adapter<CameraAdapter.ViewHolder>
{

    private List<Camera> mCameraList;

    private Context mContext;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType)
    {
        if(mContext==null)
        {
            mContext=parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.camera_view,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int position=viewHolder.getAdapterPosition();
                Camera camera = mCameraList.get(position);
                Intent intent= new Intent(mContext,CameraActivity.class);
                intent.putExtra(CameraActivity.CAMERA_NAME,camera.getName());
                intent.putExtra(CameraActivity.CAMERA_IMAGE_ID,camera.getImageId());
                mContext.startActivity(intent);
            }
        });
        return viewHolder;



    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Camera camera = mCameraList.get(position);
        holder.cameraName.setText(camera.getName());
        Glide.with(mContext).load(camera.getImageId()).into(holder.cameraImage);
    }

    @Override
    public int getItemCount() {
        return mCameraList.size();
    }

    static  class ViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;
        ImageView cameraImage;
        TextView cameraName;

        public ViewHolder(View view)
        {
            super(view);
            cardView =(CardView) view;
            cameraImage=(ImageView)view.findViewById(R.id.camera_image);
            cameraName=(TextView)view.findViewById(R.id.camera_name);
        }
    }

    public CameraAdapter(List<Camera> cameraList)
    {
        mCameraList=cameraList;
    }



}
