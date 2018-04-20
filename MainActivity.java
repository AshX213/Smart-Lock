package com.estimote.proximitycontent;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.estimote.coresdk.cloud.model.Color;
import com.estimote.cloud_plugin.common.EstimoteCloudCredentials; //
import com.estimote.internal_plugins_api.cloud.CloudCredentials;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.internal_plugins_api.cloud.proximity.ProximityAttachment;
import com.estimote.mustard.rx_goodness.rx_requirements_wizard.Requirement;
import com.estimote.mustard.rx_goodness.rx_requirements_wizard.RequirementsWizardFactory;
import com.estimote.proximity_sdk.proximity.ProximityObserver;
import com.estimote.proximity_sdk.proximity.ProximityObserverBuilder;
import com.estimote.proximity_sdk.proximity.ProximityZone;
import com.estimote.proximitycontent.estimote.EstimoteCloudBeaconDetails;
import com.estimote.proximitycontent.estimote.EstimoteCloudBeaconDetailsFactory;
import com.estimote.proximitycontent.estimote.ProximityContentManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

//
// Running into any issues? Drop us an email to: contact@estimote.com
//

public class MainActivity extends AppCompatActivity {

    private ProximityObserver proximityObserver;

    private static final String TAG = "MainActivity";

    private static final Map<Color, Integer> BACKGROUND_COLORS = new HashMap<>();

    static {
        BACKGROUND_COLORS.put(Color.ICY_MARSHMALLOW, android.graphics.Color.rgb(109, 170, 199));
        BACKGROUND_COLORS.put(Color.BLUEBERRY_PIE, android.graphics.Color.rgb(98, 84, 158));
        BACKGROUND_COLORS.put(Color.MINT_COCKTAIL, android.graphics.Color.rgb(155, 186, 160));
    }

    private static final int BACKGROUND_COLOR_NEUTRAL = android.graphics.Color.rgb(160, 169, 172);

    private RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CloudCredentials cloudCredentials = new EstimoteCloudCredentials("jgibson-linisource-com-s-p-ewj", "786957eebcd07069e1c8acf9a7c77470");
        queue = Volley.newRequestQueue(this);
        this.proximityObserver =
                new ProximityObserverBuilder(getApplicationContext(), cloudCredentials)
                        .withOnErrorAction(new Function1<Throwable, Unit>() {
                            @Override
                            public Unit invoke(Throwable throwable) {
                                Log.e("app", "proximity observer error: " + throwable);
                                return null;
                            }
                        })
                        .withBalancedPowerMode()
                        .build();

        ProximityZone zone1 = this.proximityObserver.zoneBuilder()
                .forAttachmentKeyAndValue("lock", "0")
                .inCustomRange(1.0)
                .withOnEnterAction(new Function1<ProximityAttachment, Unit>() {
                    @Override
                    public Unit invoke(ProximityAttachment attachment) {
                        Log.d("app", "Detected Lock 0 Beacon");
                        showNotification("Detected Beacon", "Lock 0");

                        final TextView mTextView = (TextView) findViewById(R.id.text);
// ...

// Instantiate the RequestQueue.
                       String url = "http://smart-lock-env.us-east-2.elasticbeanstalk.com/app_login";

                        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>()
                                {
                                    @Override
                                    public void onResponse(String response) {
                                        // response
                                        Log.d("Response", "(Succ)ess");
                                    }
                                },
                                new Response.ErrorListener()
                                {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        // error
                                        Log.d("Error.Response", "Error logging in");
                                    }
                                }
                        ) {
                            @Override
                            protected Map<String, String> getParams()
                            {
                                Map<String, String>  params = new HashMap<String, String>();
                                params.put("username","ahmed");
                                params.put("password","afro");
                                params.put("beaconid", "0bad1f66ca2237d6f4afe723e2758a3d");
                                params.put("open","true");

                                return params;
                            }
                        };
                        queue.add(postRequest);
// Request a string response from the provided URL.
                        // StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        //         new Response.Listener<String>() {
                        //             @Override
                        //             public void onResponse(String response) {
                        //                 // Display the first 500 characters of the response string.
                        //                 Log.d("app","Response is: "+ response.substring(0,500));
                        //             }
                        //         }, new Response.ErrorListener() {
                        //     @Override
                        //     public void onErrorResponse(VolleyError error) {
                        //         Log.d("app","That didn't work!");
                        //     }
                        // });

// Add the request to the RequestQueue.
                        queue.add(stringRequest);

                        return null;
                    }
                })
                .withOnExitAction(new Function1<ProximityAttachment, Unit>() {
                    @Override
                    public Unit invoke(ProximityAttachment attachment) {
                        Log.d("app", "Out of range of Lock 0 Beacon");
                        showNotification("Out of Range of Beacon", "Lock 0");

                        String url ="http://smart-lock-env.us-east-2.elasticbeanstalk.com/app_login";
                        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>()
                                {
                                    @Override
                                    public void onResponse(String response) {
                                        // response
                                        Log.d("Response", "(Succ)ess");
                                    }
                                },
                                new Response.ErrorListener()
                                {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        // error
                                        Log.d("Error.Response", "Error logging in");
                                    }
                                }
                        ) {
                            @Override
                            protected Map<String, String> getParams()
                            {
                                Map<String, String>  params = new HashMap<String, String>();
                                params.put("username","ahmed");
                                params.put("password","afro");
                                params.put("beaconid", "0bad1f66ca2237d6f4afe723e2758a3d");
                                params.put("open","false");

                                return params;
                            }
                        };
                        queue.add(postRequest);
                        return null;
                    }
                })
                .create();
        this.proximityObserver.addProximityZone(zone1);

        RequirementsWizardFactory
                .createEstimoteRequirementsWizard()
                .fulfillRequirements(this,
                        // onRequirementsFulfilled
                        new Function0<Unit>() {
                            @Override public Unit invoke() {
                                Log.d("app", "requirements fulfilled");
                                proximityObserver.start();
                                return null;
                            }
                        },
                        // onRequirementsMissing
                        new Function1<List<? extends Requirement>, Unit>() {
                            @Override public Unit invoke(List<? extends Requirement> requirements) {
                                Log.e("app", "requirements missing: " + requirements);
                                return null;
                            }
                        },
                        // onError
                        new Function1<Throwable, Unit>() {
                            @Override public Unit invoke(Throwable throwable) {
                                Log.e("app", "requirements error: " + throwable);
                                return null;
                            }
                        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        RequirementsWizardFactory
                .createEstimoteRequirementsWizard()
                .fulfillRequirements(this,
                        // onRequirementsFulfilled
                        new Function0<Unit>() {
                            @Override public Unit invoke() {
                                Log.d("app", "requirements fulfilled");
                                proximityObserver.start();
                                return null;
                            }
                        },
                        // onRequirementsMissing
                        new Function1<List<? extends Requirement>, Unit>() {
                            @Override public Unit invoke(List<? extends Requirement> requirements) {
                                Log.e("app", "requirements missing: " + requirements);
                                return null;
                            }
                        },
                        // onError
                        new Function1<Throwable, Unit>() {
                            @Override
                            public Unit invoke(Throwable throwable) {
                                Log.e("app", "requirements error: " + throwable);
                                return null;
                            }
                        }
                );
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void showNotification(String title, String message) {

        Intent notifyIntent = new Intent(this, MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[]{notifyIntent}, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }
}
