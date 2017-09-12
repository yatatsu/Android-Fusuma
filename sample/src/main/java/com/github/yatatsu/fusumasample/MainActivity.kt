package com.github.yatatsu.fusumasample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.yatatsu.fusuma.annotation.OpenFusuma

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val feature = GreatNewFeature()
    feature.call()

    val conditional = ConditionalFeature()
    conditional.call()
  }

  @OpenFusuma fun open1() {
    val feature = GreatNewFeature()
    feature.call()
  }

  @OpenFusuma(targetType = GreatNewFeature::class) fun open2() {
    val great = GreatNewFeature()
    great.call()

    val great2 = GreatNewFeature2()
    great2.call()
  }
}