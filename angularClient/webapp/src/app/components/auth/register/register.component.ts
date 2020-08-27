import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, NgForm, Validators,} from '@angular/forms';
import {Router} from '@angular/router';
import {AuthService} from 'src/app/api/service/auth/auth.service';
import {FormStateMatcher} from "../../../shared/state-matchers/form.state-matcher";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {ErrorModalComponent} from "../../../shared/modals/error-modal/error-modal.component";
import {LoginRequest} from "../../../api/entity/dto/request/loginRequest.model";
import {RegisterRequest} from "../../../api/entity/dto/request/registerRequest.model";

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.less'],
})
export class RegisterComponent implements OnInit {
    registerForm: FormGroup;
    isLoadingResults: boolean = false;
    matcher = new FormStateMatcher();

    constructor(
        private formBuilder: FormBuilder,
        private router: Router,
        private modal: MatDialog,
        private authService: AuthService
    ) {
    }

    ngOnInit(): void {
        this.registerForm = this.formBuilder.group({
            username: [null, Validators.required],
            password: [null, Validators.required],
        });
    }

    registration(form: NgForm): void {
        this.isLoadingResults = true;
        this.authService.registration(new RegisterRequest(form['username'], form['password'])).subscribe(
            (res) => {
                this.isLoadingResults = false;
                this.router.navigate(['/auth/login']);
            },
            (err) => {
                const modalConfig = new MatDialogConfig();
                modalConfig.data = err;
                const modalDialog =  this.modal.open(ErrorModalComponent, modalConfig);
                this.isLoadingResults = false;
            }
        );
    }
}


