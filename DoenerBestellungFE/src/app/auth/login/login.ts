import {Component, inject} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormControl, FormGroup, FormGroupDirective, FormsModule, NgForm, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Auth } from '../../auth';
import { ErrorStateMatcher } from '@angular/material/core';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

@Component({
  selector: 'app-login',
  imports: [
    FormsModule,
    ReactiveFormsModule,
    CommonModule,
    MatProgressSpinnerModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule
  ],
  templateUrl: './login.html',
  styleUrl: './login.scss'
})
export class Login {

  loginForm = new FormGroup({
    'username': new FormControl(null, Validators.required),
    'password': new FormControl(null, Validators.required)
  });
  username = '';
  password = '';
  matcher = new MyErrorStateMatcher();
  isLoadingResults = false;

  private formBuilder = inject(FormBuilder);
  private router = inject(Router);
  private authService = inject(Auth);

  onFormSubmit() {
    const value = this.loginForm.value;
    this.authService.login(value)
      .subscribe({
        next: (res) => {
          console.log("Login Response ", res);
          if (res.token) {
            sessionStorage.setItem('token', res.token);
            console.log('token: ', sessionStorage.getItem('token'));
            this.router.navigate(['products']);
          }
        },
        error: (err) => {
          console.log(err);
        }
      });
  }

  register() {
    this.router.navigate(['register']);
  }
  printSessiontoken() {
    console.log(sessionStorage.getItem('token'))
    console.log(this.authService.isLoggedIn)
  }
}

/** Error when invalid control is dirty, touched, or submitted. */
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}
