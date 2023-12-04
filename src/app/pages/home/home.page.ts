import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { BarcodeScanner } from '@capacitor-community/barcode-scanner';

import { AppRoutes } from 'src/app/core/models/app-routes';
import { BarcodeService } from 'src/app/core/services/barcode/barcode.service';
import { NavigationService } from 'src/app/core/services/navigation/navigation.service';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage {
  isScanActive = false;
  constructor(
    private barcodeService: BarcodeService,
    private navigationService: NavigationService
  ) {}
  async startScan() {
    this.isScanActive = true;

    const barcodeResult = await this.barcodeService.scanBarcode();
    console.log('barcoderesult');
    console.log(barcodeResult);

    this.stopScan();
    if (barcodeResult.hasContent) {
      this.navigationService.push(AppRoutes.ITEM_LIST, {
        code: barcodeResult.content,
      });
    }
  }
  stopScan() {
    BarcodeScanner.stopScan();
    this.isScanActive = false;
  }
}
