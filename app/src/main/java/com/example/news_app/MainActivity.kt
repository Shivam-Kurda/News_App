package com.example.news_app

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import kotlin.properties.Delegates

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val news_categs= mutableListOf("Health","Sports","Technology","Business","Entertainment","Science","Local News")
//        val src_names= mutableListOf("Google News (India)", "The Hindu", "The Times of India")
//        val src_ids= mutableListOf("google-news-in", "the-hindu", "the-times-of-india")
        super.onCreate(savedInstanceState)
        val view_m=ViewModelProvider(this)[Main_ViewModel :: class.java]
        setContent {
            News_AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android",news_categs,view_m)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, news_categs : MutableList<String>,view_m : Main_ViewModel,modifier: Modifier = Modifier) {
    val cont= LocalContext.current
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
//            Text(text = "Domains", fontSize = 32.sp,modifier= Modifier
//                .fillMaxWidth()
//                .padding(12.dp))
//            for (i in 0..<src_names.size){
//                Text(text = src_names[i], fontSize = 32.sp,modifier=Modifier.fillMaxWidth().padding(12.dp).clickable {
//                    val int = Intent(cont, Headlines::class.java)
//                    int.putExtra("Type","source")
//                    int.putExtra("domain",src_ids[i]);
//
//                    cont.startActivity(int)
//                })
//            }
            for(i in news_categs){
                Text(text = i , fontSize = 32.sp , modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .clickable {

                        if(i=="Local News"){
                            val locationprov= LocationServices.getFusedLocationProviderClient(cont as Activity);

                            if(ActivityCompat.checkSelfPermission(cont.applicationContext,android.Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_DENIED){
                                ActivityCompat.requestPermissions( cont as Activity, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION),101)
                            }
                            val loc=locationprov.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY,object :
                                CancellationToken(){
                                override fun onCanceledRequested(p0: OnTokenCanceledListener) = CancellationTokenSource().token

                                override fun isCancellationRequested() = false
                            })
                            var lat =28.7f;
                            var long =27.1f;


                            GlobalScope.launch {

                                loc.addOnSuccessListener {

                                        lat=it.latitude.toFloat()
                                        long=it.longitude.toFloat()





                                        Log.d("shivamLocation","Latitde : ${it.latitude} Longitude : ${it.longitude}")









                                }

                                val state=view_m.getstate(lat,long)



                                val int = Intent(cont, Headlines::class.java)

                                int.putExtra("category", i);
                                int.putExtra("state",state)
                                cont.startActivity(int)

                            }


                        }
                        else{
                            val int = Intent(cont, Headlines::class.java)
//
                            int.putExtra("category", i);
                            cont.startActivity(int)
                        }



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
//        Greeting("Android")
//    }
//}