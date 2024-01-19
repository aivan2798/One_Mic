package com.blaqbokx

import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

import java.io.File

import androidx.activity.ComponentActivity
import android.os.Bundle
import android.Manifest
import android.os.Environment
import android.widget.Toast 
import android.content.Context

import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.isGranted
//import com.google.accompanist.permissions.allGranted
import com.google.accompanist.permissions.ExperimentalPermissionsApi

import androidx.compose.ui.window.Dialog
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.geometry.Offset

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.Text
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ShoppingCart

import androidx.compose.material3.ElevatedButton

import androidx.compose.material.TopAppBar
import androidx.compose.material.Colors
import androidx.compose.material.Icon
import androidx.compose.material.IconButton

import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.Image

import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.MutableState

import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext

import androidx.compose.material3.AlertDialog

import androidx.compose.animation.core.Animatable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.RepeatMode

import com.wiryadev.bootstrapiconscompose.bootstrapicons.Filled
import com.wiryadev.bootstrapiconscompose.bootstrapicons.Normal
import com.wiryadev.bootstrapiconscompose.bootstrapicons.filled.*
//import com.wiryadev.bootstrapiconscompose.bootstrapicons.normal.*
import com.wiryadev.bootstrapiconscompose.BsIcon
import com.wiryadev.bootstrapiconscompose.BootstrapIcons

import com.blaqbokx.Mic
import com.blaqbokx.PCMRecorder

class MainActivity : ComponentActivity()
{
    
    var amic: Mic= Mic()
    var apcm: PCMRecorder = PCMRecorder()
    override fun onCreate(bundle: Bundle?)
    {
        super.onCreate(bundle)

        
    

        setContent{
            Scaffold(topBar={AppBar()})
                {
                    getPermissions()
                    Dashboard()
                }
            //Header()
        }
    }

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun getPermissions()
{
    /*
    var mic_state = rememberPermissionState(Manifest.permission.RECORD_AUDIO,{astate->
    var man = astate})
    var file_read_state = rememberPermissionState(Manifest.permission.READ_EXTERNAL_STORAGE)
    var file_write_state = rememberPermissionState(Manifest.permission.READ_EXTERNAL_STORAGE)
     */

    var make_perms = rememberMultiplePermissionsState(listOf(Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE))

    if (make_perms.allPermissionsGranted==false)
    {
        LaunchedEffect(Unit)
        {
            make_perms.launchMultiplePermissionRequest()
        }
    }
}
 
 @Composable
 fun Header()
 {
    Text("One Mic ")
 }

 @Composable
 fun AppBar()
 {
    TopAppBar(
        elevation=50.dp,
        title={Text(text="OneMic",color=Color.White,fontWeight=FontWeight.Bold,letterSpacing=10.sp)},
        backgroundColor= Color(0xFF880061),
        modifier= Modifier.height(30.dp),
        navigationIcon = {
                            Image(
                                    painter=painterResource(R.drawable.app_icon),
                                    contentDescription=" "
                                )
                        }
        )
 }

