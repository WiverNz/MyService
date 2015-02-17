// IDataSourceServiceListener.aidl
package com.example.askibin.myservice;

// Declare any non-default types here with import statements

interface IDataSourceServiceListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    oneway void albumItemLoaded(in int a);
}
