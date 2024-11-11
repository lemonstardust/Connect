package com.example.connect.activity


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.connect.dialog.ChooseSide
import com.example.connect.game.GameConfig
import com.example.desktopdemo.R
import com.example.desktopdemo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var chooseSide: ChooseSide? = null

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        chooseSide = ChooseSide(this@MainActivity)


        binding.pvp.setOnClickListener(this)
        binding.easy.setOnClickListener(this)
        binding.normal.setOnClickListener(this)
        binding.hard.setOnClickListener(this)
        binding.eve.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.easy -> {
                GameConfig.VSWay = GameConfig.PLAYERVSAI
                GameConfig.AILevel = GameConfig.NOVICEDEEP
                intent = Intent(this@MainActivity, StartBoard::class.java)
                startActivity(intent)
            }

            R.id.normal -> {
                GameConfig.VSWay = GameConfig.PLAYERVSAI
                GameConfig.AILevel = GameConfig.NORMALDEEP
                intent = Intent(this@MainActivity, StartBoard::class.java)
                startActivity(intent)
            }

            R.id.hard -> {
                GameConfig.AILevel = GameConfig.HARDDEEP
                GameConfig.VSWay = GameConfig.PLAYERVSAI
                intent = Intent(this@MainActivity, StartBoard::class.java)
                startActivity(intent)
            }

            R.id.pvp -> {
                GameConfig.VSWay = GameConfig.PLAYERVSPLAYER
                intent = Intent(this@MainActivity, StartBoard::class.java)
                startActivity(intent)
            }

            R.id.eve -> Toast.makeText(this@MainActivity, "暂未开放", Toast.LENGTH_SHORT).show()
        }
    }
}
