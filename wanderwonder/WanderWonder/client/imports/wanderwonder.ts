import {Component} from '@angular/core';
import {Routes, ROUTER_DIRECTIVES, Router} from '@angular/router';
import {StartMainComponent} from
"./components/start/start-main/start-main.component";
import {CreateRouteMainComponent} from "./components/create-route/create-route-main/create-route-main.component";
import {ForumMainComponent} from "./components/forum/forum-main/forum-main.component";
import {RouteDetailsMainComponent} from "./components/route-details/route-details-main/route-details-main.component";

// declare variables for typescript compiler from already imported modules (eg. jquery installed via meteor)
declare var $: JQueryStatic;

@Component({
    selector: 'wander-wonder',
    templateUrl: 'client/imports/wanderwonder.html',
    styleUrls: ['./styles/wanderwonder.min.css'], // all styles are compiled to the folder (public)/styles/*
    directives: [ROUTER_DIRECTIVES]
})

@Routes([
    { path: '/', component: StartMainComponent }, // useAsDefault: true
    { path: '/forum', component: ForumMainComponent },
    { path: '/create', component: CreateRouteMainComponent },
    { path: '/details', component: RouteDetailsMainComponent },
])

export class WanderWonder {

    private input: string;

    constructor(private router: Router) {
    }

    ngOnInit() {
        // http://materializecss.com/collapsible.html
        // https://github.com/InfomediaLtd/angular2-materialize
        $('main').height($(window).height());
        $(window).bind('orientationchange', (e) => {
            setTimeout(() =>  {
                $('main').height($(window).height());
            }, 250);
            console.log(e, 'orientationchange');
        });
    }
}
