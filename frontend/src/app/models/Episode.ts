import { Season } from './Season';

export class Episode {

  id: number;
  title: string;
  releaseDate: Date;
  runtime: number;
  episodeNumber: number;
  season: Season;

}
