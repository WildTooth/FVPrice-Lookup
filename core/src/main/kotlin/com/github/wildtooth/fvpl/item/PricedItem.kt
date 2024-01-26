package com.github.wildtooth.fvpl.item

data class PricedItem(
  val displayName: String,
  val expectedPriceDBS: Double,
  val stackPrice: Boolean = true
) : FVItem {
  override fun getItem(): String {
    return displayName
  }
}
