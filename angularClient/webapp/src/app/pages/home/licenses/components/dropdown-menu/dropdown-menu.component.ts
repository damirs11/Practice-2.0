import {Component} from '@angular/core';
import {ICellRendererAngularComp} from 'ag-grid-angular';
import {ICellRendererParams} from 'ag-grid-community';

@Component({
    selector: 'app-dropdown-menu',
    templateUrl: './dropdown-menu.component.html',
    styleUrls: ['./dropdown-menu.component.less']
})
export class DropdownMenuComponent implements ICellRendererAngularComp {

    params;

    constructor() {
    }

    agInit(params: ICellRendererParams): void {
        this.params = params;
    }

    refresh(params: any): boolean {
        return true;
    }

    isFileExists(item) {
        if (item?.isFileExists) {
            return item?.isFileExists(this.params.data);
        }
        return true;
    }

    onClick($event, index) {
        this.params.dropdownData[index].buttonClick(this.params.node.data);
    }
}
