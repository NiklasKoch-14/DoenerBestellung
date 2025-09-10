import {Component, inject} from '@angular/core';
import { FormControl, FormGroup, FormGroupDirective, FormsModule, NgForm, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Auth } from '../../auth';
import { ErrorStateMatcher } from '@angular/material/core';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-register',
  imports: [
    FormsModule,
    ReactiveFormsModule,
    CommonModule,
  ],
  templateUrl: './register.html',
  styleUrl: './register.scss'
})
export class Register {
  registerForm = new FormGroup({
    'username': new FormControl(null, Validators.required),
    'password': new FormControl(null, Validators.required),
    'email': new FormControl(null, Validators.required)
  });
  username = '';
  email = '';
  password = '';
  isLoadingResults = false;
  matcher = new MyErrorStateMatcher();

  response: string | undefined;

  private router = inject(Router);
  private authService = inject(Auth);

  constructor() { }

  onFormSubmit() {
    const value = this.registerForm.value;
    this.authService.register(value)
      .subscribe({
        next: (res) => {
          console.log('Response nach register ', res);
          this.response = res;
          this.router.navigate(['login']);
        }, error: (err) => {
          console.log(err);
          alert(err.error);
        }
      });
  }
}

/** Error when invalid control is dirty, touched, or submitted. */
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}
