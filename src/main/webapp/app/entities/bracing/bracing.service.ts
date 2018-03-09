import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Bracing } from './bracing.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Bracing>;

@Injectable()
export class BracingService {

    private resourceUrl =  SERVER_API_URL + 'api/bracings';

    constructor(private http: HttpClient) { }

    create(bracing: Bracing): Observable<EntityResponseType> {
        const copy = this.convert(bracing);
        return this.http.post<Bracing>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(bracing: Bracing): Observable<EntityResponseType> {
        const copy = this.convert(bracing);
        return this.http.put<Bracing>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Bracing>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Bracing[]>> {
        const options = createRequestOption(req);
        return this.http.get<Bracing[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Bracing[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Bracing = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Bracing[]>): HttpResponse<Bracing[]> {
        const jsonResponse: Bracing[] = res.body;
        const body: Bracing[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Bracing.
     */
    private convertItemFromServer(bracing: Bracing): Bracing {
        const copy: Bracing = Object.assign({}, bracing);
        return copy;
    }

    /**
     * Convert a Bracing to a JSON which can be sent to the server.
     */
    private convert(bracing: Bracing): Bracing {
        const copy: Bracing = Object.assign({}, bracing);
        return copy;
    }
}
