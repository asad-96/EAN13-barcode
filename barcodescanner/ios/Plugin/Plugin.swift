import Foundation
import Capacitor

import VSBarcodeReaderPlugin

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(VSBarcodeReaderCap)
public class VSBarcodeReaderCap: CAPPlugin, VSBarcodeScannerProtocol {

    var scanner : PluginVSBarcodeScanner<VSBarcodeReaderCap>
    var call : CAPPluginCall?
    
    public func sendPluginResult(_ result: [AnyHashable : Any]!, callbackId callback: String!) {
        let res = result as? Dictionary<String, Any>
        if (nil != res) {
            if let msg = res!["error"] as? String {
                call?.reject(msg)
            } else {
                call?.resolve( res! )
            }
        }
        else {
            call?.reject("Invalid result from VSBarcodeReaderPlugin")
        }
    }
    
    override public init() {
        scanner = PluginVSBarcodeScanner<VSBarcodeReaderCap>()!
        super.init()
        scanner.commandDelegate = self
    }
    
    @objc override public func load() {
        // Called when the plugin is first constructed in the bridge
    }
    
    @objc func scan(_ call: CAPPluginCall) {
        scanner.viewController = self.bridge?.viewController
        self.call = call
        let options = NSMutableDictionary()
        options["callback"] = call.getString("callbackId")
        options["formats"] = call.getString("formats");
        options["preferFrontCamera"] = call.getBool("preferFrontCamera");
        options["showFlipCameraButton"] = call.getBool("showFlipCameraButton");
        options["showTorchButton"] = call.getBool("showTorchButton");
        options["torchOn"] = call.getBool("torchOn");
        options["batchScan"] = call.getBool("batchScan");
        options["frameScan"] = call.getBool("frameScan");
        options["redlineScan"] = call.getBool("redlineScan");
        options["prompt"] = call.getString("prompt", "Scanning...");
        options["promptDone"] = call.getString("promptDone", "Done");
        options["promptCancel"] = call.getString("promptCancel", "Cancel");

        DispatchQueue.main.async {
           self.scanner.scan(options: options as? [AnyHashable : Any]);
        }
        
    }
}
