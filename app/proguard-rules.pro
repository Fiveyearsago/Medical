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

 -keepattributes EnclosingMethod