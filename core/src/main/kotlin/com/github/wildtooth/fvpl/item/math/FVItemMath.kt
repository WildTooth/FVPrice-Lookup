package com.github.wildtooth.fvpl.item.math

import com.github.wildtooth.fvpl.item.FVItem
import com.github.wildtooth.fvpl.item.PricedItem
import com.github.wildtooth.fvpl.item.SkullItem

class FVItemMath {
  /**
   * Sums the prices of the given items.
   * This function makes use of the average price of skull items.
   *
   * @param items The items to sum.
   * @return The sum of the prices of the given items.
   */
  fun sum(items: Collection<FVItem>): Double {
    var sum = 0.0
    for (item in items) {
      when (item) {
        is PricedItem -> {
          sum += if (item.stackPrice) item.expectedPriceDBS * 64 else item.expectedPriceDBS
        }
        is SkullItem -> {
          sum += item.getAveragePrice()
        }
      }
    }
    return sum
  }

  /**
   * Sums the minimum prices of the given items.
   */
  fun minSum(items: Collection<FVItem>): Double {
    var sum = 0.0
    for (item in items) {
      when (item) {
        is PricedItem -> {
          sum += if (item.stackPrice) item.expectedPriceDBS * 64 else item.expectedPriceDBS
        }
        is SkullItem -> {
          sum += item.getMinimumPrice()
        }
      }
    }
    return sum
  }

  /**
   * Sums the maximum prices of the given items.
   */
  fun maxSum(items: Collection<FVItem>): Double {
    var sum = 0.0
    for (item in items) {
      when (item) {
        is PricedItem -> {
          sum += if (item.stackPrice) item.expectedPriceDBS * 64 else item.expectedPriceDBS
        }

        is SkullItem -> {
          sum += item.getMaximumPrice()
        }
      }
    }
    return sum
  }
}