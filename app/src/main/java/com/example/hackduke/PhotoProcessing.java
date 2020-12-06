package com.example.hackduke;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.media.Image;
import android.os.Build;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Surface;

import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class PhotoProcessing{
FirebaseVisionImage image;
List<FirebaseVisionImageLabel> currentLabels = new ArrayList<FirebaseVisionImageLabel>();

FirebaseVisionImageLabeler labeler = FirebaseVision.getInstance().getCloudImageLabeler();

    public PhotoProcessing(){

    }

    //get a firebase image
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FirebaseVisionImage StoreImage(String cameraId, Activity activity, Context context, Image inputImage) {
        int rotation = 0;
        try {
            rotation = getRotationCompensation(cameraId,activity,context);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        FirebaseVisionImage thisImage = FirebaseVisionImage.fromMediaImage(inputImage, rotation);
        image = thisImage;
        return thisImage;
    }

    public FirebaseVisionImage imageFromBitmap(Bitmap bit){
        FirebaseVisionImage thisImage = FirebaseVisionImage.fromBitmap(bit);
        image = thisImage;
        return thisImage;
    }



    public List<String> ProcessImage() {
        List<String> myTexts = new ArrayList<>();
        labeler.processImage(image).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
            public void onSuccess(List<FirebaseVisionImageLabel> labels) {
                // Task completed successfully
                //
                currentLabels.clear();
                myTexts.clear();
                for (FirebaseVisionImageLabel item: labels) {
                    currentLabels.add(item);
                }
                for(FirebaseVisionImageLabel label: currentLabels) {
                    String text = label.getText();
                    myTexts.add(text);
                    String entityId = label.getEntityId();
                    float confidence = label.getConfidence();
                    Log.d("second", text);
                    Log.d("third", entityId);
                    Log.d("fourth", String.valueOf(confidence));
                }
            }
        })
                .addOnFailureListener (new OnFailureListener() {
            public void onFailure(Exception e) {
                Log.d("myTag", "no succ");
            }
        });
        Log.d("ALIVE", myTexts.get(0));
        return myTexts;

    }







    //googles stuff IDK how it works
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    /**
     * Get the angle by which an image must be rotated given the device's current
     * orientation.
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private int getRotationCompensation(String cameraId, Activity activity, Context context)
            throws CameraAccessException {
        // Get the device's current rotation relative to its "native" orientation.
        // Then, from the ORIENTATIONS table, look up the angle the image must be
        // rotated to compensate for the device's rotation.
        int deviceRotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int rotationCompensation = ORIENTATIONS.get(deviceRotation);

        // On most devices, the sensor orientation is 90 degrees, but for some
        // devices it is 270 degrees. For devices with a sensor orientation of
        // 270, rotate the image an additional 180 ((270 + 270) % 360) degrees.


        CameraManager cameraManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            cameraManager = (CameraManager) context.getSystemService(context.CAMERA_SERVICE);
        }
        int sensorOrientation = cameraManager
                .getCameraCharacteristics(cameraId)
                .get(CameraCharacteristics.SENSOR_ORIENTATION);
        rotationCompensation = (rotationCompensation + sensorOrientation + 270) % 360;

        // Return the corresponding FirebaseVisionImageMetadata rotation value.
        int result;
        switch (rotationCompensation) {
            case 0:
                result = FirebaseVisionImageMetadata.ROTATION_0;
                break;
            case 90:
                result = FirebaseVisionImageMetadata.ROTATION_90;
                break;
            case 180:
                result = FirebaseVisionImageMetadata.ROTATION_180;
                break;
            case 270:
                result = FirebaseVisionImageMetadata.ROTATION_270;
                break;
            default:
                result = FirebaseVisionImageMetadata.ROTATION_0;
                //Log.e(TAG, "Bad rotation value: " + rotationCompensation);
        }
        return result;
    }
}


