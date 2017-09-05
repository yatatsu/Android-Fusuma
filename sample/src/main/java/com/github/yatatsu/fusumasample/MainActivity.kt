package com.github.yatatsu.fusumasample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val feature = GreatNewFeature()
    feature.call()

    val conditional = ConditionalFeature()
    conditional.call()
  }
}
