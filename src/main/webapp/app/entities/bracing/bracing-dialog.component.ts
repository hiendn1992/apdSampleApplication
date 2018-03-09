import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Bracing } from './bracing.model';
import { BracingPopupService } from './bracing-popup.service';
import { BracingService } from './bracing.service';

@Component({
    selector: 'jhi-bracing-dialog',
    templateUrl: './bracing-dialog.component.html'
})
export class BracingDialogComponent implements OnInit {

    bracing: Bracing;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private bracingService: BracingService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.bracing.id !== undefined) {
            this.subscribeToSaveResponse(
                this.bracingService.update(this.bracing));
        } else {
            this.subscribeToSaveResponse(
                this.bracingService.create(this.bracing));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Bracing>>) {
        result.subscribe((res: HttpResponse<Bracing>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Bracing) {
        this.eventManager.broadcast({ name: 'bracingListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-bracing-popup',
    template: ''
})
export class BracingPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bracingPopupService: BracingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.bracingPopupService
                    .open(BracingDialogComponent as Component, params['id']);
            } else {
                this.bracingPopupService
                    .open(BracingDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
