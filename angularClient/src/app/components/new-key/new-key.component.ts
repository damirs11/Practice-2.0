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
import { ActivatedRoute, Router } from '@angular/router';
import { KeyService } from 'src/app/api/service/key.service';
import { ParamsInput } from 'src/app/api/entity/paramsInput';

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
  matcher = new MyErrorStateMatcher();

  constructor(
    private formBuiler: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private keyService: KeyService
  ) {}

  ngOnInit(): void {
    this.newKeyForm = this.formBuiler.group({
      name: [null, Validators.required],
      expiration: [null, Validators.required],
      coresCount: [null, Validators.min(1)],
      usersCount: [null, Validators.min(1)],
      moduleFlags: this.formBuiler.group(
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
      activationFile: [null],
    });
  }

  get f() {
    return this.newKeyForm.controls;
  }

  onSubmit(form: NgForm) {
    const keyParam = this.newKeyForm.value;
    keyParam.moduleFlags = this.convertBooleanModuleFlagToByte(
      keyParam.moduleFlags
    );
    console.log(keyParam);

    this.keyService.createNewKey(keyParam).subscribe(
      (res) => {
      },
      (err) => {
        console.log(err);
        alert(err.error);
      }
    );
  }

  convertBooleanModuleFlagToByte(moduleFlags): number {
    let byteFlag = 0b00000000;
    let bitCount = 8;

    // tslint:disable-next-line: forin
    for (const flag in moduleFlags) {
      bitCount--;
      if (moduleFlags[flag]) {
        byteFlag = byteFlag ^ (0b1 << bitCount);
      }
    }
    return byteFlag;
  }

  returnToLicenses() {
    this.router.navigate(['licenses']);
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
