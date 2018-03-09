/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ApdSampleApplicationTestModule } from '../../../test.module';
import { BracingComponent } from '../../../../../../main/webapp/app/entities/bracing/bracing.component';
import { BracingService } from '../../../../../../main/webapp/app/entities/bracing/bracing.service';
import { Bracing } from '../../../../../../main/webapp/app/entities/bracing/bracing.model';

describe('Component Tests', () => {

    describe('Bracing Management Component', () => {
        let comp: BracingComponent;
        let fixture: ComponentFixture<BracingComponent>;
        let service: BracingService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ApdSampleApplicationTestModule],
                declarations: [BracingComponent],
                providers: [
                    BracingService
                ]
            })
            .overrideTemplate(BracingComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BracingComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BracingService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Bracing(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.bracings[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
