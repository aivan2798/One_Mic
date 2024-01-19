package com.blaqbokx;

import com.github.axet.opusjni.Opus;
import com.github.axet.opusjni.Config;
//import com.github.axet.androidlibrary.app.Native;



public class Opuser
{
    Opus android_opus;
    Config my_config;
    static 
    {
        System.loadLibrary("opusjni");
    }
    public Opuser()
    {
        my_config = new Config();
        Config.natives = true;
        
        
        
       // Native.loadLibraries(context, new String[]{"opus", "opusjni"});
    }

    public byte[] encode(short[] buf, int pos, int len)
    {
        
        android_opus = new Opus();
        android_opus.open(1,44100,128000);

        return android_opus.encode(buf,pos,len);
    }

    public void stop()
    {
        android_opus.close();
    }
}

