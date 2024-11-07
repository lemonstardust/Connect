package com.example.connect.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.connect.dialog.ChooseSide;
import com.example.connect.game.GameConfig;
import com.example.desktopdemo.R;

import org.jetbrains.annotations.Nullable;


public class Start_Board extends AppCompatActivity implements View.OnClickListener{
    Button confirmButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board);

        if(GameConfig.VSWay != GameConfig.PLAYERVSPLAYER){
            ChooseSide chooseSide = new ChooseSide(this);
            chooseSide.show();
        }


        //绑定确定按钮
        confirmButton = findViewById(R.id.enterButton);
        confirmButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.enterButton:
//                //确定按钮按下，弹出提示文字
//                Toast.makeText(this,"BlackStatus = "+GameConfig.BlackStatus + "AILEVEL=" + GameConfig.AILevel,Toast.LENGTH_SHORT).show();
//        }
    }
}
