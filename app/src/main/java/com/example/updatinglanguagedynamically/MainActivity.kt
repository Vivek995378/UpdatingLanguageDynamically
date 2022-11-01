package com.example.updatinglanguagedynamically

import android.os.Build
import android.os.Bundle
import android.util.JsonReader
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import com.google.gson.Gson
import com.google.gson.JsonParser
import dev.b3nedikt.restring.Restring
import dev.b3nedikt.reword.Reword.reword
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

class MainActivity : BaseActivity() {

    lateinit var btnEnglish: AppCompatButton
    lateinit var btnHindi: AppCompatButton
    lateinit var btnPunjabi: AppCompatButton
    lateinit var englishLocale: Locale
    lateinit var hindiLocale: Locale
    lateinit var punjabiLocale: Locale
    var englishStringsMap: HashMap<String, String> = HashMap()
    var hindiStringsMap: HashMap<String, String> = HashMap()
    var punjabiStringsMap: HashMap<String, String> = HashMap()

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnEnglish = findViewById(R.id.btn_english)
        btnHindi = findViewById(R.id.btn_hindi)
        btnPunjabi = findViewById(R.id.btn_punjabi)

        configureLocales()
        updateStringsHashmap()

        btnEnglish.setOnClickListener {
            updateAppLanguage("en")
        }

        btnHindi.setOnClickListener {
            updateAppLanguage("hi")
        }

        btnPunjabi.setOnClickListener {
            updateAppLanguage("pun")
        }
    }

    private fun configureLocales() {
        englishLocale = Locale("en")
        hindiLocale = Locale("hi")
        punjabiLocale = Locale("pun")
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun updateStringsHashmap() {
        getLocalStringJsonHashmap("en").forEach {
            englishStringsMap[it.key] = it.value
        }
        getLocalStringJsonHashmap("hi").forEach {
            hindiStringsMap[it.key] = it.value
        }
        getLocalStringJsonHashmap("pun").forEach {
            punjabiStringsMap[it.key] = it.value
        }
    }

    private fun updateAppLanguage(language: String) {
        when (language) {
            "en" -> {
                Restring.putStrings(englishLocale, englishStringsMap)
                Restring.locale = englishLocale
            }
            "hi" -> {
                Restring.putStrings(hindiLocale, hindiStringsMap)
                Restring.locale = hindiLocale
            }
            else -> {
                Restring.putStrings(punjabiLocale, punjabiStringsMap)
                Restring.locale = punjabiLocale
            }
        }
        updateView()
    }

    private fun updateView() {
        val rootView: View = window.decorView.findViewById(android.R.id.content)
        reword(rootView)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun getLocalStringJsonHashmap(language: String): HashMap<String, String> {
        var listTypeJson: HashMap<String, String> = HashMap()
        try {
            applicationContext.assets.open("$language.json").use { inputStream ->

                val streamReader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
                val responseStrBuilder = StringBuilder()

                var inputStr: String?
                while (streamReader.readLine().also { inputStr = it } != null)
                    responseStrBuilder.append(inputStr)

                val jsonObject = JSONObject(responseStrBuilder.toString())

                listTypeJson = Gson().fromJson(jsonObject.toString(), java.util.HashMap::class.java) as HashMap<String, String>
            }
        } catch (exception: IOException) {
        }
        return listTypeJson
    }

}