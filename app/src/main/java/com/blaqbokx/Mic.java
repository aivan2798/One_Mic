package com.blaqbokx;
import android.media.AudioRecord;
import android.media.AudioFormat;
import android.media.MediaRecorder;
import java.io.File;
import java.nio.channels.FileChannel;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

public class Mic
{
    public static int RECORDER_SAMPLERATE = 44100;

    public static int RECORDER_CHANNELS = AudioFormat.CHANNEL_CONFIGURATION_MONO;

    public static int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    boolean isRecording = false;
    boolean recorded = false;
    AudioRecord audioRecorder;
    File audio_file;
    FileOutputStream audio_fos;
    ByteBuffer myByteBuffer;
    FileChannel audio_chan;
    ObjectOutputStream audio_obj_strm;
    public void record(String dir_path,String file_path) 
    {
        int bufferSizeInBytes, bufferSizeInShorts;
        int shortsRead;
        short audioBuffer[];
        byte byteArray[]={0,1,2};
        try 
        {
            audio_file = new File(dir_path,file_path);
            audio_fos = new FileOutputStream(audio_file,true);

            /*
            audio_chan = audio_fos.getChannel();
            bufferSizeInBytes = AudioRecord.getMinBufferSize(RECORDER_SAMPLERATE,RECORDER_CHANNELS,RECORDER_AUDIO_ENCODING);
           // byteArray=new byte[bufferSizeInBytes];
            myByteBuffer = ByteBuffer.allocate(bufferSizeInBytes);
            bufferSizeInShorts = (bufferSizeInBytes /2);
            audioRecorder = new AudioRecord(MediaRecorder.AudioSource.VOICE_RECOGNITION, RECORDER_SAMPLERATE,RECORDER_CHANNELS,RECORDER_AUDIO_ENCODING, bufferSizeInBytes);
            audioBuffer = new short[bufferSizeInShorts];
            
            audioRecorder.startRecording(); 
            isRecording = true;
            recorded = true;
            while (isRecording)
            { 
                    shortsRead = audioRecorder.read(audioBuffer, 0, bufferSizeInShorts);

                    if (shortsRead == AudioRecord.ERROR_BAD_VALUE || shortsRead == AudioRecord.ERROR_INVALID_OPERATION)
                    {
                        //Log.e("record()", "Error reading from microphone."); 
                        isRecording = false; 

                        break; 

                    }
                    //ShortBuffer myShortBuffer = myByteBuffer.asShortBuffer();
                    //myShortBuffer.put(audioBuffer);

                    //audio_chan.write(myByteBuffer);
                    //audio_chan.flush();
                    audio_fos.write(byteArray);
                    audio_fos.flush();
            }
            */
            audio_fos.write("finished".getBytes());
                    audio_fos.flush();


        }
        catch(IOException ioe)
        {
            //audio_fos.write(ioe.getMessage().getBytes());
            //        audio_fos.flush();
        }
        /*
        finally
        { 
            if (audioRecorder != null)
            { 
                    audioRecorder.stop(); 
                    audioRecorder.release(); 

            } 

        } */

    }

    public String record(File file_path) 
    {
        int bufferSizeInBytes, bufferSizeInShorts;
        int shortsRead;
        short audioBuffer[];
        byte byteArray[];//={0,1,2};
        try 
        {
            audio_file = file_path;
            audio_fos = new FileOutputStream(audio_file,true);

            audio_obj_strm = new ObjectOutputStream(audio_fos);
            
            audio_chan = audio_fos.getChannel();
            bufferSizeInBytes = AudioRecord.getMinBufferSize(RECORDER_SAMPLERATE,RECORDER_CHANNELS,RECORDER_AUDIO_ENCODING);
            byteArray=new byte[bufferSizeInBytes];
            myByteBuffer = ByteBuffer.allocate(bufferSizeInBytes);
            
            bufferSizeInShorts = (bufferSizeInBytes /2);
            audioRecorder = new AudioRecord(MediaRecorder.AudioSource.VOICE_RECOGNITION, RECORDER_SAMPLERATE,RECORDER_CHANNELS,RECORDER_AUDIO_ENCODING, bufferSizeInBytes);
            audioBuffer = new short[bufferSizeInShorts];
            
            audioRecorder.startRecording(); 
            isRecording = true;
            recorded = true;
            while (isRecording)
            { 
                    shortsRead = audioRecorder.read(audioBuffer, 0, bufferSizeInShorts);

                    if (shortsRead == AudioRecord.ERROR_BAD_VALUE || shortsRead == AudioRecord.ERROR_INVALID_OPERATION)
                    {
                        //Log.e("record()", "Error reading from microphone."); 
                        isRecording = false; 

                        break; 

                    }
                    ShortBuffer myShortBuffer = myByteBuffer.asShortBuffer();
                    myShortBuffer.put(audioBuffer);

                    audio_fos.write(myByteBuffer.array());
                    audio_fos.flush();

                    //audio_chan.write(myByteBuffer);
                    //audio_chan.flush();
                    //audio_fos.write(byteArray);
                    //audio_fos.flush();
                    //audio_obj_strm.writeObject(audioBuffer);
                    //audio_obj_strm.flush();
            }
            
           // audio_fos.write("finished".getBytes());
           //         audio_fos.flush();

            return "okay";
        }
        catch(IOException ioe)
        {
            //audio_fos.write(ioe.getMessage().getBytes());
            //        audio_fos.flush();
            return ioe.getMessage();
        }
        /*
        finally
        { 
            if (audioRecorder != null)
            { 
                    audioRecorder.stop(); 
                    audioRecorder.release(); 

            } 

        } */

    }

    public void stopRecord()
    {
       // if(isRecording==true)
       // {
            try
            {
                audioRecorder.stop(); 
                audioRecorder.release();

                audio_obj_strm.close();
                audio_chan.close();
                audio_fos.flush();
                audio_fos.close();
            }
            catch(IOException ioe)
            {

            }
         isRecording=false;
     //   }

    }
}