import {
    it,
    iit,
    describe,
    ddescribe,
    expect,
    inject,
    injectAsync,
    beforeEachProviders
} from '@angular/core/testing';
import {TestComponentBuilder} from '@angular/compiler/testing';
import {provide} from '@angular/core';
import {TemplateFile} from './template-file.pipe';

describe('TemplateFile Pipe', () => {

    beforeEachProviders(() => [TemplateFile]);

    it('should transform the input', inject([TemplateFile], (pipe:TemplateFile) => {
        expect(pipe.transform(true)).toBe(null);
    }));

});
