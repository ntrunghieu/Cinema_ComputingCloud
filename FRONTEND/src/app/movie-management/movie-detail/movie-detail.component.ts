import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { HeaderComponent } from '../../header/header.component';
import { IPhim } from '../../model/phim/IPhim';
import { PhimServiceService } from '../../services/phim/phim-service.service';
import { CommonModule } from '@angular/common';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-movie-detail',
  standalone: true,
  imports: [RouterModule, HeaderComponent, CommonModule],
  templateUrl: './movie-detail.component.html',
  styleUrl: './movie-detail.component.css'
})
export class MovieDetailComponent implements OnInit{
  phim!: IPhim;
  safeUrl!: SafeResourceUrl;

  constructor(private route: ActivatedRoute, 
              private phimService: PhimServiceService,
              private sanitizer: DomSanitizer) {}
  
  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.phimService.getPhimById(id).subscribe(data => {
      this.phim = data;

      if (this.phim.trailer) {
        this.safeUrl = this.sanitizer.bypassSecurityTrustResourceUrl(this.phim.trailer);
      }
    });
  }
}
