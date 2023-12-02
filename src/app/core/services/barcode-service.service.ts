import { Injectable } from '@angular/core';
import { VSBarcodeReader } from 'barcodescanner/src';

@Injectable({
  providedIn: 'root',
})
export class BarcodeServiceService {
  constructor() {}
  scanBarcode() {
    const options = {
      prompt: 'Please scan',
      promptCancel: 'Cancel',
      frameScan: true,
      torchOn: false,
      showTorchButton: true,
    };
    // options.formats = this.dataService.appConfigs.getValue()?.Data.APP_BARCODE_TYPE.join(',');
    // VSBarcodeReader['scan']({ value: 'Sdf' });
    VSBarcodeReader.scan({ value: 'sdfsdf' });
  }
}
