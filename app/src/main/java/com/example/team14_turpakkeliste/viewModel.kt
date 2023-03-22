package com.example.team14_turpakkeliste

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.team14_turpakkeliste.data.DataSource
import kotlinx.coroutines.launch
import java.io.InputStream

class viewModel(): ViewModel() {

    private val source: DataSource = DataSource()


    init {
        viewModelScope.launch {
            val response = source.getMetAlerts()
            val inputStream : InputStream = response.byteInputStream()
            var alerts = XmlParser().parse(inputStream)

            for (a in alerts) {
                println(a.title)
                println(a.description)
                println(a.link)
                println(a.author)
                println(a.category)
                println(a.guid)
                println(a.pubDate)
            }
        }
    }
}