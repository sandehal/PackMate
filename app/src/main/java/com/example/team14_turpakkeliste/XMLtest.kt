package com.example.team14_turpakkeliste

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.BufferedReader
import java.io.IOException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class XMLtest {
    @Throws(XmlPullParserException:: class, IOException::class)
    suspend fun getData(): MutableList<Int>{
        var dataList = mutableListOf<Int>()
        withContext(Dispatchers.IO){
            val BASE_URL = URL("https://api.met.no/weatherapi/metalerts/1.1?lang=no")
            val connection = BASE_URL.openConnection() as HttpsURLConnection
            val factory = XmlPullParserFactory.newInstance().newPullParser()
            connection.requestMethod = "GET"
            val inputStream = connection.inputStream
            val xmlString = inputStream.bufferedReader().use(BufferedReader:: readText)
            factory.setInput(xmlString.reader())
            val parser = factory
            var eventType = factory.eventType

            while(eventType != XmlPullParser.END_DOCUMENT){
                when(eventType) {
                    XmlPullParser.START_TAG -> {
                        if (parser.name == "party"){
                            parser.nextTag()
                            val id = parser.nextText().toInt()
                            parser.nextTag()
                            val votes = parser.nextText().toInt()
                            dataList.add(id,votes)
                        }
                    }
                }
                eventType = parser.next()
            }
        }
        return dataList
    }

}