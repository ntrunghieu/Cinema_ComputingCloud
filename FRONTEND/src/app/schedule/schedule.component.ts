import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { PhimServiceService } from '../services/phim/phim-service.service';
import { ILichChieu } from '../model/phim/ILichChieu';
import { NgFor } from '@angular/common';
import { IPhim } from '../model/phim/IPhim';
import { ScheduleService } from '../services/schedule.service';

@Component({
  selector: 'app-schedule',
  standalone: true,
  imports: [HeaderComponent, RouterModule, NgFor],
  templateUrl: './schedule.component.html',
  styleUrl: './schedule.component.css'
})
export class ScheduleComponent implements OnInit{
  groupedLichChieu: { phim: IPhim; suatChieu: ILichChieu[] }[] = [];
  lichChieuList: ILichChieu[] = [];
  anh: string | undefined;
  

  constructor(
    private route: ActivatedRoute,
    private lichChieuService: PhimServiceService,
    private scheduleService: ScheduleService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    // this.lichChieuService.getLichChieuByMaPhim(id).subscribe((data: ILichChieu[]) => {
    //   this.lichChieuList = data;
    //   console.log(this.lichChieuList);
    // });

    // if (this.lichChieuList.length > 0) {
    //   this.anh = this.lichChieuList[0].phim.anh;
    //   console.log(this.lichChieuList);
    // }

    this.lichChieuService.getLichChieuByMaPhim(id).subscribe(data => {
      const grouped = new Map<number, { phim: IPhim; suatChieu: ILichChieu[] }>();
  
      data.forEach(lc => {
        const phimId = lc.phim.id;
        if (!grouped.has(id)) {
          grouped.set(id, { phim: lc.phim, suatChieu: [lc] });
        } else {
          grouped.get(id)!.suatChieu.push(lc);
        }
      });
  
      this.groupedLichChieu = Array.from(grouped.values());

      console.log(this.groupedLichChieu);
    });
  }

  onShowtimeSelect(scheduleId: number, roomId: number) {
    this.scheduleService.getAvailableSeats(scheduleId, roomId).subscribe(
      (data) => {
        console.log('Available seats:', data);
        // Here you can handle the seat data, e.g. navigate to seat selection page
        // or update the UI to show available seats
      },
      (error) => {
        console.error('Error fetching seats:', error);
      }
    );
  }
}
