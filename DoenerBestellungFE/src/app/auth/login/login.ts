import {Component, inject} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormControl, FormGroup, FormGroupDirective, FormsModule, NgForm, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Auth } from '../../services/auth';
import { ErrorStateMatcher } from '@angular/material/core';
import {MatInput} from '@angular/material/input';

@Component({
  selector: 'app-login',
  imports: [
    FormsModule,
    ReactiveFormsModule,
    CommonModule,
    MatInput,
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
  errorMessage?: string;
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
          if(res.errorMessage) {
            this.errorMessage = res.errorMessage;
            console.log(this.errorMessage)
          }
          if (res.token) {
            sessionStorage.setItem('token', res.token);
            this.authService.currentUsername = res.username;
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
