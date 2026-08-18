import {Injectable} from '@angular/core';
import {Subject, Observable} from 'rxjs';

declare var $: JQueryStatic;

const MODALS = {
    CONFIRMED: { id: '#passenger-confirmation', open: false },
    ACCEPT: { id: '#provider-accept', open: false },
    SETTINGS: { id: '#provider-settings', open: false }
}

@Injectable()
export class ModalHelperService {

    private _modalState: Subject<Object> = new Subject<Object>();

    /** openConfirmedModal - set modal state to open, open the modal with callback
     *
     *  @return {void}
     */
    public openConfirmedModal() {
        MODALS.CONFIRMED.open = true;
        this.openModal(MODALS.CONFIRMED.id,
            () => this.closedModal('CONFIRMED')
        );
    }

    /** openAcceptModal - set modal state to open, open the modal with callback
     *
     *  @return {void}
     */
    public openAcceptModal() {
        MODALS.ACCEPT.open = true;
        this.openModal(MODALS.ACCEPT.id,
            () => this.closedModal('ACCEPT')
        );
    }

    /** openSettingsModal - set modal state to open, open the modal with callback
     *
     *  @return {void}
     */
    public openSettingsModal() {
        MODALS.SETTINGS.open = true;
        this.openModal(MODALS.SETTINGS.id,
            () => this.closedModal('SETTINGS')
        );
    }

    public closeSettingsModal() {
        $(MODALS['SETTINGS'].id).remove();
        this.removeOverlay();
    }

    /** removeOverlay - manually remove the overlay of the materialize modal
     *
     *  @return {void}
     */
    public removeOverlay() {
        $('.lean-overlay').remove();
    }

    /** closeAll - if modal is open, close it and remove overlay
     *
     *  @return {boolean} if a modal was closed
     */
    public closeAll() {
        let wasOpen = false;
        for (let key in MODALS) {
            if (MODALS[key].open) {
                $(MODALS[key].id).closeModal();
                wasOpen = true;
            }
        }
        if (wasOpen) {
            this.removeOverlay();
        }
        return wasOpen;
    }

    /** openModal - open the modal by id and register callback for modal complete
     *              event
     *
     *  @return {void}
     */
    private openModal(modalID: string, callback) {
        $(modalID).openModal({
            dismissible: modalID !== '#provider-accept',
            complete: callback
        });
    }

    /** isModalOpen - check if one modal is open
     *
     *  @return {boolean} true if one of the modals is open
     */
    private isModalOpen(): boolean {
        for (let key in MODALS) {
            if (MODALS[key].open) {
                return true;
            }
        }
        return false;
    }

    /** closedModal - receive key of MODALS object, set modal open false, and
     *                trigger modalState.next
     *
     *  @return {void}
     */
    private closedModal(key: string) {
        MODALS[key].open = false;
        MODALS[key].key = key;
        this.modalState = MODALS[key];
    }

    /** get modalState$ - get the Observable of the modalState subject
     *
     *  @return {Observable<Object>} the Observable of the modalState subject
     */
    get modalState$(): Observable<Object> {
        return this._modalState.asObservable();
    }

    /**
     * set modalState - set next modalState Subject value
     *
     * @param  {Object} value Object with values of modalState
     */
    set modalState(value: Object) {
        this._modalState.next(value);
    }
}
