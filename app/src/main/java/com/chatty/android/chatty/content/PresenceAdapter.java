package com.chatty.android.chatty.content;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chatty.android.chatty.PresenceActivity;
import com.chatty.android.chatty.R;
import com.chatty.android.chatty.utilities.Callbacks;
import com.chatty.android.chatty.utilities.KeyStore;

import java.util.List;

/**
 * Created by Alejandro on 05/05/2016.
 */
public class PresenceAdapter extends RecyclerView.Adapter<PresenceAdapter.Holder> {

    private List<String> users;
    private final Context mContext;
    private Callbacks.CallbackFavorite mCallback;
    private Activity activity;
    private SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Email = "emailKey";



    public PresenceAdapter(List<String> users, Context mContext, Activity act) {
        this.users = users;
        this.mContext = mContext;
        this.activity = act;
        sharedpreferences = mContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
        v.getLayoutParams().height = RecyclerView.LayoutParams.WRAP_CONTENT;
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.userTextView.setText(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView userTextView;
        View statusCircle;

        public Holder(View itemView) {
            super(itemView);
            try {
                mCallback = (Callbacks.CallbackFavorite) activity;
            }
            catch(ClassCastException e) {
                e.printStackTrace();
            }
            userTextView = (TextView) itemView.findViewById(R.id.userTextItem);
            statusCircle = (View) itemView.findViewById(R.id.view);
            userTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            try {
                if (!userTextView.getText().toString().equals(sharedpreferences.getString(Email, "")))
                    if (!KeyStore.getInstance().getFavorites().contains(userTextView.getText().toString())) {
                        mCallback.onUserPressed(userTextView.getText().toString());
                    } else {
                        mCallback.onUserAlreadyAdded();
                    }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
