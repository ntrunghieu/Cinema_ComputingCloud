import { Component, inject, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { IPhim } from '../model/phim/IPhim';
import { MovieService } from '../services/movie.service';
import { HeaderComponent } from '../header/header.component';
import { Storage, ref, uploadBytes, getDownloadURL } from '@angular/fire/storage';

@Component({
  selector: 'app-movie',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule, HeaderComponent],
  templateUrl: './movie.component.html',
  styleUrl: './movie.component.css'
})
export class MovieComponent implements OnInit {
  idDelete: any;
  movies: IPhim[] = [];
  selectedMovie: IPhim | null = null;
  isLoading = true;
  isEditing = false;
  showForm = false;
  newMovie: IPhim = {
    maPhim: '',
    tenPhim: '',
    thoiLuong: 0,
    moTa: '',
    daoDien: '',
    dienVien: '',
    quocGia: '',
    ngayPhatHanh: '',
    trangThai: 'Sắp chiếu',
    anh: '',
    trailer: ''
  };
  selectedFile: File | null = null;
  @ViewChild('fileInput') fileInput: any;

  private storage: Storage;

  constructor(private movieService: MovieService) {
    this.storage = inject(Storage);
  }

  ngOnInit() {
    this.loadMovies();
  }


  // onFileSelected(event: Event) {
  //   const input = event.target as HTMLInputElement;
  //   if (input.files && input.files[0]) {
  //     this.selectedFile = input.files[0];
  //   }
  // }

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input?.files) {
      this.selectedFile = input.files[0];
      this.newMovie.anh = this.selectedFile.name; // Lưu tên file
    }
  }
  

  loadMovies() {
    this.isLoading = true;
    this.movieService.getAllMovies().subscribe({
      next: (movies: IPhim[]) => {
        this.movies = movies;
        this.isLoading = false;
      },
      error: (error: Error) => {
        console.error('Error loading movies:', error);
        this.isLoading = false;
      }
    });
  }

  openAddForm() {
    this.isEditing = false;
    this.showForm = true;
    this.selectedMovie = null;
    this.newMovie = {
      maPhim: '',
      tenPhim: '',
      thoiLuong: 0,
      moTa: '',
      daoDien: '',
      dienVien: '',
      quocGia: '',
      ngayPhatHanh: '',
      trangThai: 'Sắp chiếu',
      anh: '',
      trailer: ''
    };
  }

  openEditForm(movie: IPhim) {
    this.isEditing = true;
    this.showForm = true;
    this.selectedMovie = { ...movie };
    this.newMovie = { ...movie };
  }

  // saveMovie() {
  //   if (this.isEditing && this.selectedMovie) {
  //     this.movieService.updateMovie(this.selectedMovie.id, this.newMovie).subscribe({
  //       next: () => {
  //         this.loadMovies();
  //         this.showForm = false;
  //         this.selectedMovie = null;
  //         alert('Cập nhật thành công')
  //       },
  //       error: (error: Error) => {
  //         console.error('Error updating movie:', error);
  //       }
  //     });
  //   } else {
  //     this.movieService.createMovie(this.newMovie).subscribe({
  //       next: () => {
  //         this.loadMovies();
  //         this.showForm = false;
  //         alert('Thêm mới thành công')
  //       },
  //       error: (error: Error) => {
  //         console.error('Error creating movie:', error);
  //       }
  //     });
  //   }
  // }

    // Lấy tên file từ URL ảnh
    getFileName(url: string): string {
      const segments = url.split('/');
      return segments[segments.length - 1];
    }

  saveMovie() {
    if (this.selectedFile) {
      const filePath = `${Date.now()}_${this.selectedFile.name}`;
      const storageRef = ref(this.storage, filePath); //ref đúng kiểu Storage
  
      uploadBytes(storageRef, this.selectedFile).then(() => {
        return getDownloadURL(storageRef);
      }).then((url) => {
        this.newMovie.anh = url;
        this._submitMovie();
        this.resetFileInput();
      }).catch((error) => {
        console.error('Upload thất bại', error);
        alert('Lỗi upload ảnh');
        this.resetFileInput();
      });
    } else {
      this._submitMovie();
      this.resetFileInput();
    }
  }
  

  private _submitMovie() {
    if (this.isEditing && this.selectedMovie) {
      this.movieService.updateMovie(this.selectedMovie.id, this.newMovie).subscribe({
        next: () => {
          this.loadMovies();
          this.showForm = false;
          this.selectedMovie = null;
          alert('Cập nhật thành công');
        },
        error: (error: Error) => console.error('Error updating movie:', error)
      });
    } else {
      this.movieService.createMovie(this.newMovie).subscribe({
        next: () => {
          this.loadMovies();
          this.showForm = false;
          alert('Thêm mới thành công');
        },
        error: (error: Error) => console.error('Error creating movie:', error)
      });
    }
  }
  
  resetFileInput() {
    if (this.fileInput) {
      this.fileInput.nativeElement.value = '';  // Đặt lại giá trị của input file thành rỗng
    }
  }

  // deleteMovie(maPhim: number) {
  //   if (confirm('Bạn có chắc chắn muốn xóa phim này?')) {
  //     this.movieService.deleteMovie(maPhim).subscribe({
  //       next: () => {
  //         this.loadMovies();
  //       },
  //       error: (error: Error) => {
  //         console.error('Error deleting movie:', error);
  //       }
  //     });
  //   }
  // }

  deleteMovie(maPhim: number | undefined) {
    this.idDelete = maPhim;
    if (confirm('Bạn có chắc chắn muốn xóa phim này?')) {
      this.movieService.deleteMovie(maPhim).subscribe({
        next: (result) => {
          if (result) {
            this.loadMovies(); // Xóa thành công → reload danh sách
            alert('Xóa thành công')
          } else {
            alert('Xoá phim thất bại. Vui lòng thử lại.');
          }
        },
        error: (error: Error) => {
          console.error('Error deleting movie:', error);
          alert('Đã xảy ra lỗi khi xóa phim.');
        }
      });
    }
  }

  cancelEdit() {
    this.showForm = false;
    this.selectedMovie = null;
  }
}
