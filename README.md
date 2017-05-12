# FlogAndroid

## A File logging library for Android.  

A common thing each Android developer do, after run the Android Project is looking at the LogCat. 
Yes we do create lot of logging statements for debugging. What if some other developer test the app on different device and crash happened.
In this scenario [FlogAndroid](https://github.com/vinayagasundar/FlogAndroid)  will help you..!!

# This project is under development

## It's easy to integrate with your App.
Add this to your app's build.gradle

```groovy
compile 'me.vinayagasundar.android:flog:0.0.1'
```

In your [Application](https://developer.android.com/reference/android/app/Application.html) class add the following lines to initialise the library 

```java
Flog.init(this);
Flog.setLoggable(BuildConfig.DEBUG);
```

Note : By Passing `BuildConfig.DEBUG` into `setLoggable` method it only works on Debug builds

You can use Flog like this

```java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Flog.i(TAG, "onCreate: "); 
    }
```

