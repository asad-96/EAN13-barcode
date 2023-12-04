import { Component, OnInit } from '@angular/core';
import { ApiService } from 'src/app/core/api/api.service';
import { Item } from 'src/app/core/models/item';
import { NavigationService } from 'src/app/core/services/navigation/navigation.service';

@Component({
  selector: 'app-item-list',
  templateUrl: './item-list.page.html',
  styleUrls: ['./item-list.page.scss'],
})
export class ItemListPage implements OnInit {
  code: string = '';
  loading: boolean = true;
  itemList: Array<Item> | undefined;
  constructor(
    private navigationService: NavigationService,
    private apiService: ApiService
  ) {
    const params = this.navigationService.getExtras();

    if (params) {
      this.code = params.code;
      console.log(this.code);
    }
  }

  async ngOnInit() {
    const apiResp = await this.apiService.get(
      `?action=getRecentPrices&ean13=${this.code}`
    );
    console.log('data ');
    console.log(JSON.parse(apiResp.data));

    if (apiResp.status === 200) {
      const parsedList = JSON.parse(apiResp.data);
      this.itemList = parsedList['data'];
    }
    this.loading = false;
  }
}
