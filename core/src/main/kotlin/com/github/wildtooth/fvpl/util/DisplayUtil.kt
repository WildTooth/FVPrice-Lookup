package com.github.wildtooth.fvpl.util

import net.labymod.api.client.component.Component
import net.labymod.api.client.component.format.NamedTextColor
import net.labymod.api.client.component.format.TextColor

class DisplayUtil {
  init {
    throw AssertionError("Please do not instantiate this class.")
  }

  companion object {
    fun displayPrettily(tooltipLines: MutableList<Component>, text: String, index: Int = 1) {
      displayPrettily(tooltipLines, index, text, NamedTextColor.GRAY)
    }

    fun displayPrettily(tooltipLines: MutableList<Component>, index: Int, text: String, color: TextColor) {
      tooltipLines.add(index, Component.text(text, color))
    }

    fun displayPrettily(tooltipLines: MutableList<Component>, vararg lines: String) {
      displayPrettily(tooltipLines, NamedTextColor.GRAY, *lines)
    }

    fun displayPrettily(tooltipLines: MutableList<Component>, color: TextColor, vararg lines: String) {
      for (i in lines.indices) {
        tooltipLines.add(i + 1, Component.text(lines[i], color))
      }
    }

    fun displayPrettily(tooltipLines: MutableList<Component>, index: Int, color: TextColor, vararg lines: String) {
      for (i in lines.indices) {
        displayPrettily(tooltipLines, index + i, lines[i], color)
      }
    }
  }
}