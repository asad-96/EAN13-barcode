package com.visionsmarts.plugins.capacitor;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;

import com.getcapacitor.*;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;

import java.util.ArrayList;

import androidx.annotation.RequiresApi;
import kotlin.Pair;

import com.getcapacitor.annotation.PermissionCallback;
import com.visionsmarts.plugins.ScannerActivity;

/**
 * This calls out to the VSBarcodeReader barcode reader and returns the result.
 *
 */
@CapacitorPlugin(
        requestCodes={VSBarcodeReader.REQUEST_CODE_VS}, // register request code(s) for intent results
        permissions={@Permission(
                alias = "camera",
                strings = { Manifest.permission.CAMERA }
        )}
)
public class VSBarcodeReader extends Plugin {
    public static final int REQUEST_CODE_VS = 1440;

    private static final String SCAN = "scan";
    private static final String CALLBACKID = "callbackId";
    private static final String CANCELLED = "cancelled";
    private static final String FORMAT = "format";
    private static final String TEXT = "text";
    private static final String BARCODE_LIST = "barcodeList";
    private static final String PREFER_FRONTCAMERA = "preferFrontCamera";
    private static final String SHOW_FLIP_CAMERA_BUTTON = "showFlipCameraButton";
    private static final String SHOW_TORCH_BUTTON = "showTorchButton";
    private static final String TORCH_ON = "torchOn";
    private static final String FORMATS = "formats";
    private static final String PROMPT = "prompt";
    private static final String PROMPT_DONE = "promptDone";
    private static final String PROMPT_CANCEL = "promptCancel";
    private static final String BATCH_SCAN = "batchScan";
    private static final String FRAME_SCAN = "frameScan";
    private static final String REDLINE_SCAN = "redlineScan";

    private static final String LOG_TAG = "VSBarcodeReader";

    private PluginCall pluginCall;

    /**
     * Constructor.
     */
    public VSBarcodeReader() {
    }


    /**
     * Executes the request.
     *
     * @param call          The call to execute.
     *
     */
    @PluginMethod
    public void scan(PluginCall call) {
        this.pluginCall = call;

        //android permission auto add
        if (getPermissionState("camera") != PermissionState.GRANTED) {
            saveCall(call);
            requestPermissionForAlias("camera", call, "cameraPermsCallback");
        } else {
            scanVS();
        }
    }


