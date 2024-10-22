package com.example.desktopdemo.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.desktopdemo.MainActivity;
import com.example.desktopdemo.R;
import com.example.desktopdemo.Start_Board;

public class WinDialog extends AlertDialog implements View.OnClickListener {
    private Context mContext;
    private String title;

    public WinDialog(Context context) {
        super(context);
        mContext = context;
    }

    public WinDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    protected WinDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.win_dialog);
        init();

    }

    private void init(){
//        View win = findViewById(R.id.winDialog);
//        win.getBackground().setAlpha(20);
        Button restart = findViewById(R.id.restart);
        Button quit = findViewById(R.id.quit);
        TextView winTitle = findViewById(R.id.win_title);
        restart.setOnClickListener(this);
        quit.setOnClickListener(this);
        winTitle.setText(title);

    }

    public void setWinTitle(String s){
        title = s;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.restart:
                Intent intent1 = new Intent(mContext, Start_Board.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(intent1);
                this.cancel();
                break;
            case R.id.quit:
                Intent intent2 = new Intent(mContext, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(intent2);
                this.cancel();
                break;

        }

    }
}
