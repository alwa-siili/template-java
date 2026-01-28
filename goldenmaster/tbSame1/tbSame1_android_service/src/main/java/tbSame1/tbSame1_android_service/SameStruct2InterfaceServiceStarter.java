package tbSame1.tbSame1_android_service;

import android.util.Log;
import android.content.Context;
import android.content.Intent;

import tbSame1.tbSame1_api.ISameStruct2InterfaceEventListener;
import tbSame1.tbSame1_api.ISameStruct2Interface;
import tbSame1.tbSame1_android_service.SameStruct2InterfaceServiceAdapter;
import tbSame1.tbSame1_android_service.SameStruct2InterfaceServiceProvider;
import tbSame1.tbSame1_android_service.SameStruct2InterfaceBaseServiceLifecycleController;


// This class provides concrete implementation, for SameStruct2InterfaceBaseServiceLifecycleController,
// which describes the lifetime of an android server and controlls the provided to the server backend lifetime.
// This class sets type of backend provided to the service Implemented backend service from package tbSame1.tbSame1_impl;.
// Please see SameStruct2InterfaceBaseServiceLifecycleController for the details.
public class SameStruct2InterfaceServiceStarter
{
    private static final String TAG = "SameStruct2InterfaceStarter";

    public interface ServiceLifecycleListener
    {
        // Called when service connects successfully.
        void onServiceConnected();

        // Called when service is killed by Android or crashed, not when stopped.
        void onServiceDied();
    }

    private static ServiceLifecycleListener sListener = null;

    public static void setServiceLifecycleListener(ServiceLifecycleListener listener)
    {
        sListener = listener;
    }

    private static final SameStruct2InterfaceBaseServiceLifecycleController IMPL =
    new SameStruct2InterfaceBaseServiceLifecycleController()
    {
        @Override
        protected String getTag()
        {
            return TAG;
        }

        @Override
        protected ISameStruct2InterfaceServiceProvider getProviderInstance()
        {
            return SameStruct2InterfaceServiceProvider.get();
        }

        @Override
        protected void onAndroidServiceConnectionStatusChanged(boolean status)
        {
            if (sListener != null)
            {
                if (status)
                {
                    sListener.onServiceConnected();
                }
                else
                {
                    sListener.onServiceDied();
                }
            }
        }
    };

    public static ISameStruct2Interface start(Context ctx)
    {
        Log.i(TAG, "SameStruct2InterfaceServiceStarter::start called");
        if (ctx == null)
        {
            Log.i(TAG, "Context is null");
        }
        else
        {
            Log.i(TAG, "Context (" + ctx.getClass().getName() + ") is: " + ctx.toString());
        }
        if (IMPL == null)
        {
            Log.i(TAG, "IMPL is null");
        }
        else
        {
            Log.i(TAG, "IMPL (" + IMPL.getClass().getName() + ") is: " + IMPL.toString());
        }
        return IMPL.start(ctx);
    }

    public static void stop(Context ctx)
    {
        IMPL.stop(ctx);
    }
}
