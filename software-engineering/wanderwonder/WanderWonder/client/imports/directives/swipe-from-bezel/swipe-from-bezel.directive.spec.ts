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
@Component({
    selector: 'test-component',
    template: `<div template-file></div>`
})
class SwipeFromBezelTestComponent {
}

describe('TemplateFile Directive', () => {

    beforeEachProviders(():any[] => []);
});
