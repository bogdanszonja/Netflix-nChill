import { Episode } from "./Episode";

export class Season {

  id: number;
  title: string;
  description: string;
  year: Date;
  serialNumber: number;
  episodes: Episode[];

}
