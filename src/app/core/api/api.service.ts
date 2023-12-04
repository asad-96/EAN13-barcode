import { Injectable } from '@angular/core';
import { CapacitorHttp, HttpResponse } from '@capacitor/core';

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  readonly baseUrl: string = 'https://your-website-demo-at-kodx.com/';
  constructor() {}
  get(endPoint: string): Promise<HttpResponse> {
    return new Promise(async (resolve, reject) => {
      try {
        const result = await CapacitorHttp.get({
          url: this.baseUrl + endPoint ?? '',
        });

        resolve(result);
      } catch (error) {
        reject(error);
      }
    });
  }
}
