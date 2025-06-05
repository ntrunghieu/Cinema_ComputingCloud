import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { ActivatedRoute, RouterModule, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ScheduleService } from '../services/schedule.service';
import { IPhim } from '../model/phim/IPhim';
import { SeatService } from '../services/seat.service';
import { SeatInfoResponse } from '../model/SeatInfoResponse';
import { PaymentService } from '../services/payment.service';

declare var paypal: any;

@Component({
  selector: 'app-seats',
  standalone: true,
  imports: [HeaderComponent, RouterModule, CommonModule],
  templateUrl: './seats.component.html',
  styleUrl: './seats.component.css'
})
export class SeatsComponent implements OnInit {
  @ViewChild('paypalButtons') paypalButtons!: ElementRef;

  phim!: IPhim;
  seatInfo!: SeatInfoResponse;

  scheduleId: number;
  roomId: number;
  phimId: number;
  selectedSeats: string[] = [];
  bookedSeats: string[] = [];
  totalPrice: number = 0;
  bookingId: string = '';
  readonly SEAT_PRICE = 120000; // Giá mỗi ghế
  readonly MAX_SEATS = 8; // Số ghế tối đa được chọn
  
  paypalButtonsRendered = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private scheduleService: ScheduleService,
    private seatService: SeatService,
    private paymentService: PaymentService
  ) {
    this.scheduleId = Number(this.route.snapshot.params['scheduleId']);
    this.roomId = Number(this.route.snapshot.params['roomId']);
    this.phimId = Number(this.route.snapshot.params['phimId']);
  }

  ngOnInit() {

    this.seatService.getSeatInfo(this.scheduleId, this.roomId, this.phimId).subscribe(
      (data) => {
        console.log('Thông tin phòng chiếu:', data);
        this.seatInfo = data;
        if (data.danhSachGheDaDat) {
          this.bookedSeats = data.danhSachGheDaDat;
        }
      },
      (error) => {
        console.error('Lỗi khi tải thông tin ghế:', error);
      }
    );
  }



  toggleSeatSelection(seatId: string, event: MouseEvent) {
    const element = event.target as HTMLElement;
    
    // Kiểm tra nếu ghế đã được đặt
    if (this.seatInfo?.danhSachGheDaDat?.includes(seatId)) {
      alert('Ghế này đã được đặt!');
      return;
    }

    const seatIndex = this.selectedSeats.indexOf(seatId);
    if (seatIndex === -1) {
      // Kiểm tra số lượng ghế tối đa
      if (this.selectedSeats.length >= this.MAX_SEATS) {
        alert(`Bạn chỉ được chọn tối đa ${this.MAX_SEATS} ghế!`);
        return;
      }
      // Thêm ghế vào danh sách đã chọn
      this.selectedSeats.push(seatId);
      element.classList.remove('available');
      element.classList.add('selected');
    } else {
      // Bỏ chọn ghế
      this.selectedSeats.splice(seatIndex, 1);
      element.classList.remove('selected');
      element.classList.add('available');
    }

    // Sắp xếp lại danh sách ghế đã chọn
    this.selectedSeats.sort();

    // Cập nhật tổng tiền
    this.totalPrice = this.selectedSeats.length * this.SEAT_PRICE;
    this.renderPaypalButtonsIfNeeded();
  }

  private renderPaypalButtonsIfNeeded() {
    if (this.selectedSeats.length > 0 && !this.paypalButtonsRendered) {
      this.renderPayPalButtons();
      this.paypalButtonsRendered = true;
    } else if (this.selectedSeats.length === 0 && this.paypalButtonsRendered) {
      this.paypalButtons.nativeElement.innerHTML = '';
      this.paypalButtonsRendered = false;
    }
  }

  private renderPayPalButtons() {
    if (!this.paypalButtons) return;

    this.paypalButtons.nativeElement.innerHTML = '';
    paypal.Buttons({
      createOrder: (data: any, actions: any) => {
        // Tạo booking trước
        return this.paymentService.createBooking({
          scheduleId: this.scheduleId,
          roomId: this.roomId,
          movieId: this.phimId,
          selectedSeats: this.selectedSeats,
          totalAmount: this.totalPrice
        }).toPromise()
          .then((response: any) => {
            if (typeof response === 'string' && response.includes('Đặt vé thất bại')) {
              // Nếu backend trả về lỗi dạng string
              throw new Error(response);
            }
            
            // Lưu booking code và paypal order id
            this.bookingId = response.bookingCode;
            const paypalOrderId = response.paypalOrderId;
            
            // Tạo PayPal order
            return actions.order.create({
              purchase_units: [{
                amount: {
                  value: (this.totalPrice / 23000).toFixed(2),
                  currency_code: 'USD'
                },
                description: `Vé xem phim - ${this.selectedSeats.join(', ')}`,
                custom_id: paypalOrderId // Thêm paypal order id từ backend
              }]
            });
          })
          .catch(error => {
            console.error('Lỗi khi tạo đơn đặt vé:', error);
            
            // Kiểm tra lỗi cụ thể
            let errorMessage = 'Không thể tạo đơn đặt vé';
            
            if (error.message) {
              if (error.message.includes('Duplicate entry') && error.message.includes('ve.lich_chieu_id')) {
                errorMessage = 'Hiện tại hệ thống đang gặp sự cố kỹ thuật. Vui lòng thử lại sau.';
                // Gửi thông báo cho admin
                console.error('Cần sửa lại unique constraint trong database');
              } else {
                errorMessage = error.message;
              }
            }
            
            alert(errorMessage);
            throw error;
          });
      },
      onApprove: (data: any, actions: any) => {
        return actions.order.capture().then((details: any) => {
          const paypalOrderId = details.purchase_units[0].custom_id;
          
          return this.paymentService.capturePaypalOrder(this.bookingId, paypalOrderId)
            .toPromise()
            .then((response: any) => {
              // Kiểm tra response.status hoặc response.message
              if (response.status === 'success') {
                // Chuyển đến trang xác nhận đặt vé
                this.router.navigate(['/booking-confirmation'], {
                  queryParams: {
                    orderId: this.bookingId,
                    seats: this.selectedSeats.join(','),
                    amount: this.totalPrice
                  }
                });
              } else {
                throw new Error('Xác nhận thanh toán thất bại');
              }
            });
        });
      },
      onError: (err: any) => {
        console.error('Lỗi thanh toán PayPal:', err);
        alert('Có lỗi xảy ra trong quá trình thanh toán. Vui lòng thử lại.');
      }
    }).render(this.paypalButtons.nativeElement);
  }

  // Không cần hàm updateSeatStatus nữa vì đã dùng class binding trong template
}
