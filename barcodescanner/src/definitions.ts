declare module '@capacitor/core' {
  interface PluginRegistry {
    VSBarcodeReader: VSBarcodeReaderPlugin;
  }
}

export interface VSBarcodeReaderPlugin {
  scan(options: { value: string }): Promise<{ value: string }>;
}
