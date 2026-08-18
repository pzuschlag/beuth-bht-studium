import {
  beforeEachProviders,
  it,
  iit,
  describe,
  ddescribe,
  expect,
  inject,
  injectAsync
} from '@angular/core/testing';
import {provide} from '@angular/core';
import {TemplateFileService} from './template-file.service';

describe('TemplateFile Service', () => {

  beforeEachProviders(() => [TemplateFileService]);
  
  it('should ...', inject([TemplateFileService], (service: TemplateFileService) => {

  }));

});
