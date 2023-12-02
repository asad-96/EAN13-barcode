import { Component } from '@angular/core';
import { BarcodeServiceService } from 'src/app/core/services/barcode-service.service';
import * as vscbs from 'visionsmarts-capacitor-barcodescanner';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage {
  constructor(private barcodeServiceService: BarcodeServiceService) {}
  async startScan() {
    const test = await this.barcodeServiceService.scanBarcode();
  }
}
