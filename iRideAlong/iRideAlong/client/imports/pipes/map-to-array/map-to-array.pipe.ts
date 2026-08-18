import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
    name: 'mapToArray',
    pure: false
})
export class MapToArrayPipe implements PipeTransform {

    transform(value: any, args?: any): any {
        let output = [];
        if (value && value.map) {
            value.map.forEach((value, key, map) => {
                output.push(value);
            })
        }
        return output;
    }

}
