import { AfterContentInit, AfterViewInit, Component } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { FormBuilder, FormGroup, Validators  } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { NgIf } from '@angular/common';
import { Router, RouterModule, RouterOutlet } from '@angular/router';
import { MovieManagementModule } from '../movie-management/movie-management.module';
import { MovieDetailComponent } from '../movie-management/movie-detail/movie-detail.component';
import { HttpClientModule } from '@angular/common/http';
declare var bootstrap: any;

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [ReactiveFormsModule, NgIf, RouterModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements AfterViewInit {
  loginForm: FormGroup;
  showModal = true;
  loginElement: any



  constructor(private fb: FormBuilder, public authService: AuthService, private router: Router) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  ngAfterViewInit(): void {
    // Kiểm tra xem Bootstrap đã được tải chưa
    this.loginElement = document.getElementById('loginModal');
    const registerElement = document.getElementById('registerModal');
    if (typeof bootstrap === 'undefined') {
      console.error('Bootstrap chưa được tải! Kiểm tra lại việc nhúng Bootstrap trong index.html');
    }
  }



  onSubmit(): void {
    this.authService.login(this.loginForm.value).subscribe({
      next: (res) => {
        const tenNd = res.nguoiDung.tenNd;
        localStorage.setItem('tenNd', tenNd); 
        this.router.navigate(['']); 
      },
      error: (err) => {
        console.error('Đăng nhập lỗi:', err);
      }
    });
    // if (this.loginForm.valid) {
    //   this.authService.login(this.loginForm.value).subscribe({
    //     next: (response) => {
    //       console.log('Đăng nhập thành công:', response);
    //       this.router.navigateByUrl('');
    //     },
    //     error: (err) => {
    //       console.error('Đăng nhập thất bại:', err);
    //     }
    //   });
    // }
    if (this.loginElement) {
      const modalInstance = bootstrap.Modal.getInstance(this.loginElement);
      modalInstance?.hide();
    }
  }

  close() {
    this.showModal = false;
  }

  openLogin() {
    const loginModal = new bootstrap.Modal(document.getElementById('loginModal')!);
    loginModal.show();
  }

  openRegister() {
    const registerModal = new bootstrap.Modal(document.getElementById('registerModal')!);
    registerModal.show();
  }
}
