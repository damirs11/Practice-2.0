import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {KeyGenerationParams} from '@api/license/key-generation-params';
import {LicenseType} from '@api/license/enums/license-type';

@Injectable({
    providedIn: 'any'
})
export class HomeStore {
    private updating$: BehaviorSubject<boolean> = new BehaviorSubject(false);
    private keys$: BehaviorSubject<KeyGenerationParams[]> = new BehaviorSubject(null);
    private selectedLicense$: BehaviorSubject<LicenseType> = new BehaviorSubject<LicenseType>(null);

    isUpdating$(): Observable<boolean> {
        return this.updating$.asObservable();
    }

    setUpdating(isUpdating: boolean): void {
        this.updating$.next(isUpdating);
    }

    getKeys$(): Observable<KeyGenerationParams[]> {
        return this.keys$.asObservable();
    }

    setKeys(keys: KeyGenerationParams[]): void {
        this.keys$.next(keys);
    }

    addKey(key: KeyGenerationParams): void {
        const currentValue = this.keys$.getValue();
        this.keys$.next([...currentValue, key]);
    }

    getSelectedLicenseValue$(): LicenseType {
        return this.selectedLicense$.value;
    }

    setSelectedLicense(license: LicenseType): void {
        this.selectedLicense$.next(license);
    }
}

