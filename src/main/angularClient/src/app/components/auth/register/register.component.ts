import { Component, OnInit } from '@angular/core';
import {
  FormGroup,
  FormControl,
  FormGroupDirective,
  NgForm,
  FormBuilder,
  Validators,
} from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/api/security/service/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.less'],
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  username = '';
  password = '';
  isLoadingResults = false;
  matcher = new MyErrorStateMatcher();

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      username: [null, Validators.required],
      password: [null, Validators.required],
    });
  }

  onFormSubmit(form: NgForm) {
    this.isLoadingResults = true;
    this.authService.register(form).subscribe(
      (res) => {
        this.router.navigate(['login']);
      },
      (err) => {
        console.log(err);
        alert(err.error);
        this.isLoadingResults = false;
      }
    );
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['login']);
  }
}

/** Error when invalid control is dirty, touched, or submitted. */
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(
    control: FormControl | null,
    form: FormGroupDirective | NgForm | null
  ): boolean {
    const isSubmitted = form && form.submitted;
    return !!(
      control &&
      control.invalid &&
      (control.dirty || control.touched || isSubmitted)
    );
  }
}
