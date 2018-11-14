import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'timeConverter'
})
export class TimeConverterPipe implements PipeTransform {

  transform(value: number): string {
    console.log(value);
    const remainder = value % 1440;
    const days = (value - remainder) / 1440;
    const minutes = remainder % 60;
    const hours = (remainder - minutes) / 60;

    let answer: string;

    answer = (days === 1) ? days + ' day ' : days + ' days ';
    answer += (hours === 1) ? hours + ' hour ' : hours + ' hours ';
    answer += (minutes === 1) ? minutes + ' minute ' : minutes + ' minutes';

    return answer;
  }

}
