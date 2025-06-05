import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, map, Observable, of } from 'rxjs';
import { IPhim } from '../model/phim/IPhim';

@Injectable({
  providedIn: 'root'
})
export class MovieService {
  private baseUrl = 'http://localhost:8080/api/phim';

  constructor(private http: HttpClient) { }

  getAllMovies(): Observable<IPhim[]> {
    return this.http.get<IPhim[]>(this.baseUrl);
  }

  getMovieById(maPhim: string): Observable<IPhim> {
    return this.http.get<IPhim>(`${this.baseUrl}/${maPhim}`);
  }

  createMovie(movie: IPhim): Observable<IPhim> {
    return this.http.post<IPhim>(this.baseUrl, movie);
  }

  updateMovie(maPhim: number | undefined, movie: IPhim): Observable<IPhim> {
    return this.http.put<IPhim>(`${this.baseUrl}/${maPhim}`, movie);
  }

  // deleteMovie(maPhim: number): Observable<void> {
  //   return this.http.delete<void>(`${this.baseUrl}/${maPhim}`);
  // }

  deleteMovie(maPhim: number | undefined): Observable<boolean> {
    return this.http.delete(`${this.baseUrl}/${maPhim}`, { responseType: 'text' })
      .pipe(
        catchError(err => {
          console.error('Delete movie failed:', err);
          return of(false); // trả về false nếu lỗi
        }),
        // map trả về true nếu thành công
        map(() => true)
      );
  }
}
