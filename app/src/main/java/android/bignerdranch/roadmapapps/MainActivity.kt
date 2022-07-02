package android.bignerdranch.roadmapapps

import android.bignerdranch.roadmapapps.databinding.ActivityMainBinding
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.launchHandlerFirstLevel.setOnClickListener {
            startActivity(Intent(this, FirstLevelHandlerActivity::class.java))
        }

        binding.launchHandlerSecondLevel.setOnClickListener {
            startActivity(Intent(this, SecondLevelHandlerActivity::class.java))
        }
    }
}
