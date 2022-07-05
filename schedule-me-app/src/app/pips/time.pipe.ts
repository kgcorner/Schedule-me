import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'time'
})
export class TimePipe implements PipeTransform {

  transform(value: unknown, ...args: unknown[]): unknown {
    if(value instanceof Array) {
      let hour : string = value[0] +"";
      let min = value[1]+"";
      if(hour.length == 1) {
        hour = "0" + hour;
      }
      if(min.length == 1) {
        min = "0" + min;
      }
      return hour+":"+min;
    }
    return value;
  }

}
