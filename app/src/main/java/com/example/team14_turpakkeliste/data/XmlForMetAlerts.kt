package com.example.team14_turpakkeliste.data

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import android.util.Xml
import com.example.team14_turpakkeliste.data.models.Alert
import com.example.team14_turpakkeliste.data.models.MetAlertsMain
import java.io.InputStream

private val ns: String? = null


/**Parser MetAlerts kallet som returnerer mange url-er
 */

class XmlForMetAlerts {
    @Throws(XmlPullParserException::class, IOException::class)
    fun parse(inputStream: InputStream): List<MetAlertsMain> {
        inputStream.use {
            val parser: XmlPullParser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(it, null)
            parser.nextTag()
            return readFeed(parser)
        }
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readFeed(parser: XmlPullParser): List<MetAlertsMain> {
        val dataList = mutableListOf<MetAlertsMain>()
        parser.require(XmlPullParser.START_TAG, ns, "rss")
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "item" -> {
                    dataList.add(readEntry(parser))
                }
                "channel" -> {
                    parser.require(XmlPullParser.START_TAG, ns, "channel")
                }
                else -> {
                    skip(parser)
                }
            }
        }
        return dataList
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readEntry(parser: XmlPullParser): MetAlertsMain {
        parser.require(XmlPullParser.START_TAG, ns, "item")
        var title: String? = null
        var description: String? = null
        var link: String? = null
        var author: String? = null
        var category: String? = null
        var guid: String? = null
        var pubDate: String? = null

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "title" -> title = readText(parser)
                "description" -> description = readText(parser)
                "link" -> link = readAttribute(parser, parser.name)
                "author" -> author = readAttribute(parser, parser.name)
                "category" -> category = readAttribute(parser, parser.name)
                "guid" -> guid = readAttribute(parser, parser.name)
                "pubDate" -> pubDate = readAttribute(parser, parser.name)
                else -> skip(parser)
            }
        }
        return MetAlertsMain(title,description,link,author,category,guid,pubDate)
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readAttribute(parser: XmlPullParser, tag: String): String {
        parser.require(XmlPullParser.START_TAG, ns, tag)
        val value = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, tag)
        return value
    }


    @Throws(IOException::class, XmlPullParserException::class)
    private fun readText(parser: XmlPullParser): String {
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        return result
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun skip(parser: XmlPullParser) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            throw IllegalStateException()
        }
        var depth = 1
        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }
        }
    }

}


/**Parser metAlert kall med farevarsler.
 * */
class XmlCurrentAlert {
    @Throws(XmlPullParserException::class, IOException::class)
    fun parse(inputStream: InputStream): List<Alert> {
        inputStream.use {
            val parser: XmlPullParser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(it, null)
            parser.nextTag()
            return readFeed(parser)
        }
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readFeed(parser: XmlPullParser): List<Alert> {
        val dataList = mutableListOf<Alert>()
        parser.require(XmlPullParser.START_TAG, ns, "alert")
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            if (parser.name == "info") {
                dataList.add(readEntry(parser))
            } else {
                skip(parser)
            }
        }
        return dataList
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readEntry(parser: XmlPullParser): Alert {
        parser.require(XmlPullParser.START_TAG, ns, "info")
        var language: String? = null
        var event: String? = null
        var severity: String? = null
        var certainty: String? = null
        var headline: String? = null
        var description: String? = null
        var instruction: String? = null
        var domain: String? = null
        var eventAwarenessName: String? = null
        var awarenessLevel: String? = null
        var awarenessSeriousness: String? = null
        var awarenessType: String? = null
        var areaPolygon: String? = null
        var eventCode: String? = null
        val parameters: MutableList<Pair<String?, String?>> = mutableListOf()


        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "language" -> language = readText(parser)
                "event" -> event = readText(parser)
                "severity" -> severity = readAttribute(parser, parser.name)
                "certainty" -> certainty = readAttribute(parser, parser.name)
                "headline" -> headline = readAttribute(parser, parser.name)
                "description" -> description = readAttribute(parser, parser.name)
                "instruction" -> instruction = readAttribute(parser, parser.name)
                "parameter" -> parameters.add(readParameter(parser))
                "area" -> areaPolygon = readArea(parser)!!.trim()
                "eventCode" -> eventCode = readEventCode(parser)!!.trim()
                else -> skip(parser)
            }
        }

        for (p in parameters) {
            when (p.first) {
                "eventAwarenessName" -> eventAwarenessName = p.second
                "awareness_level" -> awarenessLevel = p.second
                "awarenessSeriousness" -> awarenessSeriousness = p.second
                "awareness_type" -> awarenessType = p.second
                "geographicDomain" -> domain = p.second
            }
        }
        return Alert(
            language,
            event,
            severity,
            certainty,
            headline,
            description,
            instruction,
            eventAwarenessName,
            awarenessLevel,
            awarenessSeriousness,
            awarenessType,
            domain,
            areaPolygon,
            eventCode
        )
    }


    @Throws(XmlPullParserException::class, IOException::class)
    private fun readParameter(parser: XmlPullParser): Pair<String?, String?> {
        parser.require(XmlPullParser.START_TAG, ns, "parameter")
        var valueName: String? = null
        var value: String? = null

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "valueName" -> valueName = readText(parser)
                "value" -> value = readText(parser)
                else -> skip(parser)
            }
        }
        return Pair(valueName, value)
    }
    @Throws(XmlPullParserException::class, IOException::class)
    private fun readEventCode(parser: XmlPullParser): String? {
        parser.require(XmlPullParser.START_TAG, ns, "eventCode")
        var value: String? = null

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "valueName" -> readText(parser)
                "value" -> value = readText(parser)
                else -> skip(parser)
            }
        }
        return value
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readArea(parser: XmlPullParser): String? {
        parser.require(XmlPullParser.START_TAG, ns, "area")
        var areaPolygon: String? = null

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "polygon" -> areaPolygon = readText(parser)
                else -> skip(parser)
            }
        }
        return areaPolygon
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readAttribute(parser: XmlPullParser, tag: String): String {
        parser.require(XmlPullParser.START_TAG, ns, tag)
        val value = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, tag)
        return value
    }


    @Throws(IOException::class, XmlPullParserException::class)
    private fun readText(parser: XmlPullParser): String {
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        return result
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun skip(parser: XmlPullParser) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            throw IllegalStateException()
        }
        var depth = 1
        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }
        }
    }

}