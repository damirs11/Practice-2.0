import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Page} from '@api/license/page';
import {PageSettings} from '@api/license/pageSettings';

@Component({
    selector: 'app-pagination-toolbar',
    templateUrl: './pagination-toolbar.component.html',
    styleUrls: ['./pagination-toolbar.component.less']
})
export class PaginationToolbarComponent<T> implements OnInit {

    @Input() page: Page<T> = null;
    @Output() refresh: EventEmitter<null> = new EventEmitter<null>();

    constructor() {
    }

    ngOnInit(): void {
    }

    firstPage() {
        PageSettings.page = 0;
        this.refresh.emit();
    }

    back() {
        --PageSettings.page;
        this.refresh.emit();
    }

    next() {
        if (this.page.totalPages - 1 > this.page.number) {
            ++PageSettings.page;
            this.refresh.emit();
        }
    }

    lastPage() {
        PageSettings.page = this.page.totalPages - 1;
        this.refresh.emit();
    }
}
