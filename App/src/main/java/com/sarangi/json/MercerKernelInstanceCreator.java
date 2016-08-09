package com.sarangi.json;

import com.google.gson.InstanceCreator;
import smile.math.kernel.MercerKernel;
import smile.math.kernel.GaussianKernel;
import java.lang.reflect.Type;


public class MercerKernelInstanceCreator implements InstanceCreator<MercerKernel> {

        @Override
        public MercerKernel createInstance(Type type)
        {
                return new GaussianKernel(60.0d);
        }

}
