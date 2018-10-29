import { Series } from './Series';
import { Episode } from './Episode';

export class User {

  id: number;
  username: string;
  emailAddress: string;
  registrationDate: Date;
  watchlist: Series[];
  favourites: Series[];
  watchedEpisodes: Episode[];

}
