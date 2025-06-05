import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth/login';

  constructor(private http: HttpClient) {}
  private user: any = null;

  // login(userData: any) {
  //   localStorage.setItem('user', JSON.stringify(userData));
  //   this.user = userData;
  // }

  login(credentials: { email: string; password: string }): Observable<any> {
    return this.http.post<any>(this.apiUrl, credentials);
  }

  logout() {
    localStorage.removeItem('tenNd');
    this.user = null;
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('tenNd');
  }

  getUser() {
    const ten = localStorage.getItem('tenNd');
    return ten;
    // return JSON.parse(localStorage.getItem('tenNd') || '{}');
  }
}
