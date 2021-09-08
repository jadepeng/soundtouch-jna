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
    @Parameter(names = {"-i"}, description = "input", required = true)
    protected String input = "";

    @Parameter(names = {"-o"}, description = "output")
    protected String output = "output.wav";

    static void log(String message) {
        System.out.println(new Date().toString() + " " + message);
    }

    public static void main(String[] args) throws IOException, WavFileException {
        new DemoApplication().runDemo(args);
    }

    private void runDemo(String[] args) throws IOException, WavFileException {

        long start = System.currentTimeMillis();

        JCommander.newBuilder()
                  .addObject(this)
                  .allowParameterOverwriting(true)
                  .acceptUnknownOptions(true)
                  .build()
                  .parse(args);

        SoundTouch api = new SoundTouch();

        WavFile wavFile = WavFile.openWavFile(new File(this.input));

        WavFile out = WavFile.newWavFile(new File(this.output),
                                         wavFile.getNumChannels(),
                                         wavFile.getNumFrames(),
                                         wavFile.getValidBits(),
                                         wavFile.getSampleRate());

        api.setSampleRate((int) wavFile.getSampleRate());
        api.setChannels(wavFile.getNumChannels());
        api.setRateChange(this.rate);
        api.setPitchSemiTones(this.pitch);
        api.setTempoChange(this.tempo);

        int numFramesToRead = 100;
        short[] samples = new short[numFramesToRead * numFramesToRead];
        short[] decodeSamples = new short[numFramesToRead * numFramesToRead];
        int framesRead;
        do {
            // Read frames into buffer
            framesRead = wavFile.readFrames(samples, numFramesToRead);
            log("read " + framesRead + " frames");
            if (framesRead > 0) {
                api.putSamplesI16(samples, framesRead);
                log("put " + framesRead + " frames");
                int receivedsSample = api.receiveSamplesI16(decodeSamples, framesRead);
                log("receiveSamples_i16 " + receivedsSample + " frames");
                out.writeFrames(decodeSamples, receivedsSample);
            }
        }
        while (framesRead != 0);

        out.close();
        wavFile.close();

        api.dispose();

        log("转换完成, 耗时：" + (System.currentTimeMillis() - start) / 1000 + "s");

    }

}
