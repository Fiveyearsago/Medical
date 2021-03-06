# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/songran/adt-bundle-mac/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
#-libraryjars libs/pgyer_sdk_2.4.2.jar
-dontwarn android.support.v4.**
#蒲公英SDK
-dontwarn com.pgyersdk.**
-keep class com.pgyersdk.** { *; }
-keepclassmembers class com.pgyersdk.** { *; }
#-ignorewarnings option
#ButterKnife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}
#百度地图
 -keep class com.baidu.** { *; }
#mob
-keep class cn.sharesdk.**{*;}
-keep class cn.smssdk.**{*;}
-keep class com.mob.**{*;}
-keep class **.R$* {*;}
 -keepattributes EnclosingMethod
 ################### region for xUtils
 -keepattributes Signature,*Annotation*
 -keep public class org.xutils.** {
     public protected *;
 }
 -keep public interface org.xutils.** {
     public protected *;
 }
 -keepclassmembers class * extends org.xutils.** {
     public protected *;
 }
 -keepclassmembers @org.xutils.db.annotation.* class * {*;}
 -keepclassmembers @org.xutils.http.annotation.* class * {*;}
 -keepclassmembers class * {
     @org.xutils.view.annotation.Event <methods>;
 }
# 3D 地图
     -keep   class com.amap.api.maps.**{*;}
     -keep   class com.autonavi.amap.mapcore.*{*;}
     -keep   class com.amap.api.trace.**{*;}

#     定位
     -keep class com.amap.api.location.**{*;}
     -keep class com.amap.api.fence.**{*;}
     -keep class com.autonavi.aps.amapapi.model.**{*;}

#     搜索
     -keep   class com.amap.api.services.**{*;}

#     2D地图
     -keep class com.amap.api.maps2d.**{*;}
     -keep class com.amap.api.mapcore2d.**{*;}

 #################### end region