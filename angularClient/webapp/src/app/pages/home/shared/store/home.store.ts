import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {KeyGenerationParams} from '@api/license/key-generation-params';
import {LicenseType} from '@api/license/enums/license-type';
import {Page} from '@api/license/page';

@Injectable({
    providedIn: 'any'
})
export class HomeStore {
    private updating$: BehaviorSubject<boolean> = new BehaviorSubject(false);
    private keys$: BehaviorSubject<Page<KeyGenerationParams>> = new BehaviorSubject(new Page());
    private selectedLicense$: BehaviorSubject<LicenseType> = new BehaviorSubject<LicenseType>(null);

    get selectedLicenseValue$() {
        return this.selectedLicense$.value;
    }

    isUpdating$(): Observable<boolean> {
        return this.updating$.asObservable();
    }

    setUpdating(isUpdating: boolean): void {
        this.updating$.next(isUpdating);
    }

    getKeys$(): Observable<Page<KeyGenerationParams>> {
        return this.keys$.asObservable();
    }

    setKeys(keys: Page<KeyGenerationParams>): void {
        this.keys$.next(keys);
    }

    getSelectedLicense$() {
        return this.selectedLicense$.asObservable();
    }

    setSelectedLicense(license: LicenseType): void {
        this.selectedLicense$.next(license);
    }
}