    /**
     * Starts an intent to scan and decode a barcode using VSBarcodeReader.
     */
    public void scanVS() {

        final PluginCall call = this.pluginCall;

        Intent intentScan = new Intent(this.getActivity().getBaseContext(), ScannerActivity.class);

        Integer formatBitMask = 0xfffffff;

        intentScan.putExtra("EXTRA_BATCH_SCAN", call.getBoolean(BATCH_SCAN, false));
        intentScan.putExtra("EXTRA_FRAME_SCAN", call.getBoolean(FRAME_SCAN, false));
        intentScan.putExtra("EXTRA_REDLINE_SCAN", call.getBoolean(REDLINE_SCAN, false));

        intentScan.putExtra("EXTRA_PREFER_FRONTCAMERA", call.getBoolean(PREFER_FRONTCAMERA, false));
        intentScan.putExtra("EXTRA_SHOW_FLIP", call.getBoolean(SHOW_FLIP_CAMERA_BUTTON, false));
        intentScan.putExtra("EXTRA_SHOW_TORCH", call.getBoolean(SHOW_TORCH_BUTTON, false));
        intentScan.putExtra("EXTRA_TORCH_ON", call.getBoolean(TORCH_ON, false));
        if (call.hasOption(FORMATS)) {
            intentScan.putExtra("SCAN_FORMATS", call.getString(FORMATS)); // not used
        }
        if (call.hasOption(PROMPT)) {
            intentScan.putExtra("EXTRA_PROMPT", call.getString(PROMPT));
        }
        if (call.hasOption(PROMPT_DONE)) {
            intentScan.putExtra("EXTRA_PROMPT_DONE", call.getString(PROMPT_DONE));
        }
        if (call.hasOption(PROMPT_CANCEL)) {
            intentScan.putExtra("EXTRA_PROMPT_CANCEL", call.getString(PROMPT_CANCEL));
        }
        if (call.hasOption(CALLBACKID)) {
            intentScan.putExtra("EXTRA_CALLBACK_ID", call.getString(CALLBACKID));
        }

        if (call.hasOption(FORMATS)) {
            formatBitMask = 0;
            String [] formatList = call.getString(FORMATS).split(",");
            for (String format: formatList) {
                if (format.equals("QR_CODE")) formatBitMask |= com.visionsmarts.VSBarcodeReader.BARCODE_TYPE_QR;
                if (format.equals("DATA_MATRIX")) formatBitMask |= com.visionsmarts.VSBarcodeReader.BARCODE_TYPE_DATAMATRIX;
                if (format.equals("UPC_E")) formatBitMask |= com.visionsmarts.VSBarcodeReader.BARCODE_TYPE_UPC_E;
                if (format.equals("EAN_8")) formatBitMask |= com.visionsmarts.VSBarcodeReader.BARCODE_TYPE_EAN_8;
                if (format.equals("EAN_13")) formatBitMask |= com.visionsmarts.VSBarcodeReader.BARCODE_TYPE_EAN_13_UPC_A;
                if (format.equals("EAN_PLUS_2")) formatBitMask |= com.visionsmarts.VSBarcodeReader.BARCODE_TYPE_EAN_PLUS2;
                if (format.equals("EAN_PLUS_5")) formatBitMask |= com.visionsmarts.VSBarcodeReader.BARCODE_TYPE_EAN_PLUS5;
                if (format.equals("CODE_128")) formatBitMask |= com.visionsmarts.VSBarcodeReader.BARCODE_TYPE_CODE128;
                if (format.equals("CODE_93")) formatBitMask |= com.visionsmarts.VSBarcodeReader.BARCODE_TYPE_CODE93;
                if (format.equals("CODE_39")) formatBitMask |= com.visionsmarts.VSBarcodeReader.BARCODE_TYPE_CODE39;
                if (format.equals("ITF")) formatBitMask |= com.visionsmarts.VSBarcodeReader.BARCODE_TYPE_ITF;
                if (format.equals("ITF_14")) formatBitMask |= com.visionsmarts.VSBarcodeReader.BARCODE_TYPE_ITF14; // not a library code type
                if (format.equals("TELEPEN")) formatBitMask |= com.visionsmarts.VSBarcodeReader.BARCODE_TYPE_TELEPEN;
                if (format.equals("CODABAR")) formatBitMask |= com.visionsmarts.VSBarcodeReader.BARCODE_TYPE_CODABAR;
                if (format.equals("STD2OF5")) formatBitMask |= com.visionsmarts.VSBarcodeReader.BARCODE_TYPE_STD2OF5;
                if (format.equals("GS1_DATABAR_OMNI")) formatBitMask |= com.visionsmarts.VSBarcodeReader.BARCODE_TYPE_DATABAR_OMNIDIRECTIONAL;
                if (format.equals("GS1_DATABAR_LIMITED")) formatBitMask |= com.visionsmarts.VSBarcodeReader.BARCODE_TYPE_DATABAR_LIMITED;
                if (format.equals("GS1_DATABAR_EXPANDED")) formatBitMask |= com.visionsmarts.VSBarcodeReader.BARCODE_TYPE_DATABAR_EXPANDED;
                if (format.equals("GS1_DATABAR")) formatBitMask |= (com.visionsmarts.VSBarcodeReader.BARCODE_TYPE_DATABAR_OMNIDIRECTIONAL | com.visionsmarts.VSBarcodeReader.BARCODE_TYPE_DATABAR_LIMITED | com.visionsmarts.VSBarcodeReader.BARCODE_TYPE_DATABAR_EXPANDED);
            }
        }

        intentScan.addCategory(Intent.CATEGORY_DEFAULT);
        intentScan.putExtra("EXTRA_SYMBOLOGIES", formatBitMask);

        // avoid calling other phonegap apps
        intentScan.setPackage(this.getActivity().getApplicationContext().getPackageName());
        call.save();
        this.startActivityForResult(call, intentScan, REQUEST_CODE_VS);
    }

