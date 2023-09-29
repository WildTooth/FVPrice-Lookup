package com.github.wildtooth.fvpl.item

/**
 * Represents an item that can be priced.
 * @see SkullItem
 */
interface FVItem {
  /**
   * Gets the string reference / display name of the item.
   * @return The string representation of the item.
   */
  fun getItem(): String
}