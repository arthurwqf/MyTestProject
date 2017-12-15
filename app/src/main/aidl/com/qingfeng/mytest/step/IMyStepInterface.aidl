// IMyStepInterface.aidl
package com.qingfeng.mytest.step;

// Declare any non-default types here with import statements
import com.qingfeng.mytest.step.ModelStep;

interface IMyStepInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    ModelStep getModelStep();

    int getStepCount();
    int getCalorie();

}
