package com.github.wildtooth.fvtp.storage

import com.github.wildtooth.fvtp.item.FVItem

class InformationStorage {
  init {
    if (instance != null) {
      throw IllegalStateException("InformationStorage has already been initialized!")
    }
    instance = this
  }
  companion object {
    var instance: InformationStorage? = null
      private set
  }
  private val storage = mutableMapOf<String, FVItem>()

  fun add(item: FVItem) {
    storage[item.getItem()] = item
  }

  fun get(item: String): FVItem? {
    return storage[item]
  }

  fun remove(item: String) {
    storage.remove(item)
  }

  fun clear() {
    storage.clear()
  }

  fun getAll(): List<FVItem> {
    return storage.values.toList()
  }
}