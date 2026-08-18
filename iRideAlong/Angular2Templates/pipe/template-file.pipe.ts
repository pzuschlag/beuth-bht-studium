import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'templateFile'
})
export class TemplateFile implements PipeTransform {

  transform(value: any, args?: any): any {
    return null;
  }

}
