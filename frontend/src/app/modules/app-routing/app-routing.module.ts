import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LoginComponent } from '../../components/login/login.component';
import { SearchComponent } from '../../components/search/search.component';
import { UserPageComponent } from '../../components/user-page/user-page.component';
import { TrendingComponent } from '../../components/trending/trending.component';

const routes: Routes = [
  {path: '', component: TrendingComponent},
  {path: 'login', component: LoginComponent},
  {path: 'search', component: SearchComponent},
  {path: 'user-page', redirectTo: 'user-page/watchlist', pathMatch: 'full'},
  {path: 'user-page/watchlist', component: UserPageComponent},
  {path: 'user-page/favourites', component: UserPageComponent},
  {path: 'user-page/watched', component: UserPageComponent}
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule { }
