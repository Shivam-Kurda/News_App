package com.example.news_app

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.news_app.ui.theme.News_AppTheme
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import kotlinx.coroutines.GlobalScope

import kotlinx.coroutines.launch
import java.util.Locale


class Headlines : ComponentActivity() {
    lateinit var head_viewmodel: HeadlineViewModel;
    override fun onCreate(savedInstanceState: Bundle?) {
        head_viewmodel=ViewModelProvider(this)[HeadlineViewModel :: class.java]
        val location=intent.getStringExtra("state")

        val country="in";
        val locationprov= LocationServices.getFusedLocationProviderClient(this);

//        val type=intent.getStringExtra("Type");

        val pagesize=50;
        val categ=intent.getStringExtra("category");
        super.onCreate(savedInstanceState)
        if(categ=="Local News"){


            GlobalScope.launch {
                head_viewmodel.updatenews_location(location !!,pagesize)
            }
        }
        else{
            GlobalScope.launch {
                if (categ != null) {
                    val loc="Delhi"
                    head_viewmodel.updatenews(loc,country, categ.lowercase(Locale.ROOT))
                }
            }

        }


        setContent {
            News_AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting2("Android",head_viewmodel)
                }
            }
        }
    }
}

@Composable
fun Greeting2(name: String, viewmodel : HeadlineViewModel, modifier: Modifier = Modifier) {

    val news_list by viewmodel.news_titles.observeAsState()
    val cont= LocalContext.current


    LazyColumn(modifier=Modifier.fillMaxSize()) {
        item {
            for (i in news_list!!){
                Text(text = i.title,modifier=Modifier.fillMaxWidth().padding(12.dp).clickable {
                    val int=Intent(cont,News_Content :: class.java)
                    int.putExtra("Description",i.content);
                    int.putExtra("image_uri",i.urlToImage);
                    int.putExtra("weburl",i.url)
                    cont.startActivity(int);
                })

            }

        }

    }
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    News_AppTheme {
//        Greeting2("Android")
//    }
//}