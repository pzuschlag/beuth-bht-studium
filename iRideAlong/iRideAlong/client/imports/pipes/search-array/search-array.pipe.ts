import {Station} from "../../services/station-helper/station";
import {Pipe, PipeTransform} from "@angular/core";


@Pipe({
    name: 'searchArray',
    // pure: false
})
export class SearchArrayPipe implements PipeTransform {

    transform(value: Array<Object>, limit: number, term: string): any {
        if (term) {
            term = term ? term.toLowerCase() : '';
            value = value.filter(
                (station: Station, index: number) =>
                    station.name.toLowerCase().indexOf(term) !== -1);
            return value.slice(0, limit);;
        }
        return [];
    }
}
