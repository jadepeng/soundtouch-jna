# SoundTouch JNA

Soundtouch is a C + + sound change library. 
The official Android library is provided through JNI, but not provided server side java libraries. 
This library use JNA library to provide java access to SoundTouch

SoundTouch 是一个c++变声库，官方通过JNI提供了android库，但是未提供服务端java版本的，本库提供通过JNA技术，让java可以方便调用SoundTouch。

## How to Usage

### 1. create instance

```java
    SoundTouch api = new SoundTouch();
```

### 2. set arguments

    api.setChannels(wavFile.getNumChannels());
    api.setRateChange(this.rate);
    api.setPitchSemiTones(this.pitch);
    api.setTempoChange(this.tempo);
        
### 3. put audio data 

    api.putSamplesI16(samples, framesRead);

### 4. read changed audio data

    int receivedsSample = api.receiveSamplesI16(decodeSamples, framesRead);

### 5. release instance

    api.dispose();

## How to Build SoundTouch Libary

- dowload [soundtouch sources code](https://gitlab.com/soundtouch/soundtouch)
- build SoundTouch

    cmake . -DSOUNDTOUCH_DLL=ON
    make -j
    make install

- `libSoundTouchDLL.so` in project `resources` directory is builded in `docker alpine` 

