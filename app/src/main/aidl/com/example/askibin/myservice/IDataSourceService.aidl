// IDataSourceService.aidl
package com.example.askibin.myservice;
import com.example.askibin.myservice.IDataSourceServiceListener;
// Declare any non-default types here with import statements

interface IDataSourceService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    oneway void loadAlbums(in IDataSourceServiceListener listener); // Asyn
    //void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString);
    int add(int num1, int num2);
}
