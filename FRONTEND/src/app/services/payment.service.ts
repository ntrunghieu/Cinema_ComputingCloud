import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface TicketHistory {
  movieTitle: string;
  showtime: string;
  seats: string[];
  totalAmount: number;
  purchaseDate: string;
  roomName: string;
}

@Injectable({
  providedIn: 'root'
})
export class PaymentService {
  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  createBooking(data: {
    scheduleId: number,
    roomId: number,
    movieId: number,
    selectedSeats: string[],
    totalAmount: number
  }): Observable<any> {
    // Đảm bảo selectedSeats là mảng rỗng nếu null
    const selectedSeats = data.selectedSeats || [];
    
    // Chuyển đổi các trường sang đúng kiểu dữ liệu cho backend
    return this.http.post(`${this.baseUrl}/payment/create-paypal-order`, {
      scheduleId: Number(data.scheduleId), // Đảm bảo là Long
      roomId: Number(data.roomId), // Đảm bảo là Long
      movieId: Number(data.movieId), // Đảm bảo là Long
      selectedSeats: selectedSeats, // List<String>
      totalAmount: data.totalAmount.toString() // Chuyển sang string để backend parse thành BigDecimal
    });
  }

  capturePaypalOrder(bookingId: string, paypalOrderId: string): Observable<any> {
    return this.http.post(
      `${this.baseUrl}/payment/payment-success?bookingCode=${bookingId}&paypalOrderId=${paypalOrderId}`,
      null  // Không chỉ định responseType, mặc định là 'json'
    );
  }

  getTicketHistory(): Observable<TicketHistory[]> {
    return this.http.get<TicketHistory[]>(`${this.baseUrl}/payment/history`);
  }
}
