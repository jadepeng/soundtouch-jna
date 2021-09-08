package com.github.jadepeng.soundtouch;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import com.sun.jna.ptr.PointerByReference;


@SpringBootApplication
public class DemoApplication {

    @Parameter(names = {"-pitch"}, description = "pitch")
    protected float pitch = 0;
    @Parameter(names = {"-rate"}, description = "rate")
    protected float rate = 0;
    @Parameter(names = {"-tempo"}, description = "tempo")
    protected float tempo = 0;
    @Parameter(names = {"-i"}, description = "input")
    protected String input = "00000008.wav";

    @Parameter(names = {"-o"}, description = "output")
    protected String output = "output.wav";

    static void log(String message) {
        System.out.println(new Date().toString() + " " + message);
    }

    public static void main(String[] args) throws IOException, WavFileException {
        new DemoApplication().runDemo(args);
    }

    private void runDemo(String[] args) throws IOException, WavFileException {

        Long start = System.currentTimeMillis();

        JCommander.newBuilder()
                  .addObject(this)
                  .allowParameterOverwriting(true)
                  .acceptUnknownOptions(true)
                  .build()
                  .parse(args);

        SoundTouch api = SoundTouch.INSTANCE;
        PointerByReference h = api.soundtouch_createInstance();
        System.out.println(h.toString());


        WavFile wavFile = WavFile.openWavFile(new File(this.input));

        WavFile out = WavFile.newWavFile(new File(this.output),
                                         wavFile.getNumChannels(),
                                         wavFile.getNumFrames(),
                                         wavFile.getValidBits(),
                                         wavFile.getSampleRate());

        // 设置参数
        api.soundtouch_setSampleRate(h, (int) wavFile.getSampleRate());
        log("soundtouch_setChannels" + wavFile.getNumChannels());

        api.soundtouch_setChannels(h, wavFile.getNumChannels());
        api.soundtouch_setRateChange(h, this.rate);
        api.soundtouch_setPitchSemiTones(h, this.pitch);
        api.soundtouch_setTempoChange(h, this.tempo);
        int numFramesToRead = 100;
        short[] samples = new short[numFramesToRead * numFramesToRead];
        short[] decodeSamples = new short[numFramesToRead * numFramesToRead];
        int framesRead;
        do {
            // Read frames into buffer
            framesRead = wavFile.readFrames(samples, numFramesToRead);
            log("read " + framesRead + " frames");
            if (framesRead > 0) {
                api.soundtouch_putSamples_i16(h, samples, framesRead);
                log("put " + framesRead + " frames");
                int receivedsSample = api.soundtouch_receiveSamples_i16(h, decodeSamples, framesRead);
                log("soundtouch_receiveSamples_i16 " + receivedsSample + " frames");
                out.writeFrames(decodeSamples, receivedsSample);

            }
        }
        while (framesRead != 0);

        out.close();
        wavFile.close();

        api.soundtouch_destroyInstance(h);

        log("转换完成, 耗时：" + (System.currentTimeMillis() - start) / 1000 + "s");

        //System.out.println(api.soundtouch_getVersionId());
    }

}
