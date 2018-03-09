import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { BracingComponent } from './bracing.component';
import { BracingDetailComponent } from './bracing-detail.component';
import { BracingPopupComponent } from './bracing-dialog.component';
import { BracingDeletePopupComponent } from './bracing-delete-dialog.component';

@Injectable()
export class BracingResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const bracingRoute: Routes = [
    {
        path: 'bracing',
        component: BracingComponent,
        resolve: {
            'pagingParams': BracingResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bracings'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'bracing/:id',
        component: BracingDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bracings'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bracingPopupRoute: Routes = [
    {
        path: 'bracing-new',
        component: BracingPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bracings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'bracing/:id/edit',
        component: BracingPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bracings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'bracing/:id/delete',
        component: BracingDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Bracings'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
