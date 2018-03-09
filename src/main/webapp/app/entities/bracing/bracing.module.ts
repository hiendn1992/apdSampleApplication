import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ApdSampleApplicationSharedModule } from '../../shared';
import {
    BracingService,
    BracingPopupService,
    BracingComponent,
    BracingDetailComponent,
    BracingDialogComponent,
    BracingPopupComponent,
    BracingDeletePopupComponent,
    BracingDeleteDialogComponent,
    bracingRoute,
    bracingPopupRoute,
    BracingResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...bracingRoute,
    ...bracingPopupRoute,
];

@NgModule({
    imports: [
        ApdSampleApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        BracingComponent,
        BracingDetailComponent,
        BracingDialogComponent,
        BracingDeleteDialogComponent,
        BracingPopupComponent,
        BracingDeletePopupComponent,
    ],
    entryComponents: [
        BracingComponent,
        BracingDialogComponent,
        BracingPopupComponent,
        BracingDeleteDialogComponent,
        BracingDeletePopupComponent,
    ],
    providers: [
        BracingService,
        BracingPopupService,
        BracingResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ApdSampleApplicationBracingModule {}
