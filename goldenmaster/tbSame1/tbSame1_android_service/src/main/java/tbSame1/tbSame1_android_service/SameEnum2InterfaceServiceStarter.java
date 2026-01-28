package tbSame1.tbSame1_android_service;

import android.util.Log;
import android.content.Context;
import android.content.Intent;

import tbSame1.tbSame1_api.ISameEnum2InterfaceEventListener;
import tbSame1.tbSame1_api.ISameEnum2Interface;
import tbSame1.tbSame1_android_service.SameEnum2InterfaceServiceAdapter;
import tbSame1.tbSame1_android_service.SameEnum2InterfaceServiceProvider;
import tbSame1.tbSame1_android_service.SameEnum2InterfaceBaseServiceLifecycleController;


// This class provides concrete implementation, for SameEnum2InterfaceBaseServiceLifecycleController,
// which describes the lifetime of an android server and controlls the provided to the server backend lifetime.
// This class sets type of backend provided to the service Implemented backend service from package tbSame1.tbSame1_impl;.
// Please see SameEnum2InterfaceBaseServiceLifecycleController for the details.
public class SameEnum2InterfaceServiceStarter
{
    private static final String TAG = "SameEnum2InterfaceStarter";

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

    private static final SameEnum2InterfaceBaseServiceLifecycleController IMPL =
    new SameEnum2InterfaceBaseServiceLifecycleController()
    {
        @Override
        protected String getTag()
        {
            return TAG;
        }

        @Override
        protected ISameEnum2InterfaceServiceProvider getProviderInstance()
        {
            return SameEnum2InterfaceServiceProvider.get();
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

    public static ISameEnum2Interface start(Context ctx)
    {
        Log.i(TAG, "SameEnum2InterfaceServiceStarter::start called");
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
