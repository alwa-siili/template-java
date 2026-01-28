package counter.counter_android_service;

import android.util.Log;
import android.content.Context;
import android.content.Intent;

import counter.counter_api.ICounterEventListener;
import counter.counter_api.ICounter;
import counter.counter_android_service.CounterServiceAdapter;
import counter.counter_android_service.CounterServiceProvider;
import counter.counter_android_service.CounterBaseServiceLifecycleController;


// This class provides concrete implementation, for CounterBaseServiceLifecycleController,
// which describes the lifetime of an android server and controlls the provided to the server backend lifetime.
// This class sets type of backend provided to the service Implemented backend service from package counter.counter_impl;.
// Please see CounterBaseServiceLifecycleController for the details.
public class CounterServiceStarter
{
    private static final String TAG = "CounterStarter";

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

    private static final CounterBaseServiceLifecycleController IMPL =
    new CounterBaseServiceLifecycleController()
    {
        @Override
        protected String getTag()
        {
            return TAG;
        }

        @Override
        protected ICounterServiceProvider getProviderInstance()
        {
            return CounterServiceProvider.get();
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

    public static ICounter start(Context ctx)
    {
        Log.i(TAG, "CounterServiceStarter::start called");
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
