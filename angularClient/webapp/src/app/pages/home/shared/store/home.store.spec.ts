import {TestBed} from '@angular/core/testing';

import {HomeStore} from './home.store';

describe('HomeStoreService', () => {
    let service: HomeStore;

    beforeEach(() => {
        TestBed.configureTestingModule({});
        service = TestBed.inject(HomeStore);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
