package com.qingfeng.mytest.step;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class StepService extends Service {

    private ModelStep modelStep;

    private final IMyStepInterface.Stub myStep = new IMyStepInterface.Stub() {
        @Override
        public ModelStep getModelStep() throws RemoteException {
            return modelStep;
        }

        @Override
        public int getStepCount() throws RemoteException {
            return modelStep.getStepCount();
        }

        @Override
        public int getCalorie() throws RemoteException {
            return modelStep.getCalorie();
        }
    };

    public StepService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        modelStep = new ModelStep();
        modelStep.setCalorie(0);
        modelStep.setStepCount(0);
    }

    /**
     * 开始计步
     */
    private void startStep() {
        modelStep.setStepCount(109);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        startStep();
        return myStep;
    }
}
