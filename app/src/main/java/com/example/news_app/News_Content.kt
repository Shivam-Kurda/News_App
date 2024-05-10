package com.example.news_app

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.example.news_app.ui.theme.News_AppTheme

class News_Content : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val desc=intent.getStringExtra("Description")
        val img_uri=intent.getStringExtra("image_uri")
        val web_url=intent.getStringExtra("weburl")
        setContent {
            News_AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting3("Android",web_url)
                }
            }
        }
    }
}

@Composable
fun Greeting3(name: String, web_url: String?, modifier: Modifier = Modifier) {

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {

                webViewClient = WebViewClient() // Ensures links stay in the WebView
                web_url?.let { loadUrl(it) } // Load the specified URL
            }
        }

    )


}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    News_AppTheme {
//        Greeting3("Android")
//    }
//}