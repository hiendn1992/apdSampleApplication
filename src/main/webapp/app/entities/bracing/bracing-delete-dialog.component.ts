import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Bracing } from './bracing.model';
import { BracingPopupService } from './bracing-popup.service';
import { BracingService } from './bracing.service';

@Component({
    selector: 'jhi-bracing-delete-dialog',
    templateUrl: './bracing-delete-dialog.component.html'
})
export class BracingDeleteDialogComponent {

    bracing: Bracing;

    constructor(
        private bracingService: BracingService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bracingService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'bracingListModification',
                content: 'Deleted an bracing'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-bracing-delete-popup',
    template: ''
})
export class BracingDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bracingPopupService: BracingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.bracingPopupService
                .open(BracingDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
