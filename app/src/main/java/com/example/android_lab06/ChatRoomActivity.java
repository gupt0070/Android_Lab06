package com.example.android_lab06;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {
    Message msg = null;
    private ArrayList<Message> list = new ArrayList();
    Database dbOpener = new Database(this);
    SQLiteDatabase db;
    Boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        ListView listview = findViewById(R.id.listView);
        Button sendButton = findViewById(R.id.buttonSend);


        isTablet = findViewById(R.id.frameLayout) != null;

        db = dbOpener.getWritableDatabase();
        Cursor cursor = db.query(false, Database.TABLE_NAME, new String[]{Database._ID, Database.COL_MESSAGE, Database.COL_TYPE}, null, null, null, null, null, null);
        printCursor(cursor, Database.VERSION_NUM);
        while (cursor.moveToNext()) {
            String messagechat = cursor.getString(cursor.getColumnIndex(Database.COL_MESSAGE));
            String check = cursor.getString(cursor.getColumnIndex(Database.COL_TYPE));
            long id = cursor.getLong(cursor.getColumnIndex(Database._ID));


            if (check.equals("1"))
                list.add(new Message(id, messagechat, true));
            if (check.equals("0"))
                list.add(new Message(id, messagechat, false));
            ChatAdapter myAdapter = new ChatAdapter(list, getApplicationContext());
            listview.setAdapter(myAdapter);
            myAdapter.notifyDataSetChanged();
        }

        sendButton.setOnClickListener(click -> {
            EditText text = findViewById(R.id.msgBox);

            ContentValues cValues = new ContentValues();
            cValues.put(Database.COL_TYPE, "1");
            cValues.put(Database.COL_MESSAGE, text.getText().toString());
            Long id = db.insert(Database.TABLE_NAME, null, cValues);

            msg = new Message(id, text.getText().toString(), true);
            list.add(msg);

            ChatAdapter myAdapter = new ChatAdapter(list, getApplicationContext());
            listview.setAdapter(myAdapter);
            myAdapter.notifyDataSetChanged();
            text.getText().clear();
        });

        Button receiveButton = findViewById(R.id.buttonReceive);
        receiveButton.setOnClickListener(click -> {
            EditText text = findViewById(R.id.msgBox);
            ContentValues cValues = new ContentValues();
            cValues.put(Database.COL_TYPE, "0");
            cValues.put(Database.COL_MESSAGE, text.getText().toString());
            Long id = db.insert(Database.TABLE_NAME, null, cValues);
            msg = new Message(id, text.getText().toString(), false);
            list.add(msg);

            ChatAdapter myAdapter = new ChatAdapter(list, getApplicationContext());
            listview.setAdapter(myAdapter);
            myAdapter.notifyDataSetChanged();
            text.getText().clear();
        });

        listview.setOnItemLongClickListener((p, b, pos, id) -> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(R.string.deleteConfirmation)
                    .setPositiveButton("Yes", (click, arg) -> {
                        list.remove(pos);

                        int t = db.delete(Database.TABLE_NAME, Database._ID + "= ?", new String[]{Long.toString(id)});
                        ChatAdapter myAdapter = new ChatAdapter(list, getApplicationContext());
                        myAdapter.notifyDataSetChanged();
                        listview.setAdapter(myAdapter);
                        Fragment fa = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
                        if (isTablet && fa != null) {
                            getSupportFragmentManager().beginTransaction().remove(fa).commit();
                        }
                    })
                    //What the No button does:
                    .setNegativeButton("No", (click, arg) -> {
                    })

                    .setView(getLayoutInflater().inflate(R.layout.alert_dialog, null))
                    .setMessage("The row id is :" + pos + "\n" + "The database id is :" + list.get(pos).getId())
                    //Show the dialog
                    .create().show();

            return true;
        });

        listview.setOnItemClickListener((l, view, position, id) -> {
            DetailsFragment dFragment = new DetailsFragment();
            Bundle b = new Bundle();
            b.putString("message", list.get(position).getMessage());
            b.putLong("id", id);
            b.putBoolean("isSent", list.get(position).isSent);


            if (isTablet) {
                dFragment.setArguments(b);
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, dFragment).commit();
            } else {
                Intent i = new Intent(this, EmptyActivity.class);
                i.putExtras(b);
                startActivity(i);
            }
        });
    }

    public void printCursor(Cursor c, int version) {
        Log.i("**Database Version ", Integer.toString(db.getVersion()));
        Log.i("**No.of Columns ", Integer.toString(c.getColumnCount()));
        Log.i("**No.of Results", Integer.toString(c.getCount()));
        String s2 = "";
        for (String s : c.getColumnNames()) {
            s2 += s + " | ";
        }
        Log.i("**Column names", s2);
        c.moveToPosition(-1);
        while (c.moveToNext()) {
            String s1 = "";
            for (String s : c.getColumnNames()) {
                s2 += s + " | ";
                s1 += c.getString(c.getColumnIndex(s)) + " | ";
            }
            Log.i("**Row ", s1);
        }
        c.moveToPosition(-1);
    }

    public class Message {
        private String msg;
        private Boolean isSent;
        private long id;

        public Message(Long id, String msg, Boolean action) {
            this.id = id;
            setMessage(msg);
            setAction(action);
        }

        public void setMessage(String msg) {
            this.msg = msg;
        }

        public String getMessage() {
            return this.msg;
        }

        public void setAction(Boolean action) {
            this.isSent = action;
        }

        public Boolean getAction() {
            return this.isSent;
        }

        public long getId() {
            return this.id;
        }
    }

    public class ChatAdapter extends BaseAdapter {
        List<Message> messageList = new ArrayList<>();
        Context context;

        public ChatAdapter(List<Message> messageList, Context context) {
            this.messageList = messageList;
            this.context = context;
        }

        @Override
        public View getView(int i, View oldView, ViewGroup parent) {
            View newView = oldView;
            if (newView == null) {
                newView = getLayoutInflater().inflate(R.layout.alert_dialog, parent, false);
            }

            if (messageList.get(i).getAction() == true) {
                newView = getLayoutInflater().inflate(R.layout.right_layout, parent, false);
                TextView msgArea = newView.findViewById(R.id.msgRight);
                msgArea.setText(messageList.get(i).getMessage());

            } else if (messageList.get(i).getAction() == false) {
                newView = getLayoutInflater().inflate(R.layout.left_layout, parent, false);
                TextView msgArea = newView.findViewById(R.id.msgLeft);
                msgArea.setText(messageList.get(i).getMessage());
            }
            //}
            return newView;
        }

        @Override
        public int getCount() {
            return messageList.size();
        }

        @Override
        public long getItemId(int i) {
            return list.get(i).getId();
        }

        @Override
        public Message getItem(int i) {
            return messageList.get(i);
        }
    }

    public class Database extends SQLiteOpenHelper {
        protected final static String DATABASE_NAME = "ChatsDB";
        protected final static int VERSION_NUM = 1;
        public final static String TABLE_NAME = "CHATS";
        public final static String COL_MESSAGE = "MESSAGE";
        public final static String COL_TYPE = "TYPE";
        public final static String _ID = "_id";


        public Database(Context ctx) {
            super(ctx, DATABASE_NAME, null, VERSION_NUM);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

            sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_TYPE + " text," + COL_MESSAGE + " text);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(sqLiteDatabase);
        }
    }
}