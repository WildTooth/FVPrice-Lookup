package com.github.wildtooth.fvtp.storage

import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class InformationConnector {
  /**
   * Gets the connection to the source.
   *
   * @param source The source to get the connection to.
   * @return The connection to the source.
   * @throws IOException If the connection could not be established.
   */
  @Throws(IOException::class)
  fun getInformationConnection(source: URL): HttpURLConnection {
    return source.openConnection() as HttpURLConnection
  }
}