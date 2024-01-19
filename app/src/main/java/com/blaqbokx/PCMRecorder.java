package com.blaqbokx;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.blaqbokx.Opuser;
//import com.theeasiestway.opus.*;
//import com.naman14.androidlame.AndroidLame;

public class PCMRecorder {

    private static final String TAG = "PCMRecorder";
    private static final int SAMPLE_RATE = 44100; // Hz
    private static final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
    private static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;
    private static final int BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT);

    private AudioRecord audioRecord;
    private boolean isRecording = false;
    private File outputFile, opus_file;

    /*
    Constants.SampleRate opus_sample_rate = Constants.SampleRate.Companion._48000();
    Constants.Channels opus_channel = Constants.Channels.Companion.mono();
    Constants.Application opus_application = Constants.Application.Companion.audio();
    Constants.FrameSize opus_frame_size = Constants.FrameSize.Companion._120();
    Constants.Complexity opus_complexity =Constants.Complexity.Companion.instance(10);
    Constants.Bitrate opus_bitrate = Constants.Bitrate.Companion.max();
    */
    //AndroidLame androidLame ;

    
    //Opuser opuser;
    public PCMRecorder() {
        //opuser = new Opuser();
        outputFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/pcm_audio.pcm");
        opus_file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/pcm_audio_recording.xopus");
    }

    public void startRecording() {
        
        if (isRecording) {
           // Log.e(TAG, "Recording is already in progress.");
            return;
        }
        //androidLame = new AndroidLame();
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT, BUFFER_SIZE);

        if (audioRecord.getState() != AudioRecord.STATE_INITIALIZED) {
            Log.e(TAG, "AudioRecord initialization failed.");
            return;
        }

        audioRecord.startRecording();
        isRecording = true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                writeAudioDataToFile();
            }
        }).start();
    }

    public void stopRecording() {
        //opuser.stop();
        if (!isRecording) {
            //Log.e(TAG, "Recording is not in progress.");
            return;
        }

        isRecording = false;

        if (audioRecord.getState() == AudioRecord.STATE_INITIALIZED) {
            audioRecord.stop();
            audioRecord.release();
        }
    }

    private void writeAudioDataToFile() {
        byte[] buffer = new byte[BUFFER_SIZE];
        short[] sbuffer = new short[BUFFER_SIZE/2];
        FileOutputStream outputStream = null;
        FileOutputStream opusStream = null;
        /*Opus codec = new Opus();
        codec.encoderInit(opus_sample_rate,opus_channel,opus_application);
        codec.encoderSetBitrate(opus_bitrate);
        codec.encoderSetComplexity(opus_complexity);
        */


        //Opus codec = new Opus();
       // codec.decoderInit(opus_sample_rate,opus_channel);
        //codec.decoderSetBitrate(opus_bitrate);
        //codec.decoderSetComplexity(opus_complexity);


        try {
            outputStream = new FileOutputStream(outputFile);
            opusStream = new FileOutputStream(opus_file);
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            return;
        }

        while (isRecording) {
            //int readSize = audioRecord.read(buffer, 0, BUFFER_SIZE);
            int readSize = audioRecord.read(buffer, 0, BUFFER_SIZE/2);
           // byte mp3buf[] = new byte[8192];
           // androidLame.encode(buffer, buffer,SAMPLE_RATE, mp3buf);
            if (readSize != AudioRecord.ERROR_INVALID_OPERATION) {
                try {
                   // byte opus_buffer[] = codec.encode(buffer,opus_frame_size);
                    //byte ropus_buffer[] = codec.decode(opus_buffer,opus_frame_size);
                   // opusStream.write(opus_buffer);
                    //buffer = opuser.encode(sbuffer,0,readSize);
                    outputStream.write(buffer, 0, readSize);
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }
        }

      //  codec.encoderRelease();
      //  codec.decoderRelease();
        try {
            outputStream.close();
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }
}
