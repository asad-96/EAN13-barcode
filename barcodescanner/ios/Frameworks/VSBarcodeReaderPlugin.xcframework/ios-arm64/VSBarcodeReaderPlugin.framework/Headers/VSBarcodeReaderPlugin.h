//
//  VSBarcodeReaderPlugin.h
//  VSBarcodeReaderPlugin
//
//  Created by Benoit Maison on 20/01/2021.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

//! Project version number for VSBarcodeReaderPlugin.
FOUNDATION_EXPORT double VSBarcodeReaderPluginVersionNumber;

//! Project version string for VSBarcodeReaderPlugin.
FOUNDATION_EXPORT const unsigned char VSBarcodeReaderPluginVersionString[];

// In this header, you should import all the public headers of your framework using statements like #import <VSBarcodeReaderPlugin/PublicHeader.h>


@protocol VSBarcodeScannerProtocol <NSObject>
@required
- (void)sendPluginResult:(NSDictionary*)result callbackId:(NSString*)callback;
@end

@protocol PluginVSBarcodeScannerProtocol <NSObject>
@required
- (void)doneScanning;
- (void)errorScanning:(NSString*)msg;
@end

@interface PluginVSBarcodeScanner<PluginVSBarcodeScannerProtocol> : NSObject { }

@property (nonatomic, retain)   UIViewController* viewController;
@property (nonatomic, weak)   id<VSBarcodeScannerProtocol> commandDelegate;

- (PluginVSBarcodeScanner*) init;
- (void)scanWithOptions:(NSDictionary*)options;

@end

