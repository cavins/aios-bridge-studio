/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: E:\\gitCloneSpace\\aios-bridge-studio\\aios-bridge\\src\\main\\aidl\\com\\aispeech\\aios\\bridge\\IBridgeAidlInterface.aidl
 */
package com.aispeech.aios.bridge;
// Declare any non-default types here with import statements

public interface IBridgeAidlInterface extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.aispeech.aios.bridge.IBridgeAidlInterface
{
private static final java.lang.String DESCRIPTOR = "com.aispeech.aios.bridge.IBridgeAidlInterface";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.aispeech.aios.bridge.IBridgeAidlInterface interface,
 * generating a proxy if needed.
 */
public static com.aispeech.aios.bridge.IBridgeAidlInterface asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.aispeech.aios.bridge.IBridgeAidlInterface))) {
return ((com.aispeech.aios.bridge.IBridgeAidlInterface)iin);
}
return new com.aispeech.aios.bridge.IBridgeAidlInterface.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_doSomething:
{
data.enforceInterface(DESCRIPTOR);
this.doSomething();
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.aispeech.aios.bridge.IBridgeAidlInterface
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
/**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
@Override public void doSomething() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_doSomething, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_doSomething = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
/**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
public void doSomething() throws android.os.RemoteException;
}
