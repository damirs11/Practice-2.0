import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
    selector: 'app-btn-cell-renderer',
    templateUrl: './btn-cell-renderer.component.html',
    styleUrls: ['./btn-cell-renderer.component.less']
})
export class BtnCellRendererComponent {

    // TODO: Доделать кнопку

    content: string;

    @Input() cell: any;
    @Output() clicked = new EventEmitter();

    click(): void {
        this.clicked.emit(this.cell);
    }
}