 @Composable
fun Dashboard()
{
    val bg_color = Brush.linearGradient(
     0.0f to Color.White,//Color.Black,
     //0.5f to Color.Black,
     100.0f to Color(0xFF880061),
     tileMode=TileMode.Repeated
    // start = Offset(0.0f, 50.0f),
     //end = Offset(50.0f, 100.0f)
)

    Column(modifier=Modifier.fillMaxHeight().fillMaxWidth().background(bg_color))
        {
            Text(text="One Mic, Record Yo Audio Directly To The Cloud",letterSpacing=5.sp,textAlign=TextAlign.Center,modifier=Modifier.fillMaxWidth())
            recordBtn()
            
            //modifier=Modifier.align(alignment:Alignment.CenterVertically))
        }
}

@Composable
fun recordBtn()
{
    var is_clicked = rememberSaveable{mutableStateOf(false)}

    LaunchedEffect(is_clicked)
    {
        /*Dialog(onDismissRequest={}){
            Text("ok")
        }
        */
        if(is_clicked.value==true)
        {
            
        }
        else
        {
            
        }
    }
    if((is_clicked.value)==false) startRecord(is_clicked) else recording(is_clicked)

}

@Composable
fun alert(text:String)
{
    Dialog(onDismissRequest={})
                    {
                        Text(text)
                    }
}

@Composable
fun startRecord(is_clicked:MutableState<Boolean>)
{
    var is_fin = rememberSaveable{mutableStateOf(false)}
    val ctx = LocalContext.current
    val store_dir:String = Environment.getExternalStorageDirectory().getPath()
    val audio_file:File = File(store_dir,"mylife.pcm")
    Box(contentAlignment=Alignment.Center,modifier=Modifier.fillMaxHeight()
            .fillMaxWidth())
            {
            if (is_fin.value==true) alert(store_dir)
            IconButton(onClick=
                {
                    (is_clicked.value)=!(is_clicked.value)
                    var msg: String = "amic.record(audio_file)"//(store_dir,"mylife.pcm")
                    //var msg:String = 
                    apcm.startRecording()
                    is_fin.value=true
                    toast(ctx,"record finished: "+msg)
                },
                       modifier=Modifier.background(
                                                    color=Color(0xFF880061),
                                                    shape=RoundedCornerShape(topStart=100.dp,topEnd=100.dp,bottomStart=100.dp,bottomEnd=100.dp)
                                                    )
                                                    .height(200.dp)
                                                    .width(200.dp)
                                                    .border(width=10.dp,color=Color.Green,shape=RoundedCornerShape(topStart=100.dp,topEnd=100.dp,bottomStart=100.dp,bottomEnd=100.dp))

                      )
                            {
                                BsIcon(bsIcon = BootstrapIcons.Filled.Mic,tint=Color.Green,modifier=Modifier.size(100.dp))

                            }
            //alert(audio_file.getPath())
            }
}


@Composable
fun recording(is_clicked:MutableState<Boolean>)
{
    val density = LocalDensity.current.density
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val acenter = Offset(screenWidth.value / 2f, screenHeight.value / 2f)

    val animatedValue = remember{Animatable(10.dp.value)}


    LaunchedEffect(Unit)
    {
        animatedValue.animateTo(
            targetValue=30.dp.value,
            animationSpec=infiniteRepeatable(
                    animation=tween(durationMillis = 500,
                                easing = LinearEasing),
                    repeatMode=RepeatMode.Reverse
                                )
        )
    }
    
    val recording_color= Brush.radialGradient(
     0.5f to Color.White,
     1.0f to Color(0xFF9a8d8d),
     //center= acenter,
     //radius = 15.0f,
    // tileMode = TileMode.Repeated
)
    Box(contentAlignment=Alignment.Center,modifier=Modifier.fillMaxHeight()
            .fillMaxWidth())
            {
            IconButton(
                        onClick={
                                    (is_clicked.value)=!(is_clicked.value)
                                    
                                    //amic.stopRecord()
                                    apcm.stopRecording()
                                },
                       modifier=Modifier.background(
                                                    color=Color(0xFF880061),
                                                    shape=RoundedCornerShape(topStart=100.dp,topEnd=100.dp,bottomStart=100.dp,bottomEnd=100.dp)
                                                    )
                                                    .height(200.dp)
                                                    .width(200.dp)
                                                    .border(width=animatedValue.value.dp,brush=recording_color,shape=RoundedCornerShape(topStart=100.dp,topEnd=100.dp,bottomStart=100.dp,bottomEnd=100.dp))

                      )
                            {
                                 BsIcon(bsIcon = BootstrapIcons.Filled.Mic,tint=Color.White,modifier=Modifier.size(100.dp))

                            }
            }
}

fun toast(ctx:Context,msg:String)
{
    Toast.makeText(ctx,msg, Toast.LENGTH_LONG).show() 
}

}

