import {GoogleMapsApiLoaderService} from './services/google-maps-api-loader/google-maps-api-loader.service';
import {GoogleMapsWrapperService} from './services/google-maps-wrapper/google-maps-wrapper.service';
import {GoogleMapsHelperService} from './services/google-maps-helper/google-maps-helper.service';

export const GOOGLE_MAPS_SERVICE_PROVIDER: any[] =
    [GoogleMapsApiLoaderService, GoogleMapsWrapperService, GoogleMapsHelperService];
