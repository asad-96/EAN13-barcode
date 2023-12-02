import { WebPlugin } from '@capacitor/core';
import { VSBarcodeReaderPlugin } from './definitions';

export class VSBarcodeReaderWeb extends WebPlugin implements VSBarcodeReaderPlugin {
  constructor() {
    super({
      name: 'VSBarcodeReader',
      platforms: ['web'],
    });
  }

  async scan(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}

const VSBarcodeReader = new VSBarcodeReaderWeb();

export { VSBarcodeReader };

import { registerWebPlugin } from '@capacitor/core';
registerWebPlugin(VSBarcodeReader);
