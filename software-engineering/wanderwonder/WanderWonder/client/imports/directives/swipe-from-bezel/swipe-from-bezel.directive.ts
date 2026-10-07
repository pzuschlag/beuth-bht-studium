import {Directive, ElementRef, Input} from '@angular/core';
import {toast} from 'angular2-materialize';
import {HammerTimeService} from "../../services/hammer-time/hammer-time.service";


@Directive({
    selector: '[swipe-from-bezel]'
})
export class SwipeFromBezelDirective {

    @Input('swipe-from-bezel') element: string;

    constructor(private el: ElementRef, private hammertime: HammerTimeService) {
        // listen to events...
        hammertime.mc.on("panleft panright tap press", function(ev) {
            var endPoint = ev.pointers[0].pageX;
            var distance = ev.distance;
            var origin = endPoint - distance;

            if (origin <= 15) {
                toast('swiped near left edge', 500);
            } else if (origin >= (el.nativeElement.offsetWidth - 15)) {
                console.log("foo");
                toast('swiped near right edge', 500);
            }
        });

    }

}
