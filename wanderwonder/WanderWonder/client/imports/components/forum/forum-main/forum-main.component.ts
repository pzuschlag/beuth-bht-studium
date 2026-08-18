import {Component} from '@angular/core';
import {SearchComponent} from "../../util/search/search.component";
import {ROUTER_DIRECTIVES} from '@angular/router';

@Component({
    selector: 'forum-main',
    templateUrl: 'client/imports/components/forum/forum-main/forum-main.component.html', // OR html in file:
    styleUrls: ['./styles/forum-main.component.min.css'],
    directives: [
        ROUTER_DIRECTIVES,
        SearchComponent
    ]

})
export class ForumMainComponent {

    private topics: Array<Object>;

    constructor() {
        this.topics = [
            {
                txt: 'Hüttenguide'
            },
            {
                txt: 'Events'
            },
            {
                txt: 'Bergführer'
            },
            {
                txt: 'Familienausflüge'
            },
            {
                txt: '…'
            }
        ]
    }

}