    /**
     * Called when the barcode scanner intent completes.
     *
     * @param requestCode The request code originally supplied to startActivityForResult(),
     *                       allowing you to identify who this result came from.
     * @param resultCode  The integer result code returned by the child activity through its setResult().
     * @param intent      An Intent, which can return result data to the caller (various data can be attached to Intent "extras").
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void handleOnActivityResult(int requestCode, int resultCode, Intent intent) {
        super.handleOnActivityResult(requestCode, resultCode, intent);

        // Get the previously saved call
        PluginCall savedCall = getSavedCall();
        if (requestCode == REQUEST_CODE_VS && savedCall != null) {
            if (resultCode == Activity.RESULT_OK) {
                JSObject obj = new JSObject();
                JSArray barcodes = new JSArray();

                    ArrayList<Pair<String, Integer>> decodedBarcodeList = (ArrayList<Pair<String, Integer>>) intent.getExtras().getSerializable("decodedBarcodeList");
                    for(Pair<String, Integer> p: decodedBarcodeList) {
                        String barcode = p.getFirst();
                        Integer formatCode = p.getSecond();
                        String format;
                        switch (formatCode) {
                            case com.visionsmarts.VSBarcodeReader.BARCODE_TYPE_EAN_13_UPC_A:
                                format = "EAN_13";
                                break;
                            case com.visionsmarts.VSBarcodeReader.BARCODE_TYPE_UPC_E:
                                format = "UPC_E";
                                break;
                            case com.visionsmarts.VSBarcodeReader.BARCODE_TYPE_EAN_8:
                                format = "EAN_8";
                                break;
                            case com.visionsmarts.VSBarcodeReader.BARCODE_TYPE_TELEPEN:
                                format = "TELEPEN";
                                break;
                            case com.visionsmarts.VSBarcodeReader.BARCODE_TYPE_ITF:
                                format = "ITF";
                                break;
                            case com.visionsmarts.VSBarcodeReader.BARCODE_TYPE_ITF14:
                                format = "ITF_14";
                                break; // not a library format
                            case com.visionsmarts.VSBarcodeReader.BARCODE_TYPE_CODE39:
                                format = "CODE_39";
                                break;
                            case com.visionsmarts.VSBarcodeReader.BARCODE_TYPE_CODE128:
                                format = "CODE_128";
                                break;
                            case com.visionsmarts.VSBarcodeReader.BARCODE_TYPE_CODABAR:
                                format = "CODABAR";
                                break;
                            case com.visionsmarts.VSBarcodeReader.BARCODE_TYPE_CODE93:
                                format = "CODE_93";
                                break;
                            case com.visionsmarts.VSBarcodeReader.BARCODE_TYPE_STD2OF5:
                                format = "STD2OF5";
                                break;
                            case com.visionsmarts.VSBarcodeReader.BARCODE_TYPE_DATABAR_OMNIDIRECTIONAL:
                                format = "GS1_DATABAR_OMNI";
                                break;
                            case com.visionsmarts.VSBarcodeReader.BARCODE_TYPE_DATABAR_LIMITED:
                                format = "GS1_DATABAR_LIMITED";
                                break;
                            case com.visionsmarts.VSBarcodeReader.BARCODE_TYPE_DATABAR_EXPANDED:
                                format = "GS1_DATABAR_EXPANDED";
                                break;
                            case com.visionsmarts.VSBarcodeReader.BARCODE_TYPE_QR:
                                format = "QR_CODE";
                                break;
                            case com.visionsmarts.VSBarcodeReader.BARCODE_TYPE_DATAMATRIX:
                                format = "DATA_MATRIX";
                                break;
                            default:
                                format = "UNK";
                        }
                        JSObject item = new JSObject();
                        item.put(FORMAT, format);
                        item.put(TEXT, barcode);
                        barcodes.put(item);
                    }
                    obj.put(CANCELLED, false);
                    obj.put(BARCODE_LIST, barcodes);

                this.pluginCall.resolve(obj);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                JSObject obj = new JSObject();

                    obj.put(BARCODE_LIST, new JSObject());
                    obj.put(CANCELLED, true);

                this.pluginCall.resolve(obj);
            } else {
                this.pluginCall.reject("Unexpected error");
            }
        }

    }


    /**
     * processes the result of permission request
     *
     * @param call The saved plugin call
     */
    @PermissionCallback
    private void cameraPermsCallback(PluginCall call) {
        if (getPermissionState("camera") == PermissionState.GRANTED) {
            scanVS();
        } else {
            call.reject("User denied permission to access camera");
        }
    }

}
