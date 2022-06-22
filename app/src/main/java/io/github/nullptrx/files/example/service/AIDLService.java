/*
 * Copyright 2021 John "topjohnwu" Wu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.nullptrx.files.example.service;


import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;

import com.topjohnwu.superuser.ipc.RootService;

import java.util.UUID;

import io.github.nullptrx.files.example.ITestService;

// Demonstrate RootService using AIDL (daemon mode)
class AIDLService extends RootService {
  private static final String TAG = "AIDLService";


  static class TestIPC extends ITestService.Stub {

    @Override
    public int getPid() throws RemoteException {
      return Process.myPid();
    }
  }

  private final String uuid = UUID.randomUUID().toString();

  @Override
  public void onCreate() {
    Log.d(TAG, "AIDLService: onCreate, " + uuid);
  }

  @Override
  public void onRebind(@NonNull Intent intent) {
    // This callback will be called when we are reusing a previously started root process
    Log.d(TAG, "AIDLService: onRebind, daemon process reused");
  }

  @Override
  public IBinder onBind(@NonNull Intent intent) {
    Log.d(TAG, "AIDLService: onBind");
    return new TestIPC();
  }

  @Override
  public boolean onUnbind(@NonNull Intent intent) {
    Log.d(TAG, "AIDLService: onUnbind, client process unbound");
    // Return true here so onRebind will be called
    return true;
  }

  @Override
  public void onDestroy() {
    Log.d(TAG, "AIDLService: onDestroy");
  }
}
