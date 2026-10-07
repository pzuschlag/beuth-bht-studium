import {Component, OnInit} from '@angular/core';

@Component({
  moduleId: module.id,
  selector: 'template-file',
  templateUrl: 'template-file.component.html', // OR html in file:
  // template: `<div></div>`,
  styleUrls: ['./styles/template-file.component.min.css'], // all styles are compiled to the folder (public)/styles/*
  // styles: [`template-file: {selector:rule};`],
  // directives: […],
  // pipes: […],
  // providers: […]

})
export class TemplateFileComponent {

  constructor() {}

}
