# Scientific-Calculator-Android
Scientific Calculator Android

## Configuration
This Calculator depends on [Rhino Library](https://github.com/APISENSE/rhino-android)

The `ScriptEngine` in rhino feature relies on reflection to instanciate the engines. This will cause trouble while shrinking your code using Proguard or R8.
use following Proguard in production

## Proguard

```
-keep class android.support.v7.widget.** { *; }

-keep public class io.github.aveuiller.** { public *; }

-keep class javax.script.** { *; }
-keep class com.sun.script.javascript.** { *; }
-keep class org.mozilla.javascript.** { *; }

-printconfiguration /tmp/rhinosampleapp-r8-config.txt

```

# Screens

![alt text](https://github.com/orbitalsonic/Scientific-Calculator-Android/blob/master/Screenshots/Screenshot.png?raw=true)
