import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Bracing } from './bracing.model';
import { BracingService } from './bracing.service';

@Component({
    selector: 'jhi-bracing-detail',
    templateUrl: './bracing-detail.component.html'
})
export class BracingDetailComponent implements OnInit, OnDestroy {

    bracing: Bracing;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private bracingService: BracingService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBracings();
    }

    load(id) {
        this.bracingService.find(id)
            .subscribe((bracingResponse: HttpResponse<Bracing>) => {
                this.bracing = bracingResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBracings() {
        this.eventSubscriber = this.eventManager.subscribe(
            'bracingListModification',
            (response) => this.load(this.bracing.id)
        );
    }
}
