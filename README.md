# Android Bluetooth Current Time Service

[![Release](https://jitpack.io/v/RideBeeline/android-bluetooth-current-time-service.svg)]
(https://jitpack.io/#RideBeeline/android-bluetooth-current-time-service)

An implementation of the Bluetooth Current Time Service for Android applications communicating with a Bluetooth peripheral.

https://beeline.co

Copyright 2017 Relish Technologies Ltd.

## Installation

Add the following to your app's `build.gradle`:

```gradle
repositories {
    mavenCentral()
    maven { url "https://jitpack.io" }
}

dependencies {
    compile "com.github.RideBeeline:android-bluetooth-current-time-service:$version"
}
```

The latest version is shown at the top of this README.

## Usage

When starting your app, for example in the `onCreate` method of your `Application`, start `CurrentTimeService`. Note that Bluetooth should be enabled on the device to successfully start the service.

```java
boolean success = CurrentTimeService.start(this);
```

When closing your app make sure to stop the service, for example in the `onTerminate` method of your `Application`.

```java
CurrentTimeService.stop();
```

