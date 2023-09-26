package com.github.wildtooth.fvtp.storage

import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class InformationConnector {
  @Throws(IOException::class)
  fun getInformationConnection(source: URL): HttpURLConnection {
    return source.openConnection() as HttpURLConnection
  }
}