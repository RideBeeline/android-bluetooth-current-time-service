# Android Bluetooth Current Time Service

An implementation of the Bluetooth Current Time Service for Android applications communicating with a Bluetooth peripheral.

https://beeline.co

Copyright 2017 Relish Technologies Ltd.

## Usage

When starting your app, for example in the `onCreate` method of your `Application`, start `CurrentTimeService`.

```java
CurrentTimeService.start(this);
```

When closing your app make sure to stop the service, for example in the `onTerminate` method of your `Application`.

```java
CurrentTimeService.stop();
```

