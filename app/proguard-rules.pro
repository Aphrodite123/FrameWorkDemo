# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
#dontwarn:不提示警告，keep:保持原样不混淆

-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

-dontwarn
-keepattributes *Annotation*

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
    public void setPoint(android.graphics.PointF);
    public void UnityCallBack(java.lang.String);
}

-keepclassmembers class * extends android.view.View {
    public void setCurrentSize(int);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
   *;
}

-keep class com.bumptech.glide.integration.volley.VolleyGlideModule
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule
-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment
-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-dontwarn com.gistandard.androidbase.**
-keep class com.gistandard.androidbase.** { *; }
-dontwarn com.jiqid.bspatch.**
-keep class com.jiqid.bspatch.** { *; }
-dontwarn org.apache.commons.io.**
-keep class org.apache.commons.io.** { *; }
-dontwarn com.bigkoo.convenientbanner.**
-keep class com.bigkoo.convenientbanner.** { *; }
-dontwarn org.greenrobot.eventbus.**
-keep class org.greenrobot.eventbus.** { *; }
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *; }
-dontwarn com.google.gson.**
-keep class com.google.gson.** { *; }
-dontwarn javax.jmdns.**
-keep class javax.jmdns.** { *; }
-dontwarn com.inuker.bluetooth.library.**
-keep class com.inuker.bluetooth.library.** { *; }
-dontwarn com.miot.bluetooth.**
-keep class com.miot.bluetooth.** { *; }
-dontwarn miot.bluetooth.security.**
-keep class miot.bluetooth.security.** { *; }
-dontwarn com.miot.**
-keep class com.miot.** { *; }
-dontwarn android.support.multidex.**
-keep class android.support.multidex.** { *; }
-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.alibaba.sdk.android.**
-keep class com.alibaba.sdk.android.** { *; }
-dontwarn org.reactivestreams.**
-keep class org.reactivestreams.** { *; }
-dontwarn io.reactivex.android.**
-keep class io.reactivex.android.** { *; }
-dontwarn io.reactivex.**
-keep class io.reactivex.** { *; }
-dontwarn com.android.volley.**
-keep class com.android.volley.** { *; }
-dontwarn com.bumptech.glide.integration.volley.**
-keep class com.bumptech.glide.integration.volley.** { *; }
-dontwarn com.everyplay.**
-keep class com.everyplay.** { *; }
-dontwarn com.vuforia.**
-keep class com.vuforia.** { *; }
-dontwarn okio.**
-keep class okio.** { *; }
-keep interface okio.** { *; }
-dontwarn com.xiaomi.**
-keep class com.xiaomi.** { *; }
-dontwarn com.tencent.wxop.**
-keep class com.tencent.wxop.** { *; }
-dontwarn com.tencent.mm.opensdk.**
-keep class com.tencent.mm.opensdk.** { *; }
-dontwarn com.tencent.mm.sdk.**
-keep class com.tencent.mm.sdk.** { *; }
-dontwarn com.unity3d.player.**
-keep class com.unity3d.player.** { *; }
-dontwarn bitter.jnibridge.**
-keep class bitter.jnibridge.** { *; }
-dontwarn org.fmod.**
-keep class org.fmod.** { *; }
-dontwarn org.apache.**
-keep class org.apache.** { *; }
-keep interface org.apache.** { *; }

-assumenosideeffects class android.util.Log {
public static boolean isLoggable(java.lang.String, int);
public static int v(...);
public static int i(...);
public static int w(...);
public static int d(...);
public static int e(...);
}

-keepclassmembers class ** {
    public void onEvent*(**);
}

-keep class com.aphrodite.demo.model.network.** { *; }
-keep class com.aphrodite.demo.model.event.** { *; }
-keep class com.aphrodite.demo.model.bean.**{*;}

# OkHttp
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn com.squareup.okhttp.**
-keep class okio.**{*;}
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }

-dontwarn java.nio.file.*
-dontwarn javax.annotation.**
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
**[] $VALUES;
public *;
}

# okio
-keep class sun.misc.Unsafe { *; }
-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn okio.**

## ---------Retrofit混淆方法---------------
-dontwarn javax.annotation.**
-dontwarn javax.inject.**
# OkHttp3
-dontwarn okhttp3.logging.**
-keep class okhttp3.internal.**{*;}
-dontwarn okio.**
# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
# RxJava RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

# Gson
-keep class com.google.gson.stream.** { *; }
-keepattributes EnclosingMethod