package tbSimple.tbSimple_android_service;

import android.util.Log;
import android.content.Context;
import android.content.Intent;

import tbSimple.tbSimple_api.ISimpleInterfaceEventListener;
import tbSimple.tbSimple_api.ISimpleInterface;
import tbSimple.tbSimple_android_service.SimpleInterfaceServiceAdapter;
import tbSimple.tbSimple_android_service.SimpleInterfaceServiceProvider;
import tbSimple.tbSimple_android_service.SimpleInterfaceBaseServiceLifecycleController;


// This class provides concrete implementation, for SimpleInterfaceBaseServiceLifecycleController,
// which describes the lifetime of an android server and controlls the provided to the server backend lifetime.
// This class sets type of backend provided to the service Implemented backend service from package tbSimple.tbSimple_impl;.
// Please see SimpleInterfaceBaseServiceLifecycleController for the details.
public class SimpleInterfaceServiceStarter
{
    private static final String TAG = "SimpleInterfaceStarter";

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

    private static final SimpleInterfaceBaseServiceLifecycleController IMPL =
    new SimpleInterfaceBaseServiceLifecycleController()
    {
        @Override
        protected String getTag()
        {
            return TAG;
        }

        @Override
        protected ISimpleInterfaceServiceProvider getProviderInstance()
        {
            return SimpleInterfaceServiceProvider.get();
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

    public static ISimpleInterface start(Context ctx)
    {
        Log.i(TAG, "SimpleInterfaceServiceStarter::start called");
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
