import { Component, ChangeDetectorRef  } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { NgIf } from '@angular/common';
import { AuthService } from '../../../../services/auth.service';

@Component({
  selector: 'app-login-form',
  imports: [FormsModule, NgIf],
  templateUrl: './login-form.html',
  styleUrl: './login-form.css',
})
export class LoginForm {
  message = '';
  isLoading = false;
  formData = {
    login: '',
    password: ''
  };
  returnUrl: string;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private cdr: ChangeDetectorRef,
    private authService: AuthService
  ) {
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/main';
  }

  onLogin() {
    this.message = '';
    this.isLoading = true;

    if (!this.formData.login.trim() || !this.formData.password.trim()) {
      this.message = 'Логин и пароль не могут быть пустыми';
      this.isLoading = false;
      this.cdr.detectChanges();
      return;
    }

    this.authService.login(this.formData.login, this.formData.password)
      .subscribe({
        next: (response) => {
          console.log('Успешный вход:', response.message);
          this.router.navigate([this.returnUrl]);
          this.isLoading = false;
        },
        error: (error) => {
          console.error('Ошибка входа:', error);
          this.message = error.error?.message || error.error?.error || 'Ошибка входа';
          this.isLoading = false;
          this.cdr.detectChanges();
        }
      });
  }

  onRegister() {
    this.message = '';
    this.isLoading = true;

    if (!this.formData.login.trim() || !this.formData.password.trim()) {
      this.message = 'Логин и пароль не могут быть пустыми';
      this.isLoading = false;
      this.cdr.detectChanges();
      return;
    }

    this.authService.register(this.formData.login, this.formData.password)
      .subscribe({
        next: (response) => {
          this.message = response.message;
          this.authService.login(this.formData.login, this.formData.password)
            .subscribe(() => {
              this.router.navigate(['/main']);
            });
          this.isLoading = false;
          this.cdr.detectChanges();
        },
        error: (error) => {
          console.error('Ошибка регистрации:', error);
          this.message = error.error?.message || error.error?.error || 'Ошибка регистрации';
          this.isLoading = false;
          this.cdr.detectChanges();
        }
      });
  }
}
