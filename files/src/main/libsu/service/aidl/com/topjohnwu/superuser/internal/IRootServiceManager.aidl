// IRootServiceManager.aidl
package com.topjohnwu.superuser.internal;

// Declare any non-default types here with import statements

interface IRootServiceManager {
    oneway void broadcast(int uid, String action);
    oneway void stop(in ComponentName name, int uid, String action);
    void connect(in IBinder binder);
    IBinder bind(in Intent intent);
    oneway void unbind(in ComponentName name);
}
