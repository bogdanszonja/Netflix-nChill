import { Season } from './Season';

export class Series {

  id: number;
  title: string;
  description: string;
  status: string;
  airDate: Date;
  seasons: Season[];
  genres: string[];

}
