import { Season } from './Season';

export class Episode {

  id: number;
  title: string;
  releaseDate: Date;
  runtime: number;
  serialNumber: number;
  season: Season;

}
