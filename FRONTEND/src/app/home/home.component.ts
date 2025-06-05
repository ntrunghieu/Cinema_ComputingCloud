import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { RouterLink, RouterModule, RouterOutlet } from '@angular/router';
import { IPhim } from '../model/phim/IPhim';
import { PhimServiceService } from '../services/phim/phim-service.service';
import { NgFor } from '@angular/common';
import { ILichChieu } from '../model/phim/ILichChieu';
declare var bootstrap: any;

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [HeaderComponent, RouterModule, NgFor],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit{
  groupedLichChieu: { phim: IPhim; suatChieu: ILichChieu[] }[] = [];
  showModal = true;
  phim: IPhim[] = [];
  idPhim: Number | undefined;

  constructor(private phimService: PhimServiceService, private lichChieuService: PhimServiceService) { }

  ngOnInit(): void {
    this.phimService.getAllPhim().subscribe((data: IPhim[]) => {
      this.phim = data;
      console.log(this.phim);
      
    });
    // this.id = Number(this.phim[0]?.id);
    // console.log(this.id);
    // this.getLichChieu();
  }
  
  close() {
    this.showModal = false;
  }

  openScheduleModal(phimId1: Number) {
    const loginModal = new bootstrap.Modal(document.getElementById('showtimeModal')!);
    loginModal.show();
    console.log('ID phim:', phimId1);
    const id = Number(phimId1);

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
    

  // getLichChieu() {
    
  //   this.lichChieuService.getLichChieuByMaPhim(phimId).subscribe(data => {
  //     const grouped = new Map<number, { phim: IPhim; suatChieu: ILichChieu[] }>();

  //     data.forEach(lc => {
  //       const phimId = lc.phim.id;
  //       if (!grouped.has(id)) {
  //         grouped.set(id, { phim: lc.phim, suatChieu: [lc] });
  //       } else {
  //         grouped.get(id)!.suatChieu.push(lc);
  //       }
  //     });
  
  //     this.groupedLichChieu = Array.from(grouped.values());

  //     console.log(this.groupedLichChieu);
  //   });
  // }

}
