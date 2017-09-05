package com.github.yatatsu.fusumasample

import com.github.yatatsu.fusuma.annotation.Fusuma


@Fusuma(openIf = ConditionalFeature.CONDITION) class ConditionalFeature {

  companion object {
    const val CONDITION = true
  }

  fun call() {
    //
  }

}
