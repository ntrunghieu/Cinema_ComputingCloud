import { Routes } from '@angular/router';
import { MovieDetailComponent } from './movie-management.module';

export const MOVIE_ROUTES: Routes = [
  { path: 'movie-detail/:id', component: MovieDetailComponent }
];