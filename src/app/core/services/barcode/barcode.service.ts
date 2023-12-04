import { Injectable } from '@angular/core';
import {
  BarcodeScanner,
  ScanResult,
} from '@capacitor-community/barcode-scanner';

@Injectable({
  providedIn: 'root',
})
export class BarcodeService {
  constructor() {}
  scanBarcode(): Promise<ScanResult> {
    return new Promise(async (resolve, reject) => {
      try {
        await BarcodeScanner.checkPermission({ force: true });
        await BarcodeScanner.hideBackground();
        const result = await BarcodeScanner.startScan();
        resolve(result);
      } catch (error) {
        reject(error);
      }
    });
  }
}
