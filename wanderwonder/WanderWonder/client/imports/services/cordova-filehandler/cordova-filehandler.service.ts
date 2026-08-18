import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Meteor} from 'meteor/meteor';
import {GlobalHelperService} from "../global-helper-service/global-helper-service.service";

declare var cordova: any;
declare var FilePath: any;
declare var resolveLocalFileSystemURL: any;

@Injectable()
export class CordovaFilehandlerService {

    private _saveError: string;
    private _loadError: string;

    constructor(private _globalHelperService: GlobalHelperService) {
    }

    public openFile(callback) {
        var fileSelector = document.createElement('input');
        fileSelector.setAttribute('type', 'file');

        fileSelector.onchange = (evt) => {
            let file: File = evt.srcElement['files'][0];
            let isKML;
            try {
                isKML = this.checkValid(file.name);
            } catch (err) {
                this._loadError = err;
            }
            let reader = new FileReader();
            reader.onload = (event) => {
                callback({ result: reader.result, isKML: isKML, error: this._loadError });
            };
            reader.onerror = (event) => {
                this._loadError = "File could not be read!";
                callback({ result: reader.result, isKML: isKML, error: this._loadError });
            };
            reader.readAsText(file);
        };

        fileSelector.click();
    }

    private checkValid(path: string) {
        let filetype = path.substr(path.length - 3);
        if (filetype && filetype == 'kml') {
            return true;
        } else if (filetype && filetype == 'gpx') {
            return false;
        } else {
            throw new Error(`Filetype must be ".gpx" or ".kml", not ".${filetype}"`)
        }
    }

    public saveFile(content: string, name: string, isKML: boolean, callback) {
        if (Meteor.isCordova) {
            let fileType = isKML ? 'kml' : 'gpx';
            if (this._globalHelperService.isIOS) {
                console.error('ios device saving not yet implemented');
            } else if (this._globalHelperService.isANDROID) {
                resolveLocalFileSystemURL(`${cordova.file.externalRootDirectory}/Download`, (dirEntry) => {
                    dirEntry.getFile(`${name}.${fileType}`, { create: true }, (fileEntry) => {
                        fileEntry.createWriter((fileWriter) => {
                            fileWriter.onwriteend = (e) => {
                                callback({ name: `${name}.${fileType}`, error: this._saveError });
                            };
                            fileWriter.onerror = (e) => {
                                this._saveError = `Failed file write:  ${name}.${fileType}`;
                                callback({ name: `${name}.${fileType}`, error: this._saveError });
                            };
                            fileWriter.write(content);
                        }, (err) => console.error(err));
                    }, (err) => console.error(err));
                }, (err) => console.error(err));
            }
        }
    }
}
