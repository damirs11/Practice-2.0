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
import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/api/security/service/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.less'],
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  loading = false;
  submitted = false;
  returnUrl: string;
  error = '';
  matcher = new MyErrorStateMatcher();

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService
  ) {
    if (this.authService.currentUserValue) {
      console.log('redirect');
      this.router.navigate(['/']);
    }
  }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      username: [null, Validators.required],
      password: [null, Validators.required],
    });

    this.returnUrl = this.route.snapshot.queryParams.returnUrl || '/';
  }

  get f() {
    return this.loginForm.controls;
  }

  onSubmit(form: NgForm) {
    this.authService.login(form).subscribe(
      (res) => {
        console.log(res);
        this.router.navigate([this.returnUrl]);
      },
      (err) => {
        console.log(err);
      }
    );
  }

  register() {
    this.authService.logout();
    this.router.navigate(['register']);
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
