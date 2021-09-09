package com.jadepeng.soundtouch;

import com.sun.jna.ptr.PointerByReference;

/**
 * SoundTouch API
 * @author jqpeng
 */
public class SoundTouch {

    static final SoundTouchNative NATIVE_API = SoundTouchNative.INSTANCE;

    private PointerByReference handle;

    public SoundTouch() {
        handle = NATIVE_API.soundtouch_createInstance();
    }

    public int getVersionId() {
        return NATIVE_API.soundtouch_getVersionId();
    }

    public void dispose() {
        NATIVE_API.soundtouch_destroyInstance(this.handle);
    }

    public void setRate(float newRate) {
        NATIVE_API.soundtouch_setRate(this.handle, newRate);
    }

    public void setTempo(float newTempo) {
        NATIVE_API.soundtouch_setTempo(this.handle, newTempo);
    }

    public void setRateChange(float newRate) {
        NATIVE_API.soundtouch_setRateChange(this.handle, newRate);
    }

    public void setTempoChange(float newTempo) {
        NATIVE_API.soundtouch_setTempoChange(this.handle, newTempo);
    }

    public void setPitch(float newPitch) {
        NATIVE_API.soundtouch_setPitch(this.handle, newPitch);
    }

    public void setPitchOctaves(float newPitch) {
        NATIVE_API.soundtouch_setPitchOctaves(this.handle, newPitch);
    }

    public void setPitchSemiTones(float newPitch) {
        NATIVE_API.soundtouch_setPitchSemiTones(this.handle, newPitch);
    }

    public void setChannels(int numChannels) {
        NATIVE_API.soundtouch_setChannels(this.handle, numChannels);
    }

    public void setSampleRate(int srate) {
        NATIVE_API.soundtouch_setSampleRate(this.handle, srate);
    }

    public void flush(PointerByReference h) {
        NATIVE_API.soundtouch_flush(this.handle);
    }

    public void putSamples(float[] samples, int numSamples) {
        NATIVE_API.soundtouch_putSamples(this.handle, samples, numSamples);
    }

    public void putSamplesI16(short[] samples, int numSamples) {
        NATIVE_API.soundtouch_putSamples_i16(this.handle, samples, numSamples);
    }

    public void clear() {
        NATIVE_API.soundtouch_clear(this.handle);
    }

    int setSetting(int settingId, int value) {
        return NATIVE_API.soundtouch_setSetting(this.handle, settingId, value);
    }

    public int getSetting(int settingId) {
        return NATIVE_API.soundtouch_getSetting(this.handle, settingId);
    }

    public int numUnprocessedSamples() {
        return NATIVE_API.soundtouch_numUnprocessedSamples(this.handle);
    }

    public int receiveSamples(float[] outBuffer, int maxSamples) {
        return NATIVE_API.soundtouch_receiveSamples(this.handle, outBuffer, maxSamples);
    }

    public int receiveSamplesI16(short[] outBuffer, int maxSamples) {
        return NATIVE_API.soundtouch_receiveSamples_i16(this.handle, outBuffer, maxSamples);
    }

    public int numSamples() {
        return NATIVE_API.soundtouch_numSamples(this.handle);
    }

    public int isEmpty() {
        return NATIVE_API.soundtouch_isEmpty(this.handle);

    }
}
