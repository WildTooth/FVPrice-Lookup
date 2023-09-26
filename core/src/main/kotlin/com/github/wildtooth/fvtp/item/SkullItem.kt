package com.github.wildtooth.fvtp.item

data class SkullItem(
  val displayName: String,
  val rarity: Rarity,
  val modifiers: Array<PriceModifier>
) : FVItem {
  override fun getItem(): String {
    return displayName
  }

  fun getMinimumPrice(): Double {
    var price = (rarity.minPriceInStacks * 64).toDouble()
    for (modifier in modifiers) {
      price += (if (modifier.isNotPercentage) (modifier.minEffectValue * 64).toDouble()
      else (price * (modifier.minEffectValue.toDouble() / 100.0)))
    }
    return price
  }

  fun getMaximumPrice(): Double {
    var price = (rarity.maxPriceInStacks * 64).toDouble()
    for (modifier in modifiers) {
      price += (if (modifier.isNotPercentage) (modifier.maxEffectValue * 64).toDouble()
      else (price * (modifier.maxEffectValue.toDouble() / 100.0)))
    }
    return price
  }

  fun getAveragePrice(): Double {
    return ((getMinimumPrice() + getMaximumPrice()) / 2)
  }

  fun getMinimumPriceInStacks(): Double {
    return (getMinimumPrice() / 64)
  }

  fun getMaximumPriceInStacks(): Double {
    return (getMaximumPrice() / 64)
  }

  fun getAveragePriceInStacks(): Double {
    return ((getMinimumPriceInStacks() + getMaximumPriceInStacks()) / 2)
  }

  fun isSellersPrice(): Boolean {
    return rarity.isSellersPrice
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as SkullItem

    if (displayName != other.displayName) return false
    if (rarity != other.rarity) return false
    if (!modifiers.contentEquals(other.modifiers)) return false

    return true
  }

  override fun hashCode(): Int {
    var result = displayName.hashCode()
    result = 31 * result + rarity.hashCode()
    result = 31 * result + modifiers.contentHashCode()
    return result
  }

  enum class Rarity(
    val minPriceInStacks: Int,
    val maxPriceInStacks: Int,
    val isSellersPrice: Boolean = false
  ) {
    NORMAL(3, 7),
    RARE(5, 12),
    SUPER_RARE(12, 18),
    ULTRA_RARE(18, 30),
    SEMI_UNIQUE(30, 50),
    SEMI_UNIQUE_PLUS(30, 40),
    UNIQUE(0, 0,true);

    companion object {
      fun fromString(string: String): Rarity {
        return when (string) {
          "NORMAL" -> NORMAL
          "N" -> NORMAL
          "RARE" -> RARE
          "R" -> RARE
          "SUPER_RARE" -> SUPER_RARE
          "SR" -> SUPER_RARE
          "ULTRA_RARE" -> ULTRA_RARE
          "UR" -> ULTRA_RARE
          "SEMI_UNIQUE" -> SEMI_UNIQUE
          "SU" -> SEMI_UNIQUE
          "SEMI_UNIQUE_PLUS" -> SEMI_UNIQUE_PLUS
          "SU+" -> SEMI_UNIQUE_PLUS
          "UNIQUE" -> UNIQUE
          "U" -> UNIQUE
          else -> throw IllegalArgumentException("Unknown rarity: $string")
        }
      }
    }
  }

  enum class PriceModifier(
    val minEffectValue: Int = 0,
    val maxEffectValue: Int = 0,
    val isNotPercentage: Boolean = false
  ) {
    OLD(30, 75),
    SOMEWHAT_OLD(10, 30),
    COLLECTION_HEAD(0, 20),
    HYPED(20, 50),
    NEW(0, 192, true),
    NONE;

    companion object {
      fun fromString(string: String): PriceModifier {
        return when (string) {
          "EARLY" -> OLD
          "MID" -> SOMEWHAT_OLD
          "SAMLING" -> COLLECTION_HEAD
          "HYPE" -> HYPED
          "NYT" -> NEW
          else -> NONE
        }
      }
    }
  }
}
