package com.github.wildtooth.fvpl.storage

import com.github.wildtooth.fvpl.item.SkullItem
import com.opencsv.CSVReader
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.util.function.Consumer

class InformationReceiver(private val storage: InformationStorage) {

  /**
   * Catches the information from the connection and stores it in the storage.
   *
   * @param connection The connection to catch the information from.
   * @return Whether the information was caught and stored successfully.
   */
  fun catchConnection(connection: HttpURLConnection): Boolean {
    return try {
      storage.clear()
      val reader = CSVReader(BufferedReader(InputStreamReader(connection.inputStream)))
      forceRead(reader)
      true
    } catch (e: IOException) {
      false
    } finally {
      connection.disconnect()
    }
  }

  /**
   * Forces the reader to read all the information from the connection.
   *
   * @param reader The reader to read the information from.
   */
  private fun forceRead(reader: CSVReader) {
    reader.readAll().forEach(Consumer<Array<String>> { strings: Array<String> ->
      val receivedModifiers: ArrayList<String> = ArrayList()
      for (i in 2 until strings.size) {
        receivedModifiers.add(strings[i])
      }
      val modifiers: Array<SkullItem.PriceModifier> = Array(receivedModifiers.size) {
        SkullItem.PriceModifier.fromString(receivedModifiers[it])
      }
      storage.add(
        SkullItem(
          strings[0].lowercase(),
          SkullItem.Rarity.fromString(strings[1].uppercase()),
          modifiers
        )
      )
    })
  }
}