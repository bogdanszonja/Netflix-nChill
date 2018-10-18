import { Series } from './Series';
import { Episode } from "./Episode";

export class User {

  id: number;
  username: string;
  emailAddress: string;
  password: string;
  watchList: Series[];
  favourites: Series[];
  watchedEpisodes: Episode[];
  registrationDate: Date;

}
