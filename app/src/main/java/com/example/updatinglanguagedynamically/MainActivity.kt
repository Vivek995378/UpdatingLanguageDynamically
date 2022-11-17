package com.example.updatinglanguagedynamically

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import com.google.gson.Gson
import dev.b3nedikt.restring.Restring
import dev.b3nedikt.reword.Reword.reword
import org.json.JSONObject
import java.io.*
import java.util.*


class MainActivity : BaseActivity() {

    lateinit var btnEnglish: AppCompatButton
    lateinit var btnHindi: AppCompatButton
    lateinit var btnPunjabi: AppCompatButton
    lateinit var btnLocal: AppCompatButton
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

        btnEnglish.setOnClickListener {
            writeToEnglishFile("en.json")
            updateStringsHashmap("en")
            updateAppLanguage("en")
        }

        btnHindi.setOnClickListener {
            writeToHindiFile("hi.json")
            updateStringsHashmap("hi")
            updateAppLanguage("hi")
        }

        btnPunjabi.setOnClickListener {
            writeToPunjabiFile("pun.json")
            updateStringsHashmap("pun")
            updateAppLanguage("pun")
        }


    }

    private fun configureLocales() {
        englishLocale = Locale("en")
        hindiLocale = Locale("hi")
        punjabiLocale = Locale("pun")
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun updateStringsHashmap(language: String) {
        when (language) {
            "en" -> {
                getLocalStringJsonHashmap("en").forEach {
                    englishStringsMap[it.key] = it.value
                }
            }
            "hi" -> {
                getLocalStringJsonHashmap("hi").forEach {
                    hindiStringsMap[it.key] = it.value
                }
            }
            else -> {
                getLocalStringJsonHashmap("pun").forEach {
                    punjabiStringsMap[it.key] = it.value
                }
            }
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
            val targetStream: InputStream = readFromFile("$language.json")

            val streamReader = BufferedReader(InputStreamReader(targetStream, "UTF-8"))
            val responseStrBuilder = StringBuilder()

            var inputStr: String?
            while (streamReader.readLine().also { inputStr = it } != null)
                responseStrBuilder.append(inputStr)

            val jsonObject = JSONObject(responseStrBuilder.toString())

            listTypeJson = Gson().fromJson(
                jsonObject.toString(),
                java.util.HashMap::class.java
            ) as HashMap<String, String>

        } catch (exception: IOException) {
            exception.toString()
        }
        return listTypeJson
    }

    fun writeToEnglishFile(fileName: String) {
        try {
            val json = JSONObject()
            json.put("app_name", "UpdatingLanguageDynamically")
            json.put("mydownloads", "My Downloads")
            json.put("title_activity_gklist", "Title")
            json.put("general_instructions", "General Instructions")
            json.put("current_affairs", "Current Affairs")
            json.put("title_activity_alertlist", "Job Alerts")
            json.put("settings", "Settings")
            json.put("download", "Download")
            json.put("magazine", "Magazine")
            val jsonString = json.toString()
            val fos = applicationContext.openFileOutput(fileName, MODE_PRIVATE)
            fos.write(jsonString.toByteArray())
            fos.close()
            Toast.makeText(this, "Wrote to file: " + fileName, Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.d("moengage", e.toString())
            e.printStackTrace()
        }
    }

    fun writeToHindiFile(fileName: String) {
        try {
            val json = JSONObject()
            json.put("app_name", "UpdatingLanguageDynamically")
            json.put("mydownloads", "मेरे डाउनलोड")
            json.put("title_activity_gklist", "शीर्षक")
            json.put("general_instructions", "सामान्य अनुदेश")
            json.put("current_affairs", "कर्रेंट अफेयर्स")
            json.put("title_activity_alertlist", "जॉब अलर्")
            json.put("settings", "सेटिंग")
            json.put("download", "डाउनलोड")
            json.put("magazine", "मैगज़ीन")
            val jsonString = json.toString()
            val fos = applicationContext.openFileOutput(fileName, MODE_PRIVATE)
            fos.write(jsonString.toByteArray())
            fos.close()
            Toast.makeText(this, "Wrote to file: " + fileName, Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.d("moengage", e.toString())
            e.printStackTrace()
        }
    }

    fun writeToPunjabiFile(fileName: String) {
        try {
            val json = JSONObject()
            json.put("app_name", "UpdatingLanguageDynamically")
            json.put("mydownloads", "আমার ডাউনলোডগুলি")
            json.put("title_activity_gklist", "Title")
            json.put("general_instructions", "সাধারণ নির্দেশাবলী")
            json.put("current_affairs", "বর্তমান ব্যাপার")
            json.put("title_activity_alertlist", "চাকরির সতর্কতা")
            json.put("settings", "সেটিংস")
            json.put("download", "ডাউনলোড")
            json.put("magazine", "ম্যাগাজিন")
            val jsonString = json.toString()
            val fos = applicationContext.openFileOutput(fileName, MODE_PRIVATE)
            fos.write(jsonString.toByteArray())
            fos.close()
            Toast.makeText(this, "Wrote to file: " + fileName, Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.d("moengage", e.toString())
            e.printStackTrace()
        }
    }

    fun readFromFile(fileName: String): InputStream {
        val actualPath = File("/data/data/com.example.updatinglanguagedynamically/files", fileName)
        val stream = FileInputStream(actualPath)
        return try {
            Log.d("moengage", "e.toString()")
            stream
        } catch (e: Exception) {
            e.printStackTrace()
            stream
        }
    }
}