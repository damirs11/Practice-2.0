import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, NgForm, Validators,} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {KeyService} from 'src/app/api/service/key/key.service';
import {FormStateMatcher} from "../../../shared/state-matchers/form.state-matcher";

@Component({
    selector: 'app-new-key',
    templateUrl: './new-key.component.html',
    styleUrls: ['./new-key.component.less'],
})
export class NewKeyComponent implements OnInit {
    newKeyForm: FormGroup;
    loading = false;
    submitted = false;
    error = '';
    matcher = new FormStateMatcher();

    selectedFile: File;

    constructor(
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private router: Router,
        private keyService: KeyService
    ) {
    }

    ngOnInit(): void {
        this.newKeyForm = this.formBuilder.group({
            name: [null, Validators.required],
            expiration: [null, Validators.required],
            coresCount: [null, Validators.min(1)],
            usersCount: [null, Validators.min(1)],
            moduleFlags: this.formBuilder.group(
                {
                    platformOk: false,
                    EDS: false,
                    features: false,
                    archive: false,
                },
                Validators.required
            ),
            keyFileName: [null, Validators.required],
            comment: [null],
        });
    }

    createKey(form: NgForm) {
        const keyParams = this.newKeyForm.value;
        keyParams.moduleFlags = this.keyService.convertBooleanModuleFlagToByte(keyParams.moduleFlags);

        this.keyService.createNewKey(keyParams).subscribe(
            (res) => {
                this.router.navigate(['licenses']);
            },
            (err) => {
                console.log(err);
                alert(err.error);
            }
        );
    }


}
