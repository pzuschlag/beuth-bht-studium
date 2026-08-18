import {Component} from '@angular/core';
import {MaterializeDirective} from "angular2-materialize";

@Component({
    selector: 'route-details-main',
    templateUrl: 'client/imports/components/route-details/route-details-main/route-details-main.component.html', // OR html in file:
    styleUrls: ['./styles/route-details-main.component.min.css'],
    directives: [
        MaterializeDirective
    ]

})
export class RouteDetailsMainComponent {

    private routeInfos: Array<Object>;
    private mainImage: string;

    constructor() {

        this.mainImage = 'https://images.unsplash.com/photo-1459231978203-b7d0c47a2cb7?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&s=23154c381ecb96dd70781ae0bda41860';
        this.routeInfos = [
            {
                icn: 'fa-info',
                img: 'https://images.unsplash.com/photo-1459231978203-b7d0c47a2cb7?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&s=23154c381ecb96dd70781ae0bda41860',
                header: 'Routeninformation',
                body: {
                    header: 'Eins-A Superroute',
                    body: 'Lorem ipsum dolor sit amet'
                }
            },
            {
                icn: 'fa-sun-o',
                img: 'https://images.unsplash.com/photo-1444090542259-0af8fa96557e?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&s=b263b22ca6e84d3d50bd86717beaa629',
                header: 'Wetter',
                body: {
                    header: 'Eins-A Wetter',
                    body: 'Lorem ipsum dolor sit amet'
                }
            },
            {
                icn: 'fa-tree',
                img: 'https://images.unsplash.com/photo-1444309251453-6b95825ea872?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&s=afc699015897644c55ab9342272310cf',
                header: 'Erfahrungsberichte',
                body: {
                    header: 'Eins-A Erfahrungsbericht',
                    body: 'Lorem ipsum dolor sit amet'
                }
            },
            {
                icn: 'fa-eye',
                img: 'https://kwerfeldein.de/wp-content/uploads/2013/10/aussicht.jpg',
                header: 'Aussichtspunkte',
                body: {
                    header: 'Eins-A Aussichtspunkt',
                    body: 'Lorem ipsum dolor sit amet'
                }
            },
            {
                icn: 'fa-exclamation-triangle',
                img: 'https://images.unsplash.com/photo-1440149064658-a1a13625a7bf?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&s=c7e478eb11850f96b95ce0c44795b747',
                header: 'Gefahren',
                body: {
                    header: 'Eins-A Gefahr',
                    body: 'Lorem ipsum dolor sit amet'
                }
            },
            {
                icn: 'fa-newspaper-o',
                img: 'https://images.unsplash.com/photo-1424886097867-7a53e6058dff?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&s=90fcb4ea7a34e6968720ecbe35443716',
                header: 'Neuigkeiten',
                body: {
                    header: 'Eins-A Neuigkeit',
                    body: 'Lorem ipsum dolor sit amet'
                }
            },
            {
                icn: 'fa-bed',
                img: 'https://images.unsplash.com/photo-1445308394109-4ec2920981b1?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&s=37943d3b5c2a6a6401b5c9b9f0c60a68',
                header: 'Unterkünfte',
                body: {
                    header: 'Eins-A Unterkunft',
                    body: 'Lorem ipsum dolor sit amet'
                }
            }
        ]
    }

    ngOnInit() {
    }

    // TODO: get saved pictures of route an show in DOM

}
