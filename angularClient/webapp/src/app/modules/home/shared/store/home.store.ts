import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {KeyParamsInput} from '../../../../api/entity/keyParamsInput';

@Injectable({
    providedIn: 'any'
})
export class HomeStore {
    private updating$: BehaviorSubject<boolean> = new BehaviorSubject(false);
    private keys$: BehaviorSubject<KeyParamsInput[]> = new BehaviorSubject(null);

    isUpdating$(): Observable<boolean> {
        return this.updating$.asObservable();
    }

    setUpdating(isUpdating: boolean) {
        this.updating$.next(isUpdating);
    }

    getKeys$() {
        return this.keys$.asObservable();
    }

    setKeys(keys: KeyParamsInput[]) {
        this.keys$.next(keys);
    }

    addKey(key: KeyParamsInput) {
        const currentValue = this.keys$.getValue();
        this.keys$.next([...currentValue, key]);
    }
}
