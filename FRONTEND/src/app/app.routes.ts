import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { MovieDetailComponent } from './movie-management/movie-detail/movie-detail.component';
import { SeatsComponent } from './seats/seats.component';
import { RoomComponent } from './room/room.component';
import { ScheduleComponent } from './schedule/schedule.component';
import { BillComponent } from './bill/bill.component';
import { BookingConfirmationComponent } from './booking-confirmation/booking-confirmation.component';
import { HistoryComponent } from './history/history.component';
import { MovieComponent } from './movie/movie.component';

export const routes: Routes = [
    {   path: '', component: HomeComponent },
    {
        path: 'first-component',
        component: RoomComponent,
    },
    {
        path: 'seat/:scheduleId/:roomId/:phimId',
        component: SeatsComponent,
    },
    {
        path: 'booking-confirmation',
        component: BookingConfirmationComponent
    },
    {
        path: 'bill',
        component: BillComponent,
    },
    {
        path: 'schedule/:id',
        component: ScheduleComponent,
    },
    {
        path: 'movie',
        loadChildren: () => import('./movie-management/movie-management.routes').then(m => m.MOVIE_ROUTES)
    },
    {
        path: 'history',
        component: HistoryComponent
    },
    {
        path: 'admin',
        component: MovieComponent
    }
];
