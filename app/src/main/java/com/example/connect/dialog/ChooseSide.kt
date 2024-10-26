package com.example.desktopdemo.Dialog;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.desktopdemo.Game.GameConfig;
import com.example.desktopdemo.R;


public class ChooseSide extends AlertDialog implements View.OnClickListener{
    private Context mContext;
    public ChooseSide(Context context) {
        super(context);
        mContext = context;
    }

    public ChooseSide(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_first_or_second_dialog);
        init();
    }

    private void init(){
        Button black_side = findViewById(R.id.black_side);
        Button white_side = findViewById(R.id.white_side);

        black_side.setOnClickListener(this);
        white_side.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.black_side:
                GameConfig.BlackStatus = GameConfig.PLAYER;
                GameConfig.WhiteStatus = GameConfig.AI;
                this.cancel();
                break;
            case R.id.white_side:
                GameConfig.WhiteStatus = GameConfig.PLAYER;
                GameConfig.BlackStatus = GameConfig.AI;

                this.cancel();
                break;
        }
    }
}
