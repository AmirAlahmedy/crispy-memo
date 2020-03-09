package com.example.reminder.adapters;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reminder.R;
import com.example.reminder.Reminder;
import com.example.reminder.UI.Fragments.reminderFragment.OnListFragmentInteractionListener;
import com.example.reminder.UI.MainActivity;
import com.example.reminder.database.SqliteDatabase;
import com.example.reminder.dummy.DummyContent.String;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link String} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyreminderRecyclerViewAdapter extends RecyclerView.Adapter<MyreminderRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<Reminder> mValues;
    private final OnListFragmentInteractionListener mListener;

    private SqliteDatabase mDatabase;
    private Context context;

    public MyreminderRecyclerViewAdapter(Context context, ArrayList<Reminder> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;

        this.context = context;
        mDatabase = new SqliteDatabase(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_reminder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Reminder reminders =  mValues.get(position);

//        holder.mIdView.setText(mValues.get(position).getId());
        holder.mContentView.setText(mValues.get(position).getContent());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                    editReminderDialog(reminders);
                }
            }
        });

    }

    private void editReminderDialog(final Reminder reminders) {
        LayoutInflater inflater = LayoutInflater.from(context);
        final Dialog reminderDialog = new Dialog(context);
        reminderDialog.setContentView(R.layout.dialog_custom);
        final EditText contentField = (EditText) reminderDialog.findViewById(R.id.EditTextName);
        final CheckBox isImportant = (CheckBox) reminderDialog.findViewById(R.id.important);

        if(reminders != null) {
            contentField.setText(reminders.getContent());
        }

        CheckBox chkbx;
        chkbx = (CheckBox) reminderDialog.findViewById(R.id.important);
        chkbx.setChecked(reminders.isImportant());

        Button btn;
        Button btn2;
        btn2=(Button) reminderDialog.findViewById(R.id.btnDelete);
        btn2.setVisibility(View.VISIBLE);
        btn = (Button) reminderDialog.findViewById(R.id.btnAdd);
        btn.setText("Edit");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final java.lang.String content = contentField.getText().toString();
                final boolean important = isImportant.isChecked();

                if(TextUtils.isEmpty(content)){
                    Toast.makeText(context, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                }
                else{
                    mDatabase.updateReminder(new Reminder(reminders.getId(), content, important));
                   /* if(important)
                    {

                    }*/
                    //refresh the activity
                    ((Activity)context).finish();
                    context.startActivity(((Activity)context).getIntent());
                }
            }
        });



        TextView txtClose;
        txtClose = (TextView) reminderDialog.findViewById(R.id.txtClose);
        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reminderDialog.dismiss();
                Toast.makeText(context, "Task cancelled", Toast.LENGTH_LONG).show();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.deleteReminder(reminders.getId());
                ((Activity)context).finish();
                context.startActivity(((Activity)context).getIntent());
            }
        });
        reminderDialog.show();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView imgView;
        public final TextView mContentView;
        public String mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            imgView = (ImageView) view.findViewById(R.id.img);
            mContentView = (TextView) view.findViewById(R.id.content);

        }

        @Override
        public java.lang.String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

    }

}
