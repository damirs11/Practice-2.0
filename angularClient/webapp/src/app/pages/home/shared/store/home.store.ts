import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {KeyParams} from '../../../../api/license/keyParams';

@Injectable({
    providedIn: 'any'
})
export class HomeStore {
    private updating$: BehaviorSubject<boolean> = new BehaviorSubject(false);
    private keys$: BehaviorSubject<KeyParams[]> = new BehaviorSubject(null);

    isUpdating$(): Observable<boolean> {
        return this.updating$.asObservable();
    }

    setUpdating(isUpdating: boolean) {
        this.updating$.next(isUpdating);
    }

    getKeys$() {
        return this.keys$.asObservable();
    }

    setKeys(keys: KeyParams[]) {
        this.keys$.next(keys);
    }

    addKey(key: KeyParams) {
        const currentValue = this.keys$.getValue();
        this.keys$.next([...currentValue, key]);
    }
}

