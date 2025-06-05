import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PaymentService, TicketHistory } from '../services/payment.service';
import { HeaderComponent } from '../header/header.component';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-history',
  standalone: true,
  imports: [CommonModule, HeaderComponent, RouterModule],
  templateUrl: './history.component.html',
  styleUrl: './history.component.css'
})
export class HistoryComponent implements OnInit {
  ticketHistory: TicketHistory[] = [];
  isLoading = true;

  constructor(private paymentService: PaymentService) {}

  ngOnInit() {
    this.loadTicketHistory();
  }

  private loadTicketHistory() {
    // TODO: Replace with actual service call when backend is ready
    this.paymentService.getTicketHistory().subscribe({
      next: (history) => {
        this.ticketHistory = history;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading ticket history:', error);
        this.isLoading = false;
      }
    });
  }
}
