import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SeatInfoResponse } from '../model/SeatInfoResponse';


@Injectable({
  providedIn: 'root'
})
export class SeatService {
  private baseUrl = 'http://localhost:8080/api/ghe';

  constructor(private http: HttpClient) { }

  getSeatInfo(scheduleId: number, roomId: number, phimId: number): Observable<SeatInfoResponse> {
    return this.http.get<SeatInfoResponse>(
      `${this.baseUrl}/${scheduleId}/rooms/${roomId}/movies/${phimId}/seat-info`
    );
  }
  
}
