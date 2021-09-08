package com.github.jadepeng.soundtouch;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.PointerByReference;

/**
 * 使用JNA封装的SoundTouch库
 * @author jqpeng
 */
public interface SoundTouch extends Library {

    SoundTouch INSTANCE = Native.load("libSoundTouchDLL.so", SoundTouch.class);

    int soundtouch_getVersionId();

    PointerByReference soundtouch_createInstance();

    void soundtouch_destroyInstance(PointerByReference h);

    void soundtouch_setRate(PointerByReference h, float newRate);

    void soundtouch_setTempo(PointerByReference h, float newTempo);

    void soundtouch_setRateChange(PointerByReference h, float newRate);

    void soundtouch_setTempoChange(PointerByReference h, float newTempo);

    void soundtouch_setPitch(PointerByReference h, float newPitch);

    void soundtouch_setPitchOctaves(PointerByReference h, float newPitch);

    void soundtouch_setPitchSemiTones(PointerByReference h, float newPitch);

    void soundtouch_setChannels(PointerByReference h, int numChannels);

    void soundtouch_setSampleRate(PointerByReference h, int srate);

    void soundtouch_flush(PointerByReference h);

    void soundtouch_putSamples(PointerByReference h, float[] samples, int numSamples);

    void  soundtouch_putSamples_i16(PointerByReference h, short[] samples, int numSamples);

    void soundtouch_clear(PointerByReference h);

    int soundtouch_setSetting(PointerByReference h, int settingId, int value);

    int soundtouch_getSetting(PointerByReference h, int settingId);

    int soundtouch_numUnprocessedSamples(PointerByReference h);

    int soundtouch_receiveSamples(PointerByReference h, float[] outBuffer, int maxSamples);

    int soundtouch_receiveSamples_i16(PointerByReference h, short[] outBuffer, int maxSamples);

    int soundtouch_numSamples(PointerByReference h);

    int soundtouch_isEmpty(PointerByReference h);
}
