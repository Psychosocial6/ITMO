import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, BehaviorSubject, tap } from 'rxjs';

export interface LoginRequest {
  login: string;
  password: string;
}

export interface AuthResponse {
  token: string;
  username: string;
  success: boolean;
  message?: string;
}

export interface MessageResponse {
  message: string;
  success: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly TOKEN_KEY = 'jwt_token';
  private readonly USER_KEY = 'user_login';
  private apiUrl = 'http://localhost:8080/backend/app';

  private authStatus = new BehaviorSubject<boolean>(this.isAuthenticated());
  authStatus$ = this.authStatus.asObservable();

  constructor(private http: HttpClient, private router: Router) {}

  login(login: string, password: string): Observable<AuthResponse> {
    const request: LoginRequest = { login, password };
    return this.http.post<AuthResponse>(`${this.apiUrl}/auth/login`, request)
      .pipe(
        tap(response => {
          if (response.token) {
            localStorage.setItem(this.TOKEN_KEY, response.token);
            localStorage.setItem(this.USER_KEY, login);
            this.authStatus.next(true);
          }
        })
      );
  }

  register(login: string, password: string): Observable<MessageResponse> {
    const request: LoginRequest = { login, password };
    return this.http.post<MessageResponse>(`${this.apiUrl}/auth/register`, request);
  }

  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.USER_KEY);
    this.authStatus.next(false);
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  isAuthenticated(): boolean {
    const token = this.getToken();
    if (!token) return false;

    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload.exp * 1000 > Date.now();
    } catch {
      return false;
    }
  }

  getUsername(): string | null {
    const token = this.getToken();
    if (!token) return null;

    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload.sub; // Извлекаем username из subject токена
    } catch {
      return localStorage.getItem(this.USER_KEY);
    }
  }

  validateToken(): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/auth/validate`, {});
  }
}
