import {Injectable, provide} from '@angular/core';
import {Observable, Subscription, Subject} from 'rxjs';
import {CordovaGeolocationService} from "../cordova-geolocation/cordova-geolocation.service";
import {GlobalHelperService} from "../global-helper-service/global-helper-service.service";

declare var navigator: any;
declare var Camera: any;

@Injectable()
export class CordovaCameraService {

    private _currentPhoto: Subject<Object> = new Subject<Object>();
    private _currentPhoto$: Observable<Object> = this._currentPhoto.asObservable();

    constructor(
        private _geolocationService: CordovaGeolocationService,
        private _globalHelperService: GlobalHelperService) {
        console.log("CameraService constructor");
    }

    /**
    * opens device`s camera and takes picture
    */
    public takePhoto(): void {
        var options = {
            quality: 20, //20% of full resolution
            destinationType: Camera.DestinationType.DATA_URL, //Format of return value
            sourceType: Camera.PictureSourceType.CAMERA, //set the picture source
            encodingType: Camera.EncodingType.JPEG,
            mediaType: Camera.MediaType.PICTURE,
            saveToPhotoAlbum: true,  //Save the image to the photo album on the device
            correctOrientation: true, //Corrects Android orientation quirks
            targetWidth: 512,
            targetHeight: 512
        }
        navigator.camera.getPicture((imageUri) => this._cameraSuccess(imageUri), (err) => this._cameraError(err), options);
    }

    /**
    * onSuccess: gets current locaten and saves image URI in Poin (geo.ts)
    */
    private _cameraSuccess(imageUri: string): void {
        let subs = this._geolocationService.getCoordinates().subscribe(    //get locations
            (position) =>
                this._currentPhoto.next(
                    {
                        img: imageUri,
                        lat: position.coords.latitude,
                        lng: position.coords.longitude
                    }
                ),
            (error) => console.error("Geolocation error: " + error),
            () => subs.unsubscribe()
        );

        if (this._globalHelperService.isIOS) {
            navigator.camera.cleanup(() => {           // iOS only: cleanup chache
                console.log("Camera cleanup success.")
            }, (message) => {
                console.log('Camera cleanup failed: ' + message);
            });
        }
    }

    /**
    * onError: Callback if camera fails
    */
    private _cameraError(message?: string): void {
        setTimeout(() => {
            alert("Camera error occured: " + message);
        }, 0);
    }

    /**
    * Getter: returns an obersavable for photos
    */
    public get currentPhoto$(): Observable<Object> {
        return this._currentPhoto$;
    }


}
