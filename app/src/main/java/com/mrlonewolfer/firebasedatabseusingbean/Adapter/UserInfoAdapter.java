package com.mrlonewolfer.firebasedatabseusingbean.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mrlonewolfer.firebasedatabseusingbean.Model.UserBean;
import com.mrlonewolfer.firebasedatabseusingbean.R;

import java.util.ArrayList;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class UserInfoAdapter extends RecyclerView.Adapter<UserInfoAdapter.userViewHolder> {

    onUserClickListenr listenr;
    ArrayList<UserBean> userList;

    public UserInfoAdapter(onUserClickListenr listenr, ArrayList<UserBean> userList) {
        this.listenr = listenr;
        this.userList = userList;
    }

    @NonNull
    @Override
    public userViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.user_row_iteam,parent,false);

        userViewHolder viewHolder=new userViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull userViewHolder holder, int position) {

        final UserBean userBean=userList.get(position);
        holder.txtfname.setText(userBean.getFname());
        holder.txtlname.setText(userBean.getLname());
        holder.txtemail.setText(userBean.getEmail());
        holder.txtphone.setText(userBean.getMobile());

            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            holder.cardView.setCardBackgroundColor(color);

        holder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenr.onUserClick(userBean,"delete");
            }
        });
        holder.imgedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenr.onUserClick(userBean,"edit");
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


    public interface onUserClickListenr{
        void onUserClick(UserBean userBean,String myaction);
    }

    public class userViewHolder extends RecyclerView.ViewHolder{

        TextView txtfname,txtlname,txtemail,txtphone;
        ImageView imgdelete,imgedit;
        CardView cardView;
        public userViewHolder(@NonNull View itemView) {
            super(itemView);
            txtfname=itemView.findViewById(R.id.txtfname);
            txtlname=itemView.findViewById(R.id.txtlname);
            txtemail=itemView.findViewById(R.id.txtemail);
            txtphone=itemView.findViewById(R.id.txtmobile);

            imgdelete=itemView.findViewById(R.id.imgdelete);
            imgedit=itemView.findViewById(R.id.imgedit);
            cardView=itemView.findViewById(R.id.cardView);

        }
    }
}
