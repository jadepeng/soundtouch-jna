# SoundTouch JNA

Soundtouch is a C + + sound change library. 
The official Android library is provided through JNI, but not provided server side java libraries. 
This library use JNA library to provide java access to SoundTouch

SoundTouch 是一个c++变声库，官方通过JNI提供了android库，但是未提供服务端java版本的，本库提供通过JNA技术，让java可以方便调用SoundTouch。

## How to Usage

### 1. create instance

```java
    SoundTouch api = SoundTouch.INSTANCE;
    // create instance
    PointerByReference h = api.soundtouch_createInstance();
    
```

### 2. set arguments

    api.soundtouch_setChannels(h, wavFile.getNumChannels());
    api.soundtouch_setRateChange(h, this.rate);
    api.soundtouch_setPitchSemiTones(h, this.pitch);
    api.soundtouch_setTempoChange(h, this.tempo);
        
### 3. put audio data 

    api.soundtouch_putSamples_i16(h, samples, framesRead);

### 4. read changed audio data

    int receivedsSample = api.soundtouch_receiveSamples_i16(h, decodeSamples, framesRead);


## How to Build SoundTouch Libary

- dowload [soundtouch sources code](https://gitlab.com/soundtouch/soundtouch)
- build SoundTouch

    cmake . -DSOUNDTOUCH_DLL=ON
    make -j
    make install

- `libSoundTouchDLL.so` in project `resources` directory is builded in `docker alpine` 

