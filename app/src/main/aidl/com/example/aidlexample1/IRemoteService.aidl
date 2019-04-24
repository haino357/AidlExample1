// IRemoteService.aidl
package com.example.aidlexample1;

// Declare any non-default types here with import statements
// デフォルトの型でない場合、ここに import 文を宣言する

// 参考
// https://developer.android.com/guide/components/aidl?hl=ja
// https://developer.android.com/reference/android/os/Parcelable.html?hl=ja
// http://y-anz-m.blogspot.com/2010/03/androidparcelable.html
// https://www.atmarkit.co.jp/ait/articles/1204/20/news140.html
// https://www.atmarkit.co.jp/ait/articles/1206/15/news124.html

/** Example service interface */
/* サービスインタフェースの例 */
interface IRemoteService {
    /** Request the process ID of this service, to do evil things with it. */
    /* サービスのプロセスIDをリクエストする。*/
    int getPid();

    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     /*自動生成で利用できるパラメータの基本形をいくつか提示される*/
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);
    String sayYes(String message);
}
