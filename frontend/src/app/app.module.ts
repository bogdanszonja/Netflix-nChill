import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { LoginComponent } from './components/login/login.component';
import { UserPageComponent } from './components/user-page/user-page.component';
import { FavouritesComponent } from './components/user-page/favourites/favourites.component';
import { WatchlistComponent } from './components/user-page/watchlist/watchlist.component';
import { WatchedComponent } from './components/user-page/watched/watched.component';
import { SearchComponent } from './components/search/search.component';
import { SeriesDetailComponent } from './components/search/series-detail/series-detail.component';
import { TrendingComponent } from './components/trending/trending.component';
import { AppRoutingModule } from './modules/app-routing/app-routing.module';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    LoginComponent,
    UserPageComponent,
    FavouritesComponent,
    WatchlistComponent,
    WatchedComponent,
    SearchComponent,
    SeriesDetailComponent,
    TrendingComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
