import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
    name: 'trimDecimals'
})
export class TrimDecimalsPipe implements PipeTransform {

    transform(value: number, args?: number): number {
        let decimals = args || 2;
        let varArg = Math.pow(10, decimals);
        return Math.floor(value * varArg) / varArg;
    }
}
