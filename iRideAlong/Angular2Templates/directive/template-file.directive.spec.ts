import {
    beforeEachProviders,
    describe,
    ddescribe,
    expect,
    iit,
    it,
    inject,
    injectAsync
} from '@angular/core/testing';
import {
    ComponentFixture, TestComponentBuilder
} from '@angular/compiler/testing';
import {provide, Component} from '@angular/core';
import {TemplateFile} from './template-file.directive';

@Component({
    selector: 'test-component',
    template: `<div template-file></div>`
})
class TestComponent {
}

describe('TemplateFile Directive', () => {

    beforeEachProviders(():any[] => []);


    it('should ...', injectAsync([TestComponentBuilder], (tcb:TestComponentBuilder) => {
        return tcb.createAsync(TestComponent).then((fixture:ComponentFixture) => {
            fixture.detectChanges();
        });
    }));

});
